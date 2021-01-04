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
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.util.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is list component. The main features of the component are shown in the list below:
 * <ul>
 *   <li>
 *      The list component uses other light weight component as the list items. Use
 *      <code>add</code>, <code>remove</code>, <code>insert</code> methods to manipulate
 *      the list component content. For example, if it is necessary to add following three
 *      text items: "Item 1", "Item 2", "Item 3", you can use LwLabel component as it is shown
 *      below:
 *      <pre>
 *         ...
 *         LwList list = new LwList();
 *         list.add(new LwLabel("Item 1"));
 *         list.add(new LwLabel("Item 2"));
 *         list.add(new LwLabel("Item 3"));
 *         ...
 *      </pre>
 *      The class has method <code>add</code> that get string as input argument. The method
 *      creates LwLabel object with the string argument as a title. In this case the sample
 *      above can be simplify as follow:
 *      <pre>
 *         ...
 *         LwList list = new LwList();
 *         list.add("Item 1");
 *         list.add("Item 2");
 *         list.add("Item 3");
 *         ...
 *      </pre>
 *   </li>
 *   <li>
 *     The list component is a composite component, that allows to use other composite component
 *     as the list items. It means the it is possible to add LwButton component to the
 *     list as an item and it is possible to work with the component after it has been
 *     selected (the button component can be pressed).
 *   </li>
 *   <li>
 *     The list component uses LwListLayout as default manager to layout the list items.
 *     It is possible to use any other layout manager for the purpose, but the layout
 *     manager should implement org.zaval.misc.PosInfo to have ability working together
 *     with the a pos manager of the list. The library provides two layout managers that
 *     can be used as the layout manager: LwListLayout and LwGridLayout.
 *   </li>
 *   <li>
 *     The list has PosController class that is used to control position. Draw attention that
 *     selection and position are two different things. The list implementation automatically
 *     reselects item when the position has been changed. But further version of the component
 *     can support multiselection. To control the list position use pos controller that can be
 *     got by <code>getPosController</code> method. To control selection use <code>select</code>,
 *     <code>getSelected</code>, <code>getSelectedIndex</code>, <code>isSelected</code> methods.
 *     To listen when an item component has been selected use <code>addSelectionListener</code>
 *     and <code>removeSelectionListener</code> methods.
 *   </li>
 *   <li>
 *     The list component can scroll its view if not all items are visible. The feature works
 *     correct if the component will be inserted in LwScrollPan.
 *   </li>
 *   <li>
 *     Use <code>addSelectionListener</code> and <code>removeSelectionListener</code> methods
 *     to listen whenever the selected item has been changed. The selection event is represented
 *     by the LwActionEvent where <code>getData</code> method returns the selected item index.
 *   </li>
 * </ul>
 */
