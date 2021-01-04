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
import java.awt.*;
import java.util.*;

/**
 * This manager is used to support mouse cursor changing for a light weight
 * component. To support the mouse cursor changing there are two ways :
 * <ul>
 *   <li>
 *     The light weight component has to implement Cursorable interface.
 *     From the moment the manager will use the implementation to get current cursor type.
 *   </li>
 *   <li>
 *     The manager provides <code>setCursorType</code> method that allows to define the mouse
 *     cursorable interface that defines a cursor type for the component.
 *   </li>
 * </ul>
 */
public class LwCursorManager
implements LwManager, LwMouseListener, LwMouseMotionListener
{
  private Hashtable   cursors;
  private int         currCursorType = -1;

  public void dispose() {
    if (cursors != null) cursors.clear();
  }

  public void mouseEntered (LwMouseEvent e) {
     setCursorType(e.getLwComponent(), e.getX(), e.getY());
  }

  public void mouseExited(LwMouseEvent e){
    setCursorType(-1, e.getLwComponent());
  }

  public void endDragged(LwMouseMotionEvent e) {
    setCursorType(e.getLwComponent(), e.getX(), e.getY());
  }

  public void mouseMoved(LwMouseMotionEvent e) {
    setCursorType(e.getLwComponent(), e.getX(), e.getY());
  }

  public void startDragged(LwMouseMotionEvent e) {}

  public void mouseDragged (LwMouseMotionEvent e) {}
  public void mouseClicked (LwMouseEvent e) {}
  public void mousePressed (LwMouseEvent e) {}
  public void mouseReleased(LwMouseEvent e) {}

 /**
  * Sets the specified cursorable interface for the given component. To set the default cursor
  * type for the component use <code>null</code> as the cursorable interface. The interface
  * points the manager what cursor type should be set for the specified component.
  * @param <code>target</code> the component.
  * @param <code>c</code> the specified cursorable interface.
  */
  public void setCursorable (LwComponent target, Cursorable c)
  {
    if (cursors == null) cursors = new Hashtable();
    if (c == null) cursors.remove(target);
    else           cursors.put   (target, c);
  }

 /**
  * Gets the current cursorable interface for the given component.
  * @param <code>target</code> the given component.
  * @return a current cursor type.
  */
  public Cursorable getCursorable(LwComponent target) {
    return (cursors == null)?null:(Cursorable)cursors.get(target);
  }

 /**
  * Gets the current cursor type.
  * @return a current cursor type.
  */
  public int getCursorType () {
    return currCursorType;
  }

  private void setCursorType (LwComponent target, int x, int y)
  {
    int type = -1;
    if (cursors != null)
    {
      Cursorable c = (Cursorable)cursors.get(target);
      if (c != null) type = c.getCursorType(target, x, y);
    }

    if (type < 0 && target instanceof Cursorable)
      type = ((Cursorable)target).getCursorType(target, x, y);

    setCursorType (type, target);
  }

  private void setCursorType (int type, LwComponent target)
  {
    if (currCursorType != type)
    {
      LwDesktop d = LwToolkit.getDesktop(target);
      if (d != null)
      {
        currCursorType = type;
        if (currCursorType < 0) d.setProperty(LwRoot.CURSOR_PROPERTY, Cursor.getDefaultCursor());
        else                    d.setProperty(LwRoot.CURSOR_PROPERTY, new Cursor(currCursorType));
      }
    }
  }
}

