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
package org.zaval.data.event;

import org.zaval.data.*;

/**
 * This class describes event that is performed by <code>TextModel</code> interface implementation.
 * Using the event class the text implementation notifies listeners about the text content changes.
 */
public class TextEvent
extends org.zaval.util.event.EvObject
{
  /**
   * The text inserted event type.
   */
   public static final int INSERTED = 1;

  /**
   * The text removed event type.
   */
   public static final int REMOVED  = 2;

  /**
   * The text updated event type.
   */
   public static final int UPDATED  = 3;


   private int offset, size;
   private int firstUpdatedLine, updatedLines;

  /**
   * Constructs a new text event class with the given source, event id, text offset and
   * text size. The offset defines where removing or inserting operation has been performed
   * and size defines size of removed or inserted part.
   * @param <code>target</code> the source of the event.
   * @param <code>id</code> the type of the event.
   * @param <code>offset</code> the text offset.
   * @param <code>size</code> the text size.
   */
   public TextEvent(Object target, int id, int offset, int size)
   {
     super(target, id);
     this.offset = offset;
     this.size   = size;
   }

  /**
   * Sets the set of lines that has been updated.
   * @param <code>first</code> the specified start updated line number.
   * @param <code>lines</code> the specified number of updated lines.
   */
   public void setUpdatedLines (int first, int lines) {
     firstUpdatedLine = first;
     updatedLines     = lines;
   }

  /**
   * Gets the start updated line number from that the text has been modified.
   * @return a line number.
   */
   public int getFirstUpdatedLine() {
     return firstUpdatedLine;
   }

  /**
   * Gets the number of updated lines that has been modified for the text.
   * @return a number of lines.
   */
   public int getUpdatedLines() {
     return updatedLines;
   }

  /**
   * Returns the offset that defines where the removing or inserting opertaion has been
   * performed.
   * @return a text offset.
   */
   public int getOffset() {
     return offset;
   }

  /**
   * Returns the size that defines size of removed or inserted text part.
   * @return a text size.
   */
   public int getSize() {
     return size;
   }

  /**
   * Tests if the event type is possible. If the event type is unknown than
   * <code>IllegalArgumentException</code> exception will be thrown.
   * @param <code>id</code> the type of the event.
   */
   protected /*C#override*/ boolean checkID(int id) {
     return id == INSERTED || id == REMOVED || id == UPDATED;
   }
}
