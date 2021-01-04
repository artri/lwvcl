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
 * This class describes mask element. The mask element is bound with one or several mask tags
 * and represents some logical mask element, for example date. Any mask element has a type that
 * is defined by MaskValidator. The masked text field stores string mask as a set of mask elements.
 */
public class MaskElement
{
  protected char[] value, tags;
  protected int    size, type;

 /**
  * Gets the mask element value.
  * @return a mask element value.
  */
  public String getValue() {
    return value==null?null:new String (value);
  }

 /**
  * Gets the value for the specified tag name starting at the specified index.
  * @param <code>index</code> the index.
  * @param <code>tag</code> the tag name.
  * @return a value.
  */
  public String getValueByTag(int index, char tag) {
    return getValueByTag(this, index, tag, getValue());
  }

 /**
  * Gets the tags string. The string represents all tags for the mask element.
  * @return a tags string.
  */
  public String getTags() {
    return new String (tags);
  }

 /**
  * Gets the tag at the specified index.
  * @param <code>index</code> the specified index.
  * @return a tag.
  */
  public char getTagAt(int index) {
    return tags[index];
  }

 /**
  * Gets the mask element type.
  * @return a mask element type.
  */
  public int getType() {
    return type;
  }

 /**
  * Gets the mask element size.
  * @return a mask element size.
  */
  public int getSize () {
    return size;
  }

 /**
  * Fetches mask element value from the specified value string for the specified mask
  * element, the given tag at the specified index.
  * @param <code>e</code> the specified mask element.
  * @param <code>index</code> the specified index.
  * @param <code>tag</code> the specified tag.
  * @param <code>value</code> the specified value.
  * @return a value.
  */
  public static String getValueByTag(MaskElement e, int index, char tag, String value)
  {
    index = (new String(e.tags)).indexOf (tag, index);
    if (index >= 0)
    {
      int pi = index;
      while (index < e.tags.length && tag == e.tags[index]) index++;
      return value.substring(pi, index);
    }
    return null;
  }
}
