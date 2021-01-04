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
 * This is button component, that is a composite component, so it is possible
 * to use any other component as the button label. The component has following
 * features:
 * <ul>
 *   <li>
 *     The class performs LwActionEvent when the button has been pressed. Use
 *     <code>addActionListener</code> and <code>removeActionListener</code> methods to catch
 *     the event.
 *   </li>
 *   <li>
 *     The component uses "button.off" view to show unpressed state and
 *     "button.on" view to show pressed state. The two keys are used to set appropriate
 *     view, the default view manager reads the view as a static object by <code>getView</code>
 *     static method of LwToolkit.class. It is possible to redefine the view using
 *     LwAdvViewMan as follow:
 *     <pre>
 *        ...
 *        LwButton     button = new LwButton("Button");
 *        LwAdvViewMan man    = new LwAdvViewMan();
 *        man.put("button.off", new LwImgRender("off.gif"));
 *        man.put("button.on",  new LwImgRender("on.gif"));
 *        button.setViewMan(man);
 *        ...
 *     </pre>
 *   </li>
 *   <li>
 *     As it has been described above the component is a composite. The sample below illustrates
 *     how an image with a label can be used as the button title:
 *     <pre>
 *       ...
 *       LwContainer buttonLabel = new LwPanel();
 *       buttonLabel.setLwLayout(new LwFlowLayout());
 *       buttonLabel.add(new LwImage("label.gif"));
 *       buttonLabel.add(new LwLabel("Button text"));
 *       LwButton button = new LwButton(buttonLabel);
 *       ...
 *     </pre>
 *   </li>
 * </ul>
 */
public class LwButton
extends LwActContainer
implements LwMouseListener, LwKeyListener, LwMouseMotionListener
{
  private boolean pressed;
  private LwActionSupport support;

 /**
  * Constructs a button component with no label.
  */
  public LwButton() {
    this((LwComponent)null);
  }

 /**
  * Constructs a button component with the specified label text.
  * In this case the component creates LwLabel component with the text and
  * uses it as the button label.
  * @param <code>lab</code> the specified label text.
  */
  public LwButton(String lab) {
    this(new LwLabel(lab));
  }

 /**
  * Constructs a button component with the specified component as a label.
  * @param <code>t</code> the specified component to be used as the button label.
  */
  public LwButton(LwComponent t) {
    super(t);
    setPressed(false);
  }

 /**
  * Adds the specified action listener to receive action events from this button.
  * @param <code>a</code> the specified action listener.
  */
  public void addActionListener(LwActionListener a) {
    if (support == null) support = new LwActionSupport();
    support.addListener(a);
  }

 /**
  * Removes the specified action listener so it no longer receives action events
  * from this button.
  * @param <code>a</code> the specified action listener.
  */
  public void removeActionListener(LwActionListener a) {
    if (support != null) support.removeListener(a);
  }

 /**
  * Fires the action event for list of LwActionListener.
  */
  protected /*C#virtual*/ void perform() {
    if (support != null) support.perform(new LwActionEvent(this));
  }

  public void mouseClicked(LwMouseEvent e) {}

  public void mousePressed(LwMouseEvent e)  {
    if (LwToolkit.isActionMask(e.getMask())) setPressed(true);
  }

  public void mouseReleased(LwMouseEvent e) {
    if (isPressed()) perform();
    setPressed(false);
  }

  public void keyPressed (LwKeyEvent e) {
    if (!isPressed() && LwToolkit.ACTION_KEY == LwToolkit.getKeyType(e.getKeyCode(),e.getMask())) setPressed(true);
  }

  public void keyReleased(LwKeyEvent e)
  {
    if (isPressed())
    {
      setPressed(false);
      perform();
    }
  }

  public void focusLost(LwFocusEvent e) {
    setPressed(false);
  }

  public void mouseEntered(LwMouseEvent e) {}

  public void mouseExited(LwMouseEvent e) {
    setPressed(false);
  }

  public void startDragged(LwMouseMotionEvent e) {}
  public void endDragged  (LwMouseMotionEvent e) {}
  public void mouseMoved  (LwMouseMotionEvent e) {}

  public void mouseDragged(LwMouseMotionEvent e)
  {
    int x = e.getX();
    int y = e.getY();
    setPressed(x>=0&&x<width&&y>=0&&y<=height);
  }

  public void keyTyped(LwKeyEvent e) {}

 /**
  * Invoked whenever the view manager has been set. The method is overrided with the class
  * to set appropriate ("button.off" or "button.on") view by the view manager.
  */
  protected /*C#override*/ void viewManChanged()
  {
    LwViewMan s = getViewMan(true);
    if (isPressed ()) s.setView("button.on");
    else              s.setView("button.off");
  }

 /**
  * Sets the specified state of the button. The button can have "pressed" or "unpressed" state.
  * @param <code>b</code> the specified state. The <code>true</code> value is used to
  * set the "pressed" state, the <code>false</code> value to set the "unpressed" state.
  */
  protected /*C#virtual*/ void setPressed (boolean b)
  {
    if (pressed == b && getViewMan(true).getView() != null) return;
    pressed = b;
    LwViewMan s = getViewMan(true);
    if (b) s.setView("button.on");
    else   s.setView("button.off");
    repaint();
  }

 /**
  * Gets the state of the button.
  * @return <code>true</code> if the button is "pressed", <code>false</code> if the button
  * is "unpressed".
  */
  public boolean isPressed () {
    return pressed;
  }
}
