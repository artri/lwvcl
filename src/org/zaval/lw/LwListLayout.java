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

/**
 * The class implements layout manager interface. Every following component is laidout under
 * previous component. The preferred width is calculated as a maximum among children
 * preferred size widths of the owner component.
 */
public class LwListLayout
implements LwLayout, org.zaval.misc.PosInfo
{
  private int count = 0, gap = 0;

 /**
  * Constructs the layout manager.
  */
   public LwListLayout() {
    this (0);
  }

 /**
  * Constructs the layout manager with the specified gap. The gap is an indent between
  * laidout components.
  * @param <code>gap</code> the specified gap.
  */
  public LwListLayout(int gap) {
    this.gap = gap;
  }

 /**
  * Invoked when the specified layoutable component is added to the layout container, that
  * uses the layout manager. The specified constraints, layoutable component and child index
  * are passed as arguments into the method.
  * @param <code>id</code> the layoutable component constraints.
  * @param <code>lw</code> the layoutable component.
  * @param <code>index</code> the child index.
  */
  public void componentAdded   (Object id, Layoutable lw, int index) { count++; }

 /**
  * Invoked when the specified layoutable component is removed from the layout
  * container, that uses the layout manager.
  * @param <code>lw</code> the layoutable component to be removed
  * @param <code>index</code> the child index.
  */
  public void componentRemoved (Layoutable lw, int index) { count--; }

 /**
  * Calculates the preferred size dimensions for the layout container.
  * The method calculates "pure" preferred size, it means that an insets
  * of the container is not considered.
  * @param <code>lw</code> the layout container.
  */
  public Dimension calcPreferredSize(LayoutContainer lw)
  {
    int w = 0, h = 0, c = 0;
    for (int i=0; i<lw.count(); i++)
    {
      Layoutable cc = lw.get(i);
      if (cc.isVisible())
      {
        Dimension d = cc.getPreferredSize();
        h += (d.height + (c > 0?gap:0));
        if (w < d.width) w = d.width;
        c ++;
      }
    }
    return new Dimension(w, h);
  }

 /**
  * Lays out the child layoutable components inside the layout container.
  * @param <code>target</code> the layout container that needs to be laid out.
  */
  public void layout(LayoutContainer lw)
  {
    Insets ins = lw.getInsets();
    Point  offset = lw.getLayoutOffset();
    int x = ins.left + offset.x, y = ins.top + offset.y, psw = lw.getWidth() - ins.left - ins.right;

    for (int i=0; i<lw.count(); i++)
    {
      Layoutable cc = lw.get(i);
      if (cc.isVisible())
      {
        Dimension d = cc.getPreferredSize();
        cc.setSize(d.width > psw?d.width:psw, d.height);
        cc.setLocation(x, y);
        y += (d.height + gap);
      }
    }
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define the line size. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @param <code>line</code> the line number.
  * @return a size of the specified line. The method returns "1" as the result for every line number.
  */
  public int getLineSize(int line) {
    return 1;
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define number of lines. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @return a number of lines. The method returns number the owner components.
  */
  public int getLines() {
    return count;
  }

 /**
  * Implements org.zaval.misc.PosInfo interface method to define max offset. The
  * implementation provides ability to navigate over the layout manager owner components using
  * org.zaval.misc.PosController class.
  * @return a max offset.
  */
  public int getMaxOffset() {
    return count - 1;
  }
}

