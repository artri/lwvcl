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
import org.zaval.misc.*;

/**
 *  This class is "template" window component that can be used as the dialog window with the different
 *  content.
 */
public class LwBlankDialog
extends LwWindow
{
   private LwButton okButton, cancelButton;
   private LwComponent content;
   private boolean     isOkPressedValue;

  /**
   *  Constructs the window with the specified content component.
   *  @param <code>content</code> the specified content component.
   */
   public LwBlankDialog(LwComponent content)
   {
     this.content = content;

     LwContainer root = getRoot();
     root.setLwLayout(new LwBorderLayout());
     root.add(LwBorderLayout.CENTER, content);

     LwPanel buttonPanel = new LwPanel();
     buttonPanel.setInsets(4,0,0,0);
     buttonPanel.setLwLayout(new LwFlowLayout(Alignment.RIGHT, Alignment.CENTER, LwToolkit.HORIZONTAL, 4, 4));
     okButton = new LwButton((String)LwToolkit.getStaticObj("bt.ok"));
     cancelButton = new LwButton((String)LwToolkit.getStaticObj("bt.cancel"));
     int w = Math.max (okButton.getPreferredSize().width, cancelButton.getPreferredSize().width);
     okButton.setPSSize(w, -1);
     cancelButton.setPSSize(w, -1);
     okButton.addActionListener(this);
     cancelButton.addActionListener(this);
     buttonPanel.add(okButton);
     buttonPanel.add(cancelButton);
     root.add (LwBorderLayout.SOUTH, buttonPanel);
   }

  /**
   *  Tests if the "Ok" button has been pressed.
   *  @return <code>true</code> if the "Ok" button has been pressed.
   */
   public boolean isOkPressed() {
     return isOkPressedValue;
   }

  /**
   *  Gets the content component.
   *  @return a content component.
   */
   public LwComponent getContent() {
     return content;
   }

   public /*C#override*/ void actionPerformed(LwActionEvent e)
   {
     Object target = e.getSource();
     if (target == okButton || target == cancelButton)
     {
       isOkPressedValue = (target == okButton);
       LwLayer l = LwToolkit.getDesktop(this).getLayer(LwWinLayer.ID);
       l.remove(l.indexOf(this));
     }
     else super.actionPerformed(e);
   }
}
