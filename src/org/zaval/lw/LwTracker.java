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
import java.awt.*;
import java.awt.event.*;
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This is tracker component.  The component is like slider component, but this is more
 * complex:
 * <ul>
 *  <li>
 *    It allows to use set of bundles. The minimal number of bundles elements is two.
 *    The first bundle is called leftmost and the right bundle is called rightmost.
 *    The two bundles define maximal and minimal values for other bundles elements
 *    that can be placed between these.
 *  </li>
 *  <li>
 *    Any bundle can be added or removed during interaction with the component. The specified
 *    bundle can be added if his value is greater than nearest left bundle and less than
 *    nearest right bundle (bundleValue[i-1] < bundleValue[i] < bundleValue[i+1]).
 *  </li>
 *  <li>
 *    It is possible to specify a tooltip for any bundle.
 *  </li>
 *  <li>
 *    The bundle has states depending on the mouse state. It is possible to specify
 *    appropriate view for the given bundle state.
 *  </li>
 *  <li>
 *    The component performs tracker events and an application can listen when tracker
 *    value has been changed, a bundle element has been added or removed, and so on.
 *    Use <code>addTrackerListener</code> and <code>removeTrackerListener</code> to
 *    register and remove the tracker listeners.
 *  </li>
 * </ul>
 */
