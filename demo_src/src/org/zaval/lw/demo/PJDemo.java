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
package org.zaval.lw.demo;

import org.zaval.data.*;
import org.zaval.lw.event.*;
import org.zaval.lw.*;
import org.zaval.misc.*;
import org.zaval.lw.grid.*;
import org.zaval.lw.tree.*;

public class PJDemo
implements LwActionListener
{
  private static int pageNumber;
  private static LwStButton nextButton;
  private static LwStButton prevButton;
  private static LwContainer root = null;
  private static LwComponent [] pages = null;
  private static int length = 4;

  public static void main(String [] args)
  {
    LwFrame l = new LwFrame();
    l.setTitle("Zaval LwVCL v3.0 core package demo");
    l.setIconImage(LwToolkit.getImage("demo/ipaq/lw.gif"));
    l.setResizable(false);
    l.addWindowListener(new WL());

    root = l.getRoot();
    root.setLwLayout(new LwBorderLayout(2, 2));

    LwActionListener listener = new PJDemo();
    nextButton  = new LwLink(">>>");
    prevButton  = new LwLink("<<<");
    nextButton.addActionListener(listener);
    prevButton.addActionListener(listener);
    prevButton.setEnabled(false);

    root.add (LwBorderLayout.WEST, new LwImage(LwToolkit.getImage("demo/ipaq/img.gif")));
    LwPanel buttons = new LwPanel();
    buttons.setInsets(1, 4, 1, 4);
    buttons.setLwLayout(new LwBorderLayout());
    buttons.add(LwBorderLayout.EAST, nextButton);
    buttons.add(LwBorderLayout.WEST, prevButton);
    root.add (LwBorderLayout.SOUTH, buttons);

    pages = new LwComponent[length];
    pages[0] = getFirstPage();
    pages[1] = getSecondPage();
    pages[2] = getThirdPage();
    pages[3] = getForthPage();
    pageNumber = 0;
    root.add(LwBorderLayout.CENTER, pages[0]);
    nextButton.requestFocus();


    l.setSize(240, 290);
    l.setVisible(true);
  }

  public void actionPerformed(LwActionEvent e)
  {
      if(e.getSource() ==  nextButton)
      {
        if(pageNumber < length-1)
        {
          root.remove(root.indexOf(pages[pageNumber]));
          pageNumber++;
          if(pageNumber == length-1)
          {
            nextButton.setEnabled(false);
            prevButton.requestFocus();
          }

          if(pageNumber == 1) prevButton.setEnabled(true);
          root.add(LwBorderLayout.CENTER, pages[pageNumber]);
          root.repaint();
        }
      }
      else
      if(e.getSource() == prevButton)
      {
        if(pageNumber > 0)
        {
          root.remove(root.indexOf(pages[pageNumber]));
          pageNumber--;
          if(pageNumber == length-2) nextButton.setEnabled(true);
          if(pageNumber == 0)
          {
            prevButton.setEnabled(false);
            nextButton.requestFocus();
          }

          root.add(LwBorderLayout.CENTER, pages[pageNumber]);
          root.repaint();
        }
      }
  }

  private static LwComponent getFirstPage()
  {
    LwPanel grid = new LwPanel();
    grid.setLwLayout(new LwGridLayout(2, 1));

    LwConstraints c = new LwConstraints();
    c.fill = LwToolkit.HORIZONTAL|LwToolkit.VERTICAL;

//--------------
    LwPanel checkboxPanel = new LwPanel();
    checkboxPanel.setLwLayout(new LwGridLayout(2, 1));

    LwPanel radioPanel = new LwPanel();
    radioPanel.setLwLayout(new LwFlowLayout());

    LwContainer c51 = new LwPanel();
    c51.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.HORIZONTAL));
    LwGroup    gr  = new LwGroup();
    LwCheckbox ch1 = new LwCheckbox("Yes");
    ch1.setBoxType(LwCheckbox.RADIO);
    LwCheckbox ch2 = new LwCheckbox("No");
    ch2.setBoxType(LwCheckbox.RADIO);
    ch1.setSwitchManager(gr);
    ch2.setSwitchManager(gr);
    gr.setSelected(ch2);
    c51.add(ch1);
    c51.add(ch2);

    radioPanel.add(c51);

    LwPanel checkPanel = new LwPanel();
    LwCheckbox ch5 = new LwCheckbox("Extensions included");
    ch5.setState(true);
    LwCheckbox ch6 = new LwCheckbox("Swing");
    ch6.setEnabled(false);
    checkPanel.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.HORIZONTAL));
    checkPanel.add(ch5);
    checkPanel.add(ch6);

    checkboxPanel.add(radioPanel);
    checkboxPanel.add(checkPanel);

    grid.add(c, checkboxPanel);

