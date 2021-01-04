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
import org.zaval.data.*;

public class LwTreeSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();
    root.setLwLayout(new LwBorderLayout());

    Item r1 = new Item("root");
    Tree d1 = new Tree(r1);
    for (int i=0; i<5; i++)
    {
      Item sub = new Item ("Item " + i);
      d1.add (r1, sub);
      for (int j=0; j<5; j++)
        d1.add (sub, new Item("Item " + i + "." + j));
    }

    LwTree tree1 = new LwTree(d1);
    tree1.setSize(200, 200);
    tree1.setLocation(20, 20);
    tree1.getViewMan(true).setBorder("br.etched");
    tree1.addActionListener(new LwTreeSampleAL());
    tree1.addSelectionListener(new LwTreeSampleSL());
    root.add (LwBorderLayout.CENTER, new LwScrollPan(tree1));
    return root;
  }

  public static void main (String[] args) {
    runSampleApp (300, 300, new LwTreeSample());
  }

  class LwTreeSampleAL
  implements LwActionListener
  {
     public void actionPerformed (LwActionEvent e)
     {
       Item i = (Item)e.getData();
       System.out.println("The '" + i.getValue() + "' has been collapsed or expanded");
     }
  }

  class LwTreeSampleSL
  implements LwActionListener
  {
     public void actionPerformed(LwActionEvent e)
     {
       Item i = (Item)e.getData();
       System.out.println("The '" + (i!=null?i.getValue():"null") + "' has been selected");
     }
  }
}




