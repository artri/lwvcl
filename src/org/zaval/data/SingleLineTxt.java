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
package org.zaval.data;

import org.zaval.data.event.*;

/**
 * This class is an impl        ementation of <code>TextModel</code> interface. It is used to
 * represent single line text, it means that in any case the specified input string is
 * parsed to single line (you can use '\n' delimiter, but it will not have effect)
 * text with the implementation.
 */
public class SingleLineTxt
implements TextModel
{
  private StringBuffer buf;
  private int          maxLen, extra;

  private TextListenerSupport support;

 /**
  * Constructs a new text with the given text.
  * @param <code>s</code> the initial value of the text.
  */
  public SingleLineTxt(String s) {
    this(s, -1);
  }

 /**
  * Constructs a new text with the given text and the specified maximal length.
  * @param <code>s</code> the initial value of the text.
  * @param <code>max</code> the specified maximal length.
  */
  public SingleLineTxt(String s, int max) {
    maxLen = max;
    setText(s==null?"":s);
  }

 /**
        *
  * Returns the line at the specified line number.
  * @param  <code>line</code> the specified line number. The implementation doesn't use
  * the argument, since this is single line implementation.
  * @return a line at the specified line number.
  */
  public String getLine (int line) {
    return buf.toString();
  }

 /**
  * Sets the specified text data. The method performs the text data parsing process.
  * The process decides how the text data have to be divided into lines. The implementation
  * use the input text as is.
  * @param  <code>text</code> the text data.
  */
  public void setText (String text)
  {
    String old = getText();
    if (old == null || !old.equals(text))
    {
      if (old != null)
      {
        buf = null;
        perform (TextEvent.REMOVED, 0, getTextLength());
      }

      buf = (maxLen > 0 && text.length() > maxLen)? new StringBuffer(text.substring(0, maxLen))
                                                  : new StringBuffer(text);
      perform(TextEvent.INSERTED, 0, getTextLength());
    }
  }

 /**
  * Returns the original text data that have been set with <code>setText</code> method.
  * @return an original text data.
  */
  public String getText () {
    return (buf == null)?null:buf.toString();
  }

 /**
  * Returns the line number of the text.
  * @return a line number.
  */
  public int getSize() {
    return (buf==null)?0:1;
  }

 /**
  * Inserts the specified text at the given offset. The offset has to be less than the text
  * length. The method performs re-parsing of the text.
  * @param <code>s</code> the text to be inserted.
  * @param <code>offset</code> the offset where the text will be inserted.
  */
  public void write (String s, int offset)
  {
    if (maxLen <= 0 || getTextLength() + s.length() <= maxLen)
    {
      buf.insert(offset, s);
      perform(TextEvent.INSERTED, offset, s.length());
    }
  }

 /**
  * Inserts the specified character at the given offset. The offset has to be less than the text
  * length. The method performs re-parsing of the text.
  * @param <code>ch</code> the character to be inserted.
  * @param <code>offset</code> the offset where the character will be inserted.
  */
  public void write (char ch, int offset) {
    write (String.valueOf(ch), offset);
  }

 /**
  * Removes a text at the specified offset with the size. The offset and the offset plus the
  * size have to be less than the text length.
  * @param <code>offset</code> the offset where the text will be removed.
  * @param <code>size</code> the size of the part that is going to be removed.
  */
  public void remove (int offset, int size)
  {
    String s = buf.toString();
    s = s.substring(0, offset) + s.substring(offset + size);
    buf = new StringBuffer(s);
    perform(TextEvent.REMOVED, offset, size);
  }

 /**
  * Fires the specified text event to all text listeners. The text event is created
  * with the method using the specified <code>id</code>,<code>offset</code> and
  * <code>size</code>.
  * @param <code>id</code> the specified event id.
  * @param <code>offset</code> the specified offset.
  * @param <code>size</code> the specified size.
  */
  void perform (int id, int offset, int size)
  {
    if (support != null)
    {
      TextEvent e = new TextEvent(this, id, offset, size);
      e.setUpdatedLines(0, 1);
      support.perform(e);
    }
  }

 /**
  * Adds the specified text listener.
  * @param <code>l</code> the text listener.
  */
  public void addTextListener(TextListener l) {
    if (support == null) support = new TextListenerSupport();
    support.addListener(l);
  }

 /**
  * Removes the specified text listener.
  * @param <code>l</code> the text listener.
  * @see      org.zaval.data.event.TextListener
  * @see      org.zaval.data.event.TextEvent
  */
  public void removeTextListener(TextListener l) {
    if (support != null) support.removeListener(l);
  }

 /**
  * Returns the text length.
  * @return a text length.
  */
  public int getTextLength() {
    return (buf == null)?-1:buf.length();
  }

  public int getExtraChar (int i) {
    return extra;
  }

  public void setExtraChar (int i, int ch) {
    extra = ch;
  }
}