//----------------------

    LwPanel listPanel = new LwPanel();
    listPanel.setLwLayout(new LwFlowLayout());
    listPanel.setInsets(0,5,5,5);

    LwList list = new LwList();
    list.getViewMan(true).setBorder(new LwBorder(2));
    list.add("Text only");
    list.add(makeImageLabel("demo/ipaq/lw.gif", "Image"));
    list.add(new LwLabel(new Text("MultiLine\ntext item")));
    list.select(1);
    listPanel.add(list);

    LwPanel comboPanel = new LwPanel();
    comboPanel.setLwLayout(new LwFlowLayout());
    comboPanel.setInsets(5, 5, 5, 5);

    LwCombo combo = new LwCombo();
    combo.getList().add("Red");
    combo.getList().add("Yellow");
    combo.getList().add("Green");
    combo.getList().select(1);

    comboPanel.add(combo);

    LwPanel listCombo = new LwPanel();
    listCombo.setLwLayout(new LwGridLayout(2, 1));
    listCombo.add(listPanel);
    listCombo.add(comboPanel);

//--------------------------

    LwPanel scrollPanel = new LwPanel();
    scrollPanel.setLwLayout(new LwFlowLayout());
    LwTextField text = new LwTextField("Zaval Light-Weight Visual Components Library\n(LwVCL) is a pure Java alternative\nto humble AWT-based and SWING-based GUI interfaces\nfor wide ranges of platforms,\nincluding J2SE, PersonalJava and\nJ2ME.\n\nLwVCL can be used on wide range of PersonalJava\ncompatible devices, including iPAQ,\nZaurus and top models of mobil phones\nwith the same API as it is used in\nJ2SE application; our library is most\nefficient way to develop highly scalable\nGUI applications from PersonalJava to any\nJ2SE applications.");
    LwScrollPan scroll = new LwScrollPan(text);
    scroll.setPSSize(125, 115);
    scrollPanel.add(scroll);

    LwPanel complexPanel = new LwPanel();
    complexPanel.setLwLayout(new LwGridLayout(1, 2));
    complexPanel.add(listCombo);
    complexPanel.add(scrollPanel);

    grid.add(c, complexPanel);

    LwLabel header = new LwLabel(new Text("The  most  simple  components  that\ncan be used in your applications.\n"));
    header.setPSSize(209, 50);

    LwBorderPan page2 = new LwBorderPan();
    page2.add(LwBorderPan.CENTER, grid);
    page2.add(LwBorderPan.TITLE, new LwLabel("Simple components"));

    LwPanel page = new LwPanel();
    page.setLwLayout(new LwBorderLayout());
    page.add(LwBorderLayout.NORTH, header);
    page.add(LwBorderLayout.SOUTH, page2);

    return page;
  }

  private static LwComponent getSecondPage(){
    LwBorderPan page = new LwBorderPan();
    LwPanel sc1 = new LwPanel();
    sc1.getViewMan(true).setBorder("br.sunken");
    sc1.getViewMan(true).setBg(new LwImgRender(LwToolkit.getImage("demo/cosmo3.jpg"), LwImgRender.STRETCH));
    LwPanel sc2 = new LwPanel();
    sc2.getViewMan(true).setBorder("br.sunken");
    sc2.getViewMan(true).setBg(new LwImgRender(LwToolkit.getImage("demo/cosmo2.jpg"), LwImgRender.STRETCH));
    LwSplitPan sp1 = new LwSplitPan(sc1, sc2, LwToolkit.HORIZONTAL);
    sp1.setGripperLoc(91);
    LwPanel sc3 = new LwPanel();
    sc3.getViewMan(true).setBorder("br.sunken");
    sc3.getViewMan(true).setBg(new LwImgRender(LwToolkit.getImage("demo/cosmo1.jpg"), LwImgRender.STRETCH));
    LwSplitPan c2 = new LwSplitPan(sp1, sc3, LwToolkit.VERTICAL);
    c2.setGripperLoc(92);
    page.add(LwBorderPan.CENTER, c2);
    page.add(LwBorderPan.TITLE, new LwLabel("Splitters"));
    return page;
  }

  private static LwComponent getThirdPage()
  {
    Matrix m2 = new Matrix(2, 2);
    LwGrid g2 = new LwGrid(m2);
    LwGridCaption cap1 = new LwGridCaption(g2);
    cap1.putTitle (0, "Title 1");
    cap1.putTitle (1, "Title 2");
    g2.add(LwGrid.TOP_CAPTION_EL, cap1);

    g2.usePsMetric(true);
    g2.setPosController(null);
    g2.setViewProvider(new PJCompViews());
    g2.setEditorProvider(new PJCompEditors());

    Item o = new Item("org");
    Tree cont = new Tree(o);
    Item z = new Item("zaval");
    cont.add (o, z);
    cont.add (z, new Item("data"));
    cont.add (z, new Item("lw"));
    cont.add (z, new Item("misc"));
    LwTree tree = new LwTree(cont);

    m2.put(0, 1, tree);

    Matrix m = new Matrix(2, 2);
    LwGrid g = new LwGrid(m);
    g.usePsMetric(true);
    m.put(0, 0, "Zaurus");
    m.put(0, 1, "Linux");
    m.put(1, 0, "iPAQ");
    m.put(1, 1, "PocketPC");
    g.setEditorProvider(new PJGE());

    LwButton button = new LwButton("Button");
    button.setPSSize(80,-1);
    m2.put(1, 0, button);
    m2.put(1, 1, g);

    LwPanel cc1 = new LwPanel();
    cc1.getViewMan(true).setView(new LwImgRender(LwToolkit.getImage("demo/cosmo1.jpg"), LwImgRender.STRETCH));
    cc1.setPSSize(button.getPreferredSize().width, tree.getPreferredSize().height);
    m2.put(0, 0, cc1);

    java.awt.Dimension ps = g2.getPreferredSize();
    g2.setSize(ps.width, ps.height);

    LwLabel header = new LwLabel(new Text("You have complete control over child\ncomponents."));
    header.setPSSize(-1, 42);

    LwBorderPan page2 = new LwBorderPan();
    page2.add(LwBorderPan.CENTER, g2);
    page2.add(LwBorderPan.TITLE, new LwLabel("Complex components"));

    LwPanel page = new LwPanel();
    page.setLwLayout(new LwBorderLayout());
    page.add(LwBorderLayout.NORTH, header);
    page.add(LwBorderLayout.CENTER, page2);

    return page;
  }

  private static LwComponent getForthPage()
  {
    LwBorderPan page = new LwBorderPan();

    LwLabel x = new LwLabel(new LwWrappedText(new Text("Credits:\n  Sharp Electronics\n  Sun Microsystems, Inc.\n\nCopyright (C) 2001-2002  Zaval CE Group (http://www.zaval.org)\n\nThis program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License (version 2) as published by the Free Software Foundation.\n\nThis program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.")));
    x.setPSSize (183, 540);
    LwPanel y = new LwPanel();
    y.setLwLayout(new LwBorderLayout());
    LwPanel up = new LwPanel();
    up.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.TOP, LwToolkit.HORIZONTAL));
    up.add(new LwImage(LwToolkit.getImage("demo/ipaq/lwvcl.gif")));
    up.add(new LwLabel(new Text("Zaval LwVCL\n        v" + LwToolkit.getVersion ())));
    y.add(LwBorderLayout.NORTH, up);
    y.add(LwBorderLayout.SOUTH, x);

    LwScrollPan scroll = new LwScrollPan(y);
    scroll.getViewMan(true).setBorder("br.etched");

    page.add(LwBorderPan.CENTER, scroll);
    page.add(LwBorderPan.TITLE, new LwLabel("About Zaval LwVCL"));

    return page;
  }

  private static LwComponent makeImageLabel(String img, String lab)
  {
    LwPanel c = new LwPanel();
    c.setInsets (2,2,2,2);
    c.setLwLayout(new LwFlowLayout());
    c.add(new LwImage(LwToolkit.getImage(img)));
    c.add(new LwLabel(lab));
    return c;
  }
}

class PJCompViews
implements LwGridViewProvider
{
  private LwCompRender render;

  public PJCompViews() {
    render = new LwCompRender(null);
  }

  public java.awt.Color getCellColor (int row, int col) {
    return null;
  }

  public int getXAlignment (int row, int col) {
    return Alignment.CENTER;
  }

  public int getYAlignment (int row, int col) {
    return Alignment.CENTER;
  }

  public LwView getView(int row, int col, Object o) {
    render.setTarget(o);
    return render;
  }
}

class PJCompEditors
implements LwEditorProvider
{
  public LwComponent getEditor(int row, int col, Object o)  {
    return (((LwComponent)o).canHaveFocus())?(LwComponent)o:null;
  }

  public Object fetchEditedValue (int row, int col, LwComponent editor) {
    return editor;
  }

  public boolean shouldStartEdit (int row, int col, LwAWTEvent e) {
    return true;
  }
}


class PJGE
extends LwDefEditors
{
  public LwComponent getEditor(int row, int col, Object o)
  {
     LwComponent c = super.getEditor(row, col, o);
     c.getViewMan(true).setBorder((LwView)null);
     c.setBackground(java.awt.Color.white);
     return c;
  }
}
