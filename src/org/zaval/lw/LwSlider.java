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
import java.awt.event.*;
import org.zaval.lw.event.*;
import org.zaval.util.*;

/**
 * This is slider component that can be used to select a value from the
 * specified integer interval. The minimum, maximum, intervals sizes and other properties
 * are specified by <code>setValues</code> method. The table below explains the
 * properties meaning in more detail:
 * <table border="1">
 *    <tr>
 *      <td align="center">Property</td>
 *      <td align="center">Description</td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *           max
 *      </td>
 *      <td>
 *           This is maximum possible value of the slider interval. Use <code>getMax</code>
 *           method to get the property value. It is impossible to set a slider value that is
 *           greater than the maximum.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *           min
 *      </td>
 *      <td>
 *           This is minimum possible value of the slider interval. Use <code>getMin</code>
 *           method to get the property value. It is impossible to set a slider value that is
 *           less than the minimum.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *          roughStep
 *      </td>
 *      <td>
 *           This property defines a rough step that is used to change current slider value for
 *           none-interval slider model. The step is used (to change the slider value) when the
 *           left mouse button has been pressed.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *          exactStep
 *      </td>
 *      <td>
 *           This property defines an exact step that is used to change current slider value for
 *           none-interval slider model. The step is used (to change the slider value) when
 *           appropriate key has been pressed or mouse drag event has been performed.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *          interval[]
 *      </td>
 *      <td>
 *          This property defines slider intervals sizes. The intervals are used to
 *          navigate through the slider component for interval model. The painting
 *          process uses the intervals to render scale and the scale titles.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td align="center" valign="center">
 *          isIntervalModel
 *      </td>
 *      <td>
 *          This property defines navigation model. There are two possible models:
 *          <ul>
 *            <li>
 *              None-interval model (if the property is false). In this case the slider
 *              changes its value using the defined steps properties (roughStep and
 *              exactStep steps).
 *            </li>
 *            <li>
 *              Interval model (if the property is true). In this case the slider
 *              changes its value using the interval sizes as the step.
 *            </li>
 *          </ul>
 *      </td>
 *    </tr>
 * </table>
 * <br>
 * The basic components features are described below:
 * <ul>
 *  <li>
 *     It is possible to customize the slider bundle and gauge views by
 *     <code>setView</code> method. The component uses "sl.hbundle"
 *     (horizontal slider), "sl.vbundle" (vertical slider) and "sl.gauge" (gauge view)
 *     static views as the default.
 *  </li>
 *  <li>
 *     The scale and titles rendering can be disabled or enabled by the
 *     <code>showScale</code> and <code>showTitle</code> methods.
 *  </li>
 *  <li>
 *     To render titles the component uses views that are provided by LwViewProvider.
 *     Use <code>setViewProvider</code> method to customize the titles views.
 *  </li>
 *  <li>
 *     Use <code>setOrientation</code> method to define the slider orientation.
 *  </li>
 *  <li>
 *     To listen whenever the slider value has been changed use action events listener.
 *  </li>
 * </ul>
 */
