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
import org.zaval.util.*;

/**
 * This abstract class is used as base for lightweight views implementations.
 * A view is used to provide painting for a decorative element
 * (box, border and so on). To develop a view it is necessary to pass through
 * following steps:
 * <ul>
 *   <li>
 *     Inherit the class for your view.
 *   </li>
 *   <li>
 *     Implement <code>paint</code> method to define the face of the view.
 *   </li>
 *   <li>
 *     Override (if it is necessary) <code>recalc</code> method to calculate the view
 *     merics. For example, you can calculate "pure" preferred size inside the method or
 *     some metrical characteristics that are used to calculate the preferred size.
 *   </li>
 *   <li>
 *     Override <code>calcPreferredSize</code> method to define the "pure" preferred size.
 *     This is very important bacause if your view uses ORIGINAL type than the preferred size
 *     will be used to calculate a preferred size for an owner light weight component.
 *   </li>
 * </ul>
 */
public abstract class LwView
extends ValidationObject
{
 /**
  * The stretch type of the view.
  */
  public static final int STRETCH  = 1;

 /**
  * The mosaic type of the view.
  */
  public static final int MOSAIC   = 2;

 /**
  * The original type of the view.
  */
  public static final int ORIGINAL = 3;

  private int type = ORIGINAL;

 /**
  * Constructs the view. The view will use mosaic type as a default view type.
  */
  public LwView() {
    this(MOSAIC);
  }

 /**
  * Constructs the view with the specified view type.
  * @param <code>type</code> the view type.
  */
  public LwView(int type) {
    setType(type);
  }

 /**
  * Gets the type of the view.
  * @return a type of the view.
  */
  public int getType() {
    return type;
  }

 /**
  * Sets the specified type of the view. The view can have stretch, mosaic or
  * original type (the types are defined with the appropriate constants of
  * the class), otherwise the method performs IllegalArgumentException
  * exception. The type defines an algorithm that is used to paint the
  * view, the short descriptions of the possible view types are shown below:
  * <ul>
  *   <li>
  *     ORIGINAL. The <code>paint</code> method of the view gets own preferred size as
  *     the view size.
  *   </li>
  *   <li>
  *     MOSAIC. The <code>paint</code> method of the view gets own preferred size as
  *     the view size, but the view will be painted as many times as it can be placed inside
  *     the owner component surface.
  *   </li>
  *   <li>
  *     STRETCH. The view will be stretched on the owner component surface.
  *   </li>
  * </ul>
  * @param <code>t</code> the specified view type.
  */
  public void setType(int t)
  {
    if (t != STRETCH && t != MOSAIC && t != ORIGINAL) throw new IllegalArgumentException();
    if (type != t)
    {
      type = t;
      invalidate();
    }
  }

 /**
  * Gets the view insets. The insets is used to calculate the owner component
  * insets.
  * @return an insets of the view.
  */
  public /*C#virtual*/ Insets  getInsets() {
    return new Insets(0, 0, 0, 0);
  }

 /**
  * Gets the view preferred size. The size is calculated as amount of a "pure"
  * preferred size (that is returned with <code>calcPreferredSize</code> method)
  * and the view insets. The preferred size is used with an owner component to
  * compute own preferred size.
  * @return a preferred size of the view.
  */
  public /*C#virtual*/ Dimension getPreferredSize () {
    validate();
    return calcPreferredSize ();
  }

 /**
  * Calculates and returns the view preferred size. The method has not to use
  * the view insets to compute the preferred size.
  * @return a "pure" preferred size of the view.
  */
  protected /*C#virtual*/ Dimension calcPreferredSize () {
    return new Dimension();
  }

 /**
  * Invoked with <code>validate</code> method if the view is invalid. The method
  * can be overrided to calculate metrical characteristics of the view and the method
  * is called only if it is realy necessary, so there is not necessaty to care about
  * superfluous calling and computing.
  */
  protected /*C#override*/ void recalc() {}

 /**
  * Paints the view using the preferred size of the view. The location where the
  * view has to be painted is determined with <code>x</code> and <code>y</code>
  * coordinates.
  * @param <code>g</code> the specified context to be used for painting.
  * @param <code>x</code> the x coordinate.
  * @param <code>y</code> the y coordinate.
  * @param <code>d</code> the owner component.
  */
  public /*C#virtual*/ void paint(Graphics g, int x, int y, Drawable d)
  {
    //???
    validate();
    Dimension size = calcPreferredSize ();
    paint(g, x, y, size.width, size.height, d);
  }

 /**
  * Paints the view using a given width and height. The location where the
  * view has to be painted is determined with <code>x</code> and <code>y</code>
  * coordinates. This abstract method has to be implmented to define a "face" for the view.
  * @param <code>g</code> the specified context to be used for painting.
  * @param <code>x</code> the x coordinate.
  * @param <code>y</code> the y coordinate.
  * @param <code>w</code> the width of the view.
  * @param <code>h</code> the height of the view.
  * @param <code>d</code> the owner component.
  */
  public abstract void paint(Graphics g, int x, int y, int w, int h, Drawable d);

 /**
  * The method is called whenever the view owner has been changed.
  * It allows to store the owner reference for a given view if it is necessary.
  * For example, if the view preferred size has been changed so in this case
  * the view owner component should be invalidated, because the owner preferred
  * size depends on the view.
  * @param <code>v</code> the new view owner.
  */
  protected /*C#virtual*/ void ownerChanged(Validationable v) {}
}

