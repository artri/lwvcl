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
package org.zaval.lw;

import java.awt.*;
import org.zaval.data.*;
import org.zaval.misc.*;

/**
 * This render is used to paint tab page of LwNotebook component. It inherits the
 * LwTextRender, so the page tab is a text that is bound with a special border.
 */
public class LwTabRender
extends LwTextRender
{
  /**
   * Constructs the render with the specified target text.
   * @param <code>s</code> the specified tab text.
   */
   public LwTabRender(String s) {
     super(s);
   }

  /**
   * Paints the render using a given width and height. The location where the
   * view has to be painted is determined with <code>x</code> and <code>y</code>
   * coordinates. This abstract method has to be implmented to define a "face" for the view.
   * @param <code>g</code> the specified context to be used for painting.
   * @param <code>x</code> the x coordinate.
   * @param <code>y</code> the y coordinate.
   * @param <code>w</code> the width of the view.
   * @param <code>h</code> the height of the view.
   * @param <code>d</code> the owner component.
   */
   public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     paintPage(g, x, y, w, h, d);
     Point p = Alignment.getLocation (super.calcPreferredSize(), Alignment.CENTER, Alignment.CENTER, w, h);
     super.paint(g, x + p.x, y + p.y, w, h, d);
   }

  /**
   * Calculates and returns the render preferred size. The method doesn't use
   * the view insets to compute the preferred size.
   * @return a "pure" preferred size of the view.
   */
   protected /*C#override*/ Dimension calcPreferredSize()
   {
     Dimension d = super.calcPreferredSize();
     d.width  += 10;
     d.height += 6;
     return d;
   }


  /**
   * Paints the border for the view. The border type depends on a page tab
   * orientation that can have following values: Alignment.TOP, Alignment.BOTTOM,
   * Alignment.LEFT, Alignment.RIGHT.
   * @param <code>g</code> the specified context to be used for painting.
   * @param <code>x</code> the x coordinate.
   * @param <code>y</code> the y coordinate.
   * @param <code>w</code> the width of the view.
   * @param <code>h</code> the height of the view.
   * @param <code>d</code> the owner component.
   */
   protected /*C#virtual*/ void paintPage(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     //???
     LwTitleInfo ti = (LwTitleInfo)((LwComponent)d).getLwParent();
     switch (ti.getTitleAlignment())
     {
       case Alignment.TOP    : paintTop   (g, x, y, w, h, d); break;
       case Alignment.BOTTOM : paintBottom(g, x, y, w, h, d); break;
       case Alignment.LEFT   : paintLeft  (g, x, y, w, h, d); break;
       case Alignment.RIGHT  : paintRight (g, x, y, w, h, d); break;
     }
   }

   private void paintTop(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     int xx = x + w - 1;
     int yy = y + h - 1;

     g.setColor (Color.white);
     g.drawLine (x + 3, y, xx-1, y);
     g.drawLine (x, y + 3, x, yy);
     g.drawLine (x, y + 3,x + 3, y);

     g.setColor (Color.gray);
     g.drawLine (xx - 1, y + 1, xx - 1, yy);
     g.setColor (Color.black);
     g.drawLine (xx, y + 2, xx, yy);
   }

   private void paintBottom(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     int xx = x + w - 1;
     int yy = y + h - 1;

     g.setColor (Color.white);
     g.drawLine (x, y, x, yy-3);

     g.setColor (Color.gray);
     g.drawLine (xx - 1, y, xx - 1, yy - 1);
     g.drawLine (x + 3, yy-1, xx-1, yy-1);
     g.drawLine (x, yy-4, x + 4, yy);

     g.setColor (Color.black);
     g.drawLine (xx, y, xx, yy);
     g.drawLine (x + 3, yy, xx-1, yy);
     g.drawLine (x, yy-3, x + 3, yy);
   }

   private void paintLeft(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     int xx = x + w - 1;
     int yy = y + h - 1;

     g.setColor (Color.white);
     g.drawLine (x + 3, y, xx, y);
     g.drawLine (x, y + 3, x, yy-3);
     g.drawLine (x, y + 3,x + 3, y);

     g.setColor (Color.gray);
     g.drawLine (x + 3, yy - 1, xx, yy - 1);
     g.drawLine (x, yy-4, x + 4, yy);

     g.setColor (Color.black);
     g.drawLine (x + 3, yy, xx, yy);
     g.drawLine (x, yy-3, x + 3, yy);
   }

   private void paintRight(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     int xx = x + w - 1;
     int yy = y + h - 1;

     g.setColor (Color.white);
     g.drawLine (x, y, xx - 3, y);

     g.setColor (Color.gray);
     g.drawLine (xx - 4, y, xx, y + 4);
     g.drawLine (xx - 1, y + 4, xx - 1, yy - 4);
     g.drawLine (xx, yy - 4, xx - 4, yy);
     g.drawLine (x, yy-1, xx - 4, yy-1);

     g.setColor (Color.black);
     g.drawLine (xx - 3, y, xx, y + 3);
     g.drawLine (xx, y + 3, xx, yy - 3);
     g.drawLine (xx, yy - 3, xx - 3, yy);
     g.drawLine (x, yy, xx - 3, yy);
   }
}


