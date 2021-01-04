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

import org.zaval.data.*;
import java.awt.*;

/**
 * This light weight component is used to show a text by using LwTextRender as the face view.
 */
public class LwLabel
extends LwCanvas
{
 /**
  * Constructs the componen. The text string is set to "".
  */
  public LwLabel() {
    this("");
  }

 /**
  * Constructs the component with the specified string.
  * @param <code>t</code> the specified string.
  */
  public LwLabel(String t) {
    this (new SingleLineTxt(t));
  }

 /**
  * Constructs the component with the specified text model.
  * @param <code>t</code> the specified text model.
  */
  public LwLabel(TextModel t)  {
    getViewMan(true).setView(makeTextRender(t));
    setInsets(1,1,1,1);
  }

 /**
  * Constructs the component with the specified text render.
  * @param <code>r</code> the specified text render.
  */
  public LwLabel(LwTextRender r)  {
    getViewMan(true).setView(r);
    setInsets(1,1,1,1);
  }


 /**
  * Gets the face view as a LwTextReneder instance.
  * @return a text render instance.
  */
  public LwTextRender getTextRender() {
    return (LwTextRender)getViewMan(false).getView();
  }

 /**
  * Gets the text that is shown with the class as a string.
  * @return a string.
  */
  public String getText () {
    return getTextRender().getText();
  }

 /**
  * Gets the text model that is shown with the class. The text model is represented with
  * org.zaval.data.TextModel interface.
  * @return a text model.
  */
  public TextModel getTextModel () {
    return getTextRender().getTextModel();
  }

 /**
  * Sets the specified string to be shown with the class.
  * @param <code>s</code> the specified string.
  */
  public /*C#virtual*/ void setText (String s) {
    getTextRender().getTextModel().setText(s);
    repaint();
  }

 /**
  * Sets the text color. The method uses appropriate method of the text render
  * that is used as the face view.
  * @param <code>c</code> the specified text color.
  */
  public void setForeground(Color c)
  {
    if (!getForeground().equals(c))
    {
      getTextRender().setForeground(c);
      repaint();
    }
  }

 /**
  * Gets the text color.
  * @return a text color.
  */
  public Color getForeground() {
    return getTextRender().getForeground();
  }

 /**
  * The method gets default text render that will be used to render the
  * specified text model. The render is set as the face view for the component
  * during the component initialization.
  * @param <code>t</code> the specified text model.
  * @return a text render that is going to be used as the face view of the component.
  */
  protected /*C#virtual*/ LwTextRender makeTextRender(TextModel t) {
    return new LwTextRender(t);
  }
}
