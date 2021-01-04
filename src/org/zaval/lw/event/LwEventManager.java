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
package org.zaval.lw.event;

import org.zaval.lw.*;
import java.util.*;
import org.zaval.util.event.*;

/**
 * This is one of the core classes of the light weight library that is used to define
 * light weight events delivering strategy. Actually all light weight components events
 * at first are passed to the manager and after that the manager decides how the events
 * should be delivered.
 * <p>
 * The manager provides listeners supporting for following events types:
 * <ul>
 *   <li>
 *     Light weight component events listeners supporting.
 *     Use <code>addComponentListener</code> and <code>removeComponentListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight container events listeners supporting.
 *     Use <code>addContainerListener</code> and <code>removeContainerListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight mouse events listeners supporting.
 *     Use <code>addMouseListener</code> and <code>removeMouseListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight mouse motion events listeners supporting.
 *     Use <code>addMouseMotionListener</code> and <code>removeMouseMotionListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight key events listeners supporting.
 *     Use <code>addKeyListener</code> and <code>removeKeyListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight focus events listeners supporting.
 *     Use <code>addFocusListener</code> and <code>removeFocusListener</code> methods.
 *   </li>
 *   <li>
 *     Light weight wundow events listeners supporting.
 *     Use <code>addWinListener</code> and <code>removeWinListener</code> methods.
 *   </li>
 * </ul>
 * <p>
 * The manager provides universal methods to register and unregister light weight listeners,
 * the method checks if the object implements one of the events listeners and calls
 * appropriate method (see list above) to register or unregister appropriate listeners.
 * Use <code>addXXXListener</code> and <code>removeXXXListener</code> for the purposes.
 */
