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
import org.zaval.lw.event.*;

/**
 * This interface is top-level container for all other lightweight components. The main
 * purpose of the interface to provide abstraction level that binds the library with a
 * concrete GUI implementation. Every lightweight component is bound with a desktop
 * container. Use <code>LwToolkit.getDesktop</code> method to get a desktop for the
 * specified lightweight component. The desktop consists of layers. Layer is a special
 * light weight container that is child component of the desktop. It is impossible
 * to add a not-layer component to the desktop. Use <code>getRootLayer</code> method
 * to get the root layer. The layer should be used if you want to add a light weight
 * component on the desktop surface. The other available layer is the windows layer. The
 * layer provides functionality to work with internal frames.
 */
public interface LwDesktop
extends LwContainer
{
 /**
  * Gets the root layer that should be used to add a light weight component on the desktop
  * surface.
  * @return a root layer.
  */
  LwLayer getRootLayer ();

 /**
  * Gets the layer by the specified id. There are two layers available by the "root" and "win" IDs.
  * @param <code>id</code> by the specified id.
  * @return a layer.
  */
  LwLayer getLayer (Object id);

 /**
  * Gets the list of available layers' IDs.
  * @return a list of the layers' IDs.
  */
  Object[] getLayersIDs();

 /**
  * Gets a graphics context for this desktop. This is adaptive method and it is used to bind
  * native GUI implementation with the components of the library.
  * @return a graphics context.
  */
  Graphics getGraphics();

 /**
  * Creates an off-screen drawable image to be used for double buffering with the specified width
  * and height. This is adaptive method and it is used to bind native GUI implementation with the
  * components of the library.
  * @param <code>w</code> the specified image width.
  * @param <code>h</code> the specified image height.
  * @return an off-screen drawable image.
  */
  Image createImage(int w, int h);

 /**
  * Gets the value for the specified property.
  * @param <code>id</code> the specified property id.
  * @return a value of the property.
  */
  Object getProperty (int id);

 /**
  * Sets the value for the specified property.
  * @param <code>id</code> the specified property id.
  * @param <code>value</code> the specified property value.
  */
  void setProperty (int id, Object value);
}


