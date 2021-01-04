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
 * The main purpose of the sample to show how the label and image components combinations
 * can be used to change the button focus component.
 */
public class LwImgButtonSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root  = new LwPanel();
     LwButton button1 = new LwButton(new LwImage("samples/img/caution.gif"));
     button1.setSize(70, 30);
     button1.setLocation(30, 30);

     LwPanel imgLabel1 = new LwPanel();
     imgLabel1.setLwLayout(new LwFlowLayout());
     imgLabel1.add (new LwImage("samples/img/caution.gif"));
     imgLabel1.add (new LwLabel("Title"));
     LwButton button2  = new LwButton(imgLabel1);
     button2.setSize(70, 30);
     button2.setLocation(30, 80);

     LwPanel imgLabel2 = new LwPanel();
     imgLabel2.setLwLayout(new LwFlowLayout());
     imgLabel2.add (new LwLabel("Title"));
     imgLabel2.add (new LwImage("samples/img/caution.gif"));
     LwButton button3  = new LwButton(imgLabel2);
     button3.setSize(70, 30);
     button3.setLocation(130, 80);

     LwPanel imgLabel3 = new LwPanel();
     imgLabel3.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
     imgLabel3.add (new LwImage("samples/img/caution.gif"));
     imgLabel3.add (new LwLabel("Title"));
     LwButton button4  = new LwButton(imgLabel3);
     button4.setSize(70, 60);
     button4.setLocation(30, 130);

     LwPanel imgLabel4 = new LwPanel();
     imgLabel4.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
     imgLabel4.add (new LwLabel("Title"));
     imgLabel4.add (new LwImage("samples/img/caution.gif"));
     LwButton button5  = new LwButton(imgLabel4);
     button5.setSize(70, 60);
     button5.setLocation(130, 130);

     root.add(button1);
     root.add(button2);
     root.add(button3);
     root.add(button4);
     root.add(button5);

     return root;
   }

   public static void main (String[] args) {
     runSampleApp (300, 300, new LwImgButtonSample());
   }
}
