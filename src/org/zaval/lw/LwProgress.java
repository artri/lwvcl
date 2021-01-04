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
import org.zaval.lw.*;
import org.zaval.util.*;
import org.zaval.misc.*;

/**
 * This is progress bar light weight component that can be used to display progress status for
 * some processes. The progress status is changed starting from zero to a maxiaml value. The
 * maximal value can be set by <code>setMaxValue</code> method and current status can be
 * controlled by <code>getValue</code> and <code>setValue</code> methods. Use
 * <code>addActionListener</code> and <code>removeActionListener</code> methods to listen
 * whenever the current progress status has been changed. The action event returns previous
 * value (that is represented with java.lang.Integer class) by <code>getData</code> method.
 * <p>
 * The progress bar component can be oriented by two manners:
 * <ul>
 *   <li>LwToolkit.HORIZONTAL (the default orientation) </li>
 *   <li>LwToolkit.VERTICAL</li>
 * </ul>
 * Use <code>setOrientation</code> method to define the progress bar orientation.
 * <p>
 * Use <code>setTitleView</code> method to define title view for the component. The view will
 * be rendered at the center of the progress bar component.
 */
public class LwProgress
extends LwCanvas
{
  private int value = 0, bundleSize = 6, gap = 2, orient = LwToolkit.HORIZONTAL, maxValue = 20;
  private Color color  = LwToolkit.darkBlue;
  private LwActionSupport support;
  private LwView title;

 /**
  * Constructs the progress bar.
  */
  public LwProgress() {
    getViewMan(true).setBorder(LwToolkit.getView("br.sunken2"));
  }

 /**
  * Sets the specified orientation for the component. Use LwToolkit.HORIZONTAL or
  * LwToolkit.VERTICAL constants as the orientation value.
  * @param <code>o</code> the specified orientation.
  */
  public void setOrientation(int o)
  {
    if (o != LwToolkit.HORIZONTAL && o != LwToolkit.VERTICAL) throw new IllegalArgumentException();
    if (o != orient)
    {
      orient = o;
      vrp();
    }
  }

 /**
  * Gets the current progress value.
  * @return a current progress value.
  */
  public int getValue () {
    return value;
  }

 /**
  * Sets the bundle size.
  * @param <code>size</code> the bundle size.
  */
  public void setBundleSize (int size)
  {
    if (size != bundleSize)
    {
      bundleSize = size;
      vrp();
    }
  }

 /**
  * Sets the bundle color.
  * @param <code>c</code> the bundle color.
  */
  public void setBundleColor (Color c)
  {
    if (c != color)
    {
      color = c;
      repaint();
    }
  }

 /**
  * Sets the maximal value.
  * @param <code>m</code> the maximal value.
  */
  public void setMaxValue (int m)
  {
    if (m != maxValue)
    {
      maxValue = m;
      setValue(getValue());
      vrp();
    }
  }

 /**
  * Sets the current value.
  * @param <code>p</code> the current value.
  */
  public void setValue (int p)
  {
    p = p % (maxValue + 1);
    if (value != p)
    {
      int old = value;
      value = p;
      if (support != null) support.perform(new LwActionEvent(this, new Integer(old)));
      repaint();
    }
  }

 /**
  * Sets the gap. The gap defines indent between progress bar bundles.
  * @param <code>g</code> the gap.
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
  * Gets the title view.
  * @return a title view.
  */
  public LwView getTitleView () {
    return title;
  }

 /**
  * Sets the specified title view.
  * @param <code>v</code> the specified title view.
  */
  public void setTitleView (LwView v)
  {
    if (title != v)
    {
      if (title != null) title.ownerChanged(null);
      title = v;
      if (title != null) title.ownerChanged(this);
      vrp();
    }
  }

  public /*C#override*/ void paint(Graphics g)
  {
    int rs = getRs();
    if (rs > bundleSize)
    {
      rs = (rs * value)/maxValue;
      Insets ins = getInsets();
      int x = ins.left, y = height - ins.bottom;
      g.setColor(color);
      while (x < (rs + ins.left) && height - rs - ins.bottom < y)
      {
        if (orient == LwToolkit.HORIZONTAL)
        {
          g.fillRect(x, ins.top, bundleSize, height - ins.top - ins.bottom);
          x += (bundleSize + gap);
        }
        else
        {
          g.fillRect(ins.left, y - bundleSize, width - ins.left - ins.right, bundleSize);
          y -= (bundleSize + gap);
        }
      }

      if (title != null)
      {
        Point p = Alignment.getLocation(title.getPreferredSize(), Alignment.CENTER, Alignment.CENTER,
                                        width, height);
        title.paint (g, p.x, p.y, this);
      }
    }
  }

 /**
  * Adds the specified action listener to receive action events from this component.
  * @param <code>l</code> the specified action listener.
  */
  public void addActionListener(LwActionListener l) {
    if (support == null) support = new LwActionSupport();
    support.addListener(l);
  }

 /**
  * Removes the specified action listener.
  * @param <code>l</code> the specified action listener.
  */
  public void removeActionListener(LwActionListener l) {
    if (support != null) support.removeListener(l);
  }

  protected /*C#override*/ Dimension calcPreferredSize()
  {
    int v1 = (maxValue * bundleSize) + (maxValue - 1)*gap;
    Dimension d = (orient == LwToolkit.HORIZONTAL)? new Dimension(v1, bundleSize):
                                                    new Dimension(bundleSize, v1);
    return (title != null)?MathBox.max (d, title.getPreferredSize()):d;
  }

  private int getRs() {
    Insets ins = getInsets();
    return (orient == LwToolkit.HORIZONTAL)?width - ins.left - ins.right:height - ins.top - ins.bottom;
  }
}
