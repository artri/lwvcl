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
 * The main purpose of the sample to show how the button view can be customized
 */
public class LwButtonViewSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root  = new LwPanel();
     LwButton button1 = new LwButton("Label");
     button1.setSize(100, 30);
     button1.setLocation(90, 30);
     LwAdvViewMan man1 = new LwAdvViewMan();
     man1.put("button.on", LwToolkit.getView("br.sunken"));
     man1.put("button.off", LwToolkit.getView("br.raised"));
     button1.setViewMan(man1);

     LwButton button2 = new LwButton((LwComponent)null);
     button2.setInsets(8,8,8,8);
     LwAdvViewMan man2 = new LwAdvViewMan();
     man2.put("button.off", new LwTextRender("Button is Off"));
     LwTextRender tr = new LwTextRender("Button is On");
     tr.setFont(LwToolkit.BFONT);
     man2.put("button.on", tr);
     man2.setBorder("br.dot");
     button2.setViewMan(man2);
     java.awt.Dimension ps = button2.getPreferredSize();
     button2.setSize(ps.width, ps.height);
     button2.setLocation(90, button1.getY() + button1.getHeight() + 20);

     LwButton button3 = new LwButton("Title");
     button3.getFocusComponent().setOpaque(false);
     LwAdvViewMan man3 = new LwAdvViewMan();
     man3.put("button.off", new LwImgRender("samples/img/button1.gif", LwView.ORIGINAL));
     man3.put("button.on", new LwImgRender("samples/img/button2.gif", LwView.ORIGINAL));
     button3.setViewMan(man3);
     ps = button3.getPreferredSize();
     button3.setSize(ps.width, ps.height);
     button3.setLocation(90, button2.getY() + button2.getHeight() + 20);

     root.add(button1);
     root.add(button2);
     root.add(button3);
     return root;
   }

   public static void main (String[] args) {
     runSampleApp (300, 300, new LwButtonViewSample());
   }
}
