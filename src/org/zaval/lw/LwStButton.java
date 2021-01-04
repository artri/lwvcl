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
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This is state button component. <b>First of all</b>, the component can have one of the following
 * states that can be got by <code>getState()</code> method:
 * <ul>
 *   <li>
 *     LwStButton.ST_ONSURFACE - indicates that the mouse cursor is inside of the component and
 *     a mouse button has not been pressed.
 *   </li>
 *   <li>
 *     LwStButton.ST_PRESSED - indicates that a mouse button has been pressed.
 *   </li>
 *   <li>
 *     LwStButton.ST_OUTSURFACE - indicates that the mouse cursor is outside of the component and
 *     a mouse button has not been pressed.
 *   </li>
 *   <li>
 *     LwStButton.ST_DISABLED - indicates that the component is disabled.
 *   </li>
 * </ul>
 * When the state of the component is changed <code>stateChanged</code> method
 * will be called.
 * <p>
 * <b>Second</b>, the component tries to use a view for appropriate state as follow:
 * <ul>
 *   <li>
 *     "st.over" view corresponds to LwStButton.ST_ONSURFACE state.
 *   </li>
 *   <li>
 *     "st.pressed" view corresponds to LwStButton.ST_PRESSED state.
 *   </li>
 *   <li>
 *     "st.out" view corresponds to LwStButton.ST_OUTSURFACE state.
 *   </li>
 *   <li>
 *     "st.disabled" view corresponds to LwStButton.ST_DISABLED state.
 *   </li>
 * </ul>
 * <p>
 * The component generates LwActionEvent whenever the button has been pressed or released.
 * Use <code>addActionListener</code> and <code>removeActionListener</code> methods
 * to handle the events. Use <code>fireByPress</code> method to define what event should
 * perform the action event: mouse released or mouse pressed event. Moreover, the component
 * allows to set action event repeatable time. In this case the action event will be performed
 * again if for example a mouse button is kept down certain time.
 * <p>
 * The sample below illustrates the component usage for creating tool bar button:
 * <pre>
 *    ...
 *    LwStButton   b = new LwStButton();
 *    LwAdvViewMan m = new LwAdvViewMan();
 *    m.put ("st.over", new LwImgRender("over.gif"));
 *    m.put ("st.out", new LwImgRender("out.gif"));
 *    m.put ("st.pressed", new LwImgRender("out.gif"));
 *    b.setViewMan (m);
 *    ...
 * </pre>
 */
