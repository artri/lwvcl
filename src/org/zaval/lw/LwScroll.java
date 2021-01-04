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

/**
 * This is scrollbar component. The component is used as the scrollbar component in the scroll
 * bar panel (LwScrollPan) or it can be used for other purposes. The following resources are used
 * with the component:
 * <ul>
 *   <li>"scroll.hbg" - this is a background view of the horizontal scrollbar</li>
 *   <li>"scroll.vbg" - this is a background view of the vertical scrollbar</li>
 *   <li>
 *      "bt.bottom.out", "bt.bottom.over", "bt.bottom.disabled", "bt.bottom.pressed" -
 *      these are the lower button face views of the vertical scrollbar
 *   </li>
 *   <li>
 *     "bt.top.out", "bt.top.over", "bt.top.disabled", "bt.top.pressed" -
 *     these are the upper button face views of the vertical scrollbar</li>
 *   <li>
 *     "bt.left.out", "bt.left.over", "bt.left.disabled", "bt.left.pressed" -
 *      these are the left button face views of the horizontal scrollbar
 *   </li>
 *   <li>
 *    "bt.right.out", "bt.right.over", "bt.right.disabled", "bt.right.pressed" -
 *     these are the right button face views of the horizontal scrollbar
 *   </li>
 * </ul>
 * <p>
 * The scrollbar component defines a value that should be less than certain maximal value and
 * cannot be less zero. The maximal possible value and the current value are defined with
 * a pos controller of the component. Use <code>getMaxOffset</code>, <code>setMaxOffset</code>
 * methods of the component and <code>getOffset</code>, <code>setOffset</code> methods of the
 * pos controller to control the scrollbar properties. The sample below illustrates the scrollbar
 * properties usage:
 * <pre>
 *   ...
 *   LwScroll scroll = new LwScroll(LwToolkit.VERTICAL);
 *   scroll.setMaxOffset(100); // Setting the maximal posiible value of the scrollbar
 *   scroll.getPosController().getOffset();    // Getting the current value of the scrollbar
 *   scroll.getPosController().setOffset(10);  // Setting the current value of the scrollbar
 *   ...
 * </pre>
 * <p>
 * A user can interact with the component using following actions:
 * <ul>
 *   <li>
 *     By pressing the scrollbar buttons. In this case, the component uses "unitIncrement" value to
 *     increase or decrease the value property.
 *   </li>
 *   <li>
 *     By pressing a mouse button inside the scrollbar area. In this case "pageIncrement" value is
 *     used to increase or decrease the scrollbar value property.
 *   </li>
 *   <li>
 *     By dragging the scrollbar bundle element. In this case the scrollbar value will be
 *     calculated basing on the current bundle location.
 *   </li>
 * </ul>
 * To control "unitIncrement" and "pageIncrement" properties use <code>setPageIncremet</code>
 * and <code>setUnitIncremet</code> methods.
 * <p>
 * It is possible to change bundle element view using <code>setBundleView</code> method.
 */
