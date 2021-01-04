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

public class LwRenderSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
      LwPanel root = new LwPanel ();
      root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
      LwComponent comp = new LwCanvas();


      int[][] target = new int[50][50]; /*java*/

      /*C#int[][] target = new int[50][];*/
      /*C#for (int i=0;i<50;i++) target[i] = new int[50];*/


      Random random = new Random(System.currentTimeMillis());
      for (int i=0; i<target.length; i++)
        for (int j=0; j<target[i].length; j++)
          target[i][j] = random.nextInt()%8;
      comp.getViewMan(true).setView(new LwCustomRender(target));
      root.add(comp);
      return root;
   }

   class LwCustomRender
   extends LwRender
   {
     private int width = 2, height = 2;

     public LwCustomRender(Object target) {
       super(target);
     }

     protected /*C#override*/ Dimension calcPreferredSize() {
       int[][] target = (int[][])getTarget();
       return new Dimension(width * target.length, height * target[0].length);
     }

     public /*C#override*/ void paint (Graphics g, int x, int y, int w, int h, Drawable d)
     {
       int[][] target =(int[][])getTarget();
       int realWidth  = w/target.length;
       int realHeight = h/target[0].length;

       int xx = x, yy = y;
       for (int i=0; i<target.length; i++)
       {
         for (int j=0; j<target[i].length; j++)
         {
           if (target[i][j]%2 == 0) g.setColor(Color.red);
           else
           if (target[i][j]%3 == 0) g.setColor(Color.yellow);
           else
           if (target[i][j]%5 == 0) g.setColor(Color.green);
           else
           if (target[i][j]%7 == 0) g.setColor(Color.black);
           else                     g.setColor(Color.gray);
           g.fillRect(xx, yy, realWidth, realHeight);
           xx += realWidth;
         }
         yy += realHeight;
         xx = x;
       }
     }
   }

   public static void main(String[] args) {
     runSampleApp (200, 200, new LwRenderSample());
   }
}

