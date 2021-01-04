/**
 *     Caption: Zaval Light-Weight Visual Components Library
 *     $Revision: 2.79 $
 *     $Date: 2003/10/08 11:24:16 $
 *
 *     @author:     Andrei Vishnevsky
 *     @version:    3.5.4
 *
 * Zaval Light-Weight Visual Components Library (LwVCL) is a pure Java
 * alternative to humble AWT-based and SWING-based GUI interfaces for
 * wide ranges of platforms, including J2SE, PersonalJava and J2ME.
 *
 * Designed as light-weight but, alternatively to Swing, built separately
 * from AWT (not on top of the java.awt library like Swing), the LwVCL is
 * the good alternative to highly performant, memory-efficient, flexible
 * GUI solution for embedded, stand-alone and applet applications.
 *
 * For more info on this product read Zaval Light-Weight Visual Components Library Tutorial
 * (It comes within this package).
 * The latest product version is always available from the product's homepage:
 * http://www.zaval.org/products/lwvcl/
 * and from the SourceForge:
 * http://sourceforge.net/projects/zaval0003/
 *
 * Contacts:
 *   Support : support@zaval.org
 *   Change Requests : change-request@zaval.org
 *   Feedback : feedback@zaval.org
 *   Other : info@zaval.org
 *
 * Copyright (C) 2001-2003  Zaval Creative Engineering Group (http://www.zaval.org)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * (version 2) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.zaval.lw.mask;

import java.util.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

/**
 * This class implements text model interface for masked text.
 */
