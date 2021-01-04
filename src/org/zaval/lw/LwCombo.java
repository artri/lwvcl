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

import org.zaval.lw.event.*;
import java.awt.event.*;
import java.awt.*;

/**
 * This is combobox component. The component bases on LwList component.
 * Actually the usage of the combobox is the same of the light weight list component. To work with the list
 * component use <code>getList</code> and <code>setList</code> methods. The sample below
 * illustrates the component usage:
 * <pre>
 *   ...
 *   LwCombo combo = new LwCombo();
 *   LwList  list  = combo.getList();
 *   list.add(new LwLabel("Item 1"));
 *   list.add(new LwLabel("Item 2"));
 *   list.add(new LwLabel("Item 3"));
 *   ...
 * </pre>
 * <p>
 * The component uses the special pad window to show the combo list. The window height can be
 * fixed by <code>setMaxPadHeight</code>. In this case, if the list cannot be placed inside
 * the window wholly the scroll bar panel will be used.
 * <p>
 * The component uses folowing resources:
 * <ul>
 *   <li>
 *     "br.sunken" - as the border view of the component;
 *   </li>
 *   <li>
 *     "br.plain" - as the popup window border view;
 *   </li>
 *   <li>
 *     "bt.bottom.out","bt.bottom.over","bt.bottom.pressed","bt.bottom.disabled" - as
 *     the combo button views;
 *   </li>
 * </ul>
 */
