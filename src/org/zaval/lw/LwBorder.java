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
 * The class implements set of borders views. The library registers the views as static objects
 * that can be used by the border view key. It is possible to use following keys to get a border
 * view as a static object:
 * <ul>
 *   <li>"br.etched"</li>
 *   <li>"br.raised"</li>
 *   <li>"br.sunken"</li>
 *   <li>"br.sunken2"</li>
 *   <li>"br.plain"</li>
 *   <li>"br.dot"</li>
 * </ul>
 */
public class LwBorder
extends LwView
{
   /**
    * The RAISED border type.
    */
    public static final int RAISED = 1;

   /**
    * The SUNKEN border type.
    */
    public static final int SUNKEN = 2;

   /**
    * The ETCHED border type.
    */
    public static final int ETCHED = 3;

   /**
    * The PLAIN border type.
    */
    public static final int PLAIN = 4;

   /**
    * The DOT border type.
    */
    public static final int DOT = 5;

   /**
    * The SUNKEN2 border type.
    */
    public static final int SUNKEN2 = 6;


    private int borderType;

   /**
    * Constructs the new border view with the specified border type.
    * The view uses STRETCH view type.
    * @param <code>type</code> the specified border type.
    */
    public LwBorder(int type) {
      super(STRETCH);
      setBorderType(type);
    }

   /**
    * Gets the border type.
    * @return a border type.
    */
    public int getBorderType() {
      return borderType;
    }

   /**
    * Draws the left vertical border line between the coordinates (x1,y1) and (x2,y2).
    * @param <code>g</code> the specified context to be used for drawing.
    * @param <code>x1</code> the x coordinate of the start of the line.
    * @param <code>y1</code> the y coordinate of the start of the line.
    * @param <code>x2</code> the x coordinate of the end of the line.
    * @param <code>y2</code> the y coordinate of the end of the line.
    */
    protected /*C#virtual*/ void leftLine(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(Color.gray);
      switch(borderType)
      {
        case DOT:
        {
          LwToolkit.drawDotVLine(g, y1, y2, x1);
        } break;
        case PLAIN:
        {
          g.setColor(Color.black);
          g.drawLine(x1, y1, x1, y2);
        } break;
        case ETCHED:
        {
          g.drawLine(x1, y1, x1, y2-1);
          g.setColor(Color.white);
          g.drawLine(x1 + 1, y1 + 1, x1 + 1, y2 - 2);
        } break;
        case RAISED:
        {
          g.setColor(Color.white);
          g.drawLine(x1, y1, x1, y2 - 1);
        } break;
        case SUNKEN2:
        case SUNKEN:
        {
          g.drawLine(x1, y1, x1, y2 - 1);
          if (borderType == SUNKEN)
          {
            g.setColor(Color.black);
            g.drawLine(x1 + 1, y1 + 1, x1 + 1, y2 - 2);
          }
        } break;
      }
    }

   /**
    * Draws the right vertical border line between the coordinates (x1,y1) and (x2,y2).
    * @param <code>g</code> the specified context to be used for drawing.
    * @param <code>x1</code> the x coordinate of the start of the line.
    * @param <code>y1</code> the y coordinate of the start of the line.
    * @param <code>x2</code> the x coordinate of the end of the line.
    * @param <code>y2</code> the y coordinate of the end of the line.
    */
    protected /*C#virtual*/ void rightLine(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(Color.gray);
      switch(borderType)
      {
        case DOT: LwToolkit.drawDotVLine(g, y1, y2, x2); break;
        case PLAIN:
        {
          g.setColor(Color.black);
          g.drawLine(x2, y1, x2, y2);
        } break;
        case ETCHED:
        {
          g.drawLine(x2 - 1, y1, x2 - 1, y2 - 1);
          g.setColor(Color.white);
          g.drawLine(x2, y1 + 1, x2, y2);
        } break;
        case RAISED:
        {
          g.drawLine(x2, y1, x2, y2);
          //g.drawLine(x2 - 1, y1 + 1, x2 - 1, y2 - 1);
          //g.setColor(Color.black);
          //g.drawLine(x2, y1, x2, y2);
        } break;
        case SUNKEN2:
        case SUNKEN:
        {
          g.setColor(Color.white);
          g.drawLine(x2, y1, x2, y2);
        } break;
      }
    }

   /**
    * Draws the top horizontal border line between the coordinates (x1,y1) and (x2,y2).
    * @param <code>g</code> the specified context to be used for drawing.
    * @param <code>x1</code> the x coordinate of the start of the line.
    * @param <code>y1</code> the y coordinate of the start of the line.
    * @param <code>x2</code> the x coordinate of the end of the line.
    * @param <code>y2</code> the y coordinate of the end of the line.
    */
    protected /*C#virtual*/ void topLine(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(Color.gray);
      switch(borderType)
      {
        case DOT:
        {
          LwToolkit.drawDotHLine(g, x1, x2, y1);
        } break;
        case PLAIN:
        {
          g.setColor(Color.black);
          g.drawLine(x1, y1, x2, y1);
        } break;
        case ETCHED:
        {
          g.drawLine(x1, y1, x2 - 1, y1);
          g.setColor(Color.white);
          g.drawLine(x1 + 1, y1 + 1, x2 - 2, y1 + 1);
        } break;
        case RAISED:
        {
          g.setColor(Color.white);
          g.drawLine(x1, y1, x2 - 1, y1);
        } break;
        case SUNKEN2:
        case SUNKEN:
        {
          g.drawLine(x1, y1, x2 - 1, y1);
          if (borderType == SUNKEN)
          {
            g.setColor(Color.black);
            g.drawLine(x1 + 1, y1 + 1, x2 - 2, y1 + 1);
          }
        } break;
      }
    }

   /**
    * Draws the bottom horizontal border line between the coordinates (x1,y1) and (x2,y2).
    * @param <code>g</code> the specified context to be used for drawing.
    * @param <code>x1</code> the x coordinate of the start of the line.
    * @param <code>y1</code> the y coordinate of the start of the line.
    * @param <code>x2</code> the x coordinate of the end of the line.
    * @param <code>y2</code> the y coordinate of the end of the line.
    */
    protected /*C#virtual*/ void bottomLine(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(Color.gray);
      switch(borderType)
      {
        case DOT:
        {
          LwToolkit.drawDotHLine(g, x1, x2, y2);
        } break;
        case PLAIN:
        {
          g.setColor(Color.black);
          g.drawLine(x1, y2, x2, y2);
        } break;
        case ETCHED:
        {
          g.drawLine(x1, y2 - 1, x2 - 1, y2 - 1);
          g.setColor(Color.white);
          g.drawLine(x1 + 1, y2, x2, y2);
        } break;
        case RAISED:
        {

           g.drawLine(x1, y2, x2, y2);
          //g.drawLine(x1 + 1, y2 - 1, x2 - 1, y2 - 1);
          //g.setColor(Color.black);
          //g.drawLine(x1, y2, x2, y2);
        } break;
        case SUNKEN2:
        case SUNKEN:
        {
          g.setColor(Color.white);
          g.drawLine(x1, y2, x2, y2);
        } break;
      }
    }

   /**
    * Paints the view using a given width and height. The location where the
    * view has to be painted is determined with <code>x</code> and <code>y</code>
    * coordinates. This method is implemented with the border view and this defines
    * face of the view.
    * @param <code>g</code> the specified context to be used for painting.
    * @param <code>x</code> the x coordinate.
    * @param <code>y</code> the y coordinate.
    * @param <code>w</code> the width of the view.
    * @param <code>h</code> the height of the view.
    * @param <code>d</code> the owner component.
    */
    public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
    {
      int xx = x + w - 1;
      int yy = y + h - 1;
      leftLine  (g, x, y, xx, yy);
      rightLine (g, x, y, xx, yy);
      topLine   (g, x, y, xx, yy);
      bottomLine(g, x, y, xx, yy);
    }

   /**
    * Gets the view insets. The insets is used to calculate the owner component
    * insets.
    * @return an insets of the view.
    */
    public /*C#override*/ Insets getInsets() {
      return new Insets(2, 2, 2, 2);
    }

    protected /*C#override*/ Dimension calcPreferredSize() {
      return new Dimension (2, 2);
    }

    private void setBorderType(int t)
    {
      if(t != RAISED && t != SUNKEN && t != ETCHED && t != PLAIN && t != DOT && t != SUNKEN2)
        throw new IllegalArgumentException();
      borderType = t;
    }
}
