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
import java.util.*;
import java.awt.event.*;
import org.zaval.util.event.*;
import org.zaval.util.*;
import org.zaval.lw.event.*;

/**
 * This class is a root light weight component that implements LwDesktop interface.
 * The main purpose of the class to provide connection between native Java AWT library
 * implementation and light weight components hierarchy. So, on the one hand the class is light
 * weight components (implements LwContainer interface) and on the other hand this class
 * inherits Java AWT component (java.awt.Panel). Using the class the light weight library:
 * <ul>
 *   <li>
 *     Has Java AWT events source. The AWT events are transformed by the class to
 *     appropriate light weight events.
 *   </li>
 *   <li>
 *     Has graphical surface to paint the light weight components hierarchy.
 *  </li>
 *   <li>
 *     Has desktop layers supporting.
 *  </li>
 *   <li>
 *     Has custom properties supporting. For example, the implementation supports
 *     mouse cursor type property. Use the <code>getProperty</code> and <code>setProperty</code>
 *     methods to control the property.
 *  </li>
 * </ul>
 * Basing on the description above, to create own lightweight application
 * you have to use the class as a root light weight container. The sample
 * below shows the root component usage:
 * <pre>
 *   ...
 *   LwContainer root = new LwRoot();
 *   root.getRootLayer().setLwLayout(new LwBorderLayout());
 *   LwButton button = new LwButton("Ok");
 *   root.getRootLayer().add(LwBorderLayout.CENTER, button);
 *   ...
 *   java.awt.Frame frame = java.awt.Frame();
 *   frame.setSize(400, 400);
 *   frame.setLayout(new BorderLayout());
 *   frame.add("Center", root);
 *   frame.setVisible(true);
 *   ...
 *   OR
 *   ...
 *   Applet applet = new Applet();
 *   applet.setLayout(new BorderLayout());
 *   applet.add("Center", root);
 *   ...
 * </pre>
 * First of all the sample above uses the root layer component as a light weight container for creating
 * the light weight hierarchy. The second part shows the usage of the root as a part of Java AWT
 * frame component and a java applet.
 * <p>
 * More easy way is using of LwFrame class that inherits java.awt.Frame and provides the lightweight
 * top-level container ready to create lightweight hierarchy:
 * <pre>
 *   ...
 *   LwFrame frame = LwFrame();
 *   LwContainer root = frame.getRoot();
 *   root.setLwLayout(new LwBorderLayout());
 *   LwButton button = new LwButton("Ok");
 *   root.add(LwBorderLayout.CENTER, button);
 *   frame.setSize(400, 400);
 *   frame.setVisible(true);
 *   ...
 * </pre>
 */
