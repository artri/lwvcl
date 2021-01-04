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
import org.zaval.misc.*;
import org.zaval.lw.mask.*;
import org.zaval.data.*;
import org.zaval.data.event.*;

public class LwFontPanel
extends LwPanel
implements LwActionListener, TextListener
{
  private LwActionSupport support;
  private LwCheckbox      boldBox, italicBox;
  private LwList          list;
  private LwLabel         sampleText;
  private LwMaskTextField fsField;
  private boolean         lock;

  public LwFontPanel()
  {
    setInsets(2, 2, 2, 2);
    list  = new LwList();
    String[]  fonts = LwToolkit.getFontList();
    for (int i=0; i<fonts.length; i++)
      list.add (fonts[i]);
    list.select(0);
    list.addSelectionListener(this);
    LwPanel listPanel = new LwBorderPan(new LwLabel((String)LwToolkit.getStaticObj("fd.family")), list);
    add (LwBorderLayout.CENTER, new LwScrollPan(listPanel));

    boldBox   = new LwCheckbox((String)LwToolkit.getStaticObj("fd.bold"));
    italicBox = new LwCheckbox((String)LwToolkit.getStaticObj("fd.italic"));
    boldBox.getSwitchManager().addActionListener(this);
    italicBox.getSwitchManager().addActionListener(this);
    LwPanel    boxCenter = new LwPanel();
    boxCenter.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.VERTICAL, 2, 2));
    boxCenter.add (boldBox);
    boxCenter.add (italicBox);
    LwPanel boxPanel = new LwBorderPan(new LwLabel((String)LwToolkit.getStaticObj("fd.style")), boxCenter);
    LwPanel rightPanel = new LwPanel();
    rightPanel.setLwLayout(new LwBorderLayout(4, 4));
    rightPanel.add (LwBorderLayout.NORTH, boxPanel);

    fsField = new LwMaskTextField("14", "nn");
    fsField.getTextModel().addTextListener(this);
    LwPanel fieldPanel = new LwPanel ();
    fieldPanel.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.CENTER, LwToolkit.HORIZONTAL, 4, 4));
    fieldPanel.add(new LwLabel("Font Size:"));
    fieldPanel.add(fsField);
    fsField.setPSSize(3*fsField.getPreferredSize().width, -1);
    rightPanel.add (LwBorderLayout.SOUTH, fieldPanel);
    add (LwBorderLayout.EAST, rightPanel);

    sampleText = new LwLabel ("Sample text ...");
    LwPanel samplePanel = new LwPanel();
    samplePanel.setLwLayout(new LwBorderLayout(2, 2));
    samplePanel.add (LwBorderLayout.NORTH, new LwLabel((String)LwToolkit.getStaticObj("fd.sample")));
    LwPanel sampleTextPanel = new LwPanel();
    sampleTextPanel.setPSSize(-1, LwToolkit.FONT_METRICS.getHeight()*2);
    sampleTextPanel.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 2, 2));
    sampleTextPanel.getViewMan(true).setBorder("br.dot");
    sampleTextPanel.add(sampleText);
    samplePanel.add (LwBorderLayout.CENTER, sampleTextPanel);
    add (LwBorderLayout.SOUTH, samplePanel);

    syncSampleFont();
  }

  public void addActionListener(LwActionListener l) {
    if (support == null) support = new LwActionSupport();
    support.addListener(l);
  }

  public void removeActionListener(LwActionListener l) {
    if (support != null) support.removeListener(l);
  }

  public Font getSelectedFont() {
    return sampleText.getTextRender().getFont();
  }

  public void setSelectedFont(Font font)
  {
    Font selectedFont = getSelectedFont();
    if (!selectedFont.equals(font))
    {
      lock = true;
      try {
        String name = font.getFamily();
        int index = 0;
        for (;index < list.count() && !((LwLabel)list.get(index)).getText().equals(name); index++);
        if (index >= list.count()) throw new IllegalArgumentException ();
        list.select(index);
        fsField.setText(String.valueOf(font.getSize()));
        boldBox.setState(font.isBold());
        italicBox.setState(font.isItalic());
        selectedFont = font;
        repaint();
        if (support != null) support.perform (new LwActionEvent(this, selectedFont));
      }
      finally {
        lock = false;
      }
    }
  }

  public void actionPerformed(LwActionEvent e)
  {
    Object target = e.getSource();
    if (target == boldBox)
    {
      syncSampleFont();
    }
    else
    if (target == italicBox)
    {
      syncSampleFont();
    }
    else
    if (target == list)
    {
      syncSampleFont();
    }
  }

  private void syncSampleFont()
  {
    if (!lock)
    {
      int  style = Font.PLAIN;
      style |= boldBox.getState()?Font.BOLD:0;
      style |= italicBox.getState()?Font.ITALIC:0;
      LwLabel selected = (LwLabel)list.getSelected();
      Font selectedFont = new Font(selected.getText(), style, Integer.parseInt(fsField.getText()));
      sampleText.getTextRender().setFont(selectedFont);
      repaint();
      if (support != null) support.perform (new LwActionEvent(this, selectedFont));
    }
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
    //return new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL, 4, 4);
    return new LwBorderLayout(8, 4);
  }

  private void fieldChanged() {
    syncSampleFont();
  }
}