public class LwSlider
extends LwCanvas
implements LwKeyListener, LwMouseListener, LwMouseMotionListener,
           LwViewProvider
{
 /**
  * The bundle view id.
  */
  public static final int BUNDLE_VIEW  = 0;

 /**
  * The gauge view id.
  */
  public static final int GAUGE_VIEW   = 1;

  private int             max, min, value, roughStep, exactStep;
  private int             netSize = 3, gap = 3, orient, scaleStep, psW, psH;
  private int[]           intervals, pl;
  private LwView[]        views = new LwView[2];
  private LwActionSupport support;
  private Color           scaleColor = LwToolkit.darkBlue;
  private LwViewProvider  provider;
  private LwTextRender    render = new LwTextRender("");

 /**
  * Constructs a slider. The slider will use horizontal orientation.
  */
  public LwSlider() {
    this (LwToolkit.HORIZONTAL);
  }

 /**
  * Constructs a slider with the specified orientation. Use LwToolkit.HORIZONTAL or
  * LwToolkit.VERTICAL constant as the orientation property value.
  * @param <code>o</code> the specified orientation.
  */
  public LwSlider(int o)
  {
    provider = this;
    setOrientation(o);
    setView (GAUGE_VIEW, LwToolkit.getView("sl.gauge"));
    setValues (0, 20, new int[] { 0, 5, 10 }, 2, 1);
    render = new LwTextRender("");
    render.setFont (LwToolkit.BFONT);
    render.setForeground (scaleColor);
    setScaleStep(1);
    showScale (true);
    showTitle (true);
  }


  public /*C#override*/ boolean canHaveFocus() {
    return true;
  }


 /**
  * Gets the scale gap. The gap defines distance between the bundle element and the scale
  * element.
  * @return a gap.
  */
  public int getScaleGap (){
    return gap;
  }

 /**
  * Sets the specified scale gap. The gap defines distance between the bundle element
  * and the scale element.
  * @param <code>g</code> the specified scale gap.
  */
  public void setScaleGap (int g)
  {
    if (g != gap) {
      gap = g;
      vrp();
    }
  }

 /**
  * Gets the scale color.
  * @return a scale color.
  */
  public Color getScaleColor () {
    return scaleColor;
  }

 /**
  * Sets the scale color.
  * @param <code>c</code> the scale color.
  */
  public void setScaleColor (Color c)
  {
    if (!c.equals(scaleColor)) {
      scaleColor = c;
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
  * Sets the scale element visibility.
  * @param <code>b</code> the scale element visibility.
  */
  public void showScale (boolean b)
  {
    if (MathBox.checkBit(bits, SHOW_SCALE) != b) {
      bits = MathBox.getBits(bits, SHOW_SCALE, b);
      vrp();
    }
  }

 /**
  * Tests if the slider value will be changed according to the mouse cursor location by
  * mouse presssed event.
  * @return <code>true</code> if the slider value is changed by mouse pressed event
  * according to the mouse cursor location.
  */
  public boolean isJumpOnPress () {
    return MathBox.checkBit(bits, JUMP_ONPRESS);
  }

 /**
  * Sets if the slider value should be changed according to the mouse cursor location by
  * mouse presssed event.
  * @param <code>b</code> use <code>true</code> as the argument value to allow the slider
  * value changing by mouse pressed event according to the mouse cursor location.
  */
  public void jumpOnPress (boolean b) {
    bits = MathBox.getBits(bits, JUMP_ONPRESS, b);
  }

 /**
  * Sets the slider title visibility.
  * @param <code>b</code> the slider title visibility.
  */
  public void showTitle (boolean b)
  {
    if (MathBox.checkBit(bits, SHOW_TITLE) != b) {
      bits = MathBox.getBits(bits, SHOW_TITLE, b);
      vrp();
    }
  }

 /**
  * Sets the specified titles views provider. The provider defines views for the slider
  * titles that are used to render the titles.
  * @param <code>p</code> the specified titles views provider.
  */
  public void setViewProvider(LwViewProvider p)
  {
    if (p != provider)
    {
      provider = p;
      vrp();
    }
  }

  public LwView getView(Drawable d, Object o) {
    render.getTextModel().setText(o!=null?o.toString():"");
    return render;
  }

 /**
  * Sets the specified slider orientation. It is possible to use LwToolkit.HORIZONTAL or
  * LwToolkit.VERTICAL as the orientation value. Draw attention that the method restores
  * appropriate bundle view.
  * @param <code>o</code> the specified slider orientation.
  */
  public void setOrientation(int o)
  {
    if (o != LwToolkit.HORIZONTAL && o != LwToolkit.VERTICAL)
      throw new IllegalArgumentException();

    if (o != orient)
    {
      orient = o;
      setView (BUNDLE_VIEW, o == LwToolkit.HORIZONTAL?LwToolkit.getView("sl.hbundle")
                                                     :LwToolkit.getView("sl.vbundle"));
    }
  }

 /**
  * Gets the slider orientation.
  * @return a slider orientation.
  */
  public int getOrientation() {
    return orient;
  }

 /**
  * Gets the view of the specified slider element.
  * @param <code>id</code> the specified slider element id.
  * @return a view that is used to render the slider element.
  */
  public LwView getView (int id) {
    return views[id];
  }

 /**
  * Sets the view for the specified slider element. There are two elements views can be
  * specified by the method: BUNDLE_VIEW and GAUGE_VIEW.
  * @param <code>id</code> the specified slider element id.
  * @param <code>v</code> the specified view.
  */
  public void setView (int id, LwView v)
  {
    if (v != views[id])
    {
      views[id] = v;
      vrp();
    }
  }

 /**
  * Tests if the interval model is used to navigate through the slider.
  * The interval model defines that navigation possible only between slider intervals.
  * @return <code>true</code> if the interval model is used; otherwise <code>false</code>.
  */
  public boolean isIntervalModel() {
    return MathBox.checkBit(bits, INTERVAL_MODEL_BIT);
  }

 /**
  * Sets the specified navigation model.
  * @param <code>b</code> specified navigation model. Use <code>true</code> to set interval
  * model; otherwise <code>false</code>.
  */
  public void useIntervalModel(boolean b) {
    if (b != isIntervalModel())
      bits = MathBox.getBits (bits, INTERVAL_MODEL_BIT, b);
  }

 /**
  * Gets the current slider value.
  * @return a current slider value.
  */
  public int getValue() {
    return value;
  }

 /**
  * Sets the specified slider value. The value should be less than maximal and greater than
  * minimal possible properties.
  * @param <code>v</code> the specified slider value.
  */
  public void setValue(int v)
  {
    if (v < min || v > max) throw new IllegalArgumentException();

    int prev = value;
    if (value != v)
    {
      value = v;
      if (support != null) support.perform(new LwActionEvent(this, new Integer(prev)));
      repaint();
    }
  }

 /**
  * Gets the maximal possible value.
  * @return a maximal possible value.
  */
  public int getMax () {
    return max;
  }

 /**
  * Gets the minimal possible value.
  * @return a minimal possible value.
  */
  public int getMin() {
    return min;
  }

 /**
  * Gets the number of the slider intervals.
  * @return a number of the slider intervals.
  */
  public int getIntervals () {
    return intervals.length;
  }

 /**
  * Gets the specified interval size.
  * @param <code>i</code> the specified interval index.
  * @return an interval size.
  */
  public int getIntervalSize (int i) {
    return intervals[i];
  }

 /**
  * Sets the slider properties.
  * @param <code>min</code> the specified minimal possible value.
  * @param <code>max</code> the specified maximal possible value.
  * @param <code>intervals</code> the specified intervals sizes array.
  * @param <code>roughStep</code> the specified rough step. The step is used to change
  * the slider value whenever the mouse button has been pressed.
  * @param <code>exactStep</code> the specified exact step. The step is used to change
  * the slider value whenever the mouse has been dragged or appropriate key has been pressed.
  */
  public void setValues (int min, int max, int[] intervals, int roughStep, int exactStep)
  {
    if (roughStep <= 0 || exactStep < 0 || min >= max ||
        min + roughStep > max || min + exactStep > max   ) throw new IllegalArgumentException ();

    for (int i=0, start = min; i<intervals.length; i++)
    {
      start += intervals[i];
      if (start > max || intervals[i] < 0) throw new IllegalArgumentException ();
    }

    this.min  = min;
    this.max  = max;
    this.roughStep = roughStep;
    this.exactStep = exactStep;
    this.intervals = new int[intervals.length];
    System.arraycopy (intervals, 0, this.intervals, 0, intervals.length);
    if (value < min || value > max) setValue(isIntervalModel()?min + intervals[0]:min);
    vrp();
  }

 /**
  * Gets the start value for the specified interval.
  * @param <code>i</code> the specified interval index.
  * @return a start value for the specified interval.
  */
  public int getPointValue(int i)
  {
    int v = min + getIntervalSize(0);
    for (int j=0; j < i; j++, v+=getIntervalSize(j));
    return v;
  }

 /**
  * Adds the specified action listener to receive action events from this slider.
  * The event is performed when the slider value has been changed.
  * @param <code>l</code> the specified action listener.
  */
  public void addActionListener(LwActionListener l) {
    if (support == null) support = new LwActionSupport();
    support.addListener(l);
  }

 /**
  * Removes the specified action listener so it no longer receives action events
  * from this component.
  * @param <code>l</code> the specified action listener.
  */
  public void removeActionListener(LwActionListener l) {
    if (support != null) support.removeListener(l);
  }

  public void keyPressed (LwKeyEvent e)
  {
    boolean b = isIntervalModel();
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_LEFT:
      {
        int v = nextValue(value, exactStep, -1);
        if (v >= min) setValue(v);
      } break;

      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_RIGHT:
      {
        int v = nextValue(value, exactStep, 1);
        if (v <= max) setValue(v);
      } break;
      case KeyEvent.VK_HOME: setValue(b?getPointValue(0):min); break;
      case KeyEvent.VK_END : setValue(b?getPointValue(getIntervals()-1):max); break;
    }
  }

  public void mousePressed (LwMouseEvent e)
  {
    if (LwToolkit.isActionMask(e.getMask()))
    {
      int x = e.getX(), y = e.getY();
      if (!getBundleBounds(value).contains(x, y))
      {
        int l = ((orient == LwToolkit.HORIZONTAL)?x:y);
        int v = loc2value(l);
        if (value != v) setValue(isJumpOnPress() ? v : nextValue(value, roughStep, v<value?-1:1));
      }
    }
  }

  private int correctDt;

  public void startDragged(LwMouseMotionEvent e)
  {
    Rectangle r = getBundleBounds(getValue());
    if (r.contains(e.getX(), e.getY()))
    {
      bits = MathBox.getBits(bits, DRAGGED_BIT, true);
      correctDt = orient == LwToolkit.HORIZONTAL?r.x + r.width/2 - e.getX():r.y + r.height/2 - e.getY();
    }
  }

  public void endDragged  (LwMouseMotionEvent e) {
    bits = MathBox.getBits(bits, DRAGGED_BIT, false);
  }

  public void mouseDragged(LwMouseMotionEvent e)
  {
    if (MathBox.checkBit(bits, DRAGGED_BIT))
      setValue (findNearest(e.getX() + (orient == LwToolkit.HORIZONTAL?correctDt:0),
                            e.getY() + (orient == LwToolkit.HORIZONTAL?0:correctDt)));
  }

  public void keyReleased  (LwKeyEvent e)    {}
  public void keyTyped     (LwKeyEvent e)    {}
  public void mouseClicked (LwMouseEvent e)  {}
  public void mouseEntered (LwMouseEvent e)  {}
  public void mouseExited  (LwMouseEvent e)  {}
  public void mouseReleased(LwMouseEvent e)  {}
  public void mouseMoved   (LwMouseMotionEvent e) {}

  public /*C#override*/ void paint(Graphics g)
  {
    if (pl == null)
    {
      pl = new int[getIntervals()];
      for (int i=0, l=min; i<pl.length; i++)
      {
        l += getIntervalSize(i);
        pl[i] = value2loc(l);
      }
    }

    Insets    ins = getInsets();
    Dimension bs = views[BUNDLE_VIEW].getPreferredSize();
    Dimension gs = views[GAUGE_VIEW].getPreferredSize();
    int       w = width - ins.left - ins.right - 2, h = height - ins.top - ins.bottom - 2;

    if (orient == LwToolkit.HORIZONTAL)
    {
      int topY = ins.top + (h - psH)/2 + 1;
      if (isEnabled())
        views[GAUGE_VIEW].paint(g, ins.left + 1, topY + (bs.height - gs.height)/2, w, gs.height, this);
      else
      {
        g.setColor(Color.gray);
        g.drawRect(ins.left + 1, topY + (bs.height - gs.height)/2, w, gs.height);
      }

      int by = topY;
      topY += bs.height;
      if (MathBox.checkBit(bits, SHOW_SCALE))
      {
        topY += gap;
        g.setColor(isEnabled ()?scaleColor:Color.gray);

        for (int i=min; i<=max; i+=scaleStep)
        {
          int xx = value2loc(i);
          g.drawLine(xx, topY, xx, topY + netSize);
        }

        for (int i=0; i<pl.length; i++)
          g.drawLine(pl[i], topY, pl[i], topY + 2*netSize);

        topY += (2*netSize);
      }

      paintNums(g, topY);
      views[BUNDLE_VIEW].paint(g, getBundleLoc(value), by, this);
    }
    else
    {
      int leftX = ins.left + (w - psW)/2 + 1;
      if (isEnabled())
        views[GAUGE_VIEW].paint(g, leftX + (bs.width - gs.width)/2, ins.top + 1, gs.width, h, this);
      else
      {
        g.setColor(Color.gray);
        g.drawRect(leftX + (bs.width - gs.width)/2, ins.top + 1, gs.width, h);
      }

      int bx = leftX;
      leftX += bs.width;
      if (MathBox.checkBit(bits, SHOW_SCALE))
      {
        leftX += gap;
        g.setColor(scaleColor);

        for (int i=min; i<=max; i+=scaleStep)
        {
          int yy = value2loc(i);
          g.drawLine(leftX, yy, leftX + netSize, yy);
        }
        for (int i=0; i<pl.length; i++)
          g.drawLine(leftX, pl[i], leftX + 2*netSize, pl[i]);

        leftX += (2*netSize);
      }
      paintNums(g, leftX);
      views[BUNDLE_VIEW].paint (g, bx, getBundleLoc (value), this);
    }

    if (hasFocus())
      LwToolkit.getView("br.dot").paint(g, ins.left, ins.top, w + 2, h + 2, this);
  }

 /**
  * Finds nearest slider value where a bundle can be moved by the specified location.
  * @param <code>x</code> the specified x coordinate.
  * @param <code>y</code> the specified y coordinate.
  * @return a nearest value.
  */
  public int findNearest(int x, int y)
  {
    int v = loc2value(orient == LwToolkit.HORIZONTAL?x:y);
    if (isIntervalModel())
    {
      int nearest = Integer.MAX_VALUE, res = 0;
      for (int i=0; i<intervals.length; i++)
      {
        int pv = getPointValue(i);
        int dt = Math.abs(pv - v);
        if (dt < nearest)
        {
          nearest = dt;
          res = pv;
        }
      }
      return res;
    }
    else
    {
      v = exactStep*((v + v%exactStep)/exactStep);
      if (v > max) v = max;
      else
      if (v < min) v = min;
      return v;
    }
  }

 /**
  * Converts the specified slider value to a location. The method returns
  * "x" coordinate for horizontal aligned slider or "y" coordinate for vertical
  * aligned slider.
  * @param <code>v</code> the specified slider value.
  * @return a "x" or "y" location depending on the slider alignment.
  */
  public int value2loc (int v) {
    return (getScaleSize() * (v - min))/(max - min) + getScaleLocation();
  }

 /**
  * Converts the specified location to a slider value. The specified location
  * is "x" coordinate for horizontal aligned slider and "y" coordinate
  * otherwise.
  * @param <code>xy</code> the specified location.
  * @return a slider value.
  */
  public int loc2value (int xy) {
    return min + ((max - min)*(xy - getScaleLocation()))/getScaleSize();
  }

  public /*C#override*/ void invalidate() {
    pl = null;
    super.invalidate();
  }

  protected /*C#override*/ Dimension calcPreferredSize() {
    return new Dimension (psW + 2, psH + 2);
  }

  protected /*C#override*/ void recalc ()
  {
    Dimension ps = views[BUNDLE_VIEW].getPreferredSize();
    int       ns = MathBox.checkBit(bits, SHOW_SCALE)?(gap + 2*netSize):0;
    int       dt = getMax() - getMin();

    int hMax = 0, wMax = 0;
    if (MathBox.checkBit(bits, SHOW_TITLE) && getIntervals()>0)
    {
      for (int i=0; i<getIntervals(); i++)
      {
        LwView    v = provider.getView(this, new Integer(getPointValue(i)));
        Dimension d = v.getPreferredSize();
        if (d.height > hMax) hMax = d.height;
        if (d.width  > wMax) wMax = d.width;
      }
    }

    if (orient == LwToolkit.HORIZONTAL)
    {
      psW = dt*2 + ps.width;
      psH = ps.height + ns + hMax;
    }
    else
    {
      psW = ps.width + ns + wMax;
      psH = dt*2 + ps.height;
    }
  }

  private void paintNums (Graphics g, int loc)
  {
    if (MathBox.checkBit(bits, SHOW_TITLE))
      for (int i=0; i<pl.length; i++)
      {
        LwView render = provider.getView(this, new Integer(getPointValue(i)));
        Dimension d = render.getPreferredSize();
        if (orient == LwToolkit.HORIZONTAL)
          render.paint (g, pl[i] - d.width/2, loc, this);
        else
          render.paint (g, loc, pl[i] - d.height/2, this);
      }
  }

  private int getScaleSize()
  {
    Insets    ins = getInsets();
    Dimension bs = views[BUNDLE_VIEW].getPreferredSize();
    return (orient == LwToolkit.HORIZONTAL?width - ins.left - ins.right - bs.width :height - ins.top - ins.bottom - bs.height)-2;
  }

  private int getScaleLocation()
  {
    Dimension bs = views[BUNDLE_VIEW].getPreferredSize();
    return (orient == LwToolkit.HORIZONTAL?(int)((insets >> 16) & 0xFFFF) + bs.width/2
                                          :(int)(insets & 0xFFFF) + bs.height/2) + 1;
  }

  private int nextValue (int value, int s, int d)
  {
    if (isIntervalModel()) return getNeighborPoint(value, d);
    else
    {
      int v = value + (d * s);
      if (v > max) v = max;
      else
      if (v < min) v = min;
      return v;
    }
  }

  private int getBundleLoc (int v) {
    Dimension bs  = views[BUNDLE_VIEW].getPreferredSize();
    return value2loc(v) - (orient == LwToolkit.HORIZONTAL?bs.width/2:bs.height/2);
  }

  private Rectangle getBundleBounds(int v)
  {
    Insets    ins = getInsets();
    Dimension bs  = views[BUNDLE_VIEW].getPreferredSize();

    if (orient == LwToolkit.HORIZONTAL)
      return new Rectangle (getBundleLoc(v),
                            ins.top + (height - ins.top - ins.bottom - psH)/2 + 1,
                            bs.width, bs.height);
    else
      return new Rectangle (ins.left + (width - ins.left - ins.right - psW)/2 + 1,
                            getBundleLoc(v),
                            bs.width, bs.height);
  }

  private int getNeighborPoint(int v, int d)
  {
    int left = min + getIntervalSize(0), right = getPointValue(getIntervals()-1);
    if (v < left) return left;
    else
    if (v > right) return right;

    if (d > 0)
    {
      int start = min;
      for (int i=0; i<getIntervals(); i++)
      {
        start += getIntervalSize(i);
        if (start > v) return start;
      }
      return right;
    }
    else
    {
      int start = right;
      for (int i=getIntervals()-1; i>=0; i--)
      {
        if (start < v) return start;
        start -= getIntervalSize(i);
      }
      return left;
    }
  }

  private static final short INTERVAL_MODEL_BIT = 128;
  private static final short DRAGGED_BIT        = 256;
  private static final short SHOW_SCALE         = 512;
  private static final short SHOW_TITLE         = 1024;
  private static final short JUMP_ONPRESS       = 2048;
}

