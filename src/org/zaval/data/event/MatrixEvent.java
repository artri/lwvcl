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

import java.awt.*;

/**
 * This class describes event that is performed by <code>MatrixModel</code> interface implementation.
 * Using the event class the matrix model implementation notifies listeners about the data changes.
 */
public class MatrixEvent
extends org.zaval.util.event.EvObject
{
  /**
   * The matrix cell modified event type.
   */
   public static final int CELL_MODIFIED  = 1;

  /**
   * The matrix dimension modified event type.
   */
   public static final int MATRIX_RESIZED = 2;

   private int rowx, colx;
   private Object prevValue;

  /**
   * Constructs a new matrix event class with the given source, previous number of rows and columns.
   * The contructor sets event id to MATRIX_RESIZED value. The type of event (created by the
   * contructor) is used to notify that the matrix number of columns or number of rows has been
   * changed.
   * @param <code>target</code> the source of the event.
   * @param <code>prevRows</code> the previous number of rows.
   * @param <code>prevCols</code> the previous number of columns.
   */
   public MatrixEvent(Object target, int prevRows, int prevCols)
   {
     super(target, MATRIX_RESIZED);
     rowx = prevRows;
     colx = prevCols;
   }

  /**
   * Constructs a new matrix event class with the given source, row, column and previous value.
   * The contructor sets event id to CELL_MODIFIED value. The type of event (created by the
   * contructor) is used to notify that the value of the matrix cell has been changed.
   * @param <code>target</code> the source of the event.
   * @param <code>row</code> the row of the modified cell.
   * @param <code>col</code> the column of the modified cell.
   * @param <code>prevValue</code> the previous value of the modified cell.
   */
   public MatrixEvent(Object target, int row, int col, Object prevValue)
   {
     super(target, CELL_MODIFIED);
     rowx = row;
     colx = col;
     this.prevValue = prevValue;
   }

  /**
   * Returns the previous dimension of the matrix model. Use the method if the event id is
   * MATRIX_RESIZED. The returned value is presented with java.awt.Dimension class where
   * <code>width</code> field correspond to previous number of rows and <code>height</code>
   * field correspond to previous number of columns.
   * @return a previous dimension of the matrix model.
   */
   public Dimension getPrevSize() {
     return new Dimension (rowx, colx);
   }

  /**
   * Returns the row and column of the modified cell. Use the method if the event id is
   * CELL_MODIFIED. The returned value is presented with java.awt.Point class where
   * <code>x</code> field correspond to the row and <code>y</code> field correspond to
   * the column.
   * @return a row and column of the modified cell.
   */
   public Point getModifiedCell() {
     return new Point(rowx, colx);
   }

  /**
   * Returns the previous value of the modified cell. Use the method if the event id is
   * CELL_MODIFIED.
   * @return a previous value of the modified cell.
   */
   public Object getPrevValue() {
     return prevValue;
   }

   protected /*C#override*/ boolean checkID(int id) {
     return id == CELL_MODIFIED || id == MATRIX_RESIZED;
   }
}




