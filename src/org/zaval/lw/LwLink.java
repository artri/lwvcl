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
import org.zaval.lw.event.*;
import org.zaval.data.*;

/**
 * This component is something like text links that are used by "html".
 * The link component uses three link text colors to show it state:
 * <ul>
 *   <li>
 *      "highlightColor" is used to show link text whenever the mouse cursor is inside
 *       the component, but a mouse button has not been pressed.
 *   </li>
 *   <li>
 *      "pressingColor" is used to show link text when a mouse button has been pressed.
 *   </li>
 *   <li>
 *      "nonActiveColor" is used to show link text when the mouse pointer is outside the
 *      component and a mouse button has not been pressed.
 *   </li>
 * </ul>
 * <br>
 * <table border="0" >
 *   <tr>
 *     <td align="left">
 *        <b>Event performing.</b>
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       The class performs LwActionEvent when the link has been pressed. Use
 *       <code>addActionListener</code> and <code>removeActionListener</code> methods to catch
 *       the event.
 *     </td>
 *   </tr>
 * </table>
 */
public class LwLink
extends LwStButton
implements Cursorable
{
   private static final Font linkFont = new Font("Dialog", Font.ITALIC | Font.BOLD, 12);

   private Color highlightColor = Color.blue;
   private Color pressingColor  = LwToolkit.darkBlue;
   private Color nonActiveColor = Color.black;

  /**
   * Constructs a link component with the specified link text.
   * @param <code>s</code> the specified link text.
   */
   public LwLink(String s)
   {
     LwTextRender tr = new LwTextRender(s);
     tr.setFont (linkFont);
     getViewMan(true).setView(tr);
     setOpaque (false);
     sync();
   }


  /**
   * Sets the specified "highlightColor" of the link text. The color is used to show the link
   * text when the mouse cursor is inside the component, but a mouse button has not been
   * pressed.
   * @param <code>c</code> the specified color.
   */
   public void setHighlightColor(Color c)
   {
     if (!highlightColor.equals(c))
     {
       highlightColor = c;
       sync();
     }
   }

  /**
   * Sets the specified "pressingColor" of the link text. The color is used to show the link
   * text when a mouse button has been pressed.
   * @param <code>c</code> the specified color.
   */
   public void setPressingColor(Color c)
   {
     if (!pressingColor.equals(c))
     {
       pressingColor = c;
       sync();
     }
   }

  /**
   * Sets the specified "nonActiveColor" of the link text. The color is used to show the link
   * text when the mouse cursor is outside the component and a mouse button has not been
   * pressed.
   * @param <code>c</code> the specified color.
   */
   public void setNonActiveColor(Color c)
   {
     if (!nonActiveColor.equals(c))
     {
       nonActiveColor = c;
       sync();
     }
   }

  /**
   * Gets the cursor type. The method is an implementation of Cursorable interface and it
   * is used to define cursor type. The component returns java.awt.Cursor.HAND_CURSOR cursor type.
   * @param <code>target</code> the specified component.
   * @param <code>x</code> the specified x coordinate of the mouse cursor relatively the component.
   * @param <code>y</code> the specified y coordinate of the mouse cursor relatively the component.
   * @return a cursor type.
   */
   public int getCursorType(LwComponent target, int x, int y) {
     return Cursor.HAND_CURSOR;
   }

  /**
   * Invoked when the state of the component has been changed. The method is used to
   * listen state changing and setting appropriate link text color for the state.
   * @param <code>s</code> the new state of the component.
   * @param <code>ps</code> the previous state of the component.
   */
   protected /*C#override*/ void stateChanged(int s, int ps)
   {
     switch (s)
     {
       case ST_ONSURFACE : setForeground(highlightColor); break;
       case ST_PRESSED   : setForeground(pressingColor ); break;
       case ST_OUTSURFACE: setForeground(nonActiveColor); break;
     }
     repaint();
   }

   private void setForeground(Color c)
   {
     if (skins != null)
     {
       LwTextRender r = (LwTextRender)skins.getView();
       if (!r.getForeground().equals(c))
       {
         r.setForeground(c);
         repaint();
       }
     }
   }
}


