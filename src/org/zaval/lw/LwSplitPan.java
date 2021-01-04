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
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This split panel component is used to divide the container area into two resizeable
 * areas. The container area can be divided by horizontal splitter or vertical splitter.
 * The component implements and use special layout manager, so don't try to redefine the
 * layout manager with any other.
 */
public class LwSplitPan
extends LwPanel
implements LwMouseMotionListener, LwComposite, Cursorable, LwLayout
{
  private boolean isDragged = false;
  private int     prevX, prevY, orientation, gap, barLocation, cType;

 /**
  * Constructs a split panel with the specified child components for the two splitter
  * areas. The split panel will use LwToolkit.VERTICAL splitter as default.
  * @param <code>f</code> the first child component.
  * @param <code>s</code> the second child component.
  */
  public LwSplitPan(LwComponent f, LwComponent s) {
    this(f, s, LwToolkit.VERTICAL);
  }

 /**
  * Constructs a split panel with the specified child components for the two splitter
  * areas and the given splitter type.
  * @param <code>f</code> the first child component.
  * @param <code>s</code> the second child component.
  * @param <code>d</code> the splitter type. The splitter type can have LwToolkit.VERTICAL or
  * LwToolkit.HORIZONTAL value.
  */
  public LwSplitPan(LwComponent f, LwComponent s, int o)
  {
    LwCanvas bar = new LwCanvas();
    bar.getViewMan(true).setBorder(LwToolkit.getView("sp.bar"));
    stretchGripper(true);
    setOrientation(o);
    setGap(1);
    add(f);
    add(s);
    add(bar);
    setInsets(2, 2, 2, 2);
  }

 /**
  * Sets the specified gap. The gap defines horizontal and vertical indents between child
  * components and the container border.
  * @param <code>g</code> the specified gap.
  */
  public void setGap (int g)
  {
    if (gap != g)
    {
      gap = g;
      vrp();
    }
  }

 /**
  * Gets the splitter type.
  * @return a splitter type.
  */
  public int getOrientation() {
    return orientation;
  }

 /**
  * Sets the specified orientation.
  * It is possible to use LwToolkit.VERTICAL or LwToolkit.HORIZONTAL as the property value.
  * @param <code>o</code> the specified orientation.
  */
  public void setOrientation(int o)
  {
    if (o != LwToolkit.VERTICAL && o != LwToolkit.HORIZONTAL) throw new IllegalArgumentException();
    if (orientation != o)
    {
      orientation = o;
      vrp();
    }
  }

 /**
  * Tests if the gripper element is stretched over all available splitter room.
  * @return  <code>true</code> if the gripper element is stretched over all available room.
  */
  public boolean stretchGripper() {
    return MathBox.checkBit(bits, STRETCHGRIPPER_BIT);
  }

 /**
  * Sets the gripper element to be stretched or not over all available room.
  * @param <code>b</code> use <code>true</code> to stretch the gripper element over all available room.
  */
  public void stretchGripper(boolean b)
  {
    if (b != MathBox.checkBit(bits, STRETCHGRIPPER_BIT))
    {
      bits = MathBox.getBits(bits, STRETCHGRIPPER_BIT, b);
      vrp();
    }
  }

  public void startDragged(LwMouseMotionEvent e)
  {
    if (cType != -1)
    {
      isDragged = true;
      Layoutable bar = getGripper();
      prevX = e.getX();
      prevY = e.getY();
      if (getOrientation()==LwToolkit.VERTICAL) drawVLine(prevX);
      else                                      drawHLine(prevY);
    }
  }

  public void endDragged(LwMouseMotionEvent e)
  {
    if (isDragged)
    {
      int x = e.getX(), y = e.getY();
      if (orientation == LwToolkit.VERTICAL) drawVLine(x);
      else                                   drawHLine(y);
      isDragged = false;
      int newBar = (orientation == LwToolkit.VERTICAL)?x:y;
      setGripperLoc(newBar);
    }
  }

  public void mouseDragged(LwMouseMotionEvent e)
  {
    if (isDragged)
    {
      int x = e.getX(), y = e.getY();
      if (orientation == LwToolkit.VERTICAL)
      {
        if (prevX != x)
        {
          drawVLine(prevX);
          drawVLine(x);
        }
      }
      else
      {
        if (prevY != y)
        {
          drawHLine(prevY);
          drawHLine(y);
        }
      }
      prevX = x;
      prevY = y;
    }
  }

  public void mouseMoved  (LwMouseMotionEvent e) {}

  public int getCursorType(LwComponent target, int x, int y)
  {
    if (getLwComponentAt(x, y) == getGripper())
      cType = getOrientation() == LwToolkit.VERTICAL?Cursor.W_RESIZE_CURSOR:Cursor.N_RESIZE_CURSOR;
    else
      cType = -1;
    return cType;
  }

  public boolean catchInput (LwComponent child) {
    return child == getGripper();
  }

 /**
  * Gets the component that has been laid as the gripper.
  * @return a gripper component.
  */
  public LwComponent getGripper() {
    return (LwComponent)get(2);
  }

 /**
  * Sets the specified gripper component location.
  * @param <code>l</code> the specified gripper component location.
  */
  public void setGripperLoc(int l)
  {
    if (l != barLocation)
    {
      barLocation = l;
      vrp();
    }
  }

 /**
  * Gets the gripper component location.
  * @return a gripper component location.
  */
  public int getGripperLoc() {
    return barLocation;
  }

 /**
  * Calculates the preferred size dimension for the layout container.
  * The method calculates "pure" preferred size, it means that an insets
  * of the container is not considered.
  * @param <code>c</code> the layout container.
  */
  public Dimension calcPreferredSize(LayoutContainer c)
  {
    Dimension fSize = getPreferredSize(c.get(0));
    Dimension sSize = getPreferredSize(c.get(1));
    Dimension bSize = getPreferredSize(c.get(2));
    if (getOrientation() == LwToolkit.HORIZONTAL)
      return new Dimension(Math.max(Math.max(fSize.width, sSize.width), bSize.width), fSize.height + sSize.height + bSize.height + 2 * gap);
    else
      return new Dimension(fSize.width + sSize.width + bSize.width + 2 * gap, Math.max(Math.max(fSize.height, sSize.height), bSize.height));
  }

 /**
  * Lays out the child layoutable components inside the layout container.
  * @param <code>target</code> the layout container that needs to be laid out.
  */
  public void layout(LayoutContainer target)
  {
    Layoutable bar = getGripper(), second = get(1), first = get(0);
    Insets     i     =  getInsets();
    Dimension  bSize = getPreferredSize(bar);

    if (getOrientation() == LwToolkit.HORIZONTAL)
    {
      int w = width - i.left - i.right;

      if (barLocation < i.top) barLocation = i.top;
      else
      if (barLocation > height - i.bottom - bSize.height) barLocation = height - i.bottom - bSize.height;

      if (stretchGripper())
      {
        bar.setLocation(i.left, barLocation);
        bar.setSize    (w, bSize.height);
      }
      else
      {
        bar.setSize     (bSize.width, bSize.height);
        bar.setLocation ((width - bSize.width - i.left - i.right)/2, barLocation);
      }

      first.setLocation(i.left, i.top);
      first.setSize    (w, barLocation - gap - i.top);

      second.setLocation(i.left, barLocation + bSize.height + gap);
      second.setSize    (w, height - second.getY() - i.bottom);
    }
    else
    {
      int h = height - i.top - i.bottom;
      if (barLocation < i.left) barLocation = i.left;
      else
      if (barLocation > width - i.right - bSize.width) barLocation = width - i.right;

      if (stretchGripper())
      {
        bar.setLocation(barLocation, i.top);
        bar.setSize(bSize.width, h);
      }
      else
      {
        bar.setSize     (bSize.width, bSize.height);
        bar.setLocation (barLocation, (height - bSize.height - i.top - i.bottom)/2);
      }

      first.setLocation(i.left, i.top);
      first.setSize    (barLocation - i.left - gap, h);

      second.setLocation(barLocation + bSize.width + gap, i.top);
      second.setSize    (width - second.getX() - i.right, h);
    }
  }

  public void componentAdded  (Object obj, Layoutable c, int index)  {}
  public void componentRemoved(Layoutable c, int index) {}

  protected /*C#override*/ LwLayout getDefaultLayout () {
    return this;
  }

  private void drawVLine (int x) {
    Insets i = getInsets();
    LwToolkit.fillXORRect(this, x, i.top, getGripper().getWidth(), height - i.top - i.bottom);
  }

  private void drawHLine (int y) {
    Insets i = getInsets();
    LwToolkit.fillXORRect(this, i.left, y, width - i.left - i.right, getGripper().getHeight());
  }

  private static Dimension getPreferredSize(Layoutable c) {
    return (c.isVisible())?c.getPreferredSize():new Dimension();
  }

  private static short STRETCHGRIPPER_BIT = 64;
}
