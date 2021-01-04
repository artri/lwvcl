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
import org.zaval.util.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

/**
 * This is text render.
 * The render is used to paint a text that is represented with org.zaval.data.TextModel
 * target object. The data class has not any connections with painting or light weight functionality,
 * it just provides handly interface to manipulate text data. The render defines set of properties that
 * are bound with the view:
 * <ul>
 *   <li>Font to show the text</li>
 *   <li>Foreground color to define the text color</li>
 *   <li>Text line indent</li>
 * </ul>
 * <p>
 * The render listens when the text has been modified to conrol it validation state, so it is not
 * necessary to synchronize the render validation state with the modifying the text data.
 * <p>
 * The render overrides <code>ownerChanged</code> method to have reference to a light weight
 * component that uses the render. The reference is used to synchronize validation status of
 * the light weight component with the render. It means if the render is invalidated than
 * the component (target) will be invalidated with the render too.
 */
public class LwTextRender
extends LwRender
implements TextListener
{
  private FontMetrics fontMetrics;
  private int         textWidth, textHeight;
  private Color       fore;
  private int         startLine, lines;
  private Validationable owner;

 /**
  * Constructs the render with the specified target string.
  * The constructor creates org.zaval.data.Text basing on the target string.
  * @param <code>text</code> the specified target string.
  */
  public LwTextRender(String text) {
    this(new Text(text));
  }

 /**
  * Constructs the render with the specified target text model.
  * @param <code>text</code> the specified target text model.
  */
  public LwTextRender(TextModel text) {
    super(text);
  }

 /**
  * Gets line height.
  * @return a line height.
  */
  public int getLineHeight() {
    return getFontMetrics().getHeight();
  }

 /**
  * Sets the font for the text render. The font is used to render the text.
  * @param <code>f</code> the font.
  */
  public void setFont(Font f)
  {
    Font old = getFont();
    if (f != old && (f == null || !f.equals(old)))
    {
      fontMetrics = LwToolkit.getFontMetrics(f);
      invalidate(0, getTextModel().getSize());
    }
  }

 /**
  * Gets the font of the text render.
  * @return a font.
  */
  public Font getFont() {
    return fontMetrics == null?LwToolkit.FONT:fontMetrics.getFont();
  }

 /**
  * Gets the font metrics of the text render.
  * @return a font metrics.
  */
  protected FontMetrics getFontMetrics() {
    return fontMetrics == null?LwToolkit.FONT_METRICS:fontMetrics;
  }

 /**
  * Gets the foreground color. The color is used as the text color.
  * @return a foreground color.
  */
  public Color getForeground() {
    return fore == null?LwToolkit.FG_COLOR:fore;
  }

 /**
  * Sets the foreground color that is used as the text color to render the target text.
  * @param <code>c</code> the foreground color. Use <code>null</code> to set the default
  * foreground color.
  * default value.
  */
  public void setForeground(Color c) {
    if (c != fore && (c == null || !c.equals(fore)))
      fore = c;
  }

 /**
  * Gets the line indent.
  * @return a line indent.
  */
  public /*C#virtual*/ int getLineIndent() {
    return 1;
  }

 /**
  * Invoked whenever the target object has been changed.
  * The method is overrided to register and unregister the render as a text events listener
  * for the target object.
  * @param <code>o</code> the old target object.
  * @param <code>n</code> the new target object.
  */
  protected /*C#override*/ void targetWasChanged(Object o, Object n)
  {
    if (o != null) ((TextModel)o).removeTextListener(this);
    if (n != null)
    {
      ((TextModel)n).addTextListener(this);
      invalidate(0, getTextModel().getSize());
    }
  }

 /**
  * Calculates and returns the render preferred size. The method doesn't use
  * the view insets to compute the preferred size.
  * @return a "pure" preferred size of the view.
  */
  protected /*C#override*/ Dimension calcPreferredSize() {
    return new Dimension(textWidth, textHeight);
  }

 /**
  * Gets the target object as the text model.
  * @return a text model.
  */
  public TextModel getTextModel() {
    return (TextModel)getTarget();
  }

 /**
  * Gets the target text as a string.
  * @return a string presentation of the target text.
  */
  public String getText() {
    TextModel text = getTextModel();
    return text == null?null:text.getText();
  }

 /**
  * Invalidates the render. If the render has been registered as a view of a lightweight
  * component than the owner component will be invalidated too.
  */
  public /*C#override*/ void invalidate() {
    if (owner != null) owner.invalidate();
    super.invalidate();
  }

 /**
  * Invoked with <code>validate</code> method if the render is invalid. The method
  * is overrided to calculate size of the target text according to the font and indent.
  */
  protected /*C#override*/ void recalc ()
  {
    TextModel text = getTextModel();
    if (text != null)
    {
      if (lines > 0)
      {
        for (int i=startLine; i<startLine + lines; i++)
          text.setExtraChar(i, stringWidth(text.getLine(i)));
        lines = 0;
      }

      int lineHeight = getLineHeight();
      int max = 0, size = text.getSize();
      for (int i=0; i<size; i++)
      {
        int len = text.getExtraChar(i);
        if (len > max) max = len;
      }
      textWidth  = max;
      textHeight = lineHeight * size + (size - 1) * getLineIndent();
    }
  }

 /**
  * Invoked when a part of the target text has been removed.
  * @param <code>e</code> the text event.
  */
  public void textRemoved(TextEvent e) {
    if (e.getUpdatedLines() == 0) lines = 0;
    invalidate(e.getFirstUpdatedLine(), e.getUpdatedLines());
  }

 /**
  * Invoked when a part of the target text has been updated.
  * @param <code>e</code> the text event.
  */
  public void textUpdated(TextEvent e) {
    invalidate(e.getFirstUpdatedLine(), e.getUpdatedLines());
  }

 /**
  * Invoked when a new text has been inserted in the target text.
  * @param <code>e</code> the text event.
  */
  public void textInserted (TextEvent e)
  {
    int ln = e.getUpdatedLines(), first = e.getFirstUpdatedLine();
    if (ln > 1 && lines > 0)
    {
      if (first <= startLine) startLine += (ln - 1);
      else
      if (first < startLine + lines) lines += (ln - 1);
    }
    invalidate(first, ln);
  }

  protected void invalidate(int start, int size)
  {
    if ((startLine != start || size != lines) && size > 0)
    {
      if (lines == 0)
      {
        startLine = start;
        lines     = size;
      }
      else
      {
        int e1 = start     + size;
        int e2 = startLine + lines;
        startLine = Math.min(start, startLine);
        lines     = Math.max(e1, e2) - startLine;
      }
    }
    invalidate();
  }


 /**
  * Invoked to render a line with the specified index, at the given location, graphics context and
  * owner component. The method can be overrided to provide another line painting algorithm.
  * @param <code>g</code> the graphics context.
  * @param <code>x</code> the x coordinate of the text line location.
  * @param <code>y</code> the y coordinate of the text line location.
  * @param <code>line</code> the specified line index.
  * @param <code>d</code> the specified owner component that uses the render.
  */
  protected /*C#virtual*/ void paintLine(Graphics g, int x, int y, int line, Drawable d) {
    g.drawString(getLine(line), x, y + getAscent());
  }

 /**
  * Renders the text model using the specified graphics context, location, size and owner
  * component.
  * @param <code>g</code> the graphics context.
  * @param <code>x</code> the x coordinate of the text location.
  * @param <code>y</code> the y coordinate of the text location.
  * @param <code>w</code> the specified width.
  * @param <code>h</code> the specified height.
  * @param <code>d</code> the specified owner component that uses the render.
  */
  public /*C#override*/ void paint(Graphics g, int x, int y, int w, int h, Drawable d)
  {
     TextModel text   = getTextModel();
     boolean b        = d.isEnabled();
     int   lineIndent = getLineIndent();
     int   lineHeight = getLineHeight();
     int   size       = text.getSize();

     Rectangle clip = g.getClipBounds();
     if (clip == null) return;

     w = Math.min(clip.width, w);
     h = Math.min(clip.height, h);

     int startLine = -1;
     if (y < clip.y)
     {
       startLine = (lineIndent + clip.y - y)/(lineHeight + lineIndent);
       h += (clip.y - startLine * lineHeight - startLine * lineIndent);
     }
     else
     if (y > (clip.y + clip.height)) return;
     else startLine = 0;

     if (startLine < size)
     {
       int lines = (h + lineIndent)/(lineHeight + lineIndent) +
                  (((h + lineIndent)%(lineHeight + lineIndent)>lineIndent)?1:0);
       if (startLine + lines > size) lines = size - startLine;
       y += startLine*(lineHeight + lineIndent);

       g.setFont (getFont());
       if (b)
       {
         for (int i=0; i<lines; i++)
         {
           g.setColor(getForeground());
           paintLine(g, x, y, i + startLine, d);
           y += (lineIndent + lineHeight);
         }
       }
       else
       {
         for (int i=0; i<lines; i++)
         {
           g.setColor(Color.gray);
           paintLine(g, x, y, i + startLine, d);
           g.setColor(Color.white);
           paintLine(g, x + 1, y + 1, i + startLine, d);
           y += (lineIndent + lineHeight);
         }
       }
     }
  }

 /**
  * Gets the text font ascent. The font ascent is the distance from the base line
  * to the top of most Alphanumeric characters. Note, however, that some characters
  * in the font may extend above this height.
  * @return a text font ascent.
  */
  public int getAscent() {
    return getFontMetrics().getAscent();
  }

 /**
  * Returns the total advance width for showing the specified line number.
  * The advance width is the amount by which the current point is moved from one
  * character to the next in a line of the target text model.
  * @param <code>line</code> the specified line number.
  * @return a string width.
  */
  public /*C#virtual*/ int lineWidth (int line) {
    validate();
    return getTextModel().getExtraChar(line);
  }

 /**
  * Returns the total advance width for showing the specified string.
  * The advance width is the amount by which the current point is moved from one
  * character to the next in a line of the target text model.
  * @param <code>line</code> the specified string.
  * @return a string width.
  */
  public /*C#virtual*/ int stringWidth (String s) {
    return getFontMetrics().stringWidth(s);
  }

 /**
  * Returns the total advance width for showing the specified substring.
  * @param <code>s</code> the specified string.
  * @param <code>off</code> the specified starting offset of the substring.
  * @param <code>len</code> the specified length of the substring.
  * @return a string width.
  */
  public /*C#virtual*/ int substrWidth (String s, int off, int len) {
    return getFontMetrics().charsWidth(s.toCharArray(), off, len);
  }

 /**
  * Gets the string presentation of the specified line.
  * @param <code>r</code> the specified line number.
  * @return a string line.
  */
  protected /*C#virtual*/ String getLine(int r) {
    return getTextModel().getLine(r);
  }

 /**
  * The method is called whenever the view owner have been changed.
  * The render overrides the method to store owner component reference. The reference
  * is necessary to synchronize validation status of the render with the owner.
  * @param <code>v</code> the new owner component.
  */
  protected /*C#override*/ void ownerChanged(Validationable v) {
    owner = v;
  }
}


