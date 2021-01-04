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

public class LwCustomViewSpinSample
{
    public static void main (String[] args)
    {
      LwFrame     f    = new LwFrame();
      LwContainer root = f.getRoot();

      LwSpin spin1 = new LwSpin();
      spin1.getViewMan(true).setBorder("br.plain");
      spin1.setBound (0, 25);
      LwStButton stInc1 = (LwStButton)spin1.get(0);
      LwStButton stDec1 = (LwStButton)spin1.get(2);
      LwTextField tf    = (LwTextField)spin1.get(1);
      tf.getTextRender().setFont (LwToolkit.BFONT);

      tf.getViewMan(true).setBorder((LwView)null);
      tf.setEditable(false);

      LwAdvViewMan man1 = new LwAdvViewMan();
      man1.put ("st.out", new LwImgRender("samples/img/spinc2.gif", LwView.ORIGINAL));
      man1.put ("st.over", new LwImgRender("samples/img/spinc3.gif", LwView.ORIGINAL));
      man1.put ("st.pressed", new LwImgRender("samples/img/spinc1.gif", LwView.ORIGINAL));
      stInc1.setViewMan(man1);

      LwAdvViewMan man2 = new LwAdvViewMan();
      man2.put ("st.out", new LwImgRender("samples/img/spdec2.gif", LwView.ORIGINAL));
      man2.put ("st.over", new LwImgRender("samples/img/spdec3.gif", LwView.ORIGINAL));
      man2.put ("st.pressed", new LwImgRender("samples/img/spdec1.gif", LwView.ORIGINAL));
      stDec1.setViewMan(man2);

      spin1.setLwLayout(new LwFlowLayout());

      Dimension ps = spin1.getPreferredSize();
      spin1.setSize(ps.width, ps.height);
      spin1.setLocation(20, 20);

      root.add (spin1);

      f.setSize(300, 300);
      f.setVisible(true);
    }
}





