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

public class LwMenuButton
extends LwStButton
{
   private boolean switched = false;

   public LwMenuButton(String s)
   {
      LwAdvViewMan am = new LwAdvViewMan();
      am.put("st.over", LwToolkit.getView ("br.raised"));
      am.put("st.pressed", LwToolkit.getView ("br.plain"));
      if (s != null)  am.setView (new LwTextRender(s));
      setViewMan(am);
      setInsets(0,4,0,4);
   }

   public void mousePressed(LwMouseEvent e)
   {
     if (LwToolkit.isActionMask(e.getMask()))
     {
       switched = !switched;
       super.mousePressed(e);
     }
   }

/*   public void switched(boolean b) {
     if (switched != b) switched = b;
   }*/

   protected LwView getView () {
     return skins.getBorder();
   }

   protected void setView (LwView v) {
     skins.setBorder(v);
   }

   protected void stateChanged(int state, int prev)
   {
     if (skins != null)
     {
       if (switched && state == ST_ONSURFACE)
       {
         state = ST_PRESSED;
         if (prev == ST_OUTSURFACE) perform (new LwActionEvent(this));
       }
       super.stateChanged(state, prev);
     }
   }
}


