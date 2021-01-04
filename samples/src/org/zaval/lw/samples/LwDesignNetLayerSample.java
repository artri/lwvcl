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
import org.zaval.lw.event.*;


public class LwDesignNetLayerSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel() {
     return new LwPanel ();
   }

   public /*C#override*/ void started()
   {
      LwContainer root  = getRoot();

      LwDesktop desktop = LwToolkit.getDesktop(root);
      desktop.add (new LwDesignNetLayer());

      LwLabel lab = new LwLabel("Label component");
      Dimension ps = lab.getPreferredSize();
      lab.setSize(ps.width, ps.height);
      lab.setLocation(40, 50);

      LwButton button = new LwButton("Button component");
      ps = button.getPreferredSize();
      button.setSize(ps.width, ps.height);
      button.setLocation(40, lab.getY() + lab.getHeight() + 30);

      LwTextField tf = new LwTextField("Text Field Component");
      ps = tf.getPreferredSize();
      tf.setSize(ps.width, 3*ps.height);
      tf.setLocation(40, button.getY() + button.getHeight() + 30);

      root.add(lab);
      root.add(button);
      root.add(tf);
   }

   public /*C#override*/ void stopped() {
     LwDesktop desk = LwToolkit.getDesktop(getRoot());
     desk.remove (desk.count()-1);
   }

   public static void main (String[] args)  {
     runSampleApp (300, 300, new LwDesignNetLayerSample());
   }
}

class LwDesignNetLayer
extends LwBaseLayer
{
   public LwDesignNetLayer() {
     super("top");
     setOpaque(false);
   }

   public /*C#override*/ boolean isActive() {
     return false;
   }

   public /*C#override*/ void setupFocus  () {}
   public /*C#override*/ void releaseFocus() {}

   public /*C#override*/ void paint(Graphics g)
   {
     g.setColor(Color.gray);
     int xx = 0, yy = 0;
     while (yy <= getHeight())
     {
       xx = 0;
       while (xx <= getWidth())
       {
         g.drawLine(xx, yy, xx, yy);
         xx += 8;
       }
       yy += 8;
     }
   }
}

