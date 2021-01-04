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
package org.zaval.util;

import java.util.*;

/**
 * This is timer component. The class is used to organize periodic execution for the
 * registered Runnable interfaces. The class contains <code>getTimer</code> static method
 * to get the Timer instance. Using the method you can get shared timer instance or own
 * (new) timer instance. Shared instance can be used by several consumers.
 */
public class Timer
extends Thread
{
  private Vector    consumers = new Vector();
  private Hashtable map       = new Hashtable ();
  /*C#private System.Threading.ManualResetEvent signal = new System.Threading.ManualResetEvent(false);*/

  Timer() {
    super("org.zaval.util.Timer");
    start();
  }

 /**
  * Adds the specified runnable interface to be executed in the given time with the specified
  * period.
  * @param <code>l</code> the specified runnable interface.
  * @param <code>startIn</code> the specified time (milliseconds) after that the interface will
  * be executed first.
  * @param <code>repeatIn</code> the specified period (milliseconds) of the runnable interface
  * execution.
  */
  public void add(Runnable l, int startIn, int repeatIn)
  {
    synchronized (consumers)
    {
      if (map.get(l) == null) consumers.addElement(l);
      map.put (l, new int [] {startIn, repeatIn});
      if (consumers.size() == 1) consumers.notifyAll(); /*java*/
      /*C#if (consumers.size() == 1) signal.Set();*/
    }
  }

 /**
  * Removes the specified runnable interface so it no longer to be executed.
  * @param <code>l</code> the specified runnable interface.
  */
  public void remove(Runnable l)
  {
    synchronized (consumers)
    {
      if (consumers.size()>0)
      {
        consumers.removeElement(l);
        map.remove(l);
      }
    }
  }

 /**
  * Clears tick counter for the specified runnable interface. The method allows
  * to put off the runnable interface executing.
  * @param <code>l</code> the specified runnable interface.
  */
  public void clear(Runnable l)
  {
    synchronized (consumers)
    {
      if (consumers.size()>0)
      {
        int[] t = (int[])map.get(l);
        t[0] = t[1];
      }
    }
  }

  public /*C#override*/ void run ()
  {
    for(;!isInterrupted();)
    {
      try {
        synchronized (consumers) {                            /*java*/
          while (consumers.size() == 0) consumers.wait();     /*java*/
        }                                                     /*java*/
        /*C#if (consumers.size()==0) signal.WaitOne();*/
        Thread.sleep(quantum);
      }
      catch (InterruptedException e) {
        break;
      }

      synchronized (consumers)
      {
        int size = consumers.size();
        for (int i=0; i<size; i++)
        {
          Runnable r = (Runnable)consumers.elementAt(i);
          int[]    t = (int[])map.get(r);
          if (t[0] <= 0)
          {
            try {
              r.run();
            }
            catch (Error e) { e.printStackTrace(); }
            t[0] += t[1];
          }
          else t[0] -= quantum;
        }
      }
    }

    synchronized (consumers)
    {
      consumers.removeAllElements();
      map.clear();
    }
  }

  /*public void interrupt() {
    if (timer == this) throw new RuntimeException ();
    super.interrupt();
  } */

 /**
  * Gets a timer instance. The specified flag indicates if the timer instance should be created
  * or not.
  * @param <code>b</code> the specified flag. If the parameter is <code>true</code> than
  * a new timer instance will be created otherwise shared timer instance will be returned.
  * @return the timer instance.
  */
  public static Timer getTimer (boolean b)
  {
    if (b) return new Timer();
    else
    {
      if (timer.isAlive()) return timer;
      else
      {
        timer = new Timer();
        return timer;
      }
    }
  }

  private static Timer timer   = new Timer();
  private static int   quantum = 20;
}


