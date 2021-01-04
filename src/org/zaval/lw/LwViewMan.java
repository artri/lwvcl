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
import org.zaval.util.*;
import org.zaval.util.event.*;

/**
 * This class is used to support set of views. Actually the view
 * manager is a views container that provides following abilities:
 * <ul>
 *   <li>
 *     Allows to determine border, face and background views for a given owner component.
 *   </li>
 *   <li>
 *     Calculates the common preferred size of the set.
 *   </li>
 *   <li>
 *     Calculates the common insets of the set.
 *   </li>
 *   <li>
 *     Validates the views.
 *   </li>
 * </ul>
 */
public class LwViewMan
extends ValidationObject
{
  private Drawable parent;
  private LwView  bg, border, view;
  private Insets insets;
  private int width, height;

 /**
  * Sets the owner component for this view manager.
  * @param <code>p</code> the specified owner component.
  */
  protected void setParent(Drawable p)
  {
    if (p != parent)
    {
      parent = p;
      invalidate();
    }
  }

 /**
  * Gets the owner component for this view manager.
  * @return an owner component.
  */
  public Drawable getParent () {
    return parent;
  }

 /**
  * Gets the insets of the view manager that is calculated as maximal value among all views.
  * @return an insets.
  */
  public Insets getInsets() {
    validate();
    return insets;
  }

 /**
  * Gets the view that should be used as a background view for the owner component.
  * @return an background view.
  */
  public LwView getBg() {
    return bg;
  }

 /**
  * Gets the view that should be used as a border view for the owner component.
  * @return a border view.
  */
  public LwView getBorder() {
    return border;
  }

 /**
  * Gets the view that should be used as a face view for the owner component.
  * @return a face view.
  */
  public LwView getView() {
    return view;
  }

 /**
  * Sets the background view for the owner component by the specified view name.
  * The method tries to resolve the string name by <code>getView</code> method.
  * This implementation of the method uses the name as a key to find the view
  * among static objects. The library defines some of standard views as static
  * objects (for example border views, button views, checkbox views and so on) and
  * lightweight components use the standard views to paint itself.
  * @param <code>s</code> the name of the background view.
  */
  public void setBg(String s) {
    setBg(getView(s));
  }

 /**
  * Sets the border view for the owner component by the specified view name.
  * The method tries to resolve the string name by <code>getView</code> method.
  * This implementation of the method uses the name as a key to find the view
  * among static objects. The library defines some of standard views as  static
  * objects (for example border views, button views, checkbox views and so on) and
  * lightweight components use the standard views to paint itself.
  * @param <code>s</code> the name of the border view.
  */
  public void setBorder(String s) {
    setBorder(getView(s));
  }

 /**
  * Sets the face view for the owner component by the specified view name.
  * The method tries to resolve the string name by <code>getView</code> method.
  * This implementation of the method uses the name as a key to find the view
  * among static objects. The library defines some of standard views as static
  * objects (for example border views, button views, checkbox views and so on) and
  * lightweight components use the standard views to paint itself.
  * @param <code>s</code> the name of the face view.
  */
  public void setView (String s) {
    setView(getView(s));
  }

 /**
  * Sets the background view.
  * @param <code>s</code> the background view.
  */
  public void setBg(LwView s)
  {
    if (s != bg)
    {
      bg = s;
      invalidate();
    }
  }

 /**
  * Sets the border view.
  * @param <code>s</code> the border view.
  */
  public void setBorder(LwView s)
  {
    if (s != border)
    {
      LwView old = border;
      border = s;
      if (!isValid(old, view)) invalidate();
    }
  }

 /**
  * Sets the face view.
  * @param <code>s</code> the face view.
  */
  public void setView(LwView s)
  {
    if (s != view)
    {
      LwView old = view;
      view = s;
      if (view != null) view.ownerChanged(this);
      if (!isValid(old, view)) invalidate();
    }
  }

 /**
  * Invalidates the view manager. The view manager performs invalidation of the owner
  * component if it is not <code>null</code>.
  */
  public /*C#override*/ void invalidate() {
    if (parent != null) parent.invalidate();
    super.invalidate();
  }

 /**
  * Calculates the view manager metrics. The method is invoked during validation of the
  * view manager if it is necessary. The main purpose of the metod to calculate preferred
  * size and insets of the view manager basing on the views set (border, background and
  * face views).
  */
  protected /*C#override*/ void recalc ()
  {
    LwView[] s = getAllViews();
    for (int i=0; i<s.length; i++) if (s[i] != null) s[i].validate();
    insets = calcInsets(this);

    Dimension ps = new Dimension();
    for (int i=0; i<s.length; i++)
      if (s[i] != null && (s[i].getType() == LwView.ORIGINAL || s[i].getType() == LwView.STRETCH))
        ps = MathBox.max(s[i].getPreferredSize(), ps);

    width  = ps.width;
    height = ps.height;
  }

 /**
  * Returns the preferred size of the view manager. The preferred size is computed
  * basing on the views preferred sizes (border, face, background views)
  * @return a preferred size.
  */
  public /*C#virtual*/ Dimension getPreferredSize() {
    validate();
    return new Dimension (width, height);
  }

 /**
  * Resolves the view by the specified view name. The method binds a view with a name.
  * This class implementation uses the name as a static object key, so the method returns
  * a view by the name from the set of static objects. The method provides ability to
  * implement own mapping mechanism. For example, LwButton component uses "button.on"
  * and "button.off" names for the face view depending on the button state, so it is
  * possible to override the method for a new view manager implementation to change
  * the button face, but the library provides LwAdvViewMan implementation to support
  * custom views.
  * @param  <code>key</code> the name of the view.
  * @return a view.
  */
  protected /*C#virtual*/ LwView getView(String key) {
    return LwToolkit.getView(key);
  }

 /**
  * Returns all views that are provided with the view manager. The method is used with
  * <code>recalc</code> method to compute insets and preferred size of the view
  * manager.
  * @return an array of views.
  */
  protected /*C#virtual*/ LwView[] getAllViews() {
    return  new LwView[] { view, border, bg };
  }

  private static Insets calcInsets(LwViewMan view)
  {
    LwView[] skins = view.getAllViews();
    Insets   res   = new Insets(0, 0, 0, 0);
    for (int i=0; i<skins.length; i++)
      if (skins[i] != null) res = MathBox.max(res, skins[i].getInsets());
    return res;
  }

  private static boolean isValid(LwView v1, LwView v2)
  {
    return ( v1 != null && v2 != null &&
             v1.getPreferredSize().equals(v2.getPreferredSize()) &&
             v1.getInsets().equals(v2.getInsets()));
  }
}
