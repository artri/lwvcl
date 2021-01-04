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
import org.zaval.misc.*;

public class LwScrollingSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
     LwPanel root = new LwPanel();

     LwPanel scrollObj1 = new LwPanel();
     scrollObj1.setLwLayout (new LwGridLayout(20, 10));
     for (int i=0; i<20; i++)
     {
       for (int j=0; j<10; j++)
       {
         LwLabel lab = new LwLabel ("Item [" + i + "," + j + "]");
         scrollObj1.add(lab);
       }
     }
     LwScrollPan span1 = new LwScrollPan(scrollObj1);
     span1.getViewMan(true).setBorder("br.plain");
     span1.setLocation (20, 20);
     span1.setSize(120, 150);
     root.add (span1);

     LwPanel scrollObj2 = new LwPanel();
     scrollObj2.setLwLayout (new LwGridLayout(20, 10));
     for (int i=0; i<20; i++)
     {
       for (int j=0; j<10; j++)
       {
         LwLabel lab = new LwLabel ("Item [" + i + "," + j + "]");
         scrollObj2.add(lab);
       }
     }
     LwScrollPan span2 = new LwScrollPan(scrollObj2, LwToolkit.HORIZONTAL);
     span2.getViewMan(true).setBorder("br.plain");
     span2.setLocation (span1.getX() + span1.getWidth() + 20, 20);
     span2.setSize(120, 150);
     root.add (span2);

     LwPanel scrollObj3 = new LwPanel();
     scrollObj3.setLwLayout (new LwGridLayout(20, 10));
     for (int i=0; i<20; i++)
     {
       for (int j=0; j<10; j++)
       {
         LwLabel lab = new LwLabel ("Item [" + i + "," + j + "]");
         scrollObj3.add(lab);
       }
     }
     LwScrollPan span3 = new LwScrollPan(scrollObj3, LwToolkit.VERTICAL);
     span3.getViewMan(true).setBorder("br.plain");
     span3.setLocation (span2.getX() + span2.getWidth() + 20, 20);
     span3.setSize(120, 150);
     root.add (span3);

     return root;
  }

  public static void main(String[] args) {
    runSampleApp(600, 300, new LwScrollingSample());
  }
}