public class LwCombo
extends LwPanel
implements LwActionListener,
           LwWinListener, LwMouseListener, LwKeyListener,
           LwComposite
{
  private LwList       list;
  private LwStButton   button;
  private LwCanvas     view;
  private LwScrollPan  window;
  private LwCompRender render;
  private int maxPadHeight;

 /**
  * Constructs a combobox component with no items.
  */
  public LwCombo()
  {
    getViewMan(true).setBorder(LwToolkit.getView("br.sunken"));
    LwStButton button = new LwStButton();
    LwAdvViewMan man = new LwAdvViewMan();
    man.put ("st.out", LwToolkit.getView("bt.bottom.out"));
    man.put ("st.over", LwToolkit.getView("bt.bottom.over"));
    man.put ("st.pressed", LwToolkit.getView("bt.bottom.pressed"));
    man.put ("st.disabled", LwToolkit.getView("bt.bottom.disabled"));
    button.setViewMan(man);

    view   = new LwCanvas();
    render = new LwCompRender(null);
    view.getViewMan(true).setView(render);

    add(LwBorderLayout.EAST, button);
    add(LwBorderLayout.CENTER, view);
    setList(new LwComboList());
  }

  public /*C#override*/ boolean canHaveFocus() {
    return true;
  }

 /**
  * Gets the combo button.
  * @return a combo button.
  */
  public LwStButton getComboButton() {
    return button;
  }

  public /*C#override*/ void insert(int index, Object constr, LwComponent c)
  {
    if (button == null && c instanceof LwStButton)
    {
      button = (LwStButton)c;
      button.addActionListener(this);
    }
    super.insert(index, constr, c);
  }

  public /*C#override*/ void remove(int index)
  {
    if (button == get(index))
    {
      button.removeActionListener(this);
      button = null;
    }
    super.remove(index);
  }

  public /*C#override*/ void removeAll ()
  {
    if (button != null)
    {
      button.removeActionListener(this);
      button = null;
    }
    super.removeAll();
  }

 /**
  * Gets the max pad height.
  * @return a max pad height.
  */
  public int getMaxPadHeight () {
    return maxPadHeight;
  }

 /**
  * Sets the max pad height.
  * @param <code>h</code> the max pad height.
  */
  public void setMaxPadHeight (int h)
  {
    if (h < 0) throw new IllegalArgumentException();
    if (maxPadHeight != h)
    {
      hidePad();
      maxPadHeight = h;
    }
  }

 /**
  * Gets the list component that is used as popup window content of the combobox.
  * Use the list to modify the combobox content.
  * @return a list component.
  */
  public LwList getList() {
    return list;
  }

 /**
  * Sets the specified list component that will be used as popup window content of the combobox.
  * @param <code>l</code> the specified list component.
  */
  public void setList (LwList l)
  {
    if (list != l)
    {
      hidePad();
      if (list != null) list.removeSelectionListener(this);

      list = l;
      list.addSelectionListener(this);
      window = new LwScrollPan(list);
      window.getViewMan(true).setBorder("br.plain");
      vrp();
    }
  }

  public void keyPressed (LwKeyEvent e)
  {
    LwList l     = getList();
    int    index = l.getSelectedIndex();
    switch(e.getKeyCode())
    {
      case KeyEvent.VK_LEFT :
      case KeyEvent.VK_UP   : if (index > 0) l.select(index-1); break;
      case KeyEvent.VK_DOWN :
      case KeyEvent.VK_RIGHT: if (l.count()-1 > index) l.select(index+1); break;
    }
  }

  public void keyTyped (LwKeyEvent e) {}
  public void keyReleased (LwKeyEvent e) {}

  public void actionPerformed(LwActionEvent e)
  {
    if (e.getSource() == button) showPad();
    else
    {
      hidePad();
      render.setTarget(list.getSelected());
      repaint();
    }
  }

  public void winOpened(LwWinEvent e) { }
  public void winClosed(LwWinEvent e) { }
  public void winActivated(LwWinEvent e) {}

  public void winDeactivated(LwWinEvent e)
  {
    LwLayer l = LwToolkit.getDesktop(this).getLayer(LwWinLayer.ID);
    l.remove(l.indexOf(e.getLwComponent()));
    requestFocus();
  }

  public void mouseClicked (LwMouseEvent e) {}
  public void mouseEntered (LwMouseEvent e) {}
  public void mouseExited  (LwMouseEvent e) {}
  public void mouseReleased(LwMouseEvent e) {}

  public void mousePressed (LwMouseEvent e) {
    if (LwToolkit.isActionMask(e.getMask()) &&
        e.getX() > view.getX() && e.getY() > view.getY() &&
        e.getX() < view.getX() + view.getWidth ()&&
        e.getY() < view.getY() + view.getHeight()  ) showPad();
  }

  public /*C#override*/ void paintOnTop (Graphics g)
  {
    if (hasFocus())
      LwToolkit.drawMarker(g, view.getX(), view.getY(), view.getWidth(), view.getHeight(), getBackground(), list.getSelectColor());
  }

  public boolean catchInput (LwComponent child) {
    return child != button;
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return new LwBorderLayout();
  }

  protected /*C#override*/ Dimension calcPreferredSize()
  {
    Dimension ps = LwToolkit.getMaxPreferredSize(list);
    view.setPSSize(ps.width, ps.height);
    return super.calcPreferredSize();
  }

  private void hidePad()
  {
    LwDesktop d = LwToolkit.getDesktop(this);
    if (d != null)
    {
      LwWinLayer l = (LwWinLayer)d.getLayer(LwWinLayer.ID);
      if (l.getActive() == window)
      {
        l.remove(window);
        requestFocus();
      }
    }
  }

  private void showPad()
  {
    LwDesktop desktop  = LwToolkit.getDesktop (this);
    if (desktop != null)
    {
      LwWinLayer winlayer = (LwWinLayer)desktop.getLayer(LwWinLayer.ID);
      if (winlayer.getActive() != window)
      {
        Dimension ps = window.getPreferredSize();
        Point     p  = LwToolkit.getAbsLocation(this);
        if (maxPadHeight > 0 && ps.height > maxPadHeight)  ps.height = maxPadHeight;

        if (p.y + height + ps.height > desktop.getHeight())
        {
          if (p.y - ps.height >= 0) p.y -= (ps.height + height);
          else
          {
            int hAbove = desktop.getHeight() - p.y - height;
            if (p.y > hAbove)
            {
              ps.height = p.y;
              p.y -= (ps.height + height);
            }
            else ps.height = hAbove;
          }
        }

        window.setSize(width, ps.height);
        window.setLocation(p.x, p.y + height);
        list.notifyScrollMan(list.getSelectedIndex());
        winlayer.add(this, window);
        winlayer.activate(window);
        window.requestFocus();
      }
    }
  }
}

