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
package org.zaval.lw.samples;

import java.awt.*;
import java.util.*;
import org.zaval.lw.*;
import org.zaval.misc.*;
import org.zaval.lw.event.*;
import org.zaval.lw.tree.*;
import org.zaval.lw.grid.*;
import org.zaval.data.*;

public class LwSamplesCentral
{
  public static final String NOT_SELECTED_TITLE = "A sample has not been selected.\nPlease, select a sample by the tree component at the left.";
  public static final String SHORT_NOT_SELECTED_TITLE = "A sample has not been selected !";

  public static void main (String[] args)
  {
    LwFrame     frame = new LwFrame();
    LwContainer root  = frame.getRoot();
    root.setLwLayout (new LwBorderLayout());

    // Create sample page
    LwPanel samplePage = new LwPanel();
    samplePage.setInsets(8,8,8,8);
    samplePage.setLwLayout(new LwPercentLayout(LwToolkit.VERTICAL, 4));

    final LwPanel sampleArea = new LwPanel ();
    sampleArea.setLwLayout (new LwBorderLayout());
    LwPanel sampleBorderPanel = new LwBorderPan (new LwLabel("Sample Application"), sampleArea);
    sampleBorderPanel.setInsets(8,8,8,8);
    sampleBorderPanel.setPSSize(-1, 320);

    final LwLabel description = new LwLabel (new Text(NOT_SELECTED_TITLE));
    //description.setForeground(LwToolkit.darkBlue);
    //description.getTextRender().setFont(LwToolkit.BFONT);
    LwPanel descBorderPanel = new LwBorderPan (new LwLabel("Sample Description"), new LwScrollPan(description));
    descBorderPanel.setPSSize(-1, 150);
    descBorderPanel.setInsets(8,8,8,8);

    samplePage.add (new Integer(70), sampleBorderPanel);
    samplePage.add (new Integer(30), descBorderPanel);

    LwStatusBar statusBar  = new LwStatusBar ();
    final LwLabel sampleNameLabel = new LwLabel(SHORT_NOT_SELECTED_TITLE);
    statusBar.add(new Integer(30), sampleNameLabel);
    statusBar.add(new Integer(70), new LwLabel(""));
    root.add (LwBorderLayout.SOUTH, statusBar);

    final LwNotebook  notebook   = new LwNotebook();
    final LwTextField sourceCode = new LwTextField (new Text(NOT_SELECTED_TITLE));
    LwPanel sourcePage = new LwPanel();
    sourcePage.setLwLayout (new LwBorderLayout());
    sourcePage.add (LwBorderLayout.CENTER, new LwScrollPan(sourceCode));
    LwPanel linkPanel = new LwPanel();
    linkPanel.setLwLayout(new LwFlowLayout(Alignment.CENTER, Alignment.CENTER, LwToolkit.VERTICAL));
    LwLink link = new LwLink("Copy to clipboard");
    linkPanel.add(link);
    sourcePage.add (LwBorderLayout.SOUTH, linkPanel);
    link.addActionListener(new LwActionListener() {
       public void actionPerformed(LwActionEvent e) {
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(sourceCode.getText()), null);
       }
    });


    //sourceCode.setForeground (LwToolkit.darkBlue);
    sourceCode.setEditable (false);
    notebook.addPage("Sample Application", samplePage);
    notebook.addPage("Sample Source Code", sourcePage);

    LwTree  tree = new LwTree (createTreeModel("samples",
                               LwToolkit.getProperties ("samples/samples.properties")));
    tree.getViewMan(true).setBorder("br.sunken");
    tree.addSelectionListener(new LwActionListener()
    {
       private Hashtable samples = new Hashtable();
       private LwSample  sample;

       public void actionPerformed (LwActionEvent e)
       {
         TreeItem item = (TreeItem)e.getData();
         if (item.cl != null)
         {
           LwSample s = (LwSample)samples.get(item.getValue());
           if (s == null)
           {
             try {
               s = (LwSample)Class.forName(item.cl).newInstance();
               samples.put (item.getValue(), s);
             }
             catch (Exception ee) {
               ee.printStackTrace();
             }
           }

           if (s != sample)
           {
              if (sample != null)
              {
                sample.stopped();
                sampleArea.removeAll();
              }

              sample = s;
              sampleArea.add (LwBorderLayout.CENTER, sample.getRoot());
              sample.started();
              sourceCode.setForeground(LwToolkit.darkBlue);
              sourceCode.setText(sample.getSourceCode());
              description.setForeground(LwToolkit.darkBlue);
              description.setText(sample.getDescription());
              sampleNameLabel.setForeground(LwToolkit.darkBlue);
              sampleNameLabel.setText (item.title);
           }
         }
         else
         if (sample != null)
         {
           sample.stopped();
           sampleArea.removeAll();
           sample = null;
           sourceCode.setForeground(Color.black);
           sourceCode.setText(NOT_SELECTED_TITLE);
           sampleNameLabel.setForeground(Color.black);
           sampleNameLabel.setText (SHORT_NOT_SELECTED_TITLE);
           description.setForeground(Color.black);
           description.setText (NOT_SELECTED_TITLE);
         }
       }
    });


    LwPanel rightPanel = new LwPanel();
    rightPanel.setLwLayout (new LwBorderLayout());
    rightPanel.add (LwBorderLayout.CENTER, notebook);

    LwSplitPan split = new LwSplitPan(new LwScrollPan(tree), rightPanel);
    split.setGripperLoc (250);
    root.add (LwBorderLayout.CENTER, split);

    frame.addWindowListener(new WL());
    frame.setSize (800, 600);
    frame.setTitle ("LwVCL " + LwToolkit.getVersion() + " Samples Central");
    frame.setVisible (true);
  }

  private static TreeModel createTreeModel(String rootId,  Properties p) {
    return createTreeModel(rootId, null, null, p);
  }

  private static TreeModel createTreeModel(String rootId, Item parent, TreeModel model, Properties p)
  {
    TreeItem item = new TreeItem(rootId, p.getProperty(rootId + ".title"),
                                         p.getProperty(rootId + ".icon"),
                                         p.getProperty(rootId + ".class"));
    if (model == null ) model = new Tree(item);
    else                model.add (parent, item);

    String kids = p.getProperty(rootId + ".kids");
    if (kids != null)
    {
      StringTokenizer stkids = new StringTokenizer(kids, ",");
      while (stkids.hasMoreTokens())
        createTreeModel(stkids.nextToken().trim(), item, model, p);
    }

    return model;
  }

}

class TreeItem
extends Item
{
  String title, icon, cl;

  public TreeItem(String id, String title, String icon, String cl)
  {
    super(id);
    this.title = title;
    this.icon  = icon;
    this.cl    = cl;
  }

  public String toString () {
    return "" + title;
  }
}

