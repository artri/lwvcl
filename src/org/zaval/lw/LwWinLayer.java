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

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.zaval.lw.event.*;

/**
 * This is housekeeping layer implementation that is used by LwRoot class as the internal
 * windows container.
 */
public class LwWinLayer
extends LwBaseLayer
implements LwLayout, LwChildrenListener
{
 /**
  * The layer id.
  */
  public static final Object ID = "win";

 /**
  * The modal window type.
  */
  public static final Integer MODAL_WIN = new Integer(1);

 /**
  * The MDI window type.
  */
  public static final Integer MDI_WIN = new Integer(2);

 /**
  * The information window type.
  */
  public static final Integer INFO_WIN = new Integer(3);

  private Hashtable   winsInfo  = new Hashtable();
  private Vector      winsStack = new Vector();
  private int         topModalIndex = -1;
  private LwComponent activeWin;

 /**
  * Constructs the layer.
  */
  public LwWinLayer() {
    super(ID);
    setOpaque (false);
  }

  public void childPerformed(LwAWTEvent e)
  {
    if (e.getID() == LwFocusEvent.FOCUS_GAINED)
    {
      LwComponent child = LwToolkit.getDirectChild(this, e.getLwComponent());
      activate(child);
    }
  }

 /**
  * Adds the new window with the specified window listener and the given
  * window type. The window listener can be <code>null</code>. If the window type is MODAL_WIN
  * than the window will be activated automatically.
  * @param <code>c</code> the specified window.
  * @param <code>l</code> the specified window listner that wants to handle the window event.
  * @param <code>type</code> the specified window type.
  */
  public void add (LwComponent c, LwWinListener l, Integer type) {
    add (new Object[] {type, l},  c);
  }

  public void componentAdded(Object id, Layoutable lw, int index)
  {
    if (id == null) id = MDI_WIN;

    Object[] info = new Object[3];
    info[0] = activeWin;
    if (id instanceof Integer) info[1] = id;
    else
    if (id instanceof LwWinListener) info[2] = id;
    else
    {
      info[1] = ((Object[])id)[0];
      info[2] = ((Object[])id)[1];
    }

    if (info[1] == null) info[1] = MDI_WIN;
    if (!info[1].equals(MDI_WIN) && !info[1].equals(MODAL_WIN) && !info[1].equals(INFO_WIN))
      throw new IllegalArgumentException();

    winsInfo.put(lw, info);
    winsStack.addElement(lw);
    pw((LwWinListener)info[2], new LwWinEvent((LwComponent)lw, LwWinEvent.WIN_OPENED));

    if (info[1].equals(MODAL_WIN))
    {
      topModalIndex = winsStack.size()-1;
      activate((LwComponent)lw);
    }

    repaint();
  }

  public void componentRemoved(Layoutable lw, int index)
  {
    //
    // Test if the window is active
    //
    if (activeWin == lw)
    {
      activeWin = null;
      LwToolkit.getFocusManager().requestFocus(null);
    }

    //
    // Validate topModalIndex
    //
    int ci = winsStack.indexOf(lw);

    LwWinListener l = getWinListener((LwComponent)lw);
    winsInfo.remove(lw);
    winsStack.removeElement(lw);

    if (ci < topModalIndex) topModalIndex--;
    else
    if (topModalIndex == ci)
    {
      for (topModalIndex=count()-1; topModalIndex>=0; topModalIndex--)
        if (getWinType((LwComponent)winsStack.elementAt(topModalIndex)).equals(MODAL_WIN))
          break;
    }

    pw(l, new LwWinEvent((LwComponent)lw, LwWinEvent.WIN_CLOSED));

    if (topModalIndex >= 0)
    {
      int aindex = winsStack.size() - 1;
      while (getWinType((LwComponent)winsStack.elementAt(aindex)).equals(INFO_WIN)) aindex--;
      activate((LwComponent)winsStack.elementAt(aindex));
    }
  }

  public /*C#override*/ LwComponent getLwComponentAt (int x, int y) {
    return (activeWin == null)?null:activeWin.getLwComponentAt(x - activeWin.getX(), y - activeWin.getY());
  }

  public Dimension calcPreferredSize(LayoutContainer target) {
    return LwToolkit.getMaxPreferredSize(target);
  }

  public /*C#override*/ void invalidate() {
    isValidFlag = false;
  }

  public /*C#override*/ void mousePressed (int x, int y, int mask)
  {
    int cnt = count();
    if (cnt > 0)
    {
      if (activeWin != null && indexOf (activeWin) == cnt-1)
      {
        int x1 = activeWin.getX(), y1 = activeWin.getY();
        int x2 = x1 + activeWin.getWidth(), y2 = y1 + activeWin.getHeight();
        if (x >= x1 && y >= y1 && x < x2 && y < y2) return;
      }

     for (int i=cnt-1;i>=0 && i>=topModalIndex;i--)
      {
        LwCanvas d = (LwCanvas)children.elementAt(i);
        if (d.isVisible() && d.isEnabled() && !getWinType(d).equals(INFO_WIN) &&
            x >= d.x && y >= d.y && x < d.x + d.width && y < d.y + d.height)
        {
          activate(d);
          return;
        }
      }

      if (topModalIndex < 0 && activeWin != null)  activate (null);
    }
  }

  public void layout(LayoutContainer target)
  {
    Dimension d = target.getSize();
    for (int i=0; i<target.count(); i++)
    {
      Layoutable l = target.get(i);
      if (l.isVisible())
      {
        int x = l.getX(), y = l.getY(), w = l.getWidth(), h = l.getHeight();
        int minH = Math.min(VIS_PART_SIZE, h), minW = Math.min(VIS_PART_SIZE, w);
        if (x > target.getWidth() - minW) x = target.getWidth() - minW;
        else
        if (x + w < minW) x = minW - w;

        if (y > target.getHeight() - minH) y = target.getHeight() - minH;
        else
        if (y < 0) y = 0;

        l.setLocation (x, y);
      }
    }
  }

 /**
  * Activates (deactivates) the specified window.
  * If the specified window is <code>null</code> and the current active window is not
  * <code>null</code> than the current active window will be deactivated. The method
  * will perform IllegalArgumentException if the window is not MDI_WIN or has not
  * been opened before.
  * @param <code>c</code> the specified window to be activated.
  */
  public void activate (LwComponent c)
  {
    if (c != null && (winsInfo.get(c) == null || getWinType(c).equals(INFO_WIN)))
      throw new IllegalArgumentException();

    if (c != activeWin)
    {
       if (c == null)
       {
         if (getWinType(activeWin).equals(MODAL_WIN)) throw new IllegalArgumentException();

         LwComponent old = activeWin;
         activeWin = null;
         pw(getWinListener(old), new LwWinEvent(old, LwWinEvent.WIN_DEACTIVATED));
         LwToolkit.getFocusManager().requestFocus(null);
       }
       else
       {
         if (winsStack.indexOf(c) < topModalIndex) throw new IllegalArgumentException();

         LwComponent old = activeWin;
         activeWin = c;
         toFront(activeWin);

         if (old != null)
           pw(getWinListener(old), new LwWinEvent(old, LwWinEvent.WIN_DEACTIVATED));

         pw(getWinListener(activeWin), new LwWinEvent(activeWin, LwWinEvent.WIN_ACTIVATED));
         LwToolkit.getFocusManager().requestFocus(LwToolkit.getFocusManager().findFocusable(activeWin));
       }
    }
  }

  public /*C#override*/ void keyPressed  (int keyCode, int mask)
  {
    if (count() > 0 && keyCode == KeyEvent.VK_TAB && (mask & InputEvent.SHIFT_MASK) > 0)
    {
      if (activeWin == null) activate((LwComponent)get(count()-1));
      else
      {
        int winIndex = winsStack.indexOf(activeWin) - 1;
        if (winIndex < topModalIndex || winIndex < 0) winIndex = winsStack.size() - 1;
        activate ((LwComponent)winsStack.elementAt(winIndex));
      }
    }
  }

  public LwComponent getActive () {
    return activeWin;
  }

 /**
  * Gets the current window that has been activated.
  * @return a current active window.
  */
  public /*C#override*/ boolean isActive () {
    return activeWin != null;
  }

  public /*C#override*/ LwComponent getFocusRoot() {
    return activeWin;
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return this;
  }

  private Integer getWinType (LwComponent w) {
    return (Integer)((Object[])winsInfo.get(w))[1];
  }

  private final LwComponent getWinOwner (LwComponent w) {  /*java*/
  /*C#private LwComponent getWinOwner (LwComponent w) {*/
    return (LwComponent)((Object[])winsInfo.get(w))[0];
  }

  private final LwWinListener getWinListener(LwComponent c) { /*java*/
  /*C#private LwWinListener getWinListener(LwComponent c) {*/
    return (LwWinListener)((Object[])winsInfo.get(c))[2];
  }

  private void pw (LwWinListener l, LwWinEvent e) {
    LwToolkit.getEventManager().perform (e);
    if (l != null) LwWinSupport.process(l, e);
  }

  private static final int VIS_PART_SIZE = 30;
}

