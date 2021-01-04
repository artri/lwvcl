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
import java.util.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;
import org.zaval.lw.grid.*;
import org.zaval.data.*;

public class LwPropertiesViewer
extends LwPanel
{
  public LwPropertiesViewer(Hashtable p)
  {
    TreeModel  treeModel = createTreeModel(p);
    LwTreeGrid treeGrid  = createTreeGrid (treeModel);
    createTreeGridModel(treeGrid);
    for (int i=0; i < treeGrid.getCols(); i++)
      treeGrid.setColWidth(i, 180);
    treeGrid.setViewProvider(new LwCellViewProvider());
    add (LwBorderLayout.CENTER, new LwScrollPan(treeGrid));
  }

  protected void createTreeGridModel(LwTreeGrid grid)
  {
    MatrixModel matrixModel = grid.getModel();
    TreeModel   treeModel   = grid.getTreeModel();

    int row = 2;
    Item man = treeModel.getChildAt(treeModel.getRoot(), 0);
    for (int i=0;i<treeModel.getChildrenCount(man); i++)
    {
      Item item = treeModel.getChildAt(man, i);
      Object obj = LwToolkit.getStaticObj((String)item.getValue());
      matrixModel.put (row, 1, obj.getClass().getName()); /*java*/
     /*C#matrixModel.put (row, 1, System.ComponentModel.TypeDescriptor.GetClassName(obj));*/
      row++;
    }

    Item sobj = treeModel.getChildAt(treeModel.getRoot(), 1);
    row++;
    for (int i=0;i<treeModel.getChildrenCount(sobj); i++)
    {
      Item item = treeModel.getChildAt(sobj, i);
      Object obj = LwToolkit.getStaticObj((String)item.getValue());
      matrixModel.put (i + row, 1, obj.getClass().getName()); /*java*/
      /*C#matrixModel.put (i + row, 1, System.ComponentModel.TypeDescriptor.GetClassName(obj));*/
      matrixModel.put (i + row, 2, obj);
    }
  }

  protected LwTreeGrid createTreeGrid(TreeModel model)
  {
    LwTreeGrid treeGrid = new LwTreeGrid(model);
    LwGridCaption caption = new LwGridCaption(treeGrid);
    caption.putTitle (0, "Object Key");
    caption.putTitle (1, "Class Name");
    caption.putTitle (2, "View");
    treeGrid.add (LwGrid.TOP_CAPTION_EL, caption);
    return treeGrid;
  }

  protected TreeModel createTreeModel(Hashtable p)
  {
    Item root = new Item("root");
    TreeModel treeModel = new Tree(root);
    Item man  = new Item("Managers");
    Item sobj = new Item("Static Objects");
    treeModel.add (root, man);
    treeModel.add (root, sobj);

    String mans = (String)p.get("man");
    if (mans != null)
    {
      StringTokenizer st = new StringTokenizer(mans, ",");
      while (st.hasMoreTokens())
        treeModel.add(man, new Item(st.nextToken()));
    }

    String objs = (String)p.get("obj");
    if (objs != null)
    {
      StringTokenizer st = new StringTokenizer(objs, ",");
      while (st.hasMoreTokens())
        treeModel.add(sobj, new Item(p.get("obj." + st.nextToken() + ".key")));
    }

    return treeModel;
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return new LwBorderLayout();
  }

  public static void main (String[] args)
  throws Exception /*java*/
  {
    LwFrame frame = new LwFrame();
    frame.addWindowListener(new WL());
    frame.setSize(500, 500);
    LwContainer root = frame.getRoot();
    root.setLwLayout(new LwBorderLayout());
    LwPropertiesViewer pv = new LwPropertiesViewer(LwToolkit.getProperties(args.length > 0?args[0]:"lw.properties"));
    root.add (LwBorderLayout.CENTER, pv);
    frame.setVisible(true);
  }
}

class LwCellViewProvider
extends LwDefViews
{
  LwCanvas     target = new LwCanvas();
  LwCompRender render = new LwCompRender(target);

  public /*C#override*/ LwView getView(int row, int col, Object obj)
  {
    if (col == 2)
    {
      if (obj instanceof LwView)
      {
        LwView view = (LwView)obj;
        if (view.getType() != LwView.ORIGINAL)
        {
          target.getViewMan(true).setView(view);
          Dimension d = view.getPreferredSize();
          if (d.width < 4 && d.height < 4 && view.getType() == LwView.STRETCH)
          {
            d.width = 8;
            d.height = 8;
          }
          target.setPSSize(d.width, d.height);
          return render;
        }
        else return view;
      }
      else
      if (obj instanceof String)
      {
        super.getView(row, col, obj);
      }
      else
      return super.getView(row, col, "---");
    }
    else
    if (col == 1) return super.getView(row, col, obj);
    return null;
  }
}
