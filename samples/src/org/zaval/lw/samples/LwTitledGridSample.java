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
import org.zaval.lw.*;
import org.zaval.lw.event.*;
import org.zaval.lw.grid.*;
import org.zaval.misc.*;
import org.zaval.misc.event.*;
import org.zaval.data.*;

public class LwTitledGridSample
extends LwSample
implements PosListener
{
  public /*C#override*/ LwPanel createSamplePanel()
  {
    LwPanel root = new LwPanel();

    Matrix data1 = new Matrix(5, 3);
    for (int i=0;i<data1.getRows(); i++)
      for (int j=0;j<data1.getCols(); j++)
        data1.put (i, j, "Cell [" + i + "][" + j + " ]");

    LwGrid        grid1    = new LwGrid(data1);
    LwGridCaption caption1 = new LwGridCaption(grid1);
    for (int i=0; i<data1.getCols(); i++)
      caption1.putTitle (i, "Title " + i);
    grid1.add (LwGrid.TOP_CAPTION_EL, caption1);

    grid1.setSize(260, 150);
    grid1.setLocation (20, 20);
    grid1.getPosController().addPosListener(new LwTitledGridSample());
    root.add (grid1);

    Matrix data2 = new Matrix(5, 3);
    for (int i=0;i<data2.getRows(); i++)
      for (int j=0;j<data2.getCols(); j++)
        data2.put (i, j, "Cell [" + i + "][" + j + " ]");

    LwGrid        grid2    = new LwGrid(data2);
    LwGridCaption caption2 = new LwGridCaption(grid2);
    caption2.setViewProvider(new LwTitleViewProvider());
    for (int i=0; i<data2.getCols(); i++)
      caption2.putTitle (i, "Title " + i);
    grid2.add (LwGrid.TOP_CAPTION_EL, caption2);

    grid2.setSize(260, 150);
    grid2.setLocation (20, 180);
    grid2.getPosController().addPosListener(new LwTitledGridSample());
    root.add (grid2);

    return root;
  }

  public void posChanged(PosEvent e) {
    PosController pc = (PosController)e.getSource();
    System.out.println("The grid cursor position has been changed from " + e.getPrevOffset() + " to " + pc.getOffset());
  }

  class LwTitleViewProvider
  extends LwDefViews
  {
    public /*C#override*/ LwView getView (int row, int col, Object data)
    {
      LwPanel pan = new LwPanel();
      LwFlowLayout layout = new LwFlowLayout();
      layout.setGaps(4, 4);
      pan.setLwLayout(layout);
      pan.add (new LwLabel((String)data));
      pan.add (new LwImage("samples/img/gridt.gif"));
      return new LwCompRender(pan);
    }
  }

  public static void main (String[] args) {
    runSampleApp (300, 400, new LwTitledGridSample());
  }
}




