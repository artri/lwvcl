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
package org.zaval.misc.event;

/**
 * This class is used to notify when a position has been changed.
 */
public class PosEvent
extends java.util.EventObject
{
  private int prevOffset, prevLine, prevCol;

 /**
  * Constructs the event object with the specified source object, the previous position,
  * the previous line and column.
  * @param <code>src</code> the object where the event originated.
  * @param <code>po</code> the specified previous position.
  * @param <code>pl</code> the specified previous line.
  * @param <code>pc</code> the specified previous column.
  */
  public PosEvent(Object src, int po, int pl, int pc)
  {
    super(src);
    prevOffset = po;
    prevLine   = pl;
    prevCol    = pc;
  }

 /**
  * Gets the previous position.
  * @return a previous position.
  */
  public int getPrevOffset() {
    return prevOffset;
  }

 /**
  * Gets the previous line number.
  * @return a previous line number.
  */
  public int getPrevLine() {
    return prevLine;
  }

 /**
  * Gets the previous column.
  * @return a previous column.
  */
  public int getPrevCol() {
    return prevCol;
  }
}

