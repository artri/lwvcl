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
 * This is basic interface for GUI components of the light weight library. Every GUI components of the library should
 * implement the interface. Actually the interface is analog of java.awt.Component class that
 * is provided with Java AWT library. The lightweight library provides an implementation of the
 * interface - LwCanvas - that is more handly for usage. To create lightweight components
 * just to inherit the class. The another variation of the interface is lightweight container
 * interface - LwContainer. The interface extends the component interface and has implementation
 * to create own lightweight containers - LwPanel.
 */
public interface LwComponent
extends Drawable
{
  /**
   * Returns the bounding rectangle of the visible part for the component.
   * @return a visible part bounding rectangle.
   */
   Rectangle getVisiblePart();

  /**
   * Determines if the component or an immediate child component contains the
   * (x,&nbsp;y) location in its visible part and if so, returns the component.
   * @param     <code>x</code> the x coordinate.
   * @param     <code>y</code> the y coordinate.
   * @return    the component or subcomponent that contains the (x,&nbsp;y) location;
   *            <code>null</code> if the location is outside this component.
   */
   LwComponent getLwComponentAt(int x, int y);

  /**
   * Gets the lightweight parent of this component. It is supposed that the parent implements
   * LwContainer interface.
   * @return the parent container of this component.
   */
   LwComponent getLwParent();

  /**
   * Sets the lightweight parent of this component. It is supposed that the parent implements
   * LwContainer interface. The method is provided for lightweight core usage to support components
   * hierarchy, not for applications that base on the library. Don't touch
   * the method. 
   * @param <code>p</code> the specified parent container.
   * @return a parent component of this lightweight component.
   */
   void setLwParent(LwComponent p);

  /**
   * Enables or disables this component. An enabled component can participate
   * in events handling and performing processes. Component is enabled initially
   * by default.
   * @param  <code>b</code> if the value is <code>true</code> - enables the component;
   * otherwise disables this component.
   */
   void setEnabled(boolean b);

  /**
   * Shows or hides this lightweight component depending on the value of parameter
   * <code>b</code>.
   * @param <code>b</code> if it is <code>true</code>, shows this component;
   * otherwise, hides this component.
   */
   void setVisible(boolean b);

  /**
   * Sets the background color of this component. The color is going to be used to
   * fill the component background.
   * @param <code>c</code> the color to become this component background color.
   */
   void setBackground(Color c);

  /**
   * Returns a view manager of the component. The view manager can be <code>null</code>.
   * The input argument <code>autoCreate</code> defines if the view manager has to be created
   * automatically in a case if it has not been determined before. It means, if the argument is
   * <code>true</code> and the view manager is <code>null</code>, than the component will
   * try to create and initialize its view manager by a default view manager. If the argument
   * is <code>false</code> than the method returns the component view manager as is.
   * @param <code>autoCreate</code> the flag defines if the view manager should be created
   * automatically.
   * @return a view manager for the component.
   */
   LwViewMan getViewMan(boolean autoCreate);

  /**
   * Sets the specified view manager for the component. The view manager defines set of views
   * that are used as a part of the component face.
   * @param <code>man</code> the view manager to set for the component.
   */
   void setViewMan(LwViewMan man);

  /**
   * Specifies if the component can have focus.
   * @return <code>true</code> if the component can have the focus.
   */
   boolean canHaveFocus();
}
