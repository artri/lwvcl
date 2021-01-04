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
 * This abstract class is used as base for lightweight renders implementations.
 * The main purpose of a render is to provide face for an object (text, image and
 * so on). So, to create own render it is necessary to inherit the class and
 * implement <code> paint </code> method of the new class.
 */
public abstract class LwRender
extends LwView
{
   private Object target;

  /**
   * Constructs the render with the specified target component that should be rendered.
   * The constructor sets ORIGINAL view type.
   * @param <code>target</code> the target component.
   */
   public LwRender(Object target) {
     this(target, ORIGINAL);
   }

  /**
   * Constructs the render with the specified target component that should be rendered
   * and the specified view type.
   * @param <code>target</code> the target component to be rendered.
   * @param <code>type</code> the specified view type.
   */
   public LwRender(Object target, int type) {
     super(type);
     setTarget (target);
   }

  /**
   * Gets the target object.
   * @return a target object.
   */
   public Object getTarget () {
     return target;
   }

  /**
   * Sets the specified target object.
   * @param <code>o</code> the target object.
   */
   public void setTarget (Object o)
   {
     Object old = target;
     if (target != o)
     {
       target = o;
       targetWasChanged(old, target);
       invalidate();
     }
   }

  /**
   * Invoked whenever the target object has been set.
   * The method can be overrided if the render wants to be notified about
   * re-setting the target object.
   * @param <code>o</code> the old target object.
   * @param <code>n</code> the new target object.
   */
   protected /*C#virtual*/ void targetWasChanged(Object o, Object n) {}
}

