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
 * The interface is used to represent text data. Actually the interface
 * determines following three abilities to work with a text:
 * <ul>
 *   <li>
 *     Get a text properties. It is possible to get text lines, text size and so on
 *     using appropriate methods of the interface.
 *   </li>
 *   <li>
 *     Modify text data. It is possible to write and remove text data using appropriate
 *     methods of the interface.
 *   </li>
 *   <li>
 *     Listen text events. The interface provides <code>addTextListener</code> and
 *     <code>removeTextListener</code> methods to listen the text events.
 *   </li>
 * </ul>
 */
public interface TextModel
{
 /**
  * Returns the line at the specified line number.
  * @param  <code>line</code> the specified line number.
  * @return a line at the specified line number.
  */
  String getLine (int line);

 /**
  * Sets the specified text data. As rule the method should perform parsing process.
  * The process decides how the text data have to be divided into lines.
  * @param  <code>text</code> the specified text data.
  */
  void setText (String text);

 /**
  * Returns the original text data that have been set with <code>setText</code> method.
  * @return an original text data.
  */
  String getText ();

 /**
  * Returns the line number of the text.
  * @return a line number.
  */
  int getSize ();

 /**
  * Inserts the specified text at the given offset. The offset has to be less than the text
  * length. Actually the method performs re-parsing of the text.
  * @param <code>s</code> the text to be inserted.
  * @param <code>offset</code> the offset where the text will be inserted.
  */
  void write  (String s, int offset);

 /**
  * Inserts the specified character at the given offset. The offset has to be less than the text
  * length. Actually the method performs re-parsing of the text.
  * @param <code>ch</code> the character to be inserted.
  * @param <code>offset</code> the offset where the character will be inserted.
  */
  void write  (char ch, int offset);

 /**
  * Removes a text at the specified offset with the size. The offset and the offset plus the
  * size have to be less than the text length.
  * @param <code>offset</code> the offset where the text will be removed.
  * @param <code>size</code> the size of the part that is going to be removed.
  */
  void remove (int offset, int size);

 /**
  * Returns the text length (number of the text characters).
  * @return a text length.
  */
  int getTextLength();

 /**
  * Adds the specified text listener to receive text events.
  * @param <code>l</code> the text listener.
  * @see      org.zaval.data.event.TextListener
  * @see      org.zaval.data.event.TextEvent
  */
  void addTextListener(TextListener l);

 /**
  * Removes the specified text listener.
  * @param <code>l</code> the text listener.
  * @see      org.zaval.data.event.TextListener
  * @see      org.zaval.data.event.TextEvent
  */
  void removeTextListener(TextListener l);


 /**
  * Gets special extra char that is used to store extra information by the specified index.
  * The method is depricated to be used, because it will be probably re-designed in the future.
  * @param <code>i</code> the specified extra char index.
  * @return an extra char value.
  */
  int getExtraChar (int i);

 /**
  * Sets special extra char that is used to store extra information by the specified index.
  * The method is depricated to be used, because it will probably be re-designed in the future.
  * @param <code>i</code> the specified extra char index.
  * @param <code>val</code> the specified extra char value.
  */
  void setExtraChar (int i, int val);
}
