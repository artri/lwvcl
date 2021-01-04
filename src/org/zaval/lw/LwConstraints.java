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
import org.zaval.misc.*;

/**
 * The class is used to specify constrains that are used with the LwGridLayout layout manager.
 */
public class LwConstraints
{
 /**
  * Specifies number of columuns that has to be used for the cell.
  * The field is not used with the LwGridLayout layout manager.
  * It is going to be utilized in the future version of the library
  */
  public int colSpan = 1;

 /**
  * Specifies number of rows that has to be used for the cell.
  * The field is not used with the LwGridLayout layout manager.
  * It is going to be utilized in the future version of the library
  */
  public int rowSpan = 1;

 /**
  * This field is used when the component display area is larger
  * than the component requested size. It determines whether to
  * align the component, and if so, how.
  * <p>
  * The following constants can be used as the value:
  * <p>
  * <ul>
  *   <li>
  *     <b>LwToolkit.NONE</b> - do not align the component and use its preferred size.
  *   </li>
  *   <li>
  *     <b>LwToolkit.HORIZONTAL</b> - make the component wide enough to fill
  *     its display area horizontally, but do not change its height.
  *   </li>
  *   <li>
  *     <b>LwToolkit.VERTICAL</b> - make the component tall enough to fill its
  *          display area vertically, but do not change its width.
  *   </li>
  *   <li>
  *     <b>LwToolkit.HORIZONTAL | LwToolkit.VERTICAL</b> - make the component fill its display area entirely.
  *   </li>
  * </ul>
  * <p>
  *  <b>LwToolkit.HORIZONTAL|LwToolkit.VERTICAL</b> is used as default value for the field.
  */
  public int fill = LwToolkit.HORIZONTAL | LwToolkit.VERTICAL;

 /**
  * This field defines the component horizontal alignment inside the area.
  * Possible values for the alignment are: <b>Alignment.CENTER</b>,
  * <b>Alignment.LEFT</b>, <b>Alignment.RIGHT</b>, <b>Alignment.NONE</b>.
  */
  public int ax = Alignment.CENTER;


 /**
  * This field defines the component vertical alignment inside the area.
  * Possible values for the alignment are: <b>Alignment.TOP</b>,
  * <b>Alignment.BOTTOM</b>, <b>Alignment.CENTER</b>, <b>Alignment.NONE</b>.
  */
  public int ay = Alignment.CENTER;

 /**
  * This field specifies the external padding of the component, the
  * minimum amount of space between the component and the edges of its
  * display area.
  * <p>
  * The default value is <code>null</code>. This is equivalent of <code>(0, 0, 0, 0)</code>
  * insets.
  */
  public Insets insets = null;
}
