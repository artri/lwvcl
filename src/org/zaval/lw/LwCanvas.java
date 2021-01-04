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
import org.zaval.util.*;

/**
 * This is basic implemenation of the light weight component interface that should be used
 * to develop own light weight component. As rule when you develop a lightweight components
 * it is necessary to pass through following steps:
 * <ul>
 *   <li>

 *     Inherit the class for your lightweight component.
 *   </li>
 *   <li>
 *     Implement necessary events listeners interfaces, to have ability handling
 *     required lightweight events.
 *   </li>
 *   <li>
 *     Override (if it is necessary) <code>paint</code> method to define the component face.
 *   </li>
 *   <li>
 *     Override (if it is necessary) <code>update</code> method to define the component
 *     background.
 *   </li>
 *   <li>
 *     Override (if it is necessary) <code>recalc</code> method to calculate the component
 *     metrics. For example, you can calculate "pure" preferred size inside the method or
 *     some metrical characteristics that are used to calculate the preferred size.
 *   </li>
 *   <li>
 *     Override (if it is necessary) <code>calcPreferredSize</code> method to define "pure"
 *     preferred size of the component face.
 *   </li>
 * </ul>
 */
public class LwCanvas
extends org.zaval.util.ValidationObject
implements LwComponent
{
   private Color back;
   protected long insets;
   protected short bits = DEFAULT_BITS;

   protected int x, y, width, height, psWidth = -1, psHeight = -1;

  /**
   * The component parent.
   */
   protected LwComponent parent;

  /**
   * The component view manager.
   */
   protected LwViewMan skins;

  /**
   * Calculates and returns the insets of this component. Take care that insets for lightweight
   * components differ from Java AWT components. The lightweight insets
   * defines indents from "left", "top", "right" and "bottom" of the component view.
   * Don't override the method. If you want to define insets use <code>setInsets</code>
   * method.
   * @return the insets of this component.
   */
   public /*C#virtual*/ Insets getInsets()  {
     return getInsets(this);
   }

   public int getX() {
     return x;
   }

   public int getY() {
     return y;
   }

   public int getWidth() {
     return width;
   }

   public int getHeight() {
     return height;
   }

  /**
   * Sets the specified insets for the component.
   * @param <code>top</code> the top indent.
   * @param <code>left</code> the left indent.
   * @param <code>bottom</code> the bottom indent.
   * @param <code>right</code> the right indent.
   */
   public void setInsets (int top, int left, int bottom, int right)
   {
     Insets i1 = getInsets();
     if (!(new Insets (top, left, bottom, right)).equals(i1))
     {
       insets = (top & 0xFFFF) + ((left & 0xFFFF) << 16);
       long l = (bottom & 0xFFFF) + ((right & 0xFFFF) << 16);
       insets += (l << 32);
       vrp();
     }
   }

  /**
   * Returns a size of this component. The size is reperesented with
   * <code>java.awt.Dimension</code> class.
   * @return a <code>Dimension</code> object that indicates the
   * size of this component.
   */
   public Dimension getSize() {
     return new Dimension(width, height);
   }

  /**
   * Returns the bounding rectangle of the visible part for the component.
   * @return a visible part bounding rectangle.
   */
   public /*C#virtual*/ Rectangle getVisiblePart()
   {
     if (isVisible())
     {
       //???
       if (parent == null) return new Rectangle (0, 0, width, height);
       Rectangle pr  = parent.getVisiblePart();

       if (pr != null) pr = MathBox.intersection(pr.x, pr.y, pr.width, pr.height,
                                                 x, y, width, height);
       if (pr != null && pr.width > 0 && pr.height > 0)
       {
         pr.x -= x;
         pr.y -= y;
         return pr;
       }
     }
     return null;
   }

  /**
   * Sets the lightweight parent of this component. It is supposed that the parent implements
   * LwContainer interface. The method is provided for lightweight core usage to support components
   * hierarchy, not for applications that base on the library.
   * Don't touch the method.
   * @param <code>o</code> the parent component of this lightweight component.
   */
   public void setLwParent(LwComponent o)
   {
     if (o != parent)
     {
       parent = o;
       invalidate();
     }
   }

  /**
   * Gets the location of this component point specifying the component top-left corner.
   * The location is relative to the parent component coordinate space.
   * @return an instance of <code>Point</code> representing the top-left corner
   * of the component bounds in the coordinate space of the component parent.
   */
   public Point getLocation() {
     return new Point(x, y);
   }

  /**
   * Gets the background color of this lightweight component.
   * @return a componen background color.
   */
   public Color getBackground() {
     return back == null?LwToolkit.BACK_COLOR:back;
   }

  /**
   * Determines if this component is visible. The component is visible
   * if the visibility flag is true.
   * @return <code>true</code> if the component is visible;
   * <code>false</code> otherwise.
   */
   public /*C#virtual*/ boolean isVisible() {
     return MathBox.checkBit(bits, VISIBLE_BIT);
   }

  /**
   * Shows or hides this lightweight component depending on the value of parameter
   * <code>b</code>. The method performs appropriate LwComponentEvent.
   * @param <code>b</code> if it is <code>true</code>, shows this component;
   * otherwise, hides this component.
   */
   public /*C#virtual*/ void setVisible(boolean b)
   {
     if (MathBox.checkBit(bits, VISIBLE_BIT) != b)
     {
       bits = MathBox.getBits(bits, VISIBLE_BIT, b);
       invalidate();
       if (b)
       {
         LwToolkit.getEventManager().perform(new LwComponentEvent(this, LwComponentEvent.COMP_SHOWN));
         repaint();
       }
       else
       {
         LwToolkit.getEventManager().perform(new LwComponentEvent(this, LwComponentEvent.COMP_HIDDEN));
         if (parent != null) parent.repaint(x, y, width, height);
       }
     }
   }

  /**
   * Determines whether this component is enabled. If the method returns
   * <code>true</code> than the component is enabled and can participate in event
   * handling and performing processes. Components are enabled initially by default.
   * A component can be enabled or disabled by calling its <code>setEnabled</code>
   * method.
   * @return <code>true</code> if the component is enabled; <code>false</code> otherwise.
   */
   public /*C#virtual*/ boolean isEnabled() {
     return MathBox.checkBit(bits, ENABLE_BIT) && (parent == null || parent.isEnabled());
   }

  /**
   * Enables or disables this component. An enabled component can participate
   * in events handling and performing processes. Component is enabled initially
   * by default. The method performs appropriate LwComponentEvent.
   * @param  <code>b</code> if <code>true</code>, this component is
   * enabled; otherwise this component is disabled.
   */
   public /*C#virtual*/ void setEnabled(boolean b)
   {
     if (MathBox.checkBit(bits, ENABLE_BIT) != b)
     {
       bits = MathBox.getBits(bits, ENABLE_BIT, b);
       if (b) LwToolkit.getEventManager().perform(new LwComponentEvent(this, LwComponentEvent.COMP_ENABLED ));
       else   LwToolkit.getEventManager().perform(new LwComponentEvent(this, LwComponentEvent.COMP_DISABLED));
       repaint();
     }
   }

  /**
   * Sets the background color of this component. The color is used to fill the component
   * background in case if it is not transparent.
   * @param <code>c</code> the color to become this component background color. Use <code>null</code>
   * as the color value to set the background to the default color value.
   */
   public /*C#virtual*/ void setBackground(Color c)
   {
     if (c != back && (c == null || !c.equals(back)))
     {
       back = c;
       repaint();
     }
   }

  /**
   * Invalidates this component. The parent will be invalidated with this component too.
   */
   public /*C#override*/ void invalidate()  {
     if (parent != null) parent.invalidate();
     super.invalidate();
   }

  /**
   * Validates this component. The method initiates validation process only if it is necessary.
   * It means that you don't need to care about frequency the method executing. The method executes
   * <code>recalc</code> method to calculate the component metrics if it is necessary. So,
   * override the method (<code>recalc</code> method) to calculate some metrical characteristics
   * to minimize the computation time.
   */
   public /*C#override*/ void validate()
   {
     if (isVisible())
     {
       if (skins != null) skins.validate();
       super.validate();
     }
   }

  /**
   * Sets a new location for this component. The top-left corner of
   * the new location is specified by the <code>x</code> and <code>y</code>
   * parameters in the coordinate space of this component parent.
   * @param <code>xx</code> the <i>x</i>-coordinate of the new location
   * top-left corner in the parent coordinate space.
   * @param <code>yy</code> The <i>y</i>-coordinate of the new location
   * top-left corner in the parent coordinate space.
   */
   public void setLocation (int xx, int yy)
   {
     if (xx != x || y != yy)
     {
       int ox = x;
       int oy = y;
       x = xx;
       y = yy;
       if (parent != null) parent.repaint(Math.min(x, ox), Math.min(y, oy), width + Math.abs(x - ox), height + Math.abs(y - oy));
     }
   }

  /**
   * Sets the specified size for this component.
   * @param <code>w</code> the width of this component.
   * @param <code>h</code> the height of this component.
   */
   public void setSize (int w, int h)
   {
     if (w != width || h != height)
     {
       int mw = Math.max (w, width);
       int mh = Math.max (h, height);
       width  = w;
       height = h;
       invalidate();
       repaint(0, 0, mw, mh);
     }
   }

  /**
   * Gets the bounds of this component. The bounds is represented with
   * <code>java.awt.Rectangle</code> class.
   * @return a rectangle indicating this component bounds.
   */
   public Rectangle getBounds() {
     return new Rectangle (x, y, width, height);
   }

  /**
   * Determines if the component or an immediate child component contains the
   * (xx,&nbsp;yy) location in its visible part and if so, returns the component.
   * @param     <code>xx</code> the x coordinate.
   * @param     <code>yy</code> the y coordinate.
   * @return    the component or sub-component that contains the (x,&nbsp;y) location;
   *            <code>null</code> if the location doesn't belong to visible part of
   *            this component.
   */
   public /*C#virtual*/ LwComponent getLwComponentAt(int xx, int yy) {
     Rectangle r = getVisiblePart();
     return r != null && r.contains(xx, yy)?this:null;
   }

  /**
   * Gets the lightweight parent of this component. It is supposed that the parent implements
   * LwContainer interface.
   * @return a parent container of this component.
   */
   public /*C#virtual*/ LwComponent getLwParent() {
     return parent;
   }

  /**
   * Invoked with <code>validate</code> method only if the component is not valid.
   * The method shoud be overrided to calculate metrical characteristics of the component to
   * minimize computation time of the preferred size for the component. For example, you can
   * calculate a preferred size inside the method and just return it by
   * <code>calcPreferredSize</code> method.
   */
   protected /*C#override*/ void recalc() {}

  /**
   * Performs repainting process of this component. The method causes
   * calling of <code>update</code> and than <code> paint </code> methods.
   * The method bases on appropriate method of LwPaintManager.
   */
   public /*C#virtual*/ void repaint() {
     LwToolkit.getPaintManager().repaint(this);
   }

  /**
   * Performs repainting process of the specified rectangle. The method causes
   * calling of <code>update</code> and than <code> paint </code> methods.
   * The method bases on appropriate method of LwPaintManager.
   * @param  <code>x</code>  the <i>x</i> coordinate.
   * @param  <code>y</code>  the <i>y</i> coordinate.
   * @param  <code>w</code>  the width.
   * @param  <code>h</code>  the height.
   */
   public /*C#virtual*/ void repaint(int x, int y, int w, int h) {
     LwToolkit.getPaintManager().repaint(this, x, y, w, h);
   }

  /**
   * Updates this component. The calling of the method precedes
   * the calling of <code>paint</code> method and it is performed with
   * <code>repaint</code> method. The method can be used to fill the drawable
   * component with the background color if the component is opaque.
   * In the implementation light weight component is updated (using the background
   * color) with LwPaintManager, so it is not necessary to care about it.
   * <p>
   * You can use the method to define own background pattern.
   * @param <code>g</code> the specified context to be used for updating.
   */
   public /*C#virtual*/ void update(Graphics g){}

  /**
   * Paints this component. You can use the method to define a face of the
   * component
   * @param <code>g</code> the graphics context to be used for painting.
   */
   public /*C#virtual*/ void paint (Graphics g){}

  /**
   * Gets the preferred size of this component. The method computes the preferred size as
   * a sum of the component insets (returned with <code>getInsets</code> method) and
   * a "pure" preferred size (returned with <code>calcPreferredSize</code> method).
   * You should not override the method, use <code>calcPreferredSize</code> method  to
   * define the "pure" preferred size of this component.
   * @return a dimension object indicating this component preferred size.
   */
   public /*C#virtual*/ Dimension getPreferredSize () {
     validate();
     return getPreferredSize(this);
   }

  /**
   * Returns a view manager of the component. The view manager can be <code>null</code>.
   * The input argument <code>autoCreate</code> defines if the view manager has to be created
   * automatically in a case if it has not been determined before. It means, if the argument is
   * <code>true</code> and the view manager is <code>null</code>, than the component will
   * try to create and initialize its view manager by a default view manager. If the argument
   * is <code>false</code> than the method returns the component view manager as is.
   * @param <code>autoCreate</code> the flag defines if the view manager should be created
   * automatically.
   * @return a view manager for the component.
   */
   public LwViewMan getViewMan (boolean autoCreate)
   {
     if (skins == null && autoCreate)
     {
       skins = new LwViewMan();
       skins.setParent(this);
     }
     return skins;
   }

  /**
   * Sets the specified view manager for the component.
   * @param <code>man</code> the view manager to set for the component.
   */
   public void setViewMan(LwViewMan man)
   {
     if (man != skins)
     {
       skins = man;
       if (skins != null) skins.setParent(this);
       viewManChanged();
       vrp();
     }
   }

  /**
   * Returns a string representation of the object.
   * @return a string representation of this instance.
   */
   public String toString () {
     return "[(" + x + "," + y + ") " + width + " " + height + "] valid=" + isValid() + ",op=" + isOpaque() + ",vis=" + isVisible();
   }

  /**
   * Gets the "pure" preferred size for this component. The method should be overrided to define
   * the component preferred size. Don't use insets to calculate the preferred size, the insets
   * will be added with <code>getPreferredSize</code> method.
   * @return a "pure" preferred size.
   */
   protected /*C#virtual*/ Dimension calcPreferredSize() {
     return new Dimension(LwToolkit.PS_SIZE);
   }

   public /*C#virtual*/ boolean canHaveFocus() {
     return false;
   }

  /**
   * Tests if the component is a focus owner. The method uses current LwFocusManager manager
   * to define a focus owner.
   * @return <code>true</code> if the component is a focus owner; otherwise
   * <code>false</code>
   */
   public boolean hasFocus() {
     return LwToolkit.getFocusManager().hasFocus(this);
   }

  /**
   * Requests focus for this component. The method uses current LwFocusManager manager
   * to request a focus. The component can be a focus owner if it implements LwFocusListener
   * interface.
   */
   public void requestFocus() {
     LwToolkit.getFocusManager().requestFocus(this);
   }

  /**
   * Sets the opaque of this component. Use <code> false </code>
   * argument value to make a transparent component from this component.
   * The painting process will not use <code>update</code> method and the background view
   * for a transparent component.
   * @param  <code>b</code> the opaque flag.
   */
   public /*C#virtual*/ void setOpaque(boolean b)
   {
     if (b != MathBox.checkBit(bits, OPAQUE_BIT))
     {
       bits = MathBox.getBits(bits, OPAQUE_BIT, b);
       repaint();
     }
   }

  /**
   * Gets the opaque of this component. If the method returns
   * <code>false</code> than the component is transparent, in this case
   * <code>update</code> method has not be called during painting process and LwPaintManager
   * doesn't clear the component with the background color.
   * @return  <code>true</code> if the component is opaque; otherwise
   * <code>false</code>.
   */
   public boolean isOpaque() {
     return MathBox.checkBit(bits, OPAQUE_BIT);
   }

  /**
   * Sets the specified preferred size for the component. Using the method it is
   * possible to fix the preferred size with the given width and height. In this case
   * <code>getPreferredSize</code> method returns the fixed size.
   * @param  <code>w</code> the width to be used as the preferred size width.
   * If the width is less zero than the width will be calculated basing on the component
   * insets and the returned by <code>calcPreferredSize</code> width.
   * @param  <code>h</code> the height to be used as the preferred size height.
   * If the height is less zero than the height will be calculated basing on the component
   * insets and the returned by <code>calcPreferredSize</code> height.
   */
   public void setPSSize (int w, int h)
   {
     if (w != psWidth || h != psHeight)
     {
       psWidth  = w;
       psHeight = h;
       vrp();
     }
   }

  /**
   * Returns an origin of the component. The origin defines an offset of the component view
   * relatively the component point of origin. The origin can be used to scroll the component
   * view. The default origin value is <code>null</code>, in this case the origin is (0, 0).
   * @return an origin of the component.
   */
   public /*C#virtual*/ Point getOrigin () {
     return null;
   }

  /**
   * Invoked whenever the view manager has been changed. The method can be overrided to listen
   * when the view manager is re-set.
   */
   protected /*C#virtual*/ void viewManChanged() {}

  /**
   * Invalidates and then repaints the component.
   */
   protected void vrp() {
     invalidate();
     repaint();
   }

   private static Dimension getPreferredSize (LwCanvas c)
   {
     if (c.psWidth >= 0 && c.psHeight >= 0) return new Dimension(c.psWidth, c.psHeight);
     else
     {
       Dimension d = c.calcPreferredSize();
       if (c.skins != null) d = MathBox.max (d, c.skins.getPreferredSize());

       Insets i = c.getInsets();
       d.width  = c.psWidth   >= 0?c.psWidth:(d.width + i.left + i.right);
       d.height = c.psHeight  >= 0?c.psHeight:(d.height + i.top + i.bottom);
       return d;
     }
   }

   private static final Insets getInsets(LwCanvas c) /*java*/
   /*C#private static Insets getInsets(LwCanvas c)*/
   {
     Insets i = new Insets((int)(c.insets & 0xFFFF),
                           (int)((c.insets >> 16) & 0xFFFF),
                           (int)((c.insets >> 32) & 0xFFFF),
                           (int)((c.insets >> 48) & 0xFFFF));
     return (c.skins != null)?MathBox.max(c.skins.getInsets(), i):i;
   }

   private static short VISIBLE_BIT   = 1;
   private static short ENABLE_BIT    = 2;
   private static short OPAQUE_BIT    = 4;
   private static short DEFAULT_BITS  = 7;
}
