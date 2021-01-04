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
import java.io.*;
import org.zaval.lw.*;

public abstract class LwSample
{
  private LwPanel root;

  public /*C#virtual*/ void started () {}
  public /*C#virtual*/ void stopped () {}

  public /*C#virtual*/ LwPanel getRoot () {
    if (root == null) root = createSamplePanel();
    return root;
  }

  public String getSourceCode ()
  {
    String className = getClass().getName();
    String srcName   = className.substring(className.lastIndexOf('.') + 1) + ".java";
    String srcCode   = readAsResource(srcName);
    if (srcCode == null)
    {
      String srcRoot = System.getProperty("lw.samples.srcroot");
      if (srcRoot == null || srcRoot.trim().length() == 0) srcRoot = "samples/src/org/zaval/lw/samples";
      srcCode = readAsFile( srcRoot + "/" + srcName);
    }
    return srcCode == null?"Not available !":srcCode;
  }

  public String getDescription ()
  {
    String className = getClass().getName();
    String srcName   = className.substring(className.lastIndexOf('.') + 1) + ".txt";
    String desc      = readAsResource(srcName);
    if (desc == null)
    {
      String descRoot = System.getProperty("lw.samples.descroot");
      if (descRoot == null || descRoot.trim().length() == 0) descRoot = "samples/src/org/zaval/lw/samples";
      desc = readAsFile(descRoot + "/" + srcName);
    }
    return desc == null?"Not available !":desc;
  }

  public abstract LwPanel createSamplePanel();

  public static LwFrame runSampleApp (int w, int h, LwSample sample)
  {
    LwFrame frame = new LwFrame();
    frame.addWindowListener(new WL());
    LwContainer root  = frame.getRoot();
    root.setLwLayout(new LwBorderLayout());
    LwPanel content = sample.getRoot ();
    root.add (LwBorderLayout.CENTER, content);
    sample.started();

    frame.setSize(w, h);
    frame.setVisible (true);
    return frame;
  }

  public static String readAsResource(String f)
  {
    try {
      BufferedReader buf = new BufferedReader(new InputStreamReader(LwSample.class.getResourceAsStream(f)));
      String res = "", line = null;
      while ((line = buf.readLine()) != null) res += (line + "\n");
      buf.close();
      return res;
    }
    catch (Exception e) {
    }
    return null;
  }

  public static String readAsFile(String path)
  {
    try {
      BufferedReader buf = new BufferedReader(new FileReader(path));
      String res = "", line = null;
      while ((line = buf.readLine()) != null) res += (line + "\n");
      buf.close();
      return res;
    }
    catch (IOException e) {}
    return null;
  }
}
