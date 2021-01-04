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
package org.zaval.util;

import java.awt.*;

/**
 * This class provides set of useful math methods.
 */
public class MathBox
{
 /**
  * Returns the insets that has <code>top</code>, <code>left</code>,
  * <code>bottom</code>, <code>right</code> fields calculated as a maximal values
  * of appropriate fields values for the specified insets objects.
  * @param <code>a</code> the specified insets object.
  * @param <code>b</code>  the specified insets object.
  * @return a maximal insets object.
  */
  public static Insets max (Insets a, Insets b)
  {
    if (a == null && b == null) return null;
    if (a == null) return b;
    else
    if (b == null) return a;
    else
    return new Insets(Math.max (a.top,   b.top),
                      Math.max (a.left,  b.left),
                      Math.max (a.bottom,b.bottom),
                      Math.max (a.right, b.right));
  }

 /**
  * Returns the dimension that has <code>width</code>, <code>height</code>,
  * fields calculated as a maximal values of appropriate fields values for the
  * specified dimension objects.
  * @param <code>a</code> the specified dimension object.
  * @param <code>b</code> the specified dimension object.
  * @return a maximal dimension object.
  */
  public static Dimension max (Dimension a, Dimension b)
  {
    if (a == null && b == null) return null;
    else
    if (a == null) return b;
    else
    if (b == null) return a;
    else
    return new Dimension (Math.max (a.width, b.width), Math.max (a.height, b.height));
  }

 /**
  * Computes and returns the row and column for the specified offset and the given
  * number of columns. The sample below shows the offset
  * meaning:
  * <br>
  * <table border="1">
  *   <tr>
  *     <td>row=0, column=0, offset=0</td>
  *     <td>row=0, column=1, offset=1</td>
  *     <td>row=0, column=2, offset=2</td>
  *   </tr>
  *   <tr>
  *     <td>row=1, column=0, offset=3</td>
  *     <td>row=1, column=1, offset=4</td>
  *     <td>row=1, column=2, offset=5</td>
  *   </tr>
  *   <tr>
  *     <td>row=2, column=0, offset=6</td>
  *     <td>row=2, column=1, offset=7</td>
  *     <td>row=2, column=2, offset=8</td>
  *   </tr>
  * </table>
  * @param <code>offset</code> the specified offset.
  * @param <code>cols</code> the number of columns.
  * @return an row and column. The result is represented with java.awt.Point class
  * where the <code>x</code> field correspond to row and the <code>y</code> field
  * correspond to column.
  */
  public static Point index2point(int offset, int cols)
  {
     Point p = new Point(-1, -1);
     if (offset < 0 || cols == 0) return p;
     p.x = (offset / cols);
     p.y = (offset % cols);
     return p;
  }

 /**
  * Converts the specified row and column to the offset. The following formula is used to
  * calculate the offset: <b>(row*columns) + column</b>
  * @param <code>row</code> the specified row.
  * @param <code>col</code> the specified column.
  * @param <code>cols</code> the number of columns.
  * @return an offset.
  */
  public static int indexByPoint(int row, int col, int cols) {
    return (cols <= 0)?-1:((row*cols) + col);
  }

 /**
  * Checks if the specified bits set satisfies to the given mask.
  * @param <code>bits</code> the specified bits set.
  * @param <code>mask</code> the specified mask.
  * @return <code>true</code> if  the specified bits set satisfies to the given mask;
  * otherwise <code>false</code>.
  */
  public static boolean checkBit(short bits, short mask) {
    return (bits & mask) > 0;
  }

 /**
  * Sets specified by the mask bits in the given bits set to <code>1</code> or <code>0</code>
  * value and returns the new bits set.
  * @param <code>bits</code> the specified bits set.
  * @param <code>mask</code> the specified mask.
  * @param <code>b</code> use <code>true</code> to set the appropriate bits in the bits set to
  * <code>1</code>; otherwise use <code>false</code>.
  * @return a new bits set.
  */
  public static short getBits(short bits, short mask, boolean b) {
    return (short) (b?(bits | mask):(bits & ~mask));
  }

 /**
  * Gets the intersection rectangle for the two specified rectnagles.
  * @return an intersection rectangle.
  */
  public static Rectangle intersection(int x1, int y1, int w1, int h1,
                                       int x2, int y2, int w2, int h2)
  {
    int xx1 = Math.max(x1, x2);
    int xx2 = Math.min(x1 + w1, x2 + w2);
    int yy1 = Math.max(y1, y2);
    int yy2 = Math.min(y1 + h1, y2 + h2);
    return new Rectangle(xx1, yy1, xx2 - xx1, yy2 - yy1);
  }
}
