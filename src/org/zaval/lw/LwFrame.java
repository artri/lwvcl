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
import java.awt.event.*;
import org.zaval.lw.event.*;

/**
 * The class inherits java.awt.Frame components and it should be used instead the
 * java.awt.Frame to create a lightweight application. The class provides root light weight
 * component that has to be used as top-level container for all other lightweight
 * components. The root is laidout with java.awt.BorderLayout inside the frame using
 * "Center" constraint. The sample below illustrates the frame component usage:
 * <pre>
 *    ...
 *    LwFrame frame = new LwFrame();
 *    frame.setSize(400, 400);
 *    LwContainer root = frame.getRoot();
 *    root.add (new LwButton("Cancel"));
 *    root.add (new LwButton("Ok"));
 *    ...
 *    frame.setVisible(true);
 *    ...
 * </pre>
 */
public class LwFrame
extends Frame
implements FocusListener
{
  private LwDesktop root;

 /**
  * Constructs a new instance of the class.
  */
  public LwFrame()
  {
    LwToolkit.startVCL(System.getProperty("lwvcl.base"));
    setLayout (new BorderLayout());
    root = LwToolkit.createDesktop();
    add (BorderLayout.CENTER, (Component)root);
    addFocusListener(this);
  }

 /**
  * Gets the root light weight component that has to be used as a top-level container
  * for other light weight components.
  * @return a root light weight component.
  */
  public LwContainer getRoot () {
    return root.getRootLayer();
  }

 /**
  * Ensures that a component has a valid layout. This method is primarily intended to
  * operate on Container instances.
  */
  public void validate () {
    super.validate();
    root.validate();
  }

 /**
  * Invoked when a component gains the keyboard focus.
  * @param <code>e</code> the specified focus event.
  */
  public void focusGained(FocusEvent e) {
    ((Component)root).requestFocus();
  }

 /**
  * Invoked when a component loses the keyboard focus.
  * @param <code>e</code> the specified focus event.
  */
  public void focusLost(FocusEvent e) {}
}



