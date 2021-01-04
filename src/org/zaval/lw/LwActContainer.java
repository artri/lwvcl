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

import org.zaval.misc.*;
import org.zaval.lw.event.*;

import java.awt.*;

/**
 * This class can be used for creating light weight containers that:
 * <ul>
 *   <li>Wants to have focus.</li>
 *   <li>
 *     Has a special child component that can be selected with rectangle frame if the container
 *     has focus (use <code>setAsFocusComponent</code>
 *     method to define the child component. The child is called focus indicator component.
 *   </li>
 * </ul>
 * <p>
 * This class is a composite component and the child components input events are caught
 * by this class.
 */
public class LwActContainer
extends LwPanel
implements LwComposite
{
  private Color        rectColor = Color.gray;
  private LwComponent  focusComponent;

 /**
  * Constructs the container with the specified child component. The child is used
  * to indicate whenever the component has focus.
  * @param <code>t</code> the specified child component.
  */
  public LwActContainer(LwComponent t)
  {
    if (t != null)
    {
      setInsets(4,4,4,4);
      add (t);
      setAsFocusComponent(0);
    }
  }

  public /*C#override*/ boolean canHaveFocus() {
    return true;
  }

 /**
  * Gets the child component that is used to indicate whenever the component has focus.
  * @return a child focus indicator component.
  */
  public LwComponent getFocusComponent() {
    return focusComponent;
  }

 /**
  * Sets a child focus indicator component by the specified child index. Use <code>-1</code> value
  * to assign <code>null</code> value to the focus indicator component.
  * @param <code>index</code> the specified child index to be set as the focus indicator.
  */
  public void setAsFocusComponent(int index) {
    focusComponent = (index >= 0)?(LwComponent)get(index):null;
  }

 /**
  * Sets the specified color to paint rectangle frame around the focus indicator component in case
  * if the container has set focus indicator component.
  * @param <code>c</code> the specified color.
  */
  public void setRectColor(Color c)
  {
    if (c != null && rectColor.equals(c)) return;
    rectColor = c;
    repaint();
  }

 /**
  * Gets the border color that is used to paint rectangle frame around the focus idicator
  * component.
  * @return a border color.
  */
  public Color getRectColor() {
    return rectColor;
  }

  public /*C#virtual*/ boolean catchInput(LwComponent child) {
    return true;
  }

  public /*C#override*/ void paintOnTop (Graphics g)
  {
    if (focusComponent != null && hasFocus())
    {
      g.setColor(rectColor);
      g.drawRect(focusComponent.getX(), focusComponent.getY(),
                 focusComponent.getWidth()-1, focusComponent.getHeight()-1);
    }
  }

  public /*C#override*/ void remove(int i) {
    if (get(i) == focusComponent) focusComponent = null;
    super.remove(i);
  }

  public /*C#override*/ void removeAll() {
    focusComponent = null;
    super.removeAll();
  }

  protected /*C#override*/ LwLayout getDefaultLayout ()  {
    return new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.HORIZONTAL, 2, 2);
  }
}
