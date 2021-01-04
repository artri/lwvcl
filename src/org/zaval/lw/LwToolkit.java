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

import org.zaval.lw.event.*;

import java.awt.*;
import java.lang.reflect.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

/**
 * This class provides set of useful static methods for the lightweight library.
 */
public abstract class LwToolkit
{
 /**
  * The horizontal alignment constant.
  */
  public static final int HORIZONTAL = 1;

 /**
  * The vertical alignment constant.
  */
  public static final int VERTICAL = 2;

 /**
  * The none vertical and none horizontal alignment constant.
  */
  public static final int NONE = 0;

 /**
  * The dark blue color definition.
  */
  public static final Color darkBlue = new Color(0, 0, 140);

 /**
  * The default font definition.
  */
  public static final Font FONT = new Font("Dialog", Font.PLAIN, 12);

 /**
  * The default small font definition.
  */
  public static final Font SFONT = new Font("Dialog", Font.PLAIN, 10);

 /**
  * The default bold font definition.
  */
  public static final Font BFONT = new Font("Dialog", Font.BOLD, 12);

 /**
  * The default font metrics definition.
  */
  public static final FontMetrics FONT_METRICS = Toolkit.getDefaultToolkit().getFontMetrics(FONT);

 /**
  * The default foreground color definition.
  */
  public static final Color FG_COLOR = Color.black;

 /**
  * The default background color definition.
  */
  public static final Color BACK_COLOR = new Color(204, 204, 204);

 /**
  * The default preferred size definition.
  */
  public static final Dimension PS_SIZE = new Dimension();


  public static final int ACTION_KEY    = 1;

  public static final int CANCEL_KEY    = 2;

  public static final int PASSFOCUS_KEY = 3;

  private static Hashtable  staticObjects;
  private static String     base;

  //
  //
  //  Start NONE-Abstract method
  //
  //

  private static final int MAX_CASH_SIZE = 500000;

  private static final Toolkit  tool = Toolkit.getDefaultToolkit();
  private static Hashtable      images;
  private static int            currentCashSize;
  private static MediaTracker   media = new MediaTracker(new Canvas());
  private static String         version;
  private static Vector         managers = new Vector();

 /**
  * Creates a desktop instance.
  * @return a desktop instance.
  */
  public static final LwDesktop createDesktop() {
    return new LwRoot();
  }

 /**
  * Reads a properties file by the specified path and returns it as java.util.Properties instance.
  * The specified path points to the properties file relatively LwToolkit.class package.
  * If the path starts with '/' it is used as is.
  * @param <code>name</code> the specified path.
  */
  public static Properties getProperties(String name)
  {
    InputStream is = LwToolkit.class.getResourceAsStream(name.charAt(0)=='/'?name:(getResourcesBase() + name));
    if (is != null)
    {
      Properties p = new Properties();
      try {
        p.load(is);
        return p;
      }
      catch (IOException e) { e.printStackTrace (); }
    }
    return null;
  }

