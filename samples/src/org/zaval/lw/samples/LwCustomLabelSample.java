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
import org.zaval.data.*;

public class LwCustomLabelSample
extends LwSample
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();

    LwLabel lab = new LwLabel (new LwCustomTextRender("public class Test {\n  public Test(String data) {\n  }\n}"));
    Dimension ps = lab.getPreferredSize();
    lab.setSize(ps.width + 20, ps.height + 20);
    lab.setLocation (20, 20);
    lab.getViewMan(true).setBorder("br.plain");
    root.add (lab);

    return root;
  }

  public static void main (String[] args)  {
    runSampleApp (400, 300, new LwCustomLabelSample());
  }

  class LwCustomTextRender
  extends LwTextRender
  {
    private Hashtable words;

    public LwCustomTextRender(String s)
    {
      super(s);
      setForeground (Color.darkGray);
      words = new Hashtable();
      words.put ("public", Color.gray);
      words.put ("class" , Color.white);
      words.put ("String", Color.magenta);
    }

    protected /*C#override*/ void paintLine(Graphics g, int x, int y, int line, Drawable d)
    {
      String s = getLine(line);
      Vector v = parse(s);
      for (int i=0; i<v.size(); i++)
      {
         String str   = (String)v.elementAt(i);
         Color  color = (Color)words.get(str);
         if (color != null) g.setColor(color);
         else               g.setColor(getForeground());
         g.drawString(str, x, y + getAscent());
         x += stringWidth(str);
      }
    }

    private Vector parse(String s)
    {
      StringBuffer sb = new StringBuffer(s);
      Vector       v  = new Vector();
      int          c  = -2;
      boolean      isLetter = false;

      for (int i=0; i<sb.length(); i++)
      {
        boolean b = Character.isLetter(sb.charAt(i));
        if (c == -2)
        {
          isLetter = b;
          c = 0;
        }

        if (isLetter != b)
        {
          if (c >= 0)
          {
            char[] buf = new char[i - c];
            sb.getChars(c, i, buf, 0);
            v.addElement(new String(buf));
            c = i;
            isLetter = b;
          }
        }
      }
      if (c >= 0)
      {
        char[] buf = new char[sb.length() - c];
        sb.getChars(c, sb.length(), buf, 0);
        v.addElement(new String(buf));
      }
      return v;
    }
  }
}



