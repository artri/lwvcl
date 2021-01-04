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
package org.zaval.lw.grid;

import org.zaval.lw.event.*;
import org.zaval.lw.*;
import org.zaval.data.*;
import org.zaval.misc.*;

import java.awt.*;
import java.util.*;

/**
 * This component is used as the top grid component caption with a custom views.
 * The list below desribes basic features of the component:
 * <ul>
 *   <li>
 *     Use <code>putTitle</code> method to set caption title for appropriate column.
 *   </li>
 *   <li>
 *     Use <code>setViewProvider</code> method to customize the caption views.
 *   </li>
 *   <li>
 *     Use <code>setBorderView</code> method to customize the caption border view.
 *   </li>
 *   <li>
 *     Using the component it is possible to change size of the grid columns using
 *     mouse.
 *   </li>
 * </ul>
 */
public class LwGridCaption
extends LwCanvas
implements LwMouseMotionListener, Cursorable
{
 /**
  * The default row height.
  */
  public static final int DEF_ROWHEIGHT = 20;

  private LwGridViewProvider provider ;
  private int                activeAreaWidth  = 3, minSize = 10;
  private int                cellX, cellCol = -1;
  private LwGridMetrics      metrics;
  private int                px, lx, psW, psH;
  private Object[]           titles;
  private boolean            started;
  private LwView             borderView;

 /**
  * Constructs the object with the specified grid metrics.
  * @param <code>m</code> the specified grid metrics.
  */
  public LwGridCaption(LwGridMetrics m)
  {
    metrics  = m;
    LwDefViews v = new LwDefViews();
    v.getTextRender().setFont(LwToolkit.BFONT);
    setViewProvider(v);
    setBorderView (LwToolkit.getView("br.raised"));
  }

 /**
  * Sets the specified view to render the caption border.
  * @param <code>p</code> the specified view .
  */
  public void setBorderView (LwView v)
  {
    if (v != borderView)
    {
      borderView = v;
      vrp();
    }
  }

 /**
  * Sets the specified view provider. The provider defines cells views for the
  * caption component. The class sets <code>LwDefViews</code> as the default view
  * provider.
  * @param <code>p</code> the specified view provider.
  */
  public void setViewProvider (LwGridViewProvider p)
  {
    if (p != provider)
    {
      provider = p;
      vrp();
    }
  }

 /**
  * Gets the title value for the specified column.
  * @param <code>col</code> the specified column.
  * @return a title value.
  */
  public Object getTitle(int col) {
    return titles == null || titles.length <= col?null:titles[col];
  }

 /**
  * Sets the given title value for the specified column.
  * It is possible to use <code>null</code> value as the title value.
  * @param <code>col</code> the specified column.
  * @param <code>title</code> the specified title value.
  */
  public void putTitle (int col, Object title)
  {
    Object old = getTitle (col);
    if (old != title || (title != null && !title.equals(old)))
    {
      if (titles == null) titles = new Object[col + 1];
      else
      {
        if (titles.length <= col)
        {
          Object[] nt = new Object[col + 1];
          System.arraycopy(titles, 0, nt, 0, titles.length);
          titles = nt;
        }
      }
      titles[col] = title;
      vrp();
    }
  }

  public void startDragged(LwMouseMotionEvent e)
  {
    if (cellCol >= 0)
    {
      started = true;
      px = e.getX();
      lx = cellX + metrics.getColWidth(cellCol);
      drawVLine(e.getLwComponent(), lx, 0, getSizeLineHeight());
    }
  }

  public void endDragged (LwMouseMotionEvent e)
  {
    int dx = e.getX() - px;
    if (started)
    {
      drawVLine(e.getLwComponent(), lx, 0, getSizeLineHeight());
      metrics.setColWidth(cellCol, lx - cellX);
      started = false;
      cellCol = -1;
    }
  }

  public void mouseDragged(LwMouseMotionEvent e)
  {
    int dx  = e.getX() - px;
    int nlx = 0;
    if (started && (nlx = cellX + metrics.getColWidth(cellCol) + dx) > cellX + minSize)
    {
      int h = getSizeLineHeight();
      drawVLine(e.getLwComponent(), lx, 0, h);
      lx = nlx;
      drawVLine(e.getLwComponent(), lx, 0, h);
    }
  }

  public void mouseMoved (LwMouseMotionEvent e) {}

  public int getCursorType(LwComponent target, int x, int y)
  {
    if (!started)
    {
      int g  = metrics.getNetGap();
      int xx = getInsets().left + metrics.getOrigin().x + g;
      for (int i=0; i < metrics.getGridCols(); i++)
      {
        int w = metrics.getColWidth (i);
        xx += (w + g);
        if (x < xx + activeAreaWidth && x > xx - activeAreaWidth)
        {
          cellX   = xx - w;
          cellCol = i;
          return Cursor.W_RESIZE_CURSOR;
        }
      }
    }
    return -1;
  }

  public /*C#override*/ void paint (Graphics g)
  {
    Insets    ins  = getInsets();
    Point     p    = metrics.getOrigin();
    int       gap  = metrics.getNetGap();
    int       x    = ins.left + p.x;
    int       y    = ins.top;
    int       cols = metrics.getGridCols();
    Rectangle clip = g.getClipBounds();

    for (int i=0; i < cols; i++)
    {
      int    w = metrics.getColWidth(i) + gap + (((cols - 1) == i)?gap:0);
      Object t = getTitle(i);

      if (t != null)
      {
        LwView v = provider.getView(0, i, t);
        Dimension ps = v.getPreferredSize();

        Color bg = provider.getCellColor(0, i);
        if (bg != null)
        {
          g.setColor (bg);
          g.fillRect(x, y, w - 1, psH - 1);
        }

        if (v != null)
        {
          Point pp = Alignment.getLocation(ps, provider.getXAlignment(0, i),
                                               provider.getYAlignment(0, i),
                                               w, psH);
          g.clipRect(x, y, w, psH);
          v.paint (g, x + pp.x, y + pp.y, this);
          g.setClip(clip);
        }
      }

      if (borderView != null) borderView.paint (g, x, y, w, psH, this);
      x += w;
    }
  }

  protected /*C#override*/ void recalc()
  {
    psW = 0;
    psH = 0;
    int cols = metrics.getGridCols();
    for (int i = 0; i < cols; i++)
    {
      Object o = getTitle(i);
      if (o != null)
      {
        LwView v = provider.getView (0, i, o);
        if (v != null)
        {
          Dimension ps = v.getPreferredSize();
          if (ps.height > psH) psH = ps.height;
          psW += ps.width;
        }
      }
    }

    if (psH == 0) psH = DEF_ROWHEIGHT;
    Insets ins = borderView != null?borderView.getInsets():new Insets(0,0,0,0);
    psW += (ins.left + ins.right)*cols;
    psH += (ins.top + ins.bottom);
  }

  protected /*C#override*/ Dimension calcPreferredSize() {
    return new Dimension (psW, psH);
  }

  private int getSizeLineHeight() {
    //!!!
    return  getLwParent().getSize().height;
  }

  private static void drawVLine (LwComponent c, int x, int y, int len) {
    LwToolkit.drawXORLine (c, x, y, x, y + len);
  }
}

