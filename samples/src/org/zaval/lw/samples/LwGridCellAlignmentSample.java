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
package org.zaval.lw.samples;

import java.awt.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;
import org.zaval.lw.grid.*;
import org.zaval.misc.*;
import org.zaval.data.*;

public class LwGridCellAlignmentSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel ();

    Matrix data = new Matrix(3, 3);
    for (int i=0;i<data.getRows(); i++)
      for (int j=0;j<data.getCols(); j++)
        data.put (i, j, "Cell [" + i + "," + j + "]\ndata");

    LwGrid        grid    = new LwGrid(data);
    LwGridCaption caption = new LwGridCaption(grid);
    for (int i=0; i<data.getCols(); i++)
      caption.putTitle (i, "Title " + i);
    grid.add (LwGrid.TOP_CAPTION_EL, caption);

    grid.setViewProvider(new LwColumnsAlignmentProvider());

    Dimension ps = grid.getPreferredSize();
    grid.setSize(260, 280);
    grid.setLocation (20, 20);

    for (int i=0; i<data.getRows(); i++)
      grid.setRowHeight(i, 80);

    for (int i=0; i<data.getCols(); i++)
      grid.setColWidth(i, 85);


    root.add (grid);
    return root;
  }

  class LwColumnsAlignmentProvider
  extends LwDefViews
  {
    public /*C#override*/ LwView getView (int row, int col, Object data) {
      return new LwTextRender((String)data);
    }


    public /*C#override*/ int getXAlignment (int row, int col)
    {
      if (col == 0) return Alignment.LEFT;
      else
      if (col == 1) return Alignment.CENTER;
      else
      if (col == 2) return Alignment.RIGHT;
      return super.getXAlignment(row, col);
    }

    public /*C#override*/ int getYAlignment (int row, int col)
    {
      if (row == 0) return Alignment.TOP;
      else
      if (row == 1) return Alignment.CENTER;
      else
      if (row == 2) return Alignment.BOTTOM;
      return super.getYAlignment(row, col);
    }
  }


  public static void main (String[] args) {
    runSampleApp(300, 400, new LwGridCellAlignmentSample());
  }
}


