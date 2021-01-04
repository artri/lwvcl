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
import java.awt.event.*;
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This is state button component that allows to customize acceleration for repeatable action
 * event generation.
 */
public class LwSpeedButton
extends LwStButton
{
  private int repeatCount, fastTime = 80, triggerCounter = 3;

 /**
  * Sets the acceleration time.
  * @param <code>ft/code> the acceleration time.
  */
  public void setFastTime(int ft) {
    fastTime = ft;
  }

 /**
  * Sets the trigger counter. The counter defines the number of generated action events
  * after that the fast time should be set as the repeatable time.
  * @param <code>tc/code> the trigger counter.
  */
  public void setTriggerCounter (int tc) {
    triggerCounter = tc;
  }

  public /*C#override*/ void mousePressed (LwMouseEvent e) {
    repeatCount = 0;
    super.mousePressed(e);
  }

  public /*C#override*/ void run ()
  {
    if ((++repeatCount == triggerCounter) && (fastTime > 0))
      Timer.getTimer(false).add (this, 200, fastTime);
    super.run();
  }
}


