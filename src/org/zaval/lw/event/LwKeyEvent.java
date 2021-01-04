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

import org.zaval.lw.*;

/**
 * This class is used with light weight components to describe keyboard events.
 */
public class LwKeyEvent
extends LwAWTEvent
{
 /**
  * The key typed event type.
  */
  public static final int KEY_TYPED     = 512;

 /**
  * The key released event type.
  */
  public static final int KEY_RELEASED  = 1024;

 /**
  * The key pressed event type.
  */
  public static final int KEY_PRESSED   = 2048;

  private int code, mask;
  private char ch;

 /**
  * Constructs the event object with the specified source object, the event id,
  * the key code associated with the key, the character that has been typed with
  * the key and the mask that describes state of a keyboard.
  * @param <code>target</code> the object where the event originated.
  * @param <code>id</code> the specified event id.
  * @param <code>code</code> the specified key code associated with the key.
  * @param <code>ch</code> the specified key character.
  * @param <code>mask</code> the specified key mask. Using the mask you can define for example
  * status of "Ctrl", "Alt", "Shift" and so on keys.
  */
  public LwKeyEvent(LwComponent target, int id, int code, char ch, int mask)
  {
    super(target, id);
    reset (target, id, code, ch, mask);
  }

  public void reset (LwComponent target, int id, int code, char ch, int mask)
  {
    source    = target;
    this.id   = id;
    this.code = code;
    this.mask = mask;
    this.ch   = ch;
  }

 /**
  * Gets the key code associated with the key in the event.
  * @return a key code.
  */
  public int getKeyCode () {
    return code;
  }

 /**
  * Gets the key character that has been typed.
  * @return a key character.
  */
  public char getKeyChar () {
    return ch;
  }

 /**
  * Gets the keyboard mask. The mask can be used to test if the "Ctrl", "Alt"
  * and so on keys are pressed or not. Use java.awt.event.InputEvent key modifiers constants
  * for the purpose.
  * @return a keyboard mask.
  */
  public int getMask(){
    return mask;
  }

 /**
  * Gets the UID of the event. The class uses LwAWTEvent.KEY_UID as the event UID.
  * @return an UID.
  */
  public /*C#override*/ int getUID() {
    return KEY_UID;
  }
}


