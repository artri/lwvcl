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

/**
 * This is an implementation of MaskValidator interface. This basic mask validator that is
 * used by MaskedText as default if any other has not been set. The validator supports two
 * mask elements types:
 * <ul>
 *   <li>Letter type</li>
 *   <li>Numeric type </li>
 * </ul>
 * <p>
 * The mask can contains following letters:
 * <ul>
 *   <li>'a' - means any letter</li>
 *   <li>'A' - means any letter and in addition the mask validator will convert a letter to upper case</li>
 *   <li>'n' - means any numeric </li>
 *   <li>'x' - means any hexidecimal </li>
 *   <li>'#' - means any numeric sign ('+' or '-'). It is supposed that the mask letter is uses
 *       at the beginning of numeric</li>
 * </ul>
 * The first two mask letters belong to letter mask type and the rest belongs to numeric mask
 * type.
 * <p>
 * The table bellow shows different masks that can be combined using the validator:
 * <table border="1">
 *  <tr>
 *    <td align="Center">
 *        <b>Mask</b>
 *    </td>
 *    <td align="Center">
 *        <b>Shown Result (by LwMaskedTextField)</b>
 *    </td>
 *    <td align="Center">
 *        <b>Sample value</b>
 *    </td>
 *  </tr>
 *  <tr>
 *    <td align="Center">
 *         "nnnn-nnnn.nnn"
 *    </td>
 *    <td align="Center">
 *         "0000-0000.000"
 *    </td>
 *    <td align="Center">
 *         "1230-1020.220"
 *    </td>
 *  </tr>
 *  <tr>
 *    <td align="Center">
 *         "(Aaaaa nnnn)"
 *    </td>
 *    <td align="Center">
 *         "(_____ 0000)"
 *    </td>
 *    <td align="Center">
 *         "(Abcde 1211)"
 *    </td>
 *  <tr>
 *    <td align="Center">
 *         "#nnnn(digit)"
 *    </td>
 *    <td align="Center">
 *         "+0000(digit)"
 *    </td>
 *    <td align="Center">
 *         "-2331(digit)"
 *    </td>
 *  </tr>
 * </table>
 */
public class BasicMaskValidator
implements MaskValidator
{
 /**
  * This letter mask element type definition.
  */
  public static final int LETTER_TYPE  = 1;

 /**
  * This numeric mask element type definition.
  */
  public static final int NUMERIC_TYPE = 2;

  private static char LETTER_TAG   = 'a';
  private static char CLETTER_TAG  = 'A';
  private static char NUMERIC_TAG  = 'n';
  private static char HEX_TAG      = 'x';
  private static char SIGN_TAG     = '#';

  public /*C#virtual*/ boolean isHandledTag (char tag) {
    return tag == LETTER_TAG || tag == CLETTER_TAG || tag == NUMERIC_TAG || tag == SIGN_TAG || tag == HEX_TAG;
  }

  public /*C#virtual*/ char getBlankChar(char tag)
  {
    if (tag == LETTER_TAG || tag == CLETTER_TAG) return '_';
    else
    if (tag == NUMERIC_TAG || tag == HEX_TAG) return '0';
    else
    if (tag == SIGN_TAG) return '+';
    return tag;
  }

  public /*C#virtual*/ int getTypeByTag(char tag)
  {
    if (tag == LETTER_TAG || tag == CLETTER_TAG) return LETTER_TYPE;
    else
    if (tag == NUMERIC_TAG || tag == HEX_TAG || tag == SIGN_TAG) return NUMERIC_TYPE;
    return -1;
  }

  public /*C#virtual*/ boolean isValidValue (MaskElement e, String value)
  {
    switch (e.getType()) {
      case LETTER_TYPE : return isLetterType (e, value);
      case NUMERIC_TYPE: return isNumericType(e, value);
    }
    return false;
  }

  public /*C#virtual*/ String completeValue (MaskElement e, String newValue)
  {
    char[] buf = newValue.toCharArray();
    for (int i=0; i<buf.length; i++)
    {
      char tag = e.getTagAt(i);
      if (tag == CLETTER_TAG || tag == HEX_TAG) buf[i] = Character.toUpperCase(buf[i]);
      else
      if (tag == SIGN_TAG && buf[i] == getBlankChar(tag)) buf[i] = '+';
    }
    return new String (buf);
  }

  public /*C#virtual*/ Object getValueByElement (MaskElement e) {
    return e.getValue ();
  }

  private boolean isLetterType(MaskElement e, String value)
  {
    char[] buf = value.toCharArray ();
    for (int i=0; i<buf.length; i++)
    {
      char tag = e.getTagAt(i);
      if  (buf[i] != getBlankChar(tag) && !Character.isLetter(buf[i])) return false;
    }
    return true;
  }

  private boolean isNumericType(MaskElement e, String value)
  {
    char[] buf = value.toCharArray ();
    for (int i=0; i<buf.length; i++)
    {
      char tag = e.getTagAt(i);
      if ((buf[i] != getBlankChar(tag)) &&
          ((tag == SIGN_TAG && (buf[i] != '+' && buf[i] != '-')||
           (tag == NUMERIC_TAG && !Character.isDigit(buf[i]))||
           (tag == HEX_TAG && -1==Character.digit(buf[i],16)))   ))  /*java*/
/*C#       (tag == HEX_TAG &&  System.Uri.IsHexDigit(buf[i])))  ))*/


        return false;
    }
    return true;
  }
}




