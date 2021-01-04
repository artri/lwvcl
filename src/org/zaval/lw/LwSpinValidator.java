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

import org.zaval.lw.mask.*;

/**
 * This class is a special mask handler that is used by the spin component to validate
 * and represent bound integer value. On the one hand it handles spin masked text field
 * input and on the other hand it defines mask that should be used by the mask validator,
 * converts the integer spin value to the mask value and converts the mask value to
 * the spin integer value.
 */
public class LwSpinValidator
extends BasicMaskValidator
{
  private int max = 10, min = -10;

  public /*C#override*/ boolean isValidValue(MaskElement e, String value)
  {
    if (e.getType () == NUMERIC_TYPE)
    {
      String s = value;
      if (value.length() > 0 && value.charAt (0) == '+') s = value.substring(1);
      try {
        int iv = Integer.parseInt (s);
        if (iv > max || iv < min) return false;
      }
      catch (NumberFormatException ee) {
        return false;
      }
    }
    return super.isValidValue(e, value);
  }

 /**
  * Gets the minimum value.
  * @return a minimum value.
  */
  public int getMinValue () {
    return min;
  }

 /**
  * Gets the maximum value.
  * @return a maximum value.
  */
  public int getMaxValue () {
    return max;
  }

 /**
  * Creates the mask that should be set for the mask validator.
  * @return a mask.
  */
  protected /*C#virtual*/ String createMask()
  {
    StringBuffer mask = new StringBuffer();
    if  (max < 0 || min < 0) mask.append('#');
    String s1 = Integer.toString (Math.abs(min));
    String s2 = Integer.toString (Math.abs(max));
    for (int i=0; i< Math.max(s1.length(), s2.length()); i++) mask.append ('n');
    return mask.toString();
  }

 /**
  * Creates the value by the specified integer value. The created value is acceptable
  * for the given mask.
  * @param <code>value</code> the specified integer value.
  * @param <code>mask</code> the specified mask.
  * @return a value is acceptable for the mask.
  */
  protected /*C#virtual*/ String createValue(int value, String mask)
  {
    boolean  hasSign = mask.indexOf('#') == 0;
    String res = Integer.toString (value);
    if (value < 0) res = res.substring (1);
    StringBuffer buf = new StringBuffer(res);
    for (int i=0; i < (mask.length() - res.length() - (hasSign?1:0)); i++) buf.insert(0, '0');
    if (value < 0) buf.insert(0, '-');
    else if (hasSign) buf.insert(0, '+');
    return buf.toString ();
  }

 /**
  * Fetches the integer value from the masked value.
  * @param <code>maskValue</code> the masked value.
  * @return an integer value.
  */
  protected /*C#virtual*/ int fetchValue(String maskValue) {
    if (maskValue.indexOf('+') == 0) maskValue = maskValue.substring(1);
    return Integer.parseInt(maskValue);
  }

  protected /*C#virtual*/ void setBound (int min, int max)
  {
    if (this.max != max || this.min != min)
    {
      this.max = max;
      this.min = min;
    }
  }
}
