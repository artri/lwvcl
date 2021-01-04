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

import java.awt.*;
import java.io.*;
import java.util.*;
import org.zaval.data.*;
import org.zaval.misc.*;
import org.zaval.lw.*;
import org.zaval.lw.grid.*;
import org.zaval.lw.tree.*;
import org.zaval.lw.mask.*;
import org.zaval.lw.event.*;
import java.awt.event.*;

public class LwComplexPan
{
    static LwImgRender BG = new LwImgRender("demo/bg1.jpg");

    protected static void makeMainPanel(LwContainer root)
    throws Exception
    {
      LwToolkit.loadObjs("obj", LwToolkit.getProperties("demo/demo.properties"));

      root.setLwLayout(new LwBorderLayout());
      LwNotebook notebook = new LwNotebook (Alignment.LEFT);
      notebook.addPage("Borders",   getBordersPanel());
      notebook.addPage("Buttons",   getButtonsPanel());
      notebook.addPage("Splitter",  getSplitPanel());
      notebook.addPage("List",      getListPanel());
      notebook.addPage("TextField", getTextFieldPanel());
      notebook.addPage("Checkbox",  getCheckboxPanel());
      notebook.addPage("Tree",      getTreePanel());
      notebook.addPage("Notebook",  getNotebookPanel());
      notebook.addPage("ComboBox",  getComboBoxPanel());
      notebook.addPage("Scroll",    getScrollPanel());
      notebook.addPage("Grid",      getGridPanel());
      notebook.addPage("Misc",      getMiscPanel());
      notebook.addPage("Tooltip",   getTooltipPanel());

      LwTabRender r = new LwTabRender("TreeGrid");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r,  getTreeGridPanel());

