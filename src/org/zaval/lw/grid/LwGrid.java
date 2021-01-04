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
import org.zaval.util.*;
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * This is light weight grid component. The component is a composite component that is built basing
 * on MVC-model:
 * <ul>
 *   <li>
 *     <b>Model.</b> The component is bound with <code>MatrixModel</code>. Use
 *     <code>setModel</code> and <code>getModel</code> methods to set and get the
 *     model.
 *   </li>
 *   <li>
 *     <b>View.</b>
 *     The cell rendering process is defined by grid cell view provider interface. Use
 *     <code>setViewProvider</code> and <code>getViewProvider</code> methods to customize
 *     the process. Using the interface it is possible to define a background color for the
 *     specified cell, a view to render the cell data and horizontal and vertical alignments.
 *   </li>
 *   <li>
 *     <b>Controller.</b>
 *     The grid component controls painting, event handling and validation processes.
 *   </li>
 * </ul>
 * <p>
 * The component allows to edit cells data and customizes editor components types, by
 * the editor provider interface. Use <code>setEditorProvider</code> and
 * <code>getEditorProvider</code> methods to set and get the editor provider interface.
 * Actually the interface defines two things:
 * <ul>
 *  <li>light weight component that will be used to edit the specified cell data.</li>
 *  <li>how to fetch edited data from the editor component to update the grid model.</li>
 * </ul>
 * <p>
 * The component supports two metric types:
 * <ul>
 *   <li>
 *     <b>Custom metric.</b> In this case the rows heights and columns widths can be defined
 *     by the <code>setRowHeight</code> and <code>setColWidth</code> methods, the painting
 *     process uses horizontal and vertical alignments (provided by view provider interface)
 *     to align cells views.
 *   </li>
 *   <li>
 *     <b>Preferred size metric.</b> In this case the rows heights and columns widths are
 *     calculated basing on the cells views preferred sizes. <code>setRowHeight</code> and
 *     <code>setColWidth</code> methods have no effect. The painting process doesn't use
 *     horizontal and vertical alignments (provided by view provider interface) to align cells
 *     views.
 *   </li>
 * </ul>
 * To set appropriate metric type use <code>usePsMetric</code>method.
 * <p>
 * The component sets special layout manager that should not be changed. The layout
 * manager defines TOP_CAPTION_EL constraint that can be used to add grid caption
 * component as it is demonstrated below:
 * <pre>
 *   ...
 *   LwGrid        grid       = new LwGrid();
 *   LwGridCaption topCaption = new LwGridCaption(grid);
 *   grid.add (LwGrid.TOP_CAPTION_EL, topCaption);
 *   ...
 * </pre>
 * The LEFT_CAPTION_EL constraint is reserved for the futher version of the component.
 * <p>
 * To control selection state use PosController that can be got by <code>getPosController</code>
 * method. The component suppotrts single row selection. It is possible to disable selection
 * any grid row, set the pos controller to <code>null</code> for the purpose by
 * <code>setPosController</code> method.
 * <p>
 * The component implements ScrollObj interface, so the grid component can be used inside
 * LwScrollPan component.
 */
