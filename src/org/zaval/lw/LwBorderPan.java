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
 * This is lightweight component that can be used to organize border panel.
 * The panel can use any other lightweigt component as a title and the title
 * can be placed on the top or on the bottom of the container. The border panel
 * supports following alignment for the title: "left", "center", "right". The border panel
 * implements and uses own layout manager, so to add a component to the container it is
 * necessary to use one of the following constraints:
 * <ul>
 *   <li>
 *     LwBorderPan.CENTER. The constraint is used to add a central component.
 *   </li>
 *   <li>
 *     LwBorderPan.TITLE. The constraint is used to add a title component.
 *   </li>
 * </ul>
 * <p>
 * The table below shows diffirent samples of the border panel usage:
 * <table border="1" width="100%">
 *   <tr>
 *     <td align="center"> <b>Source code </b> </td>
 *     <td align="center"> <b>Application image </b> </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       <pre>
 *         ...
 *         LwBorderPan bp = new LwBorderPan();
 *         bp.setXAlignment(Alignment.CENTER);
 *         bp.add(LwBorderPan.TITLE,  new LwLabel("Title"));
 *         bp.add(LwBorderPan.CENTER, new LwLabel("Center"));
 *         ...
 *       </pre>
 *     </td>
 *     <td align="center">
 *        <img src="images/BorderPanelApp1.gif">
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       <pre>
 *         ...
 *         LwBorderPan bp = new LwBorderPan();
 *         bp.setXAlignment(Alignment.LEFT);
 *         bp.add(LwBorderPan.TITLE,  new LwLabel("Title"));
 *         bp.add(LwBorderPan.CENTER, new LwLabel("Center"));
 *         ...
 *       </pre>
 *     </td>
 *     <td align="center">
 *        <img src="images/BorderPanelApp2.gif">
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       <pre>
 *         ...
 *         LwBorderPan bp = new LwBorderPan();
 *         bp.setXAlignment(Alignment.RIGHT);
 *         bp.setTitleAlignment(Alignment.BOTTOM);
 *         bp.add(LwBorderPan.TITLE,  new LwLabel("Title"));
 *         bp.add(LwBorderPan.CENTER, new LwLabel("Center"));
 *         ...
 *       </pre>
 *     </td>
 *     <td align="center">
 *        <img src="images/BorderPanelApp3.gif">
 *     </td>
 *   </tr>
 * </table>
 */
