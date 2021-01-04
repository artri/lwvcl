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

import org.zaval.lw.event.*;

/**
 * This abstract class can be used to control state of switching components.
 * The switching component is a component that can have one of the following two states: "off" or "on".
 * The class provides ability to control state of the component by <code>getState</code>
 * <code>setState</code> methods. The abstract methods have to be implemented.
 * <p>
 * It is possible to organize combined action for a set of switching components. For example,
 * if we have group of switching components where only one component can be "on" at the given
 * moment than we can use the class to implement appropriate switch manager. First of all we
 * should have ability to register and unregister the components as a members of the set.
 * For the purpose the class provides
 * <code>install</code> and <code>uninstall</code> methods. Than we should implement
 * <code>getState</code> and <code>setState</code> abstract methods. The input argument
 * for <code>install</code>, <code>uninstall</code> and <code>setState</code> methods is Switchable
 * interface. The interface has to be used as a callback interface to notify a switching
 * component by calling <code>switchedOff</code> and <code>switchedOn</code> methods about
 * changing state of the switching component.
 * <p>
 * The switch manager has to perform action events when a state has been changed. Use
 * <code>addActionListener</code> and <code>removeActionListener</code> to register and
 * unregister listeners of the events.
 */
public abstract class LwSwitchManager
{
  private LwActionSupport support;

 /**
  * Registers the specified switching component that wants to use the manager.
  * @param <code>o</code> the specified switching component.
  */
  public /*C#virtual*/ void install (Switchable o) {
    if (getState(o)) o.switchedOn();
    else             o.switchedOff();
  }

 /**
  * Unregisters the specified switching component that doesn't want to use the manager.
  * @param <code>o</code> the specified switching component.
  */
  public /*C#virtual*/ void uninstall(Switchable o) {}

 /**
  * Adds the specified action listener to receive action events from this manager.
  * @param <code>a</code> the specified action listener.
  */
  public void addActionListener(LwActionListener a) {
    if (support == null) support = new LwActionSupport();
    support.addListener(a);
  }

 /**
  * Removes the specified action listener so it no longer receives action events
  * from this manager.
  * @param <code>a</code> the specified action listener.
  */
  public void removeActionListener(LwActionListener a) {
    if (support != null) support.removeListener(a);
  }

 /**
  * Fires the action event for the list of LwActionListener with the specified switching
  * component as the event source.
  * @param <code>s</code> the specified switching component.
  */
  protected /*C#virtual*/ void perform(Switchable s) {
    if (support != null) support.perform(new LwActionEvent(s));
  }

 /**
  * Gets state for the specified switching component.
  * @param <code>o</code> the specified switching component.
  * @return <code>true</code> if the switching component has "on" state, <code>false</code>
  * if it has "off" state.
  */
  public abstract boolean getState(Switchable o);

 /**
  * Sets the specified state for the switching component.
  * @param <code>o</code> the specified switching component.
  * @param <code>b</code> the specified state. The <code>true</code> value corresponds to
  * "on" state and <code>false</code> value corresponds to "off" state.
  */
  public abstract void setState(Switchable o, boolean b);
}


