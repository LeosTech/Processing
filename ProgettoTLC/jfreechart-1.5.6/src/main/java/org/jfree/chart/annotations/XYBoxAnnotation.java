/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * --------------------
 * XYBoxAnnotation.java
 * --------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (see patch 2809117);
                     Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import org.jfree.chart.HashUtils;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;

/**
 * A box annotation that can be placed on an {@link XYPlot}.  The
 * box coordinates are specified in data space.
 */
public class XYBoxAnnotation extends AbstractXYAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 6764703772526757457L;

    /** The lower x-coordinate. */
    private double x0;

    /** The lower y-coordinate. */
    private double y0;

    /** The upper x-coordinate. */
    private double x1;

    /** The upper y-coordinate. */
    private double y1;

    /** The stroke used to draw the box outline. */
    private transient Stroke stroke;

    /** The paint used to draw the box outline. */
    private transient Paint outlinePaint;

    /** The paint used to fill the box. */
    private transient Paint fillPaint;

    /**
     * Creates a new annotation (where, by default, the box is drawn
     * with a black outline).
     *
     * @param x0  the lower x-coordinate of the box (in data space).
     * @param y0  the lower y-coordinate of the box (in data space).
     * @param x1  the upper x-coordinate of the box (in data space).
     * @param y1  the upper y-coordinate of the box (in data space).
     */
    public XYBoxAnnotation(double x0, double y0, double x1, double y1) {
        this(x0, y0, x1, y1, new BasicStroke(1.0f), Color.BLACK);
    }

    /**
     * Creates a new annotation where the box is drawn as an outline using
     * the specified {@code stroke} and {@code outlinePaint}.
     *
     * @param x0  the lower x-coordinate of the box (in data space).
     * @param y0  the lower y-coordinate of the box (in data space).
     * @param x1  the upper x-coordinate of the box (in data space).
     * @param y1  the upper y-coordinate of the box (in data space).
     * @param stroke  the shape stroke ({@code null} permitted).
     * @param outlinePaint  the shape color ({@code null} permitted).
     */
    public XYBoxAnnotation(double x0, double y0, double x1, double y1,
                           Stroke stroke, Paint outlinePaint) {
        this(x0, y0, x1, y1, stroke, outlinePaint, null);
    }

    /**
     * Creates a new annotation.
     *
     * @param x0  the lower x-coordinate of the box (in data space, must be finite).
     * @param y0  the lower y-coordinate of the box (in data space, must be finite).
     * @param x1  the upper x-coordinate of the box (in data space, must be finite).
     * @param y1  the upper y-coordinate of the box (in data space, must be finite).
     * @param stroke  the shape stroke ({@code null} permitted).
     * @param outlinePaint  the shape color ({@code null} permitted).
     * @param fillPaint  the paint used to fill the shape ({@code null}
     *                   permitted).
     */
    public XYBoxAnnotation(double x0, double y0, double x1, double y1,
                           Stroke stroke, Paint outlinePaint, Paint fillPaint) {
        super();
        Args.requireFinite(x0, "x0");
        Args.requireFinite(y0, "y0");
        Args.requireFinite(x1, "x1");
        Args.requireFinite(y1, "y1");
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.stroke = stroke;
        this.outlinePaint = outlinePaint;
        this.fillPaint = fillPaint;
    }

    /**
     * Returns the x-coordinate for the bottom left corner of the box (set in the
     * constructor).
     * 
     * @return The x-coordinate for the bottom left corner of the box.
     */
    public double getX0() {
        return x0;
    }

    /**
     * Returns the y-coordinate for the bottom left corner of the box (set in the
     * constructor).
     * 
     * @return The y-coordinate for the bottom left corner of the box.
     */
    public double getY0() {
        return y0;
    }

    /**
     * Returns the x-coordinate for the top right corner of the box (set in the
     * constructor).
     * 
     * @return The x-coordinate for the top right corner of the box.
     */
    public double getX1() {
        return x1;
    }

    /**
     * Returns the y-coordinate for the top right corner of the box (set in the
     * constructor).
     * 
     * @return The y-coordinate for the top right corner of the box.
     */
    public double getY1() {
        return y1;
    }

    /**
     * Returns the stroke used for the box outline.
     * 
     * @return The stroke (possibly {@code null}). 
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Returns the paint used for the box outline.
     * 
     * @return The paint (possibly {@code null}). 
     */
    public Paint getOutlinePaint() {
        return outlinePaint;
    }

    /**
     * Returns the paint used for the box fill.
     * 
     * @return The paint (possibly {@code null}). 
     */
    public Paint getFillPaint() {
        return fillPaint;
    }

    /**
     * Draws the annotation.  This method is usually called by the
     * {@link XYPlot} class, you shouldn't need to call it directly.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param rendererIndex  the renderer index.
     * @param info  the plot rendering info.
     */
    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
                     ValueAxis domainAxis, ValueAxis rangeAxis,
                     int rendererIndex, PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);

        double transX0 = domainAxis.valueToJava2D(this.x0, dataArea,
                domainEdge);
        double transY0 = rangeAxis.valueToJava2D(this.y0, dataArea, rangeEdge);
        double transX1 = domainAxis.valueToJava2D(this.x1, dataArea,
                domainEdge);
        double transY1 = rangeAxis.valueToJava2D(this.y1, dataArea, rangeEdge);

        Rectangle2D box = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            box = new Rectangle2D.Double(transY0, transX1, transY1 - transY0,
                    transX0 - transX1);
        } else if (orientation == PlotOrientation.VERTICAL) {
            box = new Rectangle2D.Double(transX0, transY1, transX1 - transX0,
                    transY0 - transY1);
        }

        if (this.fillPaint != null) {
            g2.setPaint(this.fillPaint);
            g2.fill(box);
        }

        if (this.stroke != null && this.outlinePaint != null) {
            g2.setPaint(this.outlinePaint);
            g2.setStroke(this.stroke);
            g2.draw(box);
        }
        addEntity(info, box, rendererIndex, getToolTipText(), getURL());
    }

    /**
     * Tests this annotation for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYBoxAnnotation)) {
            return false;
        }
        XYBoxAnnotation that = (XYBoxAnnotation) obj;
        if (Double.doubleToLongBits(this.x0) !=
            Double.doubleToLongBits(that.x0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y0) != 
            Double.doubleToLongBits(that.y0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.x1) !=
            Double.doubleToLongBits(that.x1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y1) !=
            Double.doubleToLongBits(that.y1)) {
            return false;
        }
        if (!Objects.equals(this.stroke, that.stroke)) {
            return false;
        }
        if (!PaintUtils.equal(this.outlinePaint, that.outlinePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.fillPaint, that.fillPaint)) {
            return false;
        }
        // fix the "equals not symmetric" problem
        if (!that.canEqual(this)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Ensures symmetry between super/subclass implementations of equals. For
     * more detail, see http://jqno.nl/equalsverifier/manual/inheritance.
     *
     * @param other Object
     * 
     * @return true ONLY if the parameter is THIS class type
     */
    @Override
    public boolean canEqual(Object other) {
        // fix the "equals not symmetric" problem
        return (other instanceof XYBoxAnnotation);
    }
    
    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode(); // equals calls superclass, hashCode must also
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x0) ^
                                 (Double.doubleToLongBits(this.x0) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y0) ^
                                 (Double.doubleToLongBits(this.y0) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x1) ^
                                 (Double.doubleToLongBits(this.x1) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y1) ^
                                (Double.doubleToLongBits(this.y1) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.stroke);
        hash = 67 * hash + HashUtils.hashCodeForPaint(this.outlinePaint);
        hash = 67 * hash + HashUtils.hashCodeForPaint(this.fillPaint);
        return hash;
    }

    /**
     * Returns a clone.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException not thrown by this class, but may be
     *                                    by subclasses.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeStroke(this.stroke, stream);
        SerialUtils.writePaint(this.outlinePaint, stream);
        SerialUtils.writePaint(this.fillPaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        stream.defaultReadObject();
        this.stroke = SerialUtils.readStroke(stream);
        this.outlinePaint = SerialUtils.readPaint(stream);
        this.fillPaint = SerialUtils.readPaint(stream);
    }

}
