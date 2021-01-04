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

public class LwNestedScrollPanelSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
      LwPanel root = new LwPanel();

      LwPanel pan1 = new LwPanel();
      pan1.getViewMan(true).setBorder("br.plain");
      pan1.setLwLayout(new LwFlowLayout(Alignment.LEFT,Alignment.TOP, LwToolkit.VERTICAL));
      for (int i=0; i<6; i++)
      {
        LwSlider slider = new LwSlider();
        slider.setValues (0, 200, new int[] { 0, 5, 10, 25, 30, 35, 40, 30 }, 2, 1);
        pan1.add(slider);
      }
      LwScrollPan span1 = new LwScrollPan(pan1, LwToolkit.HORIZONTAL);

      LwPanel pan2= new LwPanel();
      pan2.getViewMan(true).setBorder("br.plain");
      pan2.setLwLayout(new LwBorderLayout(5,5));

      pan2.add(LwBorderLayout.CENTER, span1);

      LwScrollPan span2 = new LwScrollPan(pan2, LwToolkit.VERTICAL);
      span2.getViewMan(true).setBorder("br.plain");
      span2.setSize(200 , 200);
      span2.setLocation(20, 20);
      root.add (span2);

      return root;
   }

   public static void main (String[] args)  {
     runSampleApp(300, 300, new LwNestedScrollPanelSample());
   }
}


