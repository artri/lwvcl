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
 * The class represents a switching GUI componenent that can have one of two states: "on" or "off".
 * The component is a container that consists of a label component and a box component.
 * It is possible to use any light weight component as the label or the box. The box component is
 * used to indicate a state of the component. The component supports two box view types:
 * <ul>
 *   <li>Radio</li>
 *   <li>Checkbox</li>
 * </ul>
 * Use appropriate constants to construct necessary checkbox or <code>setBoxType</code> method
 * to redefine the box view type.
 * <p>
 * It is possible to customize the box component by <code>setBox</code> method. The default box
 * component is a LwCanvas component. The checkbox component sets eight views for the component:
 * <ul>
 *   <li><b>check.on</b> The view is used to show checkbox "on" state.</li>
 *   <li><b>check.off</b> The view is used to show checkbox "off" state.</li>
 *   <li><b>check.dison</b> The view is used to show checkbox "disabled on" state.</li>
 *   <li><b>check.disoff</b> The view is used to show checkbox "disabled off" state.</li>
 *   <li><b>radio.on</b> The view is used to show radio "on" state.</li>
 *   <li><b>radio.off</b> The view is used to show radio "off" state.</li>
 *   <li><b>radio.dison</b> The view is used to show radio "disabled on" state.</li>
 *   <li><b>radio.disoff</b> The view is used to show radio "disabled off" state.</li>
 * </ul>
 * The sample below illustrates how the box view can be customized:
 * <pre>
 *   ...
 *   LwCheckbox   checkbox = new LwCheckbox("Checkbox");
 *   LwComponent  box = checkbox.getBox();
 *   LwAdvViewMan man = new LwAdvViewMan();
 *   man.put("check.on", LwImgRender("chOn.gif"));
 *   man.put("check.off", LwImgRender("chOff.gif"));
 *   box.setViewMan(man);
 *   ...
 * </pre>
 * <p>
 * The component uses LwSwitchManager class to control state of the component. Use
 * <code>setSwitchManager</code> method to define the switch manager and
 * <code>getSwitchManager</code> to get the current switch manager. Use the switch
 * manager to listen action events that are performed whenever the state of the
 * component has been changed.
 */
