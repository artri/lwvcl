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

import java.util.*;
import org.zaval.data.event.*;

/**
 * This class is an implementation of <code>TextModel</code> interface. It uses simple text
 * representation - a line is considered to be terminated by a line feed ('\n').
 */
public class Text
implements TextModel
{
  private static final int EXTRA_SIZE = 1;

  private Vector lines;
  private int    textLength;
  private TextListenerSupport support;

 /**
  * Constructs a new text with the given text.
  * @param <code>s</code> the initial value of the text.
  */
  public Text(String s) {
    setText(s==null?"":s);
  }

 /**
  * Returns the line at the specified line number.
  * @param  <code>line</code> the specified line number.
  * @return a line at the specified line number.
  */
  public String getLine (int line) {
    char[] buf = (char[])lines.elementAt(line);
    return new String(buf, 0, buf.length - EXTRA_SIZE);
  }

 /**
  * Sets the specified text data. The method performs the text data parsing process.
  * The process decides how the text data have to be divided into lines, the implementation
  * uses "\n" character as the text line separator.
  * @param  <code>text</code> the text data.
  */
  public void setText (String text)
  {
    String old = getText();
    if (old == null || !old.equals(text))
    {
      if (old != null)
      {
        TextEvent e = new TextEvent(this, TextEvent.REMOVED, 0, getTextLength());
        e.setUpdatedLines(0, 0);
        lines.removeAllElements();
        lines = null;
        perform (e);
      }
      lines = parse(text.toCharArray());
      textLength = text.length();
      TextEvent ee = new TextEvent(this, TextEvent.INSERTED, 0, getTextLength());
      ee.setUpdatedLines(0, getSize());
      perform(ee);
    }
  }

 /**
  * Returns the original text data that have been set with <code>setText</code> method.
  * @return an original text data.
  */
  public String getText ()
  {
    if (lines == null || lines.size() == 0) return null;
    StringBuffer buf = new StringBuffer();
    for (int i=0; i<lines.size(); i++)
    {
      if (i > 0) buf.append ('\n');
      char[] ch = (char[])lines.elementAt(i);
      buf.append (ch, 0, ch.length - EXTRA_SIZE);
    }
    return buf.toString();
  }

 /**
  * Returns the line number of the text.
  * @return a line number.
  */
  public int getSize() {
    return (lines == null)?0:lines.size();
  }

 /**
  * Inserts the specified text at the given offset. The offset has to be less than the text
  * length. The method performs re-parsing of the text.
  * @param <code>s</code> the text to be inserted.
  * @param <code>offset</code> the offset where the text will be inserted.
  */
  public void write (String s, int offset)
  {
    int    len     = s.length();
    int[]  info    = getLnInfo(0, 0, offset);

    char[] line    = (char[])lines.elementAt(info[0]);
    int    length  =  line.length - EXTRA_SIZE;

    char[] tmp      = new char[length + s.length()];
    int    lnOffset = offset - info[1];
    System.arraycopy(line, 0, tmp, 0, lnOffset);
    System.arraycopy(s.toCharArray(), 0, tmp, lnOffset, len);
    System.arraycopy(line, lnOffset, tmp, lnOffset + len, length - lnOffset);
    Vector v = parse(tmp);
    lines.removeElementAt(info[0]);
    for (int i=0; i<v.size(); i++)
      lines.insertElementAt(v.elementAt(i), info[0] + i);

    textLength += len;
    TextEvent e = new TextEvent(this, TextEvent.INSERTED, offset, s.length());
    e.setUpdatedLines(info[0], v.size());
    perform(e);
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
    int[]  i1 = getLnInfo (0, 0, offset);
    int[]  i2 = getLnInfo (i1[0], i1[1], offset + size);
    char[] line1 = (char[])lines.elementAt(i1[0]);
    StringBuffer buf = new StringBuffer();
    buf.append (line1, 0, line1.length - EXTRA_SIZE);
    for (int i=i1[0] + 1; i<i2[0]+1; i++)
    {
      buf.append ('\n');
      char[] bb =( char[])lines.elementAt(i);
      buf.append (bb, 0, bb.length - EXTRA_SIZE);
    }

    char[] tmp      = new char[buf.length() - size];
    int    lnOffset = offset - i1[1];

    buf.getChars(0, lnOffset, tmp, 0);
    if (lnOffset + size < buf.length())
      buf.getChars(lnOffset + size, buf.length(), tmp, lnOffset);

    for (int i=i1[0]; i<i2[0]+1; i++)
      lines.removeElementAt(i1[0]);

    Vector v = parse(tmp);
    for (int i=0; i<v.size(); i++)
      lines.insertElementAt(v.elementAt(i), i1[0] + i);

    textLength -= size;
    TextEvent e = new TextEvent(this, TextEvent.REMOVED, offset, size);
    e.setUpdatedLines(i1[0], (i1[0]!=i2[0] && (i1[1]-offset)==0 && (i2[1]-offset-size)==0)?0:1);
    perform(e);
  }

 /**
  * Parses the specified text buffer and returns a vector of the text lines. The method
  * determines how the text has to be divided to the string lines.
  * @param <code>buffer</code> the specified text buffer.
  * @return a vector of the strings that are represented as character arrays.
  */
  protected Vector parse(char[] buffer)
  {
    int    size = 0, offset = 0;
    Vector v = new Vector(3);

    while (offset < buffer.length)
    {
      if (buffer[offset] == '\n')
      {
        char[] line = new char[size + EXTRA_SIZE];
        System.arraycopy(buffer, offset - size, line, 0, size);
        v.addElement(line);
        size = 0;
      }
      else size++;
      offset++;
    }

    if (offset >= buffer.length)
    {
      char[] line = new char[size + EXTRA_SIZE];
      System.arraycopy(buffer, offset - size, line, 0, size);
      v.addElement(line);
    }

    return v;
  }

 /**
  * Fires the specified text event to all text listeners.
  * @param <code>e</code> the text event that has to be fired.
  */
  void perform (TextEvent e) {
    if (support != null) support.perform(e);
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
    return textLength;
  }

  public int getExtraChar (int i) {
    char[] line = (char[])lines.elementAt(i);
    return (int) line[line.length-1];
  }

  public void setExtraChar (int i, int ch) {
    char[] line = (char[])lines.elementAt(i);
    line[line.length-1] = (char)ch;
  }

  private int[] getLnInfo (int start, int startOffset, int o)
  {
    int offset = startOffset;
    for (int i=start; i<lines.size(); i++)
    {
      char[] buf = (char[])lines.elementAt(i);
      if (o >= offset && o <= offset + buf.length - EXTRA_SIZE) return new int[] { i, offset };
      offset += (buf.length + 1 - EXTRA_SIZE);
    }
    return null;
  }
}

