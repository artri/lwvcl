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

import org.zaval.lw.*;
import org.zaval.data.*;
import org.zaval.misc.*;
import java.awt.*;

/**
 * This class is a simple implementation of <code>LwGridViewProvider</code> interface where
 * <code>LwTextRender</code> view is used to render grid cells.
 */
public class LwDefViews
implements LwGridViewProvider
{
   private LwTextRender defView = new LwTextRender(new SingleLineTxt(""));

  /**
   * Gets the horizontal alignment for the specified grid cell.
   * The method should return one of the following values:
   * <ul>
   *   <li>Alignment.CENTER</li>
   *   <li>Alignment.LEFT</li>
   *   <li>Alignment.RIGHT</li>
   * </ul>
   * The implementation returns Alignment.CENTER as the horizontal alignment value.
   * @param <code>row</code> the specified cell row.
   * @param <code>col</code> the specified cell column.
   * @return a horizontal alignment.
   */
   public /*C#virtual*/ int getXAlignment(int row, int col) {
     return Alignment.CENTER;
   }

  /**
   * Gets the vertical alignment for the specified grid cell.
   * The method should return one of the following values:
   * <ul>
   *   <li>Alignment.CENTER</li>
   *   <li>Alignment.TOP</li>
   *   <li>Alignment.BOTTOM</li>
   * </ul>
   * The implementation returns Alignment.CENTER as the vertical alignment value.
   * @param <code>row</code> the specified cell row.
   * @param <code>col</code> the specified cell column.
   * @return a vertical alignment.
   */
   public /*C#virtual*/ int getYAlignment(int row, int col) {
     return Alignment.CENTER;
   }

 /**
  * Gets the the specified grid cell color. The color is used to fill the
  * cell background. If the cell has not own background than the method
  * should return <code>null</code>. The implementation returns <code>null</code>
  * as the cell background color.
  * @param <code>row</code> the specified cell row.
  * @param <code>col</code> the specified cell column.
  * @return a color.
  */
  public /*C#virtual*/ Color getCellColor(int row, int col) {
     return null;
   }

 /**
  * Gets the view of the specified cell and the given data model value.
  * The implementation returns <code>LwTextRender</code> as the view.
  * @param <code>row</code> the specified cell row.
  * @param <code>col</code> the specified cell column.
  * @param <code>obj</code> the specified data model value.
  * @return a view.
  */
   public /*C#virtual*/ LwView getView(int row, int col, Object obj)
   {
     if (obj == null) return null;
     else
     {
       defView.getTextModel().setText((String)obj);
       return defView;
     }
   }

  /**
   * Gets the text render that is used as the grid cells view.
   * @return a text render.
   */
   public /*C#virtual*/ LwTextRender getTextRender () {
     return defView;
   }
}

