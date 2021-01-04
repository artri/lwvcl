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
package org.zaval.lw.tree;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import org.zaval.data.*;
import org.zaval.data.Item;
import org.zaval.data.event.*;
import org.zaval.lw.event.*;
import org.zaval.lw.*;
import org.zaval.misc.*;
import org.zaval.util.*;

/**
 * This is tree view component. The component renders the specified  <code>TreeModel</code>
 * and organizes navigation through the tree. The sample below illustrates
 * the component usage:
 * <pre>
 *   ...
 *   Item root = new Item("root");
 *   TreeModel data = new Tree(root);
 *   data.add(data.getRoot(), new Item("First Child root"));
 *   data.add(data.getRoot(), new Item("Second Child root"));
 *   LwTree tree = new LwTree(data);
 *   ...
 * </pre>
 * <p>
 * The class provides ability to customize the tree node rendering by defining the
 * <code>LwViewProvider</code> provider. It allows to specify the view for the tree item.
 * The default tree item view is LwTextRender.
 * <p>
 * Use <code>addSelectionListener</code> and <code>removeSelectionListener</code> methods to
 * handle selection events. The events are performed by the component whenever the tree node
 * has been selected, deselected.
 * <p>
 * Use <code>addActionListener</code> and <code>removeActionListener</code> methods to
 * be notified whenever the node has been expanded or collapsed. The action event contains
 * the tree expanded or collapsed item that can be got by <code>getData</code> method of the
 * action event object.
 * <p>
 * It is possible to disable node selection by the <code>enableSelection</code> method.
 * <p>
 * It is possible to customize views for some elements of the tree by <code>setView</code>
 * The default views of the elements are read as static objects by following keys:
 * <ul>
 *   <li>The toggle off view is fetched by "toggle.off" key.</li>
 *   <li>The toggle on view is fetched by "toggle.on" key.</li>
 *   <li>The list node view is fetched by the "tree.least" key.</li>
 *   <li>The opened node view is fetched by the "tree.open" key.</li>
 *   <li>The closed node view is fetched by the "tree.close" key.</li>
 * </ul>
 * <p>
 * The tree nodes height and width can be extended by <code>setGaps</code> method that
 * defines horizontal and vertical gaps between the nodes.
 * <p>
 * It is possible disables tree view line rendering by <code>showLines</code> method.
 */
