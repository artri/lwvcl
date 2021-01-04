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
package org.zaval.lw.grid;

import java.awt.*;

/**
 * This interface is used to get values for basic metrical characteristics of the
 * grid component. For example, it is possible to get columns widths or rows heights
 * and so on. The interface is supposed to be used with components that implement
 * decorative elements for the grid component (for example grid caption).
 */
public interface LwGridMetrics
{
 /**
  * Gets the number of the grid rows.
  * @return a number of the grid rows.
  */
  int getGridRows();

 /**
  * Gets the number of the grid columns.
  * @return a number of the grid columns.
  */
  int getGridCols();

 /**
  * Gets the specified row height.
  * @param <code>row</code> the specified grid row.
  * @return a height of the specified row.
  */
  int getRowHeight(int row);

 /**
  * Gets the specified column width.
  * @param <code>col</code> the specified grid column.
  * @return a width of the specified column.
  */
  int getColWidth (int col);

 /**
  * Sets the specified height for the given row.
  * @param <code>row</code> the specified row.
  * @param <code>h</code> the specified height.
  */
  void setRowHeight(int row, int h);

 /**
  * Sets the specified width for the given column.
  * @param <code>col</code> the specified column.
  * @param <code>w</code> the specified width.
  */
  void setColWidth (int col, int w);

 /**
  * Returns an origin of the grid component. The origin defines an offset of the component view
  * relatively the component point of origin.
  * @return an origin of the component.
  */
  Point getOrigin ();

 /**
  * Gets the cells insets. The insets defines top, left, right and bottom indents
  * inside grid cells.
  * @return a cells insets.
  */
  Insets getCellInsets ();

 /**
  * Gets the gap. The gap defines size of area that is used to paint horizontal and
  * vertical grid lines.
  * @return a gap.
  */
  int getNetGap();

 /**
  * Gets the x coordinate of the specified grid column.
  * @param <code>col</code> the specified column.
  * @return a <code>x</code> coordinate of the specified grid column.
  */
  int getColX(int col);

 /**
  * Gets the y coordinate of the specified grid row.
  * @param <code>row</code> the specified row.
  * @return an <code>y</code> coordinate of the specified grid row.
  */
  int getRowY(int row);

 /**
  * Gets the grid visibility.
  * @return a grid visibility.
  */
  CellsVisibility getCellsVisibility();
}


