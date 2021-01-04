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
import java.util.*;

import org.zaval.lw.event.*;
import org.zaval.misc.*;
import org.zaval.lw.mask.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

/**
 *  This panel provides ability to cutomize color using different ways :
 *  <ul>
 *    <li>By the specifying RGB color components in the text fields.</li>
 *    <li>By the specifying RGB color components using sliders.</li>
 *    <li>
 *      By the selecting one of the predefined color. The predefined colors set is specified by
 *      the properties. The properties has the following format:
 *      <pre>
 *         colors.rows=<number of rows>
 *         colors.cols=<number of columns>
 *
 *         colors.row.<row number>=<red1>,<green1>,<blue1>;<red2>,<green2>,<blue2>...<redN>,<greenN>,<blueN>
 *         ...
 *      </pre>
 *    </li>
 *  </ul>
 *  Register an action listener to be notified whenever a selected color has been changed.
 */
public class LwColorPanel
extends LwPanel
implements LwActionListener, TextListener
{
  private LwActionSupport support;
  private LwMaskTextField redField, blueField, greenField;
  private LwSlider        redSlider, blueSlider, greenSlider;
  private LwCanvas        selected;
  private LwColorsList    colors;
  private Object          lock__;

  private static final LwView BUNDLE_VIEW    = new LwImgSetRender("img/slider.gif", 11, 11, 9, 8, LwView.ORIGINAL);
  private static final String DEF_PROPS_FILE = "colors.properties";

 /**
  * Constructs a color panel. In this case the predefined colors set is loaded by
  * "colors.properties" properties file.
  */
  public LwColorPanel() {
    this(Color.red, DEF_PROPS_FILE);
  }

 /**
  * Constructs a color panel with the given selected color and the givent properties file to
  * specify a predefined colors set. The properties file will be fetched relatively "org/zaval/lw"
  * package.
  * @param <code>c</code> the given selected color.
  * @param <code>props</code> the given properties file.
  */
  public LwColorPanel(Color c, String props) {
    this(c, LwToolkit.getProperties(props));
  }

 /**
  * Constructs a color panel with the given selected color and the givent properties to
  * specify a predefined colors set.
  * @param <code>c</code> the given selected color.
  * @param <code>props</code> the given properties.
  */
  public LwColorPanel(Color c, Properties p)
  {
    setInsets(2,2,2,2);
    colors = new LwColorsList(p);
    int boxSize = colors.getBoxSize();
    colors.addSelectionListener(this);
    LwBorderPan tPanel = new LwBorderPan(new LwLabel((String)LwToolkit.getStaticObj("cd.predef")), colors);

    LwPanel sPanel = new LwPanel();
    sPanel.setLwLayout(new LwGridLayout(1, 2, LwToolkit.HORIZONTAL));
    selected = new LwCanvas();
    selected.getViewMan(true).setBorder("br.plain");
    selected.setPSSize(2*boxSize, boxSize);
    sPanel.add (new LwLabel((String)LwToolkit.getStaticObj("cd.selected")));
    sPanel.add (selected);

    LwPanel rgb = new LwPanel();
    MaskValidator rgbValidator = new RGBMaskValidator();
    rgb.setLwLayout(new LwGridLayout(3, 3, LwToolkit.HORIZONTAL | LwToolkit.VERTICAL));
    LwConstraints rgbCon1 = new LwConstraints();
    rgbCon1.insets = new Insets(2, 2, 2, 2);
    rgbCon1.fill = LwToolkit.HORIZONTAL;
    LwConstraints rgbCon2 = new LwConstraints();
    rgbCon2.insets = new Insets(2, 2, 2, 2);
    rgbCon2.fill = LwToolkit.HORIZONTAL | LwToolkit.VERTICAL;

    rgb.add (rgbCon1, new LwLabel((String)LwToolkit.getStaticObj("cd.red")));
    redField = new LwMaskTextField("", "nnn");
    redField.setValidator(rgbValidator);
    redField.getTextModel().addTextListener(this);
    rgb.add (rgbCon1, redField);
    redSlider = new LwSlider();
    redSlider.showTitle(false);
    redSlider.showScale(false);
    redSlider.jumpOnPress(true);
    redSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
    redSlider.setValues(0, 255, new int[] { 0 }, 1, 1);
    redSlider.setPSSize(150, -1);
    redSlider.addActionListener(this);
    rgb.add (rgbCon2, redSlider);

    rgb.add (rgbCon1, new LwLabel((String)LwToolkit.getStaticObj("cd.green")));
    greenField = new LwMaskTextField("", "nnn");
    greenField.setValidator(rgbValidator);
    greenField.getTextModel().addTextListener(this);
    rgb.add (rgbCon1, greenField);
    greenSlider = new LwSlider();
    greenSlider.showTitle(false);
    greenSlider.showScale(false);
    greenSlider.jumpOnPress(true);
    greenSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
    greenSlider.setValues(0, 255, new int[] { 0 }, 1, 1);
    greenSlider.setPSSize(150, -1);
    greenSlider.addActionListener(this);
    rgb.add (rgbCon2, greenSlider);

    rgb.add (rgbCon1, new LwLabel((String)LwToolkit.getStaticObj("cd.blue")));
    blueField = new LwMaskTextField("", "nnn");
    blueField.setValidator(rgbValidator);
    blueField.getTextModel().addTextListener(this);
    rgb.add (rgbCon1, blueField);
    blueSlider = new LwSlider();
    blueSlider.showTitle(false);
    blueSlider.showScale(false);
    blueSlider.jumpOnPress(true);
    blueSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
    blueSlider.setValues(0, 255, new int[] { 0 }, 1, 1);
    blueSlider.setPSSize(150, -1);
    blueSlider.addActionListener(this);
    rgb.add (rgbCon2, blueSlider);

    int w = Math.max(Math.max(sPanel.getPreferredSize().width, tPanel.getPreferredSize().width),
                              rgb.getPreferredSize().width);
    sPanel.setPSSize(w, -1);
    tPanel.setPSSize(w, -1);
    rgb.setPSSize(w, -1);

    add (sPanel);
    add (tPanel);
    add (rgb);

    setSelectedColor(c);
  }

 /**
  * Adds the specified action listener to receive action events from this component.
  * The action event is performed whenever a selected has been changed. The action event
  * stores the new color value in the event data field.
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

  public void actionPerformed(LwActionEvent e)
  {
    if (lock__ == null)
    {
      Object target = e.getSource();
      if (target == redSlider || target == greenSlider || target == blueSlider)
      {
        int r = redSlider.getValue(), g = greenSlider.getValue(), b = blueSlider.getValue();
        setRgbFields(r, g, b);
        colors.select(-1);
        changeBg(new Color(r, g, b));
      }
      else
      if (target == colors)
      {
        LwComponent c = colors.getSelected();
        if (c != null)
        {
          Color color = c.getBackground();
          syncByColor(color.getRed(), color.getGreen(), color.getBlue());
        }
      }
    }
  }

 /**
  * Sets the specified color as the selected color.
  * @param <code>ñ</code> the specified color to set as selected.
  */
  public void setSelectedColor(Color c) {
    Color oldColor = getSelectedColor();
    if (!oldColor.equals (c)) syncByColor (c.getRed(), c.getGreen(), c.getBlue());
  }

 /**
  * Gets a current selected color.
  * @return the current selected color.
  */
  public Color getSelectedColor() {
    return selected.getBackground();
  }

  public void textRemoved (TextEvent e) {
    fieldChanged();
  }

  public void textInserted (TextEvent e) {
    fieldChanged();
  }

  public void textUpdated (TextEvent e)  {
    fieldChanged();
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 4, 4);
  }

  private void fieldChanged()
  {
    if (lock__ == null)
    {
      int r = Integer.parseInt(redField.getText());
      int g = Integer.parseInt(greenField.getText());
      int b = Integer.parseInt(blueField.getText());
      setRgbSliders(r, g, b);
      colors.select(-1);
      changeBg(new Color(r, g, b));
    }
  }

  private void syncByColor(int r, int g, int b)
  {
    if (lock__ == null) lock__ = selected;
    try {
      setRgbFields(r, g, b);
      setRgbSliders(r, g, b);
      changeBg(new Color(r, g, b));
    }
    finally {
      if (lock__ == selected) lock__ = null;
    }
  }

  private void changeBg(Color c)  {
    selected.setBackground(c);
    if (support != null) support.perform (new LwActionEvent(this, c));
  }

  private String completeInt(int v, int f)
  {
    StringBuffer buffer = new StringBuffer(Integer.toString(v));
    while (buffer.length() < f) buffer.insert(0, '0');
    return buffer.toString();
  }

  private void setRgbFields(int r, int g, int b)
  {
    if (lock__ == null) lock__ = redField;
    try {
      redField.setText(completeInt(r, 3));
      greenField.setText(completeInt(g, 3));
      blueField.setText(completeInt(b, 3));
    }
    finally {
      if (lock__ == redField) lock__ = null;
    }
  }

  private void setRgbSliders(int r, int g, int b)
  {
    if (lock__ == null) lock__ = redSlider;
    try {
      redSlider.setValue(r);
      greenSlider.setValue(g);
      blueSlider.setValue(b);
    }
    finally {
      if (lock__ == redSlider) lock__ = null;
    }
  }
}

