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
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

public class LwTextFieldSample
extends LwSample
implements PosListener, TextListener
{
    public /*C#override*/ LwPanel createSamplePanel()
    {
      LwPanel root = new LwPanel ();

      LwTextField tf1 = new LwTextField("Ordinaly text field");
      Dimension ps = tf1.getPreferredSize();
      tf1.setLocation (20, 20);
      tf1.setSize(120, ps.height * 2);
      tf1.getPosController().addPosListener(new LwTextFieldSample());
      tf1.getTextRender().getTextModel().addTextListener(new LwTextFieldSample());
      root.add (tf1);

      LwTextField tf2 = new LwTextField("Not editable\ntext field component");
      tf2.setEditable(false);
      ps = tf2.getPreferredSize();
      tf2.setLocation (20, 20 + tf1.getY() + tf1.getHeight());
      tf2.setSize(120, ps.height * 2);
      tf2.getPosController().addPosListener(new LwTextFieldSample());
      tf2.getTextRender().getTextModel().addTextListener(new LwTextFieldSample());
      root.add (tf2);

      LwTextField tf3 = new LwTextField(new SingleLineTxt("Single line text field"));
      ps = tf3.getPreferredSize();
      tf3.setLocation (20, 20 + tf2.getY() + tf2.getHeight());
      tf3.setSize(150, ps.height);
      tf3.getPosController().addPosListener(new LwTextFieldSample());
      tf3.getTextRender().getTextModel().addTextListener(new LwTextFieldSample());
      root.add (tf3);

      LwTextField tf4 = new LwTextField(new SingleLineTxt("Fixed size (30) text field", 30));
      ps = tf4.getPreferredSize();
      tf4.setLocation (20, 20 + tf3.getY() + tf3.getHeight());
      tf4.setSize(150, ps.height);
      tf4.getPosController().addPosListener(new LwTextFieldSample());
      tf4.getTextRender().getTextModel().addTextListener(new LwTextFieldSample());
      root.add (tf4);

      LwTextField tf5 = new LwTextField();
      tf5.getViewMan(true).setView(new LwPasswordText (new SingleLineTxt("Password", 20)));
      ps = tf5.getPreferredSize();
      tf5.setLocation (20, 20 + tf4.getY() + tf4.getHeight());
      tf5.setSize(150, ps.height);
      tf5.getPosController().addPosListener(new LwTextFieldSample());
      tf5.getTextRender().getTextModel().addTextListener(new LwTextFieldSample());
      root.add (tf5);

      return root;
    }

    public static void main (String[] args)  {
      runSampleApp(300, 400, new LwTextFieldSample());
    }

    public void posChanged(PosEvent e) {
      PosController c = (PosController)e.getSource();
      System.out.println ("Cursor location has been changed to [line = " + c.getCurrentLine() + ", column = " + c.getCurrentCol() + "]");
    }

    public void textRemoved  (TextEvent e) {
      System.out.println ("TextRemoved : starting from " + e.getOffset() + ", length = " + e.getSize());
    }

    public void textInserted (TextEvent e) {
      System.out.println ("TextInserted : starting from " + e.getOffset() + ", length = " + e.getSize());
    }

    public void textUpdated (TextEvent e) {
      System.out.println ("TextUpdated : updated lines " + e.getUpdatedLines());
    }
}