 /**
  * Reads an image by the specified path. The path is relative class package.
  * If the path starts with '/' it is used as is.
  * @param <code>name</code> the specified path.
  */
  public static Image getImage(String name)
  {
    if (images == null) images = new Hashtable();
    Image img = (Image)images.get(name);
    if (img != null) return img;
    InputStream is = LwToolkit.class.getResourceAsStream(name.charAt(0)=='/'?name:(getResourcesBase() + name));
    if (is == null) return null;
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    byte[] buf   = new byte[512];
    int    count = 0;
    try {
      while ((count = is.read(buf, 0, buf.length))>=0)
        bo.write(buf, 0, count);
      img = tool.createImage(bo.toByteArray());

      media.addImage(img, 0);
      media.waitForID(0);
      if (media.isErrorID(0)) System.out.println ("Cannot load " + name);
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    finally {
      if (img != null) media.removeImage(img);
      try {
        if (is != null) is.close();
      } catch(IOException ee) {}
    }

    if (bo.size() + currentCashSize < MAX_CASH_SIZE)
    {
      currentCashSize += bo.size();
      images.put(name, img);
    }
    return img;
  }

 /**
  * Gets the font metrics for the specified font.
  * @param <code>f</code> the specified font.
  * @return a font metrics for the specified font.
  */
  public static FontMetrics getFontMetrics(Font f) {
    return f == null?FONT_METRICS:tool.getFontMetrics(f);
  }

 /**
  * Draws the horizontal dotted line between the (x1,y1) and (x2,y1) coordinates.
  * @param <code>gr</code> the specified context to be used for drawing.
  * @param <code>x1</code> the x coordinate of the start of the line.
  * @param <code>x2</code> the x coordinate of the end of the line.
  * @param <code>y1</code> the y coordinate of the start and end of the line.
  */
  public static void drawDotHLine(Graphics gr, int x1, int x2, int y1) {
    for (;x1 <= x2; x1+=2)  gr.drawLine (x1, y1, x1, y1);
  }

 /**
  * Draws the vertical dotted line between the (x1,y1) and (x1,y2) coordinates.
  * @param <code>gr</code> the specified context to be used for drawing.
  * @param <code>y1</code> the y coordinate of the start of the line.
  * @param <code>y2</code> the y coordinate of the end of the line.
  * @param <code>x1</code> the x coordinate of the start and end of the line.
  */
  public static void drawDotVLine(Graphics gr, int y1, int y2, int x1) {
    for (;y1 <= y2; y1+=2)  gr.drawLine (x1, y1, x1, y1);
  }

 /**
  * Draws the dotted outline of the specified rectangle using the current color.
  * The resulting rectangle will cover an area (w + 1) pixels wide by (h + 1) pixels tall.
  * @param <code>gr</code> the specified context to be used for drawing.
  * @param <code>x</code> the x coordinate of the rectangle to be drawn (left top corner).
  * @param <code>y</code> the y coordinate of the rectangle to be drawn (left top corner).
  * @param <code>w</code> the width of the rectangle to be drawn.
  * @param <code>h</code> the height of the rectangle to be drawn.
  */
  public static void drawDotRect(Graphics gr, int x, int y, int w, int h)
  {
     int x2 = x + w - 1, y2 = y + h - 1;
     drawDotVLine(gr, y, y2, x);
     drawDotHLine(gr, x, x2, y);
     drawDotVLine(gr, y, y2, x2);
     drawDotHLine(gr, x, x2, y2);
  }

 /**
  * Gets the size of the screen.
  * @return a size of the screen.
  */
  public static final Dimension getScreenSize () {
    return tool.getScreenSize ();
  }

 /**
  * Draws marker for the specified rectangular area, the given background and foreground
  * colors.
  * @param <code>g</code> the specified graphics context.
  * @param <code>x</code> the x coordinate of the top-left corner of the rectangular area.
  * @param <code>y</code> the y coordinate of the top-left corner of the rectangular area.
  * @param <code>w</code> the width of the rectangular area.
  * @param <code>h</code> the height of the rectangular area.
  * @param <code>bg</code> the background color.
  * @param <code>fc</code> the foreground color.
  */
  public static void drawMarker(Graphics g, int x, int y, int w, int h, Color bg, Color fc)
  {
    try {
      g.setXORMode(bg);
      g.setColor(fc);
      g.fillRect(x, y, w, h);
    }
    finally {
      g.setPaintMode();
    }
  }

 /**
  * Draws line using XOR mode.
  * @param <code>target</code> the specified component.
  * @param <code>x1</code> the first x coordinate of the line.
  * @param <code>y1</code> the first y coordinate of the line.
  * @param <code>x2</code> the second x coordinate of the line.
  * @param <code>y2</code> the second y coordinate of the line.
  */
  public static void drawXORLine (LwComponent target, int x1, int y1, int x2, int y2)
  {
     Graphics g = getDesktop(target).getGraphics();
     try {
       Point p = getAbsLocation(target);
       g.setXORMode(Color.white);
       g.setColor  (Color.black);
       g.drawLine  (p.x + x1, p.y + y1, p.x + x2, p.y + y2);
     }
     finally {
       if (g != null) g.dispose();
     }
  }

 /**
  * Draws rectangle using XOR mode.
  * @param <code>target</code> the specified component.
  * @param <code>x</code> the top-left corner x coordinate of the rectangle.
  * @param <code>y</code> the top-left corner y coordinate of the rectangle.
  * @param <code>w</code> the rectangle width.
  * @param <code>h</code> the rectangle height.
  */
  public static void drawXORRect (LwComponent target, int x, int y, int w, int h)
  {
     Graphics g = getDesktop(target).getGraphics();
     try {
       Point p = getAbsLocation(target);
       g.setXORMode(Color.white);
       g.setColor  (Color.black);
       g.drawRect  (p.x + x, p.y + y, w, h);
     }
     finally {
       if (g != null) g.dispose();
     }
  }

 /**
  * Fills the rectangle using XOR mode.
  * @param <code>target</code> the specified component.
  * @param <code>x</code> the top-left corner x coordinate of the rectangle.
  * @param <code>y</code> the top-left corner y coordinate of the rectangle.
  * @param <code>w</code> the rectangle width.
  * @param <code>h</code> the rectangle height.
  */
  public static void fillXORRect (LwComponent target, int x, int y, int w, int h)
  {
     Graphics g = getDesktop(target).getGraphics();
     try {
       Point p = getAbsLocation(target);
       g.setXORMode(Color.white);
       g.setColor  (Color.black);
       g.fillRect  (p.x + x, p.y + y, w, h);
     }
     finally {
       if (g != null) g.dispose();
     }
  }

 /**
  * Don't touch the method it will be redesigned in the further version.
  */
  public static boolean isActionMask(int mask) {
    return mask == 0 || ((mask & java.awt.event.InputEvent.BUTTON1_MASK) >  0&&
                         (mask & java.awt.event.InputEvent.BUTTON3_MASK) == 0  );
  }

 /**
  * Gets the list of available fonts names.
  * @return a list of available fonts names.
  */
  public static String[] getFontList() {
    return tool.getFontList();
  }

 /**
  * Gets the key type for the specified key code and key mask.
  * The method should return one of the following constant : ACTION_KEY, CANCEL_KEY, PASSFOCUS_KEY or
  * -1 in all other cases.
  * @param <code>keyCode</code> the specified key code.
  * @param <code>mask</code> the specified key mask.
  * @return a key type.
  */
  public static int getKeyType(int keyCode, int mask)
  {
    if (keyCode == KeyEvent.VK_ENTER || (char)keyCode == ' ') return ACTION_KEY;
    else
    if (keyCode == KeyEvent.VK_ESCAPE) return CANCEL_KEY;
    else
    if (keyCode == KeyEvent.VK_TAB && mask == 0) return PASSFOCUS_KEY;
    return -1;
  }

  public static Object createObj(String className, String argsStr)
  throws Exception
  {
      if (className.indexOf ('@') == 0) return getStaticObj(className.substring(1));
      else
      {
        Class  classInstance = Class.forName(className.indexOf('.')>0?className:PACKAGE_NAME + className);
	Object objInstance   = null;
	if (argsStr == null) objInstance = classInstance.newInstance();
        else
        {
          StringTokenizer args       = new StringTokenizer(argsStr, ",");
          Class[]         argsTypes  = new Class [args.countTokens()];
          Object[]        argsValues = new Object[argsTypes.length];
          for (int j = 0; args.hasMoreTokens(); j++)
          {
            String arg = args.nextToken().trim();
            if (arg.indexOf('\"')==0)
            {
              argsTypes [j] = String.class;
              argsValues[j] = arg.substring(1, arg.length() - 1);
            }
            else
            if (arg.equals("true") || arg.equals("false"))
            {
              argsTypes [j] = Boolean.TYPE;
              argsValues[j] = new Boolean(arg);
            }
            else
            if (arg.indexOf('@') == 0)
            {
              argsValues[j] = getStaticObj(arg.substring(1));
              argsTypes [j] = argsValues[j].getClass();
            }
            else
            {
              argsTypes [j] = Integer.TYPE;
              argsValues[j] = new Integer(arg);
            }
          }
          objInstance = classInstance.getConstructor(argsTypes).newInstance(argsValues);
        }

        if (objInstance instanceof LwManager && objInstance instanceof EventListener)
          getEventManager().addXXXListener((EventListener)objInstance);
        return objInstance;
      }
  }

  private static final String PACKAGE_NAME = "org.zaval.lw.";

  //
  //
  //  End NONE-Abstract
  //
  //


 /**
  * Loads objects by the specified key of the specified properties set into static objects
  * table.
  * @param <code>key</code> the specified key that defines set of objects that should be
  * loaded.
  * @param <code>props</code> the specified properties.
  */
  public static void loadObjs(String key, Properties props)
  throws Exception
  {
    if (staticObjects == null) staticObjects = new Hashtable();
    String keyStr = props.getProperty(key);
    if (keyStr != null)
    {
      StringTokenizer st = new StringTokenizer(keyStr, ",");
      while(st.hasMoreTokens())
      {
        String token = st.nextToken();
        String kname = props.getProperty(key + "." + token + ".key");
        Object obj   = createObj(props.getProperty(key + "." + token + ".cl" ),props.getProperty(key + "." + token + ".arg"));
        if (obj instanceof LwManager) managers.addElement(obj);
        staticObjects.put(kname == null?token:kname, obj);
      }
    }
  }

 /**
  * Gets the static object by the specified key.
  * @param <code>key</code> the specified key.
  * @return a static object.
  */
  public static Object getStaticObj(String key) {
    return staticObjects.get(key);
  }

 /**
  * Gets a static object by the specified key as LwView class.
  * @param <code>key</code> the specified key.
  * @return a static object.
  */
  public static LwView getView(String key) {
    return (LwView)staticObjects.get(key);
  }

 /**
  * Gets the library version.
  * @return the library version.
  */
  public static final String getVersion () {
    return version;
  }

  public static final String getResourcesBase() {
    return base;
  }

 /**
  * Gets a desktop container for the specified component. Desktop container is top-level
  * container where the specified component is resided.
  * @param <code>c</code> the specified component.
  * @return a desktop.
  */
  public static LwDesktop getDesktop(LwComponent c) {
    for (;c != null && !(c instanceof LwDesktop);c = c.getLwParent());
    return c==null?null:(LwDesktop)c;
  }

 /**
  * Returns the immediate child component for the parent and child.
  * @param <code>parent</code> the parent component.
  * @param <code>child</code>  the child component.
  * @return an immediate child component.
  */
  public static LwComponent getDirectChild(LwComponent parent, LwComponent child) {
    for (;child != null && child.getLwParent() != parent; child = child.getLwParent());
    return child;
  }

 /**
  * Returns the immediate child component at the specified location of the parent component.
  * @param <code>x</code> the x coordinate relatively the parent component.
  * @param <code>y</code> the y coordinate relatively the parent component.
  * @param <code>p</code> the parent component.
  * @return an immediate child component.
  */
  public static int getDirectCompAt(int x, int y, LwContainer p)
  {
    for (int i=0; i<p.count(); i++)
    {
      LwComponent c = (LwComponent)p.get(i);
      if (c.isVisible())
      {
        Rectangle b = c.getBounds();
        if (b.contains(x, y)) return i;
      }
    }
    return -1;
  }

 /**
  * Checks if the component is contained in the component hierarchy of this container.
  * @param <code>p</code> the specified parent component.
  * @param <code>c</code> the specified child component.
  * @return <code>true</code> if the component is contained in the parent component hierarchy;
  * otherwise <code>false</code>.
  */
  public static boolean isAncestorOf(LwComponent p, LwComponent c) {
    while (c != null && c != p) c = c.getLwParent();
    return c != null;
  }

 /**
  * Returns an absolute location for the given relative location of the component.
  * The absolute location is calculated relatively a native component where the
  * light weight component is resided.
  * @param <code>x</code> the x coordinate relatively the component.
  * @param <code>y</code> the y coordinate relatively the component.
  * @param <code>c</code> the lightweight component.
  * @return an absolute location.
  */
  public static Point getAbsLocation(int x, int y, LwComponent c)
  {
    LwComponent p = null;
    while ((p = c.getLwParent()) != null)
    {
      x += c.getX();
      y += c.getY();
      c = p;
    }
    return new Point (x, y);
  }

 /**
  * Returns an absolute location of the component.
  * The absolute location is calculated relatively a native component where the
  * light weight component is resided.
  * @param <code>c</code> the lightweight component.
  * @return an absolute location of the component.
  */
  public static Point getAbsLocation(LwComponent c) {
    return getAbsLocation(0, 0, c);
  }

 /**
  * Returns a relative location for the specified absolute location relatively the light weight component.
  * @param <code>x</code> the x coordinate relatively a native where the component is resided.
  * @param <code>y</code> the y coordinate relatively a native where the component is resided.
  * @param <code>c</code> the lightweight component.
  * @return a relative location.
  */
  public static Point getRelLocation(int x, int y, LwComponent c) {
    return getRelLocation(x, y, getDesktop(c), c);
  }

 /**
  * Returns a relative location for the specified target location relatively the light weight component.
  * @param <code>x</code> the x coordinate relatively the target component where the component
  * is resided.
  * @param <code>y</code> the y coordinate relatively the target component where the component
  * is resided.
  * @param <code>target</code> the target lightweight component.
  * @param <code>c</code> the lightweight component.
  * @return a relative location.
  */
  public static Point getRelLocation(int x, int y, LwComponent target, LwComponent c)
  {
    while (c != target)
    {
      x -= c.getX();
      y -= c.getY();
      c = c.getLwParent();
    }
    return new Point(x, y);
  }

 /**
  * Calculates and gets a maximal preferred size among visible children of the specified
  * container.
  * @param <code>target</code> the container.
  * @return a maximal preferred size.
  */
  public static Dimension getMaxPreferredSize(LayoutContainer target)
  {
     int maxWidth = 0, maxHeight = 0;
     for (int i=0; i<target.count(); i++)
     {
       Layoutable l = target.get(i);
       if (l.isVisible())
       {
         Dimension ps = l.getPreferredSize();
         if (ps.width  > maxWidth ) maxWidth = ps.width;
         if (ps.height > maxHeight) maxHeight = ps.height;
       }
     }
     return new Dimension(maxWidth, maxHeight);
  }

 /**
  * Calculates and gets origin for the specified area of the component. The origin is
  * calculated as a location of the component view to have the specified area inside
  * a visible part of the component.
  * @param <code>x</code> the x coordinate of the component area.
  * @param <code>y</code> the y coordinate of the component area.
  * @param <code>w</code> the width of the component area.
  * @param <code>h</code> the height of the component area.
  * @param <code>target</code> the component.
  * @return an origin of the component.
  */
  public static Point calcOrigin (int x, int y, int w, int h, LwComponent target) {
    Point origin = target.getOrigin();
    return calcOrigin (x, y, w, h, origin==null?0:origin.x, origin==null?0:origin.y, target);
  }

 /**
  * Calculates and gets origin for the specified area of the component relatively the specified
  * previous origin. The origin is calculated as a location of the component view to have the
  * specified area inside a visible part of the component.
  * @param <code>x</code> the x coordinate of the component area.
  * @param <code>y</code> the y coordinate of the component area.
  * @param <code>w</code> the width of the component area.
  * @param <code>h</code> the height of the component area.
  * @param <code>px</code> the x coordinate of the previous origin.
  * @param <code>py</code> the y coordinate of the previous origin.
  * @param <code>target</code> the component.
  * @return an origin of the component.
  */
  public static Point calcOrigin (int x, int y, int w, int h, int px, int py, LwComponent target) {
    return calcOrigin (x, y, w, h, px, py, target, target.getInsets());
  }

 /**
  * Calculates and gets origin for the specified area of the component relatively the specified
  * previous origin. The origin is calculated as a location of the component view to have the
  * specified area inside a visible part of the component.
  * @param <code>x</code> the x coordinate of the component area.
  * @param <code>y</code> the y coordinate of the component area.
  * @param <code>w</code> the width of the component area.
  * @param <code>h</code> the height of the component area.
  * @param <code>px</code> the x coordinate of the previous origin.
  * @param <code>py</code> the y coordinate of the previous origin.
  * @param <code>target</code> the component.
  * @param <code>i</code> the insets.
  * @return Fan origin of the component.
  */
  public static Point calcOrigin (int x, int y, int w, int h, int px, int py, LwComponent target, Insets i)
  {
     int dw = target.getWidth(), dh = target.getHeight();
     if (dw > 0 && dh > 0)
     {
       if (dw - i.left - i.right >= w)
       {
         int xx = x + px;
         if (xx < i.left) px += (i.left - xx);
         else
         {
           xx += w;
           if (xx > dw - i.right) px -= (xx - dw + i.right);
         }
       }

       if (dh - i.top - i.bottom >= h)
       {
         int yy = y + py;
         if (yy < i.top) py += (i.top - yy);
         else
         {
           yy += h;
           if (yy > dh - i.bottom) py -= (yy - dh + i.bottom);
         }
       }
       return new Point(px, py);
     }
     else return new Point();
  }

 /**
  * Gets a graphics context for the specified lightweight component.
  * @param <code>c</code> the lightweight component.
  * @return a graphics context.
  */
  public static Graphics getGraphics(LwComponent c) {
    return getGraphics(c, 0, 0, c.getWidth(), c.getHeight());
  }

 /**
  * Gets a graphics context for the specified area of the lightweight component.
  * The method calculates clip area as intersecation the area bounds and a visible
  * part of the component.
  * @param <code>c</code> the lightweight component.
  * @param <code>x</code> the x coordinate of the component area.
  * @param <code>y</code> the y coordinate of the component area.
  * @param <code>w</code> the width of the component area.
  * @param <code>h</code> the height of the component area.
  * @return a graphics context.
  */
  public static Graphics getGraphics(LwComponent c, int x, int y, int w, int h)
  {
    LwDesktop nc = getDesktop(c);
    if (nc == null) return null;
    Rectangle vp = c.getVisiblePart();
    if (vp == null) return null;
    Graphics  gr = nc.getGraphics();
    if (gr == null) return null;
    Point     l  = getAbsLocation(c);
    gr.clipRect(vp.x + l.x + x, vp.y + l.y + y, vp.width , vp.height);
    gr.translate(l.x, l.y);
    return gr;
  }

  public final static LwPaintManager getPaintManager() {
    return (LwPaintManager)getStaticObj("paint");
  }

  public final static LwFocusManager getFocusManager() {
    return (LwFocusManager)getStaticObj("focus");
  }

  public final static LwEventManager getEventManager() {
    return (LwEventManager)getStaticObj("event");
  }

  public static LwCursorManager getCursorManager() {
    return (LwCursorManager)getStaticObj("cursor");
  }

  public static LwTooltipMan getTooltipManager() {
    return (LwTooltipMan)getStaticObj("tooltip");
  }

  public static void startVCL (String base)
  {
    if (staticObjects == null)
    {
      try {
        LwToolkit.base = (base == null)?"rs/":base;
        Properties props = getProperties("lw.properties");
        if (props == null) throw new RuntimeException("Properties file not found.");
        version = props.getProperty("lwvcl.ver");

        loadObjs("man", props);
        loadObjs("obj", props);
        props = getProperties("lwext.properties");
        if (props != null)
        {
          loadObjs("man", props);
          loadObjs("obj", props);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void stopVCL()
  {
    if (staticObjects != null)
    {
      for (int i=0; i<managers.size(); i++) ((LwManager)managers.elementAt(i)).dispose();
      managers.removeAllElements();
      if (images != null)
      {
        for (Enumeration en = images.elements();en.hasMoreElements();)
          ((Image)en.nextElement()).flush();
        images.clear();
      }
      staticObjects.clear();
      staticObjects = null;
    }
  }
}




