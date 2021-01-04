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
import java.awt.image.*;

/**
 * This class is the same LwImgRender render but it allows to render specified part
 * of the target image object.
 */
public class LwImgSetRender
extends LwImgRender
{
   private int x, y, w, h;

  /**
   * Constructs the render with the specified target image name, the view type and
   * the specified rectangular image part that should be rendered.
   * @param <code>name</code> the specified target image name.
   * @param <code>x</code> the x coordinate of the top left corner the rendered part of the image.
   * @param <code>y</code> the y coordinate of the top left corner the rendered part of the image.
   * @param <code>w</code> the width the rendered part of the image.
   * @param <code>h</code> the height the rendered part of the image.
   * @param <code>type</code> the specified view type.
   */
   public LwImgSetRender (String name, int x, int y, int w, int h, int type) {
     this (LwToolkit.getImage(name), x, y, w, h, type);
   }

  /**
   * Constructs the render with the specified target image, the view type and
   * the specified rectangular image part that should be rendered.
   * @param <code>img</code> the specified target image to be rendered.
   * @param <code>x</code> the x coordinate of the top left corner the rendered part of the image.
   * @param <code>y</code> the y coordinate of the top left corner the rendered part of the image.
   * @param <code>w</code> the width the rendered part of the image.
   * @param <code>h</code> the height the rendered part of the image.
   * @param <code>type</code> the specified view type.
   */
   public LwImgSetRender (Image img, int x, int y, int w, int h, int type)
   {
     super(img, type);
     this.x = x;
     this.y = y;
     this.w = w;
     this.h = h;
   }

   public /*C#override*/ void paint (Graphics g, int x, int y, int w, int h, Drawable d) {
     g.drawImage (getImage(), x, y, x + w, y + h,
                  this.x, this.y, this.x + this.w, this.y + this.h, null);
   }

  /**
   * Calculates and returns the view preferred size. The render returns the target image
   * size as the "pure" preferred size.
   * @return a "pure" preferred size of the view.
   */
   protected /*C#override*/ Dimension calcPreferredSize()  {
     return getImage() == null?super.calcPreferredSize():new Dimension (w, h);
   }
}
