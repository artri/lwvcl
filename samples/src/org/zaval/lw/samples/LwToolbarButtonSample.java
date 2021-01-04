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

/**
 * The sample illustrates the LwStButon component usage to create toolbar.
 */
public class LwToolbarButtonSample
extends LwSample
{
   public /*C#override*/ LwPanel createSamplePanel()
   {
     LwPanel root = new LwPanel();

     LwPanel toolbar1 = new LwPanel();
     toolbar1.setLwLayout(new LwFlowLayout());
     toolbar1.getViewMan(true).setBorder("br.sunken");

     LwStButton st1 = new LwStButton();
     LwAdvViewMan man1 = new LwAdvViewMan();
     man1.put ("st.out"   , getImageView("st11.gif"));
     man1.put ("st.pressed"    , getImageView("st12.gif"));
     man1.put ("st.over", getImageView("st13.gif"));
     st1.setViewMan(man1);
     toolbar1.add (st1);

     LwStButton st2 = new LwStButton();
     LwAdvViewMan man2 = new LwAdvViewMan();
     man2.put ("st.out"   , getImageView("st21.gif"));
     man2.put ("st.pressed"    , getImageView("st22.gif"));
     man2.put ("st.over", getImageView("st23.gif"));
     st2.setViewMan(man2);
     toolbar1.add (st2);

     LwStButton st3 = new LwStButton();
     LwAdvViewMan man3 = new LwAdvViewMan();
     man3.put ("st.out"   , getImageView("st31.gif"));
     man3.put ("st.pressed"    , getImageView("st32.gif"));
     man3.put ("st.over", getImageView("st33.gif"));
     st3.setViewMan(man3);
     toolbar1.add (st3);

     LwStButton st4 = new LwStButton();
     LwAdvViewMan man4 = new LwAdvViewMan();
     man4.put ("st.out"   , getImageView("st41.gif"));
     man4.put ("st.pressed"    , getImageView("st42.gif"));
     man4.put ("st.over", getImageView("st43.gif"));
     st4.setViewMan(man4);
     toolbar1.add (st4);

     LwStButton st5 = new LwStButton();
     LwAdvViewMan man5 = new LwAdvViewMan();
     man5.put ("st.out"   , getImageView("st51.gif"));
     man5.put ("st.pressed"    , getImageView("st52.gif"));
     man5.put ("st.over", getImageView("st53.gif"));
     st5.setViewMan(man5);
     toolbar1.add (st5);

     LwStButton st6 = new LwStButton();
     LwAdvViewMan man6 = new LwAdvViewMan();
     man6.put ("st.out"   , getImageView("st61.gif"));
     man6.put ("st.pressed"    , getImageView("st62.gif"));
     man6.put ("st.over", getImageView("st63.gif"));
     st6.setViewMan(man6);
     toolbar1.add (st6);

     Dimension ps = toolbar1.getPreferredSize();
     toolbar1.setSize(ps.width, ps.height);
     toolbar1.setLocation (30, 30);

     root.add(toolbar1);

     return root;
   }

   public static void main (String[] args)  {
     runSampleApp(300, 300, new LwToolbarButtonSample());
   }

   private static LwImgRender getImageView (String name) {
     return new LwImgRender("samples/img/" + name, LwView.ORIGINAL);
   }
}