      r = new LwTabRender("Masks");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r, getMaskPanel());

      r = new LwTabRender("Windows");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r, getWinPanel());

      r = new LwTabRender("Misc ext.");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r, getExtMiscPanel());

      r = new LwTabRender("Slider");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r, getSliderPanel());

      r = new LwTabRender("Panels");
      r.setForeground (LwToolkit.darkBlue);
      notebook.addPage(r, getPanelsPanel());

      notebook.getViewMan(true).setBg(BG);
      root.add(LwBorderLayout.CENTER, notebook);
    }

    private static LwTree getTree(boolean full){
      Item o = new Item("org");
      Tree cont = new Tree(o);
      Item z = new Item("zaval");
      cont.add (o, z);
      cont.add (z, new Item("data"));
      cont.add (z, new Item("lw"));
      cont.add (z, new Item("misc"));
      cont.add (z, new Item("util"));
      if(full){
        cont.add(cont.getChildAt(z, 0), new Item("event"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("MatrixEvent"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("MatrixListener"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("TextEvent"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("TextListener"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("TextListenerSupport"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("TreeEvent"));
        cont.add(cont.getChildAt(cont.getChildAt(z, 0), 0), new Item("TreeListener"));

        cont.add(cont.getChildAt(z, 0), new Item("Item"));
        cont.add(cont.getChildAt(z, 0), new Item("ItemDesc"));
        cont.add(cont.getChildAt(z, 0), new Item("Matrix"));
        cont.add(cont.getChildAt(z, 0), new Item("MatrixModel"));
        cont.add(cont.getChildAt(z, 0), new Item("SingleLineTxt"));
        cont.add(cont.getChildAt(z, 0), new Item("Text"));
        cont.add(cont.getChildAt(z, 0), new Item("TextModel"));
        cont.add(cont.getChildAt(z, 0), new Item("Tree"));
        cont.add(cont.getChildAt(z, 0), new Item("TreeModel"));

        cont.add(cont.getChildAt(z, 1), new Item("event"));
        cont.add(cont.getChildAt(z, 1), new Item("grid"));
        cont.add(cont.getChildAt(z, 1), new Item("mask"));
        cont.add(cont.getChildAt(z, 1), new Item("rs"));
        cont.add(cont.getChildAt(z, 1), new Item("tree"));
        cont.add(cont.getChildAt(z, 2), new Item("event"));
        cont.add(cont.getChildAt(z, 3), new Item("event"));
      }
      LwTree tree = new LwTree(cont);
      return tree;
    }

    private static LwGrid getGrid(boolean full)
    {
      Matrix m = full?new Matrix(6, 5):new Matrix(2, 2);

      LwGrid g = new LwGrid(m);
      g.setEditorProvider(new LwGE());
      g.setViewProvider(new LwGV());

      if(full)
      {
        LwGridCaption cap2 = new LwGridCaption(g);
        cap2.putTitle (0, "PDA Model");
        cap2.putTitle (1, "OS");
        cap2.putTitle (2, "Bluetooth");
        cap2.putTitle (3, "RAM");
        cap2.putTitle (4, "ROM");
        g.setBackground (Color.white);
        g.add(LwGrid.TOP_CAPTION_EL, cap2);
        g.getPosController().setOffset(0);
        g.setColWidth(0, 140);
        g.setColWidth(1, 120);
        String ram = ";16 MB;32 MB;48 MB;64 MB";
        m.put(0, 0, "Sharp Zaurus SL-5000D");
        m.put(0, 1, "Linux 2.4 (Embedix)");
        m.put(0, 2, "No");
        m.put(0, 3, "0"+ram);
        m.put(0, 4, "1"+ram);
        m.put(1, 0, "iPAQ Pocket PC H3970");
        m.put(1, 1, "MS Pocket PC 2002");
        m.put(1, 2, "Yes");
        m.put(1, 3, "3"+ram);
        m.put(1, 4, "2"+ram);
        m.put(2, 0, "iPAQ Pocket PC H3950");
        m.put(2, 1, "MS Pocket PC 2002");
        m.put(2, 2, "No");
        m.put(2, 3, "3"+ram);
        m.put(2, 4, "1"+ram);
        m.put(3, 0, "iPAQ Pocket PC H3870");
        m.put(3, 1, "MS Pocket PC 2002");
        m.put(3, 2, "Yes");
        m.put(3, 3, 3+ram);
        m.put(3, 4, 2+ram);
        m.put(4, 0, "iPAQ Pocket PC H3970");
        m.put(4, 1, "MS Pocket PC 2002");
        m.put(4, 2, "No");
        m.put(4, 3, 3+ram);
        m.put(4, 4, 2+ram);
        m.put(5, 0, "iPAQ Pocket PC H3970");
        m.put(5, 1, "MS Pocket PC 2002");
        m.put(5, 2, "No");
        m.put(5, 3, 3+ram);
        m.put(5, 4, 2+ram);
      }
      else
      {
        g.usePsMetric(false);
        m.put(0, 0, "Zaurus SL-5000D");
        m.put(0, 1, "Linux 2.4 (Embedix)");
        m.put(1, 0, "Compaq iPAQ H3870");
        m.put(1, 1, "MS Pocket PC 2002");
        Dimension d = g.getPreferredSize();
        g.setPSSize(280, d.height);
        g.setColWidth(0, 138);
        g.setColWidth(1, 138);
      }
      return g;
    }

    private static LwComponent getBordersPanel()
    {
       LwContainer root = makeRoot();

       LwLabel lab1 = makeTitle(" Borders ...", 10);
       LwPanel c0 = new LwPanel();
       c0.setLocation(20, 40);
       c0.setPSSize(200, 180);
       c0.getViewMan(true).setBorder("br.raised");
       LwContainer c01 = new LwPanel();
       c01.setLocation(20, 20);
       c01.setSize(160, 140);
       c01.getViewMan(true).setBorder("br.sunken");
       LwContainer c02 = new LwPanel();
       c02.setLocation(20, 20);
       c02.setSize(120, 100);
       c02.getViewMan(true).setBorder("br.etched");
       LwContainer c03 = new LwPanel();
       c03.setLocation(20, 20);
       c03.setSize(80, 60);
       c03.getViewMan(true).setBorder("br.plain");
       LwContainer c04 = new LwPanel();
       c04.setLocation(20, 20);
       c04.setSize(40, 20);
       c04.getViewMan(true).setBorder("br.dot");

       c0.add(LwBorderLayout.CENTER, c01);
       c01.add(LwBorderLayout.CENTER, c02);
       c02.add(LwBorderLayout.CENTER, c03);
       c03.add(LwBorderLayout.CENTER, c04);
       LwPanel c1 = new LwPanel();
       c1.setLocation(240, 40);
       c1.setPSSize(200, 180);
       c1.getViewMan(true).setBorder(new LwCustomBorder());
       c1.setLwLayout (new LwBorderLayout());
       c1.add (LwBorderLayout.CENTER, new LwLabel("Custom border ..."));

       LwLabel lab2 = makeTitle(" Titled Borders ...", 250);
       LwBorderPan c2 = new LwBorderPan();
       c2.setLocation(20, 280);
       c2.setPSSize(100, 80);
       c2.add (LwBorderPan.TITLE, new LwLabel("Title 1"));
       LwBorderPan c3 = new LwBorderPan();
       c3.setLocation(140, 280);
       c3.setPSSize(100, 80);
       c3.setXAlignment(Alignment.CENTER);
       c3.add (LwBorderPan.TITLE, new LwCheckbox("Title 2"));
       LwBorderPan c4 = new LwBorderPan();
       c4.setLocation(260, 280);
       c4.setPSSize(100, 80);
       c4.setXAlignment(Alignment.RIGHT);
       c4.add (LwBorderPan.TITLE, new LwLabel("Title 3"));
       LwBorderPan c5 = new LwBorderPan();
       c5.setLocation(20, 380);
       c5.setPSSize(100, 80);
       c5.setTitleAlignment(Alignment.BOTTOM);
       c5.add (LwBorderPan.TITLE, new LwLabel("Title 4"));
       LwBorderPan c6 = new LwBorderPan();
       c6.setLocation(140, 380);
       c6.setPSSize(100, 80);
       c6.setTitleAlignment(Alignment.BOTTOM);
       c6.setXAlignment(Alignment.CENTER);
       c6.add (LwBorderPan.TITLE, new LwLabel("Title 5"));
       LwBorderPan c7 = new LwBorderPan();
       c7.setLocation(260, 380);
       c7.setPSSize(100, 80);
       c7.setTitleAlignment(Alignment.BOTTOM);
       c7.setXAlignment(Alignment.RIGHT);
       c7.add (LwBorderPan.TITLE, new LwLabel("Title 5"));

       root.add(lab1);
       root.add(c0);
       root.add(c1);
       root.add(lab2);
       root.add(c2);
       root.add(c3);
       root.add(c4);
       root.add(c5);
       root.add(c6);
       root.add(c7);
       return root;
    }

    private static LwComponent getButtonsPanel()
    {
       LwContainer root = makeRoot();

       LwLabel lab1 = makeTitle(" Toolbar ...", 10);
       LwContainer toolbar1 = new LwPanel();
       toolbar1.setLocation (20, 50);
       LwFlowLayout toolbar1Layout = new LwFlowLayout();
       toolbar1Layout.setGaps(1, 1);
       toolbar1.setLwLayout(toolbar1Layout);
       toolbar1.getViewMan(true).setBorder("br.raised");
       LwStButton st1 = new LwStButton();

       LwAdvViewMan man1 = new LwAdvViewMan();
       man1.put ("st.over", LwToolkit.getView("f3"));
       man1.put ("st.out", LwToolkit.getView("f2"));
       man1.put ("st.pressed", LwToolkit.getView("f1"));
       st1.setViewMan(man1);

       LwStButton st2 = new LwStButton();
       LwAdvViewMan man2 = new LwAdvViewMan();
       man2.put ("st.over", LwToolkit.getView("l3"));
       man2.put ("st.out", LwToolkit.getView("l2"));
       man2.put ("st.pressed", LwToolkit.getView("l1"));
       st2.setViewMan(man2);
       LwStButton st3 = new LwStButton();
       LwAdvViewMan man3 = new LwAdvViewMan();
       man3.put ("st.over", LwToolkit.getView("m3"));
       man3.put ("st.out", LwToolkit.getView("m2"));
       man3.put ("st.pressed", LwToolkit.getView("m1"));
       st3.setViewMan(man3);
       LwStButton st4 = new LwStButton();
       LwAdvViewMan man4 = new LwAdvViewMan();
       man4.put ("st.over", LwToolkit.getView("lg3"));
       man4.put ("st.out", LwToolkit.getView("lg2"));
       man4.put ("st.pressed", LwToolkit.getView("lg1"));
       st4.setViewMan(man4);
       LwStButton st5 = new LwStButton();
       LwAdvViewMan man5 = new LwAdvViewMan();
       man5.put ("st.over", LwToolkit.getView("p3"));
       man5.put ("st.out", LwToolkit.getView("p2"));
       man5.put ("st.pressed", LwToolkit.getView("p1"));
       st5.setViewMan(man5);

       toolbar1.add (st1);
       toolbar1.add (st2);
       toolbar1.add (st3);
       toolbar1.add (st4);
       toolbar1.add (st5);

       LwLabel lab2 = makeTitle(" Ordinary buttons ...", 120);
       root.add(lab2);
       LwButton b1 = new LwButton("WinButton");
       LwAdvViewMan man6 = new LwAdvViewMan();
       man6.put("button.on", LwToolkit.getView("br.sunken"));
       man6.put("button.off", LwToolkit.getView("br.raised"));
       b1.setViewMan(man6);
       b1.setLocation(20, 160);
       b1.setPSSize(100, -1);
       LwButton b2 = new LwButton("MetalButton");
       b2.setLocation(140, 160);
       b2.setPSSize(100, -1);
       LwButton b3 = new LwButton("TransparentButton");
       b3.setLocation(260, 160);
       b3.setPSSize(120, -1);
       b3.setOpaque(false);

       LwLabel lab3 = makeTitle(" Image buttons ...", 220);
       LwButton b4 = new LwButton(makeImageLabel("tick", "ImageButton"));
       b4.setLocation(20, 260);
       b4.setPSSize(-1, -1);
       LwButton b5 = new LwButton(new LwImage((LwImgRender)LwToolkit.getView("paw")));
       b5.setLocation(150, 260);
       b5.setPSSize(25, 25);
       LwButton b6 = new LwButton((LwComponent)null);
       LwAdvViewMan man7 = new LwAdvViewMan();
       man7.put("button.on", LwToolkit.getView("flag1"));
       man7.put("button.off", LwToolkit.getView("flag2"));
       man7.setBorder("br.etched");
       b6.setViewMan(man7);
       b6.setLocation(200, 260);
       b6.setPSSize(-1, -1);

       LwLabel b7lab = new LwLabel(new Text("BgButton\nMultiLine"));
       b7lab.setOpaque(false);
       LwButton b7 = new LwButton(b7lab);
       b7.getViewMan(true).setBg(new LwImgRender("demo/bg12s.gif"));
       b7.setLocation(250, 260);
       b7.setPSSize(100, -1);

       LwLabel lab4 = makeTitle(" Link buttons ...", 320);
       LwLink link1 = new LwLink("Link button ...");
       link1.setLocation(20, 360);
       link1.setPSSize(-1, -1);
       LwLink link2 = new LwLink("Link button ...");
       link2.setLocation(20, 390);
       link2.setPSSize(-1, -1);
       LwLink link3 = new LwLink("Link MultiLine\nbutton ...");
       link3.setLocation(20, 420);
       link3.setPSSize(-1, -1);

       root.add(lab1);
       root.add(toolbar1);
       root.add(lab2);
       root.add(b1);
       root.add(b2);
       root.add(b3);
       root.add(lab3);
       root.add(b4);
       root.add(b5);
       root.add(b6);
       root.add(b7);
       root.add(lab4);
       root.add(link1);
       root.add(link2);
       root.add(link3);
       return root;
    }

    private static LwComponent getSplitPanel()
    {
      LwPanel sc1 = new LwPanel();
      sc1.getViewMan(true).setBorder("br.sunken");
      sc1.getViewMan(true).setBg(new LwImgRender("demo/cosmo3.jpg", LwImgRender.STRETCH));
      LwPanel sc2 = new LwPanel();
      sc2.getViewMan(true).setBorder("br.sunken");
      sc2.getViewMan(true).setBg(new LwImgRender("demo/cosmo2.jpg", LwImgRender.STRETCH));
      LwSplitPan sp1 = new LwSplitPan(sc1, sc2, LwToolkit.HORIZONTAL);
      sp1.setGripperLoc(250);
      LwPanel sc3 = new LwPanel();
      sc3.getViewMan(true).setBorder("br.sunken");
      sc3.getViewMan(true).setBg(new LwImgRender("demo/cosmo1.jpg", LwImgRender.STRETCH));
      LwSplitPan c2 = new LwSplitPan(sp1, sc3, LwToolkit.VERTICAL);
      c2.setGripperLoc(190);
      return c2;
    }

    private static LwComponent getListPanel()
    {
      LwContainer root = makeRoot();

      LwLabel lab1 = makeTitle(" Simple list...", 10);
      LwList list1 = new LwList();
      list1.setLocation(20, 50);
      list1.setPSSize(-1, -1);
      list1.getViewMan(true).setBorder("br.sunken");
      list1.add("......... Text Item.");
      list1.add(makeImageLabel("flag2", "Image item 0"));
      list1.add(makeImageLabel("tick", "Image item 1"));
      list1.add(makeImageLabel("paw", "Image item 2"));
      list1.add(makeImageLabel("flag1", "Multiline items\nare supported."));
      list1.getPosController().setOffset(0);

      LwList list2 = new LwList();
      list2.setLwLayout(new LwGridLayout(2,2));
      list2.setLocation(180, 50);
      list2.setPSSize(-1, -1);
      list2.getViewMan(true).setBorder("br.sunken");
      list2.add(makeImageLabel("flag2", "Image item 0"));
      list2.add(makeImageLabel("tick", "Image item 1"));
      list2.add(makeImageLabel("paw", "Image item 2"));
      //list2.add(makeImageLabel("flag1", "Multiline items\nare supported."));
      list2.getPosController().setOffset(0);

      LwLabel lab2 = makeTitle(" Components and list component inside list...", 220);
      LwList list3 = new LwList();
      list3.setLocation(20, 260);
      list3.setPSSize(-1, -1);
      list3.setOpaque (false);
      list3.getViewMan(true).setBorder("br.etched");
      list3.add(new LwButton("Button Item"));
      list3.add(new LwCheckbox("Checkbox Item 1"));
      list3.add(new LwCheckbox("Checkbox Item 2"));
      list3.add(new LwCheckbox("Checkbox Item 3"));
      LwTextField tf1 = new LwTextField("TextField(max 15)", 18);
      tf1.getViewMan(true).setBorder("br.sunken");
      list3.add(tf1);
      list3.getPosController().setOffset(0);

      LwList list4 = new LwList();
      list4.setLocation(250, 260);
      list4.setPSSize(200, -1);
      list4.getViewMan(true).setBorder("br.sunken");
      list4.add(makeImageLabel("tick", "Image item 1"));
      list4.add(makeImageLabel("paw", "Image item 2"));
      LwList list41 = new LwList();
      list41.add(new LwButton("Button Item"));
      list41.add(new LwCheckbox("Checkbox Item 1"));
      list41.add(new LwCheckbox("Checkbox Item 2"));
      LwBorderPan list41cont = new LwBorderPan(new LwLabel("Internal List"), list41);
      list4.add (list41cont);
      list4.getPosController().setOffset(0);

      root.add(lab1);
      root.add(list1);
      root.add(list2);
      root.add(lab2);
      root.add(list3);
      root.add(list4);
      return root;
    }

    private static LwComponent getTextFieldPanel()
    {
      LwContainer root = makeRoot();

      LwLabel lab0 = makeTitle(" Text Fields ...", 10);
      LwLabel lab1 = makeComment ("Password (8 chars):", 20, 50, 150);
      LwTextField tf1 = new LwTextField("", 8);
      tf1.getViewMan(true).setBorder("br.plain");
      tf1.setLocation(180, 50);
      tf1.setPSSize(100, -1);
      tf1.setOpaque(false);
      tf1.getViewMan(true).setView(new LwPasswordText(tf1.getTextModel()));

      LwLabel lab2 = makeComment("Single Line (15 chars):", 20, 90, 150);
      LwTextField tf2 = new LwTextField("", 15);
      tf2.getViewMan(true).setBorder("br.sunken");
      tf2.setLocation(180, 90);
      tf2.setPSSize(100, -1);

      LwLabel lab3 = makeComment("Text Area:", 20, 130, 150);
      LwTextField tf3 = new LwTextField("This is text area component\nthat allows multiline text inputting.");
      tf3.getViewMan(true).setBorder("br.sunken");
      tf3.setLocation(180, 130);
      tf3.setPSSize(200, 150);
      tf3.setOpaque(false);

      root.add(lab0);
      root.add(lab1);
      root.add(tf1);
      root.add(lab2);
      root.add(tf2);
      root.add(lab3);
      root.add(tf3);

      return root;
    }

    private static LwComponent getCheckboxPanel()
    {
      LwContainer root = makeRoot();
      LwView rdOn  = LwToolkit.getView("ron1");
      LwView rdOff = LwToolkit.getView("roff1");
      LwView chOn1 = LwToolkit.getView("con1");
      LwView chOff1= LwToolkit.getView("coff1");
      LwView chOn2 = LwToolkit.getView("con2");
      LwView chOff2= LwToolkit.getView("coff2");

      LwLabel lab1 = makeTitle(" Standard and custom radio groups ...", 10);
      LwBorderPan bp1 = new LwBorderPan();
      bp1.setLocation(20, 40);
      bp1.setPSSize(180, -1);
      bp1.setOpaque (false);
      bp1.add(LwBorderPan.TITLE, makeImageLabel("flag1", "Standard radio group"));
      LwContainer c51 = new LwPanel();
      c51.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.VERTICAL));
      bp1.add(LwBorderPan.CENTER, c51);
      LwGroup    gr  = new LwGroup();
      LwCheckbox ch1 = new LwCheckbox("Radio 1");
      ch1.setBoxType(LwCheckbox.RADIO);
      LwCheckbox ch2 = new LwCheckbox("Radio 2");
      ch2.setBoxType(LwCheckbox.RADIO);
      LwCheckbox ch3 = new LwCheckbox("Radio 3");
      ch3.setBoxType(LwCheckbox.RADIO);
      ch1.setSwitchManager(gr);
      ch2.setSwitchManager(gr);
      ch3.setSwitchManager(gr);
      c51.add(ch1);
      c51.add(ch2);
      c51.add(ch3);

      LwBorderPan bp2 = new LwBorderPan();
      bp2.setLocation(220, 40);
      bp2.setPSSize(180, -1);
      bp2.setOpaque (false);
      bp2.add(LwBorderPan.TITLE, new LwLabel("Custom radio group"));
      LwContainer c52 = new LwPanel();
      c52.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.VERTICAL));
      bp2.add(LwBorderPan.CENTER, c52);
      LwGroup    gr2 = new LwGroup();
      LwAdvViewMan m1 = new LwAdvViewMan();
      m1.put("radio.on", rdOn);
      m1.put("radio.off",rdOff);
      LwCheckbox gr2ch1 = new LwCheckbox("Radio 1");
      gr2ch1.setBoxType(LwCheckbox.RADIO);
      gr2ch1.getBox().setViewMan(m1);
      LwAdvViewMan m2 = new LwAdvViewMan();
      m2.put("radio.on", rdOn);
      m2.put("radio.off",rdOff);
      LwCheckbox gr2ch2 = new LwCheckbox("Radio 2");
      gr2ch2.setBoxType(LwCheckbox.RADIO);
      gr2ch2.getBox().setViewMan(m2);
      LwAdvViewMan m3 = new LwAdvViewMan();
      m3.put("radio.on", rdOn);
      m3.put("radio.off",rdOff);
      LwCheckbox gr2ch3 = new LwCheckbox("Radio 3");
      gr2ch3.setBoxType(LwCheckbox.RADIO);
      gr2ch3.getBox().setViewMan(m3);
      gr2ch1.setSwitchManager(gr2);
      gr2ch2.setSwitchManager(gr2);
      gr2ch3.setSwitchManager(gr2);
      c52.add(gr2ch1);
      c52.add(gr2ch2);
      c52.add(gr2ch3);

      LwLabel lab2 = makeTitle(" Standard and custom check boxes ...", 200);
      LwBorderPan  bp3 = new LwBorderPan();
      bp3.setLocation(20, 240);
      bp3.setPSSize(180, -1);
      bp3.setOpaque (false);
      bp3.add(LwBorderPan.TITLE, new LwLabel("Standard check boxes"));
      LwCheckbox ch4 = new LwCheckbox("Check 1");
      LwCheckbox ch5 = new LwCheckbox("Check 2");
      ch5.setEnabled(false);
      LwCheckbox ch6 = new LwCheckbox("Check 3");
      LwPanel c53 = new LwPanel();
      c53.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.VERTICAL));
      c53.add(ch4);
      c53.add(ch5);
      c53.add(ch6);
      bp3.add(LwBorderPan.CENTER, c53);

      LwBorderPan  bp4 = new LwBorderPan();
      bp4.setLocation(220, 240);
      bp4.setPSSize(180, -1);
      bp4.setOpaque (false);
      bp4.add(LwBorderPan.TITLE, new LwLabel("Custom check boxes"));
      LwAdvViewMan m4 = new LwAdvViewMan();
      m4.put("check.on", chOn1);
      m4.put("check.off",chOff1);
      LwCheckbox bp4ch1 = new LwCheckbox("Check 1");
      bp4ch1.getBox().setViewMan(m4);
      LwAdvViewMan m5 = new LwAdvViewMan();
      m5.put("check.on", chOn2);
      m5.put("check.off",chOff2);
      LwCheckbox bp4ch2 = new LwCheckbox("Check 2");
      bp4ch2.getBox().setViewMan(m5);
      LwAdvViewMan m6 = new LwAdvViewMan();
      m6.put("check.on", LwToolkit.getView("flag1"));
      m6.put("check.off", LwToolkit.getView("flag2"));
      LwCheckbox bp4ch3 = new LwCheckbox("Check 3");
      bp4ch3.getBox().setViewMan(m6);
      LwPanel c54 = new LwPanel();
      c54.setLwLayout(new LwFlowLayout(Alignment.LEFT, Alignment.TOP, LwToolkit.VERTICAL));
      c54.add(bp4ch1);
      c54.add(bp4ch2);
      c54.add(bp4ch3);
      bp4.add(LwBorderPan.CENTER, c54);

      bp1.setOpaque(false);
      bp2.setOpaque(false);
      bp3.setOpaque(false);
      bp4.setOpaque(false);

      root.add(lab1);
      root.add(bp1);
      root.add(bp2);
      root.add(lab2);
      root.add(bp3);
      root.add(bp4);
      return root;
    }

    private static LwComponent getTreePanel()
    {
      LwContainer root = makeRoot();

      LwLabel lab1 = makeTitle(" Tree view ...", 10);

      Tree cont = new Tree(new Item("root"));
      cont.add(cont.getRoot(), new Item("Item 1"));
      cont.add(cont.getRoot(), new Item("Item 2"));
      cont.add(cont.getRoot(), new Item("Checkbox View"));
      cont.add(cont.getRoot(), new Item("Bordered Item"));
      cont.add(cont.getChildAt(cont.getRoot(), 0), new Item("Image Item 1.1"));
      cont.add(cont.getChildAt(cont.getRoot(), 2), new Item("Multi line\nitem is supported\ntoo. Item 3.1"));
      cont.add(cont.getChildAt(cont.getRoot(), 2), new Item("Item 3.2"));
      cont.add(cont.getChildAt(cont.getRoot(), 2), new Item("Item 3.3"));
      LwTree tree = new LwTree(cont);
      tree.setViewProvider(new TVP());
      tree.setLocation(20, 40);
      tree.setPSSize(430, 350);
      tree.setOpaque(false);
      tree.getViewMan(true).setBorder("br.etched");
      tree.setSelectionColor(LwToolkit.darkBlue, false);

      root.add(lab1);
      root.add(tree);
      tree.select(cont.getRoot());

      return root;
   }

   private static LwComponent getNotebookPanel()
   {
      LwContainer c1 = makeRoot();
      LwNotebook nb1 = new LwNotebook(Alignment.TOP);
      nb1.addPage(new LwTabRender("Left"), makeNotebook(Alignment.LEFT));
      nb1.addPage(new LwTabRender("Right"), makeNotebook(Alignment.RIGHT));
      nb1.addPage(new LwTabRender("Bottom"), makeNotebook(Alignment.BOTTOM));
      nb1.setOpaque(false);
      nb1.setLocation(20, 20);
      nb1.setPSSize(470, 500);
      c1.add (nb1);
      return c1;
    }

   private static LwComponent getComboBoxPanel()
   {
      LwContainer root = makeRoot();

      LwLabel lab1 = makeTitle(" ComboBox ...", 10);
      LwCombo combo1 = new LwCombo();
      combo1.setLocation(20, 40);
      combo1.setPSSize(120, -1);
      combo1.getList().add("Item 1");
      combo1.getList().add("Item 2");
      combo1.getList().add("Item 3");
      combo1.getList().select(1);

      LwCombo combo2 = new LwCombo();
      combo2.setLocation(220, 40);
      combo2.setPSSize(180, -1);
      combo2.getList().add(makeImageLabel("flag1", "Image Item 1"));
      combo2.getList().add(makeImageLabel("tick", "Image Item 2"));
      combo2.getList().add(makeImageLabel("flag2", "Image Item 3"));
      combo2.getList().select(1);

      LwLabel lab2 = makeTitle(" ComboBox with scroll bar...", 120);
      LwCombo combo3 = new LwCombo();
      combo3.setLocation(20, 150);
      combo3.setPSSize(120, -1);
      combo3.setMaxPadHeight(100);
      for (int i=0; i<20; i++) combo3.getList().add("Item " + i);

      root.add(lab1);
      root.add(combo1);
      root.add(combo2);
      root.add(lab2);
      root.add(combo3);
      return root;
    }

    private static LwComponent getScrollPanel()
    throws Exception
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" TextArea and List inside scroll panel...", 10);

      LwTextField tf1 = new LwTextField(readPropFile());
      LwScrollPan sp1 = new LwScrollPan(tf1);
      sp1.setLocation(20, 40);
      sp1.setPSSize(200, 200);

      LwList list1 = new LwList();
      list1.getViewMan(true).setBorder("br.sunken");
      for (int i=0; i<25; i++) list1.add (new LwLabel("Item " + i));
      list1.getPosController().setOffset(0);
      LwScrollPan sp2 = new LwScrollPan(list1);
      sp2.setLocation(260, 40);
      sp2.setPSSize(220, 200);

      LwLabel lab2 = makeTitle(" Image and tree inside scroll panel...", 260);
      LwImage img1 = new LwImage("demo/cosmo1.jpg");
      img1.getViewMan(true).setBorder("br.plain");
      LwScrollPan sp3 = new LwScrollPan(img1);
      sp3.setLocation(20, 290);
      sp3.setPSSize(200, 230);
      LwTree tree = getTree(true);
      tree.select(tree.getModel().getRoot());
      tree.getViewMan(true).setBorder("br.sunken");
      LwScrollPan sp4 = new LwScrollPan(tree);
      sp4.setLocation(260, 290);
      sp4.setPSSize(230, 230);

      lab1.setOpaque(true);
      root.add(lab1);
      root.add(sp1);
      root.add(sp2);
      root.add(lab2);
      root.add(sp3);
      root.add(sp4);
      return root;
    }

    private static LwComponent getGridPanel()
    {
      LwContainer root = makeRoot();

      LwLabel lab1 = makeTitle(" Simple grid ...", 10);
      Matrix m1 = new Matrix(4, 7);
      LwGrid g1 = new LwGrid(m1);
      g1.setCellInsets(3,3,3,3);
      g1.usePsMetric(true);
      for (int i=0; i<m1.getRows(); i++)
        for (int j=0; j<m1.getCols(); j++)
           m1.put (i, j, "Cell [" + i + "]" + "[" + j + "]");
      Dimension ps = g1.getPreferredSize();
      g1.setSize(ps.width, ps.height);
      g1.setLocation(20, 40);
      g1.getPosController().setOffset(0);

      LwLabel lab2 = makeTitle(" Customized views grid (try to work with the components inside)...", 140);

      Matrix m2 = new Matrix(2, 3);
      LwGrid g2 = new LwGrid(m2);
      LwGridCaption cap1 = new LwGridCaption(g2);
      cap1.putTitle (0, "Title 1");
      cap1.putTitle (1, "Title 2");
      cap1.putTitle (2, "Title 3");
      g2.add(LwGrid.TOP_CAPTION_EL, cap1);
      g2.usePsMetric(true);
      g2.setPosController (null);
      g2.setViewProvider(new LwCompViews());
      g2.setEditorProvider(new LwCompEditors());

      m2.put(0, 1, new LwLabel(new Text("You can put\nmulti-line\ntext, image or\nany component\ninside cell.")));
      LwTree tree = getTree(false);
      m2.put(0, 2, tree);

      LwGrid g1_1 = getGrid(false);
      m2.put(1, 0, g1_1);
      m2.put(1, 1, new LwCheckbox("Off"));
      m2.put(1, 2, new LwButton("Button"));

      LwPanel cc1 = new LwPanel();
      cc1.getViewMan(true).setView(new LwImgRender("demo/cosmo1.jpg", LwImgRender.STRETCH));
      cc1.setPSSize(g1_1.getPreferredSize().width, tree.getPreferredSize().height);
      m2.put(0, 0, cc1);

      ps = g2.getPreferredSize();
      g2.setSize(ps.width, ps.height);
      g2.setLocation(20, 170);

      LwLabel lab3 = makeTitle(" Editable & resizeable grid (click two times on a cell to edit it, except first column)...", 355);
      LwGrid g3 = getGrid(true);
      LwScrollPan sc1 = new LwScrollPan(g3);
      sc1.setPSSize(440, 140);
      sc1.setLocation(20, 385);

      root.add(lab1);
      root.add(g1);
      root.add(lab2);
      root.add(g2);
      root.add(lab3);
      root.add(sc1);
      return root;
    }

    private static LwComponent getMiscPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Progress bar ...", 10);

      LwProgress pr1 = new LwProgress();
      pr1.setValue (8);
      pr1.setLocation(20, 50);

      LwProgress pr2 = new LwProgress();
      pr2.setValue (18);
      pr2.setGap (0);
      pr2.setLocation(20, 80);

      LwProgress pr3 = new LwProgress();
      pr3.setValue (3);
      pr3.setTitleView(new LwTextRender("25%"));
      pr3.setBundleSize (10);
      pr3.setLocation(20, 110);

      LwProgress pr4 = new LwProgress();
      pr4.setValue (3);
      pr4.setMaxValue(10);
      pr4.setBundleColor (Color.green);
      pr4.setOrientation (LwToolkit.VERTICAL);
      pr4.setLocation(350, 50);

      LwProgress pr5 = new LwProgress();
      pr5.setValue (7);
      pr5.setMaxValue(10);
      pr5.setBundleColor (Color.yellow);
      pr5.setOrientation (LwToolkit.VERTICAL);
      pr5.setLocation(380, 50);

      LwProgress pr6 = new LwProgress();
      pr6.setValue (1);
      pr6.setMaxValue(10);
      pr6.setOrientation (LwToolkit.VERTICAL);
      pr6.setLocation(410, 50);

      LwLabel lab2 = makeTitle(" Status bar ...", 170);

      LwPanel p1 = new LwPanel();
      p1.setLwLayout(new LwBorderLayout());
      p1.setPSSize(450, 180);
      p1.setLocation(20, 200);
      LwStatusBar sb1 = new LwStatusBar(1);
      sb1.setInsets (1,0,0,0);
      p1.add(LwBorderLayout.SOUTH, sb1);
      p1.getViewMan(true).setBorder("br.raised");
      sb1.add(new Integer(30), new LwLabel("Label ..."));
      sb1.add(new Integer(20), new LwLink("[link]"));
      sb1.add(new Integer(45), new LwTextField("Text field"));
      sb1.add(new Integer(5), new LwImage((LwImgRender)LwToolkit.getView("tc")));
      LwPanel p2 = new LwPanel ();
      p2.getViewMan(true).setBorder("br.sunken");
      p1.add(LwBorderLayout.CENTER, p2);

      LwLabel lab3 = makeTitle(" Popup menu ...", 420);

      final LwLabel lab4 = new LwLabel(new Text("Press right mouse button\nand change\nbackground color."));
      lab4.getViewMan(true).setBorder("br.etched");
      lab4.setPSSize(150, -1);
      lab4.setLocation(20, 450);

      PopupMenu pop = new PopupMenu();
      pop.add("Red");
      pop.add("Blue");
      pop.add("Yellow");
      pop.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          String cmd = e.getActionCommand();
          if (cmd.equals("Red")) lab4.setBackground(Color.red);
          else
          if (cmd.equals("Blue")) lab4.setBackground(Color.blue);
          else
          if (cmd.equals("Yellow")) lab4.setBackground(Color.yellow);
        }
      });

      ((LwPopupManager)LwToolkit.getStaticObj("pop")).setPopup(lab4, pop);

      root.add(lab1);
      root.add(pr1);
      root.add(pr2);
      root.add(pr3);
      root.add(pr4);
      root.add(pr5);
      root.add(pr6);
      root.add(lab2);
      root.add(p1);
      root.add(lab3);
      root.add(lab4);
      return root;
    }

    private static LwComponent getTooltipPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle("Move the mouse inside any component below and\ndon't touch your mouse some time ...", 10);

      LwLabel l1 = makeComment("The tooltip shows mouse pointer\nlocation:", 20, 70, 200);
      LwCanvas c1 = new LwCanvas();
      c1.setLocation(240, 70);
      c1.setPSSize(150, 60);
      c1.setBackground(Color.lightGray);
      c1.getViewMan(true).setBorder("br.etched");

      LwLabel l2 = makeComment("This is multiline, trasparent tooltip:", 20, 150, 200);
      LwImage c2 = new LwImage(new LwImgSetRender("demo/cosmo3.jpg", 100, 100, 150, 60, 1));
      c2.setLocation(240, 150);
      c2.setPSSize(150, 60);
      c2.getViewMan(true).setBorder("br.etched");

      LwLabel l3 = makeComment("Shows complex tooltip:", 20, 230, 200);
      LwCanvas c3 = new LwCanvas();
      c3.setLocation(240, 230);
      c3.setPSSize(150, 60);
      c3.getViewMan(true).setBorder("br.etched");

      LwTooltipMan manager = LwToolkit.getTooltipManager();
      manager.setTooltipInfo(c1, new TTI(1));
      manager.setTooltipInfo(c2, new TTI(2));
      manager.setTooltipInfo(c3, new TTI(3));

      root.add(lab1);
      root.add(l1);
      root.add(c1);
      root.add(l2);
      root.add(c2);
      root.add(l3);
      root.add(c3);
      return root;
    }

    private static LwComponent getTreeGridPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Tree Grid component ...", 10);

      LwTreeGrid tg = new LwTreeGrid();
      tg.setTreeElView(LwTree.CLOSED_VIEW, LwToolkit.getView("tc"));
      tg.setTreeElView(LwTree.OPENED_VIEW, LwToolkit.getView("to"));
      tg.setTreeElView(LwTree.LEAST_VIEW,  LwToolkit.getView("tl"));

      tg.setEditorProvider(new LwDefEditors());
      tg.setBackground(Color.white);
      LwGridCaption cap = new LwGridCaption(tg);
      cap.putTitle(0, "PDA models");
      cap.putTitle(1, "OS");
      cap.putTitle(2, "Bluetooth");
      cap.putTitle(3, "RAM");
      cap.putTitle(4, "ROM");
      tg.add (LwGrid.TOP_CAPTION_EL, cap);

      Item r = new Item("PDA");
      Tree data = new Tree(r);
      data.add(r, new Item("Sharp"));
      data.add(r, new Item("Compaq"));
      data.add(data.getChildAt(r, 0), new Item("Zaurus SL-5000D"));
      data.add(data.getChildAt(r, 0), new Item("Zaurus SL-5500"));
      data.add(data.getChildAt(r, 0), new Item("Zaurus SL-C700"));
      data.add(data.getChildAt(r, 0), new Item("Zaurus SL-B500"));
      data.add(data.getChildAt(r, 1), new Item("iPAQ H3870"));
      data.add(data.getChildAt(r, 1), new Item("iPAQ H3950"));
      data.add(data.getChildAt(r, 1), new Item("iPAQ H3970"));
      tg.setTreeModel(data);
      MatrixModel mm = tg.getModel();

      mm.put(2, 1, "Linux 2.4(Embedix)");
      mm.put(2, 2, "No");
      mm.put(2, 3, "16 Mb");
      mm.put(2, 4, "32 Mb");

      mm.put(3, 1, "Linux 2.4(Embedix)");
      mm.put(3, 2, "No");
      mm.put(3, 3, "16 Mb");
      mm.put(3, 4, "32 Mb");

      mm.put(4, 1, "Linux 2.4(Embedix)");
      mm.put(4, 2, "Yes");
      mm.put(4, 3, "32 Mb");
      mm.put(4, 4, "64 Mb");

      mm.put(5, 1, "Linux 2.4(Embedix)");
      mm.put(5, 2, "Yes");
      mm.put(5, 3, "32 Mb");
      mm.put(5, 4, "64 Mb");

      mm.put(7, 1, "Pocket PC 2002");
      mm.put(7, 2, "Yes");
      mm.put(7, 3, "64 Mb");
      mm.put(7, 4, "48 Mb");

      mm.put(8, 1, "Pocket PC 2002");
      mm.put(8, 2, "No");
      mm.put(8, 3, "64 Mb");
      mm.put(8, 4, "32 Mb");

      mm.put(9, 1, "Pocket PC 2002");
      mm.put(9, 2, "Yes");
      mm.put(9, 3, "64 Mb");
      mm.put(9, 4, "48 Mb");

      tg.setColWidth(0, 180);
      tg.setColWidth(1, 150);
      tg.setColWidth(2, 80);
      tg.setColWidth(3, 60);
      tg.setColWidth(4, 60);
      tg.getPosController().setOffset(0);

      LwScrollPan sp = new LwScrollPan(tg);
      sp.setLocation(20, 50);
      sp.setPSSize  (450, 300);

      root.add(lab1);
      root.add(sp);
      return root;
    }

    private static LwComponent getMaskPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Masked text fields ...", 10);
      lab1.setSize (500, lab1.getPreferredSize().height);

      LwLabel l1 = makeComment("Numeric mask (for example \"121.02\"):", 30, 50, 270);
      LwMaskTextField tf1 = new LwMaskTextField("", "nnn.nn");
      tf1.setPSSize (150, -1);
      tf1.setLocation (320, 50);

      LwLabel l2 = makeComment("Letter mask (for example \"BMW-Mercedes\"):", 30, 80, 270);
      LwMaskTextField tf2 = new LwMaskTextField("", "aaa-aaaaaaaa");
      tf2.setPSSize (150, -1);
      tf2.setLocation (320, 80);

      LwLabel l3 = makeComment("Combined mask (for example \"12  CAR  09-FF\"):", 30, 110, 270);
      LwMaskTextField tf3 = new LwMaskTextField("", "nn  AAA  nn-aa");
      tf3.setPSSize (150, -1);
      tf3.setLocation (320, 110);

      LwLabel l4 = makeComment("RGB mask (for example \"128\",\"097\",\"010\"):", 30, 140, 270);
      MaskedText mt1 = new MaskedText("nnn", new RgbMaskValidator());
      LwMaskTextField tf4 = new LwMaskTextField(mt1);
      tf4.setPSSize (30, -1);
      tf4.setLocation (320, 140);
      MaskedText mt2 = new MaskedText("nnn", new RgbMaskValidator());
      LwMaskTextField tf5 = new LwMaskTextField(mt2);
      tf5.setPSSize (30, -1);
      tf5.setLocation (380, 140);
      MaskedText mt3 = new MaskedText("nnn", new RgbMaskValidator());
      LwMaskTextField tf6 = new LwMaskTextField(mt3);
      tf6.setPSSize (30, -1);
      tf6.setLocation (440, 140);

      LwLabel lab2 = makeTitle(" Date masked text fields ...", 10);
      lab2.setSize (500, lab2.getPreferredSize().height);
      lab2.setLocation (10, 190);
      MaskedText mt4 = new MaskedText("dd / mm / yyyy", new DateMaskValidator());
      LwMaskTextField tf7 = new LwMaskTextField(mt4);
      mt4.setText ("21 / 12 / 2002");
      tf7.setPSSize (150, -1);
      tf7.setLocation (30, 230);

      MaskedText mt5 = new MaskedText("dd - MMM - yyyy", new DateMaskValidator());
      mt5.setText ("07 - Jul - 1974");
      LwMaskTextField tf8 = new LwMaskTextField(mt5);
      tf8.setPSSize (150, -1);
      tf8.setLocation (30, 260);

      MaskedText mt6 = new MaskedText("dd - mm (MMM) - yyyy", new DateMaskValidator());
      mt6.setText ("01 - 12 (Dec) - 2005");
      LwMaskTextField tf9 = new LwMaskTextField(mt6);
      tf9.setPSSize (150, -1);
      tf9.setLocation (30, 290);

      root.add (lab1);
      root.add (l1);
      root.add (tf1);
      root.add (l2);
      root.add (tf2);
      root.add (l3);
      root.add (tf3);
      root.add (l4);
      root.add (tf4);
      root.add (tf5);
      root.add (tf6);
      root.add (lab2);
      root.add (tf7);
      root.add (tf8);
      root.add (tf9);

      return root;
    }

    private static LwComponent getExtMiscPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Auto-wrapped text label ...", 10);
      lab1.setSize (500, lab1.getPreferredSize().height);

      LwLabel l1 = new LwLabel(new LwWrappedText("This is auto wrapped text. It means that the text data are divided by lines automatically according to the view area size."));
      l1.getViewMan(true).setBorder("br.etched");
      l1.setPSSize (200, 100);
      l1.setLocation (30, 50);

      LwLabel lab2 = makeTitle(" Spin [-10 - 10]...", 180);
      lab2.setSize (500, lab2.getPreferredSize().height);

      LwSpin spin = new LwSpin();
      spin.setLocation (30, 220);
      spin.setPSSize(50, -1);

      root.add (lab1);
      root.add (l1);
      root.add (lab2);
      root.add (spin);
      return root;
    }

    private static LwComponent getWinPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Internal frames ...", 10);
      lab1.setSize (500, lab1.getPreferredSize().height);

      LwButton b1 = new LwButton("Open Opaque Win");
      b1.setLocation (30, 50);

      LwButton b2 = new LwButton("Open Transient Win");
      b2.setLocation (160, 50);

      LwButton b3 = new LwButton("Open Modal Win");
      b3.setLocation (300, 50);

      //!!!
      /*LwTracker tr = new LwTracker();
      tr.setLocation(30, 350);
      root.add (tr);*/

      LwActionListener l = new WOAL(b1, b2, b3);
      b1.addActionListener(l);
      b2.addActionListener(l);
      b3.addActionListener(l);

      root.add (lab1);
      root.add (b1);
      root.add (b2);
      root.add (b3);
      return root;
    }

    private static LwComponent getSliderPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Sliders  ...", 10);
      lab1.setSize (500, lab1.getPreferredSize().height);

      LwSlider sl1 = new LwSlider();
      sl1.setValues (0, 100, new int[] {0, 30, 30, 20 }, 1, 1);
      sl1.setScaleStep(2);
      sl1.setLocation(20, 50);

      LwSlider sl2 = new LwSlider();
      sl2.setValues (0, 100, new int[] { 0, 10, 50, 30 }, 5, 1);
      sl2.setLocation(20, 120);
      sl2.useIntervalModel(true);

      sl2.setPSSize(sl1.getPreferredSize().width, -1);

      LwSlider sl3 = new LwSlider();
      sl3.setValues (0, 100, new int[0], 1, 1);
      sl3.setScaleStep(2);
      sl3.setLocation(20, 190);

      LwSlider sl4 = new LwSlider();
      sl4.setValues (0, 100, new int[0], 5, 1);
      sl4.setLocation(20, 250);
      sl4.setPSSize(sl1.getPreferredSize().width, -1);
      sl4.showScale(false);


      LwSlider sl5 = new LwSlider(LwToolkit.VERTICAL);
      sl5.setValues (-20, 60, new int[] {0, 30, 30, 20 }, 1, 1);
      sl5.setScaleStep(2);
      sl5.setLocation(20, 300);

      LwSlider sl6 = new LwSlider(LwToolkit.VERTICAL);
      sl6.setValues (0, 80, new int[] { 0, 10, 60, 10 }, 5, 1);
      sl6.setLocation(140, 300);
      sl6.useIntervalModel(true);
      sl6.setPSSize(-1, sl5.getPreferredSize().height);

      LwSlider sl7 = new LwSlider(LwToolkit.VERTICAL);
      sl7.setValues (0, 100, new int[0], 5, 1);
      sl7.setScaleStep(2);
      sl7.setLocation(240, 300);
      sl7.setPSSize(-1, sl5.getPreferredSize().height);
      sl7.showScale(false);


      /*LwSlider sl4 = new LwSlider();
      sl3.setValues (0, 100, new int[0], 1, false);
      sl3.setLocation(20, 120);*/

      root.add (sl1);
      root.add (sl2);
      root.add (sl3);
      root.add (sl4);
      root.add (sl5);
      root.add (sl6);
      root.add (sl7);
      root.add (lab1);
      return root;
    }

    private static LwComponent getPanelsPanel()
    {
      LwContainer root = makeRoot();
      LwLabel lab1 = makeTitle(" Color Customization Panel...", 10);
      LwColorPanel cpanel = new LwColorPanel();
      cpanel.setInsets(6,6,6,6);
      cpanel.getViewMan(true).setBorder("br.sunken");
      cpanel.setLocation(20, lab1.getY() + 10 + lab1.getPreferredSize().height);

      LwLabel lab2 = makeTitle(" Font Customization Panel...", cpanel.getY() + 20 + cpanel.getPreferredSize().height);
      LwFontPanel fpanel = new LwFontPanel();
      fpanel.setInsets(6,6,6,6);
      fpanel.getViewMan(true).setBorder("br.sunken");
      fpanel.setLocation(20, lab2.getY() + lab2.getPreferredSize().height + 10);

      root.add(lab1);
      root.add(cpanel);
      root.add(lab2);
      root.add(fpanel);
      return root;
    }

    private static LwContainer makeRoot()
    {
      LwContainer root = new LwPanel();
      root.setLwLayout(new LwRasterLayout(LwRasterLayout.USE_PS_SIZE));
      root.setOpaque(false);
      return root;
    }

    private static LwComponent makeImageLabel(String img, String lab)
    {
      LwPanel c = new LwPanel();
      c.setInsets (2,2,2,2);
      c.setLwLayout(new LwFlowLayout());
      c.add(new LwImage((LwImgRender)LwToolkit.getView(img)));
      c.add(new LwLabel(new Text(lab)));
      return c;
    }

    private static String readPropFile()
    throws Exception
    {
      BufferedReader buf = new BufferedReader(new InputStreamReader(LwToolkit.class.getResourceAsStream(LwToolkit.getResourcesBase() + "lw.properties")));
      String res = "", line = null;
      while ((line = buf.readLine()) != null) res += (line + "\n");
      return res;
    }

    private static LwLabel makeTitle(String s, int y)
    {
      LwLabel lab = new LwLabel(new Text(s));
      lab.setBackground(Color.lightGray);
      lab.getTextRender().setFont(LwToolkit.BFONT);
      lab.setPSSize(480, -1);
      lab.setInsets(1,1,1,1);
      lab.setLocation(10, y);
      return lab;
    }

    private static LwLabel makeComment (String s, int x, int y, int w)
    {
      LwLabel l = new LwLabel(new Text(s));
      l.setPSSize (w, -1);
      l.setLocation (x, y);
      l.setOpaque(false);
      l.getTextRender().setFont(LwToolkit.BFONT);
      l.getTextRender().setForeground(Color.darkGray);
      return l;
    }

    private static LwComponent makeNotebook(int o)
    {
      LwNotebook nb = new LwNotebook(o);
      for (int i=1; i<4; i++)
      {
        LwPanel pan = new LwPanel();
        pan.setLwLayout(new LwBorderLayout());
        pan.setInsets(4, 4, 4, 4);
        pan.add (LwBorderLayout.CENTER, new LwImage(new LwImgRender("demo/cosmo" + i +".jpg", LwImgRender.STRETCH)));
        nb.addPage("Page " + i, pan);
      }
      nb.addPage("Disabled", new LwCanvas());
      nb.enablePage(3, false);
      return nb;
    }
}

