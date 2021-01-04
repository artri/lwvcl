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
 * This is basic interface for components that are going to be laidout with the library layout
 * managers. The main purpose of the interface to separate layouting functionality from
 * the lightweight component functionality. It means that it is possible to re-use the set of
 * layout managers that is provided with the library for any other components. It just
 * necessary to implement the interface.
 */
public interface Layoutable
{
 /**
  * Gets the location of this component point specifying the component top-left corner.
  * The location is relative to the parent component coordinate space.
  * @return an instance of <code>Point</code> representing the top-left corner
  * of the component bounds in the coordinate space of the component parent.
  */
  Point getLocation();

 /**
  * Gets the <code>x</code> location of this component specifying the component top-left corner.
  * The location is relative to the parent component coordinate space.
  * @return an <code>x</code> coordinate representing the top-left corner
  * of the component bounds in the coordinate space of the component parent.
  */
  int getX();

 /**
  * Gets the <code>y</code> location of this component specifying the component top-left corner.
  * The location is relative to the parent component coordinate space.
  * @return an <code>y</code> coordinate representing the top-left corner
  * of the component bounds in the coordinate space of the component parent.
  */
  int getY();

 /**
  * Gets the width of this component.
  * @return a width of the component.
  */
  int getWidth();

 /**
  * Gets the height of this component.
  * @return a height of the component.
  */
  int getHeight();


 /**
  * Sets a new location for this component. The top-left corner of
  * the new location is specified by the <code>x</code> and <code>y</code>
  * parameters in the coordinate space of this component parent.
  * @param <code>x</code> the <i>x</i>-coordinate of the new location
  * top-left corner in the parent coordinate space.
  * @param <code>y</code> the <i>y</i>-coordinate of the new location
  * top-left corner in the parent's coordinate space.
  */
  void setLocation(int x, int y);

 /**
  * Returns a size of this component. The size is reperesented with
  * <code>java.awt.Dimension</code> class.
  * @return a <code>Dimension</code> object that indicates the
  * size of this component.
  */
  Dimension getSize();

 /**
  * Sets the specified size for this component.
  * @param <code>width</code> the width of this component.
  * @param <code>height</code> the height of this component.
  */
  void setSize(int w, int h);

 /**
  * Gets the preferred size of this component.
  * @return a dimension object indicating this component preferred size.
  */
  Dimension getPreferredSize();

 /**
  * Determines if this component is visible. The component is visible
  * if the visibility flag is <code>true</code>.
  * @return <code>true</code> if the component is visible;
  * <code>false</code> otherwise.
  */
  boolean isVisible();

 /**
  * Gets the bounds of this component. The bounds is represented with <code>java.awt.Rectangle</code> class.
  * @return a rectangle indicating this component's bounds.
  */
  Rectangle getBounds();

 /**
  * Determines the insets of this component. Take care that insets for lightweight
  * components differ from Java AWT components. The lightweight insets
  * defines indents from "left", "top", "right" and "bottom" of the component view.
  * @return an insets of this component.
  */
  Insets getInsets();
}


