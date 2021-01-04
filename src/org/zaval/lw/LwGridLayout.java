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
import java.util.*;
import org.zaval.misc.*;
import org.zaval.util.*;

/**
 * This class implements layout manager interface. The layout manager divides the container area
 * into certain quantity of virtual rows and columns and every cell is used to place a
 * child component. A column preferred width is calculated as maximal preferred width among
 * all cells of the column. A row preferred height is calculated as maximal preferred height
 * among all cells of the row.
 * <p>
 * If the virtual cell space is larger than the component preferred size than the additional
 * constraints can be defined. Use LwConstraints class as the input argument of <code>add</code>
 * container method to describe the cell constraints. Using the constraints you can define
 * vertical and horizontal alignments, insets, filling rules. The table below illustrates the
 * layout manager usage (the samples use grid layout manager with two columns and two rows):
 * <table border="1" width="100%">
 *   <tr>
 *     <td align="center"><b>Constraints</b></td>
 *     <td align="center"><b>Sample App</b></td>
 *   </tr>
 *   <tr>
 *     <td>Default constraints (fill=LwToolkit.HORIZONTAL|LwToolkit.VERTICAL).</td>
 *     <td><img src="images/GridLayout1.gif"></td>
 *   </tr>
 *   <tr>
 *     <td>Cell[1][1] uses fill=LwToolkit.HORIZONTAL, vertical alignment is LwToolkit.CENTER.</td>
 *     <td align="center"><img src="images/GridLayout2.gif"></td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Cell[1][1] uses fill=LwToolkit.NONE, vertical alignment is Alignment.CENTER and horizontal
 *       alignment is Alignment.CENTER.
 *
 *     </td>
 *     <td align="center"><img src="images/GridLayout3.gif"></td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Cell[1][1] uses fill=LwToolkit.NONE, vertical alignment is Alignment.TOP and horizontal
 *       alignment is Alignment.LEFT.
 *
 *     </td>
 *     <td align="center"><img src="images/GridLayout4.gif"></td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Cell[1][1] uses fill=LwToolkit.VERTICAL, horizontal alignment is Alignment.RIGHT.
 *     </td>
 *     <td align="center"><img src="images/GridLayout5.gif"></td>
 *   </tr>
 * Additionally you can use stretching mask that says if the virtual cells should be stretched
 * according to the target container size vertically or horizontally.
 * </table>
 */
