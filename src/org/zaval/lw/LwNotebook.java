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
import java.awt.event.*;
import java.util.*;

import org.zaval.lw.event.*;
import org.zaval.misc.*;

/**
 * This is notebook container. The main features of the container are shown in the list below:
 * <ul>
 *   <li>
 *     The page tabs can be laid out using  "right", "left", "top" or "bottom" alignments.
 *     Use <code>setTitleAlignment</code> to define the alignment.
 *     The default tabs alignment is "top".
 *   </li>
 *   <li>
 *     It is possible to use any views as tabs views. The appropriate tab view are passed
 *     as argument of <code>addPage</code> method. The library provides standard
 *     tab view - LwTabRender.
 *   </li>
 *   <li>
 *     <code>add</code>, <code>insert</code>, <code>remove</code> methods can be used to change
 *     the notebook pages components.
 *   </li>
 *   <li>
 *     <code>enablePage</code>, <code>isPageEnabled</code> methods can be used to change
 *     enabled state of the specified page.
 *   </li>
 *   <li>
 *     The container implements and uses own layout manager, so it is undesirable
 *     to use any other layout manager for the notebook container.
 *   </li>
 *   <li>
 *     To listen when a page of the container has been selected use <code>addSelectionListener</code>
 *     and <code>removeSelectionListener</code> methods. The selection event is represented by
 *     the LwActionEvent. Use <code>getData</code> method of the event to get a page index that
 *     has been selected.
 *   </li>
 * </ul>
 * <p>
 * The sample below illustrates the notebook container usage:
 * <pre>
 *    ...
 *    LwNotebook n = new LwNotebook();
 *    n.addPage("Tab 1", new LwPanel());
 *    n.addPage("Tab 2", new LwPanel());
 *    n.addPage("Tab 3", new LwPanel());
 *    ...
 * </pre>
 */
