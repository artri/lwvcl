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

/**
 * This is implementation of the light weight container interface that has to be used
 * to develop own light weight containers. The container implementation inherits
 * the light weight component implementation. Draw attention at the following features:
 * <ul>
 *   <li>
 *     The implementation uses a layout manager to layout child components. The layout
 *     manager cannot be <code>null</code>.
 *   </li>
 *   <li>
 *     You should not define a preferred size for the container by
 *     <code>calcPreferredSize</code> method as it needs for LwCanvas,
 *     because the container preferred size is determined by a layout manager.
 *   </li>
 *   <li>
 *     You should not override <code>recalc</code> method as it needs for LwCanvas,
 *     because the container preferred size is determined by a layout manager.
 *   </li>
 *   <li>
 *     This implementation of the container uses LwRastLayout as default layout manager.
 *     The layout manager uses original sizes and locations (that have been set by
 *     <code>setSize</code> and <code>setLocation</code> methods of the components)
 *     to layout child components.
 *   </li>
 * </ul>
 */
public class LwPanel
extends LwCanvas
implements LwContainer
{
  protected Vector children;
  private LwLayout layout;
  private int cpsWidth = -1, cpsHeight;

 /**
  * Constructs the container.
  */
  public LwPanel() {
    setLwLayout(getDefaultLayout());
  }

  public /*C#virtual*/ void paintOnTop(Graphics g) {}

 /**
  * Adds the specified lightweight component with the specified constraints as a child
  * of this container. The method calls <code> componentAdded </code> method its
  * layout manager to inform the layout manager that the new child has been added with
  * the given constraints.
  * @param <code>s</code> the object expressing layout constraints for this.
  * @param <code>c</code> the lightweight component to be added.
  */
  public void add(Object s, LwComponent c) {
    insert(count(), s, c);
  }

 /**
  * Adds the specified lightweight component as a child of this container.
  * The method calls <code> componentAdded </code> method its layout manager to inform
  * the layout manager that the new child has been added.
  * @param <code>c</code> the lightweight component to be added.
  */
  public void add(LwComponent c) {
    insert(count(), null, c);
  }

 /**
  * Inserts the specified lightweight component as a child of this container
  * at the specified position in the container list. The method calls
  * <code>componentAdded</code> method its layout manager to inform the layout manager
  * that the new child has been added.
  * @param <code>i</code> the position in the container list at which to insert
  * the component.
  * @param <code>d</code> the lightweight component to be added.
  */
  public void insert(int i, LwComponent d) {
    insert(i, null, d);
  }

 /**
  * Inserts the specified lightweight component with the specified constraints as a child
  * of this container at the specified position in the container list. The method calls
  * <code>componentAdded</code> method its layout manager to inform the layout manager
  * that the new child has been added with the given constraints.
  * @param <code>i</code> the position in the container list at which to insert
  * the component.
  * @param <code>s</code> the object expressing layout contraints for this.
  * @param <code>d</code> the lightweight component to be added.
  */
  public /*C#virtual*/ void insert(int i, Object s, LwComponent d) {
    insert(this, i, s, d);
  }

 /**
  * Removes the specified component from this container.
  * The layout manager of this container is informed by calling
  * <code>componentRemoved</code> method of the manager.
  * @param <code>c</code> the specified component to be removed.
  */
  public void remove(LwComponent c) {
    remove(indexOf(c));
  }

 /**
  * Removes the component at the specified index from this container.
  * The layout manager of this container is informed by calling
  * <code>componentRemoved</code> method of the manager.
  * @param <code>i</code> the index of the component to be removed.
  */
  public /*C#virtual*/ void remove(int i) {
    remove(this, i);
  }

 /**
  * Removes all child components from this container.
  * The layout manager of this container is informed by calling
  * <code>componentRemoved</code> method of the manager for every child that has been
  * removed.
  */
  public /*C#virtual*/ void removeAll() {
    removeAll(this);
  }

 /**
  * Searches the specified component among this container children and returns
  * an index of the component in the child list. If the component has not been found
  * than the method returns -1.
  * @param <code>c</code> the component to get index.
  * @return the child index.
  */
  public int indexOf(LwComponent c) {
    return children == null?-1:children.indexOf(c);
  }

 /**
  * Gets a child component at the given index.
  * @param <code>i</code> the specified index of the child to be returned.
  * @return a child component at the specified index.
  */
  public Layoutable get(int i) {
    return (Layoutable)children.elementAt(i);
  }

 /**
  * Returns the number of child components in this container.
  * @return a number of child components in this container.
  */
  public int count() {
    return children == null?0:children.size();
  }

 /**
  * Sets the layout manager for this container. It is impossible to use <code>null</code>
  * as a layout manager, in this case IllegalArgumentException will be thrown.
  * @param <code>m</code> the specified layout manager.
  */
  public /*C#virtual*/ void setLwLayout(LwLayout m)
  {
    if (m == null) throw new IllegalArgumentException();
    if (layout != m)
    {
      LwLayout prev = layout;
      layout = m;
      invalidate();
    }
  }

 /**
  * Gets the layout manager of this container.
  * @return a layout manager.
  */
  public LwLayout getLwLayout() {
    return layout;
  }

 /**
  * Sets the background color of this container. Additionally the method sets the background color
  * for every child component.
  * @param <code>c</code> the color to become this component background color.
  */
  public /*C#override*/ void setBackground(Color c)
  {
    super.setBackground(c);
    if (children != null)
      for (int i=0; i<children.size(); i++)
        ((LwComponent)children.elementAt(i)).setBackground(c);
  }

 /**
  * Sets the opaque of this component. Use <code> false </code>
  * argument value to make a transparent component from this component.
  * Additionally the method sets the specified opaque for every child component.
  * @param  <code>b</code> the opaque flag.
  */
  public /*C#override*/ void setOpaque(boolean b)
  {
    super.setOpaque(b);
    if (children != null)
      for (int i=0; i<children.size(); i++)
        ((LwComponent)children.elementAt(i)).setOpaque(b);
  }

 /**
  * Determines if the component or an immediate child component contains the
  * (xx,&nbsp;yy) location in its visible part and if so, returns the component.
  * @param     <code>xx</code> the x coordinate.
  * @param     <code>yy</code> the y coordinate.
  * @return    the component or sub-component that contains the (x,&nbsp;y) location;
  *            <code>null</code> if the location is outside of this component visible part.
  */
  public /*C#override*/ LwComponent getLwComponentAt(int xx, int yy)
  {
    LwComponent c = super.getLwComponentAt(xx, yy);
    if (children != null && c != null)
    {
      for (int i=children.size()-1;i >= 0;i--)
      {
        LwComponent d = (LwComponent)children.elementAt(i);
        LwComponent r = d.getLwComponentAt(xx - d.getX(), yy - d.getY());
        if (r != null) return r;
      }
    }
    return c;
  }

 /**
  * Sets the specified child component to be painted over other child components.
  * @param  <code>c</code> the specified child component.
  */
  public void toFront(LwComponent c)
  {
    int index = indexOf (c);
    if (index < 0) throw new IllegalArgumentException();
    if (index < (count()-1))
    {
      children.removeElementAt(index);
      children.addElement(c);
      repaint(c.getX(), c.getY(), c.getWidth(), c.getHeight());
    }
  }

/*  public void validate () {
    if (isVisible() && width > 0 && height > 0) super.validate();
  }*/

 /**
  * Invoked with <code>validate</code> method only if the component is not valid.
  * The method should not be overrided for this container like it can be done for LwCanvas,
  * because the method starts validating and layouting of the child components.
  */
  protected /*C#override*/ void recalc ()
  {
    layout.layout(this);
    if (children != null)
      for (int i=0; i<children.size(); i++)
        ((LwComponent)children.elementAt(i)).validate();
    isValidFlag = true;
  }

 /**
  * Returns the offset. The offset can be used with a layout manager to offset child components
  * locations of the container according to the offset values. The offset is represented
  * with java.awt.Point class, the <code>x</code> is offset for x-coordinates and <code>y</code>
  * is offset for y-coordinates. The ability to offset child components with a layout manager
  * is used with the library to organize scrolling.
  * @return an offset to move children.
  */
  public /*C#virtual*/ Point getLayoutOffset() {
    return new Point();
  }

  public /*C#override*/ void invalidate() {
    cpsWidth = -1;
    super.invalidate();
  }

 /**
  * Gets the "pure" preferred size for this component. The method has not be overrided,
  * because it determines the preferred size by the layout manager.
  * @return a "pure" preferred size.
  */
  protected /*C#override*/ Dimension calcPreferredSize()
  {
    if (cpsWidth < 0)
    {
      Dimension d = layout.calcPreferredSize(this);
      cpsWidth  = d.width;
      cpsHeight = d.height;
      return d;
    }
    else return new Dimension(cpsWidth, cpsHeight);
  }

 /**
  * Gets the default layout manager that is set with the container during initialization.
  * This implementation of the method returns LwRastLayout as the default layout manager, the
  * layout manager is got as a static object by "layout.raster" key.
  * @return a layout manager.
  */
  protected /*C#virtual*/ LwLayout getDefaultLayout() {
    return (LwLayout)LwToolkit.getStaticObj("layout.raster");
  }

  protected /*C#virtual*/ void updateCashedPs(int w, int h) {
    if (w >= 0) cpsWidth  = w;
    if (h >= 0) cpsHeight = h;
  }

  private static void insert(LwPanel c, int i, Object s, LwComponent d)
  {
    d.setLwParent(c);
    if (c.children == null) c.children = new Vector(5);
    c.children.insertElementAt(d, i);
    c.layout.componentAdded(s, d, i);
    LwToolkit.getEventManager().perform(new LwContainerEvent(d, c, LwContainerEvent.COMP_ADDED));
    c.invalidate();
  }

  private static void remove(LwPanel c, int i)
  {
    if (c.children != null)
    {
      LwComponent obj = (LwComponent)c.children.elementAt(i);
      obj.setLwParent(null);
      c.children.removeElementAt(i);
      c.layout.componentRemoved(obj, i);
      LwToolkit.getEventManager().perform(new LwContainerEvent(obj, c, LwContainerEvent.COMP_REMOVED));
      c.vrp();
    }
  }

  private static void removeAll(LwPanel c)
  {
    if (c.count() > 0)
    {
      int size = c.children.size();
      for (;size>0;size--)
      {
        LwComponent child = (LwComponent)c.children.elementAt(size-1);
        c.children.removeElementAt(size-1);
        c.layout.componentRemoved(child, size-1);
        LwToolkit.getEventManager().perform(new LwContainerEvent(child, c, LwContainerEvent.COMP_REMOVED));
      }
      c.vrp();
    }
  }
}

