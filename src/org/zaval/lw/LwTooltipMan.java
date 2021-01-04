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
import java.util.*;
import org.zaval.lw.event.*;
import org.zaval.util.Timer;

/**
 * This manager is used to support tooltip for a light weight components. As the tooltip can be used
 * any light weight component. The tooltip component is shown whenever the mouse cursor certain
 * time immobile inside the tooltip owner component (and no mouse buttons have been pressed).
 * There are two ways to work with the tooltip:
 * <ul>
 *   <li>
 *     The light weight component has to implement TooltipInfo interface.
 *     From the moment the manager will use the implementation to get a tooltip for the component
 *     by the specified target component location.
 *   </li>
 *   <li>
 *     The manager provides <code>setTooltipInfo</code> method tha allows to define the
 *     TooltipInfo interface for the specified component.
 *   </li>
 * </ul>
 */
public class LwTooltipMan
implements LwManager, LwMouseListener, LwMouseMotionListener, Runnable
{
 /**
  * The tooltip background color definition.
  */
  public static final Color tooltipBack = new Color(240, 240, 240);

  private int         x, y, tick = 400;
  private LwLayer     targetLayer;
  private LwComponent tooltip, target;
  private Hashtable   tooltips;

 /**
  * Sets the specified tick (milliseconds) that defines interval between "frozen" mouse state and
  * starting the tooltip showing.
  * @param <code>t</code> the specified tick.
  */
  public void setTick (int t) {
    tick = t;
  }

 /**
  * Removes a tooltip info interface for the given component, so it no longer show tooltips.
  * @param <code>c</code> the component.
  */
  public void removeTooltipInfo (LwComponent c)
  {
    if (target == c)
    {
      Timer.getTimer(false).remove(this);
      target = null;
      closeTooltipInfo();
    }
    tooltips.remove(c);
  }

 /**
  * Sets the specified tooltip info interface for the given component.
  * @param <code>c</code> the component.
  * @param <code>t</code> the specified tooltip info interface.
  */
  public void setTooltipInfo (LwComponent c, TooltipInfo t) {
    if (tooltips == null) tooltips = new Hashtable();
    tooltips.put(c, t);
  }

  public void mouseEntered (LwMouseEvent e)
  {
    LwComponent c = e.getLwComponent ();
    if (getTooltipInfo(c) != null)
    {
      target       = c;
      targetLayer = LwToolkit.getDesktop(c).getLayer(LwWinLayer.ID);
      this.x = e.getX();
      this.y = e.getY();
      Timer.getTimer(false).add(this, tick, tick);
    }
  }

  public void mouseExited  (LwMouseEvent e)
  {
    if (target != null)
    {
      Timer.getTimer(false).remove(this);
      target = null;
      closeTooltipInfo();
    }
  }

  public void mouseClicked (LwMouseEvent e) {}

  public void mousePressed (LwMouseEvent e)
  {
    if (target != null)
    {
      Timer.getTimer(false).remove(this);
      closeTooltipInfo();
    }
  }

  public void mouseReleased(LwMouseEvent e)
  {
    if (target != null)
    {
      this.x = e.getX();
      this.y = e.getY();
      Timer.getTimer(false).add(this, tick, tick);
    }
  }

  public void startDragged(LwMouseMotionEvent e) {}
  public void endDragged  (LwMouseMotionEvent e) {}
  public void mouseDragged(LwMouseMotionEvent e) {}

  public void mouseMoved  (LwMouseMotionEvent e)
  {
    if (target != null)
    {
      Timer.getTimer(false).clear(this);
      x = e.getX();
      y = e.getY();
      closeTooltipInfo();
    }
  }

  public void run ()
  {
    if (tooltip == null)
    {
      TooltipInfo tp = getTooltipInfo(target);
      tooltip = tp.getTooltip(x, y);
      if (tooltip != null)
      {
        Dimension ps = tooltip.getPreferredSize();
        tooltip.setSize(ps.width, ps.height);
        Point p = LwToolkit.getAbsLocation(x, y, target);
        int tx = p.x, ty = p.y - tooltip.getHeight();

        int dw = targetLayer.getWidth();
        if (tx + ps.width  > dw) tx = dw - ps.width - 1;
        tooltip.setLocation (tx < 0?0:tx, ty);
        targetLayer.add(LwWinLayer.INFO_WIN, tooltip);
      }
    }
  }

  public void dispose() {
    if (tooltips != null) tooltips.clear();
  }

 /**
  * Creates and returns a tooltip component by the given label. The method can be
  * used to create standard tooltip component for light weight applications.
  * @param <code>text</code> the specified text.
  * @return the tooltip component.
  */
  public static LwComponent createTooltip(String text)
  {
    LwLabel lab = new LwLabel(new org.zaval.data.Text(text));
    lab.setBackground(tooltipBack);
    lab.getTextRender().setFont (LwToolkit.SFONT);
    lab.getViewMan(true).setBorder("br.plain");
    lab.setInsets(1,4,1,4);
    Dimension d = lab.getPreferredSize();
    lab.setSize(d.width, d.height);
    return lab;
  }

  private void closeTooltipInfo()
  {
    if (tooltip != null)
    {
      targetLayer.remove(targetLayer.indexOf(tooltip));
      tooltip = null;
    }
  }

  private TooltipInfo getTooltipInfo(LwComponent c)
  {
    TooltipInfo ti = null;
    if (tooltips != null) ti = (TooltipInfo)tooltips.get(c);
    if (ti == null && c instanceof TooltipInfo) ti = (TooltipInfo)c;
    return ti;
  }
}

