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
 * This class is implementation of the LwPaintManager for the library.
 * The implementation supports:
 * <ul>
 *   <li>Double buffering.</li>
 *   <li>Light weight component views painting.</li>
 * </ul>
 */
public class LwPaintManImpl
extends LwPaintManager
{
  private Dimension MAX_BUFFER_SIZE;
  private boolean isBuffered = true;
  private Image   buffer;

 /**
  * Constructs the paint manager.
  */
  public LwPaintManImpl () {
    MAX_BUFFER_SIZE = LwToolkit.getScreenSize();
  }

 /**
  * Enables or disables double buffering. The double buffering is used to increase painting
  * performance but it needs memory.
  * @param <code>b</code> <code>true</code> to enable double buffering; <code>false</code>
  * otherwise.
  */
  public void useDoubleBuffer(boolean b)
  {
    if (b != isBuffered)
    {
      isBuffered = b;
      if (!isBuffered)
      {
        buffer.flush();
        buffer = null;
      }
    }
  }

  public void dispose()
  {
    if (buffer != null)
    {
       buffer.flush();
       buffer = null;
    }
  }

 /**
  * The method initiates painting process for the specified root light weight component
  * using the graphics. The method has to be used with a light weight root component
  * implementation to start painting process. The method is overrided to support double
  * buffering.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified root lightweight component.
  */
  protected void rootPaint(Graphics g, LwComponent c)
  {
    LwDesktop nc = LwToolkit.getDesktop(c);
    Rectangle clipRect = g.getClipBounds();
    if (isBuffered && clipRect.width  <= MAX_BUFFER_SIZE.width &&
                      clipRect.height <= MAX_BUFFER_SIZE.height  )
    {
      if (buffer == null ||
          clipRect.width  > buffer.getWidth (null)||
          clipRect.height > buffer.getHeight(null)  )
      {
        if (buffer != null) buffer.flush();
        buffer = nc.createImage(clipRect.width, clipRect.height);
      }

      Graphics gg = null;
      try {
        gg = buffer.getGraphics();
        gg.clearRect(0, 0, buffer.getWidth (null), buffer.getHeight(null));

        gg.setClip(0, 0, clipRect.width, clipRect.height);
        gg.translate(-clipRect.x, -clipRect.y);
        paint(gg, c);
        g.drawImage(buffer, clipRect.x, clipRect.y, null);
      }
      finally {
        if (gg != null) gg.dispose();
      }
    }
    else paint(g, c);
  }

 /**
  * The method is called to update the specified component. The updating process
  * can be occured with the following two ways:
  * <ul>
  *   <li>
  *     If the component has a view manager and the manager defines a background
  *     view, than the view will be used to fill the component background.
  *   </li>
  *   <li>
  *     If the component has not any view manager or the manager doesn't define a
  *     background view, than the method fills the component with its background
  *     color.
  *   </li>
  * </ul>
  * After that the method calls <code>update</code> method of the component.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified lightweight component to be updated.
  */
  protected void updateComponent (Graphics g, LwComponent c)
  {
     LwViewMan skins = c.getViewMan(false);
     if (skins != null && skins.getBg() != null) paintSkin(g, skins.getBg(), c);
     else
     {
       g.setColor(c.getBackground());
       g.fillRect(0, 0, c.getWidth(), c.getHeight());
     }
     c.update(g);
  }

 /**
  * The method is called to paint the specified component. The method uses
  * the border and face views that can be provided with a view manager of
  * the specified component to paint the component.
  * @param <code>g</code> the specified graphics.
  * @param <code>c</code> the specified lightweight component to be painted.
  */
  protected void paintComponent(Graphics g, LwComponent c)
  {
    LwViewMan skins = c.getViewMan(false);
    LwView    view  = null;
    if (skins != null)
    {
      paintSkin(g, skins.getBorder(), c);
      view = skins.getView();
      if (view != null && view.getType() != LwView.ORIGINAL)
      {
        paintSkin(g, view, c);
        view = null;
      }
    }

    Insets insets = c.getInsets();
    if (insets.left + insets.right + insets.top + insets.bottom > 0)
    {
      //
      //!!! This is MSIE clipRect() method equivalent.
      //
      Rectangle clip = g.getClipBounds();
      int x1 = Math.max(clip.x, insets.left);
      int y1 = Math.max(clip.y, insets.top);
      g.setClip(x1, y1, Math.min(clip.x + clip.width,  insets.left + c.getWidth() - insets.left - insets.right) - x1,
                        Math.min(clip.y + clip.height, insets.top + c.getHeight() - insets.top - insets.bottom) - y1);
    }

    Point p = c.getOrigin();
    if (p != null) g.translate(p.x, p.y);
    if (view != null) paintSkin(g, view, c);
    c.paint(g);
    if (p != null) g.translate(-p.x, -p.y);
  }

  protected void paintOnTop (Graphics g, LwContainer c)
  {
    Point p = c.getOrigin();
    if (p != null) g.translate(p.x, p.y);
    c.paintOnTop(g);
    if (p != null) g.translate(-p.x, -p.y);
  }

  private static void paintSkin(Graphics g, LwView s, Drawable c)
  {
    if (s != null)
    {
      switch (s.getType())
      {
        case LwView.STRETCH :
        {
          s.paint(g, 0, 0, c.getWidth(), c.getHeight(), c);
        } break;
        case LwView.MOSAIC  :
        {
          Dimension ps = s.getPreferredSize();
          if (ps.width > 0 && ps.height > 0)
          {
            int dx = c.getWidth ()/ps.width +  (c.getWidth()%ps.width>0?1:0);
            int dy = c.getHeight()/ps.height + (c.getHeight()%ps.height>0?1:0);
            int xx = 0, yy;
            for (int i=0; i<dx; i++)
            {
               yy = 0;
               for (int j=0; j<dy; j++)
               {
                 s.paint(g, xx, yy, ps.width, ps.height, c);
                 yy += ps.height;
               }
               xx += ps.width;
            }
          }
        } break;
        case LwView.ORIGINAL:
        {
          Insets i= c.getInsets();
          s.paint(g, i.left, i.top, c);
        } break;
      }
    }
  }
}




