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
 * This class implements image render. The target object is java.awt.Image object.
 */
public class LwImgRender
extends LwRender
{
  /**
   * Constructs the render with the specified target image and the view type.
   * @param <code>img</code> the specified target image to be rendered.
   * @param <code>type</code> the specified view type.
   */
   public LwImgRender (Image img, int type) {
     super(img, type);
   }

  /**
   * Constructs the render with the specified target image name. The name is
   * a path to the image file relatively the "org/zaval/lw" directory
   * The constructor sets MOSAIC view type as default.
   * @param <code>name</code> the specified image name.
   */
   public LwImgRender(String name) {
     this(name, MOSAIC);
   }

  /**
   * Constructs the render with the specified target image name and the view type.
   * The name is a path to the image file relatively light weight resource directory
   * ("org/zaval/lw").
   * @param <code>name</code> the specified image name.
   * @param <code>type</code> the specified view type.
   */
   public LwImgRender (String name, int type) {
     this(LwToolkit.getImage(name), type);
   }

  /**
   * Gets the target image object that is painted with the render.
   * @return a target image object.
   */
   public Image getImage () {
     return (Image)getTarget();
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
   public /*C#override*/ void paint (Graphics g, int x, int y, int w, int h, Drawable d) {
    g.drawImage (getImage(), x, y, w, h, null);
   }

  /**
   * Calculates and returns the view preferred size. The render returns the target image
   * size as the "pure" preferred size.
   * @return a "pure" preferred size of the view.
   */
   protected /*C#override*/ Dimension calcPreferredSize()  {
     Image img = getImage();
     return img == null?super.calcPreferredSize():new Dimension (img.getWidth(null), img.getHeight(null));
   }
}
