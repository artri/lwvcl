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
import java.io.*;
import java.util.*;
import org.zaval.lw.*;
import org.zaval.data.*;

public class LwCustomTextRenderSample
extends LwSample
{
    public /*C#override*/ LwPanel createSamplePanel()
    {
      LwPanel root = new LwPanel();
      root.setLwLayout(new LwBorderLayout());

      LwTextField tf1 = new LwTextField();
      tf1.getViewMan(true).setView(new LwCustomTextRender(readAsResource("/org/zaval/lw/rs/samples/img/ExpireObjects.java")));
      Dimension ps = tf1.getPreferredSize();
      tf1.setLocation (20, 20);
      root.add (LwBorderLayout.CENTER, new LwScrollPan(tf1));

      return root;
    }

    public static void main (String[] args)  {
      runSampleApp (300, 300, new LwCustomTextRenderSample());
    }

    class LwCustomTextRender
    extends LwAdvTextRender
    {
      private Hashtable words;

      public LwCustomTextRender(String s)
      {
        super(new Text(s));
        setForeground (Color.darkGray);
        words = new Hashtable();
        words.put ("package", Color.blue);
        words.put ("public", Color.gray);
        words.put ("private", Color.gray);
        words.put ("static", Color.white);
        words.put ("class" , Color.white);
        words.put ("this" , Color.white);
        words.put ("for" , Color.white);
        words.put ("if" , Color.white);
        words.put ("return" , Color.white);
        words.put ("while" , Color.white);
        words.put ("try" , Color.white);
        words.put ("catch" , Color.white);

        words.put ("import" , new Color(150, 0, 0));

        Color c = new Color(0, 180, 0);
        words.put ("Vector",  c);
        words.put ("Runnable",  c);
        words.put ("InterruptedException", c);
        words.put ("Hashtable", c);
        words.put ("Thread",    c);
        words.put ("String",    c);
      }

      protected /*C#override*/ void paintLine(Graphics g, int x, int y, int line, Drawable d)
      {
        String s = getLine(line);
        Vector v = parse(s);
        int xx = x;
        for (int i=0; i<v.size(); i++)
        {
           String str   = (String)v.elementAt(i);
           Color  color = (Color)words.get(str);
           if (color != null) g.setColor(color);
           else               g.setColor(getForeground());
           g.drawString(str, xx, y + getAscent());
           xx += stringWidth(str);
        }
        paintSelection(g, x, y, line, d);
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





