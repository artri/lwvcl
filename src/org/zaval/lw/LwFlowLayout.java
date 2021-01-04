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

/**
 * This class implements layout manager interface. The impementation layouts child components
 * according three parameters : direction (can be LwToolkit.HORIZONTAL or LwToolkit.VERTICAL), vertical alignment
 * (Alignment.TOP, Alignment.BOTTOM, Alignment.CENTER) and horizontal alignment (Alignment.LEFT,
 * Alignment.RIGHT, Alignment.CENTER).
 * <p>
 * The first parameter defines direction, if the direction is LwToolkit.VERTICAL than every following
 * component will be added under the previous, otherwise every following component will be added
 * to the right of the previous.
 * <p>
 * The vertical and horizontal alignments define how the child components will be laidout
 * relatively the parent container. The table below shows the samples of the layout manager
 * usage:
 * <table border="1" width="100%">
 *   <tr>
 *     <td align="center"><b>Direction</b></td>
 *     <td align="center"><b>Horizontal alignment</b></td>
 *     <td align="center"><b>Vertical alignment</b></td>
 *     <td align="center"><b>Sample App</b></td>
 *   </tr>
 *   <tr>
 *     <td align="center">LwToolkit.HORIZONTAL</td>
 *     <td align="center">Alignment.LEFT</td>
 *     <td align="center">Alignment.TOP</td>
 *     <td align="center"><img src="images/FlowLayout1.gif"></td>
 *   </tr>
 *   <tr>
 *     <td align="center">LwToolkit.VERTICAL</td>
 *     <td align="center">Alignment.LEFT</td>
 *     <td align="center">Alignment.TOP</td>
 *     <td align="center"><img src="images/FlowLayout2.gif"></td>
 *   </tr>
 *   <tr>
 *     <td align="center">LwToolkit.VERTICAL</td>
 *     <td align="center">Alignment.RIGHT</td>
 *     <td align="center">Alignment.TOP</td>
 *     <td align="center"><img src="images/FlowLayout3.gif"></td>
 *   </tr>
 *   <tr>
 *     <td align="center">LwToolkit.HORIZONTAL</td>
 *     <td align="center">Alignment.CENTER</td>
 *     <td align="center">Alignment.CENTER</td>
 *     <td align="center"><img src="images/FlowLayout4.gif"></td>
 *   </tr>
 *   <tr>
 *     <td align="center">LwToolkit.HORIZONTAL</td>
 *     <td align="center">Alignment.CENTER</td>
 *     <td align="center">Alignment.BOTTOM</td>
 *     <td align="center"><img src="images/FlowLayout5.gif"></td>
 *   </tr>
 * </table>
 */
