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
import org.zaval.misc.*;
import org.zaval.misc.event.*;

public class LwMenu
extends LwComboList
//implements LwMouseMotionListener
{
  private Hashtable menus;

  public LwMenu () {
    setInsets(3, 3, 3, 3);
    getViewMan (true).setBorder("br.plain");
  }

  public void removeAll () {
    if (menus != null) menus.clear();
    super.removeAll ();
  }

  public void remove(int i) {
    if (menus != null) setSubMenu((LwComponent)get(i), null);
    super.remove(i);
  }

  /*public void mouseMoved(LwMouseMotionEvent e)
  {
    int index = LwToolkit.getDirectCompAt(e.getX(), e.getY(), this);
    if (index < 0) getPosController().clearPos();
    else           getPosController().setOffset(index);
  }

  public void startDragged(LwMouseMotionEvent e) {}
  public void endDragged  (LwMouseMotionEvent e) {}
  public void mouseDragged(LwMouseMotionEvent e) {}

  public void mousePressed(LwMouseEvent e) {

  } */

  /*public void posChanged (PosEvent e)
  {
    if (activeSub != null)
    {
      LwPopupManager.hidePopup(activeSub);
      activeSub = null;
    }

    if (e.getOffset() >= 0)
    {
      LwMenu m = getSubMenu(get(e));
      LwPopupManager.showPopup(this, );
    }
    super.posChanged(e);
  } */

//  public /*C#override*/ boolean catchInput(LwComponent child)  {
  //  return true;
  //}

  public void setSubMenu (LwComponent comp, LwMenu menu)
  {
    if (indexOf(comp) < 0 || menu == this) throw new IllegalArgumentException();
    if (menus == null) menus = new Hashtable ();

    if (menu != null) menus.put (comp, menu);
    else              menus.remove (comp);
  }

  public LwMenu getSubMenu (LwComponent item) {
    return menus==null?null:(LwMenu)menus.get(item);
  }

  public void posChanged(PosEvent e)
  {
    if (getLwParent() != null && isVisible())
    {
      int off = e.getPrevOffset();
      if (off >= 0)
      {
        LwMenu m = getSubMenu((LwComponent)get(off));
        if (m != null) getPM().hidePopup(m);
      }

      off = getPosController().getOffset();
      if (off >= 0)
      {
        LwMenu m = getSubMenu((LwComponent)get(off));
        if (m != null) getPM().showPopup(null, 20, 20, m);
      }
    }
    super.posChanged(e);
  }


  public void setVisible (boolean v) {

    System.out.println ("LwMenu.setVisible() " + v);
    super.setVisible (v);
  }


  protected void perform (int from)
  {
    if (getSubMenu((LwComponent)get(from)) == null)
      getPM().hidePopup(this);

    super.perform(from);
  }


  public void addDelimiter()
  {
    LwCanvas canvas = new LwLine (LwBorder.ETCHED);
    canvas.setPSSize(-1, 2);
    add (canvas);
  }

  protected LwLayout getDefaultLayout() {
    return new LwListLayout (1);
  }

  private final LwPopupManager getPM() {
    return (LwPopupManager)LwToolkit.getStaticObj("pop");
  }
}
