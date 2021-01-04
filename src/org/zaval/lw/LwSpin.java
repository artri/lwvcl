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
import org.zaval.lw.mask.*;
import org.zaval.util.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

/**
 * This is spin light weight component that can be used to input a bound integer value.
 * The bound is determined by <code>setBound</code> method. To control current value
 * use <code>setValue</code> and <code>getValue</code> methods. The component performs
 * action event whenever the spin value has been changed. Use <code>addActionListener</code>
 * and <code>removeActionListener</code> methods to register the listener.
 * <p>
 * The spin value validation and representation are defined by LwSpinValidator. Use
 * setSpinValidator method to customize it.
 */
public class LwSpin
extends LwPanel
implements LwLayout, LwActionListener, TextListener
{
  private LwMaskTextField editor;
  private LwStButton  incButton, decButton;
  private int gapx = 1, prevValue, step = 1;
  private LwActionSupport support;

 /**
  * Constructs the componen. In this case the bound is [-10 .. +10].
  */
  public LwSpin()
  {
    LwAdvViewMan m1 = new LwAdvViewMan();
    m1.put("st.out", LwToolkit.getView("sp.b1.out"));
    m1.put("st.over", LwToolkit.getView("sp.b1.over"));
    m1.put("st.pressed", LwToolkit.getView("sp.b1.pressed"));
    m1.setBorder (LwToolkit.getView("br.etched"));
    incButton = new LwSpinButton(this);
    incButton.fireByPress(true, 150);
    incButton.setViewMan(m1);
    incButton.addActionListener(this);

    LwAdvViewMan m2 = new LwAdvViewMan();
    m2.put("st.out", LwToolkit.getView("sp.b2.out"));
    m2.put("st.over", LwToolkit.getView("sp.b2.over"));
    m2.put("st.pressed", LwToolkit.getView("sp.b2.pressed"));
    m2.setBorder (LwToolkit.getView("br.etched"));
    decButton = new LwSpinButton(this);
    decButton.fireByPress(true, 150);
    decButton.setViewMan(m2);
    decButton.addActionListener(this);

    editor = new LwMaskTextField();
    editor.setValidator(null);
    add (decButton);
    add (editor);
    add (incButton);
    setSpinValidator(new LwSpinValidator());
    getTextModel().addTextListener(this);
  }

 /**
  * Gets the text model.
  * @return a text model.
  */
  public TextModel getTextModel () {
    return editor.getTextModel();
  }

 /**
  * Enables or disables the loop mode. If the loop mode is enabled than a spin value
  * will grow from minimal to maximal or from maximal to minimum values.
  * @param <code>b</code> <code>true</code> to enable the loop mode.
  */
  public void enableLoop(boolean b) {
    bits = MathBox.getBits(bits, LOOP_BIT, b);
  }

 /**
  * Returns the loop mode.
  * @return a loop mode
  */
  public boolean isLoopEnabled() {
    return MathBox.checkBit(bits, LOOP_BIT);
  }

 /**
  * Sets the specified step. The step is used as the spin value increment or decrement
  * whenever a spin button is pressed.
  * @param <code>s</code> the specified step.
  */
  public void setStep(int s) {
    step = s;
  }

 /**
  * Gets the current step.
  * @return a current step
  */
  public int getStep() {
    return step;
  }

 /**
  * Adds the action events listener. The action event is performed when a spin value has
  * been changed.
  * @param <code>l</code> the action events listener.
  */
  public void addActionListener(LwActionListener l) {
    if (support == null) support = new LwActionSupport();
    support.addListener(l);
  }

 /**
  * Removes the action events listener so it no longer get action events from the component.
  * @param <code>l</code> the action events listener.
  */
  public void removeActionListener(LwActionListener l) {
    if (support != null) support.removeListener(l);
  }

  public void componentAdded(Object id, Layoutable lw, int index) { }
  public void componentRemoved(Layoutable lw, int index) {}

  public Dimension calcPreferredSize(LayoutContainer target)
  {
    Dimension ps1 = editor.getPreferredSize();
    Dimension ps2 = incButton.getPreferredSize();
    Dimension ps3 = decButton.getPreferredSize();
    return new Dimension (ps1.width + Math.max (ps2.width, ps3.width) + gapx,
                          Math.max(ps1.height, ps2.height + ps3.height));
  }

  public void layout(LayoutContainer target)
  {
    Insets    ins = target.getInsets();
    Dimension ps1 = editor.getPreferredSize();
    Dimension ps2 = incButton.getPreferredSize();
    Dimension ps3 = decButton.getPreferredSize();
    int mBtWidth = Math.max (ps2.width, ps3.width);

    editor.setLocation(ins.left, ins.top);
    editor.setSize    (width  - mBtWidth - ins.left - ins.right - gapx,
                       height - ins.top - ins.bottom);

    incButton.setLocation (width - mBtWidth - ins.right, ins.top);
    incButton.setSize     (mBtWidth, ps2.height);
    decButton.setLocation (width - mBtWidth - ins.right,  height - ps3.height - ins.bottom);
    decButton.setSize     (mBtWidth, ps3.height);
  }

  public void actionPerformed (LwActionEvent e)
  {
    if (e.getSource() == incButton) setValue(getValue() + step);
    else
    if (e.getSource() == decButton) setValue(getValue() - step);
  }

 /**
  * Gets the current value.
  * @return a current value.
  */
  public int getValue () {
    return getSpinValidator().fetchValue(editor.getText().trim());
  }

 /**
  * Sets the specified value.
  * If the loop mode is enabled than a case when the new value is less than minimum the
  * method sets maximum value as the current or if the new value is greater than
  * maximum the method sets minimum value as the current.
  * @param <code>v</code> the new current value.
  */
  public void setValue (int v)
  {
    boolean b = isLoopEnabled();
    int min = getMinValue(), max = getMaxValue();
    if (v < min) v = b?max:min;
    if (v > max) v = b?min:max;
    int prev = getValue ();
    if (prev != v)
    {
      prevValue = prev;
      editor.setText(getSpinValidator().createValue(v, ((MaskedText)getTextModel()).getMask()));
      repaint();
    }
  }

 /**
  * Gets the minimum value. The current value cannot be less the minimum value.
  * @return a minimum value.
  */
  public int getMinValue () {
    return getSpinValidator().getMinValue();
  }

 /**
  * Gets the maximum value. The current value cannot be greater the maximum value.
  * @return a maximum value.
  */
  public int getMaxValue () {
    return getSpinValidator().getMaxValue();
  }

 /**
  * Gets the spin validator. The current validator is a mask handler that defines
  * a spin value representation and validation algorithms.
  * @return a spin validator.
  */
  public LwSpinValidator getSpinValidator() {
    return (LwSpinValidator)editor.getValidator();
  }

 /**
  * Sets the specified spin validator. The validator is a mask handler that defines
  * a spin value representation and validation algorithms.
  * @param <code>v</code> the specified spin validator.
  */
  public void setSpinValidator(LwSpinValidator v)
  {
    LwSpinValidator old = getSpinValidator();
    if (old != v)
    {
      int value = (old == null)?0:getValue();
      editor.setValidator(v);
      editor.setMask(v.createMask());
      setValue(value);
      repaint();
    }
  }

 /**
  * Sets the specified bound. The bound defines minimum and maximum values.
  * The current value cannot be less minimum and greater the maximum values.
  * @param <code>min</code> the specified minimum value.
  * @param <code>max</code> the specified maximum value.
  */
  public void setBound (int min, int max)
  {
    if (min >= max) throw new IllegalArgumentException();

    if (getMaxValue() != max || getMinValue() != min)
    {
      getSpinValidator().setBound(min, max);
      editor.setMask(getSpinValidator().createMask());
      setValue (getValue());
      repaint();
    }
  }

  public void textRemoved  (TextEvent e) { valueChanged(); }
  public void textInserted (TextEvent e) { valueChanged(); }
  public void textUpdated  (TextEvent e) { valueChanged(); }

  protected /*C#override*/ LwLayout getDefaultLayout () {
    return this;
  }

 /**
  *  The method is called whenever the spin value has been changed.
  */
  protected /*C#virtual*/ void valueChanged()
  {
    if (editor.getMask().length() > 0)
    {
      int value = getValue();
      if (prevValue != value)
      {
        if (support != null) support.perform (new LwActionEvent(this, new Integer(prevValue)));
        prevValue = value;
      }
    }
  }

  private static final short LOOP_BIT = 128;
}

class LwSpinButton
extends LwSpeedButton
{
  private int saveY = 0;
  private LwSpin target;

  public LwSpinButton(LwSpin s) {
    target = s;
  }

  public /*C#override*/ void startDragged(LwMouseMotionEvent e)
  {
    super.startDragged(e);
    saveY = e.getY();
  }

  public /*C#override*/ void mouseDragged(LwMouseMotionEvent e)
  {
    super.mouseDragged(e);
    int y = e.getY();
    target.setValue(target.getValue() + target.getStep()*(saveY - y));
    saveY = y;
  }
}



