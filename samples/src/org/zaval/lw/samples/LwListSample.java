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
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;

public class LwListSample
extends LwSample
implements LwActionListener, PosListener
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root  = new LwPanel();

     LwList list1 = new LwList();
     list1.getViewMan (true).setBorder("br.etched");
     for (int i=0;i<5; i++) list1.add ("Item " + i);
     Dimension ps = list1.getPreferredSize();
     list1.setSize(2*ps.width, ps.height);
     list1.setLocation(20, 20);
     list1.getPosController().addPosListener(new LwListSample());
     list1.addSelectionListener(new LwListSample());
     root.add (list1);

     LwList list2 = new LwComboList();
     list2.getViewMan (true).setBorder("br.plain");
     for (int i=0;i<5; i++) list2.add ("Item " + i);
     ps = list2.getPreferredSize();
     list2.setSize(2*ps.width, ps.height);
     list2.setLocation(30 + list1.getX() + list1.getWidth() , 20);
     list2.getPosController().addPosListener(new LwListSample());
     list2.addSelectionListener(new LwListSample());
     root.add (list2);

     LwList list3 = new LwList();
     list3.getViewMan (true).setBorder("br.sunken");
     list3.add (new LwCheckbox("Checkbox item"));
     list3.add (new LwButton("Button item"));
     list3.add (new LwTextField("Text field item"));
     LwCombo combo = new LwCombo();
     for (int i=0;i<5; i++) combo.getList().add ("Item " + i);
     list3.add (combo);
     ps = list3.getPreferredSize();
     list3.setSize(2*ps.width, ps.height);
     list3.setLocation(20, 20 + list1.getY() + list1.getHeight());
     list3.getPosController().addPosListener(new LwListSample());
     list3.addSelectionListener(new LwListSample());
     root.add (list3);


     LwList list4 = new LwList();
     list4.getViewMan (true).setBorder("br.sunken");
     list4.add ("Item 1");
     list4.add ("Item 2");
     list4.add ("Item 3");
     LwList subList4 = new LwList();
     subList4.getViewMan (true).setBorder("br.sunken");
     subList4.add ("Item 4.1");
     subList4.add ("Item 4.2");
     subList4.add ("Item 4.3");
     list4.add (subList4);
     ps = list4.getPreferredSize();
     list4.setSize(2*ps.width, ps.height);
     list4.setLocation(20 + list3.getX() + list3.getWidth(), 20);
     root.add (list4);

     return root;
   }

   public static void main (String[] args)  {
     runSampleApp(400, 300, new LwListSample());
   }

   public void actionPerformed(LwActionEvent e) {
     Integer i = (Integer)e.getData();
     System.out.println ("The " + i + " item has been selected.");
   }

   public void posChanged (PosEvent e) {
     System.out.println ("The pos has been changed " + ((PosController)e.getSource()).getOffset());
   }
}

