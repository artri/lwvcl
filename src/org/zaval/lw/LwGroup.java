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
 * This implementation of the switch manager class is used to control state for a set of switching
 * components where only one component can be "on". The library uses the manager to organize
 * radio groups. The example below shows the manager usage for the purpose:
 * <pre>
 *    ...
 *    LwCheckbox ch1 = new LwCheckbox("Radio 1");
 *    LwCheckbox ch2 = new LwCheckbox("Radio 3");
 *    LwCheckbox ch3 = new LwCheckbox("Radio 3");
 *    LwGroup    group = new LwGroup();
 *    ch1.setSwitchManager(group);
 *    ch2.setSwitchManager(group);
 *    ch3.setSwitchManager(group);
 *    ...
 * </pre>
 */
public class LwGroup
extends LwSwitchManager
{
   private Switchable selected;

  /**
   * Gets the switching component that has "on" state for the group.
   * @return a selected switching component.
   */
   public Switchable getSelected() {
     return selected;
   }

  /**
   * Sets the specified switching component as selected component. In this case
   * the old selected component will be switched off. Use <code>null</code> if none of the components
   * need to be selected.
   * @param <code>s</code> the specified switching component.
   */
   public void setSelected(Switchable s) {
     if (s != null) setState(s, true);
     else           clearSelected();
   }

   public /*C#override*/ boolean getState(Switchable o) {
     return o == selected;
   }

   public /*C#override*/ void setState(Switchable o, boolean b)
   {
     if (b)
     {
       clearSelected();
       selected = o;
       if (selected != null)  selected.switchedOn();
       perform (selected);
     }
   }

   private void clearSelected()
   {
     if (selected != null)
     {
       Switchable old = selected;
       selected = null;
       old.switchedOff();
       perform (old);
     }
   }
}
