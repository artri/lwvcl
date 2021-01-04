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
 * Defines the interface for classes that know how to paint itself. The interface provides
 * set of properties that are bound with painting process.
 */
public interface Drawable
extends org.zaval.util.Validationable, Layoutable
{
 /**
  * Paints this drawable component.
  * @param <code>g</code> the graphics context to be used for painting.
  */
  void paint(Graphics g);

 /**
  * Updates this drawable component. The calling of the method precedes
  * the calling of <code>paint</code> method and it is performed with
  * <code>repaint</code> method. The method can be used to fill the drawable
  * component with the background color if the component is opaque.
  * @param <code>g</code> the specified context to be used for updating.
  */
  void update(Graphics g);

 /**
  * Performs repainting process of this drawable component. The method causes
  * calling of <code>update</code> and than <code> paint </code> methods.
  */
  void repaint ();

 /**
  * Performs repainting process of the specified rectangular area of this component. The method causes
  * calling of <code>update</code> and than <code> paint </code> methods.
  * @param  <code>x</code>  the <i>x</i> coordinate.
  * @param  <code>y</code>  the <i>y</i> coordinate.
  * @param  <code>w</code>  the width.
  * @param  <code>h</code>  the height.
  */
  void  repaint (int x, int y, int w, int h);

 /**
  * Gets the opaque of this drawable component. If the method returns
  * <code>false</code> than the component is transparent, in this case
  * <code>update</code> method is not be called during painting process.
  * @return  <code>true</code> if the component is opaque; otherwise
  * <code>false</code>.
  */
  boolean isOpaque();

 /**
  * Sets the opaque of this drawable component. Use <code> false </code>
  * argument value to make a transparent component from this component.
  * @param  <code>b</code> the opaque flag.
  */
  void  setOpaque(boolean b);

 /**
  * Returns an origin of the drawable component. The origin defines an offset of the component view
  * relatively the component point of origin. The method can be implemented to organize
  * scrolling of the drawable component view.
  * @return an origin of the component.
  */
  Point getOrigin();

 /**
  * Gets the background color of this drawable component.
  * @return a componen background color.
  */
  Color getBackground();

  /**
   * Determines whether this drawable component is enabled. If the method returns
   * <code>true</code> than the drawable component is enabled and can participate in event
   * handling and performing processes. Drawable components are enabled initially by default.
   * @return <code>true</code> if the drawable component is enabled; <code>false</code> otherwise.
   */
   boolean isEnabled();
}
