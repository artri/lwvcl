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

/**
 * This class is an implementation of the LwFocusManager for the libarary.
 * According to the implementation the light weight component can be a focus owner
 * if the component <code>canHaveFocus</code> method returns <code>true</code> and
 * the component is enabled and visible.
 * <p>
 * The focus manager implements the mouse listener interface to deliver focus to a light
 * weight component. The manager controls (by implementing LwComponentListener,
 * LwContainerListener listeners interfaces) when the focus owner component is disabled,
 * hidden or removed from the parent component to take away the focus if it is necessary.
 * <p>
 * The manager implements keyboard listener interface to change focus owner when the "TAB"
 * key has been pressed.
 * <p>
 * The manager provides following methods that are used by other components of the
 * library:
 * <ul>
 *   <li>
 *     <code>requestFocus</code>. The method is called to try pass the focus to the specified
 *     component or some other component. The method tests if the specified component
 *     can have focus by the <code>isFocusable</code> method.
 *   </li>
 *   <li>
 *     <code>hasFocus</code>. The method is called to test if the component has the focus or not.
 *   </li>
 *   <li>
 *     <code>getFocusOwner</code>. The method returns a component which has the focus.
 *   </li>
 *   <li>
 *     <code>findFocusable</code>. The method looks for a component that can get the focus.
 *   </li>
 * </ul>
 */
