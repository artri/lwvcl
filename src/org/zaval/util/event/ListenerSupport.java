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
package org.zaval.util.event;

import java.util.*;

/**
 * This abstract, utility class can be inherited with a classes that implement:
 * <ul>
 *   <li>
 *     Supporting list of listeners with a given type. Use <code>addListener</code>
 *     and <code>removeListener</code> methods to control the list content.
 *   </li>
 *   <li>
 *     Delivering events with a given type to the listeners. First of all, implement
 *     <code>perform(java.util.EventListener, java.util.EventObject)</code> method that
 *     determines how the specified event should be delivered to the listener and
 *     after that you can use <code>perform(java.util.EventObject)</code> to delivery
 *     the specified event to all listeners of the list.
 *   </li>
 *  </ul>
 */
public abstract class ListenerSupport
{
   private Vector v;

  /**
   * Adds the specified listener to the listeners list.
   * @param <code>l</code> the specified listener.
   */
   public void addListener(EventListener l)  {
     if (v == null) v = new Vector(1);
     if (!v.contains(l)) v.addElement(l);
   }

  /**
   * Removes the specified listener from the listeners list.
   * @param <code>l</code> the specified listener.
   */
   public void removeListener(EventListener l)
   {
     if (v != null && v.contains(l))
       v.removeElement(l);
   }

   public void clear() {
     if (v != null) v.removeAllElements();
   }

  /**
   * Fires the specified event to every member of the listeners list.
   * The method uses <code>perform(java.util.EventListener, java.util.EventObject)</code>
   * method to delivery the event to every listener.
   * @param <code>e</code> the specified event.
   */
   public void perform(EventObject e)
   {
     if (v != null)
       for (int i=0; i<v.size(); i++) perform((EventListener)v.elementAt(i), e);
   }

 /**
  * The method knows how the specified event has to be delivered to an appropriate
  * method of the specified listener.
  * @param <code>l</code> the specified listener.
  * @param <code>e</code> the specified event.
  */
   public abstract void perform(EventListener l, EventObject e);
}
