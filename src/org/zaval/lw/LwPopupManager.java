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
import java.awt.event.*;
import java.util.*;
import org.zaval.lw.event.*;

/**
 * This abstract class should be used to implement a popup menu manager. Use
 * the <code>setPopup</code> method to register or unregister the specified
 * pop up menu for the specified light weight component.
 */
public abstract class LwPopupManager
implements LwManager, LwMouseListener
{
  private Hashtable menus;

 /**
  * Binds the specified pop up menu with the given light weight component.
  * Use <code>null</code> value as the pop up menu to unbind a popup menu from
  * the specified light weight component.
  * @param <code>c</code> the given light weight component.
  * @param <code>p</code> the specified pop up menu.
  */
  public void setPopup(LwComponent c, Object p)
  {
    if (menus == null) menus = new Hashtable();
    if (p == null) menus.remove (c);
    else           menus.put (c, p);
  }

  public void dispose() {
    if (menus != null) menus.clear();
  }

  public /*C#virtual*/ void mousePressed (LwMouseEvent e)
  {
    if (menus != null && (e.getMask() & InputEvent.BUTTON3_MASK) > 0)
    {
      LwComponent target = e.getLwComponent();
      Object      popup  = menus.get(target);
      if (popup != null) showPopup(target, e.getAbsX(), e.getAbsY(), popup);
    }
  }

  public /*C#virtual*/ void mouseClicked (LwMouseEvent e) {}
  public /*C#virtual*/ void mouseEntered (LwMouseEvent e) {}
  public /*C#virtual*/ void mouseExited  (LwMouseEvent e) {}
  public /*C#virtual*/ void mouseReleased(LwMouseEvent e) {}

 /**
  * Shows the specified popup menu for the target light weight component at the given
  * location. The location should be relative to the desktop where the target component resides.
  * @param <code>target</code> the given light weight component.
  * @param <code>x</code> the <code>x<code> coordinate.
  * @param <code>y</code> the <code>y<code> coordinate.
  * @param <code>popup</code> the specified popup menu.
  */
  public abstract void showPopup (LwComponent target, int x, int y, Object popup);

 /**
  * Hides the specified popup menu.
  * @param <code>popup</code> the specified popup menu.
  */
  public abstract void hidePopup (Object popup);
}

