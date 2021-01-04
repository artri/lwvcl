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

import java.util.*;
import java.awt.*;
import org.zaval.data.event.*;
import org.zaval.util.*;

/**
 * This class is implementation of <code>MatrixModel</code> interface and it used to organize
 * matrix-like structures.
 */
public class Matrix
implements MatrixModel
{
   private Object[][] objs;
   private int        rows, cols, alloc_rows, alloc_cols;
   private Vector support;

  /**
   * Constructs the matrix component with the specified number of rows an columns.
   * @param <code>rows</code> the specified number of rows.
   * @param <code>cols</code> the specified number of columns.
   */
   public Matrix(int rows, int cols) {
     setSize(rows, cols);
   }

  /**
   * Gets the number of rows.
   * @return a number of rows.
   */
   public int getRows () {
     return rows;
   }

  /**
   * Gets the number of columns.
   * @return a number of columns.
   */
   public int getCols () {
     return cols;
   }

  /**
   * Sets the number of rows.
   * @param <code>rows</code> the specified number of rows.
   */
   public void setRows (int rows) {
     setSize(rows, cols);
   }

  /**
   * Sets the number of columns.
   * @param <code>cols</code> the specified number of columns.
   */
   public void setCols (int cols) {
     setSize(rows, cols);
   }

  /**
   * Sets the specified matrix dimension.
   * @param <code>rows</code> the specified number of rows.
   * @param <code>cols</code> the specified number of columns.
   */
   public void setSize (int rows, int cols)
   {
     if (rows != this.rows || cols != this.cols)
     {
       if (alloc_cols < cols||
           alloc_rows < rows  )
       {
          Object[][] nobjs = new Object[rows][cols]; /*java*/

          /*C#object[][] nobjs = new object[rows][];*/
          /*C#for (int i=0;i<rows;i++) nobjs[i] = new object[cols];*/

          alloc_cols = cols;
          alloc_rows = rows;
          if (objs != null) copy (objs, nobjs);
          objs = nobjs;
       }
       else
       {
         if (cols < this.cols)
         {
           for (int i=cols; i<this.cols; i++)
             for (int j=0; j<this.rows; j++)
               objs[j][i] = null;
         }

         if (rows < this.rows)
         {
           for (int i=0; i<cols; i++)
             for (int j=rows; j<this.rows; j++)
               objs[j][i] = null;
         }
       }

       int pc = this.cols;
       int pr = this.rows;
       this.cols = cols;
       this.rows = rows;

       if (pc != cols || pr != rows) perform (new MatrixEvent(this, pr, pc));
     }
   }

  /**
   * Gets the matrix dimension. The result is represented with java.awt.Dimension
   * class where <code>width</code> field correspond to number of rows and <code>height</code>
   * field correspond to number of columns.
   * @return a matrix dimension.
   */
   public Dimension getSize () {
     return new Dimension (rows, cols);
   }

  /**
   * Updates the specified cell with the specified value. If the specified row or column
   * is out of bounds the matrix dimension than the matrix size will be extended
   * automatically.
   * @param <code>row</code> the specified row.
   * @param <code>col</code> the specified column.
   * @param <code>obj</code> the specified value to update the cell value.
   */
   public void put(int row, int col, Object obj)
   {
      int nr = getRows();
      int nc = getCols();
      if (row >= nr) nr += (row - nr + 1);
      if (col >= nc) nc += (col - nc + 1);
      setSize(nr, nc);

      Object old = objs[row][col];
      if ((obj == null && obj != objs[row][col]      )||
          (obj != null && !obj.equals(objs[row][col]))  )
      {
        objs[row][col] = obj;
        perform(new MatrixEvent(this, row, col, old));
      }
   }

  /**
   * Updates a cell with the specified value at the specified index. Any cell of the
   * matrix object can be identified by row and column or by index. The index for the
   * specified cell (row, col) is calculated using following formula:
   * <code>((row*columns) + col)</code>. If the index is out of bounds the matrix dimension
   * than the matrix size will be extended automatically.
   * @param <code>index</code> the specified index.
   * @param <code>obj</code> the specified value to update the cell value.
   */
   public Point put(int index, Object obj)
   {
      Point p = MathBox.index2point(index, getCols());
      put (p.x, p.y, obj);
      return p;
   }

  /**
   * Gets the value of the specified cell.
   * @param <code>row</code> the specified row.
   * @param <code>col</code> the specified column.
   * @return a value.
   */
   public Object get(int row, int col) {
     return objs[row][col];
   }

  /**
   * Gets the value at the specified cell index.
   * @param <code>index</code> the specified cell index.
   * @return a value.
   */
   public Object get(int index) {
     Point p = MathBox.index2point(index, getCols());
     return objs[p.x][p.y];
   }

  /**
   * Removes the specified number of rows starting from the given row.
   * @param <code>begrow</code> the first removed row.
   * @param <code>count</code> the number of rows to be removed starting from the first row.
   */
   public void removeRows(int begrow, int count)
   {
     int from = begrow;
     for (int i=(begrow + count - 1); i<rows; i++, begrow++)
     {
       for (int j=0; j<cols; j++)
       {
         objs[begrow][j] = objs[i][j];
         objs[i][j] = null;
       }
     }
     rows -= count;
     perform(new MatrixEvent(this, rows + count, cols));
   }

  /**
   * Removes the specified number of columns starting from the given column.
   * @param <code>begcol</code> the first removed column.
   * @param <code>count</code> the number of columns to be removed starting from the first column.
   */
   public void removeCols(int begcol, int count)
   {
     int from = begcol;
     for (int i=(begcol+count); i<cols; i++, begcol++)
     {
       for (int j=0; j<rows; j++)
       {
         objs[j][begcol] = objs[j][i];
         objs[j][i]      = null;
       }
     }
     cols -= count;
     perform(new MatrixEvent(this, rows, cols + count));
   }

  /**
   * Adds the matrix listener to be notified whenever the matrix cell has been updated or
   * the matrix dimension has been changed.
   * @param <code>m</code> the matrix listener.
   */
   public void addMatrixListener (MatrixListener m) {
     if (support == null) support = new Vector(1);
     if (!support.contains(m)) support.addElement(m);
   }

  /**
   * Removes the matrix listener.
   * @param <code>m</code> the matrix listener.
   */
   public void removeMatrixListener (MatrixListener m) {
     if (support != null) support.removeElement(m);
   }

  /**
   * Fires the specified event to registered matrix listeners.
   * @param <code>e</code> the specified event.
   */
   protected void perform(MatrixEvent e)
   {
     if (support != null)
     {
       for (int i=0; i<support.size(); i++)
       {
         MatrixListener l = (MatrixListener)support.elementAt(i);
         if (e.getID() == MatrixEvent.MATRIX_RESIZED) l.matrixResized(e);
         else l.cellModified(e);
       }
     }
   }

   private static void copy (Object[][] s, Object[][] d)
   {
      Dimension sd = arraySize(s);
      Dimension dd = arraySize(d);

      int rows = Math.min(sd.height, dd.height);
      int cols = Math.min(sd.width,  dd.width );

      for (int i=0; i<rows; i++)
        for (int j=0; j<cols; j++)
          d[i][j] = s[i][j];
   }

   private static Dimension arraySize(Object[][] s)
   {
     if (s == null) return new Dimension();
     Dimension d = new Dimension();
     d.height= s.length;
     d.width = (s.length==0)?0:((Object[])s[0]).length;
     return d;
   }
}
