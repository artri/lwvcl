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
package org.zaval.misc;

import java.awt.*;

/**
 * This interface is used to describe and control an object that can be scrolled.
 * Principally, there are two ways to organize the scrolling for a light weight
 * component:
 * <ul>
 *   <li>
 *     By moving the component (changing the component location). For example, if we want
 *     to organize scrolling for java.awt.Component, we should use <code>setLocation</code>
 *     method to scroll the component inside a parent container.
 *   </li>
 *   <li>
 *     By moving the component view. For example, if we want to create scrolling
 *     for java.awt.Component, we should override <code>paint</code> method
 *     of the component and move view inside the method basing on the scrolling offsets.
 *   </li>
 * </ul>
 */
public interface ScrollObj
{
 /**
  * Gets the scroll object location.
  * @return a scroll object location.
  */
  Point getSOLocation();

 /**
  * Sets the specified scroll object location. The method defines a mechanism that will be used to
  * scrool the object.
  * @param <code>x</code> the specified x coordinate.
  * @param <code>y</code> the specified y coordinate.
  */
  void setSOLocation(int x, int y);

 /**
  * Gets the scroll object size. The size is a size that the scroll object wants to have.
  * @return a scroll object size.
  */
  Dimension getSOSize();

 /**
  * Sets the specified scroll manager for the scroll object. The manager reference should
  * be used with the scroll object to notify when the scroll object has been moved or resized.
  * @param <code>m</code> the specified scroll manager.
  */
  void setScrollMan(ScrollMan m);

 /**
  * Tests if the scroll component performs scrolling by changing its location or moving view.
  * @return <code>true</code> if the scroll component organizes scrolling by moving
  * its view; otherwise <code>false</code>.
  */
  boolean moveContent();
}
