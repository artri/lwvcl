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
import org.zaval.lw.*;
import org.zaval.data.*;
import org.zaval.misc.*;
import org.zaval.lw.event.*;

public class LwWindowSample
extends LwSample
implements LwWinListener
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root =  new LwPanel ();
    root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 8, 8));
    return root;
  }

  public /*C#override*/ void started()
  {
    LwPanel root = getRoot();

    if (root.count() == 0)
    {
      Point absLoc = LwToolkit.getAbsLocation(root);

      LwDesktop  desktop  = LwToolkit.getDesktop(root);
      LwWinLayer winLayer = (LwWinLayer)desktop.getLayer(LwWinLayer.ID);
      LwWindow   win1     = new LwWindow();
      win1.setSize(100, 100);
      win1.getRoot ().setLwLayout(new LwBorderLayout());
      win1.getRoot ().add(LwBorderLayout.CENTER, new LwLabel ("Label"));
      win1.setLocation(absLoc.x + 20, absLoc.y + 20);
      winLayer.add (win1, new LwWindowSample(), LwWinLayer.MDI_WIN);

      LwLabel win2 = new LwLabel(new Text("This is label\ncomponent that\nis shown as\nwindow"));
      win2.setLocation(absLoc.x + 80, absLoc.y + 80);
      win2.getViewMan(true).setBorder("br.plain");
      Dimension ps = win2.getPreferredSize();
      win2.setSize(ps.width, ps.height);
      winLayer.add (win2, new LwWindowSample(), LwWinLayer.MDI_WIN);

      LwPanel win3 = new LwPanel();
      win3.setLwLayout(new LwBorderLayout());
      win3.add (LwBorderLayout.NORTH, new LwLabel("List as Window"));
      LwList list = new LwList();
      list.getViewMan(true).setBorder("br.sunken");
      for (int i=0;i<5;i++) list.add ("List Item " + i);
      win3.add (LwBorderLayout.CENTER, list);
      win3.setLocation(absLoc.x + 130, absLoc.y + 130);
      win3.getViewMan(true).setBorder("br.etched");
      ps = win3.getPreferredSize();
      win3.setSize(ps.width, ps.height);
      winLayer.add (win3, new LwWindowSample(), LwWinLayer.MDI_WIN);
    }
  }

  public /*C#override*/ void stopped() {
    LwToolkit.getDesktop(getRoot()).getLayer(LwWinLayer.ID).removeAll();
  }


  public static void main (String[] args) {
    runSampleApp(300, 300, new LwWindowSample());
  }

  public void winOpened(LwWinEvent e) {
    System.out.println("The window " + e.getSource() + " is opened");
  }

  public void winClosed(LwWinEvent e) {
    System.out.println("The window " + e.getSource() + " is closed");
  }

  public void winActivated(LwWinEvent e) {
    System.out.println("The window " + e.getSource() + " is activated");
  }

  public void winDeactivated(LwWinEvent e) {
    System.out.println("The window " + e.getSource() + " is deactivated");
  }

}



