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
import org.zaval.lw.tree.*;
import org.zaval.data.*;

public class LwComplexEditableGridSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root  = new LwPanel();
    root.setLwLayout(new LwFlowLayout());
    ((LwPanel)root).setInsets(6,6,6,6);

    Matrix data = new Matrix(1, 3);
    for (int i=0;i<3;i++)
      for (int j=0;j<2;j++)
         data.put(j, i, "Cell[" + i + "][" + j + "]");

    data.put(2, 0, makeSubgrid());
    data.put(2, 1, makeTree());
    data.put(2, 2, makeNotebook());

    LwGrid grid = new LwGrid(data);
    LwGridCaption cap = new LwGridCaption(grid);
    cap.putTitle(0, "Grid Inside");
    cap.putTitle(1, "Tree Inside");
    cap.putTitle(2, "Notebook Inside");
    grid.add (LwGrid.TOP_CAPTION_EL, cap);
    grid.setEditorProvider (new LwCustomEditorProvider());
    grid.setViewProvider   (new LwCustomViewProvider());
    grid.setPosController(null);
    grid.usePsMetric(true);
    root.add (grid);

    return root;
  }

  private static LwGrid makeSubgrid()
  {
    Matrix data = new Matrix(10, 2);
    for (int i=0; i<data.getRows(); i++)
      for (int j=0; j<data.getCols(); j++)
        data.put (i, j, "Cell [" + i + "," + j + "]");

    LwGrid grid = new LwGrid(data);
    grid.getPosController().setOffset(0);

    LwGridCaption cap = new LwGridCaption(grid);
    cap.putTitle(0, "Title 1");
    cap.putTitle(1, "Title 2");
    grid.enableColResize(false);
    grid.add (LwGrid.TOP_CAPTION_EL, cap);

    return grid;
  }

  private static LwTree makeTree()
  {
    Item root = new Item("root");
    Tree data = new Tree (root);
    for (int i=0;i < 3; i++)
    {
      Item item = new Item ("Item " + i);
      data.add (root, item);
      for (int j=0; j < 3; j++)
      {
        data.add (item, new Item ("Item " + i + "." + j));
      }
    }

    LwTree tree = new LwTree (data);
    tree.select(root);
    return tree;
  }

  private static LwNotebook makeNotebook()
  {
    LwNotebook book = new LwNotebook();
    book.addPage("Page 1", new LwCanvas());
    book.addPage("Page 2", new LwCanvas());
    book.addPage("Page 3", new LwCanvas());
    Dimension ps = book.getPreferredSize();
    book.setPSSize(ps.width, 200);
    return book;
  }

  class LwCustomViewProvider
  extends LwDefViews
  {
      public /*C#override*/ LwView getView(int row, int col, Object o) {
        return row==2?new LwCompRender((LwComponent)o):super.getView(row, col, o);
      }
  }

  class LwCustomEditorProvider
  extends LwDefEditors
  {
    public /*C#override*/ LwComponent getEditor(int r, int c, Object o)
    {
      if (r == 2) return (LwComponent)o;
      else
      {
        LwComponent ce = super.getEditor(r, c, o);
        ce.getViewMan(true).setBorder((LwView)null);
        return ce;
      }
    }

    public /*C#override*/ Object fetchEditedValue(int row,
                                                  int col,
                                                  LwComponent c)
    {
      return (row == 2)?c:super.fetchEditedValue(row, col, c);
    }

    public /*C#override*/ boolean shouldStartEdit (int row,
                                                   int col,
                                                   LwAWTEvent e)
    {
       return true;
    }
  }

  public static void main (String[] args) {
    runSampleApp (500, 350, new LwComplexEditableGridSample());
  }
}


