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
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.data.*;

/**
 * This class is a text field component. The library provides several text renders implementations
 * that can be used as a face view of the component:
 * <ul>
 *   <li>
 *      LwAdvTextRender. This text render is used as default text render.
 *   </li>
 *   <li>LwTextRender. This text render is base for LwAdvTextRender render.</li>
 *   <li>
 *     LwPasswordText. This text render is used for password text fields.
 *     The sample below shows the render usage:
 *     <pre>
 *        ...
 *        LwTextFiled tf = new LwTextFiled("Password text");
 *        tf.getViewMan(true).setView(new LwPasswordText(tf.getText()));
 *        ...
 *     </pre>
 *    </li>
 * </ul>
 * <p>
 * The list below describes some features of the component:
 * <ul>
 *   <li>
 *     Use following code to organize single line text field component with a fixed number
 *     of characters
 *     <pre>
 *        ...
 *        // Creates single line text field component, where it is possible to
 *        // enter at most 10 characters.
 *        LwTextFiled tf = new LwTextFiled("", 10);
 *        ...
 *     </pre>
 *   </li>
 *   <li>
 *     Use <code>setPSByRowsCols</code> method to define preferred size of the component
 *     basing on the number of rows and columns. The method calculates and sets preferred size of
 *     the text field component according to row size and column size. Draw attention that
 *     if the font has been redefined for the text render, than it is necessary to call
 *     the method again to recalculate his preferred size.
 *   </li>
 *   <li>
 *     It is possible to redefined text cursor view by <code>setCursorView</code> method.
 *   </li>
 *   <li>
 *     It is possible to define another pos controller by <code>setPosController</code>
 *     method to control text cursor position.
 *   </li>
 *   <li>
 *     Use <code>setEditable</code> method to define if a text can be edited or not.
 *   </li>
 *   <li>
 *     Use <code>getSelectedText</code> method to get selected text.
 *   </li>
 * </ul>
 * <p>
 * The component overrides the <code>getOrigin</code> method to organize scrolling of the
 * content. The scrolling mechanism works right if the component will be inserted
 * into a LwScrollPan component.
 * <p>
 * To listen when the cursor position has been changed use the pos controller as follow:
 * <pre>
 *   public class Sample
 *   implements PosListener
 *   {
 *     ...
 *     public void init()
 *     {
 *       ...
 *       LwTextFiled tf = new LwTextFiled("Text");
 *       tf.getPosController().addPosListener(this);
 *       ...
 *     }
 *     public void posChanged(PosEvent e) {
 *       System.out.println("The old cursor location is - " + e.getPrevOffset());
 *     }
 *   }
 * </pre>
 */
