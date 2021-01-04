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
import org.zaval.misc.*;
import org.zaval.util.*;
import org.zaval.lw.event.*;

/**
 * This class is window lightweight component that can be used as a desktop window. The view of the
 * component is like standard window (java.awt.Frame for example)that contains following elements
 * and features:
 * <ul>
 *   <li>
 *     Window title area. Use <code>getTitle</code> and <code>setTitle</code> methods to control it.
 *   </li>
 *   <li>
 *     Window icon. Use <code>setIcon</code> method to specified the icon view.
 *   </li>
 *   <li>
 *     Window caption. The caption indicates if the window is active or not by seting
 *     appropriate caption color. Use <code>setCaptionColor</code> method to customize it.
 *   </li>
 *   <li>
 *     Window can be sizeable. The component provides ability to size window using the
 *     mouse, by dragging right-bottom corner of the component. Use <code>isSizeable</code>
 *     and <code>setSizeable</code> methods to disable or enable sizing of the window
 *     component.
 *   </li>
 *   <li>
 *     The window provides root container by <code>getRoot</code> method and the container
 *     should be used to add any child components to the window.
 *   </li>
 * </ul>
 */
public class LwWindow
extends LwPanel
implements LwMouseMotionListener, LwWinListener,
           LwComposite, LwActionListener, Cursorable
{
  private LwPanel root, icons;
  private LwLabel title = new LwLabel();
  private LwComponent corner, icon;
  private Color activeColor = LwToolkit.darkBlue, nonactiveColor = Color.lightGray;
  private LwStButton exit;
  private int cursor = -1, px, py, dx = 0, dy = 0, action = -1;

 /**
  * Constructs the window component with empty title.
  */
  public LwWindow() {
    this ("");
  }

 /**
  * Constructs the window component with the specified title.
  * @param <code>s</code> the specified title.
  */
  public LwWindow(String s)
  {
    LwPanel titlePanel = new LwPanel();
    titlePanel.setBackground(nonactiveColor);
    titlePanel.setLwLayout(new LwBorderLayout (2, 2));
    titlePanel.setInsets(1,1,1,1);

    add (LwBorderLayout.NORTH, titlePanel);
    root = new LwPanel();
    add (LwBorderLayout.CENTER, root);
    getViewMan(true).setBorder("br.raised");

    exit = new LwStButton();
    LwAdvViewMan man = new LwAdvViewMan();
    man.put ("st.over", LwToolkit.getView("win.b.over"));
    man.put ("st.out",  LwToolkit.getView("win.b.out"));
    man.put ("st.pressed", LwToolkit.getView("win.b.pressed"));
    exit.setViewMan(man);
    exit.addActionListener(this);
    title.setOpaque(false);
    title.setForeground (Color.white);
    title.setText(s);
    LwPanel buttons = new LwPanel();
    buttons.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER));
    buttons.add (exit);
    buttons.setOpaque (false);

    icons = new LwPanel();
    icons.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER));
    icon = new LwImage((LwImgRender)LwToolkit.getView("win.icon"));
    icons.add (icon);

    titlePanel.add (LwBorderLayout.EAST, buttons);
    titlePanel.add (LwBorderLayout.WEST, icons);
    titlePanel.add (LwBorderLayout.CENTER, title);

    LwPanel status = new LwPanel();
    status.setLwLayout(new LwFlowLayout(Alignment.RIGHT, Alignment.CENTER));
    corner = new LwImage((LwImgRender)LwToolkit.getView("win.corner"));
    status.add (corner);
    add (LwBorderLayout.SOUTH, status);

    setSizeable (true);
    hideOnClose (true);
  }

 /**
  * Sets the specified title.
  * @param <code>s</code> the specified title.
  */
  public void setTitle (String s) {
    title.setText(s);
  }

 /**
  * Gets the window title.
  * @return a window title.
  */
  public String getTitle () {
    return title.getText();
  }

 /**
  * Sets the specified component as the window icon.
  * @param <code>i</code> the specified component.
  */
  public void setIcon (LwComponent i)
  {
    if (i != icon)
    {
      icons.remove(icon);
      icon = i;
      if (icon != null) icons.add (icon);
      repaint();
    }
  }

 /**
  * Sets the specified color that will be used as background color of the window caption
  * for the specified window state.
  * @param <code>state</code> the specified window state. <code>true</code> value correspond to
  * active window state and <code>false</code> value correspond to non-active window state.
  * @param <code>c</code> the specified color.
  */
  public void setCaptionColor(boolean state, Color c)
  {
    Color old = state?activeColor:nonactiveColor;
    if (!old.equals(c))
    {
      if (state) activeColor = c;
      else       nonactiveColor = c;
      repaint();
    }
  }

 /**
  * Tests if the close window button will hide the window.
  * @return <code>true</code> if the close button will hide the window component;
  * otherwise <code>false</code>.
  */
  public boolean isHideOnClose () {
    return MathBox.checkBit(bits, ONCLOSE_BIT);
  }

 /**
  * Sets the specified reaction to the close button pressing.
  * @param <code>b</code> the specified reaction. If the the argumen is <code>true</code>
  * than the window will close by pressing the close button.
  */
  public void hideOnClose (boolean b){
    if (isHideOnClose() != b) bits = MathBox.getBits(bits, ONCLOSE_BIT, b);
  }

 /**
  * Tests if the window component is sizeable.
  * @return <code>true</code> if the window component can be sized;
  * otherwise <code>false</code>.
  */
  public boolean isSizeable() {
    return MathBox.checkBit(bits, SIZABLE_BIT);
  }

 /**
  * Enables sizing of the window.
  * @param <code>b</code> if the argument is <code>true</code> than the window is sizeable;
  * otherwise <code>false</code>.
  */
  public void setSizeable(boolean b) {
    if (isSizeable() != b) bits = MathBox.getBits(bits, SIZABLE_BIT, b);
  }

 /**
  * Gets the root container.
  * @return a root container.
  */
  public LwContainer getRoot () {
    return root;
  }

  public void startDragged(LwMouseMotionEvent e)
  {
    if (cursor == -1 || isSizeable())
    {
      action = cursor==-1?MOVE_ACTION:SIZE_ACTION;
      px = e.getX();
      py = e.getY();
      dx = 0;
      dy = 0;
      LwToolkit.drawXORRect(this, 0, 0, width, height);
    }
  }

  public void mouseDragged(LwMouseMotionEvent e)
  {
    if (action > 0)
    {
      if (action == MOVE_ACTION) LwToolkit.drawXORRect(this, dx, dy, width, height);
      else                       LwToolkit.drawXORRect(this, 0, 0, width + dx, height + dy);
      dx = (e.getX() - px);
      dy = (e.getY() - py);
      if (action == MOVE_ACTION) LwToolkit.drawXORRect(this, dx, dy, width, height);
      else                       LwToolkit.drawXORRect(this, 0, 0, width + dx, height + dy);
    }
  }

  public void endDragged(LwMouseMotionEvent e)
  {
    if (action > 0)
    {
      if (action == MOVE_ACTION)
      {
        LwToolkit.drawXORRect(this, dx, dy, width, height);
        int xx = x + dx;
        int yy = y + dy;
        // don't touch the invalidate
        invalidate();
        setLocation (xx, yy);
      }
      else
      {
        LwToolkit.drawXORRect(this, 0, 0, width + dx, height + dy);
        int nw = width + dx, nh = height + dy;
        if (nw > MIN_SIZE && nh > MIN_SIZE) setSize (nw, nh);
      }
      action = -1;
    }
  }

  public void mouseMoved  (LwMouseMotionEvent e) {}

  public void winOpened (LwWinEvent e)  {}

  public void winClosed (LwWinEvent e) {
    getTitlePanel().setBackground(nonactiveColor);
  }

  public void winActivated (LwWinEvent e) {
    getTitlePanel().setBackground(activeColor);
  }

  public void winDeactivated (LwWinEvent e) {
    getTitlePanel().setBackground(nonactiveColor);
  }

  public /*C#virtual*/ void actionPerformed (LwActionEvent e) {
    LwLayer l = LwToolkit.getDesktop(this).getLayer(LwWinLayer.ID);
    if (e.getSource() == exit && isHideOnClose()) l.remove(l.indexOf(this));
  }

  public int getCursorType (LwComponent target, int x, int y) {
    cursor = (getLwComponentAt(x, y) == corner && isSizeable())?Cursor.SE_RESIZE_CURSOR:-1;
    return cursor;
  }

  public boolean catchInput (LwComponent c) {
    LwComponent tp = getTitlePanel();
    return c != exit && (c == tp || LwToolkit.isAncestorOf(tp, c) || c == corner);
  }

  public LwComponent getTitlePanel() {
    return (LwComponent)get(0);
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return new LwBorderLayout();
  }

  private static final short SIZABLE_BIT = 128;
  private static final short ONCLOSE_BIT = 256;

  private static final short MIN_SIZE = 40;
  private static final int   MOVE_ACTION = 1;
  private static final int   SIZE_ACTION = 2;
}