public class LwScroll
extends LwPanel
implements LwActionListener, LwMouseListener, LwMouseMotionListener, PosInfo, PosListener
{
   private int           type, max = 100, pageIncrement = 20, unitIncrement = 5, bundleSize = 15, extra = 100;
   private int           startDragX, startDragY;
   private LwView        bundle;
   private LwStButton    incBt, decBt;
   private PosController pos;
   private Rectangle     boundDragged;

  /**
   * Constructs a scrollbar component with the specified orientation.
   * The orientation value can be LwToolkit.VERTICAL or LwToolkit.HORIZONTAL,
   * otherwise the IllegalArgumentException will be thrown.
   * @param <code>t</code> the specified orientation.
   */
   public LwScroll(int t)
   {
     setPosController(new PosController(this));
     incBt = new LwStButton();
     incBt.fireByPress(true, 20);
     decBt = new LwStButton();
     decBt.fireByPress(true, 20);

     LwAdvViewMan man1 = new LwAdvViewMan();
     LwAdvViewMan man2 = new LwAdvViewMan();
     if (t == LwToolkit.VERTICAL)
     {
       getViewMan(true).setBg(LwToolkit.getView("scroll.vbg"));
       man1.put ("st.out", LwToolkit.getView("bt.bottom.out"));
       man1.put ("st.over", LwToolkit.getView("bt.bottom.over"));
       man1.put ("st.pressed", LwToolkit.getView("bt.bottom.pressed"));
       man1.put ("st.disabled", LwToolkit.getView("bt.bottom.disabled"));

       man2.put ("st.out", LwToolkit.getView("bt.top.out"));
       man2.put ("st.over", LwToolkit.getView("bt.top.over"));
       man2.put ("st.pressed", LwToolkit.getView("bt.top.pressed"));
       man2.put ("st.disabled", LwToolkit.getView("bt.top.disabled"));

       add (LwBorderLayout.NORTH, decBt);
       add (LwBorderLayout.SOUTH, incBt);
     }
     else
     if (t == LwToolkit.HORIZONTAL)
     {
       getViewMan(true).setBg(LwToolkit.getView("scroll.hbg"));
       man1.put ("st.out", LwToolkit.getView("bt.right.out"));
       man1.put ("st.over", LwToolkit.getView("bt.right.over"));
       man1.put ("st.pressed", LwToolkit.getView("bt.right.pressed"));
       man1.put ("st.disabled", LwToolkit.getView("bt.right.disabled"));

       man2.put ("st.out", LwToolkit.getView("bt.left.out"));
       man2.put ("st.over", LwToolkit.getView("bt.left.over"));
       man2.put ("st.pressed", LwToolkit.getView("bt.left.pressed"));
       man2.put ("st.disabled", LwToolkit.getView("bt.left.disabled"));
       add (LwBorderLayout.WEST, decBt);
       add (LwBorderLayout.EAST, incBt);
     }
     else throw new IllegalArgumentException();

     incBt.setViewMan(man1);
     decBt.setViewMan(man2);

     type = t;

     setBundleView(LwToolkit.getView("scroll.bundle"));
     incBt.addActionListener(this);
     decBt.addActionListener(this);
   }

  /**
   * Sets the specified maximal value for the scrollbar component.
   * The scrollbar value cannot be more than the maximal value, so the method
   * sets the current value to maximal value if the current value is more than
   * the new maximal value.
   * @param <code>m</code> the specified maximal value.
   */
   public void setMaximum (int m)
   {
     if (m != max)
     {
       max = m;
       if (pos.getOffset() > max) pos.setOffset(max);

       setBundleSize(bundleSize_());
       repaint();
     }
   }

  /**
   * Sets the bundle size. The size is a bundle with if this is horizontal scrollbar or
   * a bundle height if this is vertical scrollbar.
   * @param <code>s</code> the specified bundle size.
   */
   public void setBundleSize (int s)
   {
     if (bundleSize != s)
     {
       bundleSize = s;
       repaint();
     }
   }

  /**
   * Sets the page increment value. The value is used to decrease or increase the scrollbar value
   * whenever a mouse button has been pressed inside the component area.
   * @param <code>pi</code> the specified page increment.
   */
   public void setPageIncrement (int pi) {
     if (pageIncrement != pi) pageIncrement = pi;
   }

  /**
   * Sets the unit increment value. The value is used to decrease or increase the scrollbar value
   * whenever one of the scrollbar button has been pressed.
   * @param <code>li</code> the specified unit increment.
   */
   public void setUnitIncrement (int li) {
     if (unitIncrement != li) unitIncrement = li;
   }

  /**
   * Sets the bundle view. The view is used to reneder the bundle element of the scrollbar component.
   * @param <code>v</code> the specified bundle view.
   */
   public void setBundleView (LwView v)
   {
     if (bundle != v)
     {
       bundle = v;
       repaint();
     }
   }

  /**
   * Sets the specified position controller. The controller is used to control the
   * scrollbar value.
   * @param <code>p</code> the specified position controller.
   */
   public void setPosController (PosController p)
   {
     if (p != pos)
     {
       if (pos != null) pos.removePosListener(this);
       pos = p;
       if (pos != null)
       {
         pos.addPosListener(this);
         pos.setPosInfo (this);
         pos.setOffset(0);
       }
     }
   }

  /**
   * Gets the position controller.
   * @return a position controller.
   */
   public PosController getPosController () {
     return pos;
   }

   public /*C#override*/ void paint (Graphics g)
   {
     super.paint(g);
     Rectangle r = value2bounds();
     if (r != null) bundle.paint(g, r.x, r.y, r.width, r.height, this);
   }

   public void actionPerformed(LwActionEvent e)
   {
     int value = pos.getOffset();
     if (e.getSource() == incBt) pos.setOffset(value + unitIncrement);
     else                        pos.setOffset(value - unitIncrement);
   }

   public void mouseClicked (LwMouseEvent e) {}
   public void mouseEntered (LwMouseEvent e) {}
   public void mouseExited  (LwMouseEvent e) {}
   public void mouseReleased(LwMouseEvent e) {}
   public void mouseMoved   (LwMouseMotionEvent e) {}

   public void startDragged (LwMouseMotionEvent e)
   {
     Rectangle r = value2bounds();
     if (r != null && r.contains(e.getX(), e.getY()))
     {
       startDragX = e.getX();
       startDragY = e.getY();
       boundDragged = r;
     }
   }

   public void endDragged (LwMouseMotionEvent e) {
     boundDragged = null;
   }

   public void mouseDragged (LwMouseMotionEvent e)
   {
     if (boundDragged != null)
     {
       int dx = e.getX() - startDragX;
       int dy = e.getY() - startDragY;
       pos.setOffset (pixel2value(boundDragged.x + dx, boundDragged.y + dy));
     }
   }

   public void mousePressed (LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()))
     {
       Rectangle r = value2bounds();
       if (r != null && !r.contains(e.getX(), e.getY()))
       {
         int d = pageIncrement;
         if (type == LwToolkit.VERTICAL)
         {
           if (e.getY() < r.y) d = -pageIncrement;
         }
         else
           if (e.getX() < r.x) d = -pageIncrement;

         pos.setOffset(pos.getOffset() + d);
       }
     }
   }

   public void posChanged(PosEvent e) {
     repaint();
   }

   public int getLines   () {
     return max;
   }

   public int getLineSize(int line) {
     return 1;
   }

   public int getMaxOffset() {
     return max;
   }

   public int getExtraSize() {
     return extra;
   }

   public void setExtraSize(int e)
   {
     if (e != extra)
     {
       extra = e;
       setBundleSize(bundleSize_());
     }
   }

   protected /*C#override*/ LwLayout getDefaultLayout() {
     return new LwBorderLayout();
   }

   private int pixel2value(int x, int y)
   {
     int pixelLen = visibleAmount() - bundleSize;
     if (pixelLen > 0)
       return type == LwToolkit.VERTICAL?((y - decBt.height) * max)/pixelLen
                                        :((x - decBt.width ) * max)/pixelLen;
     return pos.getOffset();
   }

   private Rectangle value2bounds()
   {
     int value = pos.getOffset();
     Insets ins = getInsets();

     int pixelLen = visibleAmount();
     if (bundleSize <= pixelLen)
       return type == LwToolkit.VERTICAL
                 ?new Rectangle(ins.left, ins.top + incBt.getHeight() + ((pixelLen - bundleSize) * value) / max, width - ins.left - ins.right, bundleSize)
                 :new Rectangle(ins.left + decBt.getWidth() + ((pixelLen - bundleSize) * value) / max, ins.top, bundleSize, height - ins.top - ins.bottom);
     return null;
   }

   private int bundleSize_()
   {
     int va = visibleAmount();
     Dimension ps = bundle.getPreferredSize();
     int min = type == LwToolkit.VERTICAL?ps.height:ps.width;
     return Math.max (Math.min ((extra*va)/max, va - min), min);
   }

   private int visibleAmount()
   {
     Dimension d1 = incBt.getPreferredSize();
     Dimension d2 = decBt.getPreferredSize();
     Insets    ins = getInsets();
     return (type == LwToolkit.VERTICAL)?height - d1.height - d2.height - ins.top - ins.bottom:
                                         width - d1.width - d2.width - ins.left - ins.right;
   }
}

