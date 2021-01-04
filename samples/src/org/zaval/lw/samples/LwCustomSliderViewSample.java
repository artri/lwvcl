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

public class LwCustomSliderViewSample
extends LwSample
{
    public /*C#override*/ LwPanel createSamplePanel()
    {
      LwPanel root = new LwPanel();

      LwSlider s1 = new LwSlider();
      s1.setLocation(20, 20);
      s1.setValues(0, 100, new int[] {10, 40, 40}, 2, 4 );
      s1.setViewProvider(new LwTitleViewProvider());
      Dimension ps = s1.getPreferredSize();
      s1.setSize(ps.width, ps.height);
      root.add(s1);

      LwSlider s2 = new LwSlider();
      s2.setValues(0, 100, new int[] {20, 20, 30}, 2, 4 );
      s2.setView(LwSlider.BUNDLE_VIEW, new LwImgRender("samples/img/bundle.gif", LwView.ORIGINAL));
      s2.setView(LwSlider.GAUGE_VIEW, new LwImgRender("samples/img/gauge.gif", LwView.ORIGINAL));
      s2.setViewProvider(new LwTitleViewProvider());
      s2.showTitle (false);
      s2.showScale (false);
      ps = s2.getPreferredSize();
      s2.setSize(ps.width, ps.height);
      s2.setLocation(20, 30 + s1.getY() + s1.getHeight());
      root.add(s2);

      return root;
    }

    public static void main (String[] args)   {
      runSampleApp(300, 300, new LwCustomSliderViewSample());
    }

    class LwTitleViewProvider
    implements LwViewProvider
    {
      public LwView getView (Drawable d, Object o)
      {
        Integer value = (Integer)o;

        LwPanel panel = new LwPanel();
        LwFlowLayout layout = new LwFlowLayout();
        panel.setLwLayout (layout);

        String imgName = null;
        if (value.intValue() == 10) imgName = "sl1.gif";
        else
        if (value.intValue() == 50) imgName = "sl2.gif";
        else
        if (value.intValue() == 90) imgName = "sl3.gif";

        panel.add (new LwImage("samples/img/" + imgName));
        panel.add (new LwLabel("" + value));
        return new LwCompRender(panel);
      }
    }
}