public class LwList
extends LwPanel
implements LwKeyListener, LwMouseListener,
           LwFocusListener, LwComposite,
           PosListener, ScrollObj
{
    private int selectedIndex = -1;
    private PosController controller;
    private PosInfo       posInfo;
    private LwActionSupport support;
    private LwComponent input;
    private int  dx, dy;
    private Color selectColor = LwToolkit.darkBlue, posRectColor = Color.yellow;
    private ScrollMan man;

   /**
    * Constructs a list component with no items.
    */
    public LwList() {
      setPosController(new PosController());
    }

    public /*C#override*/ boolean canHaveFocus() {
      return true;
    }

   /**
    * Sets the specified position controller. The controller manages the virtual cursor
    * location.
    * @param <code>c</code> the specified position controller.
    */
    public void setPosController(PosController c)
    {
      if(c == null) throw new IllegalArgumentException();
      if(c != controller)
      {
        if (controller != null) controller.removePosListener(this);
        controller = c;
        controller.addPosListener(this);
        if (posInfo != null) controller.setPosInfo(posInfo);
        repaint();
      }
    }

   /**
    * Sets the layout manager for this container. The layout manager has to implement
    * org.zaval.misc.PosInfo interface, the interface allows to control virtual position
    * of the list component with the position controller of the component.
    * @param <code>l</code> the specified layout manager.
    */
    public /*C#override*/ void setLwLayout(LwLayout l)
    {
      if (l != getLwLayout())
      {
        super.setLwLayout(l);
        if (l instanceof PosInfo) posInfo = (PosInfo)l;
        if (controller != null) controller.setPosInfo(posInfo);
      }
    }

   /**
    * Gets the position controller.
    * @return a position controller.
    */
    public PosController getPosController() {
      return controller;
    }

   /**
    * Gets the selection marker color.
    * @return a selection marker color.
    */
    public Color getSelectColor() {
      return selectColor;
    }

   /**
    * Sets the selection marker color.
    * @param <code>c</code> the selection marker color.
    */
    public void setSelectColor(Color c)
    {
      if (!c.equals(selectColor)) {
        selectColor = c;
        repaint();
      }
    }

   /**
    * Gets the position rectangle color.
    * @return a position rectangle color.
    */
    public Color getPosRectColor() {
      return posRectColor;
    }

   /**
    * Sets the color for a position rectangle. The position rectangle is rendered around
    * a list item where the current position is.
    * @param <code>c</code> the color.
    */
    public void setPosRectColor(Color c)
    {
      if (!c.equals(posRectColor)) {
        posRectColor = c;
        repaint();
      }
    }

   /**
    * Adds an item to the list. The method creates LwLabel component with the specified
    * label and adds it to the list.
    * @param <code>s</code> the specified label.
    */
    public void add (String s)  {
      add (new LwLabel(s));
    }

    /**
    * Selects the item by the specified index.
    * @param <code>index</code> the specified item index.
    */
    public void select(int index)
    {
      if (selectedIndex != index)
      {
        boolean b = clearSelection();
        if (index >= 0)
        {
          selectedIndex = index;
          b = !notifyScrollMan(index);
        }
        if (b) repaint();

        if (selectedIndex >= 0) perform(selectedIndex);
      }
    }

   /**
    * Gets the selected item index.
    * @return a selected item index.
    */
    public int getSelectedIndex() {
      return selectedIndex;
    }

   /**
    * Tests if the item with the specified index is selected or not.
    * @return <code>true</code> if the item with the specified index is selected; otherwise
    * <code>false</code>.
    */
    public boolean isSelected(int i)  {
      return i == selectedIndex;
    }

   /**
    * Adds the specified selection listener to receive selection events from this list component.
    * The event is represented by LwActionEvent where <code>getData</code> method returns Integer
    * object that is selection index.
    * @param <code>l</code> the specified listener.
    */
    public void addSelectionListener(LwActionListener l)  {
      if (support == null) support = new LwActionSupport();
      support.addListener(l);
    }

   /**
    * Removes the specified selection listener so it no longer receives selection events
    * from this list component.
    * @param <code>l</code> the specified listener.
    */
    public void removeSelectionListener(LwActionListener l)  {
      if (support != null) support.removeListener(l);
    }

   /**
    * Paints this component. The method is overrided to paint the list item selection rectangle.
    * @param <code>g</code> the graphics context to be used for painting.
    */
    public /*C#override*/ void paintOnTop(Graphics g) {
      if (!hasInputFocus()) drawSelMarker(g);
      if (hasFocus()) drawPosMarker(g);
    }

   /**
    * Invoked to paint selection marker.
    * @param <code>g</code> the graphics context to be used for painting.
    */
    protected /*C#virtual*/ void drawSelMarker(Graphics g)
    {
       if (selectedIndex >= 0)
       {
         LwComponent c = getSelected();
         LwToolkit.drawMarker(g, c.getX(), c.getY(), c.getWidth(), c.getHeight(), getBackground(), selectColor);
       }
    }

   /**
    * Invoked to paint position marker.
    * @param <code>g</code> the graphics context to be used for painting.
    */
    protected /*C#virtual*/ void drawPosMarker(Graphics g)
    {
       int offset = controller.getOffset();
       if (offset >= 0)
       {
         LwComponent c = (LwComponent)get(offset);
         g.setColor(posRectColor);
         g.drawRect(c.getX(), c.getY(), c.getWidth()-1, c.getHeight()-1);
       }
    }

    public /*C#virtual*/ void mouseClicked(LwMouseEvent e) {}
    public /*C#virtual*/ void mouseEntered(LwMouseEvent e) {}
    public /*C#virtual*/ void mouseExited(LwMouseEvent e)  {}

    public /*C#virtual*/ void mousePressed(LwMouseEvent e)
    {
      if (LwToolkit.isActionMask(e.getMask()))
      {
        int index = LwToolkit.getDirectCompAt(e.getX(), e.getY(), this);
        if (index >= 0)
        {
          controller.setOffset(index);
          select(index);
        }
      }
    }

    public /*C#virtual*/ void mouseReleased(LwMouseEvent e) {}

    public /*C#virtual*/ void keyPressed(LwKeyEvent e)
    {
      if (posInfo.getMaxOffset() > 0)
      {
        boolean isCtrl = (e.getMask() &  KeyEvent.CTRL_MASK) > 0;
        switch(e.getKeyCode())
        {
          case KeyEvent.VK_END :  if (isCtrl) controller.setOffset(controller.getMaxOffset());
                                  else        controller.seekLineTo(PosController.END);
                                  break;
          case KeyEvent.VK_HOME:  if (isCtrl) controller.setOffset(0);
                                  else        controller.seekLineTo(PosController.BEG);
                                  break;
          case KeyEvent.VK_RIGHT: controller.seek(1); break;
          case KeyEvent.VK_DOWN:  controller.seekLineTo(PosController.DOWN); break;
          case KeyEvent.VK_LEFT:  controller.seek(-1); break;
          case KeyEvent.VK_UP:    controller.seekLineTo(PosController.UP); break;
          case KeyEvent.VK_PAGE_UP  : controller.seek(pageSize(-1));  break;
          case KeyEvent.VK_PAGE_DOWN: controller.seek(pageSize(1)); break;
        }
      }
    }

    public /*C#virtual*/ void keyReleased(LwKeyEvent e){}
    public /*C#virtual*/ void keyTyped(LwKeyEvent e)   {}

    public /*C#virtual*/ void focusGained(LwFocusEvent e)
    {
      int o = controller.getOffset();
      if (o >= 0 && o == selectedIndex) input = (LwComponent)get(controller.getOffset());
      else input=null;
    }

    public /*C#virtual*/ void focusLost(LwFocusEvent e) {}

    public /*C#virtual*/ boolean catchInput (LwComponent child)
    {
      boolean b = input != null && LwToolkit.isAncestorOf(input, child);
      if (b && !hasInputFocus() && !hasFocus()) input = null;
      return (input == null || !b);
    }

    public /*C#virtual*/ void posChanged(PosEvent e)
    {
      int off = controller.getOffset();
      select(off);
      repaint();
      input = (controller.getOffset() >= 0)?(LwComponent)get(off):null;
    }

    public /*C#override*/ void insert(int i, Object s, LwComponent d)
    {
      int offset = controller.getOffset();
      super.insert(i, s, d);

      if (offset >= 0 && offset >= i)
      {
        clearSelection();
        controller.clearPos();
        offset = offset == i?offset:offset+1;
        controller.setOffset(offset);
      }
      //if (csupport != null) csupport.perform(new LwContainerEvent(d, this, LwContainerEvent.COMP_ADDED));
    }

    public /*C#override*/ void remove(int i)
    {
      int offset = controller.getOffset();
      super.remove(i);

      if (offset >= 0)
      {
        if (count() == 0 || offset >= i)
        {
          clearSelection();
          controller.clearPos();
        }

        if (count() > 0 && offset >= i)
        {
          offset = offset == i?offset:offset-1;
          controller.setOffset(offset);
        }
      }
    }

    public /*C#override*/ void removeAll()
    {
      clearSelection();
      controller.clearPos();
      super.removeAll();
    }

    public Point getSOLocation () {
      return getLayoutOffset();
    }

    public void setSOLocation (int x, int y)
    {
      if (x != dx || y != dy)
      {
        dx = x;
        dy = y;
        vrp();
      }
    }

    public Dimension getSOSize() {
      return getPreferredSize();
    }

    public void setScrollMan (ScrollMan m) {
      man = m;
    }

    public boolean moveContent() {
      return true;
    }

    public /*C#override*/ Point getLayoutOffset()  {
      return new Point(dx, dy);
    }

   /**
    * Gets the selected item component.
    * @return a selected item component.
    */
    public LwComponent getSelected() {
      return selectedIndex < 0?null:(LwComponent)get(selectedIndex);
    }

   /**
    * Creates and fires appropriate selection event for list of selection listeners.
    * @param <code>from</code> the selected item index.
    */
    protected /*C#virtual*/ void perform(int from) {
      if (support != null) support.perform(new LwActionEvent(this, new Integer(from)));
    }

   /**
    * Notifies the scroll manager that an item component at the specified index should be fully
    * visible. The scroll manager should scrolls the list view to make the component fully
    * visible if it is necessary.
    * @param <code>index</code> the specified index.
    * @return <code>true</code> if the item component has not been fully visible; <code>false</code>
    * otherwise.
    */
    protected /*C#virtual*/ boolean notifyScrollMan(int index)
    {
      if (index >= 0)
      {
        LwComponent c = (LwComponent)get(index);
        Point p = LwToolkit.calcOrigin (c.getX() - dx, c.getY()- dy, c.getWidth(), c.getHeight(), dx, dy, this);
        if (p.x != dx || p.y != dy)
        {
          if (man != null) man.scrollObjMoved(p.x, p.y);
          else             setSOLocation(p.x, p.y);
          return true;
        }
      }
      return false;
    }

   /**
    * Returns the page size for the specified direction.
    * @param <code>d</code> the specified direction. Use <code>-1</code> value to specify bottom-up direction and
    * <code>1</code> value to specify up-bottom direction.
    * @return a page size.
    */
    protected /*C#virtual*/ int pageSize(int d)
    {
      int offset = controller.getOffset();
      if (offset >= 0)
      {
        Rectangle vp = getVisiblePart();
        int sum = 0, i = 0;
        for (i=offset; i>=0 && i<=controller.getMaxOffset() && sum<vp.height; i+=d)
          sum += get(i).getHeight();
        return i - offset - d;
      }
      return 0;
    }

    protected /*C#override*/ LwLayout getDefaultLayout() {
      return new LwListLayout();
    }

    private boolean clearSelection()
    {
      if (selectedIndex >= 0)
      {
        int prev = selectedIndex;
        selectedIndex = -1;
        return true;
      }
      return false;
    }

   private boolean hasInputFocus()  {
     return input != null && LwToolkit.isAncestorOf(input, LwToolkit.getFocusManager().getFocusOwner());
   }
}