public class LwRoot
extends Panel
implements LwDesktop, ComponentListener, MouseListener,
           MouseMotionListener, KeyListener, FocusListener,
           LwLayout
{
 /**
  * Defines cursor property id. The id should be used as an argument for
  * <code>getProperty</code> and <code>setProperty</code> method to control
  * native cursor state.
  */
  public static final int CURSOR_PROPERTY = 1;

  private LwPanel proxy;
  private LwLayer root;
  private LwComponent draggOwner, moveOwner, pressOwner, last;
  private int keyCode, pressX, pressY;
  private Hashtable layers = new Hashtable ();

 /**
  * Constructs the class instance.
  */
  public LwRoot()
  {
    setLayout (null);
    addComponentListener  (this);
    addFocusListener      (this);
    addKeyListener        (this);
    addMouseMotionListener(this);
    addMouseListener      (this);

    proxy = new LwPanel();
    proxy.setLwLayout(this);
    proxy.setLwParent(this);
    root = new LwBaseLayer("root");

    proxy.add (root);
    proxy.add (new LwWinLayer());

    //!!!
    // Fix problem with the focus under JDK1.4
    //!!!
    try {
      java.lang.reflect.Method m = getClass().getMethod ("setFocusTraversalKeysEnabled", new Class[] { Boolean.TYPE });
      m.invoke (this, new Object[] { Boolean.FALSE } );
    }
    catch (NoSuchMethodException e) {}
    catch (Exception ee) {
      ee.printStackTrace();
    }
  }

  public void setLwLayout (LwLayout l) {
    throw new RuntimeException();
  }

  public int getX() {
    return proxy.getX();
  }

  public int getY() {
    return proxy.getY();
  }

  public int getWidth() {
    return proxy.getWidth();
  }

  public int getHeight() {
    return proxy.getHeight();
  }

  public LwLayout getLwLayout () {
    return proxy.getLwLayout();
  }

 /**
  * Gets the lightweight parent of this component.
  * The method always returns <code>null</code>, since the root component cannot have
  * a light weight component as a parent, because this component should be used as
  * a top-level light weight component.
  * @return <code>null</code>.
  */
  public LwComponent getLwParent() {
    return null;
  }

  /**
   * Sets the lightweight parent of this component.
   * It is impossible to use the method to define the parent component, because
   * the class is used as a top-level parent component. The method will throw
   * <code>RuntimeException</code> if you try to call it.
   * @param <code>p</code> the parent component of this lightweight component.
   */
  public void setLwParent(LwComponent p) {
    throw new RuntimeException();
  }

  public LwViewMan getViewMan (boolean b) {
    return null;
  }

  public void setViewMan (LwViewMan v) {
    throw new RuntimeException();
  }

  public Rectangle getVisiblePart() {
    return isVisible()?new Rectangle(0, 0, getWidth(), getHeight()):null;
  }

  public LwComponent getLwComponentAt(int x, int y)
  {
    LwLayer targetLayer = fal();
    if (targetLayer != null)
    {
/*???      if (last != null && LwToolkit.isAncestorOf(targetLayer, last))
      {
        Point p = LwToolkit.getAbsLocation(last);
        if (x >= p.x && y >= p.y && x < p.x + last.getWidth() && y < p.y + last.getHeight())
        {
          last = LwEventManager.manager.getEventDestination(last.getLwComponentAt(x - p.x, y - p.y));
          if (last != null) return last;
        }
      }*/

      last = LwToolkit.getEventManager().getEventDestination(targetLayer.getLwComponentAt(x, y));
      return last;
    }
    return null;
  }

 /**
  * Gets the opaque of this component. If the method returns
  * <code>false</code> than the component is transparent, in this case
  * <code>update</code> method has not be called during painting process.
  * The method allways returns <code>true</code> as result, because this is
  * root component.
  * @return  <code>true</code> if the component is opaque; otherwise
  * <code>false</code>.
  */
  public boolean isOpaque () {
    return false;
  }

  public Layoutable get(int index) {
    return proxy.get(index);
  }

  public int count() {
    return proxy.count();
  }

  public void add(LwComponent c) {
    proxy.add(c);
  }

  public void add(Object s, LwComponent c) {
    proxy.add(s, c);
  }

  public void insert(int i, Object s, LwComponent d) {
    proxy.insert(i, s, d);
  }

  public void remove(int i) {
    if (get(i) == root) throw new RuntimeException ();
    proxy.remove(i);
  }

  public void removeAll() {
    throw new RuntimeException ();
  }

  public int indexOf(LwComponent c) {
    return proxy.indexOf(c);
  }

  public void validate() {
    proxy.validate();
    super.validate();
  }

  public void setEnabled (boolean b) {
    throw new RuntimeException();
  }

  public Dimension getPreferredSize() {
    return proxy.getPreferredSize();
  }

  public Insets getInsets() {
    return proxy.getInsets();
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint (Graphics g) {
    if (getParent() != null && getParent().isVisible()) LwToolkit.getPaintManager().rootPaint(g, proxy);
  }

  public void paintOnTop(Graphics g) {}

  public LwLayer getRootLayer () {
    return root;
  }

  public LwLayer getLayer (Object id) {
    return (LwLayer)layers.get(id);
  }

  public Object[] getLayersIDs ()
  {
    Object[] ids = new Object[layers.size()];
    Enumeration en  = layers.keys();
    for (int i=0; i < ids.length; i++) ids[i] = en.nextElement();
    return ids;
  }

 /**
  * Invoked when component has been hidden.
  */
  public void componentHidden(ComponentEvent e) {
    proxy.setVisible(false);
  }

 /**
  * Invoked when component has been moved.
  */
  public void componentMoved(ComponentEvent e) {}

  public void reshape(int x, int y, int w, int h) {
    super.reshape(x, y, w, h);
    proxy.setSize(w, h);
  }

 /**
  * Invoked when component has been resized.
  */
  public void componentResized(ComponentEvent e) {}

 /**
  * Invoked when component has been shown.
  */
  public void componentShown(ComponentEvent e) {
    proxy.setVisible(true);
  }

 /**
  * Returns whether this component can be traversed using Tab or Shift-Tab keyboard
  * focus traversal. If this method returns "false", this component may still
  * request the keyboard focus using requestFocus(), but it will not automatically
  * be assigned focus during tab traversal.
  */
  public boolean isFocusTraversable() {
    return true;
  }

 /**
  * Sets the opaque of this component. Use <code> false </code>
  * argument value to make a transparent component from this component.
  * The method usage has not any effect, because the root component has to
  * be always opaque.
  * @param  <code>b</code> the opaque flag.
  */
  public void setOpaque (boolean b) {
    throw new RuntimeException();
  }

  public Point getOrigin () {
    return null;
  }

  public Point getLayoutOffset() {
    return new Point();
  }

 /**
  * Invoked when a component gains the keyboard focus.
  */
  public void focusGained(FocusEvent e)
  {
    LwLayer activeLayer = fal();
    if (activeLayer != null) activeLayer.setupFocus();
  }

 /**
  * Invoked when a component loses the keyboard focus.
  */
  public void focusLost(FocusEvent e) {
    LwLayer activeLayer = fal();
    if (activeLayer != null) activeLayer.releaseFocus();
  }

 /**
  * Invoked when the mouse has been clicked on a component.
  */
  public void mouseClicked(MouseEvent e) {}

 /**
  * Invoked when the mouse enters a component.
  */
  public void mouseEntered(MouseEvent e)
  {
    if (draggOwner == null)
    {
      LwComponent d = getLwComponentAt(e.getX(), e.getY());
      if (isEventable(d)){
        moveOwner = d;
        fme(d, LwMouseEvent.MOUSE_ENTERED, e);
      }
    }
  }

 /**
  * Invoked when the mouse exits a component.
  */
  public void mouseExited(MouseEvent e)
  {
    if (moveOwner != null && draggOwner == null)
    {
      LwComponent p = moveOwner;
      moveOwner = null;
      fme(p, LwMouseEvent.MOUSE_EXITED, e);
    }
  }

 /**
  * Invoked when a mouse button has been pressed on a component.
  */
  public void mousePressed(MouseEvent e)
  {
    pressX = e.getX();
    pressY = e.getY();

    for (int i=proxy.count()-1; i>=0; i--)
    {
      LwLayer l = (LwLayer)proxy.get(i);
      l.mousePressed(pressX, pressY, e.getModifiers());
      if (l.isActive()) break;
    }


    LwComponent d = getLwComponentAt(pressX, pressY);
    if (isEventable(d))
    {
      pressOwner = d;
      fme(d, LwMouseEvent.MOUSE_PRESSED, e);
    }
  }

 /**
  * Invoked when a mouse button has been released on a component.
  */
  public void mouseReleased(MouseEvent e)
  {
    int x = e.getX(), y = e.getY();
    int m = e.getModifiers();
    boolean drag = (draggOwner != null);

    if (drag)
    {
      fmme(draggOwner, LwMouseMotionEvent.MOUSE_ENDDRAGGED, x, y, m);
      draggOwner = null;
    }

    LwComponent po = pressOwner;
    if (pressOwner != null)
    {
      fme(pressOwner, LwMouseEvent.MOUSE_RELEASED, e);
      if (!drag) fme(pressOwner, LwMouseEvent.MOUSE_CLICKED, e);
      pressOwner = null;
    }

    if (drag || (po != null && po != moveOwner))
    {
      LwComponent nd = getLwComponentAt(x, y);
      if (nd != moveOwner)
      {
        if (moveOwner != null) fme(moveOwner, LwMouseEvent.MOUSE_EXITED, e);
        if (isEventable(nd))
        {
          moveOwner = nd;
          fme(nd, LwMouseEvent.MOUSE_ENTERED, e);
        }
      }
    }
  }

 /**
  * Invoked when a key has been pressed.
  */
  public void keyPressed(KeyEvent e)
  {
    for (int i=proxy.count()-1; i>=0; i--)
    {
      LwLayer l = (LwLayer)proxy.get(i);
      l.keyPressed(e.getKeyCode(), e.getModifiers());
      if (l.isActive()) break;
    }

    LwComponent focusOwner = LwToolkit.getFocusManager().getFocusOwner();
    if (focusOwner != null)
    {
      keyCode = e.getKeyCode();
      fke (focusOwner, LwKeyEvent.KEY_PRESSED, e);
    }
  }

 /**
  * Invoked when a key has been released.
  */
  public void keyReleased(KeyEvent e)
  {
    LwComponent focusOwner = LwToolkit.getFocusManager().getFocusOwner();
    if (focusOwner != null)
      fke(focusOwner, LwKeyEvent.KEY_RELEASED, e);
  }

 /**
  * Invoked when a key has been typed.
  * This event occurs when a key press is followed by a key release.
  */
  public void keyTyped(KeyEvent e) {
    LwComponent focusOwner = LwToolkit.getFocusManager().getFocusOwner();
    if (focusOwner != null) fke(focusOwner, LwKeyEvent.KEY_TYPED, e);
  }

 /**
  * Invoked when the mouse button has been moved on a component
  * (with no buttons no down).
  */
  public void mouseMoved(MouseEvent e)
  {
    int x = e.getX(), y = e.getY();
    LwComponent d = getLwComponentAt(x, y);
    int m = e.getModifiers();
    if (moveOwner != null)
    {
       if (d != moveOwner)
       {
         LwComponent old = moveOwner;
         moveOwner = null;
         fme(old, LwMouseEvent.MOUSE_EXITED, e);

         if (isEventable(d))
         {
           moveOwner = d;
           fme(moveOwner, LwMouseEvent.MOUSE_ENTERED, e);
         }
       }
       else
       {
         if (isEventable(d)) fmme(d, LwMouseMotionEvent.MOUSE_MOVED, x, y, m);
       }
    }
    else
    if (isEventable(d))
    {
      moveOwner = d;
      fme(d, LwMouseEvent.MOUSE_ENTERED, e);
    }
  }

 /**
  * Invoked when a mouse button is pressed on a component and then
  * dragged. Mouse drag events will continue to be delivered to
  * the component where the first originated until the mouse button is
  * released (regardless of whether the mouse position is within the
  * bounds of the component).
  */
  public void mouseDragged(MouseEvent e)
  {
    int m = e.getModifiers() | InputEvent.BUTTON1_MASK;
    int x = e.getX(), y = e.getY();

    if (draggOwner == null)
    {
      LwComponent d = (moveOwner == null)?getLwComponentAt(pressX, pressY):moveOwner;
      if (isEventable(d))
      {
        draggOwner = d;
        fmme(draggOwner, LwMouseMotionEvent.MOUSE_STARTDRAGGED, pressX, pressY, m);
        if (pressX != x || pressY != y)
          fmme(draggOwner, LwMouseMotionEvent.MOUSE_DRAGGED, x, y, m);
      }
    }
    else fmme(draggOwner, LwMouseMotionEvent.MOUSE_DRAGGED, x, y, m);
  }

  public void componentAdded(Object id, Layoutable lw, int index)
  {
    LwLayer l = (LwLayer)lw;
    if (layers.get(l.getID()) != null) throw new RuntimeException();

    layers.put (l.getID(), lw);
  }

  public void componentRemoved(Layoutable lw, int index) {
    layers.remove(((LwLayer)lw).getID());
  }

  public Dimension calcPreferredSize(LayoutContainer target) {
    return new Dimension ();
  }

  public void layout(LayoutContainer target)
  {
    int w = getWidth(), h = getHeight();
    for (int i=0;i<target.count();i++)
    {
      Layoutable l = target.get(i);
      l.setSize(w, h);
    }
  }

  public Object getProperty (int id) {
    if (id == CURSOR_PROPERTY) return getCursor();
    else throw new IllegalArgumentException();
  }

  public void setProperty (int id, Object value) {
    if (id == CURSOR_PROPERTY) setCursor((Cursor)value);
    else throw new IllegalArgumentException();
  }

  public boolean canHaveFocus () {
    return false;
  }

  /*[fire key event]*/
  private final void  fke(LwComponent target, int id, KeyEvent e)  {
    KE_STUB.reset(target, id, keyCode, e.getKeyChar(), e.getModifiers());
    LwToolkit.getEventManager().perform (KE_STUB);
  }

  /*[fire mouse event]*/
  private final void fme(LwComponent target, int id, MouseEvent e) {
    ME_STUB.reset(target, id, e.getX(), e.getY(), e.getModifiers(), e.getClickCount());
    LwToolkit.getEventManager().perform(ME_STUB);
  }

  /*[fire mouse motion event]*/
  private final void fmme (LwComponent target, int id, int ax, int ay, int m) {
    MME_STUB.reset(target, id, ax, ay, m, 0);
    LwToolkit.getEventManager().perform (MME_STUB);
  }

  /*[find active layer]*/
  private final LwLayer fal()
  {
    for (int i=proxy.count()-1; i>=0; i--)
    {
      LwLayer targetLayer = (LwLayer)proxy.get(i);
      if (targetLayer.isActive()) return targetLayer;
    }
    return null;
  }

  private static final boolean isEventable(LwComponent c) {
    return c != null && c.isEnabled();
  }

  private LwKeyEvent         KE_STUB  = new LwKeyEvent        (this, LwKeyEvent.KEY_PRESSED, 0, 'x', 0);
  private LwMouseEvent       ME_STUB  = new LwMouseEvent      (this, LwMouseEvent.MOUSE_PRESSED, 0, 0, 0, 1);
  private LwMouseMotionEvent MME_STUB = new LwMouseMotionEvent(this, LwMouseMotionEvent.MOUSE_MOVED, 0, 0, 0);
}

