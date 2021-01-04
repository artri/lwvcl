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

/**
 * The class implements views for bundle element.
 */
class LwBundleView
extends LwView
{

/**
 * Gets "pure" preferred size.
 * @return a "pure" preferred size.
 */
 protected /*C#override*/ Dimension calcPreferredSize() {
   return new Dimension (16, 16);
 }

/**
 * Paints the view using the given width and height. The location where the
 * view has to be painted is determined with <code>x</code> and <code>y</code>
 * coordinates. This method is implemented with the bundle view and this defines
 * face of the view.
 * @param <code>g</code> the specified context to be used for painting.
 * @param <code>x</code> the x coordinate.
 * @param <code>y</code> the y coordinate.
 * @param <code>w</code> the width of the view.
 * @param <code>h</code> the height of the view.
 * @param <code>d</code> the owner component.
 */
 public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
 {
   g.setColor (d.getBackground());
   g.fillRect (x, y, w, h);

   g.setColor (Color.white);
   g.drawLine (x + 1, y + 1, x + 1, y + h - 2);
   g.drawLine (x + 1, y + 1, x + w - 2, y + 1);

   g.setColor (Color.gray);
   g.drawLine (x, y, x, y + h - 1);
   g.drawLine (x, y, x + w - 1, y);
   g.setColor (Color.lightGray);
   g.drawLine (x + 1, y + h - 2, x + w - 2, y + h - 2);
   g.drawLine (x + w - 2, y + 1, x + w - 2, y + h - 2);

   g.setColor (Color.black);
   g.drawLine (x, y + h - 1, x + w - 1, y + h - 1);
   g.drawLine (x + w - 1, y, x + w - 1, y + h - 1);
 }
}
