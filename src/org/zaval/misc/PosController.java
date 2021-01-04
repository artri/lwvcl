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
package org.zaval.misc;

import java.util.*;
import java.awt.*;
import org.zaval.misc.event.*;

/**
 * This class can be used to organize navigation along an object. The class allows to control
 * virtual position basing on the navigation object information. The information
 * should be provided by implementing <code>PosInfo</code> interface with the object. The interface
 * defines number of virtual lines, size of the specified virtual line and so on.
 */
public class PosController
extends org.zaval.util.ValidationObject
{
 /**
  * The down direction type.
  */
  public static final int DOWN = 1;

 /**
  * The up direction type.
  */
  public static final int UP = 2;

 /**
  * The begining direction type.
  */
  public static final int BEG = 3;

 /**
  * The end direction type.
  */
  public static final int END = 4;

  private int currentLine, currentCol, offset;
  private PosInfo pi;
  private Vector support;
  protected int prevOffset, prevCol, prevLine;

 /**
  * Constructs the new instance of the class.
  */
  public PosController() {}

 /**
  * Constructs the new instance of the class with the specified pos info.
  * @param <code>pi</code> the specified pos info.
  */
  public PosController(PosInfo pi) {
    setPosInfo(pi);
  }

 /**
  * Sets the specified pos info. It is impossible to use <code>null</code> value as
  * the pos info, in this case the IllegalArgumentException will be thrown.
  * @param <code>p</code> the specified pos info.
  */
  public void setPosInfo(PosInfo p)
  {
    if (p == null) throw new IllegalArgumentException();
    if (p != pi)
    {
      pi = p;
      clearPos();
    }
  }

 /**
  * Sets the virtual position to undefined state. The position is undefined
  * if it less than zero.
  */
  public /*C#virtual*/ void clearPos()
  {
    if (offset >= 0)
    {
      validate();
      prevOffset = offset;
      prevLine   = currentLine;
      prevCol    = currentCol;
      offset = -1;
      currentLine = -1;
      currentCol = -1;
      perform(new PosEvent(this,  prevOffset, prevLine, prevCol));
    }
  }

 /**
  * Sets the specified virtual position. If the new position is less than zero than
  * the method will set zero as the position value, if the new position is greater than
  * maximal than the method sets the maximal offset as the position value.
  * @param <code>o</code> the specified virtual position.
  */
  public /*C#virtual*/ void setOffset (int o)
  {
    if (o < 0) o = 0;
    else
    {
      int max = getMaxOffset();
      if (o >= max) o = max;
    }

    if (o != offset)
    {
      validate();
      prevOffset = offset;
      prevLine = currentLine;
      prevCol  = currentCol;
      offset = o;
      invalidate();
      perform(new PosEvent(this, prevOffset, prevLine, prevCol));
    }
  }

 /**
  * Moves the current position by additing specified value. The method works by
  * <code>setOffset</code> method.
  * @param <code>off</code> the specified value.
  */
  public /*C#virtual*/ void seek(int off) {
    setOffset(offset + off);
  }

 /**
  * Sets the current position by the specified row and column. The method works by
  * <code>setOffset</code> method.
  * @param <code>r</code> the specified row.
  * @param <code>c</code> the specified column.
  */
  public void setRowCol (int r, int c)
  {
    if (r != currentLine || c != currentCol)
    {
      prevOffset  = offset;
      prevLine    = currentLine;
      prevCol     = currentCol;
      currentLine = r;
      currentCol  = c;
      offset = getOffsetByPoint(r, c, this);
      perform(new PosEvent(this,  prevOffset, prevLine, prevCol));
    }
  }

 /**
  * Moves the current position to the next line according to the specified direction. The table
  * below shows how the method works depending on the direction type:
  * <br>
  * <table border="1">
  *   <tr><td align="Center"><b>Direction type</b></td><td align="Center"><b>Description<b></td></tr>
  *   <tr>
  *     <td>PosController.DOWN</td>
  *     <td>
  *       Moves the current position to the next line below. The controller tries to set the column
  *       that has been used for previous line if it is possible, otherwise the new column will be
  *       set to the maximal column of the new line.
  *     </td>
  *   </tr>
  *   <tr>
  *     <td>PosController.UP</td>
  *     <td>
  *       Moves the current position to the next line above. The controller tries to set the column
  *       that has been used for previous line if it is possible, otherwise the new column will be
  *       set to the maximal column of the new line.
  *     </td>
  *   </tr>
  *     <td>PosController.END</td>
  *     <td>
  *       Moves the current position to the end of the current line.
  *     </td>
  *   </tr>
  *   </tr>
  *     <td>PosController.BEG</td>
  *     <td>
  *       Moves the current position at the beginning of the current line.
  *     </td>
  *   </tr>
  * </table>
  * @param <code>t</code> the specified direction.
  */
  public void seekLineTo(int t) {
    seekLineTo (t, 1);
  }

 /**
  * Moves the current position to the next line according the specified direction and count.
  * The table below shows how the method works depending on the direction type:
  * <br>
  * <table border="1">
  *   <tr><td align="Center"><b>Direction type</b></td><td align="Center"><b>Description<b></td></tr>
  *   <tr>
  *     <td>PosController.DOWN</td>
  *     <td>
  *       Moves the current position to the next line below. The controller tries to set the column
  *       that has been used for previous line if it is possible, otherwise the new column will be
  *       set to the maximal column of the new line.
  *     </td>
  *   </tr>
  *   <tr>
  *     <td>PosController.UP</td>
  *     <td>
  *       Moves the current position to the next line above. The controller tries to set the column
  *       that has been used for previous line if it is possible, otherwise the new column will be
  *       set to the maximal column of the new line.
  *     </td>
  *   </tr>
  *     <td>PosController.END</td>
  *     <td>
  *       Moves the current position to the end of the current line.
  *     </td>
  *   </tr>
  *   </tr>
  *     <td>PosController.BEG</td>
  *     <td>
  *       Moves the current position at the beginning of the current line.
  *     </td>
  *   </tr>
  * </table>
  * The count defines how many times the current position will be changed using
  * the given direction.
  * @param <code>t</code> the specified direction.
  * @param <code>num</code> the specified count.
  */
  public void seekLineTo(int t, int num)
  {
     if (getOffset() < 0)
     {
       setOffset(0);
       return;
     }

     validate();
     prevOffset = offset;
     prevLine   = currentLine;
     prevCol    = currentCol;
     switch (t)
     {
       case BEG :
       {
         if (currentCol > 0)
         {
           offset -= currentCol;
           currentCol = 0;
           perform(new PosEvent(this, prevOffset, prevLine, prevCol));
         }
       } break;
       case END :
       {
         int maxCol = pi.getLineSize(currentLine);
         if (currentCol < (maxCol - 1))
         {
           offset += (maxCol - currentCol - 1);
           currentCol = maxCol - 1;
           perform(new PosEvent(this, prevOffset, prevLine, prevCol));
         }
       } break;
       case UP :
       {
         if (currentLine > 0)
         {
           offset -= (currentCol + 1);
           currentLine--;
           for (int i=0; currentLine > 0 && i < (num - 1); i++, currentLine--)
             offset -= pi.getLineSize(currentLine);

           int maxCol = pi.getLineSize(currentLine);
           if (currentCol < maxCol) offset -= (maxCol - currentCol - 1);
           else                     currentCol = maxCol - 1;
           perform(new PosEvent(this, prevOffset, prevLine, prevCol));
         }
       } break;
       case DOWN :
       {
         if (currentLine < (pi.getLines() - 1))
         {
           offset += (pi.getLineSize(currentLine) - currentCol);
           currentLine++;
           int size = pi.getLines() - 1;
           for (int i=0; currentLine < size && i < (num-1); i++, currentLine++)
            offset += pi.getLineSize(currentLine);

           int maxCol = pi.getLineSize(currentLine);
           if (currentCol < maxCol) offset += currentCol;
           else
           {
             offset += (maxCol - 1);
             currentCol = maxCol - 1;
           }
           perform(new PosEvent(this, prevOffset, prevLine, prevCol));
         }
       } break;
       default : throw new IllegalArgumentException();
     }
  }

 /**
  * Gets the maximal possible position value. The method uses pos info object to define
  * the maximal value by <code>getMaxOffset</code> method. If the pos info
  * method returns <code>-1</code> than the method will calculate maximal position
  * basing on the number of lines and the appropriate lines sizes.
  * @return a maximal possible position value.
  */
  public int getMaxOffset()
  {
    int max = pi.getMaxOffset();
    if (max < 0)
    {
      validate();
      return calcMaxOffset(pi);
    }
    else  return max;
  }

 /**
  * Gets the current column inside the current line.
  * @return a current column.
  */
  public int getCurrentCol() {
    validate();
    return currentCol;
  }

 /**
  * Gets the current line.
  * @return a current line.
  */
  public int getCurrentLine() {
    validate();
    return currentLine;
  }

 /**
  * Gets the current position.
  * @return a current position.
  */
  public int getOffset() {
    return offset;
  }

  protected /*C#override*/ void recalc()
  {
    if (getOffset() >= 0)
    {
      Point p = getPointByOffset(getOffset(), this);
      currentLine = p.x;
      currentCol  = p.y;
    }
  }

 /**
  * Adds the specified PosListener listener.
  * @param <code>l</code> the specified listener.
  */
  public void addPosListener (PosListener l) {
    if (support == null) support = new Vector(1);
    if (!support.contains(l)) support.addElement(l);
  }

 /**
  * Removes the specified PosListener listener.
  * @param <code>l</code> the specified listener.
  */
  public void removePosListener (PosListener l) {
    if (support != null) support.removeElement(l);
  }

 /**
  * Gets the pos info.
  * @return a pos info.
  */
  public PosInfo getPosInfo() {
    return pi;
  }

 /**
  * Fires the specified PosEvent to pos listeners.
  * @param <code>e</code> the specified pos event.
  */
  protected /*C#virtual*/ void perform (PosEvent e)
  {
    if (support != null)
     for (int i=0;i<support.size();i++)
      ((PosListener)support.elementAt(i)).posChanged(e);
  }

 /**
  * Gets a line number and a column number by the specified text offset for the specified controller.
  * @param <code>offset</code> the specified offset.
  * @param <code>pc</code> the specified position controller.
  * @return the Point object where the <code>x</code> field is the line number and the <code>y</code> field
  * is the column number.
  */
  public static Point getPointByOffset(int offset, PosController pc)
  {
     PosInfo pi = pc.getPosInfo();
     if (pi == null) return null;

     if (offset > pc.prevOffset)
     {
       boolean b = pc.prevOffset < 0;
       int startOffset = b?0:pc.prevOffset - pc.prevCol;
       int startLine   = b?0:pc.prevLine;
       for (int i=startLine; i < pi.getLines(); i++)
       {
         int l = pi.getLineSize(i);
         if (offset >= startOffset && offset <  startOffset + l) return new Point(i, offset - startOffset);
         startOffset += l;
       }
     }
     else
     if (offset < pc.prevOffset)
     {
       boolean b = (pc.prevLine >= pi.getLines() || pc.prevOffset > pi.getMaxOffset());
       int startLine = b?pi.getLines() - 1:pc.prevLine;
       int endOffset = b?pi.getMaxOffset():pc.prevOffset + (pi.getLineSize(startLine) - pc.prevCol - 1);
       for (int i=startLine; i >= 0; i--)
       {
         int l = pi.getLineSize(i);
         if (offset <= endOffset && offset >=  endOffset - l + 1) return new Point(i, l - (endOffset - offset + 1));
         endOffset -= l;
       }
     }
     return new Point (pc.prevLine, pc.prevCol);
  }

 /**
  * Gets an offset by the specified text line and column for the specified controller.
  * @param <code>row</code> the specified text line number.
  * @param <code>col</code> the specified text column number.
  * @param <code>pc</code> the specified position controller.
  * @return the offset.
  */
  public static int getOffsetByPoint(int row, int col, PosController pc)
  {
    PosInfo pi = pc.getPosInfo();
    int startOffset = pc.prevOffset < 0?0:pc.prevOffset - pc.prevCol;
    int startLine   = pc.prevOffset < 0?0:pc.prevLine;

    if (startLine <= row)
    {
      for (int i = startLine; i<row; i++)
        startOffset += pi.getLineSize(i);
    }
    else
    {
      for (int i = startLine-1; i>=row; i--)
        startOffset -= pi.getLineSize(i);
    }
    return startOffset + col;
  }

 /**
  * Calculates and returns the maximal offset for the specified pos info.
  * @param <code>pi</code> the specified pos info.
  * @return a maximal offset.
  */
  public static int calcMaxOffset(PosInfo pi)
  {
    int max = 0;
    for (int i = 0; i < pi.getLines(); i++) max += pi.getLineSize(i);
    return max - 1;
  }
}


