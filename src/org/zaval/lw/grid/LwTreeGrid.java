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
package org.zaval.lw.grid;

import org.zaval.lw.event.*;
import org.zaval.lw.*;
import org.zaval.lw.tree.*;
import org.zaval.util.*;
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

import java.util.*;
import java.awt.*;

/**
 * This is light weight tree grid component. The functionality of the component bases
 * on light weight grid component, but there are several essential differences:
 * <ul>
 *  <li>
 *    The first row of the component contains tree component.
 *  </li>
 *  <li>
 *    The component uses two models: TreeModel and MatrixModel models. Use
 *    <code>getTreeModel</code> and <code>setTreeModel</code> methods to work with
 *    the tree model and use <code>getModel</code> to get the matrix model. Draw
 *    attention that it is impossible to redefine the matrix model because
 *    the model has specific implementation to be associated with the tree model.
 *  </li>
 *  <li>
 *    As it has been described in previous point, the matrix model is bound with
 *    tree model. Let's consider the matrix model and its connection with tree model.
 *    <ul>
 *      <li>
 *       <b>Rule 1.</b> The number of rows (returned by <code>getRows</code> method) of
 *       the matrix model is equals to number of item in the tree model.
 *      </li>
 *      <li>
 *       <b>Rule 2.</b> It is impossible to change data for the first column of the
 *       grid using the matrix model. If the <code>put</code> method of the model
 *       will be called for the purpose, than runtime exception will be thrown.
 *      </li>
 *      <li>
 *       <b>Rule 3.</b> Use the tree model to change data in the first row.
 *      </li>
 *      <li>
 *       <b>Rule 4.</b> The matrix model is used to put data into grid cells that
 *       has column number greater than zero. In this case the data is bound with
 *       appropriate tree item. It means that if the tree item is removed than all
 *       data that are bound with it in the matrix model will be removed too.
 *      </li>
 *      <li>
 *       <b>Rule 5.</b> It is important to understand the difference between number of
 *       the matrix model rows and the rows that are returned by <code>getGridRows</code>
 *       method. The model rows is a total number of tree items. Returned by
 *       <code>getGridRows</code> method number of rows is the number of rows that is rendered by
 *       the component. For example, some of tree items can be collapsed, in this case
 *       the <code>getGridRows</code> method returns number of rows that is less than
 *       the matrix model rows. The component has special method to convert the displayed
 *       row to the appropriate model row - <code>getModelRow</code> method.
 *      </li>
 *    </ul>
 *  </li>
 *  <li>
 *    It is impossible to change rows heights by the <code>setRowHeight</code> method of
 *    the component, because the tree items metric defines the rows heights. It is possible
 *    to extend cell height by <code>setExtraRowHeight</code> method.
 *  </li>
 *  <li>
 *    The selected tree item can be got by <code>getSelectedItem</code> method.
 *  </li>
 * </ul>
 * The sample below illustrates the component usage:
 * <pre>
 *   ...
 *   LwTreeGrid tg = new LwTreeGrid();
 *   // Creates tree model
 *   Item root = new Item(new Item("root"));
 *   TreeModel model = new Tree(root);
 *   model.add (root, new Item("Item 1"));
 *   model.add (root, new Item("Item 2"));
 *   model.add (root, new Item("Item 3"));
 *   // Sets tree model
 *   tg.setTreeModel(model);
 *   // Fills grid cells
 *   tg.getModel().put (0, 1, "Column[1] bound with root");
 *   tg.getModel().put (0, 2, "Column[2] bound with root");
 *   tg.getModel().put (1, 1, "Column[1] bound with item 1");
 *   // Sets cells editor provider
 *   tg.setEditorProvider(new LwDefEditors());
 *   // Adds top grid caption
 *   LwGridCaption cap = LwGridCaption(tg);
 *   tg.add (LwGrid.TOP_CAPTION_EL, cap);
 *   ...
 * </pre>
 */