class TVP
implements LwViewProvider
{
   public LwView getView(Drawable d, Object obj)
   {
     String s = (String)((Item)obj).getValue();
     if (s.indexOf ("Check")==0)
     {
       LwCheckbox ch = new LwCheckbox(s);
       ch.setOpaque(false);
       return new LwCompRender(ch);
     }
     else
     if (s.indexOf ("Image")==0)
     {
       LwContainer  c = new LwPanel();
       LwFlowLayout l = new LwFlowLayout();
       l.setGaps(0, 0);
       c.setLwLayout(l);
       c.add (new LwImage((LwImgRender)LwToolkit.getView("paw")));
       c.add (new LwLabel(s));
       c.setOpaque(false);
       return new LwCompRender(c);
     }
     else
     if (s.indexOf ("Multi")==0)
     {
       LwTextRender tr = new LwTextRender(s);
       tr.setFont(LwToolkit.BFONT);
       return tr;
     }
     else
     if (s.indexOf("Bordered") == 0)
     {
       LwLabel lab = new LwLabel (s);
       lab.getViewMan(true).setBorder("br.etched");
       return new LwCompRender(lab);
     }
     return new LwTextRender(s);
   }
}


class LwCustomBorder
extends LwView
{
  private int size = 5;

  LwCustomBorder() {
    super(STRETCH);
  }

  public Insets getInsets () {
    return new Insets(size, size, size, size);
  }

  public void paint(Graphics g, int x, int y, int w, int h, Drawable d)
  {
    int xx = x, yy = y;
    int wc = w/size;
    for (int i = 0; i < wc; i++)
    {
      drawCross (g, xx, yy, size, size);
      drawCross (g, xx, yy + h - size - 1, size, size);
      xx+=size;
    }

    xx = x; yy = y + size;
    h -= (2*size);
    int hc = h/size;
    for (int i = 0; i < hc; i++)
    {
      drawCross (g, xx, yy, size, size);
      drawCross (g, xx + w - size - 1, yy, size, size);
      yy+=size;
    }
  }

  protected void drawCross (Graphics g, int x, int y, int w, int h)
  {
    g.setColor(Color.black);
    g.drawLine(x, y, x + w, y + h);
    g.drawLine(x + w, y, x, y + h);
  }
}