public class LwTracker
extends LwCanvas
implements LwMouseListener, LwMouseMotionListener, TooltipInfo
{
 /**
  * The ONSURFACE state.
  */
  public static final int ST_ONSURFACE  = 0;

 /**
  * The PRESSED state.
  */
  public static final int ST_PRESSED    = 1;

 /**
  * The OUTSURFACE state.
  */
  public static final int ST_OUTSURFACE = 2;

 /**
  * The REMOVING state.
  */
  public static final int ST_REMOVING   = 3;

 /**
  * Specifies type for leftmost and rightmost bundles. It is used to customize views for
  * the bundle type.
  */
  public static final int BUNDLE_1 = 1;

 /**
  * Specifies type for bundles that are located between leftmost and rightmost bundles.
  * It is used to customize views for the bundle type.
  */
  public static final int BUNDLE_2 = 2;

 /**
  * Specifies scale area that is placed on the right and on the left of rightmost and
  * left most bundles. It is used to customize color for the scale area.
  */
  public static final int OUTER_SCALE = 0;

 /**
  * Specifies scale area that is placed between rightmost and
  * left most bundles. It is used to customize color for the scale area.
  */
  public static final int INNER_SCALE = 1;

  private int           min, max, psW, psH;
  private int           intervals[], scaleStep = 1, scaleSize, titleLoc, netSize = 2, exactStep;
  private int           gapx, gapy;
  private int           targetBundle = -1, targetMin, targetMax;
  private Point         scaleLoc = new Point();
  private Vector        values = new Vector();
  private LwView[]      bundles = new LwView[8];
  private Vector        support;
  private LwTextRender  titleRender;
  private Color[]       scaleColor = new Color[] { Color.gray, LwToolkit.darkBlue };

 /**
  * Constructs the class instance.
  */
  public LwTracker()
  {
    setView (BUNDLE_1, ST_ONSURFACE, LwToolkit.getView("tr.b1.on"));
    setView (BUNDLE_1, ST_PRESSED,   LwToolkit.getView("tr.b1.pressed"));
    setView (BUNDLE_1, ST_OUTSURFACE,LwToolkit.getView("tr.b1.out"));
    setView (BUNDLE_1, ST_REMOVING,  LwToolkit.getView("tr.b1.removing"));
    setView (BUNDLE_2, ST_ONSURFACE, LwToolkit.getView("tr.b2.on"));
    setView (BUNDLE_2, ST_PRESSED,   LwToolkit.getView("tr.b2.pressed"));
    setView (BUNDLE_2, ST_OUTSURFACE,LwToolkit.getView("tr.b2.out"));
    setValues(0, 100, new int[] {0, 20, 20, 20, 20 }, new int[] {0, 20, 30, 100 }, 1 );
    titleRender = new LwTextRender("");
    titleRender.setForeground(scaleColor[INNER_SCALE]);
    titleRender.setFont(LwToolkit.BFONT);
    showTitle (true);
  }

  public /*C#override*/ boolean canHaveFocus() {
    return true;
  }


 /**
  * Sets the tracker title visibility.
  * @param <code>b</code> the tracker title visibility.
  */
  public void showTitle (boolean b)
  {
    if (MathBox.checkBit(bits, SHOW_TITLE) != b) {
      bits = MathBox.getBits(bits, SHOW_TITLE, b);
      vrp();
    }
  }

 /**
  * Sets the given component as tooltip for the specified bundle.
  * @param <code>index</code> the specified bundle index.
  * @param <code>c</code> the specified component to be used as tooltip.
  */
  public void setTooltip (int index, LwComponent c) {
    LwTrackerEl el = (LwTrackerEl)values.elementAt(index);
    el.tooltip = c;
  }

 /**
  * Gets the text render that is used to render the tracker titles.
  * @return a titles render.
  */
  public LwTextRender getTitleRender () {
    return titleRender;
  }

 /**
  * Gets a view that is associated with the given state for the specified bundle type.
  * @param <code>id</code> the specified bundle type. Use BUNDLE_1 or BUNDLE_2 as the
  * argument value.
  * @param <code>state</code> the bundle state. Depending on the bundle type the state
  * can be as follow:
  * <ul>
  *   <li>
  *     BUNDLE_1 (leftmost and rightmost bundles): ST_ONSURFACE,ST_PRESSED,ST_OUTSURFACE.
  *   </li>
  *   <li>
  *     BUNDLE_2: ST_ONSURFACE,ST_PRESSED,ST_OUTSURFACE,ST_REMOVING.
  *   </li>
  * </ul>
  * @return the view.
  */
  public LwView getView (int id, int state) {
    return bundles[(id == BUNDLE_1?state:bundles.length/2 + state)];
  }

 /**
  * Sets the given view for the given state and the specified bundle type.
  * @param <code>id</code> the specified bundle type. Use BUNDLE_1 or BUNDLE_2 as the
  * argument value.
  * @param <code>state</code> the bundle state. Depending on the bundle type the state
  * can be as follow:
  * <ul>
  *   <li>
  *     BUNDLE_1 (leftmost and rightmost bundles): ST_ONSURFACE,ST_PRESSED,ST_OUTSURFACE.
  *   </li>
  *   <li>
  *     BUNDLE_2: ST_ONSURFACE,ST_PRESSED,ST_OUTSURFACE,ST_REMOVING.
  *   </li>
  * </ul>
  * @param <code>v</code> the specified view.
  */
  public void setView (int id, int state, LwView v)
  {
    LwView old = getView (id, state);
    if (old != v)
    {
      bundles[(id == BUNDLE_1?state:bundles.length/2 + state)] = v;
      vrp();
    }
  }

 /**
  * Gets the scale color for the specified scale area.
  * @return a scale color.
  */
  public Color getScaleColor (int type) {
    return scaleColor[type];
  }

 /**
  * Sets the scale color for the specified scale area.
  * @param <code>type</code> the specified scale area. Use INNER_SCALE (scale area that is located
  * between leftmost and rightmost bundles) or OUTER_SCALE.
  * as the parameter value.
  * @param <code>c</code> the scale color.
  */
  public void setScaleColor (int type, Color c)
  {
    if (!c.equals(scaleColor[type]))
    {
      scaleColor[type] = c;
      repaint();
    }
  }

 /**
  * Sets the scale step.
  * @param <code>s</code> the scale step.
  */
  public void setScaleStep (int s)
  {
    if (s != scaleStep)
    {
      scaleStep = s;
      repaint();
    }
  }

 /**
  * Sets the tracker properties.
  * @param <code>min</code> the specified minimal possible value.
  * @param <code>max</code> the specified maximal possible value.
  * @param <code>intervals</code> the specified intervals sizes array.
  * @param <code>values</code> the specified bundles values.
  * @param <code>step</code> the specified step.
  */
  public void setValues (int min, int max, int[] intervals, int[] values, int step)
  {
    if (values.length < 2 || step < 0 || min >= max || min + step > max)
      throw new IllegalArgumentException ();

    for (int i=0, start = min; i<intervals.length; i++)
    {
      start += intervals[i];
      if (start > max || intervals[i] < 0) throw new IllegalArgumentException ();
    }

    this.min  = min;
    this.max  = max;
    this.exactStep = step;
    this.intervals = new int[intervals.length];
    System.arraycopy (intervals, 0, this.intervals, 0, intervals.length);

    this.values.removeAllElements();
    for (int i=0; i<values.length; i++) insertBundle(values[i]);
    vrp();
  }

 /**
  * Gets a number of bundles elements.
  * @return the number of bundles elements.
  */
  public int getBundles() {
    return values.size();
  }

 /**
  * Removes a bundle at the specified index.
  * @param <code>index</code> the specified index.
  */
  public void removeBundle(int index)
  {
    int value = getValue (index);
    values.removeElementAt(index);
    perform (index, value, value, LwTrackerEvent.BUNDLE_REMOVED);
    repaint();
  }

 /**
  * Inserts a new bundle with the specified value. The method calculates the new bundle
  * element index basing on the value and after that inserts the new bundle in the bundle
  * list at the found index.
  * @param <code>value</code> the specified bundle value.
  */
  public int insertBundle(int value)
  {
    int i = getInterval(value);
    if (i < 0) throw new IllegalArgumentException();
    values.insertElementAt(new LwTrackerEl(value), i);
    perform (i, value, value, LwTrackerEvent.BUNDLE_ADDED);
    repaint();
    return i;
  }


 /**
  * Gets a minimal possible value.
  * @return the minimal possible value.
  */
  public int getMin () {
    return min;
  }

 /**
  * Gets a minimal possible value for the specified bundle.
  * @param <code>bundleIndex</code> the specified bundle index.
  * @return the minimal possible value.
  */
  public int getMin (int bundleIndex) {
    return bundleIndex == 0?getMin():getValue(bundleIndex-1) + exactStep;
  }

 /**
  * Gets the maximal possible value.
  * @return a maximal possible value.
  */
  public int getMax () {
    return max;
  }

 /**
  * Gets the maximal possible value for the specified bundle.
  * @param <code>bundleIndex</code> the specified bundle index.
  * @return a maximal possible value.
  */
  public int getMax (int bundleIndex)  {
    return bundleIndex == values.size()-1?getMax():getValue(bundleIndex + 1) - exactStep;
  }

 /**
  * Sets the given value for the specified bundle. The value should be less than
  * nearest right bundle value and greater than nearest left bundle value.
  * @param <code>index</code> the specified bundle index.
  * @param <code>value</code> the specified value.
  */
  public void setValue (int index, int value)
  {
    int max = getMax (index);
    int min = getMin (index);
    if (value > max || value < min) throw new IllegalArgumentException();

    int oldValue = getValue (index);
    if (oldValue != value)
    {
      LwTrackerEl info = (LwTrackerEl)values.elementAt(index);
      info.value = value;
      perform (index, value, oldValue, LwTrackerEvent.VALUE_CHANGED);
      repaint();
    }
  }

 /**
  * Gets the specified bundle value.
  * @param <code>index</code> the specified bundle index.
  * @return a value.
  */
  public int getValue (int index) {
    return ((LwTrackerEl)values.elementAt(index)).value;
  }

 /**
  * Adds the specified tracker listener. Using the listener it possible to be notified
  * whenever a bundle value has been changed, a new bundle has been added or removed or
  * tracker has provide value info.
  * @param <code>l</code> the specified tracker listener.
  */
  public void addTrackerListener(LwTrackerListener l) {
    if (support == null) support = new Vector();
    if (!support.contains(l)) support.addElement(l);
  }

 /**
  * Removes the specified tracker listener so it no longer receives tracker events
  * from this component.
  * @param <code>l</code> the specified tracker listener.
  */
  public void removeTrackerListener(LwTrackerListener l) {
    if (support != null) support.removeElement(l);
  }

  public void paint (Graphics g)
  {
    rMetric();
    Insets ins = getInsets();
    int    th = titleRender.getFontMetrics().getHeight();
    int    left = value2loc(getValue(0)), right = value2loc(getValue(values.size()-1));

    for (int i=min; i <= max; i += scaleStep)
    {
      int x = value2loc(i);
      g.setColor(isEnabled ()?((x < left || x > right)?scaleColor[0]:scaleColor[1]):Color.gray);
      g.drawLine (x, scaleLoc.y, x, scaleLoc.y + netSize);
    }

    boolean showTitle = MathBox.checkBit (bits, SHOW_TITLE);
    int v = min;
    for (int i=0; i < intervals.length; i++)
    {
      v += intervals[i];
      int x = value2loc(v);
      if (showTitle)
      {
        titleRender.getTextModel().setText(Integer.toString(v));
        Dimension tps = titleRender.getPreferredSize();
        titleRender.paint (g, x - tps.width/2, titleLoc, this);
      }

      g.setColor(isEnabled ()?((x < left || x > right)?scaleColor[0]:scaleColor[1]):Color.gray);
      g.drawLine (x, scaleLoc.y, x, scaleLoc.y + netSize*4);
    }

    int size = values.size();
    for (int i=0; i<size; i++)
    {
      LwView view = getBundleView(i);
      if (view != null)
      {
        Dimension ps = view.getPreferredSize();
        int       x  = value2loc(getValue(i)) - ps.width/2;
        view.paint (g, x, scaleLoc.y - ps.height/(isLRBundle(i)?2:1), this);
      }
    }

    if (hasFocus())
      LwToolkit.getView("br.dot").paint(g, ins.left, ins.top,
                                           width - ins.left - ins.right,
                                           height - ins.top - ins.bottom, this);
  }

  private int correctDt, startValue;

  public void startDragged(LwMouseMotionEvent e)
  {
    if (targetBundle >=0)
    {
      targetMin = getMin(targetBundle);
      targetMax = getMax(targetBundle);
      Rectangle r = getBundleBounds(targetBundle);
      correctDt = r.x + r.width/2 - e.getX();
      startValue = getValue(targetBundle);
    }
  }

  public void endDragged(LwMouseMotionEvent e)
  {
    if (targetBundle >= 0)
    {
      int x = e.getX(), y = e.getY();
      if (targetBundle > 0 && targetBundle < values.size() - 1 &&
          (x < 0 || y < 0 || x >= width || y >= height))
        removeBundle(targetBundle);
      else
      {
        int endValue = getValue(targetBundle);
        if (startValue != endValue)
          perform (targetBundle, endValue, startValue, LwTrackerEvent.VALUE_CHANGED_DONE);
      }
    }
  }

  public void mouseDragged(LwMouseMotionEvent e)
  {
    if (targetBundle >= 0)
    {
      int x = e.getX(), y = e.getY();
      int v = findNearest(x + correctDt, y);
      if (v > targetMax) v = targetMax;
      else
      if (v < targetMin) v = targetMin;

      setValue (targetBundle, v);
      if (targetBundle > 0 && targetBundle < values.size() - 1)
      {
        int s = x < 0 || x >= width || y < 0 || y >= height?ST_REMOVING:ST_PRESSED;
        setBundleState(targetBundle, s);
      }
    }
  }

  public void mousePressed (LwMouseEvent e)  {
    if (targetBundle >= 0)
      setBundleState(targetBundle, ST_PRESSED);
  }

  public void mouseReleased(LwMouseEvent e)
  {
    if (targetBundle >= 0)
    {
      int i = getBundleIndex(e.getX(), e.getY());
      if (i >= 0) setBundleState(i, ST_ONSURFACE);
      if (i != targetBundle) setBundleState(targetBundle, ST_OUTSURFACE);
      targetBundle = i;
    }
  }

  public void mouseMoved(LwMouseMotionEvent e)
  {
    int x = e.getX(), y = e.getY();
    int i = getBundleIndex(x, y);
    if (targetBundle != i)
    {
      if (targetBundle >= 0)
        setBundleState(targetBundle, ST_OUTSURFACE);

      targetBundle = i;
      if (targetBundle >= 0) setBundleState(targetBundle, ST_ONSURFACE);
    }
  }

  public void mouseClicked (LwMouseEvent e)
  {
    if (LwToolkit.isActionMask(e.getMask()) && e.getClickCount() > 1)
    {
      if (targetBundle >= 0)
      {
        int v = getValue(targetBundle);
        perform (targetBundle, v, v, LwTrackerEvent.VALUE_INFO);
      }
      else
      {
        int v = findNearest(e.getX(), e.getY());
        int i = getInterval(v);
        if (i > 0 && i < values.size()) insertBundle(v);
      }
    }
  }

  public void mouseEntered (LwMouseEvent e) {}

  public void mouseExited  (LwMouseEvent e)
  {
    if (targetBundle >= 0) {
      setBundleState(targetBundle, ST_OUTSURFACE);
      targetBundle = -1;
    }
  }

  public void invalidate() {
    scaleSize = -1;
    super.invalidate();
  }

  public LwComponent getTooltip (int x, int y) {
    int index = getBundleIndex(x, y);
    return (index >= 0)?((LwTrackerEl)values.elementAt(index)).tooltip:null;
  }

 /**
  * Returns a bundle element index by the specified location.
  * @param <code>x</code> the specified x coordinate.
  * @param <code>y</code> the specified y coordinate.
  * @return a bundle element index. The index is <code>-1</code> if there
  * is not any bundle that contains the location.
  */
  public int getBundleIndex(int x, int y)
  {
      int found = -1;
    for (int i=0; i<values.size(); i++)
    {
      Rectangle r = getBundleBounds(i);
      if (r != null && r.contains(x, y))
      {
          if (found == -1)
          {
              found = i;

              // Two cases where the first one we find is preferred:
              // If the mouse is in the left half, and we aren't
              //   already at our minimum
              // If the next one is at its maximum.
              if (x <= r.x+r.width/2 && getValue(i) > getMin(i) ||
                  i+1 < values.size() && getValue(i+1) == getMax(i+1))
                  break;
          }
          else
          {
              // The mouse was not in the left half of the
              // first control we were in, so use the last
              // control we find.
              found = i;
          }
      }
      else if (found != -1) // Once we miss a bundle, we can take the value we found
          break;
    }
    return found;
  }

 /**
  * Finds nearest tracker value where a bundle can be moved by the specified location.
  * @param <code>x</code> the specified x coordinate.
  * @param <code>y</code> the specified y coordinate.
  * @return a nearest value.
  */
  public int findNearest(int x, int y) {
    int v = loc2value (x);
    return exactStep*((v + v%exactStep)/exactStep);
  }

 /**
  * Converts the specified tracker value to a location.
  * @param <code>v</code> the specified value.
  * @return a "x" location.
  */
  public int value2loc (int v) {
    return (scaleSize * (v - min))/(max - min) + scaleLoc.x;
  }

 /**
  * Converts the specified location to a tracker value.
  * @param <code>x</code> the specified location. The location is "x" coordinate.
  * @return a tracker value.
  */
  public int loc2value (int x) {
    return min + ((max - min)*(x - scaleLoc.x))/scaleSize;
  }

 /**
  * Sets the specified exact step.
  * @param <code>step</code> the specified exact step.
  */
  public void setStep(int step) {
    exactStep = step;
  }

 /**
  * Gets the exact step.
  * @return a exact step value.
  */
  public int getStep() {
    return exactStep;
  }

  protected Dimension calcPreferredSize () {
    return new Dimension (psW, psH);
  }

 /**
  * Gets a view for the specified bundle.
  * @param <code>bundleIndex</code> the specified bundle index.
  * @return the bundle view.
  */
  protected LwView getBundleView (int bundleIndex)  {
    int index = bundleIndex == 0 || bundleIndex == values.size() - 1?bundles.length/2:0;
    return bundles[index + getBundleState(bundleIndex)];
  }

 /**
  * Performs a tracker event for the specified bundle with the specified event id and
  * event parameters.
  * @param <code>bi</code> the specified bundle index.
  * @param <code>v</code> the specified bundle value.
  * @param <code>pv</code> the specified previous bundle value.
  * @param <code>id</code> the specified tracker event id.
  */
  protected void perform (int bi, int v, int pv, int id)
  {
    if (support != null)
    {
      LwTrackerEvent e = new LwTrackerEvent(this, bi, v, pv, id);
      for (int i=0; i < support.size(); i++)
      {
        LwTrackerListener l = (LwTrackerListener)support.elementAt(i);
        switch (id)
        {
          case LwTrackerEvent.VALUE_CHANGED : l.valueChanged (e); break;
          case LwTrackerEvent.BUNDLE_ADDED  : l.bundleAdded  (e); break;
          case LwTrackerEvent.BUNDLE_REMOVED: l.bundleRemoved(e); break;
          case LwTrackerEvent.VALUE_INFO    : l.valueInfo(e);     break;
          case LwTrackerEvent.VALUE_CHANGED_DONE: l.valueChangedDone(e); break;
        }
      }
    }
  }

  protected void recalc ()
  {
    int mw2 = 0, mh1 = 0,  mh2 = 0;
    for (int i=0; i < bundles.length/2; i++)
    {
      if (bundles[i] != null)
      {
        Dimension ps = bundles[i].getPreferredSize();
        if (ps.height > mh1) mh1 = ps.height;
      }
    }

    for (int i=bundles.length/2; i < bundles.length ; i++)
    {
      if (bundles[i] != null)
      {
        Dimension ps = bundles[i].getPreferredSize();
        if (ps.height > mh2) mh2 = ps.height;
        if (ps.width  > mw2) mw2 = ps.width;
      }
    }

    gapx = mw2/2 + mw2%2;
    gapy = Math.max (mh1, mh2/2);
    psW  = (getMax() - getMin()) * 2 + mw2 + 2;
    psH  = gapy + Math.max(mh2/2 + mh2%2, 4*netSize) + 2;
    if (MathBox.checkBit(bits, SHOW_TITLE))
      psH += (titleRender.getFontMetrics().getHeight());
  }

  private int getInterval(int value)
  {
    int i = 0, min = 0, max = getMin();
    for (i = 0; i < values.size(); i++)
    {
      min = getMin  (i);
      max = getValue(i);
      if (min <= value && value < max) break;
    }
    return (i == values.size() && (value > getMax() || value < max))?-1:i;
  }

  private void setBundleState(int index, int state)
  {
    LwTrackerEl bi = (LwTrackerEl)values.elementAt(index);
    if (bi.state != state)
    {
      bi.state = state;
      repaint();
    }
  }

  private int getBundleState(int index) {
    return ((LwTrackerEl)values.elementAt(index)).state;
  }

  private Rectangle getBundleBounds(int index)
  {
    LwView v = getBundleView (index);
    if (v != null)
    {
      Dimension ps = v.getPreferredSize();
      return new Rectangle (value2loc(getValue(index)) - ps.width/2,
                            scaleLoc.y - ps.height/(isLRBundle(index)?2:1),
                            ps.width, ps.height);
    }
    else return null;
  }

  private boolean isLRBundle(int index) {
    return index == 0 || index == values.size()-1;
  }

  private void rMetric ()
  {
    if (scaleSize < 0)
    {
      Insets ins = getInsets();
      scaleSize  = width - ins.left - ins.right - 2*gapx - 2;
      scaleLoc.x = ins.left + gapx + 1;
      scaleLoc.y = ins.top + gapy + (height - ins.top - ins.bottom - psH)/2 + 1;
      titleLoc   = scaleLoc.y + 4*netSize;
    }
  }

  private static final short SHOW_TITLE = 1024;

  static {
    try {
      LwToolkit.loadObjs("obj", LwToolkit.getProperties("tr.properties"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}

class LwTrackerEl
{
  LwTrackerEl(int v) {
    value = v;
  }
  int value, state = LwTracker.ST_OUTSURFACE;
  LwComponent tooltip = null;
}