public class LwGridLayout
implements LwLayout, PosInfo
{
  private int       cols, rows, mask, count;
  private Hashtable args;

 /**
  * Constructs a new grid layout with the specified number of rows and columns.
  * @param <code>r</code> the specified number of rows.
  * @param <code>c</code> the specified number of columns.
  */
  public LwGridLayout(int r, int c) {
    this(r, c, 0);
  }

 /**
  * Constructs a new grid layout with the specified number of rows, columns and the layout stretching mask.
  * @param <code>r</code> the specified number of rows.
  * @param <code>c</code> the specified number of columns.
  * @param <code>m</code> the layout stretching mask. The mask indicates if it is necessary to stretch
  * virtual cells' sizes according to a parent area size. Use LwToolkit.HORIZONTAL, LwToolkit.VERTICAL or
  * LwToolkit.NONE values to construct the mask value.
  */
  public LwGridLayout(int r, int c, int m)
  {
    rows = r;
    cols = c;
    mask = m;
  }

 /**
  * Calculates the preferred size dimension for the layout container.
  * The method calculates "pure" preferred size, it means that an insets
  * of the container is not considered.
  * @param <code>c</code> the layout container.
  */
  public Dimension calcPreferredSize (LayoutContainer c) {
    return new Dimension(getColSizes(c)[cols], getRowSizes(c)[rows]);
  }

 /**
  * Lays out the child layoutable components inside the layout container.
  * @param <code>c</code> the layout container that needs to be laid out.
  */
  public void layout(LayoutContainer c)
  {
    int[]   colSizes = getColSizes(c);
    int[]   rowSizes = getRowSizes(c);
    Insets  insets   = c.getInsets();
    Point   offset   = c.getLayoutOffset();

    if ((mask & LwToolkit.HORIZONTAL) > 0)
    {
      int dw = c.getWidth() - insets.left - insets.right - colSizes[cols];
      for (int i=0; i<cols; i++)
        colSizes[i] = colSizes[i] + (dw*colSizes[i])/colSizes[cols];
    }

    if ((mask & LwToolkit.VERTICAL) > 0)
    {
      int dh = c.getHeight() - insets.top - insets.bottom - rowSizes[rows];
      for (int i=0; i<rows; i++)
        rowSizes[i] = rowSizes[i] + (dh*rowSizes[i])/rowSizes[rows];
    }

    int yy = insets.top, cc = 0;
    for (int i = 0; i < rows && cc < count; i++)
    {
      int xx = insets.left;
      for (int j = 0; j < cols && cc < count; j++, cc++)
      {
        Layoutable l = c.get(cc);
        if (l.isVisible())
        {
          LwConstraints arg = getConstraints(l, args);
          Dimension     d   = l.getPreferredSize();

          int cellW = colSizes[j];
          int cellH = rowSizes[i];

          if (arg.insets != null)
          {
            cellW -=  (arg.insets.left + arg.insets.right);
            cellH -=  (arg.insets.top  + arg.insets.bottom);
          }

          if ((LwToolkit.HORIZONTAL & arg.fill) > 0) d.width  = cellW;
          if ((LwToolkit.VERTICAL   & arg.fill) > 0) d.height = cellH;

          Point p = Alignment.getLocation(d, arg.ax, arg.ay, cellW, cellH);

          l.setSize    (d.width, d.height);
          l.setLocation(xx + offset.x + (arg.insets==null?0:arg.insets.left) + p.x,
                        yy + offset.y + (arg.insets==null?0:arg.insets.top) + p.y);
          xx += colSizes[j];
        }
      }
      yy += rowSizes[i];
    }
  }

 /**
  * Invoked when the specified layoutable component is added to the layout container, that
  * uses the layout manager. The specified constraints, layoutable component and child index
  * are passed as arguments into the method. The method stores the specified constraints for
  * the component if the constraints are defined.
  * @param <code>id</code> the layoutable component constraints.
  * @param <code>b</code> the layoutable component.
  * @param <code>index</code> the child index.
  */
  public void componentAdded(Object id, Layoutable b, int index)
  {
    count++;
    if (count > cols * rows) throw new IllegalArgumentException();
    if (id != null)
    {
      if (args == null) args = new Hashtable();
      args.put(b, id);
    }
  }

 /**
  * Invoked when the specified layoutable component is removed from the layout
  * container, that uses the layout manager. The manager removes appropriate constraints
  * for the given component if the constraints have been defined.
  * @param <code>lw</code> the layoutable component to be removed
  * @param <code>index</code> the child index.
  */
  public void componentRemoved(Layoutable lw, int index)
  {
    count--;
    if (args != null) {
      args.remove(lw);
      if (args.size() == 0) args = null;
    }
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define number of lines. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @return a number of lines. The method returns number of rows as the number of lines.
  */
  public int getLines   () {
    return (cols == 0 || rows == 0 || count == 0)?0:(count-1)/cols + 1;
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define the line size. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @param <code>line</code> the line number.
  * @return a size of the specified line. The method returns number of columns as the size
  * for any line number.
  */
  public int getLineSize(int line)
  {
    int lines = getLines();
    if (line >= lines) throw new IllegalArgumentException();
    return (line < lines - 1)?cols:(count-1)%cols + 1;
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define max offset. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @return a max offset. The max offset is computed as "(rows * columns - 1)" value.
  */
  public int getMaxOffset() {
    return count - 1;
  }

  private int[] getRowSizes(LayoutContainer c)
  {
    int[] res = new int[rows + 1];
    for (int i = 0; i<rows; i++)
    {
      res[i]     = getRowSize(i, c, args, cols);
      res[rows] += res[i];
    }
    return res;
  }

  private int[] getColSizes(LayoutContainer c)
  {
    int[] res = new int[cols + 1];
    for (int i = 0; i<cols; i++)
    {
      res[i]     = getColSize(i, c, args, cols);
      res[cols] += res[i];
    }
    return res;
  }

  private static int getRowSize(int row, LayoutContainer c, Hashtable args, int cols)
  {
    int max = 0;
    for (int i = 0; i<c.count(); i++)
    {
      Layoutable a = c.get(i);
      Point      p = MathBox.index2point(i, cols);
      if (a.isVisible() && p.x == row)
      {
        LwConstraints arg = getConstraints(a, args);
        Dimension d = a.getPreferredSize();
        if (arg.insets != null) d.height += (arg.insets.top + arg.insets.bottom);
        max = Math.max (d.height, max);
      }
    }
    return max;
  }

  private static int getColSize(int col, LayoutContainer c, Hashtable args, int cols)
  {
    int max = 0;
    for (int i = 0; i<c.count(); i++)
    {
      Layoutable a = c.get(i);
      Point      p = MathBox.index2point(i, cols);
      if (a.isVisible() && p.y == col)
      {
        LwConstraints arg = getConstraints(a, args);
        Dimension d = a.getPreferredSize();
        if (arg.insets != null) d.width += (arg.insets.left + arg.insets.right);
        max = Math.max (d.width, max);
      }
    }
    return max;
  }

  private static LwConstraints getConstraints (Layoutable l, Hashtable args)
  {
    if (args == null) return DEF_CONSTR;
    LwConstraints arg = (LwConstraints)args.get(l);
    return arg != null?arg:DEF_CONSTR;
  }

  private static final LwConstraints DEF_CONSTR = new LwConstraints();
}


