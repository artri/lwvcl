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
package org.zaval.lw.grid;

import org.zaval.lw.*;
import org.zaval.lw.event.*;

/**
 * This class is very simple implementation of <code>LwEditorProvider</code> interface where
 * a grid cells are edited with a text field component.
 */
public class LwDefEditors
implements LwEditorProvider
{
 /**
  * Gets the editor component for the specified cell and the given data model value.
  * The method can return <code>null</code> if the cell has not an appropriate editor
  * component. In this case the editing process will not be initiated.
  * The implementation returns single line text field component as the cell
  * editor with the fixed size (150 characters).
  * @param <code>row</code> the specified cell row.
  * @param <code>col</code> the specified cell column.
  * @param <code>o</code> the specified data model value for the grid cell.
  * @return an editor component.
  */
  public /*C#virtual*/ LwComponent getEditor(int row, int col, Object o)  {
    return new LwTextField((o==null?"":(o.toString())), 150);
  }

 /**
  * Fetches the value from the editor component of the specified cell. The returned
  * value will be used to update appropriate grid data model value. The implementation
  * expects that the editor component is LwTextField component and returns a string value
  * (that is fetched from the text model of the component) as the result.
  * @param <code>row</code> the specified cell row.
  * @param <code>col</code> the specified cell column.
  * @param <code>editor</code> the specified component editor that has been used to edit the
  * cell value.
  * @return a value to update the grid data model.
  */
  public /*C#virtual*/ Object fetchEditedValue (int row, int col, LwComponent editor) {
    return ((LwTextField)editor).getTextModel().getText();
  }

 /**
  * Tests if the specified event should perform editing process for the specified cell.
  * The implementation indicates that editing process can be started whenever mouse
  * pressed event has occured and a mouse button has been clicked twice.
  * @param <code>row</code> the specified cell row.
  * @param <code>col</code> the specified cell column.
  * @param <code>e</code> the specified event.
  * @return <code>true</code> if the editing process should be performed; otherwise
  * <code>false</code>.
  */
  public /*C#virtual*/ boolean shouldStartEdit (int row, int col, LwAWTEvent e) {
    return e.getID() == LwMouseEvent.MOUSE_PRESSED && ((LwMouseEvent)e).getClickCount()==2;
  }
}

