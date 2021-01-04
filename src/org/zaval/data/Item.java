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
package org.zaval.data;

/**
 * This class is used to represent tree model items. Actually this is very simple stubb
 * that stores a value.
 */

public class Item
{
  private Object value;

 /**
  * Constructs a new item. The item value is set to <code>null</code>.
  */
  public Item() {
    this(null);
  }

 /**
  * Constructs a new item with the given initial value.
  * @param <code>value</code> the initial value of the item value.
  */
  public Item(Object value) {
    setValue(value);
  }

 /**
  * Constructs a new item as a copy of the given item.
  * @param <code>item</code> the given item.
  */
  public Item(Item item) {
    this(item.value);
  }

 /**
  * Gets the value of this item.
  * @return an item value. The value can be <code>null</code>.
  */
  public Object getValue() {
    return value;
  }

  public /*C#override*/ String toString () {
    return "" + value;
  }

 /**
  * Sets the specified value for this item.
  * @param <code>v</code> the specified value.
  */
  protected void setValue (Object v) {
    value = v;
  }
}

