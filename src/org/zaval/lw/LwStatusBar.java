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
 * This is status bar light weight component. The component is a light weight container that
 * uses special layout manager to place status bar components, so don't set any other
 * layout manager for the container. The container layouts child components according to
 * it percentage constraint. The constraint should be passed to <code>add</code> or
 * <code>insert</code> methods of the container as an Integer object where the int value
 * defines the percent. The percent determines width of the child relatively the container width.
 * The sample below illustrates the component usage
 * <pre>
 *   ...
 *   LwStatusBar status = new LwStatusBar();
 *   status.add (new Integer(30), new LwLabel("Label 1"));
 *   status.add (new Integer(70), new LwLabel("Label 2"));
 *   ...
 * </pre>
 * <p>
 * The child components should have identical borders, so the component has special
 * <code>setBorderView</code> method that defines the common border view. The view
 * will set for all added components.
 */
public class LwStatusBar
extends LwPanel
{
  private LwView borderView;

 /**
  * Constructs the class instance.
  */
  public LwStatusBar(){
    this(2);
  }

 /**
  * Constructs the class with the specified horizontal gap between child components.
  * @param <code>hgap</code> the specified horizontal gap.
  */
  public LwStatusBar(int gap)
  {
    setBorderView(LwToolkit.getView("br.sunken2"));
    setInsets(gap, 0, 0, 0);
    setLwLayout(new LwPercentLayout());
  }

 /**
  * Sets the border view that should be applied for all child components. The method
  * sets the border view for all child components that have been added to the container
  * before.
  * @param <code>v</code> the specified border view.
  */
  public void setBorderView(LwView v)
  {
    if (v != borderView)
    {
      borderView = v;
      for (int i=0; i<count(); i++)
      {
        LwComponent c = (LwComponent)get(i);
        c.getViewMan(true).setBorder(borderView);
      }
      repaint();
    }
  }

  public /*C#override*/ void insert (int i, Object s, LwComponent d) {
   ((LwComponent)d).getViewMan(true).setBorder(borderView);
    super.insert(i, s, d);
  }
}



