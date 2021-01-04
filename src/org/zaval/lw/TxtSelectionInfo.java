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
package org.zaval.lw;

import java.awt.*;

/**
 * This interface is used with the text render class (LwAdvTextRender) to paint selected text.
 * The interface defines following properties that are necessary to paint selected text:
 * <ul>
 *   <li>
 *     Start selection row and column.
 *   </li>
 *   <li>
 *     Last selection row and column.
 *   </li>
 *   <li>
 *     Selection color.
 *   </li>
 * </ul>
 * <p>
 * To get selected text <code>getSelectedText</code> method should be used.
 */
public interface TxtSelectionInfo
{

 /**
  * Returns the start selection row and column
  * @return a start selection row and column. The result is represented using java.awt.Point
  * class where <code>x</code> field is selection row and <code>y</code> field is selection
  * column.
  */
  Point  getStartSelection();

 /**
  * Returns the last selection row and column
  * @return a last selection row and column. The result is represented using java.awt.Point
  * class where <code>x</code> field is selection row and <code>y</code> field is selection
  * column.
  */
  Point  getEndSelection();

 /**
  * Returns the selected text.
  * @return a selected text. The method returns <code>null</code> if there is not selected
  * text.
  */
  String getSelectedText();

 /**
  * Returns a color that is used to render selected text background.
  * @return the color to render selected text background.
  */
  Color getSelectColor();
}