class LwCompViews
implements LwGridViewProvider
{
  private LwCompRender render;

  public LwCompViews() {
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

class LwCompEditors
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

class RgbMaskValidator
extends BasicMaskValidator
{
  public boolean isValidValue(MaskElement e, String v)
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


class LwGV
extends LwDefViews
{
  public LwView getView(int row, int col, Object obj)
  {
    if (col == 3 || col == 4)
    {
      StringTokenizer st = new StringTokenizer((String)obj, ";");
      int index = Integer.parseInt (st.nextToken());
      while (index > 0) { st.nextToken(); index--; }
      obj = st.nextToken();
    }

    LwView v = super.getView(row, col, obj);
    if (col == 0) ((LwTextRender)v).setFont(LwToolkit.BFONT);
    else          ((LwTextRender)v).setFont(LwToolkit.FONT);
    return v;
  }
}

class WOAL
implements LwActionListener
{
  private static int x = 120, y = 100;

  private LwButton opaque, trans, modal;

  WOAL (LwButton o, LwButton t, LwButton m)
  {
    opaque = o;
    trans  = t;
    modal  = m;
  }

  public void actionPerformed (LwActionEvent e)
  {
    LwWindow w = new LwWindow ("Demo window");
    w.setSize (200, 200);
    w.setLocation(x+=20, y+=20);
    w.getRoot().setLwLayout (new LwBorderLayout());
    w.getRoot().add (LwBorderLayout.CENTER, new LwTextField("Edit text here !"));

    //!!!
    LwCombo cc = new LwCombo();
    cc.getList().add ("Item 1");
    cc.getList().add ("Item 2");
    cc.getList().add ("Item 3");
    w.getRoot().add (LwBorderLayout.SOUTH, cc);

    if (e.getSource() == trans) {
      w.setOpaque (false);
      w.getTitlePanel().setOpaque(true);
    }

    LwToolkit.getDesktop((LwComponent)e.getSource()).getLayer(LwWinLayer.ID).add (e.getSource()==modal?LwWinLayer.MODAL_WIN:LwWinLayer.MDI_WIN, w);
  }
}

class LwGE
extends LwDefEditors
{
  public LwComponent getEditor(int row, int col, Object o)
  {
    switch (col)
    {
      case 1:
      {
       LwComponent c = super.getEditor(row, col, o);
       c.getViewMan(true).setBorder((LwView)null);
       c.setBackground(Color.white);
       return c;
      }
      case 2:
      {
        LwCheckbox box = new LwCheckbox((LwComponent)null, LwCheckbox.CHECK);
        box.setBackground(Color.white);
        box.setState(o.equals("Yes"));
        return box;
      }
      case 3:
      {
        LwCombo box = new LwCombo();
        box.setBackground(Color.white);
        StringTokenizer st = new StringTokenizer((String)o, ";");
        int index = Integer.parseInt(st.nextToken());
        for(;st.hasMoreTokens();) box.getList().add (st.nextToken());
        box.getList().select(index);
        return box;
      }
      case 4:
      {
        LwCombo box = new LwCombo();
        box.setBackground(Color.white);
        StringTokenizer st = new StringTokenizer((String)o, ";");
        int index = Integer.parseInt(st.nextToken());
        for(;st.hasMoreTokens();) box.getList().add (st.nextToken());
        box.getList().select(index);
        return box;
      }
    }
    return null;
  }

  public Object fetchEditedValue (int row, int col, LwComponent editor)
  {
    switch (col)
    {
      case 1: return super.fetchEditedValue(row, col, editor);
      case 2:
      {
        LwCheckbox box = (LwCheckbox)editor;
        return box.getState()?"Yes":"No";
      }
      case 3:
      {
        LwCombo box = (LwCombo)editor;
        box.setBackground(Color.white);
        String res = "" + box.getList().getSelectedIndex() + ";";
        for (int i=0; i<box.getList().count(); i++)
        {
          if (i > 0) res += ";";
          res += ((LwLabel)box.getList().get(i)).getText();
        }
        return res;
      }
      case 4:
      {
        LwCombo box = (LwCombo)editor;
        box.setBackground(Color.white);
        String res = "" + box.getList().getSelectedIndex() + ";";
        for (int i=0; i<box.getList().count(); i++)
        {
          if (i > 0) res += ";";
          res += ((LwLabel)box.getList().get(i)).getText();
        }
        return res;
      }
    }
    return ((LwTextField)editor).getTextModel().getText();
  }
}

class TTI
implements TooltipInfo
{
  private LwComponent tooltip;
  private int type;

  TTI(int type)
  {
    this.type = type;
    if (type == 1) tooltip = LwTooltipMan.createTooltip("");
    else
    if (type == 2)
    {
      tooltip = LwTooltipMan.createTooltip("Transparent and\nmultiline tooltip.");
      tooltip.setOpaque(false);
      tooltip.getViewMan(true).setBorder("br.dot");
      ((LwLabel)tooltip).setInsets(5,5,5,5);
      ((LwLabel)tooltip).getTextRender().setForeground(Color.red);
      ((LwLabel)tooltip).getTextRender().setFont (LwToolkit.BFONT);
    }
    else
    if (type == 3)
    {
      LwPanel p = new LwPanel();
      p.setLwLayout(new LwFlowLayout());
      p.add (new LwImage((LwImgRender)LwToolkit.getView("paw")));
      p.add (new LwLabel(new Text("This is more\ncomplex tooltip.")));
      tooltip = p;
      tooltip.getViewMan(true).setBorder("br.plain");
      tooltip.setBackground(LwTooltipMan.tooltipBack);
    }
  }

  public LwComponent getTooltip(int x, int y)
  {
    if (type == 1)
    {
      ((LwLabel)tooltip).setText ("[x=" + x + ",y=" + y + "]");
      return tooltip;
    }
    else return tooltip;
  }
}


