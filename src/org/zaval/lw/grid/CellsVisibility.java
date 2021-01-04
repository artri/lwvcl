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
 * The class is used to represent grid cells visibility. Using the class it is possible
 * to determine what cells are visible, locations of the visible cells and so on.
 */
public class CellsVisibility
{
  protected Point fr = null;
  protected Point fc = null;
  protected Point lr = null;
  protected Point lc = null;

 /**
  * Gets the first visible cell.
  * @return a first visible cell's row and column. java.awt.Point class is used to represent
  * the first visible cell where <code>x</code> field is the cell row and <code>y</code> field
  * is the cell column.
  */
  public Point getFirstCell() {
    return hasVisibleCells()?new Point(fr.x, fc.x):null;
  }

 /**
  * Gets the last visible cell.
  * @return a last visible cell's row and column. java.awt.Point class is used to represent
  * the last visible cell where <code>x</code> field is the cell row and <code>y</code> field
  * is the cell column.
  */
  public Point getLastCell() {
    return hasVisibleCells()?new Point(lr.x, lc.x):null;
  }

 /**
  * Gets the first visible cell location.
  * @return a first visible cell location. java.awt.Point class is used to represent
  * the location where <code>x</code> field is the x coordinate and <code>y</code> field
  * is the y coordinate.
  */
  public Point getFirstCellLoc() {
    return hasVisibleCells()?new Point(fc.y, fr.y):null;
  }

 /**
  * Gets the last visible cell location.
  * @return a last visible cell location. java.awt.Point class is used to represent
  * the location where <code>x</code> field is the x coordinate and <code>y</code> field
  * is the y coordinate.
  */
  public Point getLastCellLoc() {
    return hasVisibleCells()?new Point(lc.y, lr.y):null;
  }

 /**
  * Checks if there are any visible cells.
  * @return <code>true</code> if there are visible cells; otherwise <code>false</code>.
  */
  public boolean hasVisibleCells () {
    return (fr != null && fc != null&&
            lr != null && lc != null   );
  }
}