public class MaskedText
implements TextModel
{
  private MaskElement[] elements = new MaskElement[0];
  private MaskValidator validator;
  private StringBuffer mask = new StringBuffer();
  private int extra;
  private TextListenerSupport support;
  private String text = "";

 /**
  * Constructs the class with the specified mask.
  * @param <code>mask</code> the specified mask.
  */
  public MaskedText(String mask) {
    this (mask, new BasicMaskValidator());
  }

 /**
  * Constructs the class with the specified mask and the given mask validator.
  * @param <code>mask</code> the specified mask.
  * @param <code>v</code> the specified mask validator.
  */
  public MaskedText(String mask, MaskValidator v) {
    validator = v;
    setMask(mask);
  }

 /**
  * Gets the mask.
  * @return a mask.
  */
  public String getMask () {
    return mask.toString ();
  }

 /**
  * Sets the specified mask.
  * @param <code>m</code> the specified mask.
  */
  public void setMask (String m) {
    if (!m.equals(getMask())) resetMask(m);
  }

  public String getLine (int i) {
    return getText();
  }

 /**
  * Gets the mask validator.
  * @return a mask validator.
  */
  public MaskValidator getValidator() {
    return validator;
  }

 /**
  * Sets the specified mask validator.
  * @param <code>v</code> the specified mask validator.
  */
  public void setValidator(MaskValidator v)
  {
    if (v != validator)
    {
      validator = v;
      resetMask(getMask());
    }
  }

  public String getText () {
    return text;
  }

  public void setText (String t) {
    setText (0, t);
  }

  public int getSize () {
    return 1;
  }

  public int getTextLength () {
    return mask.length();
  }

  public void write(char ch, int offset) {
    write((new Character(ch)).toString(), offset);
  }

  public void write(String s, int offset) {
    setText(offset, s);
  }

  public void remove (int offset, int count)
  {
    int[] res = findElementByOffset(offset);
    int k = res[1], c = count;
    for (int j = res[0]; j < elements.length && count > 0; j++)
    {
      if (elements[j].getType() != -1)
      {
        for (; k < elements[j].size && count > 0; k++, count--)
          if (validator.isHandledTag(elements[j].getTagAt(k)))
            elements[j].value[k] = validator.getBlankChar(elements[j].getTagAt(k));
      }
      else count -= (elements[j].size - k);
      k = 0;
    }

    if (c > 0)
    {
      String nt = formTextValue();
      if (!nt.equals(text))
      {
        text = nt;
        perform (TextEvent.UPDATED, offset, c);
      }
    }
  }

  public void addTextListener(TextListener l) {
    if (support == null) support = new TextListenerSupport();
    support.addListener(l);
  }

  public void removeTextListener(TextListener l) {
    if (support != null) support.removeListener(l);
  }

  public int getExtraChar (int i) {
    return extra;
  }

  public void setExtraChar (int i, int ch) {
    extra = ch;
  }

 /**
  * Looks for the next handled (by the validator) character index in the text starting from the
  * given offset. The method gets the direction argument that defines look-up direction (forward or backwards).
  * @param <code>offset</code> the starting offset.
  * @param <code>d</code> the look-up direction.
  * @return a next handled character index.
  */
  public int findHandledTag (int offset, int d)
  {
    while (offset + d > 0 && offset + d < getTextLength() && !isHandledOffset(offset))
      offset+=d;
    return offset;
  }

  public boolean isHandledOffset (int offset) {
    return validator.isHandledTag(mask.charAt(offset));
  }

  void setText (int offset, String value)
  {
    int[]  res = findElementByOffset(offset);
    char[] buf = value.toCharArray();
    int i = 0, j = res[0], k = res[1];
    while (i < buf.length && j < elements.length)
    {
      if (elements[j].getType() != -1)
      {
        int    len = Math.min (elements[j].getSize() - k, value.length() - i);
        char[] tmp = elements[j].getValue().toCharArray();
        System.arraycopy (buf, i, tmp, k, len);
        String newValue = validator.completeValue(elements[j], new String(tmp));
        if (validator.isValidValue(elements[j], newValue)) elements[j].value = newValue.toCharArray();
        else break;
        i += len;
      }
      else
        for (;k < elements[j].size && i < buf.length && buf[i] == elements[j].getTagAt(k);k++);
      j++;
      k = 0;
    }

    if (i > 0)
    {
      String nt = formTextValue();
      if (!nt.equals(text))
      {
        text = nt;
        perform (TextEvent.UPDATED, offset, i);
      }
    }
  }

  void resetMask (String m)
  {
    if (mask.length() > 0)
    {
      StringBuffer prev = mask;
      mask     = new StringBuffer();
      elements = new MaskElement[0];
      perform (TextEvent.REMOVED, 0, prev.length());
    }

    mask = new StringBuffer(m);
    Vector res = new Vector();
    int index = 0, length = mask.length();
    while (index < length)
    {
      int prevIndex = index;
      int elementType = validator.getTypeByTag(mask.charAt(index));
      while (index + 1 < length && elementType == validator.getTypeByTag(mask.charAt(index + 1)))
        index++;

      MaskElement el = new MaskElement();
      el.type  = elementType;
      el.tags  = m.substring(prevIndex, index + 1).toCharArray();
      el.size  = index - prevIndex + 1;
      el.value = new char[el.size];
      for (int i=0; i<el.size; i++)
        el.value[i] = (validator.isHandledTag(el.tags[i]))?validator.getBlankChar(el.tags[i]):el.tags[i];
      index++;
      res.addElement(el);
    }

    elements = new MaskElement[res.size()];
    for (int i=0; i<elements.length; i++) elements[i] = (MaskElement)res.elementAt(i);

    text = formTextValue();
    perform (TextEvent.INSERTED, 0, mask.length());
  }

  void perform (int id, int offset, int size)
  {
    if (support != null)
    {
      TextEvent e = new TextEvent(this, id, offset, size);
      e.setUpdatedLines(0, 1);
      support.perform(e);
    }
  }

  private int[] findElementByOffset(int offset)
  {
    int off = 0, j = 0;
    for (j=0;j<elements.length; j++)
    {
      if (offset >= off && offset < elements[j].size + off) break;
      off += elements[j].size;
    }
    return new int[] {j, offset-off};
  }

  private String formTextValue()
  {
    StringBuffer buf = new StringBuffer();
    for (int i=0; i<elements.length; i++) buf.append (elements[i].value);
    return buf.toString();
  }
}


