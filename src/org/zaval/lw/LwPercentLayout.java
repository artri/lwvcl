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
 */
public class LwPercentLayout
implements LwLayout
{
  private int   gap, dir, count = 0;
  private int[] constraints = new int[0];

  public LwPercentLayout() {
    this (LwToolkit.HORIZONTAL, 2);
  }

  public LwPercentLayout(int dir, int gap)
  {
    if (dir != LwToolkit.HORIZONTAL && dir != LwToolkit.VERTICAL)
      throw new IllegalArgumentException();
    this.dir = dir;
    this.gap = gap;
  }

  public void componentAdded(Object id, Layoutable lw, int index)
  {
    if (id == null) throw new IllegalArgumentException();
    count++;
    int[] array = new int[count];
    System.arraycopy(constraints, 0, array, 0, index);
    if (index < constraints.length) System.arraycopy(constraints, index, array, index + 1, array.length - index - 1);
    constraints = array;
    constraints[index] = ((Integer)id).intValue();
  }

  public void componentRemoved(Layoutable lw, int index)
  {
    count--;
    int[] array = new int[count];
    System.arraycopy(constraints, 0, array, 0, index);
    if (index < constraints.length) System.arraycopy(constraints, index + 1, array, index, constraints.length - index - 1);
    constraints = array;
  }

  public void layout(LayoutContainer target)
  {
    Insets ins = target.getInsets();
    int size = target.count();
    int rs   = (-gap*(size==0?0:size-1)), loc = 0;

    if (dir == LwToolkit.HORIZONTAL)
    {
      rs  += target.getWidth () - ins.left - ins.right;
      loc =  ins.left;
    }
    else
    {
      rs += target.getHeight() - ins.top - ins.bottom;
      loc =  ins.top;
    }

    for (int i=0; i<size; i++)
    {
      Layoutable l = target.get(i);
      if (dir == LwToolkit.HORIZONTAL)
      {
        int ns = ((size - 1) == i)?target.getWidth() - ins.right - loc:(rs * constraints[i])/100;
        l.setLocation(loc, ins.top);
        l.setSize(ns, target.getHeight() - ins.top - ins.bottom);
        loc += (ns + gap);
      }
      else
      {
        int ns = ((size - 1) == i)?target.getHeight() - ins.bottom - loc:(rs * constraints[i])/100;
        l.setLocation(ins.left, loc);
        l.setSize(target.getWidth() - ins.left - ins.right, ns);
        loc += (ns + gap);
      }
    }
  }

  public Dimension calcPreferredSize(LayoutContainer target)
  {
    int max = 0, size = target.count();
    for (int i=0; i<size; i++)
    {
      Dimension d = target.get(i).getPreferredSize();
      if (dir == LwToolkit.HORIZONTAL)
      {
        if (d.height > max) max = d.height;
      }
      else
      {
        if (d.width > max) max = d.width;
      }
    }
    return (dir == LwToolkit.HORIZONTAL)?new Dimension (target.getWidth() + gap*(size==0?0:size-1), max)
                                        :new Dimension (max, target.getHeight() + gap*(size==0?0:size-1));
  }
}



