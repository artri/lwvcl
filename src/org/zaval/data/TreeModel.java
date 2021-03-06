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

import org.zaval.data.event.*;

/**
 * This interface is used to manipulate tree-like data structures. The interface provides
 * following three abilities:
 * <ul>
 *   <li>Supporting tree-hierarchy</li>
 *   <li>Modifying tree-hierarchy</li>
 *   <li>Listening when the tree-hierarchy has been changed</li>
 * </ul>
 */
public interface TreeModel
{
 /**
  * Gets the root item of the tree model.
  * @return a root item of the tree model.
  */
  Item getRoot();

 /**
  * Gets the total items number that are contained by the tree.
  * @return a total items number.
  */
  int getItemsCount();

 /**
  * Returns a parent for the given item.
  * @param <code>item</code> the specified item to define it parent.
  * @return a parent item of the child item.
  */
  Item getParent (Item item);

 /**
  * Returns a child item for the given parent item at the specified index.
  * @param <code>item</code> the item that is used as a parent to get a child item by the index.
  * @param <code>index</code> the index into this parent child items vector.
  * @return the item at the specified index.
  */
  Item getChildAt(Item item, int index);

 /**
  * Searches for the first occurence of the given item in the parent items vector,
  * testing for equality using the <code>equals</code> method and returns the child index.
  * @param <code>item</code> the specified item to define it index.
  * @return the index of the first occurrence of the item in this parent items
  *         vector. Returns <code>-1</code> if the item is not member of the model.
  */
  int getChildIndex(Item item);

 /**
  * Returns a number of child items for the given parent item.
  * @param <code>item</code> the parent item to define a number of child items.
  * @return a number of child items. Actually the method gets a size of the item
  * child vector.
  */
  int getChildrenCount(Item item);

 /**
  * Tests if the item has one or more child items.
  * @param <code>item</code> the item to test.
  * @return <code>true</code> if the item has one or more child items; <code>false</code>
  * otherwise
  */
  boolean hasChildren(Item item);

 /**
  * Adds the item into the tree as a child of the parent item.
  * The parent item has to belong to the tree.
  * @param <code>to</code>  the parent item.
  * @param <code>item</code> the child item that has to be added.
  */
  void add(Item to, Item item);

 /**
  * Inserts the specified item into the tree as a child of the parent item,
  * at the specified index. The parent item has to belong to the tree.
  * @param <code>to</code>  the specified parent item.
  * @param <code>item</code> the specified child item that has to be added.
  * @param <code>index</code> the specified index where the child has to be inserted.
  */
  void insert(Item to, Item item, int index);

 /**
  * Sets the specified value for the given tree item.
  * @param <code>item</code> the item.
  * @param <code>o</code> the specified value.
  */
  void set(Item item, Object value);

 /**
  * Removes the specified item from the tree. If the item has child items than the children
  * will be removed too. The method is recursive to remove all hierarchy that is bound with
  * this item.
  * @param <code>item</code> the item that has to be removed.
  */
  void remove(Item item);

 /**
  * Adds the specified tree listener to receive the tree events.
  * @param <code>l</code> the tree listener.
  */
  void addTreeListener (TreeListener l);

 /**
  * Removes the specified tree listener so that it no longer
  * receives tree events from this tree model.
  * @param <code>l</code> the tree listener.
  */
  void removeTreeListener(TreeListener l);
}

