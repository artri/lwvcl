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
import org.zaval.misc.*;
import org.zaval.lw.*;
import org.zaval.lw.event.*;

public class LwNotebookSample
extends LwSample
implements LwActionListener
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();

    LwNotebook n1 = new LwNotebook();
    n1.addPage("Page 1", new LwCanvas());
    n1.addPage("Page 2", new LwCanvas());
    n1.addPage("Page 3", new LwCanvas());
    n1.enablePage(1, false);
    Dimension ps = n1.getPreferredSize();
    n1.setSize(ps.width, 120);
    n1.setLocation (20, 20);
    n1.addSelectionListener(new LwNotebookSample());
    root.add (n1);

    LwNotebook n2 = new LwNotebook(Alignment.LEFT);
    n2.addPage("Page 1", new LwCanvas());
    n2.addPage("Page 2", new LwCanvas());
    n2.addPage("Page 3", new LwCanvas());
    n2.setSize(ps.width, 120);
    n2.setLocation (n1.getX() + n1.getWidth() + 20, 20);
    n2.addSelectionListener(new LwNotebookSample());
    root.add (n2);

    LwNotebook n3 = new LwNotebook(Alignment.TOP);
    n3.addPage("Page 1", new LwCanvas());
    n3.addPage("Page 2", new LwCanvas());
    n3.addPage("Page 3", new LwCanvas());
    ps = n3.getPreferredSize();
    n3.setSize(ps.width, 120);
    n3.setLocation (20, 20 + n1.getY() + n1.getHeight());
    n3.addSelectionListener(new LwNotebookSample());
    root.add (n3);

    LwNotebook n4 = new LwNotebook(Alignment.RIGHT);
    n4.addPage("Page 1", new LwCanvas());
    n4.addPage("Page 2", new LwCanvas());
    n4.addPage("Page 3", new LwCanvas());
    n4.setSize(ps.width, 120);
    n4.setLocation (n3.getX() + n3.getWidth() + 20, 20 + n2.getY() + n3.getHeight());
    n4.addSelectionListener(new LwNotebookSample());
    root.add (n4);

    return root;
  }

  public static void main(String[] args) {
    runSampleApp(400, 400, new LwNotebookSample());
  }

  public void actionPerformed(LwActionEvent e) {
    Integer i = (Integer)e.getData();
    System.out.println ("The " + i + " page has been selected.");
  }
}

