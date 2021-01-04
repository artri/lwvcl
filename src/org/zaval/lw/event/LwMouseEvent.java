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
package org.zaval.lw.event;

import java.awt.*;
import java.awt.event.*;
import org.zaval.lw.*;

/**
 * This class is used with light weight components to describe mouse events.
 */
public class LwMouseEvent
extends LwAWTEvent
{
 /**
  * The mouse clicked event type.
  */
  public static final int MOUSE_CLICKED = 1;

 /**
  * The mouse pressed event type.
  */
  public static final int MOUSE_PRESSED  = 2;

 /**
  * The mouse released event type.
  */
  public static final int MOUSE_RELEASED = 4;

 /**
  * The mouse entered event type.
  */
  public static final int MOUSE_ENTERED  = 8;

 /**
  * The mouse exited event type.
  */
  public static final int MOUSE_EXITED   = 16;

  protected int x, y, ax, ay, mask, clickCount;

 /**
  * Constructs the event object with the specified source object, the event id,
  * the absolute mouse pointer location, mask and click count. The location defines x and
  * y coordinates relatively the desktop parent component of the light weight source component.
  * @param <code>target</code> the object where the event originated.
  * @param <code>id</code> the specified event id.
  * @param <code>ax</code> the x coordinate relatively the desktop parent component.
  * @param <code>ay</code> the y coordinate relatively the desktop parent component.
  * @param <code>mask</code> the specified mask. The mask can be used to check trigger keys
  * status.
  * @param <code>clickCount</code> the number of consecutive clicks.
  */
  public LwMouseEvent(LwComponent target, int id, int ax, int ay, int mask, int clickCount) {
    super(target, id);
    reset(target, id, ax, ay, mask, clickCount);
  }

  public /*C#virtual*/ void reset(LwComponent target, int id, int ax, int ay, int mask, int clickCount)
  {
    source = target;
    this.id = id;
    this.ax = ax;
    this.ay = ay;
    this.mask = mask;
    this.clickCount = clickCount;
    Point p = LwToolkit.getRelLocation(ax, ay, target);
    x = p.x;
    y = p.y;
  }


 /**
  * Gets the mask. The mask can be used to test if the "Ctrl", "Alt"
  * and so on keys are pressed or not. Use java.awt.event.InputEvent key modifiers constants
  * for the purpose.
  * @return a mask.
  */
  public int getMask(){
    return mask;
  }

 /**
  * Gets the x coordinate of the mouse pointer relatively the desktop parent component.
  * @return a x coordinate.
  */
  public int getAbsX() {
    return ax;
  }

 /**
  * Gets the y coordinate of the mouse pointer relatively the desktop parent component.
  * @return an y coordinate.
  */
  public int getAbsY() {
    return ay;
  }

 /**
  * Gets the x coordinate of the mouse pointer relatively the source component.
  * @return a x coordinate.
  */
  public int getX() {
    return x;
  }

 /**
  * Gets the y coordinate of the mouse pointer relatively the source component.
  * @return an y coordinate.
  */
  public int getY() {
    return y;
  }

 /**
  * Gets the UID of the event. The class uses LwAWTEvent.MOUSE_UID as the event UID.
  * @return an UID.
  */
  public /*C#override*/ int getUID() {
    return LwAWTEvent.MOUSE_UID;
  }

 /**
  * Gets the number of consecutive mouse clicks.
  * @return a number of consecutive mouse clicks.
  */
  public int getClickCount() {
    return clickCount;
  }
}