public class LwCheckbox
extends LwActContainer
implements LwMouseListener, LwKeyListener, Switchable
{
 /**
  * The radio box type.
  */
  public static final int RADIO = 1;

 /**
  * The checkbox box type.
  */
  public static final int CHECK = 2;

  private LwSwitchManager manager;
  private LwComponent box;
  private int type;

 /**
  * Constructs a checkbox component with no label.
  */
  public LwCheckbox() {
    this(null);
  }

 /**
  * Constructs a checkbox component with the specified label text.
  * In this case the component creates appropriate LwLabel component with the text and
  * uses it as the checkbox label.
  * @param <code>lab</code> the specified label text.
  */
  public LwCheckbox(String lab) {
    this (lab, CHECK);
  }

 /**
  * Constructs a checkbox component with the specified label text and the given box view type.
  * The component creates appropriate LwLabel component with the text and
  * uses it as the checkbox label. It is necessary to use RADIO or CHECK as the box view type.
  * @param <code>lab</code> the specified label text.
  * @param <code>type</code> the box view type.
  */
  public LwCheckbox(String lab, int type) {
    this (lab != null?new LwLabel(lab):(LwComponent)null, type);
  }

 /**
  * Constructs a checkbox component with the specified component as a label and the given
  * box view type. The component uses the component as the checkbox label.
  * It is possible to use RADIO or CHECK as the box view type.
  * @param <code>c</code> the specified component to be used as the checkbox label.
  * @param <code>type</code> the box view type.
  */
  public LwCheckbox(LwComponent c, int type)
  {
    super(c);
    setBox(new LwCanvas());
    setSwitchManager(new LwSwitchManImpl());
    setBoxType (type);
  }

 /**
  * Sets the specified box view type. It is possible to use RADIO or CHECK as the box view type.
  * @param <code>t</code> the specified box view type.
  */
  public void setBoxType (int t)
  {
    if (t != RADIO && t != CHECK) throw new IllegalArgumentException();
    if (t != type)
    {
      type = t;
      vrp();
    }
  }

 /**
  * Gets the box view type.
  * @return a box view type.
  */
  public int getBoxType () {
    return type;
  }

 /**
  * Sets a child component as the box component by the specified child index. The box
  * component indicates the current state of the checkbox.
  * @param <code>i</code> the specified child index.
  */
  public void setBox(int i) {
    setBox((LwComponent)get(i));
  }

 /**
  * Sets the specified component as the box component. The box component indicates the current
  * state of the checkbox. The new box component will be inserted as a child of the component
  * and an old box component will be removed from the child list.
  * @param <code>b</code> the specified component.
  */
  public void setBox(LwComponent b)
  {
    if (box != b)
    {
      int i = 0;
      if (box != null)
      {
        i = indexOf(box);
        remove(i);
      }
      box = b;
      if (box != null) insert(i, box);
    }
  }

 /**
  * Gets the component that is used as a the box component with the component.
  * @return a box component.
  */
  public LwComponent getBox() {
    return box;
  }

 /**
  * Sets the specified switch manager to control state of the component.
  * @param <code>m</code> the specified switch manager. It is impossible to use <code>null</code>
  * value as the switch manager, in this case IllegalArgumentException will be thrown.
  */
  public void setSwitchManager(LwSwitchManager m)
  {
    if (m == null) throw new IllegalArgumentException ();
    if (manager != m)
    {
      if (manager != null) manager.uninstall(this);
      manager = m;
      manager.install(this);
    }
  }

 /**
  * Gets the switch manager that is used with the checkbox.
  * @return a switch manager
  */
  public LwSwitchManager getSwitchManager() {
    return manager;
  }

 /**
  * Sets the specified state of the checkbox. The method calls appropriate method of
  * the checkbox switch manager.
  * @param <code>b</code> the specified state. Use <code>true</code> to set "on" state and
  * <code>false</code> to set "off" state.
  */
  public void setState(boolean b) {
    manager.setState(this, b);
  }

 /**
  * Gets the state of the checkbox. The method calls appropriate method of
  * the checkbox switch manager.
  * @return a state of the checkbox.
  */
  public boolean getState() {
    return manager.getState(this);
  }

 /**
  * Invoked when the state of a switching component has been changed to "on" with
  * the switch manager.
  */
  public void switchedOn() {
    sync ();
  }

 /**
  * Invoked when the state of a switching component has been changed to "off" with
  * the switch manager.
  */
  public void switchedOff() {
    sync ();
  }

 /**
  * Invoked whenever it is necessary to set appropriate box view for the current state.
  */
  public void sync ()
  {
    String prefix = (type == CHECK)?"check.":"radio.";
    if (isEnabled())
    {
      if (getState()) box.getViewMan(true).setView(prefix + "on");
      else            box.getViewMan(true).setView(prefix + "off");
    }
    else
    {
      if (getState()) box.getViewMan(true).setView(prefix + "dison");
      else            box.getViewMan(true).setView(prefix + "disoff");
    }
    repaint();
  }

  public /*C#override*/ void setEnabled (boolean b) {
    super.setEnabled(b);
    sync();
  }

  public void mouseClicked (LwMouseEvent e) {}
  public void mouseReleased(LwMouseEvent e) {}

  public void mousePressed (LwMouseEvent e) {
    if (LwToolkit.isActionMask(e.getMask())) setState(!getState());
  }

  public void keyPressed (LwKeyEvent e) {
    if (LwToolkit.ACTION_KEY == LwToolkit.getKeyType(e.getKeyCode(), e.getMask())) setState(!getState());
  }

  public /*C#override*/ void remove(int i) {
    if (get(i) == box)  box = null;
    super.remove(i);
  }

  public /*C#override*/ void removeAll() {
    box = null;
    super.removeAll();
  }

  public void mouseEntered (LwMouseEvent e) {}
  public void mouseExited  (LwMouseEvent e) {}
  public void keyReleased  (LwKeyEvent e) {}
  public void keyTyped     (LwKeyEvent e) {}

  protected /*C#override*/ void recalc ()
  {
    sync();
    super.recalc();
  }
}

