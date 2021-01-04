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
 * This light weight component is used to show an image. To show the image the
 * component uses LwImgRender as the face view.
 */
public class LwImage
extends LwCanvas
{
  /**
   * Constructs the component with the specified image object.
   * LwView.STRETCH view type is set by the contructor.
   * @param <code>img</code> the specified image object.
   */
   public LwImage(Image img) {
     this(new LwImgRender(img, LwView.STRETCH));
   }

  /**
   * Constructs the component with the specified image name. The name is used to
   * read the image as resource. It means that the name should point to an image
   * file relatively "org/zaval/lw/rs/" directory. LwView.ORIGINAL view type is
   * set by the contructor.
   * @param <code>name</code> the specified image name.
   */
   public LwImage(String name) {
     this(new LwImgRender(name, LwView.ORIGINAL));
   }

  /**
   * Constructs the component with the specified image render. The render is used as
   * the face view of the component.
   * @param <code>r</code> the specified image render.
   */
   public LwImage(LwImgRender r) {
     getViewMan(true).setView(r);
   }

  /**
   * Gets the image that is shown with the component.
   * @return an image that is shown.
   */
   public Image getImage() {
     return getImageRender().getImage();
   }

  /**
   * Gets the image render that is used as the face view for the component with the view manager.
   * @return an image render.
   */
   public LwImgRender getImageRender() {
     return (LwImgRender)getViewMan(true).getView();
   }
}
