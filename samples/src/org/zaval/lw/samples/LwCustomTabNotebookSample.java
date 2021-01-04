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
import org.zaval.lw.*;
import org.zaval.misc.*;

public class LwCustomTabNotebookSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();

    LwNotebook n1 = new LwNotebook();
    n1.addPage(new LwImgTabRender(getImgView("item0.gif")), new LwCanvas());
    n1.addPage(new LwImgTabRender(getImgView("item1.gif")), new LwCanvas());
    n1.addPage(new LwImgTabRender(getImgView("item2.gif")), new LwCanvas());
    n1.setSize(150, 150);
    n1.setLocation(20, 20);
    root.add (n1);

    LwNotebook n2 = new LwNotebook(Alignment.LEFT);
    n2.addPage(new LwImgTabRender(getImgView("item0.gif")), new LwCanvas());
    n2.addPage(new LwImgTabRender(getImgView("item1.gif")), new LwCanvas());
    n2.addPage(new LwImgTabRender(getImgView("item2.gif")), new LwCanvas());
    n2.setSize(150, 150);
    n2.setLocation(20 + n1.getX() + n1.getWidth(), 20);
    root.add (n2);

    return root;
  }

  public static void main(String[] args) {
    runSampleApp (400, 300, new LwCustomTabNotebookSample());
  }

  private static LwImgRender getImgView(String name) {
    return new LwImgRender("samples/img/" + name, LwView.ORIGINAL);
  }

  class LwImgTabRender
  extends LwTabRender
  {
    private LwImgRender icon;

    public LwImgTabRender(LwImgRender img)
    {
      super("");
      icon = img;
    }

    protected /*C#override*/ Dimension calcPreferredSize()
    {
      Dimension d  = super.calcPreferredSize();
      Dimension ps = icon.getPreferredSize();
      d.width += ps.width;
      d.height = Math.max(ps.width, d.height - 6) + 6;
      return d;
    }

    public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
    {
      Dimension ps = icon.getPreferredSize();
      super.paint (g, x, y, w , h, d);
      icon.paint (g, x + (w - ps.width)/2, y + (h - ps.height)/2, d);
    }
  }
}
