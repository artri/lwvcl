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
 * This class represents tracker event. The event is performed by the tracker component when:
 * a tracker value has been changed, a new bundle has been added, a bundle has been removed,
 * value changing process has been finished, the information event has been performed.
 */
public class LwTrackerEvent
extends org.zaval.util.event.EvObject
{
 /**
  * The tracker value changed event type.
  */
  public static final int VALUE_CHANGED      = 1;

 /**
  * The tracker bundle added event type.
  */
  public static final int BUNDLE_ADDED       = 2;

 /**
  * The tracker bundle removed event type.
  */
  public static final int BUNDLE_REMOVED     = 3;

 /**
  * The tracker info performed event type.
  */
  public static final int VALUE_INFO         = 4;

 /**
  * The tracker value changing done event type.
  */
  public static final int VALUE_CHANGED_DONE = 5;

  private int bundleIndex, value, prevValue;

 /**
  * Constructs the event object with the specified source object, the bundle index,
  * the bundle value, the bundle previous value an the event id.
  * @param <code>target</code> the object where the event originated.
  * @param <code>bi</code> the specified bundle id. The value can be <code>-1</code> if
  * the bundle element is not specified.
  * @param <code>bv</code> the bundle value.
  * @param <code>obv</code> the previous bundle value.
  * @param <code>id</code> the specified event id.
  */
  public LwTrackerEvent(LwComponent target, int bi, int bv, int obv, int id)
  {
    super(target, id);
    bundleIndex = bi;
    value       = bv;
    prevValue   = obv;
  }

 /**
  * Gets the value (for the specified by the event tracker bundle element).
  * @return a value.
  */
  public int getValue () {
    return value;
  }

 /**
  * Gets the previous value (for the specified by the event tracker bundle element).
  * @return a previous value.
  */
  public int getPrevValue () {
    return prevValue;
  }

 /**
  * Gets the tracker bundle index.
  * @return a tracker bundle index.
  */
  public int getBundleIndex () {
    return bundleIndex;
  }

  protected boolean checkID(int id) {
    return id == VALUE_CHANGED || id == BUNDLE_ADDED || id == BUNDLE_REMOVED || id == VALUE_INFO || id == VALUE_CHANGED_DONE;
  }
}