public class LwTree
extends LwCanvas
implements TreeListener, LwViewProvider, LwMouseListener,
           LwFocusListener, LwKeyListener , ScrollObj
{
  /**
   * Defines list view element type.
   */
   public static final int LEAST_VIEW  = 1;

  /**
   * Defines opened view element type.
   */
   public static final int OPENED_VIEW = 2;

  /**
   * Defines closed view element type.
   */
   public static final int CLOSED_VIEW = 3;

  /**
   * Defines toggle off view element type.
   */
   public static final int TOGGLE_OFF_VIEW = 4;

  /**
   * Defines toggle on view element type.
   */
   public static final int TOGGLE_ON_VIEW  = 5;

   /* To speed up */
   private static final Dimension ZERO = new Dimension();

   private TreeModel          data;
   private LwActionSupport    actions;
   private LwActionSupport    selection;
   private boolean isOpenVal;

   private LwViewProvider  provider;
   private LwView          openedImg, closedImg, leastImg, toggleOnView, toggleOffView;
   private Hashtable       nodes;
   private int             gapx = 5, gapy = 2, dx, dy;
   private Item            selected, firstVisible;
   private Rectangle       visibleArea;
   private Dimension       max,openedImgSize, closedImgSize, leastImgSize, toggleOnSize, toggleOffSize;
   private ScrollMan       man;
   private LwTextRender    defaultRender = new LwTextRender("");
   private Color           selectColor1 = LwToolkit.darkBlue, selectColor2 = Color.yellow, linesColor = Color.gray;

  /**
   * Constructs a tree view component with the specified tree model.
   * @param <code>d</code> the specified tree model.
   */
   public LwTree(TreeModel d) {
     this(d, true);
   }

  /**
   * Constructs a tree view component with the specified tree model and tree node state.
   * The state defines if all nodes that have children should be opened or closed.
   * @param <code>d</code> the specified tree model.
   * @param <code>b</code> the specified tree node state.
   */
   public LwTree(TreeModel d, boolean b)
   {
     isOpenVal = b;
     setModel(d);
     setViewProvider(this);
     setView(TOGGLE_ON_VIEW,  LwToolkit.getView("toggle.on"));
     setView(TOGGLE_OFF_VIEW, LwToolkit.getView("toggle.off"));
     setView(LEAST_VIEW,      LwToolkit.getView("tree.least"));
     setView(OPENED_VIEW,     LwToolkit.getView("tree.open"));
     setView(CLOSED_VIEW,     LwToolkit.getView("tree.close"));
     enableSelection(true);
     showLines(true);
   }

   public /*C#override*/ boolean canHaveFocus() {
     return true;
   }

  /**
   * Enables the tree node selecting.
   * @param <code>b</code> use <code>true</code> to enable the tree node selecting;
   * <code>false</code> otherwise.
   */
   public void enableSelection(boolean b)
   {
     if (isSelectionEnabled() != b)
     {
       if (!b && selected != null) select(null);
       bits = MathBox.getBits(bits, ENSEL_BIT, b);
       repaint();
     }
   }

  /**
   * Tests if the tree items selection is enabled.
   * @return <code>true</code> if the tree items selection is enabled;
   * <code>false</code> otherwise.
   */
   public boolean isSelectionEnabled() {
     return MathBox.checkBit(bits, ENSEL_BIT);
   }

  /**
   * Enables the tree lines rendering.
   * @param <code>b</code> use <code>true</code> to enable the tree line rendering;
   * <code>false</code> otherwise.
   */
   public void showLines(boolean b)
   {
     if (isLinesShown() != b)
     {
       bits = MathBox.getBits(bits, ENLINES_BIT, b);
       repaint();
     }
   }

  /**
   * Tests if the tree line rendering is enabled.
   * @return <code>true</code> if the line rendering is enabled;
   * <code>false</code> otherwise.
   */
   public boolean isLinesShown() {
     return MathBox.checkBit(bits, ENLINES_BIT);
   }

  /**
   * Gets the color that is used to paint tree lines.
   * @param a color.
   */
   public Color getLinesColor() {
     return linesColor;
   }

  /**
   * Sets the specified color to paint tree lines.
   * @param <code>c</code> the specified color.
   */
   public void setLinesColor(Color c)
   {
     if (!c.equals (linesColor))
     {
       linesColor = c;
       repaint();
     }
   }

  /**
   * Sets the specified vertical and horizontal gaps. The method doesn't touch
   * a gap in case if it is less zero, so it is possible to use the method to
   * set only vertical or only horizontal gap.
   * @param <code>gx</code> the specified horizontal gap.
   * @param <code>gy</code> the specified vertical gap.
   */
   public void setGaps (int gx, int gy)
   {
     if ((gx >= 0 && gx != gapx)||
         (gy >= 0 && gy != gapy)  )
     {
       gapx = gx<0?gapx:gx;
       gapy = gy<0?gapy:gy;
       invalidateMetrics();
       repaint();
     }
   }

  /**
   * Gets the tree model that is rendered with the tree view component.
   * @return a tree model.
   */
   public TreeModel getModel() {
     return data;
   }

  /**
   * Sets the specified view provider. The provider is used to define a view that
   * will be used to paint the node. It is possible to use <code>null</code> value
   * as the input argument, in this case the LwTextRender view is used (the method
   * sets itself as the view provider) to paint the node.
   * @param <code>p</code> the specified view provider.
   */
   public void setViewProvider (LwViewProvider p)
   {
     if (p == null) p = this;
     if (provider != p)
     {
       provider = p;
       invalidateData();
       repaint();
     }
   }

  /**
   * Gets the view provider.
   * @return a view provider.
   */
   public LwViewProvider getViewProvider () {
     return provider;
   }

  /**
   * Sets the specified view for the specified tree view element. Use one of the following
   * constants as the view element id:
   * <ul>
   *   <li>
   *      LEAST_VIEW - to define least node element.
   *   </li>
   *   <li>
   *      OPENED_VIEW - to define opened node element.
   *   </li>
   *   <li>
   *      CLOSED_VIEW - to define closed node element.
   *   </li>
   *   <li>
   *      TOGGLE_OFF_VIEW - to define toggle off node element.
   *   </li>
   *   <li>
   *      TOGGLE_ON_VIEW - to define toggle on node element.
   *   </li>
   * </ul>
   * @param <code>id</code> the specified element id.
   * @param <code>v</code> the specified view.
   */
   public void setView(int id, LwView v)
   {
     switch (id)
     {
       case LEAST_VIEW     : if (v != leastImg)  { leastImg  = v; leastImgSize  = viewPS(v); } break;
       case OPENED_VIEW    : if (v != openedImg) { openedImg = v; openedImgSize = viewPS(v); } break;
       case CLOSED_VIEW    : if (v != closedImg) { closedImg = v; closedImgSize = viewPS(v); } break;
       case TOGGLE_OFF_VIEW: if (v != toggleOffView) { toggleOffView = v; toggleOffSize = viewPS(v); } break;
       case TOGGLE_ON_VIEW : if (v != toggleOnView)  { toggleOnView = v; toggleOnSize = viewPS(v); } break;
       default: throw new IllegalArgumentException();
     }
     invalidateMetrics();
     repaint();
   }

  /**
   * Sets the specified tree model to be rendered with the component.
   * @param <code>d</code> the specified tree model.
   */
   public void setModel(TreeModel d)
   {
     if (data != d)
     {
       select(null);
       if (data != null) data.removeTreeListener(this);
       data = d;
       if (data != null) data.addTreeListener(this);
       invalidateData();
       repaint();
     }
   }

  /**
   * Paints this component.
   * @param <code>g</code> the graphics context to be used for painting.
   */
   public /*C#override*/ void paint (Graphics g)
   {
     if (data != null)
     {
       vVisibility();
       if (MathBox.checkBit(bits, VALVIS_BIT) && firstVisible != null)
         paintTree (g, firstVisible);
     }
   }

  /**
   * The method is overrided to calculate metrical characteristics of the tree view
   * component.
   */
   protected /*C#override*/ void recalc()
   {
     if (data!=null && max == null)
     {
       Insets i = getInsets();
       max = new Dimension (i.left, i.top);
       recalc (i.left, i.top, null, data.getRoot());
       max.width  -= i.left;
       max.height -= i.top;
     }
   }

  /**
   * Adds the specified selection listener to be notified whenever the tree item has been
   * selected, deselected.
   * @param <code>l</code> the specified selection listener.
   */
   public void addSelectionListener(LwActionListener l) {
     if (selection == null) selection = new LwActionSupport();
     selection.addListener(l);
   }

  /**
   * Removes the specified selection listener.
   * @param <code>l</code> the specified selection listener.
   */
   public void removeSelectionListener(LwActionListener l) {
     if (selection != null) selection.removeListener(l);
   }

   public LwView getView(Drawable d, Object obj) {
     defaultRender.getTextModel().setText(obj.toString());
     return defaultRender;
   }

   public void focusGained  (LwFocusEvent e) {}
   public void focusLost    (LwFocusEvent e) {}
   public void mouseClicked (LwMouseEvent e) {}
   public void mouseEntered (LwMouseEvent e) {}
   public void mouseExited  (LwMouseEvent e) {}
   public void mouseReleased(LwMouseEvent e) {}
   public void keyReleased  (LwKeyEvent e) {}
   public void keyTyped     (LwKeyEvent e) {}

   public void keyPressed (LwKeyEvent e)
   {
     if (selected != null)
     {
       switch (e.getKeyChar())
       {
         case '+' : if (!isOpen(selected)) toggle(selected); return;
         case '-' : if (isOpen(selected)) toggle(selected); return;
       }
     }

     Item newSelection = null;
     switch (e.getKeyCode())
     {
       case KeyEvent.VK_DOWN :
       case KeyEvent.VK_RIGHT: newSelection = findNext (selected); break;

       case KeyEvent.VK_UP   :
       case KeyEvent.VK_LEFT : newSelection = findPrev (selected); break;

       case KeyEvent.VK_HOME : if ((e.getMask() & KeyEvent.CTRL_MASK) > 0) select(data.getRoot());
                               break;
       case KeyEvent.VK_END  : if ((e.getMask() & KeyEvent.CTRL_MASK) > 0) select(findLast(data.getRoot()));
                               break;
       case KeyEvent.VK_PAGE_DOWN : if (selected != null) select(nextPage(selected, 1));
                                    break;
       case KeyEvent.VK_PAGE_UP   : if (selected != null) select(nextPage(selected, -1));
                                    break;
     }
     if (newSelection != null) select(newSelection);
   }

   public void mousePressed(LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()))
     {
       int x = e.getX() - dx, y = e.getY() - dy;

       Item root = getItemAt(firstVisible, x, y);
       if (root != null)
       {
         Rectangle r = getToggleBounds(root);
         if (r.contains(x, y)) toggle(root);
         else if (x > r.x + r.width) select(root);
       }
     }
   }

  /**
   * Sets the selection marker color for the given tree view component state.
   * @param <code>c</code> the selection marker color. Use <code>null</code> if the marker should not be
   * rendered.
   * @param <code>isHasFocus</code> the given tree view component focus state. Use <code>true</code>
   * to identify the selection color when the component has focus, otherwise use <code>false</code>
   */
   public void setSelectionColor (Color c, boolean isHasFocus)
   {
      Color rc = getSelectionColor(isHasFocus);
      if (rc != c || (rc == null || !rc.equals(c)))
      {
        if (isHasFocus) selectColor1 = c;
        else            selectColor2 = c;
        repaint();
      }
   }

  /**
   * Gets the selection marker color for the specified component focus state.
   * @param <code>isHasFocus</code> the given tree view component focus state. Use <code>true</code>
   * to identify the selection color when the component has focus, otherwise use <code>false</code>
   * @return a selection marker color.
   */
   public Color getSelectionColor (boolean isHasFocus) {
     return isHasFocus?selectColor1:selectColor2;
   }

  /**
   * Switches a toggle state for the specified tree item. If the toggle state
   * is "on" than the method switches it to "off" and the "off" state will be
   * switched to "on" state.
   * @param <code>item</code> the specified tree item.
   */
   public void toggle(Item item)
   {
     validate();
     ItemMetrics node = getIM (item);
     node.isOpen = !node.isOpen;
     if (actions != null)  actions.perform (new LwActionEvent (this, item));
     invalidateMetrics();
     if (!node.isOpen && selected != null && isParentFor(item, selected)) select(item);
     else repaint();
   }

  /**
   * Selects the specified node of the tree.
   * @param <code>item</code> the specified node.
   */
   public void select(Item item)
   {
     if (isSelectionEnabled())
     {
       if (item != selected)
       {
         selected = item;
         if (selected != null)
         {
           validate();
           Rectangle r = getViewBounds(selected);

           Point o = LwToolkit.calcOrigin(r.x, r.y, r.width, r.height, this);
           if (man != null) man.scrollObjMoved(o.x, o.y);
           else             setSOLocation(o.x, o.y);
         }
         if (selection != null)
           selection.perform (new LwActionEvent(this, selected));

         repaint();
       }
     }
   }

  /**
   * Gets the item that is selected at the moment.
   * @return a selected item.
   */
   public Item getSelectedItem() {
     return selected;
   }

  /**
   * Adds the specified action listener to receive action events from this tree.
   * The event is generated whenever the tree node has been expanded or collapsed.
   * The node can be got by <code>getData</code> method of the action event.
   * @param <code>l</code> the specified action listener.
   */
   public void addActionListener(LwActionListener l) {
     if (actions == null) actions = new LwActionSupport();
     actions.addListener(l);
   }

  /**
   * Removes the specified action listener.
   * @param <code>l</code> the specified action listener.
   */
   public void removeActionListener(LwActionListener l) {
     if (actions != null) actions.removeListener(l);
   }

   public void itemInserted(TreeEvent e) {
     invalidateMetrics();
     repaint();
   }

   public void itemRemoved (TreeEvent e)
   {
     if (nodes != null)
     {
       nodes.remove(e.getItem());
       invalidateMetrics();
       repaint();
     }
   }

   public void itemModified(TreeEvent e)
   {
     if (nodes != null)
     {
       ItemMetrics node = getIM(e.getItem());
       if (node != null) node.viewWidth = -1;
       invalidateMetrics();
       repaint();
     }
   }

   public /*C#override*/ void invalidate () {
     bits = MathBox.getBits(bits, VALVIS_BIT, false);
     super.invalidate();
   }

   public /*C#override*/ Point getOrigin() {
     return new Point(dx, dy);
   }

   public Point getSOLocation () {
     return getOrigin();
   }

   public void setSOLocation (int x, int y)
   {
     if (x != dx || y != dy)
     {
       dx = x;
       int prevDy = dy;
       dy = y;

       if (isVisibilityValid())
         firstVisible = (y < prevDy)?nextVisible(firstVisible):prevInVisible(firstVisible);
       repaint();
     }
   }

   public Dimension getSOSize() {
     return getPreferredSize();
   }

   public void setScrollMan (ScrollMan m) {
     man = m;
   }

   public boolean moveContent() {
     return true;
   }

  /**
   * Checks if the tree item is expanded. The item is expanded if it is expanded and
   * all parent tree items are exapnded too.
   * @param <code>i</code> the specified tree item.
   * @return <code>true</code> if the item and its parent item is expanded too;
   * <code>false</code> otherwise.
   */
   public boolean isOpen(Item i) {
     validate();
     return isOpen_(i);
   }

  /**
   * Gets the item metric for the specified item.
   * @param <code>i</code> the specified item.
   * @return an item metric.
   */
   public ItemMetrics getItemMetrics (Item i) {
     validate();
     return getIM (i);
   }

  /**
   * Gets the preferred size of this component. The method is overrided with the
   * component to returns the "pure" preferred size basing on the tree structure.
   * @return a dimension object indicating this component preferred size.
   */
   protected /*C#override*/ Dimension calcPreferredSize() {
     return max == null?super.calcPreferredSize():new Dimension (max.width, max.height);
   }

   private int recalc(int x, int y, Item parent, Item root)
   {
     ItemMetrics node = getIM(root);
     if (node == null)
     {
       node = new ItemMetrics(isOpenVal);
       if (nodes == null) nodes = new Hashtable();
       nodes.put(root, node);
     }

     if (node.viewWidth < 0)
     {
       LwView nodeView = provider.getView(this, root);
       Dimension viewSize = nodeView.getPreferredSize();
       node.viewWidth  = viewSize.width == 0?5:viewSize.width;
       node.viewHeight = viewSize.height;
     }

     Dimension imageSize  = getImageSize (root);
     Dimension toggleSize = getToggleSize(root);
     if (parent != null)
     {
       Rectangle pImg = getImageBounds(parent);
       x = pImg.x + (pImg.width - toggleSize.width)/2;
     }

     node.x      = x;
     node.y      = y;
     node.width  = toggleSize.width + imageSize.width + node.viewWidth +
                   (toggleSize.width>0?gapx:0) + (imageSize.width>0?gapx:0);
     node.height = Math.max(Math.max (toggleSize.height, imageSize.height), node.viewHeight);

     if (node.x + node.width > max.width ) max.width  = node.x + node.width;
     if (node.y + node.height> max.height) max.height = node.y + node.height;

     x  = node.x + toggleSize.width + (toggleSize.width>0?gapx:0);
     y += (node.height + gapy);

     //!!!
     // Take care that it is necessary to use method isOpen(Item), but the method
     // implementation works correctly if the field isOpenVal is used. The feature allows
     // speed up performance for the method.
     //!!!
     if (node.isOpen)
     {
       int count = data.getChildrenCount(root);
       for (int i=0; i<count; i++) y = recalc(x, y, root, data.getChildAt(root, i));
     }
     return y;
   }

   private boolean isOpen_(Item i) {
     return (i == null || (data.hasChildren(i) && getIM(i).isOpen && isOpen_(data.getParent(i))));
   }

   private ItemMetrics getIM(Item i) {
     return (nodes == null)?null:(ItemMetrics)nodes.get(i);
   }

   private Item getItemAt (Item root, int x, int y)
   {
     if (isVisibilityValid() && y + dy >= visibleArea.y && y + dy < visibleArea.y + visibleArea.height)
     {
       Item found = getItemAtInBranch(root, x, y);
       if (found != null) return found;

       Item parent = data.getParent(root);
       while(parent != null)
       {
         int index = data.getChildIndex(root);
         int count = data.getChildrenCount(parent);
         for (int i=index + 1; i<count; i++)
         {
           found = getItemAtInBranch (data.getChildAt(parent, i), x, y);
           if (found != null) return found;
         }
         root   = parent;
         parent = data.getParent(root);
       }
     }
     return null;
   }

   private Item getItemAtInBranch (Item root, int x, int y)
   {
     if (root != null)
     {
       ItemMetrics node = getIM(root);
       if (x >= node.x && y >= node.y && x < node.x + node.width && y < node.y + node.height + gapy)
         return root;
       if (isOpen_(root))
       {
         for (int i=0; i<data.getChildrenCount(root); i++)
         {
           Item res = getItemAtInBranch(data.getChildAt(root, i), x, y);
           if (res != null) return res;
         }
       }
     }
     return null;
   }

   private LwView getImageView (Item i)
   {
     if (data.hasChildren(i))
     {
       ItemMetrics node = getIM(i);
       return node.isOpen?openedImg:closedImg;
     }
     return leastImg;
   }

   private Dimension getImageSize (Item i)
   {
     if (data.hasChildren(i))
     {
       ItemMetrics node = getIM(i);
       return node.isOpen?openedImgSize:closedImgSize;
     }
     return leastImgSize;
   }

   private Rectangle getImageBounds (Item root)
   {
     ItemMetrics node = getIM(root);
     Dimension id = getImageSize(root);
     Dimension td = getToggleSize(root);
     return new Rectangle(node.x + td.width + (td.width>0?gapx:0), node.y + (node.height - id.height)/2, id.width, id.height);
   }

   private Rectangle getToggleBounds (Item root)
   {
     ItemMetrics node = getIM(root);
     Dimension d = getToggleSize(root);
     return new Rectangle(node.x, node.y + (node.height - d.height)/2, d.width, d.height);
   }

   private Rectangle getViewBounds(Item root)
   {
     ItemMetrics metrics = getIM(root);
     Rectangle toggle = getToggleBounds(root);
     Rectangle image  = getImageBounds(root);
     return new Rectangle(image.x + image.width + (image.width > 0 || toggle.width > 0 ?gapx:0),
                          metrics.y + (metrics.height - metrics.viewHeight)/2,
                          metrics.viewWidth, metrics.viewHeight);
   }

   private LwView getToggleView (Item i)
   {
     if (data.hasChildren(i))
     {
       ItemMetrics node = getIM(i);
       return node.isOpen?toggleOnView:toggleOffView;
     }
     return null;
   }

   private Dimension getToggleSize (Item i)  {
     return isOpen_(i)?toggleOnSize:toggleOffSize;
   }

   private boolean isAbove (Item i){
     ItemMetrics node = getIM(i);
     return node.y + node.height + dy < visibleArea.y;
   }

   private boolean isVisible (Item i)  {
     ItemMetrics node = getIM(i);
     return visibleArea.intersects(new Rectangle(node.x + dx, node.y + dy , node.width, node.height));
   }

   private boolean isParentFor (Item parent, Item item)  {
     while ((item = data.getParent(item))!= null && parent != item);
     return (item == parent && parent != null);
   }

   private Item findNext(Item item)
   {
     if (item == null) return null;
     if (data.hasChildren(item) && isOpen_(item)) return data.getChildAt(item, 0);

     Item parent = null;
     while ((parent = data.getParent(item)) != null)
     {
       int index = data.getChildIndex(item);
       int count = data.getChildrenCount(parent);
       if (index + 1 < count) return  data.getChildAt(parent, index + 1);
       item = parent;
     }
     return null;
   }

   private Item findPrev(Item item)
   {
     if (item == null) return null;
     Item parent = data.getParent(item);
     if (parent != null)
     {
       int index = data.getChildIndex(item);
       int count = data.getChildrenCount(parent);
       if (index - 1 >= 0) return findLast(data.getChildAt(parent, index - 1));
       else                return parent;
     }
     return null;
   }

   private Item findLast(Item item)
   {
     if (item == null) return null;
     int count = data.getChildrenCount(item);
     if (count == 0 || !isOpen_(item)) return item;
     else
     if (count > 0) return findLast(data.getChildAt(item, count - 1));
     return null;
   }

   private Item prevInVisible(Item item)
   {
     Item parent = null;
     if (item != null)
     while ((parent = data.getParent(item)) != null)
     {
       if (isAbove(item)) return item;
       int index = data.getChildIndex(item);
       for (int i=index-1; i>=0; i--)
       {
         Item res = nextInVisibleInBranch(data.getChildAt(parent, i));
         if (res != null) return res;
       }
       item = parent;
     }
     return data.getRoot();
   }

   private Item nextVisible(Item item)
   {
     Item res = nextVisibleInBranch(item);
     if (res != null) return res;

     Item parent = data.getParent(item);
     while (parent != null)
     {
       int count = data.getChildrenCount(parent);
       for (int i=data.getChildIndex(item) + 1; i<count; i++)
       {
         res = nextVisibleInBranch(data.getChildAt(parent, i));
         if (res != null) return res;
       }
       item   = parent;
       parent = data.getParent(item);
     }
     return null;
   }

   private Item nextInVisibleInBranch (Item item)
   {
     if (item != null)
     {
       if (isAbove(item)) return item;
       if (isOpen_(item))
       {
          for (int i=data.getChildrenCount(item)-1; i>=0; i--)
          {
            Item res = nextInVisibleInBranch(data.getChildAt(item, i));
            if (res != null) return res;
          }
       }
     }
     return null;
   }

   private Item nextVisibleInBranch (Item item)
   {
     if (item != null)
     {
       if (isVisible(item)) return item;
       if (isOpen_(item))
       {
          for (int i=0; i < data.getChildrenCount(item); i++)
          {
            Item res = nextVisibleInBranch(data.getChildAt(item, i));
            if (res != null) return res;
          }
       }
     }
     return null;
   }

   private void paintTree (Graphics g, Item item)
   {
     paintBranch(g, item);
     Item parent = null;
     while ((parent = data.getParent(item)) != null)
     {
       paintChild (g, parent, data.getChildIndex(item)+1);
       item = parent;
     }
   }

   private boolean paintBranch(Graphics g, Item root)
   {
     if (root == null) return false;

     ItemMetrics node = getIM(root);
     if (isVisible(root))
     {
       Rectangle toggle     = getToggleBounds(root);
       LwView    toggleView = getToggleView(root);
       if (toggleView != null) toggleView.paint(g, toggle.x, toggle.y, this);

       Rectangle image = getImageBounds(root);
       if (image.width > 0) getImageView (root).paint(g, image.x, image.y, this);

       int vx = image.x + image.width + (image.width > 0 || toggle.width > 0 ?gapx:0);
       int vy = node.y + (node.height - node.viewHeight)/2;
       LwView nodeView = provider.getView(this, root);
       nodeView.paint(g, vx, vy, this);

       if (isLinesShown())
       {
         g.setColor (linesColor);
         LwToolkit.drawDotHLine(g, toggle.x + (toggleView==null?toggle.width/2 + 1:toggle.width) + 1, image.x - 1, toggle.y + toggle.height/2);
       }

       if (selected == root)
       {
         Color selectColor = getSelectionColor(hasFocus());
         if (selectColor != null)
           LwToolkit.drawMarker(g, vx, vy, node.viewWidth, node.viewHeight, getBackground(), selectColor);
       }
     }
     else
     {
       if (node.y + dy > visibleArea.y + visibleArea.height||
           node.x + dx > visibleArea.x + visibleArea.width   ) return false;
     }
     return paintChild (g, root, 0);
   }

   private int y_(Item item, boolean isStart)
   {
     Rectangle r = getToggleBounds(item);
     if (data.hasChildren(item)) return isStart?r.y + r.height:r.y - 1;
     else                        return r.y + r.height/2;
   }

   private boolean paintChild (Graphics g, Item root, int index)
   {
     if (isOpen_(root))
     {
       Rectangle tb = getImageBounds(root);
       //???
       Rectangle br = getToggleBounds(data.getChildAt(root, 0));
       int x = br.x + br.width/2, count = data.getChildrenCount(root);
       if (index < count)
       {
         int y = (index > 0)?y_(data.getChildAt(root, index-1), true):tb.y + tb.height;
         for (int i=index; i<count; i++)
         {
           Item child = data.getChildAt(root, i);
           if (isLinesShown())
           {
             g.setColor(linesColor);
             LwToolkit.drawDotVLine(g, y, y_(child, false), x);
             y = y_(child, true);
           }

           if (!paintBranch(g, child))
           {
             if (isLinesShown() && i + 1 != count)
             {
               g.setColor(linesColor);
               LwToolkit.drawDotVLine(g, y, height - dy, x);
             }
             return false;
           }
         }
       }

       if (isLinesShown() && index == count && count > 0)
       {
         g.setColor(linesColor);
         LwToolkit.drawDotVLine(g, y_(root, true), y_(data.getChildAt(root, index-1), false), x);
       }
     }
     return true;
   }

   private Item nextPage(Item item, int dir)
   {
     int sum = 0;
     Item prev = item;
     while (item != null && sum < visibleArea.height)
     {
       ItemMetrics node = getIM(item);
       sum += (node.height + gapy);
       prev = item;
       item = dir < 0?findPrev(item):findNext(item);
     }
     return prev;
   }

   private void invalidateData() {
     if (nodes != null) nodes.clear();
     invalidateMetrics();
   }

   private boolean isVisibilityValid () {
     return MathBox.checkBit(bits, VALVIS_BIT);
   }

   private void vVisibility ()
   {
     //!!!
     Rectangle nva = getVisiblePart();
     if (nva == null) return;

     if (!isVisibilityValid() ||
         (visibleArea != null && !nva.equals(visibleArea)))
     {
       visibleArea = nva;
       if (visibleArea != null)
       {
         firstVisible = null;
         if (-dy > max.height/2)
           firstVisible = prevInVisible(findLast(data.getRoot()));
         else
           firstVisible = nextVisible(data.getRoot());
         bits = MathBox.getBits(bits, VALVIS_BIT, true);
       }
     }
   }

   private Dimension viewPS(LwView v) {
     return (v == null)?new Dimension():v.getPreferredSize();
   }

   private void invalidateMetrics()  {
     max = null;
     invalidate();
   }

   private static final short ENSEL_BIT   = 32;
   private static final short ENLINES_BIT = 64;
   private static final short VALVIS_BIT  = 128;
}


