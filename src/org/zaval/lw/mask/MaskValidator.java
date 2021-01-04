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

/**
 * This interface is used with a masked text to perform mask validation process.
 * The interface provides following abilities
 * <ul>
 *  <li>
 *    Provides information what mask tags are handled with it.
 *    For this purpose <code>isHandlesTag</code> method is executed.
 *  </li>
 *  <li>
 *    Defines types of mask elements that are handled with this validator.
 *    Any mask element is bound with a type that defines rules which should be use to handle
 *    the mask element.
 *  </li>
 *  <li>
 *    Defines blank characters for handled mask tags by <code>getBlankChar</code> method.
 *  </li>
 *  <li>
 *    Completes the specified mask element by <code>completeValue</code> method.
 *  </li>
 *  <li>
 *    Validates the specified mask element by <code>isValidValue</code> method.
 *  </li>
 * </ul>
 */
public interface MaskValidator
{
 /**
  * Tests if the mask tag is handled with the validator.
  * @param <code>tag</code> the mask tag.
  * @return <code>true</code> if the mask handles the specified mask tag; otherwise
  * <code>false</code>.
  */
  boolean isHandledTag  (char tag);

 /**
  * Gets the type for the specified mask tag. The method should returns -1 type
  * if the tag is not bound with any type.
  * @param <code>tag</code> the mask tag.
  * @return a type.
  */
  int getTypeByTag(char tag);

 /**
  * Gets the blank character for the specified mask tag. The blank char is used to fill
  * mask value for the tag.
  * @param <code>tag</code> the mask tag.
  * @return a blank character.
  */
  char getBlankChar(char tag);

 /**
  * Tests if the specified value is valid for the mask element.
  * @param <code>e</code> the mask element.
  * @param <code>newValue</code> the new value.
  * @return <code>true</code> if the mask value is valid; otherwise
  * <code>false</code>.
  */
  boolean isValidValue  (MaskElement e, String newValue);

 /**
  * Completes the new value for the specified mask element.
  * @param <code>e</code> the mask element.
  * @param <code>newValue</code> the new value.
  * @return a new completed value.
  */
  String  completeValue (MaskElement e, String newValue);
}