public class LwStButton
extends LwCanvas
implements LwMouseListener, LwMouseMotionListener, Runnable
{
  /**
   * The ONSURFACE state.
   */
   public static final int ST_ONSURFACE = 1;

  /**
   * The PRESSED state.
   */
   public static final int ST_PRESSED = 2;

  /**
   * The OUTSURFACE state.
   */
   public static final int ST_OUTSURFACE = 3;

  /**
   * The DISABLED state.
   */
   public static final int ST_DISABLED = 4;

   private int state, time = 20;
   private LwActionSupport support;

  /**
   * Constructs the class instance.
   */
   public LwStButton() {
     setState(ST_OUTSURFACE);
   }

  /**
   * Tests if the action event will be perfomed by mouse pressed event.
   * @return <code>true</code> if the action event is perfomrd by mouse pressed event;
   * <code>false</code> if the action event is perfomrd by mouse released event;
   */
   public boolean isFireByPress() {
     return MathBox.checkBit(bits, EVENT_BY_PRESS);
   }

  /**
   * Sets the trigger event to generate action event by the component and the given
   * repeatable time.
   * @param <code>b</code> <code>true</code> to set mouse pressed event as the trigger,
   * <code>false</code> to set mouse released event as the trigger.
   * @param <code>time</code> the repeatable time. The time defines an interval after
   * the action event will be performed again if the trigger event is not switched off.
   * Use <code>-1</code> as the property value to disable the event repeating.
   */
   public void fireByPress(boolean b, int time) {
     bits      = MathBox.getBits(bits, EVENT_BY_PRESS, b);
     this.time = time;
   }

   public /*C#virtual*/ void mouseClicked (LwMouseEvent e) {}

   public /*C#virtual*/ void mouseEntered (LwMouseEvent e) {
     setState(ST_ONSURFACE);
   }

   public /*C#virtual*/ void mouseExited  (LwMouseEvent e) {
     if (isEnabled()) setState(ST_OUTSURFACE);
   }

   public /*C#virtual*/ void mousePressed (LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()))
     {
       setState(ST_PRESSED);
       if (isFireByPress())
       {
         perform (new LwActionEvent(this));
         if (time > 0) Timer.getTimer(false).add (this, 400, time);
       }
     }
   }

   public /*C#virtual*/ void mouseReleased(LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()) && getState() != ST_OUTSURFACE)
     {
       setState(ST_ONSURFACE);
       if (!isFireByPress()) perform (new LwActionEvent(this));
     }
     Timer.getTimer(false).remove (this);
   }

   public /*C#virtual*/ void startDragged(LwMouseMotionEvent e) {}
   public /*C#virtual*/ void endDragged  (LwMouseMotionEvent e) {}
   public /*C#virtual*/ void mouseMoved  (LwMouseMotionEvent e) {}

   public /*C#virtual*/ void mouseDragged(LwMouseMotionEvent e)
   {
     int x = e.getX();
     int y = e.getY();
     setState(x>=0 && x<width && y>=0 && y<=height?ST_PRESSED:ST_OUTSURFACE);
   }


  /**
   * Gets the current button state.
   * @return a current button state.
   */
   public int getState () {
     return state;
   }

  /**
   * Adds the specified action listener to receive action events from this component.
   * @param <code>a</code> the specified action listener.
   */
   public void addActionListener(LwActionListener a) {
     if (support == null) support = new LwActionSupport();
     support.addListener(a);
   }

  /**
   * Removes the specified action listener so it no longer receives action events
   * from this component.
   * @param <code>a</code> the specified action listener.
   */
   public void removeActionListener(LwActionListener a) {
     if (support != null) support.removeListener(a);
   }

   public /*C#override*/ void setEnabled (boolean b)
   {
     super.setEnabled(b);
     if (b) setState(ST_OUTSURFACE);
     else   setState(ST_DISABLED);
   }

  /**
   * Invoked whenever it is necessary to set appropriate state view for the current state.
   */
   public /*C#virtual*/ void sync() {
     stateChanged(getState(), getState());
   }

   public /*C#virtual*/ void run () {
     if (getState() == ST_PRESSED) perform(new LwActionEvent (this));
   }

   protected /*C#override*/ void viewManChanged() {
     sync();
   }

  /**
   * Sets the specified button state.
   * @param <code>s</code> the specified button state.
   */
   protected /*C#virtual*/ void setState (int s)
   {
     if (s != state)
     {
       int p = state;
       state = s;
       stateChanged(s, p);
     }
   }

  /**
   * Invoked when the state of the component has been changed. Use the method to
   * listen state changing.
   * @param <code>state</code> the new state of the component.
   * @param <code>prevState</code> the previous state of the component.
   */
   protected /*C#virtual*/ void stateChanged(int state, int prevState)
   {
     if (skins != null)
     {
       LwView n = null;
       switch (state)
       {
         case ST_ONSURFACE  :  n = skins.getView("st.over");     break;
         case ST_PRESSED    :  n = skins.getView("st.pressed");  break;
         case ST_OUTSURFACE :  n = skins.getView("st.out");      break;
         case ST_DISABLED   :  n = skins.getView("st.disabled"); break;
       }

       if (skins.getView() != n)
       {
         skins.setView(n);
         repaint();
       }
     }
   }

  /**
   * Fires the specified action event to the registered listeners.
   * @param <code>e</code> the specified action event.
   */
   protected /*C#virtual*/ void perform (LwActionEvent e) {
     if (support != null) support.perform (e);
   }

   private static final short EVENT_BY_PRESS = 64;
}


