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
 * Defines the interface for classes that know how to layout Layoutable components
 * inside a LayoutContainer container.
 */

public interface LwLayout
{
 /**
  * Invoked when the specified layoutable component is added to the layout container
  * (that uses the layout manager). The specified constraints, layoutable component
  * and child index are passed as arguments into the method.
  * @param <code>id</code> the layoutable component constraints.
  * @param <code>lw</code> the layoutable component that has been added.
  * @param <code>index</code> the child index.
  */
  void componentAdded(Object id, Layoutable lw, int index);

 /**
  * Invoked when the specified layoutable component is removed from the layout
  * container, that uses the layout manager.
  * @param <code>lw</code> the layoutable component that has been removed.
  * @param <code>index</code> the child component index.
  */
  void componentRemoved(Layoutable lw, int index);

 /**
  * Calculates the preferred size dimension for the layout container.
  * The method has to calculate "pure" preferred size, it means that an insets
  * of the container should not be considered.
  * @param <code>target</code> the layout container.
  * @return  a "pure" preferred size.
  */
  Dimension calcPreferredSize(LayoutContainer target);

 /**
  * Lays out the child layoutable components inside the layout container.
  * @param <code>target</code> the layout container that needs to be laid out.
  */
  void layout(LayoutContainer target);
}
