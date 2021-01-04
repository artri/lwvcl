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
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This manager is abstract class that is used as base for creating own paint managers
 * for the light weight libarary. To create own paint manager it is necessary to
 * implement <code>updateComponent</code> and <code>paintComponent</code> methods that
 * define update and paint algorithms for a light weight component.
 * <p>
 * Use <code>manager</code> static field of the class to get current implementation of
 * the paint manager. The field is set with LwToolkit.during initialization the library
 * basing on the light weight properties file.
 * <p>
 * Any light weight component implementation has to use <code>paint</code> and
 * <code>repaint</code> methods of the current paint manager to paint and repaint
 * itself.
 */
public abstract class LwPaintManager
implements LwManager
{
 /**
  * Repaints the specified light weight component.
  * @param <code>c</code> the specified light weight component.
  */
  public /*C#virtual*/ void repaint(LwComponent c) {
    if (c.isVisible())
      repaint(c, 0, 0, c.getWidth(), c.getHeight());
  }

 /**
  * Repaints a part of the specified light weight component. The part is specified
  * with the bound (location and size).
  * @param <code>c</code> the specified light weight component.
  * @param <code>x</code> the x coordinate of the repainting part.
  * @param <code>y</code> the y coordinate of the repainting part.
  * @param <code>w</code> the width of the repainting part.
  * @param <code>h</code> the height of the repainting part.
  */
  public /*C#virtual*/ void repaint(LwComponent c, int x, int y, int w, int h)
  {
    if (c.isVisible() && w > 0 && h > 0)
    {
      LwDesktop desktop = LwToolkit.getDesktop(c);
      if (desktop != null)
      {
        Point     p = LwToolkit.getAbsLocation(x, y, c);
        Rectangle r = MathBox.intersection(0, 0, desktop.getWidth(), desktop.getHeight(),
                                           p.x, p.y, w, h);
        if (r.width > 0 && r.height > 0) desktop.repaint (r.x, r.y, r.width, r.height);
      }
    }
  }

 /**
  * The method initiates painting process for the specified root light weight component
  * using the graphics. The method has to be used with a light weight root component
  * implementation to start painting process.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified root lightweight component.
  */
  protected /*C#virtual*/ void rootPaint(Graphics g, LwComponent c) {
    paint(g, c);
  }

 /**
  * The method initiates painting process for the specified light weight component
  * using the graphics. The method has to be used with a light weight component implementation
  * to paint itself.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified lightweight component.
  */
  public /*C#virtual*/ void paint(Graphics g, LwComponent c)
  {
    //!!! This path for IE.
    int dw = c.getWidth(), dh = c.getHeight();
    if (dw == 0 || dh == 0) return;
    c.validate();

    if (c.isOpaque()) updateComponent (g, c);
    paintComponent (g, c);
    if (c instanceof LwContainer)
    {
      LwContainer container = (LwContainer)c;

      g.clipRect(0, 0, dw, dh);
      Rectangle clipRect = g.getClipBounds();
      if (clipRect == null) return;

      for (int i = 0; i< container.count(); i++)
      {
        LwComponent kid = (LwComponent)container.get(i);
        if (kid.isVisible())
        {
          int kidX = kid.getX(), kidY = kid.getY();
          Rectangle intersect = MathBox.intersection(kidX, kidY, kid.getWidth(), kid.getHeight(), clipRect.x, clipRect.y, clipRect.width, clipRect.height);
          if (intersect.width > 0 && intersect.height > 0)
          {
            g.setClip(intersect.x, intersect.y, intersect.width, intersect.height);
            g.translate(kidX, kidY);
            paint(g, kid);
            g.translate(-kidX, -kidY);
          }
        }
      }
      g.setClip(clipRect);
      paintOnTop(g, container);
    }
  }

 /**
  * The method is called to update the specified component. The method has to just update
  * the component, it doesn't care about updating any child components.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified lightweight component to be updated.
  */
  abstract protected void updateComponent (Graphics g, LwComponent c);

 /**
  * The method is called to paint the specified component. The method has to just paint
  * the component, it doesn't care about painting any child components.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified lightweight component to be painted.
  */
  abstract protected void paintComponent(Graphics g, LwComponent c);

 /**
  * Probably will be redesigned
  */
  abstract protected void paintOnTop (Graphics g, LwContainer c);
}
