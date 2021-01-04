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

import java.util.*;
import org.zaval.data.event.*;
import org.zaval.util.event.*;

/**
 * This class is implementation of <code>TreeModel</code> interface and it is used to organize
 * tree-like structures basing on <code>Item</code> class. The class provides methods to bound
 * items with each other The class supports listener interface that allows to listen when
 * the structure has been modified.
 */
public class Tree
implements TreeModel
{
  private Item      root;
  private Hashtable elements = new Hashtable();
  private Vector    support;

 /**
  * Constructs a new tree class. The constructur defines a root item as default.
  */
  public Tree() {
    this(new Item());
  }

 /**
  * Constructs a new tree with the given item as a root of the hierarchy.
  * @param <code>r</code> the initial value of the container root. The root item
  * will be used as root of the hierarchy. Use <code>null</code> value to postpone
  * the root item definition.
  */
  public Tree(Item r) {
    setRoot(r);
  }

  public void setRoot (Item r)
  {
    if (root != null || r == null) throw new IllegalArgumentException();
    root = r;
    regItem(r, null, null);
  }

  public Item getRoot() {
    return root;
  }

  public int getItemsCount() {
    return elements.size();
  }

 /**
  * Gets all child items for the root item of the tree.
  * @return an array of all child items for the root item.
  */
  public Item[] getChildren() {
    return getChildren(getRoot());
  }

 /**
  * Gets all child items for the specified item.
  * @param <code>item</code> the item that is used as a root to get a child items array.
  * @return an array of all child items for the given root item.
  */
  public Item[] getChildren(Item item)
  {
    ItemDesc desc = getItemDesc(item);
    if (desc.children == null) return new Item[0];

    Item[] items = new Item[desc.children.size()];
    for (int i=0; i<items.length; i++) items[i] = (Item)desc.children.elementAt(i);
    return items;
  }

  public Item getChildAt(Item item, int index) {
    return (Item)getItemDesc(item).children.elementAt(index);
  }

  public int getChildIndex (Item item)
  {
    Item parent = getParent(item);
    if (parent == null) return 0;
    ItemDesc desc = getItemDesc(parent);
    return desc.children.indexOf(item);
  }

  public Item getParent (Item item) {
    return getItemDesc(item).parent;
  }

  public int getChildrenCount(Item item) {
    ItemDesc desc = getItemDesc(item);
    return (desc.children == null?0:desc.children.size());
  }

  public boolean hasChildren(Item item) {
    return getChildrenCount(item) > 0;
  }

 /**
  * Tests if the specified item is contained with this tree model.
  * @param   <code>item</code>  the specified item to be tested.
  * @return  <code>true</code> if the specified item contained with this tree model;
  *          <code>false</code> otherwise.
  */
  public boolean contains(Item item) {
    return elements.get(item) != null;
  }

  public void add (Item to, Item item) {
    insert(to, item, getChildrenCount(to));
  }

  public void insert (Item to, Item item, int index)
  {
    if (index < 0) throw new IllegalArgumentException ();
    ItemDesc desc = getItemDesc(to);
    if (desc.children == null) desc.children = new Vector(5);
    desc.children.insertElementAt(item, index);
    regItem(item, to, null);
  }

  public void remove(Item item)
  {
    ItemDesc desc = getItemDesc(item);
    if (desc.children != null)
      while (desc.children.size() != 0)
        remove((Item)desc.children.elementAt(0));

    unregItem(item);
  }

 /**
  * Deletes a child item from the specified parent item at the specified index.
  * @param <code>parent</code> the parent item for what the child item will be removed.
  * @param <code>index</code> the index of the child item to be removed.
  */
  public void removeChild(Item parent, int index)
  {
    ItemDesc desc = getItemDesc(parent);
    Item item = (Item)desc.children.elementAt(index);
    remove(item);
  }

 /**
  * Deletes all child items for the specified parent item.
  * @param <code>item</code> the parent item for what all child items will be removed.
  */
  public void removeKids(Item item)
  {
    ItemDesc desc = getItemDesc(item);
    Item[] items = getChildren(item);
    for (int i=0; i<items.length; i++) remove(items[i]);
  }

 /**
  * Creates a subtree for the specified item of this tree. The specified item
  * is set as the root item for the subtree.
  * @param <code>item</code> the specified item.
  * @return a clone of the item hierarchy.
  */
  public Tree clone(Item item)
  {
    Item  root = new Item(item);
    Tree  res  = new Tree(root);
    clone(res, root, getItemDesc(item));
    return res;
  }

  public void addTreeListener (TreeListener l) {
    if (support == null) support = new Vector(1);
    if (!support.contains(l)) support.addElement(l);
  }

  public void removeTreeListener(TreeListener l) {
    if (support != null) support.removeElement(l);
  }

 /**
  * Sets the specified value for the given tree item.
  * @param <code>item</code> the item.
  * @param <code>o</code> the specified value.
  */
  public void set (Item item, Object o)
  {
    ItemDesc desc = (ItemDesc)elements.get(item);
    if (desc == null) throw new IllegalArgumentException();
    item.setValue(o);
    perform (new TreeEvent(this, TreeEvent.MODIFIED, item));
  }

 /**
  * The method is used to register the specified item as a member of the tree model.
  * The new item is bound with the given parent item.
  * @param <code>item</code> the item that has to be registered as a member of the tree.
  * @param <code>parent</code> the parent of the registered item.
  * @param <code>v</code> the vector of child items for the given parent item.
  */
  protected void regItem(Item item, Item parent, Vector v)
  {
    ItemDesc desc = new ItemDesc(v, parent);
    elements.put(item, desc);
    perform (new TreeEvent(this, TreeEvent.INSERTED, item));
  }

 /**
  * The method is used to unregister the specified item as a member of the tree model.
  * @param <code>item</code> the item that has to be unregistered as a member of this tree.
  */
  protected void unregItem(Item item)
  {
    ItemDesc desc = getItemDesc(item);
    if (desc.parent != null)
    {
      ItemDesc pdesc = getItemDesc(desc.parent);
      pdesc.children.removeElement(item);
    }
    elements.remove(item);
    perform (new TreeEvent(this, TreeEvent.REMOVED, item));
  }

 /**
  * Fires the specified tree event to the tree model listeners.
  * @param <code>e</code> the tree event.
  */
  protected void perform(TreeEvent e)
  {
    if (support != null)
    {
      for (int i=0; i<support.size(); i++)
      {
        TreeListener l = (TreeListener)support.elementAt(i);
        switch (e.getID())
        {
          case TreeEvent.INSERTED: l.itemInserted(e); break;
          case TreeEvent.REMOVED : l.itemRemoved (e); break;
          case TreeEvent.MODIFIED: l.itemModified(e); break;
        }
      }
    }
  }

 /**
  * Returns an internal description of the given item. The description provides information
  * about a parent item and child items.
  * @return an internal description of the item.
  */
  ItemDesc getItemDesc(Item item) {
    return (ItemDesc)elements.get(item);
  }

  private static void clone (Tree res, Item root, ItemDesc desc)
  {
    if (desc.children != null)
    {
      for (int i=0; i < desc.children.size(); i++)
      {
        Item originalItem = (Item)desc.children.elementAt(i);
        Item item         = new Item(originalItem);
        res.add(root, item);
        clone(res, item, res.getItemDesc(originalItem));
      }
    }
  }
}

class ItemDesc
{
  protected Vector children;
  protected Item   parent;

  protected ItemDesc(Vector c, Item p) {
    children = c;
    parent = p;
  }
}





