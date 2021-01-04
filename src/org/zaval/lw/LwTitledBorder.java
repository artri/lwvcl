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
 * This	 class inherits LwBorder class to support a title area for the border view. The view using
 * the title bounds and alignment that are provided with an owner component, paints the border
 * with a break (title area). To use the title border view it is necessary to do following steps:
 * <ul>
 *   <li>
 *     The owner component has to implement LwTitleInfo interface to provide the title
 *     parameters: the title bounds - to determine location and size of the title and
 *     the title alignment - to determine where the title has to be placed. The alignment
 *     can have one of following values :  Alignment.TOP - to use top border line as
 *     the title place, Alignment.BOTTOM - to use bottom border line as the title place,
 *     Alignment.LEFT - to use left border line as the title place and Alignment.RIGHT -
 *     to use right border line as the title place.
 *   </li>
 *   <li>
 *     Sets the title border view with appropriate line alignment for the owner component:
 *     <pre>
 *       ...
 *       LwComponent c = LwTitleInfoComponent();
 *       c.getViewMan(true).setBorder(new LwTitledBorder(LwBorder.ETCHED));
 *       ...
 *     </pre>
 *   </li>
 * </ul>
 * <p>
 * The view has line alignment propety that defines border line alignment relatively the
 * title area and can have following values: Alignment.TOP, Alignment.BOTTOM, Alignment.CENTER.
 * The next samples illustrate the property usage:
 * <br><br>
 * <table border="1" width="100%">
 *   <tr>
 *     <td align="center">
 *        <b>Property value</b>
 *     </td>
 *     <td align="center">
 *        <b>Result application</b>
 *     </td>
 *   </tr>
 *   <tr>
 *     <td align="center">
 *        Alignment.BOTTOM
 *     </td>
 *     <td align="center">
 *       <img src="images/TitledBorderBottom.gif">
 *     </td>
 *   </tr>
 *   <tr>
 *     <td align="center">
 *       Alignment.CENTER
 *     </td>
 *     <td align="center">
 *       <img src="images/TitledBorderCenter.gif">
 *     </td>
 *   </tr>
 *   <tr>
 *     <td align="center">
 *       Alignment.TOP
 *     </td>
 *     <td align="center">
 *       <img src="images/TitledBorderTop.gif">
 *     </td>
 *   </tr>
 * <table>
 */
public class LwTitledBorder
extends LwBorder
{
    private int lineAlignment;
   /**
    * Constructs the border view with the specified border type. The default line alignment is
    * Alignment.BOTTOM.
    * @param <code>type</code> the border type.
    */
    public LwTitledBorder(int type) {
      this(type, Alignment.BOTTOM);
    }

   /**
    * Constructs the border view with the specified border type and the line alignment.
    * @param <code>type</code> the border type.
    * @param <code>a</code> the line alignment.
    */
    public LwTitledBorder(int type, int a)
    {
      super(type);
      if (a != Alignment.BOTTOM && a != Alignment.TOP && a != Alignment.CENTER)
        throw new IllegalArgumentException();
      lineAlignment = a;
    }

   /**
    * Paints the view using a given width and height. The location where the
    * view has to be painted is determined with <code>x</code> and <code>y</code>
    * coordinates.
    * @param <code>g</code> the specified context to be used for painting.
    * @param <code>x</code> the x coordinate.
    * @param <code>y</code> the y coordinate.
    * @param <code>w</code> the width of the view.
    * @param <code>h</code> the height of the view.
    * @param <code>d</code> the owner component.
    */
    public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
    {
      if (d instanceof LwTitleInfo)
      {
        Rectangle r = ((LwTitleInfo)d).getTitleBounds();
        int       o = ((LwTitleInfo)d).getTitleAlignment();
        int       borderType = getBorderType();

        int xx = w - 1;
        int yy = h - 1;
        switch (o)
        {
          case Alignment.TOP:
          {
            if (lineAlignment == Alignment.TOP)    y = r.y;
            else
            if (lineAlignment == Alignment.BOTTOM) y = r.y + r.height - 1;
            else
            if (lineAlignment == Alignment.CENTER) y = r.y + r.height/2;

            leftLine(g, x, y, x, yy);
            rightLine(g, xx, y, xx, yy);
            topLine(g, x, y, r.x, y);
            topLine(g, r.x + r.width, y, xx, y);
            bottomLine(g, x, yy, xx, yy);
          } break;
          case Alignment.LEFT:
          {
            if (lineAlignment == Alignment.TOP)    x = r.x;
            else
            if (lineAlignment == Alignment.BOTTOM) x = r.x + r.width - 1;
            else
            if (lineAlignment == Alignment.CENTER) x = r.x + r.width/2;

            leftLine(g, x, y, x, r.y);
            leftLine(g, x, r.y + r.height, x, yy);
            rightLine(g, xx, y, xx, yy);
            topLine(g, x, y, xx, y);
            bottomLine(g, x, yy, xx, yy);
          } break;
          case Alignment.BOTTOM:
          {
            if (lineAlignment == Alignment.TOP)    yy = r.y + r.height - 1;
            else
            if (lineAlignment == Alignment.BOTTOM) yy = r.y;
            else
            if (lineAlignment == Alignment.CENTER) yy = r.y + r.height/2;

            leftLine(g, x, y, x, yy);
            rightLine(g, xx, y, xx, yy);
            topLine(g, x, y, xx, y);
            bottomLine(g, x, yy, r.x, yy);
            bottomLine(g, r.x + r.width, yy, xx, yy);
          } break;
          case Alignment.RIGHT :
          {
            if (lineAlignment == Alignment.TOP)    xx = r.x + r.width - 1;
            else
            if (lineAlignment == Alignment.BOTTOM) xx = r.x;
            else
            if (lineAlignment == Alignment.CENTER) xx = r.x + r.width/2;

            leftLine(g, x, y, x, yy);
            rightLine(g, xx, y, xx, r.y);
            rightLine(g, xx, r.y + r.height, xx, yy);
            topLine(g, x, y, r.x, y);
            bottomLine(g, x, yy, xx, yy);
          } break;
        }
      }
      else
        super.paint(g, x, y, w, h, d);
    }
}
