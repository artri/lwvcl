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

public class LwLayoutUsageSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root = new LwPanel();
     root.setLwLayout(new LwBorderLayout());
     root.add (LwBorderLayout.CENTER, new LwLoginPanel());
     return root;
   }

   public static void main (String[] args) {
     runSampleApp (200, 200, new LwLayoutUsageSample());
   }

   class LwLoginPanel
   extends LwPanel
   {
     public LwLoginPanel()
     {
       getViewMan(true).setBorder("br.etched");

       LwPanel p1 = new LwPanel();
       p1.setLwLayout(new LwGridLayout(2, 2));
       p1.add (new LwLabel("User name:"));
       p1.add (new LwTextField("", 8));
       p1.add (new LwLabel("Password:"));
       p1.add (new LwTextField("", 8));

       LwPanel p2 = new LwPanel();
       p2.setLwLayout(new LwBorderLayout(2, 2));

       LwPanel p3 = new LwPanel();
       p3.setInsets(4,4,4,4);
       p3.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER));
       p3.add(new LwButton("Login"));

       p2.add (LwBorderLayout.CENTER, p1);
       p2.add (LwBorderLayout.SOUTH, p3);

       setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER));
       add (p2);
     }
  }
}
