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
import org.zaval.misc.event.*;
import org.zaval.misc.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is list component that is supposed to be used as the list of the combobox component.
 * The component inherits the LwList functionality.
 */
public class LwComboList
extends LwList
implements LwMouseMotionListener
{
   public /*C#override*/ void posChanged(PosEvent e) {
     notifyScrollMan(getPosController().getOffset());
     repaint();
   }

   public /*C#override*/ boolean catchInput(LwComponent child)  {
     return true;
   }

   public /*C#override*/ void mousePressed(LwMouseEvent e) {}

   public /*C#override*/ void keyPressed(LwKeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) select(getPosController().getOffset());
     else                                     super.keyPressed(e);
   }

   public /*C#override*/ void mouseReleased(LwMouseEvent e) {
     if (LwToolkit.isActionMask(e.getMask()))
       select(getPosController().getOffset());
   }

   public /*C#override*/ boolean canHaveFocus() {
     return false;
   }

   public /*C#override*/ void paintOnTop (Graphics g) {
     super.paintOnTop (g);
     if (!hasFocus()) drawPosMarker(g);
   }

   public void startDragged(LwMouseMotionEvent e) {}
   public void endDragged  (LwMouseMotionEvent e) {}
   public void mouseDragged(LwMouseMotionEvent e) {}

   public void mouseMoved  (LwMouseMotionEvent e)  {
     updateMarker(e.getX(), e.getY());
   }

   public /*C#override*/ void mouseEntered(LwMouseEvent e) {
     updateMarker(e.getX(), e.getY());
   }

   private void updateMarker(int x, int y)
   {
     int index = LwToolkit.getDirectCompAt(x, y, this);
     if (index < 0) getPosController().clearPos();
     else           getPosController().setOffset(index);
   }
}