public class LwBorderPan
extends LwPanel
implements LwLayout, LwTitleInfo
{
 /**
  * The center layout constraint (it is used to add a central component).
  */
  public static final Object CENTER = new Integer(1);

 /**
  * The title layout constraint (it is used to add a title component).
  */
  public static final Object TITLE  = new Integer(2);

  private Layoutable label;
  private Layoutable center;
  private int titleAlignment, xAlignment, indent = 4;

 /**
  * Constructs a new border panel.
  */
  public LwBorderPan() {
    this(null, null);
  }

 /**
  * Constructs a new border panel with the specified title component and center component.
  * In this case the title and the center components will be added to the border panel
  * automatically
  * @param <code>title</code> the specified title component.
  * @param <code>center</code> the specified central component.
  */
  public LwBorderPan(LwComponent title, LwComponent center)
  {
    getViewMan(true).setBorder(new LwTitledBorder(LwBorder.ETCHED, Alignment.CENTER));
    setTitleAlignment(Alignment.TOP);
    setXAlignment(Alignment.LEFT);
    if (title  != null) add (TITLE, title);
    if (center != null) add (CENTER, center);
  }

 /**
  * The method is called if a component has been added to the owner
  * layoutable container. The implementation performs IllegalArgumentException
  * if the constraint is not defined or doesn't equal LwBorderPan.TITLE or
  * LwBorderPan.CENTER constant.
  * @param <code>id</code> the layoutable component constraints.
  * @param <code>lw</code> the layoutable component.
  * @param <code>index</code> the child index.
  */
  public void componentAdded (Object id, Layoutable lw, int index)
  {
    if (id.equals(TITLE)) label = lw;
    else
    if (id.equals(CENTER)) center = lw;
    else throw new IllegalArgumentException();
  }

 /**
  * The method is called if a component has been removed from the owner
  * layoutable container.
  * @param <code>lw</code> the layoutable component.
  * @param <code>index</code> the child index.
  */
  public void  componentRemoved (Layoutable lw, int index) {
    if (lw == label) label = null;
    else if (lw == center) center = null;
  }

 /**
  * The method computes a preferred size for the specified target component.
  * @param <code>target</code> the specified layoutable container.
  */
  public Dimension calcPreferredSize(LayoutContainer target)
  {
     Dimension ps = new Dimension();
     if (center != null && center.isVisible()) ps = center.getPreferredSize();

     if (label != null && label.isVisible())
     {
       Dimension lps = label.getPreferredSize();
       lps.width += (2*indent);
       ps.height += lps.height;
       ps.width   = Math.max(ps.width, lps.width);
     }
     return ps;
  }

 /**
  * The method is an implementation of appropriate layout manager method.
  * The method performs layouting of the child layoutable components for the specified
  * target component.
  * @param <code>target</code> the specified layoutable container.
  */
  public void layout(LayoutContainer target)
  {
    Insets targetInsets = getInsets();
    int    y = 0, x = 0, w = 0, h = 0;
    if (label != null && label.isVisible())
    {
      Dimension ps = label.getPreferredSize();
      if (getTitleAlignment() == Alignment.BOTTOM)
        y = height - targetInsets.bottom - ps.height;
      else
        y = targetInsets.top;

      if (xAlignment == Alignment.LEFT ) x = targetInsets.left + indent;
      else
      if (xAlignment == Alignment.RIGHT) x = width - targetInsets.right - ps.width - indent;
      else
        x = width/2 - ps.width/2;

      w = ps.width;
      h = ps.height;
      label.setSize(w, h);
      label.setLocation(x, y);
    }

    if (center != null && center.isVisible())
    {
      if (getTitleAlignment() == Alignment.BOTTOM)
      {
        center.setSize(width - targetInsets.right - targetInsets.left, height - targetInsets.top - Math.max(height - y, targetInsets.bottom));
        center.setLocation(targetInsets.left, targetInsets.bottom);
      }
      else
      {
        center.setSize(width - targetInsets.right - targetInsets.left, height - targetInsets.bottom - Math.max(y + h, targetInsets.top));
        center.setLocation(targetInsets.left, Math.max(y + h, targetInsets.top));
      }
    }
  }

 /**
  * Overrides parent method to define default layout. The component returns itself
  * as the default layout manager.
  * @return the default layout manager.
  */
  protected /*C#override*/ LwLayout getDefaultLayout() {
    return this;
  }

 /**
  * Gets the rectangle where the title component has been placed with the border panel.
  * @return a rectangle where the the title component has been placed.
  */
  public Rectangle getTitleBounds() {
    if (label != null) return label.getBounds();
    else return getBounds();
  }

 /**
  * Sets the specified vertical alignment for the title of the border panel. The border panel
  * supports Alignment.TOP and Alignment.BOTTOM values for the alignment, otherwise
  * IllegalArgumentException will be thrown.
  * @param <code>a</code> the vertical alignment.
  */
  public void setTitleAlignment(int a)
  {
    if (a != Alignment.TOP && a != Alignment.BOTTOM) throw new IllegalArgumentException();
    if (a != titleAlignment)
    {
      titleAlignment = a;
      vrp();
    }
  }

 /**
  * Sets the specified horizontal alignment for the title of the border panel. The border panel
  * supports Alignment.LEFT, Alignment.RIGHT and Alignment.CENTER values for the alignment,
  * otherwise IllegalArgumentException will be thrown.
  * @param <code>a</code> the horizontal alignment.
  */
  public void setXAlignment(int a)
  {
    if (a != Alignment.LEFT && a != Alignment.RIGHT && a != Alignment.CENTER) throw new IllegalArgumentException();
    if (a != xAlignment)
    {
      xAlignment = a;
      vrp();
    }
  }

 /**
  * Gets the vertical alignment of the title component.
  * @return a vertical alignment.
  */
  public int getTitleAlignment() {
    return titleAlignment;
  }
}