public class LwEventManager
implements LwManager
{
  private LwMouseSupport       mouse;
  private LwMouseMotionSupport mouseMotion;
  private LwComponentSupport   component;
  private LwContainerSupport   container;
  private LwKeySupport         keyboard;
  private LwFocusSupport       focus;
  private LwWinSupport         window;

 /**
  * Adds the specified events listener. The method checks what light weight listeneres interfaces
  * are implemented with the listener and adds the events listeners as appropriate light weight
  * events listeners.
  * @param <code>l</code> the specified events listener.
  */
  public void addXXXListener (EventListener l)
  {
    if (l instanceof LwComponentListener)  addComponentListener  ((LwComponentListener)l);
    if (l instanceof LwContainerListener)  addContainerListener  ((LwContainerListener)l);
    if (l instanceof LwMouseListener) 	   addMouseListener      ((LwMouseListener)l);
    if (l instanceof LwMouseMotionListener)addMouseMotionListener((LwMouseMotionListener)l);
    if (l instanceof LwKeyListener)        addKeyListener        ((LwKeyListener)l);
    if (l instanceof LwFocusListener)	   addFocusListener      ((LwFocusListener)l);
    if (l instanceof LwWinListener)	   addWinListener        ((LwWinListener)l);
  }

 /**
  * Removes the specified events listener. The method checks what light weight listeneres interfaces
  * are implemented with the listener and removes the events listeners from the appropriate listners
  * lists.
  * @param <code>l</code> the specified events listener.
  */
  public void removeXXXListener (EventListener l)
  {
    if (l instanceof LwComponentListener)   removeComponentListener  ((LwComponentListener)l);
    if (l instanceof LwContainerListener)   removeContainerListener  ((LwContainerListener)l);
    if (l instanceof LwMouseListener)       removeMouseListener      ((LwMouseListener)l);
    if (l instanceof LwMouseMotionListener) removeMouseMotionListener((LwMouseMotionListener)l);
    if (l instanceof LwKeyListener)         removeKeyListener        ((LwKeyListener)l);
    if (l instanceof LwFocusListener)       removeFocusListener      ((LwFocusListener)l);
    if (l instanceof LwWinListener)         removeWinListener        ((LwWinListener)l);
  }

 /**
  * Adds the specified component events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addComponentListener(LwComponentListener l) {
    if (component == null) component = new LwComponentSupport();
    component.addListener(l);
  }

 /**
  * Removes the specified component events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeComponentListener(LwComponentListener l) {
    if (component != null) component.removeListener(l);
  }

 /**
  * Adds the specified container events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addContainerListener(LwContainerListener l) {
    if (container == null) container = new LwContainerSupport();
    container.addListener(l);
  }

 /**
  * Removes the specified container events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeContainerListener(LwContainerListener l) {
    if (container != null) container.removeListener(l);
  }

 /**
  * Adds the specified mouse events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addMouseListener(LwMouseListener l) {
    if (mouse == null) mouse = new LwMouseSupport();
    mouse.addListener(l);
  }

 /**
  * Removes the specified mouse events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeMouseListener(LwMouseListener l) {
    if (mouse != null) mouse.removeListener(l);
  }

 /**
  * Adds the specified mouse motion events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addMouseMotionListener(LwMouseMotionListener l) {
    if (mouseMotion == null) mouseMotion = new LwMouseMotionSupport();
    mouseMotion.addListener(l);
  }

 /**
  * Removes the specified mouse motion events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeMouseMotionListener(LwMouseMotionListener l) {
    if (mouseMotion != null) mouseMotion.removeListener(l);
  }

 /**
  * Adds the specified focus events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addFocusListener(LwFocusListener l) {
    if (focus == null) focus = new LwFocusSupport();
    focus.addListener(l);
  }

 /**
  * Removes the specified focus events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeFocusListener(LwFocusListener l) {
    if (focus != null) focus.removeListener(l);
  }

 /**
  * Adds the specified key events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addKeyListener(LwKeyListener l) {
    if (keyboard == null) keyboard = new LwKeySupport();
    keyboard.addListener(l);
  }

 /**
  * Removes the specified key events listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void removeKeyListener(LwKeyListener l) {
    if (keyboard != null) keyboard.removeListener(l);
  }

 /**
  * Removes the specified window listener.
  * @param <code>l</code> the specified window listener.
  */
  public synchronized void removeWinListener(LwWinListener l) {
    if (window != null) window.removeListener(l);
  }

 /**
  * Adds the specified window listener.
  * @param <code>l</code> the specified events listener.
  */
  public synchronized void addWinListener(LwWinListener l) {
    if (window == null) window = new LwWinSupport();
    window.addListener(l);
  }

  public void dispose()
  {
    if (mouse != null) mouse.clear();
    if (mouseMotion != null) mouseMotion.clear();
    if (component != null) component.clear();
    if (container != null) container.clear();
    if (keyboard != null) keyboard.clear();
    if (focus != null) focus.clear();
    if (window != null) window.clear();
  }

 /**
  * Performs the specified light weight event. The method is used to distribute
  * the event according the manager functionality and it is utilized with different
  * parts of the library.
  * @param <code>e</code> the specified light weight event.
  */
  public void perform(LwAWTEvent e)
  {
    LwComponent target = e.getLwComponent();
    switch (e.getUID())
    {
      case LwAWTEvent.MOTION_UID:
      {
        if (mouseMotion != null) mouseMotion.perform (e);
        if (target instanceof LwMouseMotionListener)
          LwMouseMotionSupport.process((LwMouseMotionListener)target, (LwMouseMotionEvent)e);
        return;
      }
      case LwAWTEvent.MOUSE_UID:
      {
        if (mouse != null) mouse.perform (e);
        if (target instanceof LwMouseListener)
          LwMouseSupport.process((LwMouseListener)target, (LwMouseEvent)e);
      } break;
      case LwAWTEvent.COMP_UID:
      {
        if (component != null) component.perform (e);
        if (target instanceof LwComponentListener)
          LwComponentSupport.process((LwComponentListener)target, (LwComponentEvent)e);
      } break;
      case LwAWTEvent.CONT_UID:
      {
        if (container != null) container.perform (e);
        if (target instanceof LwContainerListener)
          LwContainerSupport.process((LwContainerListener)target, (LwContainerEvent)e);
      } break;
      case LwAWTEvent.KEY_UID:
      {
        if (keyboard != null) keyboard.perform (e);
        if (target instanceof LwKeyListener)
          LwKeySupport.process((LwKeyListener)target, (LwKeyEvent)e);
      } break;
      case LwAWTEvent.FOCUS_UID:
      {
        if (focus != null) focus.perform (e);
        if (target instanceof LwFocusListener)
          LwFocusSupport.process((LwFocusListener)target, (LwFocusEvent)e);
      } break;
      case LwAWTEvent.WIN_UID:
      {
        if (window != null) window.perform (e);
        if (target instanceof LwWinListener)
          LwWinSupport.process((LwWinListener)target, (LwWinEvent)e);
      } break;
    }

    for (target = target.getLwParent();target != null;target = target.getLwParent())
    {
      if (target instanceof LwChildrenListener)
        ((LwChildrenListener)target).childPerformed(e);
    }
  }

 /**
  * Gets the event destination. The method looks for composite parent of the specified target
  * component and defines if the parent should be the event destination
  * @param <code>target</code> the specified target component.
  * @return an event destination.
  */
  public /*C#virtual*/ LwComponent getEventDestination(LwComponent target) {
    LwComponent composite = findComposite(target);
    return composite == null?target:composite;
  }

  /*C#private static LwComponent findComposite (LwComponent target) {*/
  private static final LwComponent findComposite (LwComponent target) { /*java*/
    return findComposite (target, target);
  }

  /*C#private static LwComponent findComposite (LwComponent target, LwComponent child)*/
  private static final LwComponent findComposite (LwComponent target, LwComponent child) /*java*/
  {
    if (target == null) return null;
    LwComponent parent = target.getLwParent();

    boolean b = (parent instanceof LwComposite);
    LwComponent res = findComposite (parent, b?parent:child);
    if (res != null) return res;

    if (b && ((LwComposite)parent).catchInput(child)) return parent;
    return null;
  }
}

