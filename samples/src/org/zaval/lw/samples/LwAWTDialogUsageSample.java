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
import java.awt.event.*;
import org.zaval.misc.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;

public class LwAWTDialogUsageSample
extends LwSample
{
    public static Dialog dialog;
    public static Frame  stubFrame = new Frame();

    public /*C#override*/ LwPanel createSamplePanel()
    {
      LwPanel root = new LwPanel();
      root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 8, 8));
      LwButton  b1 = new LwButton("Open AWT Modal Dialog");
      b1.addActionListener(new LwAWTDialogActionHandler());
      LwButton  b2 = new LwButton("Open AWT None-Modal Dialog");
      b2.addActionListener(new LwAWTDialogActionHandler());
      root.add(b1);
      root.add(b2);
      return root;
    }

    public /*C#override*/ void stopped()
    {
      if (dialog != null)
      {
        dialog.dispose();
        dialog = null;
      }
    }


    public static void main (String[] args) {
      runSampleApp (300, 300, new LwAWTDialogUsageSample());
    }
}

class LwAWTDialogActionHandler
implements LwActionListener
{
  //LwAWTDialogActionHandler

  public void actionPerformed(LwActionEvent e)
  {
    LwLabel lab = (LwLabel)((LwButton)e.getSource()).getFocusComponent();
    if (LwAWTDialogUsageSample.dialog != null)
    {
      LwAWTDialogUsageSample.dialog.dispose();
      LwAWTDialogUsageSample.dialog = null;
    }

    LwAWTDialogUsageSample.dialog = new Dialog(LwAWTDialogUsageSample.stubFrame, lab.getText().indexOf ("AWT Modal")>0);
    LwAWTDialogUsageSample.dialog.addWindowListener(new WindowListener()
    {
       public void windowActivated(WindowEvent e) {  }
       public void windowClosed(WindowEvent e) {  }
       public void windowClosing(WindowEvent e) { LwAWTDialogUsageSample.dialog.dispose(); LwAWTDialogUsageSample.dialog = null; }
       public void windowDeactivated(WindowEvent e) { }
       public void windowDeiconified(WindowEvent e) {}
       public void windowIconified(WindowEvent e) {}
       public void windowOpened(WindowEvent e) {}
    });

    LwAWTDialogUsageSample.dialog.setLayout (new BorderLayout());
    LwRoot proxy = new LwRoot();
    LwAWTDialogUsageSample.dialog.add ("Center", proxy);
    LwContainer root = proxy.getRootLayer();
    root.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 8, 8));
    root.add (new LwTextField ("Input Field ", 15));
    LwButton okButton = new LwButton("  Ok  ");
    okButton.addActionListener(new LwActionListener() {
       public void actionPerformed (LwActionEvent e) {
         LwAWTDialogUsageSample.dialog.dispose();
         LwAWTDialogUsageSample.dialog = null;
       }
    });
    root.add (okButton);
    LwAWTDialogUsageSample.dialog.setSize(200, 200);
    LwAWTDialogUsageSample.dialog.setVisible (true);
  }
}



