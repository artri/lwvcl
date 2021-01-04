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

import org.zaval.misc.*;
import org.zaval.misc.event.*;

/**
 * This is scroll panel component. The scroll panel is a container that implements
 * automatic horizontal and/or vertical scrolling for a single child component.
 * The horizontal and vertical scrollbars are shown only if it is necessary, the properties
 * of the scroll bar components are computed with the component accordingly the metrical
 * characteristics of the child component. There are two types of the scrolling child
 * components:
 * <ul>
 *   <li>
 *     The child component doesn't implement org.zaval.misc.ScrollObj interface.
 *     In this case the scroll panel will use a preferred size of the component
 *     to calculate if it is necessary to use horizontal or vertical scrolling.
 *     The child component will be scrolled with <code>setLocation</code> method.
 *   </li>
 *   <li>
 *     The child component implements org.zaval.misc.ScrollObj interface.
 *     In this case the child component defines the size, and scrolling mechanism by
 *     the interface. For example, it is possible to scroll a content of the child component
 *     instead of the changing the component location.
 *   </li>
 * </ul>
 */
public class LwScrollPan
extends LwPanel
implements PosListener, ScrollObj, ScrollMan, LwLayout
{
   private LwScroll    hBar, vBar;
   private LwComponent scrollObj, scrollArea;
   private ScrollObj   sobj;

  /**
   * Constructs a scroll panel component with the specified child component to be scrolled.
   * This constructor allows vertical and horizontal scroll bars.
   * @param <code>c</code> the specified child component.
   */
   public LwScrollPan(LwComponent c) {
     this (c, LwToolkit.HORIZONTAL | LwToolkit.VERTICAL);
   }

  /**
   * Constructs a scroll panel component with the specified child component to be scrolled and
   * the given scroll bar mask. The mask defines if the horizontal or vertical scroll bar or
   * both scrollbars should be shown. Use following values for the mask:
   * <ul>
   *   <li>
   *     LwToolkit.HORIZONTAL - allows to use horizontal scroll bar.
   *   </li>
   *   <li>
   *     LwToolkit.VERTICAL - allows to use vertical scroll bar.
   *   </li>
   *   <li>
   *     LwToolkit.VERTICAL | LwToolkit.HORIZONTAL - allows to use both vertical and
   *     horizontal scroll bars.
   *   </li>
   * </ul>
   * @param <code>c</code> the specified child component.
   * @param <code>barMask</code> the specified scroll bar mask.
   */
   public LwScrollPan(LwComponent c, int barMask)
   {
     scrollObj = c;
     if (c instanceof ScrollObj) sobj = (ScrollObj)c;
     else                        sobj = this;
     sobj.setScrollMan(this);

     if ((LwToolkit.HORIZONTAL & barMask)>0)
     {
       hBar = new LwScroll(LwToolkit.HORIZONTAL);
       hBar.getPosController().addPosListener(this);
       add(hBar);
     }

     if ((LwToolkit.VERTICAL & barMask)>0)
     {
       vBar = new LwScroll(LwToolkit.VERTICAL);
       vBar.getPosController().addPosListener(this);
       add(vBar);
     }

     if (sobj.moveContent())
     {
       scrollArea = scrollObj;
       add(scrollObj);
     }
     else
     {
       LwPanel sa = new LwPanel();
       sa.setLwLayout(new LwRasterLayout(LwRasterLayout.USE_PS_SIZE | ((hBar != null)?0:LwRasterLayout.W_BY_PARENT) | ((vBar != null)?0:LwRasterLayout.H_BY_PARENT)));
       sa.add(scrollObj);
       scrollArea = sa;
       add(scrollArea);
     }
   }

  /**
   * Set the horizontal & vertical unit & page increments.
   * @param hUnit the horizontal scroll unit increment (-1 for no change)
   * @param hPage the horizontal scroll page increment (-1 for no change)
   * @param vUnit the vertical scroll unit increment (-1 for no change)
   * @param vPage the vertical scroll page increment (-1 for no change)
   */
   public void setIncrements(int hUnit, int hPage, int vUnit, int vPage)
   {
     if (hBar != null)
     {
       if (hUnit != -1) hBar.setUnitIncrement(hUnit);
       if (hPage != -1) hBar.setPageIncrement(hPage);
     }

     if (vBar != null)
     {
       if (vUnit != -1) vBar.setUnitIncrement(vUnit);
       if (vPage != -1) vBar.setPageIncrement(vPage);
     }
   }

   public void posChanged(PosEvent e)
   {
     Point p = sobj.getSOLocation();
     if (hBar != null && hBar.getPosController() == e.getSource())
       sobj.setSOLocation(-hBar.getPosController().getOffset(), p.y);
     else
     {
       if (vBar != null) sobj.setSOLocation(p.x, -vBar.getPosController().getOffset());
     }
   }

   public Point getSOLocation() {
     return scrollObj.getLocation();
   }

   public void setSOLocation(int x, int y) {
     scrollObj.setLocation(x, y);
   }

   public Dimension  getSOSize() {
     return scrollObj.getPreferredSize();
   }

   public void setScrollMan(ScrollMan m) {}

   public void scrollObjMoved(int x, int y) {
     if (hBar != null) hBar.getPosController().setOffset(-x);
     if (vBar != null) vBar.getPosController().setOffset(-y);
   }

   public void scrollObjResized (int w, int h) {
     invalidate();
     validate();
   }

   public boolean  moveContent () {
     return false;
   }

   public void componentAdded  (Object id, Layoutable comp, int index) {}
   public void componentRemoved(Layoutable comp, int index) {}

  /**
   * Calculates the preferred size dimension of the layout container.
   * The method calculates "pure" preferred size, it means that an insets
   * of the container is not considered.
   * @param <code>target</code> the layout container.
   */
   public Dimension calcPreferredSize(LayoutContainer target)
   {
     Dimension d = getPS(scrollArea);
     if (vBar == null && hBar != null) d.height += hBar.getPreferredSize().height;
     if (hBar == null && vBar != null) d.width  += vBar.getPreferredSize().width;
     return d;
   }

  /**
   * Makes visible the specified location of the scrolled component.
   * @param <code>x</code> the "x" coordinate of the scrolled component. Pass -1 to ignore.
   * @param <code>y</code> the "y" coordinate of the scrolled component. Pass -1 to ignore.
   */
   public void makeVisible(int x, int y)
   {
     Point soLoc = getSOLocation();
     boolean hb = hBar != null && hBar.isVisible();
     boolean vb = vBar != null && vBar.isVisible();
     int w = width  - (vb?vBar.getWidth():0);
     int h = height - (hb?hBar.getHeight():0);

     if (hb && x != -1)
     {
       if ((x < soLoc.x) || (x >= soLoc.x + w))
         hBar.getPosController().setOffset(x - w/2);
     }

     if (vb && y != -1)
     {
       if ((y < soLoc.y) || (y >= soLoc.y + h))
         vBar.getPosController().setOffset(y - h/2);
     }
   }


  /**
   * Lays out the child layoutable components inside the layout container.
   * @param <code>target</code> the layout container that needs to be laid out.
   */
   public void layout(LayoutContainer target)
   {
     //
     // Calculate maximal values for vertical and horizontal scroll bars
     //
     Dimension so = sobj.getSOSize();
     Insets    insets = target.getInsets();
     int ww = width  - insets.left - insets.right,  maxH = ww;
     int hh = height - insets.top  - insets.bottom, maxV = hh;

     // Vertical scroll bar max value calculation
     if (hBar != null)
       if (so.width > ww || ((so.height > hh) && so.width > (ww - (vBar==null?0:vBar.getPreferredSize().width))))
         maxV -= hBar.getPreferredSize().height;
     maxV = so.height > maxV?(so.height - maxV):-1;

     // Horizontal scroll bar max value calculation
     if (vBar != null)
       if (so.height > hh || ((so.width > ww) && so.height > (hh - (hBar==null?0:hBar.getPreferredSize().height))))
         maxH -= vBar.getPreferredSize().width;
     maxH = so.width > maxH?(so.width - maxH):-1;

     //
     // Sets scroll bars visibility
     //
     Point p  = sobj.getSOLocation();
     int   sy = p.y;
     if (vBar != null)
     {
       if (maxV < 0)
       {
         if (vBar.isVisible())
         {
           vBar.setVisible(false);
           sobj.setSOLocation(p.x, 0);
           vBar.getPosController().setOffset (0);
         }
         sy = 0;
       }
       else
       {
         vBar.setVisible(true);
         sy = sobj.getSOLocation().y;
       }
     }

     if (hBar != null)
     {
       if (maxH < 0)
       {
         if (hBar.isVisible())
         {
           hBar.setVisible(false);
           sobj.setSOLocation(0, sy);
           hBar.getPosController().setOffset (0);
         }
       }
       else hBar.setVisible(true);
     }

     //
     // Layout scroll panel
     //
     Dimension sa = getPS(scrollArea), vs = getPS(vBar), hs = getPS(hBar);
     if (sa.width > 0)
     {
       scrollArea.setLocation (insets.left, insets.top);
       scrollArea.setSize     (ww - vs.width,
                               hh - hs.height);
     }

     if (hBar != null && hs.height > 0)
     {
       hBar.setLocation(insets.left, height - insets.bottom - hs.height);
       hBar.setSize    (ww - vs.width, hs.height);
       hBar.setMaximum(maxH);
     }

     if (vBar != null && vs.width > 0)
     {
       vBar.setLocation(width - insets.right - vs.width, insets.top);
       vBar.setSize    (vs.width, hh - hs.height);
       vBar.setMaximum(maxV);
     }
   }

  /**
   * Gets the default layout manager that is set with the container during initialization.
   * This implementation of the method returns LwScrollLayout as the default layout manager.
   * @return a layout manager.
   */
   protected /*C#override*/ LwLayout getDefaultLayout() {
     return this;
   }

   private static Dimension getPS(Layoutable l)  {
     return (l != null && l.isVisible())?l.getPreferredSize():new Dimension();
   }
}



