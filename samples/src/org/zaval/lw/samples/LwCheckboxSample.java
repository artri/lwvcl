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

import org.zaval.lw.*;
import org.zaval.misc.*;

/**
 * The sample shows the basic LwCheckbox component feature.
 */
public class LwCheckboxSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root  = new LwPanel();
     root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 4, 4));

     LwCheckbox box1 = new LwCheckbox("Checkbox 1");
     LwPanel radio = new LwPanel ();
     radio.setLwLayout(new LwListLayout(4));
     radio.getViewMan(true).setBorder("br.etched");
     LwGroup group = new LwGroup();
     LwCheckbox r1 = new  LwCheckbox("Radio 1", LwCheckbox.RADIO);
     LwCheckbox r2 = new  LwCheckbox("Radio 2", LwCheckbox.RADIO);
     LwCheckbox r3 = new  LwCheckbox("Radio 3", LwCheckbox.RADIO);
     r1.setSwitchManager(group);
     r2.setSwitchManager(group);
     r3.setSwitchManager(group);
     radio.add(r1);
     radio.add(r2);
     radio.add(r3);

     LwCheckbox box2 = new LwCheckbox(null);
     LwLabel box2Title = new LwLabel("Checkbox 2");
     box2.insert(0, null, box2Title);
     box2.setAsFocusComponent(0);

     LwCheckbox box3 = new LwCheckbox(null);

     root.add(box1);
     root.add(radio);
     root.add(box2);
     root.add(box3);

     return root;
   }

   public static void main (String[] args)  {
     runSampleApp (300, 300, new LwCheckboxSample());
   }
}
