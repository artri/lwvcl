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
package org.zaval.lw.samples;

import java.awt.*;
import java.util.*;
import org.zaval.lw.*;
import org.zaval.data.*;

public class LwCustomLayoutSample
extends LwSample
implements LwLayout
{
  public static final Integer TOP_LEFT     = new Integer(1);
  public static final Integer TOP_RIGHT    = new Integer(2);
  public static final Integer BOTTOM_LEFT  = new Integer(3);
  public static final Integer BOTTOM_RIGHT = new Integer(4);

  private Layoutable topLeft, topRight, bottomLeft, bottomRight;

  public void componentAdded(Object id, Layoutable lw, int index)
  {
    if (id.equals(TOP_LEFT)) topLeft = lw;
    else
    if (id.equals(TOP_RIGHT)) topRight = lw;
    else
    if (id.equals(BOTTOM_LEFT)) bottomLeft = lw;
    else
    if (id.equals(BOTTOM_RIGHT)) bottomRight = lw;
    else throw new IllegalArgumentException();
  }

  public void componentRemoved (Layoutable lw, int index)
  {
    if (topLeft == lw) topLeft = null;
    else
    if (topRight == lw) topRight = null;
    else
    if (bottomRight == lw) bottomRight = null;
    else
    if (bottomLeft == lw) bottomLeft = null;
  }

  public Dimension calcPreferredSize(LayoutContainer target)
  {
    int w1 = 0, w2 = 0, h1 = 0, h2 = 0;
    if (topLeft != null && topLeft.isVisible())
    {
      Dimension ps = topLeft.getPreferredSize();
      w1 = ps.width;
      h1 = ps.height;
    }

    if (topRight != null && topRight.isVisible())
    {
      Dimension ps = topRight.getPreferredSize();
      w2 = ps.width;
      h1 = Math.max(ps.height, h1);
    }
    if (bottomLeft != null && bottomLeft.isVisible())
    {
      Dimension ps = bottomLeft.getPreferredSize();
      w1 = Math.max(ps.width, w1);
      h2 = ps.height;
    }

    if (bottomRight != null && bottomRight.isVisible())
    {
      Dimension ps = bottomRight.getPreferredSize();
      w2 = Math.max(ps.width, w2);
      h2 = Math.max(h2, ps.height);
    }
    return new Dimension (w1 + w2, h1 + h2);
  }

  public void  layout (LayoutContainer target)
  {
    int w1 = 0, w2 = 0, h1 = 0, h2 = 0;
    Insets insets = target.getInsets();

    if (topLeft != null && topLeft.isVisible())
    {
      Dimension ps = topLeft.getPreferredSize();
      w1 = ps.width;
      h1 = ps.height;
    }

    if (topRight != null && topRight.isVisible())
    {
      Dimension ps = topRight.getPreferredSize();
      w2 = ps.width;
      h1 = Math.max(ps.height, h1);
    }

    if (bottomLeft != null && bottomLeft.isVisible())
    {
      Dimension ps = bottomLeft.getPreferredSize();
      w1 = Math.max(ps.width, w1);
      h2 = ps.height;
    }

    if (bottomRight != null && bottomRight.isVisible())
    {
      Dimension ps = bottomRight.getPreferredSize();
      w2 = Math.max(ps.width, w2);
      h2 = Math.max(h2, ps.height);
    }

    Point offset = target.getLayoutOffset();
    if (topLeft != null && topLeft.isVisible())
    {
      topLeft.setLocation(insets.left + offset.x, insets.top + offset.y);
      topLeft.setSize(w1, h1);
    }

    if (topRight != null && topRight.isVisible())
    {
      topRight.setLocation(insets.left + w1 + offset.x, insets.top + offset.y);
      topRight.setSize(w2, h1);
    }

    if (bottomRight != null && bottomRight.isVisible())
    {
      bottomRight.setLocation(insets.left + w1 + offset.x, insets.top + h1 + offset.y);
      bottomRight.setSize    (w2, h2);
    }

    if (bottomLeft != null && bottomLeft.isVisible())
    {
      bottomLeft.setLocation(insets.left + offset.x, insets.top + h1 + offset.y);
      bottomLeft.setSize    (w1, h2);
    }
  }

  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();
    root.getViewMan(true).setBorder("br.dot");

    root.setLwLayout(new LwCustomLayoutSample());
    LwComponent c1 = new LwButton("Top Left");
    c1.setBackground(Color.green);
    LwComponent c2 = new LwButton("Top Right");
    c2.setBackground(Color.yellow);
    LwComponent c3 = new LwButton("Bottom Left");
    c3.setBackground(Color.blue);
    LwComponent c4 = new LwButton(new LwLabel(new Text("Bottom Right\n...")));
    c4.setBackground(Color.white);

    root.add(LwCustomLayoutSample.TOP_LEFT, c1);
    root.add(LwCustomLayoutSample.TOP_RIGHT, c2);
    root.add(LwCustomLayoutSample.BOTTOM_LEFT, c3);
    root.add(LwCustomLayoutSample.BOTTOM_RIGHT, c4);
    return root;
  }

  public static void main(String[] args) {
    runSampleApp(200, 120, new LwCustomLayoutSample());
  }
}
