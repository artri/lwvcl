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

public class LwViewSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root = new LwPanel();
     root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
     LwComponent comp = new LwCanvas();
     comp.getViewMan(true).setView(new LwCustomView());
     comp.getViewMan(true).setBorder("br.plain");
     root.add(comp);
     return root;
   }

   class LwCustomView
   extends LwView
   {
     private Color basic = Color.white;
     private int   width = 15, height = 15;
     private int   rgbIncrement = -15;

     public LwCustomView() {
       super(ORIGINAL);
     }

     protected /*C#override*/ Dimension calcPreferredSize() {
       return new Dimension(width*4, height*3);
     }

     public /*C#override*/ void paint (Graphics g, int x, int y, int w, int h, Drawable d)
     {
       int realWidth  = w/4;
       int realHeight = h/3;

       int xx = x, yy = y;
       Color color = basic;
       for (int i=0; i < 3; i++)
       {
         for (int j=0; j < 4; j++)
         {
           g.setColor(color);
           g.fillRect(xx, yy, realWidth, realHeight);
           color = new Color(color.getRed()   + rgbIncrement,
                             color.getGreen() + rgbIncrement,
                             color.getBlue()  + rgbIncrement);
           xx += realWidth;
         }
         yy += realHeight;
         xx = x;
       }
     }
   }

   public static void main(String[] args) {
     runSampleApp(200, 200, new LwViewSample());
   }
}

