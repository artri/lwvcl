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

import java.util.*;

/**
 * This is an implementation of MaskValidator interface and it implements date mask type.
 * <p>
 * The mask can contains following letters:
 * <ul>
 *   <li>'d' - means a day as a numeric</li>
 *   <li>'y' - means an year as a numeric</li>
 *   <li>'m' - means a month as a numeric</li>
 *   <li>'M' - means a month as a short abbreviation (Use <code>setShortMonths</code> method to define the abbreviations)</li>
 * </ul>
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
 *         "dd-mm-yyyy"
 *    </td>
 *    <td align="Center">
 *         "__-__-____"
 *    </td>
 *    <td align="Center">
 *         "22-12-1998"
 *    </td>
 *  </tr>
 *  <tr>
 *    <td align="Center">
 *         "dd/MMM/yyyy"
 *    </td>
 *    <td align="Center">
 *         "__/___/____"
 *    </td>
 *    <td align="Center">
 *         "18/Feb/2003"
 *    </td>
 * </table>
 */
public class DateMaskValidator
implements MaskValidator
{
 /**
  * This date mask element type definition.
  */
  public static final int DATE_TYPE = 1;

  private static char DAY_TAG    = 'd';
  private static char YEAR_TAG   = 'y';
  private static char MONTH_TAG  = 'm';
  private static char LMONTH_TAG = 'M';

  private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

 /**
  * Constructs the date mask validator.
  */
  public DateMaskValidator () {
  }

 /**
  * Sets the months abbreviations that is used for 'M' mask tag.
  * @param <code>months</code> the specified months abbreviations.
  */
  public void setShortMonths(String[] months) {
    this.months = months;
  }

  public char getBlankChar(char tagName) {
    return '_';
  }

  public int getTypeByTag(char tag) {
    return DATE_TYPE;
  }

  public boolean isHandledTag(char tagName) {
    return tagName == DAY_TAG || tagName == YEAR_TAG || tagName == MONTH_TAG || tagName == LMONTH_TAG;
  }

  public boolean isValidValue (MaskElement e, String value)
  {
    for (int i=0; i<value.length(); i++)
      if (!isHandledTag(e.getTagAt(i)) && value.charAt(i) != e.getTagAt(i)) return false;

    String day    = MaskElement.getValueByTag(e, 0, DAY_TAG, value);
    String month1 = MaskElement.getValueByTag(e, 0, MONTH_TAG, value);
    String month2 = MaskElement.getValueByTag(e, 0, LMONTH_TAG, value);
    String year   = MaskElement.getValueByTag(e, 0, YEAR_TAG, value);
    try
    {
      int iDay = -1, iMonth = -1, iYear = -1;
      if ((day    != null && ((iDay=Integer.parseInt  (day))   > 31   || iDay   <= 0))||
          (month1 != null && ((iMonth=Integer.parseInt(month1))> 12   || iMonth <= 0))||
          (year   != null && ((iYear=Integer.parseInt (year))  > 9999 || iYear  <= 0))||
          (month2 != null && getMonthByName(month2) <= 0))
        return false;
    }
    catch (NumberFormatException ee) { return false; }
    return true;
  }

  public String completeValue(MaskElement e, String value)
  {
    String tags = e.getTags();
    String m1 = MaskElement.getValueByTag(e, 0, MONTH_TAG, value);
    String m2 = MaskElement.getValueByTag(e, 0, LMONTH_TAG, value);

    if (m1 != null)
    {
      char   blank = getBlankChar(MONTH_TAG);
      char[] buf   = m1.toCharArray();
      if (buf[0] == blank) buf[0] = '0';
      if (buf[1] == blank) buf[1] = (buf[0] == '0')?'1':'0';
      m1 = new String(buf);
      value = MaskUtil.setValueByTag (MONTH_TAG, value, tags, m1);
    }

    if (m2 != null)
    {
      String s = null;
      if (m1 != null)
      {
        try {
          int im1 = Integer.parseInt (MaskUtil.trim(m1, getBlankChar(MONTH_TAG)));
          if (im1 > 0 && im1 < 13) s = months[im1 - 1];
        }
        catch (NumberFormatException ee) {}
      }
      if (s == null) s = getAppropriateMonth(m2, getBlankChar(LMONTH_TAG));
      if (s != null) value = MaskUtil.setValueByTag (LMONTH_TAG, value, tags, s);
    }

    String day = MaskElement.getValueByTag(e, 0, DAY_TAG, value);
    if (day != null)
    {
      char   blank = getBlankChar(DAY_TAG);
      char[] buf = day.toCharArray();
      if (buf[0] == blank) buf[0] = '0';
      if (buf[1] == blank) buf[1] = (buf[0] == '0')?'1':'0';
      value = MaskUtil.setValueByTag (DAY_TAG, value, tags, new String(buf));
    }

    String year = MaskElement.getValueByTag(e, 0, YEAR_TAG, value);
    if (year != null)
    {
      char   blank = getBlankChar(YEAR_TAG);
      char[] buf   = year.toCharArray();
      if (buf.length == 2)
      {
        if (buf[0] == blank) buf[0] = '9';
        if (buf[1] == blank) buf[1] = '1';
      }
      else
      if (buf.length == 4)
      {
        if (buf[0] == blank) buf[0] = '2';
        for (int i=1; i<buf.length; i++) if (buf[i] == blank) buf[i] = '0';
      }
      value = MaskUtil.setValueByTag (YEAR_TAG, value, tags, new String(buf));
    }
    return value;
  }

  private int getMonthByName(String name) {
    for (int i=0; i<months.length; i++) if (name.equalsIgnoreCase(months[i])) return i + 1;
    return -1;
  }

  private String getAppropriateMonth (String value, char blank)
  {
    char[]   buf = value.toCharArray();
    Vector   res = new Vector();
    for (int i=0; i<months.length; i++) res.addElement (months[i].toCharArray());

    for (int i=0; i<buf.length && buf[i] != blank; i++)
    {
      int size = res.size();
      Vector prev = (Vector)res.clone();
      for (int j = size-1; j >= 0; j--)
      {
        char[] m = (char[])res.elementAt(j);
        if (Character.toUpperCase(m[i]) != Character.toUpperCase(buf[i]))
          res.removeElementAt(j);
      }

      if (res.size() == 0)
      {
        res = prev;
        break;
      }
    }
    return res.size()>0?new String((char[])res.elementAt(0)):null;
  }
}



