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
package org.zaval.lw.mask;

import org.zaval.lw.*;

/**
 * This is masked text field lightweight component that is supposed to be used for masked input.
 * The component uses masked text (MaskedText) as the text model and special pos
 * controller (MaskPosController) to navigate through the mask. Don't redefine the properties,
 * since it can result in incorrected work of the component.
 */

public class LwMaskTextField
extends LwTextField
{
 /**
  * Constructs the class with empty text and empty mask.
  */
  public LwMaskTextField() {
    this ("", "");
  }

 /**
  * Constructs the class with the specified text and the given mask.
  * @param <code>text</code> the specified text.
  * @param <code>mask</code> the specified mask.
  */
  public LwMaskTextField(String text, String mask) {
    this(new MaskedText(mask));
    setText(text);
  }

 /**
  * Constructs the class with the specified masked text model.
  * @param <code>t</code> specified masked text model.
  */
  public LwMaskTextField(MaskedText t) {
    super (t);
    setPosController (new MaskPosController(t));
  }

 /**
  * Sets the mask validator.
  * @param <code>v</code> the mask validator.
  */
  public void setValidator (MaskValidator v) {
   ((MaskedText)getTextModel()).setValidator(v);
    repaint();
  }

 /**
  * Gets the mask validator.
  * @return a mask validator.
  */
  public MaskValidator getValidator () {
    return ((MaskedText)getTextModel()).getValidator();
  }


 /**
  * Gets the mask.
  * @return a mask.
  */
  public String getMask () {
    return ((MaskedText)getTextModel()).getMask();
  }

 /**
  * Sets the mask.
  * @param <code>mask</code> the mask.
  */
  public void setMask (String mask)
  {
    MaskedText mt = (MaskedText)getTextModel();
    String     m  = mt.getMask();
    if (m == null || !m.equals (mask))
    {
      mt.setMask (mask);
      repaint();
    }
  }

  protected /*C#override*/ void write(int pos, String s)
  {
    MaskedText mt = (MaskedText)getTextModel();
    pos = mt.findHandledTag(pos, 1);
    getPosController().setOffset(pos);
    super.write(pos, s);
  }
}