public class LwFocusManager
implements LwManager, LwMouseListener, LwComponentListener, LwContainerListener, LwKeyListener
{
  private LwComponent focusOwner;

 /**
  * Requests the input focus for the specified component. The component can be the focus owner
  * if it is visible and enabled. If the focus owner has been changed than appropriate focus
  * lost and gained event will be performed and passed to LwEventManager.
  * @param <code>c</code> the specified component.
  */
  public void requestFocus(LwComponent c)
  {
    if (c != focusOwner && (c == null || isFocusable(c)))
    {
      LwComponent oldFocusOwner = focusOwner;
      if  (c != null)
      {
        LwComponent nf = LwToolkit.getEventManager().getEventDestination(c);
        if (nf == null || oldFocusOwner == nf) return;
        focusOwner = nf;
      }
      else focusOwner = c;

      if (oldFocusOwner != null)
      {
        LwToolkit.getEventManager().perform(new LwFocusEvent(oldFocusOwner, LwFocusEvent.FOCUS_LOST));
        oldFocusOwner.repaint();
      }

      if (focusOwner != null)
      {
        LwToolkit.getEventManager().perform(new LwFocusEvent(focusOwner, LwFocusEvent.FOCUS_GAINED));
        focusOwner.repaint();
      }
    }
  }

 /**
  * Looks for a component that can be the focus owner starting from the specified component.
  * @param <code>c</code> the specified starting component.
  * @return the component that can be the focus owner or <code>null</code> if the component has
  * not been found.
  */
  public LwComponent findFocusable(LwComponent c) {
    return (isFocusable(c)?c:fd(c, 0));
  }

 /**
  * Tests if the specified component has the focus.
  * @param <code>c</code> the specified component.
  * @return  <code>true</code> if the component is a focus owner; otherwise
  * <code>false</code>.
  */
  public boolean hasFocus(LwComponent c) {
    return focusOwner == c;
  }

 /**
  * Gets the focus owner component.
  * @return a focus owner component.
  */
  public LwComponent getFocusOwner() {
    return focusOwner;
  }

 /**
  * Invoked when the mouse has been pressed on a light weight component.
  * The manager uses the method to set the source component as the focus owner if
  * it is possible.
  * @param <code>e</code> the specified mouse event.
  */
  public void mousePressed(LwMouseEvent e) {
    if (LwToolkit.isActionMask(e.getMask()))
      requestFocus(e.getLwComponent());
  }

 /**
  * Invoked when the mouse has been clicked on a light weight component.
  * @param <code>e</code> the specified mouse event.
  */
  public void mouseClicked(LwMouseEvent e) {}

 /**
  * Invoked when the mouse enters a light weight component.
  * @param <code>e</code> the specified mouse event.
  */
  public void mouseEntered(LwMouseEvent e) {}

 /**
  * Invoked when the mouse exits a light weight component.
  * @param <code>e</code> the specified mouse event.
  */
  public void mouseExited(LwMouseEvent e) {}

 /**
  * Invoked when the mouse has been released on a light weight component.
  * @param <code>e</code> the specified mouse event.
  */
  public void mouseReleased(LwMouseEvent e) {}

 /**
  * Invoked when the light weight component has been disabled.
  * The method clears the focus owner if the owner is a source of the event.
  * @param <code>e</code> the specified component event.
  */
  public void compDisabled (LwComponentEvent e) { freeFocus(e.getLwComponent()); }

 /**
  * Invoked when the light weight component has been enabled.
  * @param <code>e</code> the specified component event.
  */
  public void compEnabled(LwComponentEvent e) { }

 /**
  * Invoked when the light weight component has been shown.
  * @param <code>e</code> the specified component event.
  */
  public void compShown(LwComponentEvent e) { }

 /**
  * Invoked when the light weight component has been hidden.
  * The method clears the focus owner if the owner is the source of the event.
  * @param <code>e</code> the specified component event.
  */
  public void compHidden(LwComponentEvent e) { freeFocus(e.getLwComponent()); }

 /**
  * Invoked when the light weight component has been added to the container.
  * @param <code>e</code> the specified container event.
  */
  public void compAdded(LwContainerEvent e) {}

 /**
  * Invoked when the light weight component has been removed from the container.
  * The method clears the focus owner if the owner is the source of the event.
  * @param <code>e</code> the specified container event.
  */
  public void compRemoved(LwContainerEvent e) { freeFocus(e.getLwComponent()); }

  public void keyPressed (LwKeyEvent e)
  {
    if (LwToolkit.PASSFOCUS_KEY == LwToolkit.getKeyType(e.getKeyCode(), e.getMask()))
    {
      LwComponent cc = ff (e.getLwComponent());
      if (cc != null) requestFocus(cc);
    }
  }

  public void keyReleased(LwKeyEvent e) {}
  public void keyTyped   (LwKeyEvent e) {}

  public void dispose() {}

 /**
  * Tests if the component can have the focus.
  * @param <code>c</code> the specified component to test.
  * @return <code>true</code> if the component can have the focus.
  */
  protected /*C#virtual*/ boolean isFocusable (LwComponent c) {
    return c.isEnabled() && c.canHaveFocus();
  }

  private void freeFocus(LwComponent target) {
    if (target == getFocusOwner()) requestFocus(null);
  }

  private static LwComponent fd(LwComponent target, int index)
  {
    if (target instanceof LwContainer)
    {
      LwContainer container   = (LwContainer)target;
      boolean     isComposite = target instanceof LwComposite;
      for (int i=index; i<container.count(); i++)
      {
        LwComponent cc = (LwComponent)container.get(i);
        if (cc.isEnabled() && cc.isVisible() && (!isComposite || !((LwComposite)container).catchInput(cc)))
        {
          if (cc.canHaveFocus()) return cc;
          cc = fd(cc, 0);
          if (cc != null) return cc;
        }
      }
    }
    return null;
  }

  private static LwComponent ff(LwComponent c)
  {
    LwComponent top = c;
    while (!(top instanceof LwLayer)) top = top.getLwParent();
    top = ((LwLayer)top).getFocusRoot();

    for (int index = 0;c != top.getLwParent();)
    {
      LwComponent cc = fd(c, index);
      if (cc != null) return cc;
      cc = c;
      c  = c.getLwParent();
      if (c != null) index = 1 + ((LwContainer)c).indexOf (cc);
    }
    return fd(top, 0);
  }
}