class LwColorsList
extends LwList
{
  private int boxSize = 20;

  public LwColorsList(Properties p)
  {
    int rows = Integer.parseInt(p.getProperty("colors.rows"));
    int cols = Integer.parseInt(p.getProperty("colors.cols"));

    setLwLayout(new LwGridLayout(rows, cols, LwToolkit.HORIZONTAL | LwToolkit.VERTICAL));
    LwConstraints c1 = new LwConstraints();
    c1.insets = new Insets(3,3,3,3);

    for (int i=0; i<rows; i++)
    {
      /*C#char[]   seps   = {';', ','};*/
      /*C#string[] values = p.getProperty("colors.row." + i).Split(seps);*/
      /*C#for (int j=0;j<values.Length;j+=3)*/

      StringTokenizer st = new StringTokenizer(p.getProperty("colors.row." + i), ",;"); /*java*/
      while (st.hasMoreTokens())                                                        /*java*/
      {
        LwCanvas box = new LwCanvas();
        box.setBackground(new Color(Integer.parseInt(st.nextToken()),   /*java*/
                                    Integer.parseInt(st.nextToken()),   /*java*/
                                    Integer.parseInt(st.nextToken()))); /*java*/
        /*C#box.setBackground(new Color(System.Convert.ToInt32(values[j]),*/
        /*C#                            System.Convert.ToInt32(values[j+1]),*/
        /*C#                            System.Convert.ToInt32(values[j+2])));*/
        box.setPSSize(boxSize, boxSize);
        box.getViewMan(true).setBorder("br.sunken");
        add(c1, box);
      }
    }
  }

  public int getBoxSize() {
    return boxSize;
  }

  protected /*C#override*/ void drawSelMarker(Graphics g)
  {
     int selectedIndex = getSelectedIndex();
     if (selectedIndex >= 0)
     {
       LwComponent c = getSelected();
       g.setColor(getSelectColor());
       g.drawRect(c.getX()-2, c.getY()-2, c.getWidth()+2, c.getHeight()+2);
     }
  }

  protected /*C#override*/ void drawPosMarker(Graphics g) {}
}


class RGBMaskValidator
extends BasicMaskValidator
{
  public /*C#override*/ boolean isValidValue(MaskElement e, String v)
  {
    if (e.getType() == NUMERIC_TYPE)
    {
      try {
        int value = Integer.parseInt (v);
        return value > 0 && value < 256;
      }
      catch (NumberFormatException ee) {
        return false;
      }
    }
    return super.isValidValue(e, v);
  }
}










