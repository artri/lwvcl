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
import java.io.*;
import org.zaval.lw.*;
import org.zaval.lw.grid.*;
import org.zaval.data.*;
import org.zaval.misc.*;

public class LwScrolledComponentSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
     LwPanel root = new LwPanel();

     LwList scrollObj1 = new LwList();
     for (int i=0; i<40; i++) scrollObj1.add ("Item " + i);
     LwScrollPan span1 = new LwScrollPan(scrollObj1);
     span1.getViewMan(true).setBorder("br.plain");
     span1.setLocation (20, 20);
     span1.setSize(150, 150);
     root.add (span1);

     LwTextField scrollObj2 = new LwTextField(readAsResource("lw.properties"));
     LwScrollPan span2 = new LwScrollPan(scrollObj2);
     span2.getViewMan(true).setBorder("br.plain");
     span2.setLocation (span1.getX() + span1.getWidth() + 20, 20);
     span2.setSize(150, 150);
     root.add (span2);

     Matrix m = new Matrix (20, 20);
     for (int i=0; i<20; i++)
       for (int j=0; j<20; j++)
         m.put(i, j, "Item [" + i + "," + j + "]");
     LwGrid scrollObj3 = new LwGrid(m);
     LwScrollPan span3 = new LwScrollPan(scrollObj3);
     span3.getViewMan(true).setBorder("br.plain");
     span3.setLocation (20, span2.getY() + span2.getHeight() + 20);
     span3.setSize(320, 120);
     root.add (span3);

     return root;
  }

  public static void main(String[] args) {
    runSampleApp (500, 500, new LwScrolledComponentSample());
  }
}
