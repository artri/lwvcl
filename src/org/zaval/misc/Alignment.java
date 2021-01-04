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
package org.zaval.misc;

import java.awt.*;

/**
 * This class provides set of useful methods to calculate metrical characteristics
 * for a rectangular area that has vertical or horizontal alignments. The alignments
 * types are shown with the table below (it is supposed that the table is the rectangular
 * area), where the horizontal alignment is denoted with <code>ax</code>, and vertical
 * - <code>ay</code>:
 * <br><br>
 * <table border="1">
 *   <tr>
 *      <td>
 *         ax = Alignment.LEFT <br>
 *         ay = Alignment.TOP
 *      </td>
 *      <td>
 *         ax = Alignment.CENTER <br>
 *         ay = Alignment.TOP
 *      </td>
 *      <td>
 *         ax = Alignment.RIGHT <br>
 *         ay = Alignment.TOP
 *      </td>
 *   </tr>
 *   <tr>
 *      <td>
 *         ax = Alignment.LEFT <br>
 *         ay = Alignment.CENTER
 *      </td>
 *      <td>
 *         ax = Alignment.CENTER <br>
 *         ay = Alignment.CENTER
 *      </td>
 *      <td>
 *         ax = Alignment.RIGHT <br>
 *         ay = Alignment.CENTER
 *      </td>
 *   </tr>
 *   <tr>
 *      <td>
 *         ax = Alignment.LEFT <br>
 *         ay = Alignment.BOTTOM
 *      </td>
 *      <td>
 *         ax = Alignment.CENTER <br>
 *         ay = Alignment.BOTTOM
 *      </td>
 *      <td>
 *         ax = Alignment.RIGHT <br>
 *         ay = Alignment.BOTTOM
 *      </td>
 *   </tr>
 * </table>
 */
public class Alignment
{
 /**
  * The left horizontal alignment type.
  */
  public static final int LEFT   = 1;

 /**
  * The right horizontal alignment type.
  */
  public static final int RIGHT = 2;

 /**
  * The top vertical alignment type.
  */
  public static final int TOP = 4;

 /**
  * The bottom vertical alignment type.
  */
  public static final int BOTTOM = 8;

 /**
  * The center horizontal or vertical alignment type.
  */
  public static final int CENTER = 0;

 /**
  * The none horizontal or vertical alignment type.
  */
  public static final int NONE = -1;

 /**
  * Calculates and returns location of the specified rectangular area that
  * is adjusted inside the given rectangular area with the specified horizontal
  * and vertical alignments .
  * @param <code>alignObj</code> the specified rectangular area to be adjusted.
  * @param <code>ax</code> the specified horizontal alignment.
  * @param <code>ay</code> the specified vertical alignment.
  * @param <code>aw</code> the specified align area width.
  * @param <code>ah</code> the specified align area height.
  * @return a location. The method throws <code>IllegalArgumentException</code> if
  * the horizontal or vertical alignment is undefined.
  */
  public static Point getLocation (Dimension alignObj, int alignX, int alignY, int aw, int ah)
  {
     Point r = new Point();
     if (alignObj == null) return r;

     if (alignX == LEFT ) r.x = 0;
     else
     if (alignX == RIGHT) r.x = aw - alignObj.width;
     else
     if (alignX == CENTER) r.x = (aw - alignObj.width)/2;
     else
     if (alignX != NONE) throw new IllegalArgumentException();

     if (alignY == TOP ) r.y = 0;
     else
     if (alignY == BOTTOM) r.y = ah - alignObj.height;
     else
     if (alignY == CENTER) r.y = (ah - alignObj.height)/2;
     else
     if (alignY != NONE) throw new IllegalArgumentException();
     return r;
  }
}
