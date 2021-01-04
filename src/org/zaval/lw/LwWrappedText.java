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
package org.zaval.lw;

import java.util.*;
import java.awt.*;
import org.zaval.data.*;

/**
 * This is wrapped text render. The class inherits the LwTextRender functionality and allows
 * to render text according the owner component size. It means if a text line size is greater
 * than drawable area size than the render will try to break the line to several lines that can
 * be placed completely. The render can be used as follow:
 * <pre>
 *   ...
 *   LwWrappedText text  = new LwWrappedText("This is demo text that can be wrapped ...");
 *   LwLabel       label = new LwLabel (text);
 *   ...
 * </pre>
 */
public class LwWrappedText
extends LwTextRender
{
   private Vector brokenLines;
   private int lastWidth = -1;

  /**
   * Constructs the render with the specified target string.
   * @param <code>t</code> the specified target string.
   */
   public LwWrappedText(String t) {
     super(t);
   }

  /**
   * Constructs the render with the specified target text model.
   * @param <code>t</code> the specified target text model.
   */
   public LwWrappedText(TextModel t) {
     super(t);
   }

   public /*C#override*/ void invalidate () {
     super.invalidate();
     brokenLines = null;
   }

   public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
   {
     int ww = w;
     if (getType() == ORIGINAL)
     {
       Insets insets = d.getInsets();
       ww = d.getWidth() - insets.left - insets.right;
     }

     if (ww <= 0) return;

     if (brokenLines == null || ww != lastWidth)
     {
       TextModel text = getTextModel();
       brokenLines = new Vector ();
       for (int i=0; i<text.getSize(); i++)
       {
         String line = getLine(i);
         int lineSize = stringWidth(line);
         if (lineSize > ww)
         {
           Vector v = new Vector();
           broke(line, lineSize, ww, v);
           String[] strs = new String[v.size()];
           for (int j=0; j<strs.length; j++) strs[j] = (String)v.elementAt(j);
           brokenLines.addElement(strs);
         }
         else brokenLines.addElement(new String[] {line});
       }
       lastWidth = ww;
     }

     int lineIndent = getLineIndent();
     int lineHeight = getLineHeight();

     g.setFont (getFont());
     g.setColor(getForeground());
     for (int i=0; i<brokenLines.size(); i++)
     {
       String[] strs = (String[])brokenLines.elementAt(i);
       for (int j=0; j<strs.length; j++)
       {
         g.drawString(strs[j], x, y + getAscent());
         y += (lineIndent + lineHeight);
       }
     }
   }

  /**
   * Checks if the specified string with the specified width can be placed in an area
   * with the given width and if it is necessary breaks the line to several. The result
   * is put to the specified vector.
   * @param <code>s</code> the specified string.
   * @param <code>sw</code> the specified string width.
   * @param <code>w</code> the specified area width.
   * @param <code>v</code> the splitting result.
   */
   protected void broke(String s, int sw, int w, Vector v)
   {
     if (sw > w)
     {
       int chIndex = (s.length()* w) / sw;
       int cl = substrWidth (s, 0, chIndex);
       int d  = cl > w?-1:1;
       for (;chIndex>0 && chIndex<s.length(); chIndex+=d, cl = stringWidth (s.substring(0, chIndex)))
         if ((d > 0 && cl > w) || (d < 0 && cl < w)) break;
       chIndex -= (d>0)?d:0;
       v.addElement(s.substring(0, chIndex));
       String ps = s;
       s = s.substring(chIndex);
       broke(s, substrWidth(ps, chIndex, ps.length() - chIndex), w, v);
     }
     else v.addElement(s);
   }
}

