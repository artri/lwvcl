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
import java.applet.*;
import org.zaval.util.*;

/**
 * This class should be used as a base for light weight applets creation. Usage of the class
 * is the same LwFrame usage for light weight applications and the sample is shown
 * below:
 * <pre>
 *    ...
 *    public class MyApplet
 *    extends LwApplet
 *    {
 *      public void init ()
 *      {
 *        super.init(); // Don't forget to call the parent method !
 *        // Getting root container that should be used as a top light weight
 *        // container where all other components "live".
 *        LwContainer root = getRoot();
 *        // Creating light weight components hierarchy
 *        root.setLwLayout(new LwFlowLayout());
 *        root.add (new LwButton("Test"));
 *        ...
 *      }
 *    }
 * </pre>
 */
public class LwApplet
extends Applet
{
  private LwContainer root;

 /**
  * Constructs the light weigt applet.
  */
  public LwApplet()
  {
    setForeground(Color.black);
    setBackground(Color.white);
    enableEvents(AWTEvent.KEY_EVENT_MASK);
  }

 /**
  * Gets the root light weight component that has to be used as a top-level container
  * for other light weight components.
  * @return a root light weight component.
  */
  public LwContainer getRoot() {
    return ((LwDesktop)root).getRootLayer();
  }

  public void init ()
  {
    super.init();
    LwToolkit.startVCL(getParameter("lwvcl.base"));
    setLayout(new BorderLayout());
    root = LwToolkit.createDesktop();
    add ("Center", (Component)root);
  }

  public void start () {
    super.start();
    applets++;
  }

  public void stop () {
    super.stop();
    applets--;
  }

  public void destroy ()
  {
    //!!!
    System.out.println ("Applet destroy " + applets);

    super.destroy();
    if (applets == 0)
    {
      Timer.getTimer(false).interrupt();
      LwToolkit.stopVCL();
    }
  }

  private static short applets = 0;
}

