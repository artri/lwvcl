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
import java.util.*;
import org.zaval.data.*;
import org.zaval.misc.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;

public class LwSliderSample
extends LwSample
implements LwActionListener
{
    public /*C#override*/ LwPanel createSamplePanel()
    {
      LwPanel root = new LwPanel();

      LwSlider s1 = new LwSlider();
      s1.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s1.setLocation(20, 20);
      Dimension ps = s1.getPreferredSize();
      s1.setSize(ps.width, ps.height);
      s1.addActionListener(new LwSliderSample());
      root.add(s1);

      LwSlider s2 = new LwSlider();
      s2.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s2.useIntervalModel(true);
      s2.setLocation(20, s1.getY() + s1.getHeight() + 20);
      ps = s2.getPreferredSize();
      s2.setSize(ps.width, ps.height);
      s2.addActionListener(new LwSliderSample());
      root.add(s2);

      LwSlider s3 = new LwSlider();
      s3.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s3.showTitle(false);
      s3.setLocation(20, s2.getY() + s2.getHeight() + 20);
      ps = s3.getPreferredSize();
      s3.setSize(ps.width, ps.height);
      s3.addActionListener(new LwSliderSample());
      root.add(s3);

      LwSlider s4 = new LwSlider();
      s4.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s4.showScale(false);
      s4.setLocation(20, s3.getY() + s3.getHeight() + 20);
      ps = s4.getPreferredSize();
      s4.setSize(ps.width, ps.height);
      s4.addActionListener(new LwSliderSample());
      root.add(s4);

      LwSlider s5 = new LwSlider(LwToolkit.VERTICAL);
      s5.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s5.setLocation(20 + s1.getX() + s1.getWidth(), 20);
      ps = s5.getPreferredSize();
      s5.setSize(ps.width, ps.height);
      s5.addActionListener(new LwSliderSample());
      root.add(s5);

      LwSlider s6 = new LwSlider(LwToolkit.VERTICAL);
      s6.setScaleGap(15);
      s6.setValues(0, 100, new int[] { 0, 20, 20, 20, 20 }, 5, 5 );
      s6.setLocation(20 + s5.getX() + s5.getWidth(), 20);
      ps = s6.getPreferredSize();
      s6.setSize(ps.width, ps.height);
      s6.addActionListener(new LwSliderSample());
      root.add(s6);

      return root;
    }

    public static void main (String[] args) {
      runSampleApp(400, 300, new LwSliderSample());
    }

    public void actionPerformed (LwActionEvent e) {
      Integer i = (Integer)e.getData();
      System.out.println ("Src = " + e.getSource() + "," + i);
    }
}





