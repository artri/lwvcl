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
 * The class implements layout manager interface. The layout divides container area
 * into five parts: <code>North</code>, <code>South</code>, <code>East</code>,
 * <code>West</code>, and <code>Center</code>.  To add a component to a container
 * with the border layout, use one of constants of this layout manager as the constraints,
 * for example:
 * <pre>
 *    ...
 *    LwContainer c = new LwPanel();
 *    c.setLwLayout(new LwBorderLayout());
 *    c.add(LwBorderLayout.SOUTH, new LwButton("South"));
 *    ...
 * </pre>
 * The image below shows how the five buttons have been laid out with the border layout
 * manager inside the container: <br>
 * <img src="images/BorderLayoutApp.gif">
 * <br><br>
 * Actually the light weight border layout manager is the same of java.awt.BorderLayout.
 */
public class LwBorderLayout
implements LwLayout
{
   /**
    * The north layout constraint (top of container).
    */
    public static final Object NORTH  = new Integer(1);

   /**
    * The south layout constraint (bottom of container).
    */
    public static final Object SOUTH  = new Integer(2);

   /**
    * The east layout constraint (left side of container).
    */
    public static final Object EAST   = new Integer(3);

   /**
    * The west layout constraint (right side of container).
    */
    public static final Object WEST   = new Integer(4);

   /**
    * The center layout constraint (middle of container).
    */
    public static final Object CENTER = new Integer(5);

    private int        hgap, vgap;
    private Layoutable north, west, east, south, center;

   /**
    * Constructs a new border layout with no gaps between components.
    */
    public LwBorderLayout() {
      this(0, 0);
    }

   /**
    * Constructs a border layout with the specified gaps between components.
    * The horizontal gap is specified by <code>hgap</code> and the vertical gap is
    * specified by <code>vgap</code>.
    * @param <code>hgap</code> the horizontal gap.
    * @param <code>vgap</code> the vertical gap.
    */
    public LwBorderLayout(int hgap, int vgap) {
      this.hgap = hgap;
      this.vgap = vgap;
    }

   /**
    * Returns the horizontal.
    * @return a horizontal gap.
    */
    public int getHgap() {
      return hgap;
    }

   /**
    * Sets the horizontal gap.
    * @param <code>hgap</code> the horizontal gap.
    */
    public void setHgap(int hgap) {
      this.hgap = hgap;
    }

   /**
    * Returns the vertical gap.
    * @return a vertical gap.
    */
    public int getVgap() {
      return vgap;
    }

   /**
    * Sets the vertical gap.
    * @param <code>vgap</code> the vertical gap.
    */
    public void setVgap(int vgap) {
      this.vgap = vgap;
    }

   /**
    * Invoked when the specified layoutable component is added to the layout container, that
    * uses the layout manager. The specified constraints, layoutable component and child index
    * are passed as arguments into the method. For the border layout manager, the constraints must
    * be equal one of the manager constants, otherwise IllegalArgumentException will be performed.
    * @param <code>id</code> the layoutable component constraints.
    * @param <code>comp</code> the layoutable component.
    * @param <code>index</code> the child index.
    */
    public void componentAdded(Object id, Layoutable comp, int index)
    {
      if (CENTER.equals(id)) center = comp;
      else
      if (NORTH.equals(id)) north = comp;
      else
      if (SOUTH.equals(id)) south = comp;
      else
      if (EAST.equals(id)) east = comp;
      else
      if (WEST.equals(id)) west = comp;
      else                 throw new IllegalArgumentException();
    }

   /**
    * Invoked when the specified layoutable component is removed from the layout
    * container, that uses the layout manager.
    * @param <code>lw</code> the layoutable component to be removed
    * @param <code>index</code> the child index.
    */
    public void componentRemoved (Layoutable lw, int index)
    {
      if (lw == center) center = null;
      else
      if (lw == north)  north = null;
      else
      if (lw == south) south = null;
      else
      if (lw == east)  east = null;
      else
      if (lw == west)  west = null;
    }

   /**
    * Calculates the preferred size dimension for the layout container.
    * The method calculates "pure" preferred size, it means that an insets
    * of the container is not considered as a part of the preferred size.
    * @param <code>target</code> the layout container.
    */
    public Dimension calcPreferredSize(LayoutContainer target)
    {
      Dimension dim = new Dimension();

      if (isVisible(east))
      {
        Dimension d = east.getPreferredSize();
        dim.width += d.width + hgap;
        dim.height = Math.max(d.height, dim.height);
      }

      if (isVisible(west))
      {
        Dimension d = west.getPreferredSize();
        dim.width += d.width + hgap;
        dim.height = Math.max(d.height, dim.height);
      }

      if (isVisible(center))
      {
        Dimension d = center.getPreferredSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
      }

      if (isVisible(north))
      {
        Dimension d = north.getPreferredSize();
        dim.width = Math.max(d.width, dim.width);
        dim.height += d.height + vgap;
      }

      if (isVisible(south))
      {
        Dimension d = south.getPreferredSize();
        dim.width = Math.max(d.width, dim.width);
        dim.height += d.height + vgap;
      }
      return dim;
    }

   /**
    * Lays out the child layoutable components inside the specified layout container.
    * @param <code>target</code> the layout container that needs to be laid out.
    */
    public void layout(LayoutContainer target)
    {
      Insets  insets = target.getInsets();
      Point   offs   = target.getLayoutOffset();
      int top = insets.top + offs.y;
      int bottom = target.getHeight() - insets.bottom;
      int left = insets.left + offs.x;
      int right = target.getWidth() - insets.right;

      if (isVisible(north))
      {
          Dimension d = north.getPreferredSize();
          north.setLocation(left, top);
          north.setSize(right - left, d.height);
          top += d.height + vgap;
      }

      if (isVisible(south))
      {
          Dimension d = south.getPreferredSize();
          south.setLocation(left, bottom - d.height);
          south.setSize(right - left, d.height);
          bottom -= d.height + vgap;
      }

      if (isVisible(east))
      {
          Dimension d = east.getPreferredSize();
          east.setLocation(right - d.width, top);
          east.setSize(d.width, bottom - top);
          right -= d.width + hgap;
      }

      if (isVisible(west))
      {
          Dimension d = west.getPreferredSize();
          west.setLocation(left, top);
          west.setSize(d.width, bottom - top);
          left += d.width + hgap;
      }

      if (isVisible(center))
      {
          center.setLocation(left, top);
          center.setSize    (right - left, bottom - top);
      }
    }

   /**
    * Tests if the specified layout container is not <code>null</code> and visible.
    * @param <code>l</code> the specified layout container.
    * @return <code>true</code> if the layoutable component is not null and visible;
    * otherwise <code>false</code>
    */
    protected static boolean isVisible (Layoutable l) {
      return l != null && l.isVisible();
    }
}




