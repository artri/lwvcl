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
 * This is basic interface for components that can contain Layoutable components as child.
 * The lightweight layout managers are defined for a component that implements the interface.
 * Actually, layout managers are used to layout the layout container child components.
 */
public interface LayoutContainer
extends Layoutable
{
 /**
  * Gets a child element at the given index.
  * @param <code>index</code> the index of a child to be returned.
  * @return a child element at the specified index.
  */
  Layoutable get(int index);

 /**
  * Returns the number of child elements in this container.
  * @return a number of child elements in this container.
  */
  int count();

 /**
  * Returns the offset. The offset can be used with a layout manager to offset child components
  * locations of the container according to the offset values. The offset is represented
  * with java.awt.Point class, the <code>x</code> is offset for x-coordinates and <code>y</code>
  * is offset for y-coordinates. The ability to offset child components with a layout manager
  * is used with the library to organize scrolling.
  * @return an offset to move children.
  */
  Point getLayoutOffset();
}


