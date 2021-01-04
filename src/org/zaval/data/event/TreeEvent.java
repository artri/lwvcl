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
package org.zaval.data.event;

import org.zaval.data.*;

/**
 * This class describes event that is performed by <code>TreeModel</code> class implementation.
 * Using the event class the tree model notifies listeners about the structure changes.
 */
public class TreeEvent
extends org.zaval.util.event.EvObject
{
 /**
  * The item inserted event type.
  */
  public static final int INSERTED = 1;

 /**
  * The item removed event type.
  */
  public static final int REMOVED  = 2;

 /**
  * The item modified event type.
  */
  public static final int MODIFIED = 3;

  private Item item;

 /**
  * Constructs a new tree model event with the given source, event id and item.
  * The event id defines type of the event and the item is an item that has been
  * inserted, modified or removed.
  * @param <code>src</code> the source of the event.
  * @param <code>id</code> the type of the event.
  * @param <code>item</code> the item.
  */
  public TreeEvent(Object src, int id, Item item) {
    super(src, id);
    this.item = item;
  }

 /**
  * Gets the item that has been modified, inserted or added.
  * @return an item.
  */
  public Item getItem () {
    return item;
  }

  protected /*C#override*/ boolean checkID (int id) {
    return (id == INSERTED || id == REMOVED || id == MODIFIED);
  }
}