public class LwGrid
extends LwPanel
implements MatrixListener, ScrollObj, LwMouseListener,
           LwFocusListener, LwKeyListener, PosInfo, PosListener,
           LwLayout, LwGridMetrics, LwChildrenListener
{
   /**
    * Top caption component layout constraint.
    */
    public static final Integer TOP_CAPTION_EL  = new Integer(1);

   /**
    * Cell editor component layout constraint.
    */
    public static final Integer EDITOR_EL       = new Integer(2);

   /**
    * Left caption component layout constraint.
    */
    public static final Integer LEFT_CAPTION_EL = new Integer(3);

   /**
    * Use preferred size metric bit mask.
    */
    public static final short USE_PSMETRIC = 32;

   /**
    * Draw horizontal lines bit mask.
    */
    public static final short DRAW_HLINES = 64;

   /**
    * Draw vertical lines bit mask.
    */
    public static final short DRAW_VLINES = 128;

   /**
    * Enable col resizing bit mask.
    */
    public static final short ENABLE_COLRESIZE = 256;

   /**
    * The default column width.
    */
    public static final int DEF_COLWIDTH  = 80;

   /**
    * The default row height.
    */
    public static final int DEF_ROWHEIGHT = 20;

    private static final short METRIC_VALID = 512;
    private static final short ROWVIS_VALID = 1024;
    private static final short COLVIS_VALID = 2048;

    private Dimension          psSize;
    private MatrixModel        data;
    protected int[]            colWidths, rowHeights;
    protected int              dx, dy;

    private int                editingRow = -1, editingCol = -1, netSize = 1;
    private Insets             cellInsets = new Insets (2, 2, 2, 2);
    private LwGridViewProvider provider;
    private Color              netColor = Color.gray, noneActSelColor = LwToolkit.darkBlue, actSelColor = Color.yellow;

    private CellsVisibility    visibility = new CellsVisibility();
    private ScrollMan          man;
    private PosController      controller;
    private LwComponent        topCaption, editor;
    private LwEditorProvider   editors;

   /**
    * Constructs the component with the default data model.
    */
    public LwGrid() {
      this(new Matrix(5, 5));
    }

   /**
    * Constructs the component with the specified data model.
    * @param <code>data</code> the specified data model
    */
    public LwGrid(MatrixModel data)
    {
      setModel(data);
      setViewProvider(new LwDefViews());
      setPosController (new PosController());
      enableColResize(true);
      setNetMask ((short)(DRAW_HLINES | DRAW_VLINES));
    }

    public /*C#override*/ boolean canHaveFocus() {
      return true;
    }


   /**
    * Sets the editor provider. The provider is used to define how the specified
    * cell should be edited.
    * @param <code>p</code> the specified editor provider.
    */
    public /*C#virtual*/ void setEditorProvider (LwEditorProvider p)
    {
      if (p != editors) {
        stopEditing(true);
        editors = p;
      }
    }

   /**
    * Gets the net mask.
    * @return a net mask.
    */
    public short getNetMask () {
      return (short)(bits & (DRAW_HLINES | DRAW_VLINES));
    }

   /**
    * Sets the specified net mask. The net mask is a bit mask that defines
    * what grid lines should be painted. There are four ways to paint grid lines:
    * <ul>
    *   <li>To paint only horizontal lines. Use DRAW_HLINES bit mask.</li>
    *   <li>To paint only vertical lines. Use DRAW_VLINES bit mask.</li>
    *   <li>To paint vertical and horizontal lines. Use DRAW_VLINES | DRAW_HLINES bit mask.</li>
    *   <li>To paint no lines. Use zero bit mask.</li>
    * </ul>
    * The default net mask is DRAW_VLINES | DRAW_HLINES.
    * @param <code>mask</code> the specified net mask.
    */
    public void setNetMask (short mask)
    {
      if (mask != getNetMask())
      {
        bits = MathBox.getBits(bits, DRAW_HLINES, (mask & DRAW_HLINES) > 0);
        bits = MathBox.getBits(bits, DRAW_VLINES, (mask & DRAW_VLINES) > 0);
        repaint();
      }
    }

   /**
    * Enables columns resizing. The method defines if the columns of the component
    * can be resized or not by <code>setColWidth</code> method.
    * @param <code>b</code> use <code>true</code> to enable columns resizing; <code>false</code>
    * otherwise.
    */
    public void enableColResize (boolean b) {
      if (MathBox.checkBit(bits, ENABLE_COLRESIZE) != b)
        bits = MathBox.getBits(bits, ENABLE_COLRESIZE, b);
    }

   /**
    * Sets the specified grid metric type. There are two metric types:
    * <ul>
    *  <li>
    *    Preferred size metric type.
    *  </li>
    *  <li>
    *    Custom metric type.
    *  </li>
    * </ul>
    * The default metric type is custom metric type.
    * @param <code>b</code> use <code>true</code> to set preferred size metric type;
    *  <code>false</code> to set custom metric type.
    */
    public void usePsMetric (boolean b)
    {
      if (isUsePsMetric() != b)
      {
        bits = MathBox.getBits(bits, USE_PSMETRIC, b);
        if (b) enableColResize(false);
        iMetric();
        repaint();
      }
    }

   /**
    * Gets the grid metric type.
    * @return <code>true</code> if the preferred size metric type is used;
    * <code>false</code> otherwise.
    */
    public boolean isUsePsMetric() {
      return MathBox.checkBit(bits, USE_PSMETRIC);
    }

   /**
    * Gets the position controller.
    * @return a position controller.
    */
    public PosController getPosController() {
      return controller;
    }

   /**
    * Sets the position controller. The controller can be set to <code>null</code>, in this
    * case it will be impossible to navigate over the grid component rows.
    * @param <code>p</code> the specified position controller.
    */
    public void setPosController(PosController p)
    {
      if (controller != p)
      {
        if (controller != null) controller.removePosListener(this);
        controller = p;
        if (controller != null)
        {
          controller.addPosListener(this);
          controller.setPosInfo(this);
        }
        repaint();
      }
    }

   /**
    * Gets the data model.
    * @return a data model.
    */
    public MatrixModel getModel () {
      return data;
    }

   /**
    * Gets the view provider.
    * @return a view provider.
    */
    public /*C#virtual*/ LwGridViewProvider getViewProvider() {
      return provider;
    }

   /**
    * Sets the view provider.
    * @param <code>p</code> the view provider.
    */
    public /*C#virtual*/ void setViewProvider(LwGridViewProvider p)
    {
      if (provider != p)
      {
        provider = p;
        iMetric();
        repaint();
      }
    }

   /**
    * Sets the data model.
    * @param <code>d</code> the data model.
    */
    public /*C#virtual*/ void setModel (MatrixModel d)
    {
      if (d != data)
      {
        if (data != null) data.removeMatrixListener(this);
        data = d;
        if (data != null) data.addMatrixListener(this);
        iMetric();
        repaint();
      }
    }

   /**
    * Sets the selection marker color for the specified grid state. There are two possible states:
    * <ul>
    *   <li>The grid has focus.</li>
    *   <li>The grid has not focus.</li>
    * </ul>
    * @param <code>c</code> the selection marker color.
    * @param <code>hasFocus</code> the specified state. Use <code>true</code> for "grid has focus"
    * state and <code>false</code> for "grid has not focus" state.
    */
    public void setSelectColor (Color c, boolean hasFocusVal)
    {
       Color old = getSelectColor(hasFocusVal);
       if (!c.equals(old))
       {
         if (hasFocusVal)  actSelColor     = c;
         else              noneActSelColor = c;
         if (hasFocus() && hasFocusVal) repaint();
       }
    }

   /**
    * Gets the selection marker color for the specified grid state. There are two possible states:
    * <ul>
    *   <li>The grid has focus.</li>
    *   <li>The grid has not focus.</li>
    * </ul>
    * @param <code>hasFocus</code> the specified state. Use <code>true</code> for "grid has focus"
    * state and <code>false</code> for "grid has not focus" state.
    * @return a selection marker color.
    */
    public Color getSelectColor (boolean hasFocus) {
      return hasFocus?actSelColor:noneActSelColor;
    }

   /**
    * Gets the grid lines color.
    * @return a grid lines color.
    */
    public Color getNetColor () {
      return netColor;
    }

    public Insets getCellInsets () {
      return new Insets (cellInsets.top, cellInsets.left, cellInsets.bottom, cellInsets.right);
    }

   /**
    * Sets the grid cells insets.
    * @param <code>t</code> the top cell indent.
    * @param <code>l</code> the left cell indent.
    * @param <code>b</code> the bottom cell indent.
    * @param <code>r</code> the right cell indent.
    */
    public void setCellInsets (int t, int l, int b, int r)
    {
      Insets i = new Insets ((t<0)?cellInsets.top:t, (l<0)?cellInsets.left:l,
                             (b<0)?cellInsets.bottom:b, (r<0)?cellInsets.right:r);
      if (!i.equals (cellInsets))
      {
        cellInsets = i;
        iMetric();
        repaint();
      }
    }

   /**
    * Sets the grid lines color.
    * @param <code>c</code> the grid lines color.
    */
    public void setNetColor (Color c)
    {
      if (!c.equals(netColor))
      {
        netColor = c;
        repaint();
      }
    }

    public void matrixResized(MatrixEvent e)
    {
      iMetric();
      if (controller != null && controller.getCurrentLine() >= getGridRows())
        controller.clearPos();
    }

    public void cellModified (MatrixEvent e) {
      if (isUsePsMetric()) iMetric();
    }

    public /*C#override*/ void paint(Graphics g)
    {
      vVisibility();
      if (visibility.hasVisibleCells())
      {
        paintData  (g);
        paintNet (g);
      }
    }

    public /*C#override*/ void paintOnTop(Graphics g) {
      vVisibility();
      if (visibility.hasVisibleCells()) paintMarker(g);
    }

    public /*C#override*/ void validate () {
      vMetric();
      super.validate();
    }

    public /*C#override*/ void invalidate()
    {
      super.invalidate();
      iColVisibility();
      iRowVisibility();
    }

    public /*C#virtual*/ void setRowHeight (int row, int h)
    {
      if (!isUsePsMetric())
      {
        vMetric();
        if (rowHeight(row) != h)
        {
          stopEditing(false);
          int dy = h - rowHeight(row);
          rowHeights[row] = h;
          psSize.height += dy;
          updateCashedPs(-1, psSize.height);
          if (man != null) man.scrollObjResized (psSize.width, psSize.height);
          vrp();
        }
      }
    }

    public /*C#virtual*/ void setColWidth (int col, int w)
    {
      if (!isUsePsMetric())
      {
        vMetric();
        if (colWidth(col) != w)
        {
          stopEditing(false);
          int dx = w - colWidth(col);
          colWidths[col] = w;
          psSize.width += dx;
          updateCashedPs(psSize.width, -1);
          if (man != null) man.scrollObjResized (psSize.width, psSize.height);
          vrp();
        }
      }
    }

    public /*C#override*/ Point getOrigin () {
      return new Point (dx, dy);
    }

    public /*C#virtual*/ Point getSOLocation() {
      return getOrigin ();
    }

    public /*C#virtual*/ void setSOLocation(int x, int y)
    {
      if (x != dx || y != dy)
      {
        int offx = x - dx, offy = y - dy;
        dx = x;
        dy = y;
        if (offx != 0) iColVisibility();
        if (offy != 0) iRowVisibility();
        rVisibility(offx, offy);
        stopEditing(false);
        repaint();
      }
    }

    public /*C#virtual*/ Dimension getSOSize() {
      return getPreferredSize();
    }

    public boolean moveContent() {
      return true;
    }

    public void setScrollMan (ScrollMan m) {
      man = m;
    }

    public /*C#virtual*/ void mouseClicked (LwMouseEvent e) {}
    public /*C#virtual*/ void mouseEntered (LwMouseEvent e) {}
    public /*C#virtual*/ void mouseExited  (LwMouseEvent e) {}
    public /*C#virtual*/ void mouseReleased(LwMouseEvent e) {}

    public /*C#virtual*/ void mousePressed (LwMouseEvent e)
    {
      if (visibility.hasVisibleCells())
      {
        stopEditing(true);
        if (LwToolkit.isActionMask(e.getMask()))
        {
          Point p = cellByLocation(e.getX(), e.getY());
          if (p != null)
          {
            if (controller != null)
            {
              int off = controller.getCurrentLine();
              if (off == p.x) calcOrigin(off, getRowY(off));
              else controller.setOffset (p.x);
            }

            if (editors != null && editors.shouldStartEdit(p.x, p.y, e))
              startEditing(p.x, p.y);
          }
        }
      }
    }

    public int getLines() {
      return getGridRows();
    }

    public int getLineSize(int line) {
      return 1;
    }

    public int getMaxOffset() {
      return getGridRows()-1;
    }

    public /*C#virtual*/ void posChanged(PosEvent e)
    {
      int off = controller.getCurrentLine();

      if (off >= 0)
      {
        int y = getRowY(off);
        Point o = calcOrigin(off, y);
        int poff = e.getPrevOffset();
        if (poff >= 0)
        {
          int yy = getRowY(poff);
          repaint (0, Math.min(yy, y) + dy, width, Math.abs(yy - y) + rowHeight(Math.max(off, poff)));
        }
        else repaint ();
      }
    }

    public /*C#virtual*/ void keyPressed(LwKeyEvent e)
    {
      if (controller != null)
      {
        boolean ctrl = (e.getMask() & KeyEvent.CTRL_MASK) > 0;
        switch (e.getKeyCode())
        {
          case KeyEvent.VK_LEFT     : controller.seek(-1); break;
          case KeyEvent.VK_UP       : controller.seekLineTo(PosController.UP); break;
          case KeyEvent.VK_RIGHT    : controller.seek(1); break;
          case KeyEvent.VK_DOWN     : controller.seekLineTo(PosController.DOWN); break;
          case KeyEvent.VK_PAGE_UP  : controller.seekLineTo(PosController.UP,   pageSize(-1)); break;
          case KeyEvent.VK_PAGE_DOWN: controller.seekLineTo(PosController.DOWN, pageSize(1)); break;
          case KeyEvent.VK_END      : if (ctrl) controller.setOffset(getLines()-1); break;
          case KeyEvent.VK_HOME     : if (ctrl) controller.setOffset(0); break;
        }
      }
    }

    public /*C#virtual*/ void keyReleased(LwKeyEvent e) {}
    public /*C#virtual*/ void keyTyped   (LwKeyEvent e) {}
    public /*C#virtual*/ void focusGained(LwFocusEvent e) {}
    public /*C#virtual*/ void focusLost  (LwFocusEvent e) {}

    public /*C#virtual*/ void layout(LayoutContainer target)
    {
      if (topCaption != null && topCaption.isVisible())
      {
        Insets i = getInsets();
        topCaption.setLocation (i.left, i.top);
        Dimension d = topCaption.getPreferredSize();
        topCaption.setSize(Math.min(target.getWidth() - i.left - i.right, psSize.width), d.height);
      }

      if (editors != null && editor != null && editor.isVisible())
      {
        int w = getColWidth(editingCol), h = getRowHeight(editingRow);
        int x = getColX(editingCol), y = getRowY(editingRow);
        if (isUsePsMetric())
        {
          x += cellInsets.left;
          y += cellInsets.top;
          w -= (cellInsets.left + cellInsets.right);
          h -= (cellInsets.top  + cellInsets.bottom);
        }
        editor.setLocation(x + dx, y + dy);
        editor.setSize(w, h);
        LwToolkit.getFocusManager().requestFocus((LwComponent)editor);
      }
    }

    public /*C#virtual*/ void componentAdded(Object id, Layoutable lw, int index)
    {
      if (id != null)
      {
        if (id.equals(TOP_CAPTION_EL)) topCaption = (LwComponent)lw;
        else
        if (id.equals(EDITOR_EL)) editor = (LwComponent)lw;
      }
    }

    public /*C#virtual*/ void componentRemoved(Layoutable lw, int index)
    {
      if (lw == editor) editor = null;
      else
      if (lw == topCaption) topCaption = null;
    }

    public /*C#virtual*/ Dimension calcPreferredSize(LayoutContainer target) {
      return new Dimension (psSize.width, psSize.height + getTopCaptionHeight());
    }

    public /*C#virtual*/ int getGridRows() {
      return data.getRows();
    }

    public /*C#virtual*/ int getGridCols() {
      return data.getCols();
    }

    public /*C#virtual*/ int getRowHeight(int row) {
      vMetric();
      return rowHeight(row);
    }

    public /*C#virtual*/ int getColWidth (int col) {
      vMetric();
      return colWidth(col);
    }

    public CellsVisibility getCellsVisibility() {
      vVisibility();
      return visibility;
    }

    public int getNetGap() {
      return netSize;
    }

    public /*C#virtual*/ int getColX (int col)
    {
      vVisibility();
      int start = 0, d = 1;
      int x = getInsets().left + netSize;
      if (visibility.hasVisibleCells())
      {
        start = visibility.fc.x;
        x     = visibility.fc.y;
        d     = (col > visibility.fc.x)?1:-1;
      }
      for (int i=start; i != col; x += ((colWidth(i) + netSize)*d), i+=d);
      return x;
    }

    public /*C#virtual*/ int getRowY (int row)
    {
      vVisibility();
      int start = 0, d = 1;
      int y = getInsets().top + getTopCaptionHeight() + netSize;
      if (visibility.hasVisibleCells())
      {
        start = visibility.fr.x;
        y     = visibility.fr.y;
        d     = (row > visibility.fr.x)?1:-1;
      }
      for (int i=start; i != row; y += ((rowHeight(i) + netSize)*d), i+=d);
      return y;
    }

    public void childPerformed(LwAWTEvent e)
    {
      if (e.getID() == LwKeyEvent.KEY_PRESSED &&
          LwToolkit.CANCEL_KEY == LwToolkit.getKeyType(((LwKeyEvent)e).getKeyCode(), ((LwKeyEvent)e).getMask()))
      {
        stopEditing(false);
      }
    }

   /**
    * Starts editing of the specified grid cell. The method initiates editing process if
    * the editor provider has been defined for the grid component and the editor component
    * exists.
    * @param <code>row</code> the specified cell row.
    * @param <code>col</code> the specified cell column.
    */
    public /*C#virtual*/ void startEditing (int row, int col)
    {
      stopEditing (true);
      if (editors != null)
      {
        LwComponent editor = editors.getEditor(row, col, getDataToEdit(row, col));
        if (editor != null)
        {
          editingRow = row;
          editingCol = col;
          add(EDITOR_EL, editor);
          repaint();
        }
      }
    }

   /**
    * Stops the cell editing. The method has effect if the editing process has been initiated
    * before.
    * @param <code>applyData</code> use <code>true</code> value if the edited data should be
    * applied to data model, use <code>false</code> otherwise.
    */
    public /*C#virtual*/ void stopEditing (boolean applyData)
    {
      if (editors != null && editingRow >= 0 && editingCol >= 0)
      {
        try {
          Object v = editors.fetchEditedValue (editingRow, editingCol, (LwComponent)editor);
          if (applyData) setEditedData(editingRow, editingCol, v);
        }
        finally
        {
          editingRow = -1;
          editingCol = -1;
          int index = indexOf((LwComponent)editor);
          if (index >= 0) remove(index);
          repaint();
        }
      }
    }

   /**
    * Gets the grid top caption component.
    * @return a grid top caption component.
    */
    public LwComponent getTopCaption() {
      return topCaption;
    }

   /**
    * Returns the specified column width. The method is used by all other methods
    * (except recalculation) to get the actual column width.
    * @param <code>col</code> the specified column.
    * @return the specified column width.
    */
    protected /*C#virtual*/ int colWidth (int col) {
      return colWidths[col];
    }

   /**
    * Returns the specified row height. The method is used by all other methods
    * (except recalculation) to get the actual row height.
    * @param <code>row</code> the specified row.
    * @return the specified row height.
    */
    protected /*C#virtual*/ int rowHeight (int row) {
      return rowHeights[row];
    }

   /**
    * Invoked whenever the component wants fetch data from the data model for
    * the specified cell editing.
    * @param <code>row</code> the specified row.
    * @param <code>col</code> the specified column.
    * @return data model value to edit.
    */
    protected /*C#virtual*/ Object getDataToEdit (int row, int col) {
      return data.get(row, col);
    }

   /**
    * Invoked whenever the component wants applies the edited value (for the specified cell)
    * to the grid data model.
    * @param <code>row</code> the specified row.
    * @param <code>col</code> the specified column.
    * @param <code>value</code> the specified edited value.
    */
    protected /*C#virtual*/ void setEditedData (int row, int col, Object value) {
      data.put (row, col, value);
    }

   /**
    * Checks if the grid metric is valid.
    * @return <code>true</code> if the grid metric is valid; <code>false</code> otherwise.
    */
    protected /*C#virtual*/ boolean isMetricValid() {
      return MathBox.checkBit(bits, METRIC_VALID);
    }

   /**
    * Invoked whenever the component paints the specified cell to fetch data from the
    * grid data model.
    * @param <code>row</code> the specified row.
    * @param <code>col</code> the specified column.
    * @return data to be painted.
    */
    protected /*C#virtual*/ Object dataToPaint (int row, int col) {
      return data.get(row, col);
    }

   /**
    * Validates the grid metric.
    */
    protected void vMetric()
    {
      if (!isMetricValid())
      {
        if (isUsePsMetric()) rPsMetric();
        else                 rCustomMetric();
        psSize = rPs();
        bits = MathBox.getBits(bits, METRIC_VALID, true);
      }
    }

   /**
    * Validates the grid visibility. The method validates the grid metrics
    * (by <code>vMetric</code> method) before.
    */
    protected void vVisibility() {
      vMetric();
      if (getVisiblePart() != null) rVisibility(0, 0);
    }

   /**
    * Calculates the preferred size of the grid component. The method calls
    * <code>colWidth</code> and <code>rowHeight</code> to get actual columns
    * widths and rows heights. The method is called by <code>vMetric</code>
    * method.
    * @return a calculated preferred size;
    */
    protected Dimension rPs()
    {
      int cols = getGridCols();
      int rows = getGridRows();
      int psWidth  = netSize * (cols + 1);
      int psHeight = netSize * (rows + 1);
      for (int i=0; i<cols; i++) psWidth  += colWidth(i);
      for (int i=0; i<rows; i++) psHeight += rowHeight(i);
      return new Dimension (psWidth, psHeight);
    }

   /**
    * Paints the grid lines.
    * @param <code>g</code> the specified graphics context.
    */
    protected /*C#virtual*/ void paintNet (Graphics g)
    {
      int topX = visibility.fc.y - netSize;
      int topY = visibility.fr.y - netSize;
      int botX = visibility.lc.y + colWidth (visibility.lc.x);
      int botY = visibility.lr.y + rowHeight(visibility.lr.x);

      g.setColor(netColor);
      if (MathBox.checkBit(bits, DRAW_HLINES))
      {
        int y = topY, i = visibility.fr.x;
        for (;i <= visibility.lr.x; i++)
        {
          g.drawLine(topX, y, botX, y);
          y += rowHeight(i) + netSize;
        }
        g.drawLine(topX, y, botX, y);
      }

      if (MathBox.checkBit(bits, DRAW_VLINES))
      {
        int x = topX, i = visibility.fc.x;
        for (;i <= visibility.lc.x; i++)
        {
          g.drawLine(x, topY, x, botY);
          x += colWidth(i) + netSize;
        }
        g.drawLine(x, topY, x, botY);
      }
    }

   /**
    * Paints the grid cells.
    * @param <code>g</code> the specified graphics context.
    */
    protected /*C#virtual*/ void paintData (Graphics g)
    {
      int y    = visibility.fr.y + cellInsets.top;
      int addW = cellInsets.left + cellInsets.right;
      int addH = cellInsets.top  + cellInsets.bottom;
      Rectangle r = g.getClipBounds();

      for (int i=visibility.fr.x; i<=visibility.lr.x && y < r.y + r.height; i++)
      {
        if (y + rowHeight(i) > r.y)
        {
          int x = visibility.fc.y + cellInsets.left;
          for (int j=visibility.fc.x; j<=visibility.lc.x; j++)
          {
             LwView v = provider.getView(i, j, dataToPaint(i, j));
             if (v != null && !(i == editingRow && j == editingCol))
             {
               Color bg = provider.getCellColor(i, j);
               if (bg != null)
               {
                 g.setColor(bg);
                 g.fillRect(x - cellInsets.left, y - cellInsets.top, colWidth(j), rowHeight(i));
               }

               int w = colWidth (j) - addW;
               int h = rowHeight(i) - addH;
               g.clipRect(x, y, w, h);
               if (isUsePsMetric() || v.getType() == LwView.STRETCH) v.paint(g, x, y, w, h, this);
               else
               {
                 Dimension ps = v.getPreferredSize();
                 Point p = Alignment.getLocation (ps,
                                                  provider.getXAlignment(i, j),
                                                  provider.getYAlignment(i, j),
                                                  w, h);
                 v.paint(g, x + p.x, y + p.y, ps.width, ps.height, this);
               }
               g.setClip (r);
             }
             x += (colWidth(j) + netSize);
          }
        }
        y += (rowHeight(i) + netSize);
      }
    }

   /**
    * Paints the grid marker.
    * @param <code>g</code> the specified graphics context.
    */
    protected /*C#virtual*/ void paintMarker (Graphics g)
    {
      if (controller != null && controller.getOffset() >= 0)
      {
        //???
        g.clipRect(-dx, getTopCaptionHeight() - dy, width, height);

        int offset = controller.getOffset();
        int y = getRowY(offset), h = rowHeight(offset);
        int x = visibility.fc.y;

        for (int i=visibility.fc.x; i<=visibility.lc.x; i++)
        {
          if (i != editingCol || editingRow != offset)
          {
            Color bg = provider.getCellColor(offset, i);
            if (bg == null) bg = getBackground();
            LwToolkit.drawMarker(g, x, y, colWidth(i), h, bg, hasFocus()?actSelColor:noneActSelColor);
          }
          x += colWidth(i) + netSize;
        }
      }
    }

    protected /*C#override*/ LwLayout getDefaultLayout() {
      return this;
    }

   /**
    * Finds and returns grid cell row and column at the specified location.
    * The result is presented with java.awt.Point class where <code>x</code>
    * field correspond to row and <code>y</code> field correspond to column.
    * @param <code>x</code> the specified x coordinate.
    * @param <code>y</code> the specified y coordinate.
    * @return a cell at the specified location.
    */
    protected /*C#virtual*/ Point cellByLocation(int x, int y)
    {
      vVisibility();
      int ry1 = visibility.fr.y + dy;
      int ry2 = visibility.lr.y + rowHeight(visibility.lr.x) + dy;
      int rx1 = visibility.fc.y + dx;
      int rx2 = visibility.lc.y + colWidth(visibility.lc.x) + dx;

      int row = -1, col = -1;

      if (y > ry1 && y < ry2)
      {
         for (int i = visibility.fr.x; i<=visibility.lr.x; ry1 += rowHeight(i) + netSize, i++)
           if (y > ry1 && y < ry1 + rowHeight(i))
           {
             row = i;
             break;
           }
      }

      if (x > rx1 && x < rx2)
      {
         for (int i = visibility.fc.x; i<=visibility.lc.x; rx1 += colWidth(i) + netSize, i++)
           if (x > rx1 && x < rx1 + colWidth(i))
           {
             col = i;
             break;
           }
      }
      return (col >= 0 && row >=0)?new Point(row, col):null;
    }

   /**
    * Invoked by <code>vMetric</code> method to calculate preferred size metric type.
    */
    protected /*C#virtual*/ void rPsMetric()
    {
      colWidths  = new int[getGridCols()];
      rowHeights = new int[getGridRows()];

      int addW = cellInsets.left + cellInsets.right;
      int addH = cellInsets.top  + cellInsets.bottom;
      for (int i=0; i<colWidths.length ;i++)
      {
        for (int j=0; j<rowHeights.length; j++)
        {
          LwView v = provider.getView(j, i, data.get(j, i));
          if (v != null)
          {
            Dimension ps = v.getPreferredSize();
            ps.width  += addW;
            ps.height += addH;
            if (ps.width  > colWidths[i] ) colWidths [i] = ps.width;
            if (ps.height > rowHeights[j]) rowHeights[j] = ps.height;
          }
          else
          {
            if (DEF_COLWIDTH  > colWidths [i]) colWidths [i] = DEF_COLWIDTH;
            if (DEF_ROWHEIGHT > rowHeights[j]) rowHeights[j] = DEF_ROWHEIGHT;
          }
        }
      }
    }

   /**
    * Invoked by <code>vMetric</code> method to calculate custom metric type.
    */
    protected /*C#virtual*/ void rCustomMetric()
    {
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
      for (; start<colWidths.length; start++) colWidths[start] = DEF_COLWIDTH;

      start = 0;
      if (rowHeights != null)
      {
        start = rowHeights.length;
        if (rowHeights.length != getGridRows())
        {
          int[] na = new int[getGridRows()];
          System.arraycopy(rowHeights, 0, na, 0, Math.min(rowHeights.length, na.length));
          rowHeights = na;
        }
      }
      else rowHeights = new int[getGridRows()];
      for (;start<rowHeights.length; start++) rowHeights[start] = DEF_ROWHEIGHT;
    }

   /**
    * Invalidates grid metric.
    */
    protected void iMetric()  {
      bits = MathBox.getBits(bits, METRIC_VALID, false);
      invalidate();
    }

   /**
    * Returns the top caption size.
    * @return a top caption size.
    */
    protected /*C#virtual*/ int getTopCaptionHeight() {
      return (topCaption != null && topCaption.isVisible())?topCaption.getPreferredSize().height:0;
      //return (topCaption != null && topCaption.isVisible())?topCaption.getHeight():0;
    }

   /**
    * Returns the page size for the specified direction.
    * @param <code>d</code> the specified direction. Use <code>-1</code> value to specify bottom-up direction and
    * <code>1</code> value to specify up-bottom direction.
    * @return a page size.
    */
    protected /*C#virtual*/ int pageSize(int d)
    {
      int off = controller.getOffset();
      if (off >= 0)
      {
         Rectangle visibleArea = getVisiblePart();
         visibleArea.height -= getTopCaptionHeight();

         int sum = 0, poff = off;
         for (;off >=0 && off < getGridRows() && sum < visibleArea.height; sum += rowHeight(off) + netSize, off+=d);
         return Math.abs(poff - off);
      }
      return 0;
    }

   /**
    * Invalidates columns visibility properties.
    */
    protected /*C#virtual*/ void iColVisibility() {
      bits = MathBox.getBits(bits, COLVIS_VALID, false);
    }

   /**
    * Invalidates rows visibility properties.
    */
    protected /*C#virtual*/ void iRowVisibility() {
      bits = MathBox.getBits(bits, ROWVIS_VALID, false);
    }

    private Point colVisibility(Point p, int d, boolean b)
    {
      Rectangle visibleArea = getVisiblePart();
      int       cols        = getGridCols();
      if (cols == 0 || p == null) return null;

      int x   = p.y;
      int col = p.x;
      for (;col < cols && col >=0; col+=d)
      {
        if (x + dx < (visibleArea.x + visibleArea.width) && (x + colWidth(col) + dx) > visibleArea.x)
        {
          if (b) return new Point (col, x);
        }
        else if (!b) return colVisibility(new Point(col, x), (d > 0?-1:1), true);

        if (d < 0) {
          if (col > 0) x -= (colWidth(col - 1) + netSize);
        }
        else if (col < cols - 1) x += (colWidth(col) + netSize);
      }

      if (!b)
        return (d > 0)?new Point(col - 1, x):
                       new Point(0, getInsets().left + netSize);
      return null;
    }

    private Point rowVisibility(Point p, int d, boolean b)
    {
      Rectangle visibleArea = getVisiblePart();
      int       rows        = getGridRows();
      if (rows == 0 || p == null) return null;

      int y   = p.y;
      int row = p.x;
      for (;row < rows && row >= 0; row+=d)
      {
        if (y + dy < (visibleArea.y + visibleArea.height) && (y + rowHeight(row) + dy) > visibleArea.y)
        {
          if (b) return new Point (row, y);
        }
        else if (!b) return rowVisibility(new Point(row, y), (d > 0?-1:1), true);

        if (d < 0)  {
          if (row > 0) y -= (rowHeight(row - 1) + netSize);
        }
        else if (row < rows - 1) y += (rowHeight(row) + netSize);
      }

      if (!b)
        return (d > 0)?new Point(row - 1, y):
                       new Point(0, getInsets().top + getTopCaptionHeight() + netSize);
      return null;
    }

    private void rVisibility (int offx, int offy)
    {
      boolean b = visibility.hasVisibleCells();
      if (!MathBox.checkBit(bits, COLVIS_VALID))
      {
        if (offx > 0 && b)
        {
          visibility.lc = colVisibility(visibility.lc, -1, true);
          visibility.fc = colVisibility(visibility.lc,-1, false);
        }
        else
        if (offx < 0 && b)
        {
          visibility.fc = colVisibility(visibility.fc, 1, true);
          visibility.lc = colVisibility(visibility.fc, 1, false);
        }
        else
        {
          visibility.fc = new Point(0, getInsets().left + netSize);
          visibility.fc = colVisibility(visibility.fc, 1, true);
          visibility.lc = colVisibility(visibility.fc, 1, false);
        }
        bits = MathBox.getBits(bits, COLVIS_VALID, true);
      }

      if (!MathBox.checkBit(bits, ROWVIS_VALID))
      {
        if (offy > 0 && b)
        {
          visibility.lr = rowVisibility(visibility.lr,  -1, true);
          visibility.fr = rowVisibility(visibility.lr , -1, false);
        }
        else
        if (offy < 0 && b)
        {
          visibility.fr = rowVisibility(visibility.fr, 1, true);
          visibility.lr = rowVisibility(visibility.fr, 1, false);
        }
        else
        {
          visibility.fr = new Point(0, getInsets().top + getTopCaptionHeight() + netSize);
          visibility.fr = rowVisibility(visibility.fr, 1, true);
          visibility.lr = rowVisibility(visibility.fr, 1, false);
        }
        bits = MathBox.getBits(bits, ROWVIS_VALID, true);
      }
    }

    private Point calcOrigin(int off, int y)
    {
      Insets i = getInsets();
      i.top += getTopCaptionHeight();
      Point o = LwToolkit.calcOrigin(getColX(0) - netSize, y - netSize, psSize.width, rowHeight(off) + 2*netSize, dx, dy, this, i);
      if (man != null) man.scrollObjMoved(o.x, o.y);
      else             setSOLocation(o.x, o.y);
      return o;
    }
 }


