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

public class LwMenuBar
extends LwPanel
implements LwActionListener
{
  private Hashtable menus;

  public void setMenu(int i, LwMenu menu)
  {
    LwMenuButton b = (LwMenuButton)get(i);
    if (menu == null)
    {
      if (menus != null) menus.remove(b);
    }
    else
    {
      if (menus == null) menus = new Hashtable();
      menus.put (get(i), menu);
    }
  }

  public void insert(int index, Object constr, LwComponent c) {
    ((LwMenuButton)c).addActionListener(this);
    super.insert(index, constr, c);
  }

  public void removeAll ()  {
    for (int i=0; i<count(); i++) ((LwMenuButton)get(i)).removeActionListener(this);
    super.removeAll();
  }

  public void remove (int i) {
    ((LwMenuButton)get(i)).removeActionListener(this);
    super.remove(i);
  }

  public void actionPerformed (LwActionEvent e)
  {
    LwMenuButton comp = (LwMenuButton)e.getSource();
    LwMenu       menu = menus != null?(LwMenu)menus.get(comp):null;

    if (menu != null)
    {
      Point p = LwToolkit.getAbsLocation (comp);
      getPM().showPopup(this, p.x, p.y + comp.getHeight(), menu);
    }
  }

  protected LwLayout getDefaultLayout() {
    return new LwFlowLayout();
  }

  private final LwPopupManager getPM() {
    return (LwPopupManager)LwToolkit.getStaticObj("popup");
  }

  public static void main (String[] args)
  throws Exception
  {
    LwFrame frame = new LwFrame();
    frame.setSize(400, 400);
    frame.addWindowListener(new org.zaval.lw.demo.WL());
    frame.getRoot().setLwLayout(new LwBorderLayout()) ;

    //LwPopupManager.popupPanel = new LwPopupPanel();
    //frame.getRoot().add (LwBorderLayout.CENTER, LwPopupManager.popupPanel);


    LwButton b = new LwButton ("Test ...");
    b.setSize(100, 30);
    b.setLocation(10, 30);

    //frame.getRoot().add (LwBorderLayout.CENTER, LwPopupManager.popupPanel);



    LwMenu menu = new LwMenu();
    menu.add("Item 1");
    menu.add("Item 2");
    menu.add("Item 3");
    menu.add("Item 4");

    LwMenu m1 = new LwMenu();
    m1.add("Item 1");
    m1.add("Item 2");
    m1.add("Item 3");

    menu.setSubMenu((LwComponent)menu.get(0), m1);


    LwMenuBar mb = new LwMenuBar();
    mb.add (new LwMenuButton("Item 2"));
    mb.add (new LwMenuButton("Item 1"));
    mb.add (new LwMenuButton("Item 3"));

    mb.setMenu(0, menu);
    frame.getRoot().add (LwBorderLayout.NORTH, mb);

    frame.setVisible (true);

  }
}