public class LwFlowLayout
implements LwLayout
{
  private int ax, ay, direction, vgap, hgap;

 /**
  * Constructs a new flow layout manager with default parameters.
  * In this case the direction property is LwToolkit.HORIZONTAL, the horizontal alignment is
  * Alignment.LEFT and the vertical alignment is Alignment.TOP.
  */
  public LwFlowLayout() {
    this(Alignment.LEFT, Alignment.TOP, LwToolkit.HORIZONTAL);
  }

 /**
  * Constructs a new flow layout manager with the specified horizontal and vertical
  * alignments. In this case the direction property is LwToolkit.HORIZONTAL.
  * @param <code>ax</code> the specified horizontal alignment.
  * @param <code>ay</code> the specified vertical alignment.
  */
  public LwFlowLayout (int ax, int ay) {
    this(ax, ay, LwToolkit.HORIZONTAL);
  }

 /**
  * Constructs a new flow layout manager with the specified horizontal,  vertical
  * alignments and the direction.
  * @param <code>ax</code> the specified horizontal alignment.
  * @param <code>ay</code> the specified vertical alignment.
  * @param <code>dir</code> the specified direction.
  */
  public LwFlowLayout (int ax, int ay, int dir) {
    this(ax, ay, dir, 0, 0);
  }

 /**
  * Constructs a new flow layout manager with the specified horizontal,  vertical
  * alignments, the direction and the given gaps.
  * @param <code>ax</code> the specified horizontal alignment.
  * @param <code>ay</code> the specified vertical alignment.
  * @param <code>dir</code> the specified direction.
  * @param <code>vg</code> the specified vertical gap.
  * @param <code>hg</code> the specified horizontal gap.
  */
  public LwFlowLayout (int ax, int ay, int dir, int vg, int hg)
  {
    if (dir != LwToolkit.HORIZONTAL && dir != LwToolkit.VERTICAL) throw new IllegalArgumentException();
    this.ax = ax;
    this.ay = ay;
    direction = dir;
    setGaps(vg, hg);
  }

 /**
  * Calculates the preferred size dimensions for the layout container.
  * The method calculates "pure" preferred size, it means that an insets
  * of the container is not considered.
  * @param <code>target</code> the layout container.
  */
  public Dimension calcPreferredSize (LayoutContainer target) {
    return calcPreferredSize (target, direction, hgap, vgap);
  }

 /**
  * Lays out the child layoutable components inside the layout container.
  * @param <code>c</code> the layout container that needs to be laid out.
  */
  public void layout(LayoutContainer c)
  {
    Dimension psSize = calcPreferredSize(c);
    Insets    ins    = c.getInsets();
    Point     p      = Alignment.getLocation(psSize, ax, ay, c.getWidth() - ins.left - ins.right,
                                                             c.getHeight() - ins.top - ins.bottom);

    int x = p.x, y = p.y;
    for (int i=0; i<c.count(); i++)
    {
      Layoutable a = c.get(i);
      if (a.isVisible())
      {
        Dimension d  = a.getPreferredSize();
        if (direction == LwToolkit.HORIZONTAL)
        {
          a.setLocation(x + ins.left, (psSize.height - d.height)/2 + y + ins.top);
          x += (d.width + hgap);
        }
        else
        {
          a.setLocation(x + (psSize.width - d.width)/2 + ins.right, y + ins.top);
          y += d.height + vgap;
        }
        a.setSize(d.width, d.height);
      }
    }
  }

 /**
  * Gets the vertical gap.
  * @return a vertical gap.
  */
  public int getVGap () {
    return vgap;
  }

 /**
  * Gets the horizontal gap.
  * @return a horizontal gap.
  */
  public int getHGap () {
    return hgap;
  }

 /**
  * Sets the vertical and horizontal gaps.
  * @param <code>vg</code> the specified vertical gap.
  * @param <code>hg</code> the specified horizontal gap.
  */
  public void setGaps (int vg, int hg) {
    vgap = vg;
    hgap = hg;
  }

 /**
  * Invoked when the specified layoutable component is added to the layout container
  * (that uses the layout manager). The specified constraints, layoutable component
  * and child index are passed as arguments into the method. The layout manager doesn't
  * use any constraints, so the method is empty for the manager.
  * @param <code>id</code> the layoutable component constraints.
  * @param <code>lw</code> the layoutable component.
  * @param <code>index</code> the child index.
  */
  public void  componentAdded  (Object id, Layoutable lw, int index) {}

 /**
  * Invoked when the specified layoutable component is removed from the layout
  * container, that uses the layout manager.
  * @param <code>lw</code> the layoutable component to be removed
  * @param <code>index</code> the child index.
  */
  public void  componentRemoved(Layoutable lw, int index) {}

  private static Dimension calcPreferredSize (LayoutContainer c, int direction, int hgap, int vgap)
  {
    Dimension m  = new Dimension();
    int       cc = 0;
    for (int i = 0; i<c.count(); i++)
    {
      Layoutable a = c.get(i);
      if (a.isVisible())
      {
        Dimension d = a.getPreferredSize();
        if (direction == LwToolkit.HORIZONTAL)
        {
          m.width  += d.width;
          m.height  = Math.max (d.height, m.height);
        }
        else
        {
          m.width   = Math.max (d.width, m.width);
          m.height += d.height;
        }
        cc++;
      }
    }

    if (direction == LwToolkit.HORIZONTAL) m.width  += (hgap * (cc - 1));
    else                                   m.height += (vgap * (cc - 1));

    return m;
  }
}



