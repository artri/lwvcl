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

import java.io.*;
import java.awt.*;

import org.zaval.lw.event.*;
import org.zaval.lw.tree.*;
import org.zaval.data.*;
import org.zaval.misc.*;
import org.zaval.util.*;

public class LwFilePanel
extends LwPanel
implements LwActionListener, LwChildrenListener
{
  private String root;
  private String[][] ext;
  private LwTextField pathField, fileField;
  private LwTree tree;
  private LwCombo filterCombo;
  private LwActionSupport support;

  public LwFilePanel(String root, String[][] ext)
  {
    this.root = root;

    /*C#this.ext  = new string[ext.Length][];*/
    /*C#for (int i=0; i<ext.Length; i++) this.ext[i] = new string[2];*/

    this.ext  = new String[ext.length][2]; /*java*/
    for (int i=0; i<ext.length; i++)
      for (int j=0; j<2; j++)
        this.ext[i][j] = ext[i][j];

    setInsets(2,2,2,2);
    tree = new LwTree(null);
    tree.addSelectionListener(this);
    LwScrollPan sp = new LwScrollPan(tree);
    sp.getViewMan(true).setBorder("br.sunken");
    add(LwBorderLayout.CENTER, sp) ;

    LwPanel topPanel = new LwPanel();
    topPanel.setLwLayout(new LwGridLayout(1, 2, LwToolkit.HORIZONTAL));
    pathField = new LwTextField(new SingleLineTxt(""));

    pathField.setPSSize(80, -1);
    pathField.setEditable(false);
    LwConstraints c1 = new LwConstraints();
    c1.ax = Alignment.LEFT;
    c1.fill = 0;
    topPanel.add(c1, new LwLabel((String)LwToolkit.getStaticObj("fd.path")));
    LwConstraints c2 = new LwConstraints();
    c2.ax = Alignment.RIGHT;
    c2.fill = LwToolkit.HORIZONTAL;
    topPanel.add(c2, pathField);
    add(LwBorderLayout.NORTH, topPanel);

    LwPanel bottomPanel = new LwPanel();
    bottomPanel.setLwLayout(new LwGridLayout(2, 2, LwToolkit.HORIZONTAL));
    LwConstraints c3 = new LwConstraints();
    c3.ax   = Alignment.LEFT;
    c3.fill = 0;
    c3.insets = new Insets(0, 0, 2, 2);
    bottomPanel.add(c3, new LwLabel((String)LwToolkit.getStaticObj("fd.name")));
    fileField = new LwTextField(new SingleLineTxt(""));
    fileField.setPSSize(70, -1);
    LwConstraints c4 = new LwConstraints();
    c4.ax   = Alignment.RIGHT;
    c4.fill = LwToolkit.HORIZONTAL;
    c4.insets = new Insets(0, 0, 2, 0);

    bottomPanel.add(c4, fileField);
    LwConstraints c5 = new LwConstraints();
    c5.ax   = Alignment.LEFT;
    c5.fill = 0;
    c5.insets = new Insets(0, 0, 2, 2);
    bottomPanel.add(c5, new LwLabel((String)LwToolkit.getStaticObj("fd.mask")));
    LwConstraints c6 = new LwConstraints();
    c6.ax   = Alignment.RIGHT;
    c6.fill = LwToolkit.HORIZONTAL;
    c6.insets = new Insets(0, 0, 2, 0);
    filterCombo = new LwCombo();
    LwList list = filterCombo.getList();
    list.addSelectionListener(this);
    for (int i=0; i<ext.length; i++) list.add(ext[i][0]);
    list.select(0);
    filterCombo.setPSSize(70, -1);
    bottomPanel.add(c6, filterCombo);
    add(LwBorderLayout.SOUTH, bottomPanel);
  }

  public File getSelectedFile() {
    FileItem selected = (FileItem)tree.getSelectedItem();
    return selected == null?null: selected.getFile();
  }

  public File lookup (String path) {
    FileItem fi = lookup(tree.getModel(), (FileItem)tree.getModel().getRoot(), path);
    return fi == null?null:fi.getFile();
  }

  public void addActionListener(LwActionListener l) {
    if (support == null) support = new LwActionSupport();
    support.addListener(l);
  }

  public void removeActionListener(LwActionListener l) {
    if (support != null) support.removeListener(l);
  }

  public void childPerformed(LwAWTEvent e)
  {
    if (e.getID() == LwKeyEvent.KEY_PRESSED &&
        e.getSource() == fileField          &&
        ((LwKeyEvent)e).getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
    {
      FileItem selected = (FileItem)tree.getSelectedItem();
      if (selected != null && !selected.getFileName().equals(fileField.getText()))
      {
        FileItem item = lookup(tree.getModel(), selected, fileField.getText());
        if (item != null) tree.select(item);
      }
    }
  }

  public void actionPerformed(LwActionEvent e)
  {
    Object target = e.getSource();
    if (target == tree)
    {
      FileItem item = (FileItem)e.getData();
      pathField.setText((item == null)?"":item.getDir());
      fileField.setText((item != null && !item.isDirectory())?item.getFileName():"");
      if (support != null) support.perform (new LwActionEvent(this, pathField.getText() + File.separator + fileField.getText()));
    }
    else
    if (target == filterCombo.getList())
    {
      TreeModel model = createTreeModel(root, new ExtFileFilter(ext[filterCombo.getList().getSelectedIndex()][1]));
      tree.setModel(model);
      repaint();
    }
  }

  protected /*C#override*/ LwLayout getDefaultLayout() {
    return new LwBorderLayout(5, 5);
  }

  private FileItem lookup (TreeModel model, FileItem root, String path)
  {
    if (root.isDirectory())
    {
      int index = path.indexOf (File.separator);
      String s = (index >= 0)?path.substring(0, index):path;
      for (int i=0; i<model.getChildrenCount(root); i++)
      {
        FileItem fi = (FileItem)model.getChildAt(root, i);
        if (fi.getFileName().equals(s))
          return (index >= 0)?lookup(model, fi, path.substring(index + 1)):fi;
      }
    }
    return null;
  }

  public static TreeModel createTreeModel(String root, FilenameFilter f)
  {
    File rootFile = new File(root);
    if (!rootFile.exists()) throw new IllegalArgumentException();
    FileItem  rootItem = new FileItem(rootFile);
    TreeModel model    = new Tree(rootItem);
    createTreeModel(model, rootItem, rootFile, f);
    return model;
  }

  private static void createTreeModel(TreeModel t, FileItem r, File f, FilenameFilter filter)
  {
    if (f.isDirectory())
    {
      String[] dirs = f.list(filter);
      for (int i=0; i<dirs.length; i++)
      {
        File      kidFile = new File(r.getDir() + File.separator + dirs[i]);
        FileItem  kidItem = new FileItem(kidFile);
        t.add (r, kidItem);
        createTreeModel(t, kidItem, kidFile, filter);
      }
    }
  }
}

class FileItem
extends Item
{
  private String  path;
  private boolean isDirectoryValue;

  public FileItem(File f)
  {
    super(f.getName());
    path = f.getPath();
    isDirectoryValue = f.isDirectory();
    if (!isDirectoryValue)
    {
      int index = path.lastIndexOf(File.separator);
      if (index < 0) path = "";
      else           path = path.substring(0, index);
    }
  }

  public String getDir() {
    return path;
  }

  public String getFileName() {
    return getValue().toString();
  }

  public File getFile () {
    if (isDirectory()) return new File(path);
    else               return new File(path.length() > 0?path + File.separator + getFileName():getFileName());
  }

  public boolean isDirectory() {
    return isDirectoryValue;
  }
}

class ExtFileFilter
implements FilenameFilter
{
   private String ext;

   public ExtFileFilter(String ext) {
     this.ext = ext;
   }

   public boolean accept(File dir, String name)
   {
     if (ext.equals("*") || (new File(dir.getPath() + File.separator + name)).isDirectory())
       return true;

     int index = name.lastIndexOf ('.');
     return index > 0 && ext.equalsIgnoreCase(name.substring(index+1));
   }
}