public class LwTextField
extends LwLabel
implements LwKeyListener, LwMouseListener, LwMouseMotionListener,
           LwFocusListener, Cursorable, PosInfo, PosListener, ScrollObj,
           TxtSelectionInfo
{
  private Color         selectColor = LwToolkit.darkBlue;
  private LwView        curView;
  private PosController cur;
  private ScrollMan     man;
  private int           cx = -1, cy = -1, dx = 0, dy = 0;
  private int           startLine = -1, startCol, startOff, endLine, endCol, endOff;
  private boolean       isEditableVal = true;

 /**
  * Constructs a text filed component with no text.
  */
  public LwTextField() {
    this("");
  }

 /**
  * Constructs a text field component with the specified text. The methods sets LwAdvTextRender
  * as the face view and sets org.zaval.data.Text as the render target.
  * @param <code>s</code> the specified text.
  */
  public LwTextField(String s) {
    this(new Text(s));
  }

 /**
  * Constructs a single line text field component with the specified text and
  * the maximal number of columns.
  * @param <code>s</code> the specified text.
  * @param <code>maxCol</code> the specified maximal number of columns.
  */
  public LwTextField(String s, int maxCol) {
    this (new SingleLineTxt(s, maxCol));
    if (maxCol > 0) setPSByRowsCols(-1, maxCol);
  }

 /**
  * Constructs a text field component with the specified text model.
  * @param <code>model</code> the specified text model.
  */
  public LwTextField(TextModel model)
  {
    super (model);
    setPosController(new PosController(this));
    setCursorView(LwToolkit.getView("txt.cur"));
    getViewMan(true).setBorder(LwToolkit.getView("br.sunken"));
  }

  public /*C#override*/ boolean canHaveFocus() {
    return true;
  }

 /**
  * Gets the pos controller that manages the text cursor position.
  * @return a pos controller.
  */
  public PosController getPosController() {
    return cur;
  }

 /**
  * Sets the specified pos controller to manage the text cursor position.
  * @param <code>p</code> the specified pos controller.
  */
  public void setPosController(PosController p)
  {
    if (cur != p)
    {
      if (cur != null) cur.removePosListener(this);
      cur = p;
      cur.setPosInfo(this);
      cur.addPosListener(this);
      invalidate();
    }
  }

 /**
  * Gets the cursor view that is used to paint the text cursor.
  * @return a cursor view.
  */
  public LwView  getCursorView() {
    return curView;
  }

 /**
  * Sets the specified cursor view to render the text cursor.
  * @param <code>v</code> the specified cursor view.
  */
  public void setCursorView(LwView v)
  {
    if (v != curView) {
      curView = v;
      repaint();
    }
  }

 /**
  * Sets the preferred size for the text field component that conforms to the
  * specified number of rows and columns.
  * @param <code>r</code> the specified number of rows.
  * @param <code>c</code> the specified number of columns.
  */
  public void setPSByRowsCols(int r, int c)
  {
    LwTextRender tr = getTextRender();
    int w = (c > 0)? (tr.stringWidth("W") * c):psWidth;
    int h = (r > 0)? (r * tr.getLineHeight() + (r - 1) * tr.getLineIndent()):psHeight;
    setPSSize(w, h);
  }

 /**
  * Paints this component. The method initiates painting of the cursor caret by calling
  * <code>drawCursor</code> method.
  * @param <code>g</code> the graphics context to be used for painting.
  */
  public /*C#override*/ void paint(Graphics g) {
    super.paint(g);
    drawCursor(g);
  }

 /**
  * Sets the specified mode for the text field component. The component is editable if
  * it is possible to edit a text.
  * @param <code>b</code> the specified mode. If the mode is <code>true</code> than the component
  * is editable; otherwise not-editable.
  */
  public void setEditable (boolean b) {
    if (b != isEditableVal) isEditableVal = b;
  }

 /**
  * Checks if the text field component is editable.
  * @return <code>true</code> if the text field is editable; <code>false</code>
  * otherwise.
  */
  public boolean isEditable() {
    return isEditableVal;
  }

  public void keyPressed(LwKeyEvent e) {
    if (!isFiltered(e)) handleKey(e);
  }

  public void keyTyped(LwKeyEvent e)
  {
    if ((e.getMask()&InputEvent.CTRL_MASK) == 0)
    {
      char ch = (char)e.getKeyChar();
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_ENTER       : if (getTextModel() instanceof SingleLineTxt) return;
                                       else ch = '\n'; break;
        case KeyEvent.VK_ESCAPE      :
        case KeyEvent.VK_TAB         :
        case KeyEvent.VK_BACK_SPACE  : return;
      }

      if (hasSelection())
      {
        Point p = getSelectionOffsets();
        remove(p.x, p.y - p.x);
        stopSelection();
      }
      write(cur.getOffset(), String.valueOf(ch));
    }
  }

  public void focusGained  (LwFocusEvent e) {
    if (cur.getOffset() < 0) cur.setOffset(0);
  }

  public void focusLost    (LwFocusEvent e) {}
  public void keyReleased  (LwKeyEvent e)   {}
  public void mouseClicked (LwMouseEvent e) {}
  public void mouseEntered (LwMouseEvent e) {}
  public void mouseExited  (LwMouseEvent e) {}
  public void startDragged (LwMouseMotionEvent e) { startSelection(); }
  public void endDragged   (LwMouseMotionEvent e) {}
  public void mouseReleased(LwMouseEvent e) {}
  public void mouseMoved   (LwMouseMotionEvent e) {}

  public void mouseDragged (LwMouseMotionEvent e) {
    Point p = getTextRowColAt(getTextRender(), e.getX() - dx, e.getY() - dy);
    if (p != null) cur.setRowCol(p.x, p.y);
  }

  public void mousePressed (LwMouseEvent e)
  {
    if (LwToolkit.isActionMask(e.getMask()))
    {
      if ((e.getMask() & KeyEvent.SHIFT_MASK) > 0) startSelection();
      else stopSelection();
      Insets i = getInsets();
      Point p = getTextRowColAt(getTextRender(), e.getX() - dx - i.left, e.getY() - dy - i.top);
      if (p != null) cur.setRowCol(p.x, p.y);
    }
  }

 /**
  * Gets the cursor type for the specified location of the given component.
  * The method returns Cursor.TEXT_CURSOR cursor type.
  * @param <code>target</code> the given component.
  * @param <code>x</code> the x coordinate.
  * @param <code>y</code> the y coordinate.
  * @return a cursor type.
  */
  public int getCursorType (LwComponent target, int x, int y) {
    return Cursor.TEXT_CURSOR;
  }

  public int getLineSize(int i) {
    return getTextModel().getLine(i).length() + 1;
  }

  public int getLines() {
    return getTextModel().getSize();
  }

  public int getMaxOffset() {
    return getTextModel().getTextLength();
  }

  public /*C#override*/ void setText(String s)
  {
    cur.setOffset(0);
    if (man != null) man.scrollObjMoved(0, 0);
    else             setSOLocation(0, 0);
    super.setText(s);
  }

  public /*C#virtual*/ void posChanged(PosEvent e)
  {
    cur.validate();
    if (cur.getOffset() >=0)
    {
      LwTextRender r = getTextRender();
      Point  p = getTextLocationAt(r, cur);
      Insets i = getInsets();

      cx = p.x + i.left;
      cy = p.y + i.top;
      Point o = LwToolkit.calcOrigin(cx, cy, curView.getPreferredSize().width, r.getLineHeight(), this);
      if (man != null) man.scrollObjMoved(o.x, o.y);
      else             setSOLocation(o.x, o.y);

      boolean isSelStarted = isSelectionStarted();
      if (e.getPrevLine() >= 0 && !isSelStarted)
      {
        int minUpdatedLine = Math.min(e.getPrevLine(), cur.getCurrentLine());
        int maxUpdatedLine = Math.max(e.getPrevLine(), cur.getCurrentLine());

        int lh = r.getLineHeight(), li = r.getLineIndent();
        int y1 = lh * minUpdatedLine + minUpdatedLine * li + i.top + dy;
        int y2 = lh * maxUpdatedLine + maxUpdatedLine * li + i.top + dy;
        repaint (i.left, y1, width, y2 - y1 + r.getLineHeight()) ;
      }
      else
      {
        if (isSelStarted)
        {
          endLine = cur.getCurrentLine();
          endCol  = cur.getCurrentCol();
          endOff  = cur.getOffset();
        }
        repaint();
      }
    }
  }

 /**
  * Returns an origin of the component. The method is overrided with the component
  * to offset a content of the text field depending on the cursor position.
  * @return an origin of the component.
  */
  public /*C#override*/ Point getOrigin() {
    return new Point(dx, dy);
  }

  public /*C#override*/ Dimension getPreferredSize()
  {
    Dimension d1 = super.getPreferredSize();
    Dimension d2 = curView.getPreferredSize();
    d1.width += d2.width;
    return d1;
  }

  public Point getSOLocation () {
    return getOrigin();
  }

  public void setSOLocation (int x, int y)
  {
    if (x != dx || y != dy)
    {
      dx = x;
      dy = y;
      repaint();
    }
  }

  public Dimension getSOSize() {
    return getPreferredSize();
  }

  public void setScrollMan (ScrollMan m) {
    man = m;
  }

 /**
  * Tests if the scroll component performs scrolling by changing it location or view.
  * The method is overrided with the component to point move a content of the text field,
  * the method always returns <code>true</code>.
  * @return <code>true</code> if the scroll component organizes scrolling by moving
  * its view; otherwise <code>false</code>.
  */
  public boolean moveContent   () {
    return true;
  }

  public Point getStartSelection()
  {
    if (isSelectionStarted())
      return (startOff < endOff)?new Point(startLine, startCol)
                                :new Point(endLine, endCol);
    else return new Point(-1, -1);
  }

  public Point getEndSelection()
  {
    if (isSelectionStarted())
      return (startOff < endOff)?new Point(endLine, endCol)
                                :new Point(startLine, startCol);
    else return new Point(-1, -1);
  }

 /**
  * Tests if the a text part has been selected.
  * @return <code>true</code> if the text part has been selected; <code>false</code> otherwise.
  */
  public boolean hasSelection() {
    return isSelectionStarted() && startOff != cur.getOffset();
  }

 /**
  * Gets the start and last selection offsets.
  * @return the start and last selection offsets. The result is represented with
  * java.awt.Point class where <code>x</code> field corresponds to start selection offset and
  * <code>y</code> field corresponds to last selection offset.
  */
  public Point getSelectionOffsets()
  {
    if (isSelectionStarted())
      return (startOff < endOff)?new Point(startOff, endOff)
                                :new Point(endOff, startOff);
    else return new Point(-1, -1);
  }

  public String getSelectedText() {
    return hasSelection()?getSubString(getTextModel(), getStartSelection(), getEndSelection()):null;
  }

  public Color getSelectColor () {
    return selectColor;
  }

 /**
  * Sets the specified color to render selected text.
  * @param <code>c</code> the specified color.
  */
  public void setSelectColor (Color c)
  {
    if (!c.equals(selectColor)) {
      selectColor = c;
      if (hasSelection()) repaint();
    }
  }

  public /*C#override*/ void setEnabled (boolean b) {
    stopSelection();
    super.setEnabled(b);
  }

 /**
  * Selects the specified by the start and end offsets the text part.
  * @param <code>startOffset</code> the specified start offset.
  * @param <code>endOffset</code> the specified end offset.
  */
  public /*C#virtual*/ void select (int startOffset, int endOffset)
  {
    if (endOffset   <= startOffset ||
        startOffset < 0            ||
        endOffset   > cur.getMaxOffset()) throw new IllegalArgumentException();

    stopSelection();
    if (startOff != startOffset || endOffset != endOff)
    {
      startOff = startOffset;
      Point  p1 = PosController.getPointByOffset(startOffset, cur);
      startLine =  p1.x;
      startCol  =  p1.y;

      endOff = endOffset;
      Point  p2 = PosController.getPointByOffset(endOffset, cur);
      endLine = p2.x;
      endCol  = p2.y;

      repaint();
    }
  }

 /**
  * The method is used to paint the text cursor using the cursor view.
  * @param <code>g</code> the graphics context to be used for painting.
  */
  protected /*C#virtual*/ void drawCursor(Graphics g)  {
    if (isEditableVal && hasFocus() && cur.getOffset() >= 0)
      curView.paint(g, cx, cy, curView.getPreferredSize().width, getTextRender().getLineHeight() - 1, this);
  }

  protected /*C#override*/ void recalc()
  {
    super.recalc();
    stopSelection();
    if (cur.getOffset() >= 0)
    {
      cur.validate();
      int row = cur.getCurrentLine();
      int col = cur.getCurrentCol ();
      TextModel text = getTextModel();
      if (row >= text.getSize() || (row > 0 && col > text.getLine(row).length())) cur.setOffset(0);
    }
  }

 /**
  * Handles the specified key event.
  * @param <code>e</code> the specified key event to be handle.
  * @return <code>true</code> if the key event has been handled with the method; otherwise
  * <code>false</code>.
  */
  protected /*C#virtual*/ boolean handleKey(LwKeyEvent e)
  {
    int     mask          = e.getMask();
    boolean isControlDown = (mask & KeyEvent.CTRL_MASK ) > 0;
    boolean isShiftDown   = (mask & KeyEvent.SHIFT_MASK) > 0;
    if (isShiftDown) startSelection();
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_DOWN  : cur.seekLineTo(PosController.DOWN); break;
      case KeyEvent.VK_UP    : cur.seekLineTo(PosController.UP); break;
      case KeyEvent.VK_RIGHT :
      {
        if (isControlDown)
        {
          Point p = findNextWord(getTextModel(), cur.getCurrentLine(), cur.getCurrentCol(), 1);
          if (p != null) cur.setRowCol(p.x, p.y);
        }
        else cur.seek(1);
      } break;
      case KeyEvent.VK_LEFT  :
      {
        if (isControlDown)
        {
          Point p = findNextWord(getTextModel(), cur.getCurrentLine(), cur.getCurrentCol(), -1);
          if (p != null) cur.setRowCol(p.x, p.y);
        }
        else  cur.seek(-1);
      } break;
      case KeyEvent.VK_END   :
      {
        if (isControlDown) cur.seekLineTo(PosController.DOWN, cur.getPosInfo().getLines() - cur.getCurrentLine() - 1);
        else               cur.seekLineTo(PosController.END);
      } break;
      case KeyEvent.VK_HOME  :
      {
        if (isControlDown) cur.seekLineTo(PosController.UP, cur.getCurrentLine());
        else               cur.seekLineTo(PosController.BEG);
      } break;
      case KeyEvent.VK_PAGE_DOWN : cur.seekLineTo(PosController.DOWN, pageSize()); break;
      case KeyEvent.VK_PAGE_UP   : cur.seekLineTo(PosController.UP, pageSize()); break;
      case KeyEvent.VK_DELETE    :
      {
        if (hasSelection())
        {
          if (isShiftDown)
          {
            LwClipboardMan cm = getCM();
            if (cm != null) cm.put(getSelectedText());
          }
          Point p = getSelectionOffsets();
          remove(p.x, p.y - p.x);
        }
        else remove(cur.getOffset(), 1);
      } break;
      case KeyEvent.VK_BACK_SPACE: if (!hasSelection()) remove(cur.getOffset()-1, 1);
                                   else { Point p = getSelectionOffsets(); remove(p.x, p.y - p.x); }
                                   break;
      case KeyEvent.VK_INSERT    :
      {
        if (hasSelection() && isControlDown)
        {
          LwClipboardMan cm = getCM();
          if (cm != null) cm.put(getSelectedText());
          return true;
        }
        else
        if (isShiftDown)
        {
          LwClipboardMan cm = getCM();
          String s = cm == null?null:(String)cm.get();
          if (s != null)
          {
            if (hasSelection())
            {
              Point p = getSelectionOffsets();
              remove(p.x, p.y - p.x);
            }
            write(cur.getOffset(), s);
          }
        }
      } break;
      default: return false;
    }

    if (!isShiftDown) stopSelection();
    return true;
  }

 /**
  * Returns <code>true</code> if the key event should be handle next, returns
  * <code>false</code> if the handling process has to be terminated. The method
  * is called before any other event handler will be executed.
  * @param <code>e</code> the specified key event.
  * @return <code>true</code> if the key event should be handled.
  */
  protected /*C#virtual*/ boolean isFiltered(LwKeyEvent e)
  {
    int code = e.getKeyCode();
    return code == KeyEvent.VK_SHIFT || code == KeyEvent.VK_CONTROL ||
           code == KeyEvent.VK_TAB   || code == KeyEvent.VK_ALT ||
           (e.getMask() & KeyEvent.ALT_MASK) > 0;
  }

  protected /*C#override*/ LwTextRender makeTextRender(TextModel t) {
    return new LwAdvTextRender(t);
  }

 /**
  * Removes a part of the text starting from the given position and with the specified size.
  * @param <code>pos</code> the given position.
  * @param <code>size</code> the specified removed part size.
  */
  protected /*C#virtual*/ void remove(int pos, int size)
  {
    if (isEditableVal)
    {
      int max = cur.getMaxOffset();
      int pl  = getLines();
      if (pos >= 0 && (pos + size) <= max)
      {
        int old = cur.getOffset();
        cur.setOffset(pos);
        getTextModel().remove(pos, size);
        if (man != null) {
          Dimension d = getSOSize();
          man.scrollObjResized(d.width, d.height);
        }
        if (getLines() != pl || old == pos) repaint();
      }
    }
  }

 /**
  * Inserts the specified text at the given position.
  * @param <code>pos</code> the given position.
  * @param <code>s</code> the specified text to be inserted.
  */
  protected /*C#virtual*/ void write(int pos, String s)
  {
    if (isEditableVal)
    {
      int old = cur.getOffset();
      int pl  = getLines();
      getTextModel().write(s, pos);
      if (man != null)  {
        Dimension d = getSOSize();
        man.scrollObjResized(d.width, d.height);
      }
      cur.seek (s.length());
      if (getLines() != pl || cur.getOffset() == old) repaint();
    }
  }

 /**
  * Gets the page size.
  * @return a page size.
  */
  protected /*C#virtual*/ int pageSize()
  {
    Dimension d   = getSize();
    Insets    ins = getInsets();
    d.height -= (ins.top + ins.bottom);
    LwTextRender render = getTextRender();
    int indent = render.getLineIndent();
    int height = render.getLineHeight();
    return (d.height + indent)/(height + indent) +
           (((d.height + indent)%(height + indent)>indent)?1:0);
  }

 /**
  * Clear the current selection.
  */
  protected void stopSelection()
  {
    if (startLine >= 0) {
      startLine = -1;
      repaint();
    }
  }

  private boolean isSelectionStarted() {
    return startLine != -1;
  }

  private void startSelection()
  {
    if (!isSelectionStarted())
    {
      startLine = cur.getCurrentLine();
      startCol  = cur.getCurrentCol();
      startOff  = cur.getOffset();
    }
  }

  private final LwClipboardMan getCM() {
    return ((LwClipboardMan)LwToolkit.getStaticObj("clip"));
  }

  /**
   * Calculates and gets a pixel location for the specified text render and the text
   * cursor position.
   * @param <code>render</code> the specified text render.
   * @param <code>pos</code> the pos controller that defines the text position.
   * @return a pixel location.
   */
   public static Point getTextLocationAt(LwTextRender render, PosController pos)
   {
     if (pos.getOffset() < 0) return null;
     int cl = pos.getCurrentLine();
     return new Point(render.substrWidth(render.getLine(cl), 0, pos.getCurrentCol()),
                      cl * (render.getLineHeight() + render.getLineIndent()));
   }

  /**
   * Calculates and gets a text position for the specified text render and the location.
   * The result is represented with java.awt.Point class where <code>x</code> field defines
   * a row and <code>y</code> field defines a column.
   * @param <code>render</code> the specified text render.
   * @param <code>x</code> the x coordinate of the location.
   * @param <code>y</code> the y coordinate of the location.
   * @return a text position.
   */
   public static Point getTextRowColAt(LwTextRender render, int x, int y)
   {
     int size = render.getTextModel().getSize();
     if (size == 0) return null;

     int lineHeight = render.getLineHeight();
     int lineIndent = render.getLineIndent();
     int lineNumber = (y<0)?0:(y  + lineIndent)/(lineHeight + lineIndent) +
                      ((y  + lineIndent)%(lineHeight + lineIndent)>lineIndent?1:0) - 1;

     if (lineNumber >= size) return new Point (size - 1, render.getLine(size - 1).length());
     else
     if (lineNumber < 0) return new Point();

     if (x < 0) return new Point(lineNumber, 0);

     int x1 = 0, x2 = 0;
     String s = render.getLine(lineNumber);
     for(int c = 0; c < s.length(); c++)
     {
       x1 = x2;
       x2 = render.substrWidth(s, 0, c + 1);
       if (x >= x1 && x < x2) return new Point(lineNumber, c);
     }
     return new Point (lineNumber, s.length());
   }

  /**
   * Finds starting from the specified line and column next word in the given text model and returns
   * the found word location. The result is represented with java.awt.Point object where
   * <code>x</code> field defines a row text position and <code>y</code> field defines a
   * column text position.
   * @param <code>t</code> the specified text model.
   * @param <code>line</code> the specified starting line.
   * @param <code>col</code> the specified starting column.
   * @param <code>d</code> the specified searching direction. If the value is <code>1</code>
   * than the method looks for a next word location, if the value is
   * <code>-1</code> than the method looks for a previous word location.
   * @return a word location.
   */
   public static Point findNextWord(TextModel t, int line, int col, int d)
   {
     if (line < 0 || line >= t.getSize()) return null;

     String ln = t.getLine(line);
     col += d;

     if (col < 0 && line > 0) return new Point (line - 1, t.getLine(line - 1).length());
     else
     if (col > ln.length() && line < t.getSize() - 1) return new Point (line + 1, 0);

     char[]  buf = ln.toCharArray();
     boolean b   = false;
     int     j   = col;
     for (; j >= 0 && j < buf.length; j += d)
     {
       if (b)
       {
         if (d > 0)
         {
           if (Character.isLetter(buf[j])) return new Point (line, j);
         }
         else
           if (!Character.isLetter(buf[j])) return new Point (line, j + 1);
       }
       else
       {
         if (d > 0) b = !Character.isLetter(buf[j]);
         else       b = Character.isLetter(buf[j]);
       }
     }
     return (d > 0?new Point(line, buf.length):new Point (line, 0));
   }

  /**
   * Returns a substring of the specified text model, at the given start and last position.
   * The positions are represented with java.awt.Point object where
   * <code>x</code> field defines a row and <code>y</code> field defines a
   * column.
   * @param <code>t</code> the specified text model.
   * @param <code>start</code> the specified start position.
   * @param <code>end</code> the specified last position.
   * @return a substring.
   */
   public static String getSubString(TextModel t, Point start, Point end)
   {
     StringBuffer res = new StringBuffer();
     for (int i=start.x; i<end.x + 1; i++)
     {
       String ln = t.getLine(i);
       if (i != start.x) res.append ('\n');
       if (i == start.x) ln = ln.substring(start.y);
       if (i == end.x  ) ln = ln.substring(0, end.y - ((start.x == end.x)?start.y:0));
       res.append (ln);
     }
     return res.toString();
   }
}

