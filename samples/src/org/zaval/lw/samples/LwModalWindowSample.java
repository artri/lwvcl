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
import org.zaval.misc.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;

public class LwModalWindowSample
extends LwSample
implements LwActionListener
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel ();
    root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
    LwButton button = new LwButton("Open Modal Window !");
    button.addActionListener(new LwModalWindowSampleAL(root));
    root.add (button);
    return root;
  }

  public /*C#override*/ void started()
  {
    LwContainer root = getRoot();
    Point absLoc = LwToolkit.getAbsLocation(root);
    lastX = absLoc.x + 20;
    lastY = absLoc.y + 20;
  }

  public /*C#override*/ void stopped() {
    LwToolkit.getDesktop(getRoot()).getLayer(LwWinLayer.ID).removeAll();
  }

  private static int lastX, lastY;

  protected static void makeModalWin (LwContainer top)
  {
    LwWindow win = new LwWindow("Modal Window");
    LwContainer root = win.getRoot();
    root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 8, 8));
    ((LwPanel)root).setInsets(8, 8, 8, 8);

    LwActionListener listener = new LwModalWindowSample();
    LwButton mdiButton = new LwButton("Open MDI Window");
    mdiButton.addActionListener(listener);
    root.add(mdiButton);
    LwButton modalButton = new LwButton("Open Modal Window");
    modalButton.addActionListener(listener);
    root.add(modalButton);
    LwDesktop  desktop  = LwToolkit.getDesktop(top);
    LwWinLayer winLayer = (LwWinLayer)desktop.getLayer(LwWinLayer.ID);

    win.setLocation(lastX, lastY);
    winLayer.add (win, null, LwWinLayer.MODAL_WIN);
    Dimension ps = win.getPreferredSize();
    win.setSize(ps.width, ps.height);
  }

  public void actionPerformed(LwActionEvent e)
  {
    LwButton   src      = (LwButton)e.getSource();
    String     lab      = ((LwLabel)src.getFocusComponent()).getText();
    LwDesktop  desktop  = LwToolkit.getDesktop(src);
    LwWinLayer winLayer = (LwWinLayer)desktop.getLayer(LwWinLayer.ID);

    if (lab.indexOf("Modal")>=0)
    {
      makeModalWin (src);
    }
    else
    if (lab.indexOf("MDI")>=0)
    {
      LwWindow  win = new LwWindow("MDI Window");
      win.setSize(100, 100);
      win.setLocation(lastX, lastY);
      winLayer.add (win, null, LwWinLayer.MDI_WIN);
    }

    lastX += 20;
    lastY += 20;
  }

  class LwModalWindowSampleAL
  implements LwActionListener
  {
     private LwPanel root;

     public LwModalWindowSampleAL(LwPanel r) {
       root = r;
     }

     public void actionPerformed (LwActionEvent e)   {
       makeModalWin(root);
     }
  }

  public static void main (String[] args) {
    runSampleApp (300, 300, new LwModalWindowSample());
  }
}



