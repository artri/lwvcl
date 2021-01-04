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
 * The interface has to be implemented with a lightweight component that wants to use
 * LwTitledBorder view as a border view. The methods of the interface provide
 * information about the border title bounds and alignment.
 */
public interface LwTitleInfo
{
 /**
  * Gets the title size and location. The bounds usage depends on the title alignment:
  * <ul>
  *   <li>
  *     If the alignment is Alignment.BOTTOM or Alignment.TOP than the appropriate title
  *     border view will use <code>y</code> coordinate to locate the border and <code>x</code>
  *     coordinate is calculated depending on the title border alignment.
  *   </li>
  *   <li>
  *     If the alignment is Alignment.LEFT or Alignment.RIGHT than the appropriate title
  *     border view will use <code>x</code> coordinate to locate the border and <code>y</code>
  *     coordinate is calculated depending on the title border alignment.
  *   </li>
  * </ul>
  * @return a title size and location.
  */
  Rectangle getTitleBounds();

 /**
  * Gets the title alignment. The alignment can have one of following values:
  * Alignment.TOP, Alignment.BOTTOM, Alignment.LEFT, Alignment.RIGHT and it
  * defines how the title has to be placed on the border.
  * @return a title alignment.
  */
  int getTitleAlignment();
}
