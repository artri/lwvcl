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
import org.zaval.lw.grid.*;
import org.zaval.data.*;

public class LwEditableGridSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();

    Matrix data1 = new Matrix(3, 3);
    for (int i=0;i<data1.getRows(); i++)
      for (int j=0;j<data1.getCols(); j++)
      {
        data1.put (i, j, "Cell [" + i + "][" + j + " ]");
      }

    LwGrid grid1 = new LwGrid(data1);
    Dimension ps = grid1.getPreferredSize();
    grid1.setSize(ps.width, ps.height);
    grid1.setLocation (20, 20);
    grid1.setEditorProvider(new LwDefEditors());
    root.add (grid1);

    Matrix data2 = new Matrix(3, 3);
    for (int i=0;i<data2.getRows(); i++)
      for (int j=0;j<data2.getCols(); j++)
      {
        if (j == 0) data2.put(i, j, "On");
        else
        if (j == 1) data2.put(i, j, "Item 1");
        else
         data2.put(i, j, "Text Item " + j);
      }

    LwGrid grid2 = new LwGrid(data2);
    LwGridCaption cap = new LwGridCaption(grid2);
    cap.putTitle(0, "Checkbox");
    cap.putTitle(1, "Combobox");
    cap.putTitle(2, "Textfield");
    grid2.add (LwGrid.TOP_CAPTION_EL, cap);

    ps = grid2.getPreferredSize();
    grid2.setSize(ps.width, ps.height);
    grid2.setLocation (20, grid1.getY() + grid1.getHeight() + 20);
    grid2.setEditorProvider(new LwCustomGridEditor());
    root.add (grid2);

    return root;
  }

  class LwCustomGridEditor
  extends LwDefEditors
  {
     public /*C#override*/ LwComponent getEditor(int row, int col, Object o)
     {
       if (col == 0)
       {
         LwCheckbox cbox = new LwCheckbox(null);
         if (o.equals("On")) cbox.setState(true);
         else                cbox.setState(false);
         return cbox;
       }
       else
       if (col == 1)
       {
         LwCombo combo = new LwCombo();
         LwList  list  = combo.getList();
         list.add("Item 1");
         list.add("Item 2");
         list.add("Item 3");
         for (int i=0; i<list.count(); i++)
           if (((LwLabel)list.get(i)).getText().equals(o))
           {
             list.select(i);
             break;
           }
         return combo;
       }
       else
       return super.getEditor(row, col, o);
     }

     public /*C#override*/ Object fetchEditedValue (int row, int col, LwComponent editor)
     {
       if (col == 0) return ((LwCheckbox)editor).getState()?"On":"Off";
       else
       if (col == 1)
       {
         LwList list = ((LwCombo)editor).getList();
         return ((LwLabel)list.getSelected()).getText();
       }
       else
       return super.fetchEditedValue(row, col, editor);
     }
  }

  public static void main (String[] args) {
    runSampleApp (300, 300, new LwEditableGridSample());
  }
}



