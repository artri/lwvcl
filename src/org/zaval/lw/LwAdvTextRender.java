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

import java.awt.*;
import org.zaval.data.*;

/**
 * The class extends LwTextRender to support text block painting. The render checks if the
 * owner drawable object implements TxtSelectionInfo interface and use the interface to
 * render text block, otherwise the render functionality is the same to LwTextRender.
 */
public class LwAdvTextRender
extends LwTextRender
{
 /**
  * Constructs the render with the specified target text model.
  * @param <code>text</code> the specified target text model.
  */
  public LwAdvTextRender(TextModel text) {
    super(text);
  }

  protected /*C#override*/ void paintLine(Graphics g, int x, int y, int line, Drawable d) {
    super.paintLine(g, x, y, line, d);
    paintSelection(g, x, y, line, d);
  }

 /**
  * Invoked by <code>paintLine</code> method to render selection block.
  * @param <code>g</code> the graphics context.
  * @param <code>x</code> the x coordinate of the text line location.
  * @param <code>y</code> the y coordinate of the text line location.
  * @param <code>line</code> the specified line index.
  * @param <code>d</code> the specified owner component that uses the render.
  */
  protected /*C#virtual*/ void paintSelection(Graphics g, int x, int y, int line, Drawable d)
  {
    if (d instanceof TxtSelectionInfo)
    {
      TxtSelectionInfo selection = (TxtSelectionInfo)d;
      Point p1 = selection.getStartSelection();
      Point p2 = selection.getEndSelection();

      if (!p1.equals(p2) && line >= p1.x && line <= p2.x)
      {
        String s = getLine(line);
        int w = lineWidth(line);

        if (line == p1.x)
        {
          int ww = substrWidth(s, 0, p1.y);
          x += ww;
          w -= ww;
          if (p1.x == p2.x) w -= substrWidth(s, p2.y, s.length() - p2.y);
        }
        else
        if (line == p2.x)
          w = substrWidth(s, 0, p2.y);

        int indent = getLineIndent();
        LwToolkit.drawMarker(g, x, y - indent, w, getLineHeight() + indent, d.getBackground(), selection.getSelectColor());
      }
    }
  }
}