public class LwTreeGrid
extends LwGrid
implements LwGridViewProvider, MatrixModel,
           TreeListener, LwActionListener, LwComposite
{
  private LwTree              tree;
  private LwGridViewProvider  provider;
  private Hashtable           data;
  private int                 gridCols = 1;
  private Vector              itemByRow = new Vector(), listeners;
  private int                 extraRowHeight = 2;

 /**
  * Constructs the component with the default data model and tree model.
  */
  public LwTreeGrid () {
    this (new Tree(new Item ("root")));
  }

 /**
  * Constructs the component with the specified tree data model.
  * @param <code>model</code> the specified tree data model.
  */
  public LwTreeGrid (TreeModel model)
  {
    tree = new LwTree(model);
    tree.enableSelection (false);
    tree.showLines (false);
    tree.addActionListener(this);
    tree.setOpaque(false);
    add (tree);
    setCellInsets(0,2,0,2);
    super.setViewProvider(this);
    setViewProvider(new LwDefViews());
    super.setModel (this);
  }

 /**
  * Sets the matrix model. It is impossible to set own matrix model for the component because
  * the matrix model has specific implementation to be bound with the tree model. The method
  * is empty.
  * @param <code>m</code> the matrix model.
  */
  public /*C#override*/ void setModel(MatrixModel m) {
   // throw new RuntimeException();
  }

  public boolean catchInput(LwComponent child)  {
    return LwToolkit.isAncestorOf(tree, child);
  }

 /**
  * Sets the extra rows height. The extra height defines value to increase rows heights from
  * below and top.
  * @param <code>h</code> the extra rows height.
  */
  public void setExtraRowHeight (int h)
  {
    if (extraRowHeight != h)
    {
      extraRowHeight = h;
      iMetric();
      repaint();
    }
  }

  public /*C#override*/ LwGridViewProvider getViewProvider() {
    return provider;
  }

  public /*C#virtual*/ void setTreeViewProvider(LwViewProvider p)
  {
    if (tree.getViewProvider() != p)
    {
      tree.setViewProvider(p);
      iMetric();
    }
  }

 /**
  * Sets the specified view for the specified tree view element. Use one of the following
  * constants as the element view id:
  * <ul>
  *   <li>
  *      LwTree.LEAST_VIEW - to define least node element.
  *   </li>
  *   <li>
  *      LwTree.OPENED_VIEW - to define opened node element.
  *   </li>
  *   <li>
  *      LwTree.CLOSED_VIEW - to define closed node element.
  *   </li>
  *   <li>
  *      LwTree.TOGGLE_OFF_VIEW - to define toggle off node element.
  *   </li>
  *   <li>
  *      LwTree.TOGGLE_ON_VIEW - to define toggle on node element.
  *   </li>
  * </ul>
  * @param <code>id</code> the specified element id.
  * @param <code>v</code> the specified view.
  */
  public void setTreeElView(int id, LwView v) {
    tree.setView(id, v);
    iMetric();
  }

  public /*C#override*/ void setViewProvider(LwGridViewProvider p)
  {
    if (provider != p)
    {
      provider = p;
      iMetric();
      repaint();
    }
  }

  public /*C#override*/ void mousePressed (LwMouseEvent e)
  {
    if (e.getX() >= tree.getX() && e.getX() < tree.getX() + tree.getWidth())
    {
      LwMouseEvent ee = new LwMouseEvent (tree, e.getID(), e.getAbsX(), e.getAbsY(), e.getMask(), 1);
      tree.mousePressed(ee);
    }
    super.mousePressed (e);
  }

  public /*C#override*/ void keyPressed (LwKeyEvent e)
  {
    PosController controller = getPosController();
    if (controller != null && controller.getCurrentLine() >=0)
    {
      char ch = e.getKeyChar();
      if (ch == '+')
      {
        Item item = getItemByRow(controller.getCurrentLine(), true);
        if (!tree.isOpen(item)) tree.toggle(item);
        return;
      }
      else
      if (ch == '-')
      {
        Item item = getItemByRow(controller.getCurrentLine(), true);
        if (tree.isOpen(item)) tree.toggle(item);
        return;
      }
    }
    super.keyPressed(e);
  }

  public /*C#override*/ void layout(LayoutContainer target)
  {
    super.layout(target);
    if (tree != null && tree.isVisible())
    {
      Insets    ins = getInsets();
      Insets    ci  = getCellInsets();
      Dimension ps  = tree.getPreferredSize();
      int       gap = getNetGap();
      Point     off = getOrigin();
      tree.setSize(getColWidth(0) - ci.left - ci.right, ps.height);
      tree.setLocation (ins.left + gap + ci.left + off.x, ins.top + gap + ci.top + getExtraHeight() + getTopCaptionHeight() + off.y);
    }
  }

  public LwView getView(int row, int col, Object o)
  {
    if (col > 0)
    {
      Item item = getItemByRow(row, true);
      Object[] a = (Object[])data.get(item);
      o = (a == null || a.length < col ?null:a[col - 1]);
      return provider.getView(row, col, o);
    }
    return null;
  }

  public int getXAlignment(int row, int col) {
    return provider.getXAlignment(row, col);
  }

  public int getYAlignment(int row, int col) {
    return provider.getYAlignment(row, col);
  }

  public Color getCellColor (int row, int col) {
    return provider.getCellColor(row, col);
  }

 /**
  * Sets the tree model.
  * @param <code>m</code> the tree model.
  */
  public /*C#virtual*/ void setTreeModel(TreeModel m)
  {
    TreeModel data = tree.getModel();
    if (m != data)
    {
      if (data != null) data.removeTreeListener(this);
      data = m;
      tree.setModel(data);
      if (data != null) data.addTreeListener(this);
      if (this.data != null) this.data.clear();
      iTreeIndex();
      iMetric();
    }
  }

 /**
  * Gets the tree model.
  * @return a tree model.
  */
  public TreeModel getTreeModel() {
    return tree.getModel();
  }

  public int getRows () {
    return getTreeModel().getItemsCount();
  }

  public int getCols () {
    return gridCols;
  }

  public /*C#override*/ int getGridRows ()  {
    vTreeIndex();
    return itemByRow.size();
  }

  public /*C#override*/ int getGridCols () {
    return getCols();
  }

  public Object get(int row, int col)
  {
    if (row >= getRows() || col >= getCols()) throw new IndexOutOfBoundsException();

    if (col != 0 && data != null)
    {
      Item item = getItemByRow (row, false);
      Object[] a = (Object[])data.get(item);
      return a == null || a.length < col ?null:a[col - 1];
    }
    return null;
  }

  public void actionPerformed (LwActionEvent e)
  {
    stopEditing(true);
    iMetric();
    iTreeIndex();
    PosController c = getPosController();
    if (c != null)
    {
      if (c.getCurrentLine() >= getGridRows()) c.clearPos();
      else
      {
        c.invalidate();
        c.validate();
      }
    }
    repaint();
  }

  public void put(int row, int col, Object o) {
    if (col == 0) throw new IllegalArgumentException();
    put (getItemByRow(row, false), row, col, o);
  }

  public void put(Item item, int row, int col, Object o)
  {
    if (col == 0) throw new IllegalArgumentException();
    if (data == null) data = new Hashtable();
    Object[] cells = (Object[])data.get(item);
    int      len   = (cells == null)?0:cells.length;
    if (len < col)
    {
      Object[] na = new Object[col];
      if (cells != null) System.arraycopy(cells, 0, na, 0, cells.length);
      cells = na;
      if (cells.length + 1 > gridCols) gridCols = cells.length + 1;
      perform (new MatrixEvent(this, getRows(), len));
    }
    Object prev = cells[col-1];
    cells[col-1] = o;
    data.put(item, cells);
    perform (new MatrixEvent(this, row, col, prev));
  }

  public void addMatrixListener (MatrixListener m) {
    if (listeners == null) listeners = new Vector();
    if (!listeners.contains(m)) listeners.addElement(m);
  }

  public void removeMatrixListener(MatrixListener l) {
    if (listeners != null) listeners.removeElement(l);
  }

  public void itemInserted(TreeEvent e)
  {
    if (tree.isOpen(getTreeModel().getRoot())) {
      iTreeIndex();
      iMetric();
    }
    perform (new MatrixEvent (this, getRows() - 1, getCols()));
  }

  public void itemRemoved (TreeEvent e)
  {
    iTreeIndex();
    iMetric();

    if (data != null)
    {
      data.remove (e.getItem ());
      perform (new MatrixEvent (this, getRows() + 1, getCols()));
    }
  }

  public void itemModified(TreeEvent e) {
    iMetric();
  }

  public /*C#override*/ void setSOLocation (int dx, int dy)
  {
    if (dx != this.dx || dy != this.dy)
    {
      tree.invalidate();
      super.setSOLocation(dx, dy);
    }
  }

 /**
  * It is impossible to use the method to define the row height. In this case the runtime
  * exception will be thrown.
  */
  public /*C#override*/ void setRowHeight (int row, int h)  {
    throw new RuntimeException();
  }

 /**
  * Converts and returns the model row by the specified grid row.
  * @param <code>gridRow</code> the specified grid row.
  * @return a model row.
  */
  public int getModelRow(int gridRow) {
    return getDataRow(getItemByRow(gridRow, true));
  }

 /**
  * Gets the selected tree item.
  * @return a selected tree item.
  */
  public Item getSelectedItem()
  {
    PosController c = getPosController ();
    if (c != null && c.getCurrentLine() >= 0) return getItemByRow(c.getCurrentLine(), true);
    return null;
  }

  public /*C#override*/ void startEditing (int row, int col) {
    if (col != 0) super.startEditing(row, col);
  }

 /**
  * Returns the tree item by the specified model row. Draw attention that the method requires
  * model row not a visible grid row.
  * @param <code>row</code> the specified model row.
  * @return a tree item.
  */
  public Item getItemByRow (int row) {
    return getItemByRow(row, false);
  }

 /**
  * Performs the specified matrix event.
  * @param <code>e</code> the specified matrix event.
  */
  protected void perform(MatrixEvent e)
  {
    if (listeners != null)
    {
      for (int i=0; i<listeners.size(); i++)
      {
        MatrixListener l = (MatrixListener)listeners.elementAt(i);
        if (e.getID() == MatrixEvent.MATRIX_RESIZED) l.matrixResized(e);
        else l.cellModified(e);
      }
    }
  }

  protected /*C#override*/ void rCustomMetric()
  {
    Insets ins = getCellInsets();
    tree.setGaps(-1, ins.top + ins.bottom + getNetGap() + extraRowHeight*2);

    vTreeIndex();
    int start = 0;
    if (colWidths != null)
    {
      start = colWidths.length;
      if (colWidths.length != getGridCols())
      {
        int[] na = new int[getGridCols()];
        System.arraycopy(colWidths, 0, na, 0, Math.min(colWidths.length, na.length));
        colWidths = na;
      }
    }
    else colWidths = new int[getGridCols()];
    for (;start<colWidths.length; start++) colWidths[start] = DEF_COLWIDTH;
  }

  protected /*C#override*/ void rPsMetric()
  {
    Insets ins = getCellInsets();
    tree.setGaps(-1, ins.top + ins.bottom + getNetGap());
    vTreeIndex();
    colWidths = new int[getGridCols()];
    Insets cellInsets = getCellInsets();
    int addW = cellInsets.left + cellInsets.right;
    for (int i=1; i<colWidths.length ;i++)
    {
      for (int j=0; j<getGridRows(); j++)
      {
        LwView v = getView(j, i, dataToPaint(j, i));
        if (v != null)
        {
          Dimension ps = v.getPreferredSize();
          ps.width  += addW;
          if (ps.width  > colWidths[i] ) colWidths [i] = ps.width;
        }
        else if (DEF_COLWIDTH  > colWidths [i]) colWidths [i] = DEF_COLWIDTH;
      }
    }
    colWidths[0] = tree.getPreferredSize().width + addW;
  }

  protected /*C#override*/ Object dataToPaint (int row, int col)
  {
    if (col > 0)
    {
      Item i = getItemByRow(row, true);
      Object[] a = (Object[])data.get(i);
      return a == null || a.length < col ?null:a[col - 1];
    }
    return null;
  }

  protected /*C#override*/ Object getDataToEdit (int row, int col) {
    return getModel().get(getDataRow(getItemByRow(row, true)), col);
  }

  protected /*C#override*/ void setEditedData (int row, int col, Object value) {
    getModel().put (getDataRow(getItemByRow(row, true)), col, value);
  }

  protected /*C#override*/ int rowHeight(int r) {
    Insets cellInsets = getCellInsets();
    return tree.getItemMetrics((Item)itemByRow.elementAt(r)).getHeight() + cellInsets.top + cellInsets.bottom + 2*getExtraHeight();
  }

  private Item getItemByRow (int row, boolean onlyVisible) {
    vTreeIndex();
    return onlyVisible?(Item)itemByRow.elementAt(row):
                        getItemByRow(getTreeModel().getRoot(), row, new int[] { 0 } );
  }

  private Item getItemByRow (Item root, int row, int[] c)
  {
    if (row == c[0]) return root;
    c[0]++;
    TreeModel data = getTreeModel();
    for (int i=0; i<data.getChildrenCount(root); i++)
    {
      Item res = getItemByRow(data.getChildAt(root, i), row, c);
      if (res != null) return res;
    }
    return null;
  }

   private int getDataRow (Item item) {
     int[] c = new int[1];
     return getDataRow(getTreeModel().getRoot(), item, c)?c[0]:-1;
   }

   private boolean getDataRow (Item root, Item item, int[] c)
   {
     if (root == item) return true;
     c[0]++;
     TreeModel data = getTreeModel ();
     int size = data.getChildrenCount(root);
     for (int i=0; i<size; i++) if (getDataRow(data.getChildAt(root, i), item, c)) return true;
     return false;
   }

  private void iTreeIndex() {
    itemByRow.removeAllElements();
  }

  private void vTreeIndex() {
    if (itemByRow.size() == 0) rTreeIndex(getTreeModel().getRoot());
  }

  private void rTreeIndex(Item root)
  {
    itemByRow.addElement(root);
    if (tree.isOpen(root))
    {
      TreeModel data = getTreeModel ();
      int       size = data.getChildrenCount(root);
      for (int i=0; i<size; i++)
        rTreeIndex(data.getChildAt(root, i));
    }
  }

  private int getExtraHeight() {
    return isUsePsMetric()?0:extraRowHeight;
  }
}


