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

/**
 * This layer interface is a special container that is used as the LwDesktop children.
 * Desktop consists of the layers. Every layer is laid out by the desktop to place all
 * desktop room. When an event is performed the desktop "asks" the layers starting from the
 * tail layer if it is ready to get the event and delivers the event to the first-found
 * layer. The layered structure of the desktop allows to implement additional features
 * (for example, internal frame) basing on the LwVCL level.
 */
public interface LwLayer
extends LwContainer
{
  /**
   * Gets the layer id.
   * @return the layer id
   */
   Object getID ();

  /**
   * Invoked whenever the desktop lost focus.
   */
   void releaseFocus();

   void setupFocus();

  /**
   * Invoked whenever a mouse button has been pressed.
   * @param <code>x</code> the x coordinate of the mouse pointer.
   * @param <code>y</code> the y coordinate of the mouse pointer.
   * @param <code>mask</code> the mask that specifies mouse buttons states.
   */
   void mousePressed(int x, int y, int mask);

  /**
   * Invoked whenever a key has been pressed.
   * @param <code>keyCode</code> the key code.
   * @param <code>mask</code> the keyboard trigger keys states.
   */
   void keyPressed  (int keyCode, int mask);

  /**
   * Gets if the layer is active.
   * @return <code>true</code> if the layer is active.
   */
   boolean isActive();

  /**
   * Gets if the focus root component.
   * @return a focus root component.
   */
   LwComponent getFocusRoot();
}
