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
import org.zaval.lw.event.*;

public class LwSpinSample
implements LwActionListener
{
    public static void main (String[] args)
    {
      LwFrame     f    = new LwFrame();
      LwContainer root = f.getRoot();
      root.setLwLayout (new LwGridLayout(3, 2));

      LwSpin    spin1 = new LwSpin();
      Dimension ps    = spin1.getPreferredSize();
      spin1.setPSSize(2*ps.width, -1);
      spin1.addActionListener(new LwSpinSample());
      root.add (new LwLabel("Default spin [-10, 10]: "));
      root.add (spin1);

      LwSpin spin2 = new LwSpin();
      spin2.setBound(0, 5);
      spin2.enableLoop(true);
      ps = spin2.getPreferredSize();
      spin2.setPSSize(2*ps.width, -1);
      spin2.addActionListener(new LwSpinSample());
      root.add (new LwLabel("Looped spin [0, 5]: "));
      root.add (spin2);

      LwSpin spin3 = new LwSpin();
      spin3.setBound(0, 5);
      spin3.setStep (2);
      spin3.addActionListener(new LwSpinSample());
      ps = spin3.getPreferredSize();
      spin3.setPSSize(2*ps.width, -1);
      root.add(new LwLabel("Step 2 spin [0, 6]: "));
      root.add (spin3);

      f.setSize(300, 300);
      f.setVisible(true);
    }

    public void actionPerformed (LwActionEvent e) {
      Integer prevValue = (Integer)e.getData();
      System.out.println("Spin value has been changed from " + prevValue + " to " + ((LwSpin)e.getSource()).getValue());
    }
}





