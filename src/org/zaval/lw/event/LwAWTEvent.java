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
import org.zaval.lw.*;

/**
 * This class is basic for all light weight events. Every light event class that inherits the
 * class has to provide:
 * <ul>
 *   <li>The event id by <code>getID</code> method.</li>
 *   <li>
 *     The event class UID by <code>getUID</code> method. The event class UID defines type of the
 *     event, for example LwMouseEvent has LwAWTEvent.MOUSE_UID UID for all ids.
 *   </li>
 * </ul>
 */
public class LwAWTEvent
extends java.util.EventObject
{
 /**
  * The component event UID.
  */
  public static final int COMP_UID   = 1;

 /**
  * The mouse event UID.
  */
  public static final int MOUSE_UID  = 2;

 /**
  * The key event UID.
  */
  public static final int KEY_UID    = 4;

 /**
  * The focus event UID.
  */
  public static final int FOCUS_UID  = 8;

 /**
  * The container event UID.
  */
  public static final int CONT_UID   = 16;

 /**
  * The mouse motion event UID.
  */
  public static final int MOTION_UID = 32;

 /**
  * The window event UID.
  */
  public static final int WIN_UID = 64;

 /**
  * The event id.
  */
  protected int id;

 /**
  * Constructs an event object with the specified source object and the event id.
  * @param <code>target</code> the object where the event originated.
  * @param <code>id</code> the specified event id.
  */
  public LwAWTEvent(LwComponent target, int id) {
    super(target);
    this.id = id;
  }

 /**
  * Gets the event id.
  * @return an event id.
  */
  public int getID() {
    return id;
  }

 /**
  * Gets the source object of the event as LwComponent instance.
  * @return a source object as LwComponent instance.
  */
  public LwComponent getLwComponent() {
    return (LwComponent)getSource();
  }

 /**
  * Gets the UID of the event. The UID defines the event class.
  * @return an UID.
  */
  public /*C#virtual*/ int getUID () {
    return -1;
  }
}
