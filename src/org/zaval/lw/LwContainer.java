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
 * This interface defines light weight container that inherits the LwComponent interface and
 * can contain other light weight components as child components.
 * <p>
 * The interface inherits LayoutContainer interface so it is possible to use a layout manager
 * to layout its child components.
 */
public interface LwContainer
extends LwComponent, LayoutContainer
{
 /**
  * Adds the specified lightweight component as a child of this container.
  * The method should call <code> componentAdded </code> method its layout manager to inform
  * the layout manager that the new child has been added.
  * @param <code>c</code> the lightweight component to be added.
  */
  void add(LwComponent c);

 /**
  * Adds the specified lightweight component with the specified constraints as a child
  * of this container. The method should call <code> componentAdded </code> method its
  * layout manager to inform the layout manager that the new child has been added.
  * @param <code>s</code> the object expressing layout contraints for this.
  * @param <code>c</code> the lightweight component to be added.
  */
  void add(Object s, LwComponent c);

 /**
  * Inserts the specified lightweight component with the specified constraints as a child
  * of this container at the specified position in the container list. The method should call
  * <code>componentAdded</code> method its layout manager to inform the layout manager
  * that the new child has been added with the given constraints.
  * @param <code>i</code> the position in the container list at which to insert
  * the component.
  * @param <code>s</code> the object expressing layout contraints for this.
  * @param <code>c</code> the lightweight component to be added.
  */
  void insert(int i, Object s, LwComponent c);

 /**
  * Removes the component, specified by the index, from this container.
  * The layout manager of this container should be informed by calling
  * <code>componentRemoved</code> method of the manager.
  * @param <code>index</code> the index of the component to be removed.
  */
  void remove(int i);

 /**
  * Removes all child components from this container.
  * The layout manager of this container should be informed by calling
  * <code>componentRemoved</code> method of the manager for every child component
  * that has been removed.
  */
  void removeAll();

 /**
  * Searches the specified component among this container children and returns
  * an index of the component in the child list. If the component has not been found
  * than the method returns <code>-1</code>.
  * @param <code>c</code> the component to get index.
  * @return a child component index inside the container children list.
  */
  int indexOf(LwComponent c);

 /**
  * Sets the layout manager for this container.
  * @param <code>m</code> the specified layout manager.
  */
  void setLwLayout(LwLayout m);

 /**
  * Gets a layout manager for this container.
  * @return a layout manager.
  */
  LwLayout getLwLayout();

 /**
  * Paints additional elements (for example, marker) after the container and its child components
  * have been rendered.
  * @param <code>g	</code> the graphics context.
  */
  void paintOnTop(Graphics g);
}

