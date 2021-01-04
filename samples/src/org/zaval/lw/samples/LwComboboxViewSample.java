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
import org.zaval.misc.*;

/**
 * The sample shows how the LwCombo component view can be customized.
 */
public class LwComboboxViewSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root  = new LwPanel ();

     LwCombo cbox1 = new LwCombo();
     LwList  list1 = cbox1.getList();
     list1.add("Item 0");
     list1.add("Item 1");
     list1.add("Item 2");
     list1.add("Item 3");
     list1.select(0);
     LwAdvViewMan man1 = new LwAdvViewMan();
     man1.put ("st.out", getImgView("cbox11.gif"));
     man1.put ("st.over", getImgView("cbox12.gif"));
     man1.put ("st.pressed", getImgView("cbox13.gif"));
     cbox1.getComboButton().setViewMan(man1);
     root.add(cbox1);
     Dimension ps = cbox1.getPreferredSize();
     cbox1.setSize(120, ps.height);
     cbox1.setLocation(50, 60);

     LwCombo cbox2 = new LwCombo();
     LwList  list2 = cbox2.getList();
     list2.add("Item 0");
     list2.add("Item 1");
     list2.add("Item 2");
     list2.add("Item 3");
     list2.add("Item 4");
     list2.add("Item 5");
     list2.select(0);

     cbox2.setLwLayout (new LwFlowLayout (Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
     LwStButton button = cbox2.getComboButton();
     LwAdvViewMan man2 = new LwAdvViewMan();
     man2.put ("st.out", getImgView("cbox1.gif"));
     man2.put ("st.over", getImgView("cbox2.gif"));
     man2.put ("st.pressed", getImgView("cbox3.gif"));
     button.setViewMan(man2);
     cbox2.remove(button);
     cbox2.add (button);
     cbox2.getViewMan(true).setBorder("br.dot");
     root.add(cbox2);
     ps = cbox2.getPreferredSize();
     cbox2.setSize(120, ps.height);
     cbox2.setLocation(50, cbox1.getY() + cbox1.getHeight() + 20);

     return root;
   }

   public static void main (String[] args) {
     runSampleApp(300, 300, new LwComboboxViewSample());
   }

   private static LwView getImgView (String name)  {
     return new LwImgRender ("samples/img/" + name, LwView.STRETCH);
   }
}