public class LwNotebook
extends LwPanel
implements LwMouseListener, LwKeyListener,
           LwLayout, LwTitleInfo, LwFocusListener
{
    private Vector     pages = new Vector();
    private Rectangle  tabArea;
    private int        selectedIndex = -1, offX = 1, offY = 1, orient;
    private LwActionSupport support;

   /**
    * Constructs a notebook container using default (Alignment.TOP) tabs alignment.
    */
    public LwNotebook() {
      this(Alignment.TOP);
    }

   /**
    * Constructs a notebook container using the specified tabs alignment.
    * @param <code>orient</code> the specified tabs alignement.
    */
    public LwNotebook(int orient) {
      setTitleAlignment(orient);
      getViewMan(true).setBorder(new LwTitledBorder(LwBorder.RAISED));
    }

    public /*C#override*/ boolean canHaveFocus() {
      return true;
    }

   /**
    * Gets the tabs alignment.
    * @return a tabs alignment.
    */
    public int getTitleAlignment() {
      return orient;
    }

   /**
    * Sets the specified tabs alignment. The alignment can have one of following values:
    * Alignment.TOP Alignment.BOTTOM Alignment.LEFT Alignment.RIGHT, otherwise the
    * IllegalArgumentException will be thrown.
    * @param <code>o</code> the specified tabs alignement.
    */
    public void setTitleAlignment(int o)
    {
      if (o != Alignment.TOP &&
          o != Alignment.BOTTOM &&
          o != Alignment.LEFT &&
          o != Alignment.RIGHT)  throw new IllegalArgumentException();

      if (orient != o)
      {
        orient = o;
        vrp();
      }
    }

   /**
    * Tests if the specified page is enabled or not.
    * @param <code>index</code> the specified page index.
    * @return <code>true</code> if the page is enaled;otherwise <code>false</code>.
    */
    public boolean isPageEnabled (int index) {
      return ((LwComponent)get(index)).isEnabled();
    }

   /**
    * Sets the given enabled state for the specified page.
    * @param <code>index</code> the specified page index.
    * @param <code>b</code> the given enabled state.
    */
    public void enablePage (int index, boolean b)
    {
      LwComponent c = (LwComponent)get(index);
      if (c.isEnabled() != b)
      {
        c.setEnabled(b);
        if (!b && selectedIndex == index) select(-1);
        repaint();
      }
    }

   /**
    * Adds the page with the specified title. The method uses default tab view to
    * render the page tab.
    * @param <code>title</code> the specified tab title.
    * @param <code>c</code> the specified page component.
    */
    public void addPage (String title, LwComponent c) {
      addPage(new LwTabRender(title), c);
    }

   /**
    * Adds the specified component as the notebook page and sets the specified view as.
    * the tab view.
    * @param <code>v</code> the specified tab view.
    * @param <code>c</code> the specified page component.
    */
    public void addPage (LwView v, LwComponent c)
    {
      pages.addElement(v);
      add (c);
      if (selectedIndex < 0)
      {
        int nxt = next(0, 1);
        if (nxt >= 0) select(nxt);
      }
      vrp();
    }

   /**
    * Returns the tab view for the specified page.
    * @param <code>i</code> the specified page number.
    * @return a tab view.
    */
    public LwView getPageView (int i) {
       return (LwView)pages.elementAt(i);
    }

    public /*C#override*/ void remove(int i)
    {
      if (selectedIndex == i) select(-1);
      pages.removeElementAt(i);
      super.remove(i);
    }

    public /*C#override*/ void removeAll()
    {
      if (selectedIndex >= 0) select(-1);
      pages.removeAllElements();
      super.removeAll();
    }

   /**
    * The method is overrided by the component for internal usage. Don't tuch the method.
    */
    protected /*C#override*/ void recalc ()
    {
      int maxW = 0, maxH = 0, w = 0, h = 0;
      tabArea = new Rectangle();
      Insets ins = getInsets();
      for (int i=0; i<pages.size(); i++)
      {
        LwView v = (LwView)pages.elementAt(i);
        Dimension ps = v.getPreferredSize();
        if (maxW < ps.width ) maxW = ps.width;
        if (maxH < ps.height) maxH = ps.height;
        w += ps.width;
        h += ps.height;
      }

      if (orient == Alignment.LEFT || orient == Alignment.RIGHT)
      {
         tabArea.y      = ins.top + offY;
         tabArea.width  = maxW;
         tabArea.height = h;
         tabArea.x      = (orient == Alignment.LEFT)?ins.left +offX:width - ins.right - offX - maxW;
      }
      else
      {
        tabArea.x      = ins.left + offX;
        tabArea.width  = w  + ins.left;
        tabArea.height = maxH;
        tabArea.y      = (orient == Alignment.TOP)?ins.top +offY:height - ins.bottom - offY - maxH;
      }
      super.recalc();
    }

   /**
    * Gets the tab index for the tab that is selected at the moment.
    * @return a tab index.
    */
    public int getSelectedIndex() {
      return selectedIndex;
    }

   /**
    * Paints this component.
    * @param <code>g</code> the graphics context to be used for painting.
    */
    public /*C#override*/ void paint(Graphics g)
    {
      Rectangle clip = g.getClipBounds();
      if (selectedIndex >= 0)
      {
        Rectangle r = getPageBounds(selectedIndex);
        if (orient == Alignment.LEFT || orient == Alignment.RIGHT) g.clipRect(r.x, 0, r.width, r.y);
        else                                                       g.clipRect(0, r.y, r.x, r.height);
      }

      for (int i=0; i<selectedIndex; i++)
      {
        Rectangle r = getPageBounds(i);
        LwView v = (LwView)pages.elementAt(i);
        v.paint(g, r.x, r.y, r.width, r.height, (Drawable)get(i));
      }

      if (selectedIndex >= 0)
      {
        g.setClip(clip);
        Rectangle r = getPageBounds(selectedIndex);
        if (orient == Alignment.LEFT || orient == Alignment.RIGHT) g.clipRect(r.x, r.y + r.height, r.width, tabArea.height);
        else                                                       g.clipRect(r.x + r.width, r.y, tabArea.width, r.height);
      }

      for (int i=selectedIndex+1; i<pages.size(); i++)
      {
        Rectangle r = getPageBounds(i);
        LwView v = (LwView)pages.elementAt(i);
        v.paint(g, r.x, r.y, r.width, r.height, (Drawable)get(i));
      }

      if (selectedIndex >= 0)
      {
        g.setClip(clip);
        Rectangle r = getPageBounds(selectedIndex);
        LwView v = (LwView)pages.elementAt(selectedIndex);
        v.paint(g, r.x, r.y, r.width, r.height, (Drawable)get(selectedIndex));
        v.paint(g, r.x+1, r.y, r.width, r.height, (Drawable)get(selectedIndex));
        if (hasFocus()) LwToolkit.drawDotRect(g, r.x + 3, r.y + 3, r.width - 6, r.height - 6);
      }
   }

   public void keyPressed (LwKeyEvent e)
   {
     if (selectedIndex != -1 && pages.size() > 0)
     {
       switch (e.getKeyCode())
       {
         case KeyEvent.VK_UP   :
         case KeyEvent.VK_LEFT :
         {
           int nxt = next (selectedIndex-1, -1);
           if (nxt >= 0) select(nxt);
         }  break;
         case KeyEvent.VK_DOWN :
         case KeyEvent.VK_RIGHT:
         {
           int nxt = next (selectedIndex+1, 1);
           if (nxt >= 0) select(nxt);
         } break;
       }
     }
   }

   public void mousePressed (LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()))
     {
       int index = getPageAt(e.getX(), e.getY());
       if (index >= 0 && isPageEnabled(index)) select(index);
     }
   }

   public void keyReleased  (LwKeyEvent e)   {}
   public void keyTyped     (LwKeyEvent e)   {}
   public void mouseEntered (LwMouseEvent e) {}
   public void mouseExited  (LwMouseEvent e) {}
   public void mouseClicked (LwMouseEvent e) {}
   public void mouseReleased(LwMouseEvent e) {}

   public void  componentAdded   (Object id, Layoutable lw, int index) {}
   public void  componentRemoved (Layoutable lw, int index) {}

   public Dimension calcPreferredSize(LayoutContainer target)
   {
     Dimension max = LwToolkit.getMaxPreferredSize(target);
     if (orient == Alignment.BOTTOM || orient == Alignment.TOP)
     {
       max.width  = Math.max (2*offX + max.width, 2*offX + tabArea.width);
       max.height += (offY + tabArea.height);
     }
     else
     {
       max.width  += (offX + tabArea.width);
       max.height  = Math.max (2*offY + max.height, 2*offY + tabArea.height);
     }
     return max;
   }

   public void layout(LayoutContainer target)
   {
     Insets targetInsets = target.getInsets();
     for (int i=0; i<target.count(); i++)
     {
       LwComponent l = (LwComponent)target.get(i);

       if (isSelected(i))
       {
         l.setVisible(true);
         if (orient == Alignment.TOP || orient ==  Alignment.BOTTOM)
           l.setSize(width  - targetInsets.left - targetInsets.right,
                     height - tabArea.height - targetInsets.top - targetInsets.bottom - offY);
         else
           l.setSize(width  - tabArea.width - targetInsets.left - targetInsets.right - offX,
                     height - targetInsets.top - targetInsets.bottom);

         switch (orient)
         {
           case Alignment.TOP    : l.setLocation(targetInsets.left, tabArea.y + tabArea.height); break;
           case Alignment.BOTTOM : l.setLocation(targetInsets.left, targetInsets.top); break;
           case Alignment.LEFT   : l.setLocation(tabArea.x + tabArea.width, targetInsets.top); break;
           case Alignment.RIGHT  : l.setLocation(targetInsets.left, targetInsets.top); break;
         }
       }
       else l.setVisible(false);
     }
   }

  /**
   * Selects the tab by the specified index. Use <code>-1</code> index value to de-select
   * current selected tab, in this case no one tab will be selected.
   * @param <code>index</code> the specified item index.
   */
   public void select(int index)
   {
     if (selectedIndex != index)
     {
       selectedIndex = index;
       perform(selectedIndex);
       vrp();
     }
   }

  /**
   * Tests if the tab with the specified index is selected or not.
   * @return <code>true</code> if the tab with the specified index is selected; otherwise
   * <code>false</code>.
   */
   public boolean isSelected(int i) {
     return i == selectedIndex;
   }

  /**
   * Adds the specified selection listener to receive selection events from this notebook container.
   * @param <code>l</code> the specified listener.
   */
   public void addSelectionListener(LwActionListener l)  {
     if(support == null) support = new LwActionSupport();
     support.addListener(l);
   }

  /**
   * Removes the specified selection listener so it no longer receives selection events
   * from this notebook conatiner.
   * @param <code>l</code> the specified listener.
   */
   public void removeSelectionListener(LwActionListener l)  {
     if(support != null) support.removeListener(l);
   }

   public Rectangle getTitleBounds()
   {
     if (selectedIndex == -1)
     {
       if (orient == Alignment.LEFT  ) return new Rectangle (0, 0, tabArea.width + tabArea.x, 0);
       if (orient == Alignment.TOP   ) return new Rectangle (0, 0, 0, tabArea.height + tabArea.y);
       if (orient == Alignment.RIGHT ) return new Rectangle (tabArea.x, 0, tabArea.width, 0);
       if (orient == Alignment.BOTTOM) return new Rectangle (0, tabArea.y, 0, tabArea.height);
       return null;
     }
     else   return getPageBounds(selectedIndex);
   }


  public void focusGained(LwFocusEvent e) {
    if (selectedIndex < 0) select(next(0, 1));
  }

  public void focusLost(LwFocusEvent e) {}


  /**
   * Fires appropriate selection event for list of selection listeners. The method
   * performs LwActionEvent as the selection event where the <code>getData</code> method
   * returns the tab index that has been selected
   * @param <code>index</code> the tab index that has been selected.
   */
   protected /*C#virtual*/ void perform(int index) {
     if (support != null) support.perform(new LwActionEvent(this, new Integer(index)));
   }

  /**
   * Gets the default layout manager that is set with the container during initialization.
   * The component defines itself as the default layout. Don't use any other layout for
   * the component, in this case the working of the component can be wrong.
   * @return a layout manager.
   */
   protected /*C#override*/ LwLayout getDefaultLayout() {
     return this;
   }

   private Rectangle getPageBounds(int index)
   {
     Rectangle res = null;
     int x = 0, y = 0;
     for (int i=0; i<pages.size(); i++)
     {
       LwView    v = (LwView)pages.elementAt(i);
       Dimension d = v.getPreferredSize();

       if (index == i)
       {
         if (orient == Alignment.BOTTOM || orient == Alignment.TOP)
           res = new Rectangle(x + tabArea.x, tabArea.y, d.width, tabArea.height);
         else
           res = new Rectangle(tabArea.x, tabArea.y + y, tabArea.width, d.height);
         break;
       }

       x += d.width;
       y += d.height;
     }

     if (index == selectedIndex)
     {
       switch (orient)
       {
         case Alignment.LEFT  :
         {
           res.x -= offX;
           res.y -= offY;
           res.width += offX;
           res.height += 2*offY;
         } break;
         case Alignment.RIGHT :
         {
           res.y -= offY;
           res.width += offX;
           res.height += 2*offY;
         } break;
         case Alignment.TOP :
         {
           res.x -= offX;
           res.y -= offY;
           res.width += 2*offX;
           res.height += offY;
         } break;
         case Alignment.BOTTOM :
         {
           res.x -= offX;
           res.width  += 2*offX;
           res.height += offY;
         } break;
       }
     }
     return res;
   }

   private int getPageAt(int x, int y)
   {
     if (tabArea.contains(x, y))
       for (int i=0; i<pages.size(); i++)
         if (getPageBounds(i).contains(x, y)) return i;
     return -1;
   }

   private int next (int page, int d)
   {
     for (;page>=0 && page < pages.size(); page+=d)
      if (isPageEnabled(page)) return page;
     return -1;
   }
}

