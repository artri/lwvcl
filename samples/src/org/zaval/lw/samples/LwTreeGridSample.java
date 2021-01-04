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
import org.zaval.lw.tree.*;
import org.zaval.lw.grid.*;
import org.zaval.data.*;

public class LwTreeGridSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel ();
    root.setLwLayout (new LwBorderLayout ());

    Item r1    = new Item("root");
    Tree model = new Tree(r1);
    for (int i=0; i<3; i++)
    {
      Item sub = new Item ("Item " + i);
      model.add (r1, sub);
      for (int j=0; j<3; j++)
        model.add (sub, new Item("Item " + i + "." + j));
    }

    LwTreeGrid treeGrid = new LwTreeGrid(model);

    MatrixModel matrixModel = treeGrid.getModel ();
    for (int i=0; i<model.getItemsCount(); i++)
      for (int j=1; j<4; j++)
        matrixModel.put (i, j, "Cell [" + i + "][" + j + "]");

    LwGridCaption caption = new LwGridCaption(treeGrid);
    caption.putTitle (0, "Title 1");
    caption.putTitle (1, "Title 2");
    caption.putTitle (2, "Title 3");
    caption.putTitle (3, "Title 4");
    treeGrid.add (LwGrid.TOP_CAPTION_EL, caption);

    treeGrid.setColWidth(0, 150);
    treeGrid.setEditorProvider(new LwDefEditors());

    root.add (LwBorderLayout.CENTER, treeGrid);

    return root;
  }

  public static void main (String[] args) {
    runSampleApp(400, 400, new LwTreeGridSample());
  }
}




