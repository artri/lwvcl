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

import java.util.*;

/**
 * The class extends the basic view manager fuctionality. It provides ability to bind a view
 * with a given name. The view manager can be used to change standard view faces for lightweight
 * components. For example, for LwButton component using the view manager it is possible to
 * re-bind "button.off" and "button.on" face views to some custom views, by <code>put</code>
 * method of the view manager. The sample below illustrates the usage:
 * <pre>
 *   ...
 *   LwButton button = new LwButton("Ok");
 *   LwAdvViewMan man = new LwAdvViewMan();
 *   man.put("button.off", LwImgRender("off.gif"));
 *   man.put("button.on", LwImgRender("on.gif"));
 *   button.setViewMan(man);
 *   ...
 * </pre>
 * In the sample above the "off.gif" image will be used when the button is unpressed and
 * the "on.gif" image when the button is pressed.
 */
public class LwAdvViewMan
extends LwViewMan
{
  private Hashtable skins;

 /**
  * Binds the specified name with the given view.
  * @param <code>id</code> the name to bind with the view.
  * @param <code>s</code>  the view to be bound.
  */
  public void put(String id, LwView s)
  {
    if (skins == null) skins = new Hashtable();
    skins.put(id, s);
    invalidate();
  }

 /**
  * Removes the specified binding.
  * @param <code>id</code> the name to remove the view binding.
  */
  public void remove(String id)
  {
    if (skins == null || skins.get(id) == null) return;
    skins.remove(id);
    if (skins.size()== 0) skins = null;
    invalidate();
  }

 /**
  * The method is overrided to return appropriate view by the specified name basing on
  * the binding hash table. The table content is defined with <code>put</code> and
  * <code>remove</code> methods of the view manager.
  * @param <code>key</code> the name of the view.
  * @return a view.
  */
  protected /*C#override*/ LwView getView(String key)
  {
    LwView s = null;
    if (skins != null) s = (LwView)skins.get(key);
    return (s == null)?super.getView(key):s;
  }

 /**
  * The method is overrided to return all views that have been stored in the binding
  * hash table. The returned array is used to calculate preferred size and insets
  * basing on the view set.
  * @return an array of views.
  */
  protected /*C#override*/ LwView[] getAllViews()
  {
    LwView[] res = super.getAllViews();
    if (skins != null)
    {
      LwView[] nres = new LwView[res.length + skins.size()];
      System.arraycopy(res, 0, nres, 0, res.length);
      Enumeration e = skins.elements();
      for (int i=res.length; i<nres.length; i++) nres[i] = (LwView)e.nextElement();
      return nres;
    }
    return res;
  }
}
