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
 * --------------------------
 * XYDataImageAnnotation.java
 * --------------------------
 * (C) Copyright 2008-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (patch 2809117);
                     Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.Range;

/**
 * An annotation that allows an image to be placed within a rectangle specified
 * in data coordinates on an {@link XYPlot}.  Note that this annotation
 * is not currently serializable, so don't use it if you plan on serializing
 * your chart(s).
 */
public class XYDataImageAnnotation extends AbstractXYAnnotation
        implements Cloneable, PublicCloneable, XYAnnotationBoundsInfo {

    /** The image. */
    private transient Image image;

    /**
     * The x-coordinate (in data space).
     */
    private double x;

    /**
     * The y-coordinate (in data space).
     */
    private double y;

    /**
     * The image display area width in data coordinates.
     */
    private double w;

    /**
     * The image display area height in data coordinates.
     */
    private double h;

    /**
     * A flag indicating whether the annotation should contribute to
     * the data range for a plot/renderer.
     */
    private boolean includeInDataBounds;

    /**
     * Creates a new annotation to be displayed within the specified rectangle.
     *
     * @param image  the image ({@code null} not permitted).
     * @param x  the x-coordinate (in data space).
     * @param y  the y-coordinate (in data space).
     * @param w  the image display area width.
     * @param h  the image display area height.
     */
    public XYDataImageAnnotation(Image image, double x, double y, double w,
            double h) {
        this(image, x, y, w, h, false);
    }

    /**
     * Creates a new annotation to be displayed within the specified rectangle.
     *
     * @param image  the image ({@code null} not permitted).
     * @param x  the x-coordinate (in data space).
     * @param y  the y-coordinate (in data space).
     * @param w  the image display area width.
     * @param h  the image display area height.
     * @param includeInDataBounds  a flag that controls whether the
     *     annotation is included in the data bounds for the axis autoRange.
     */
    public XYDataImageAnnotation(Image image, double x, double y, double w,
            double h, boolean includeInDataBounds) {

        super();
        Args.nullNotPermitted(image, "image");
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.includeInDataBounds = includeInDataBounds;
    }

    /**
     * Returns the image for the annotation.
     *
     * @return The image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Returns the x-coordinate (in data space) for the annotation.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate (in data space) for the annotation.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the width (in data space) of the data rectangle into which the
     * image will be drawn.
     *
     * @return The width.
     */
    public double getWidth() {
        return this.w;
    }

    /**
     * Returns the height (in data space) of the data rectangle into which the
     * image will be drawn.
     *
     * @return The height.
     */
    public double getHeight() {
        return this.h;
    }

    /**
     * Returns the flag that controls whether the annotation should
     * contribute to the autoRange for the axis it is plotted against.
     *
     * @return A boolean.
     */
    @Override
    public boolean getIncludeInDataBounds() {
        return this.includeInDataBounds;
    }

    /**
     * Returns the x-range for the annotation.
     *
     * @return The range.
     */
    @Override
    public Range getXRange() {
        return new Range(this.x, this.x + this.w);
    }

    /**
     * Returns the y-range for the annotation.
     *
     * @return The range.
     */
    @Override
    public Range getYRange() {
        return new Range(this.y, this.y + this.h);
    }

    /**
     * Draws the annotation.  This method is called by the drawing code in the
     * {@link XYPlot} class, you don't normally need to call this method
     * directly.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param rendererIndex  the renderer index.
     * @param info  if supplied, this info object will be populated with
     *              entity information.
     */
    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
                     ValueAxis domainAxis, ValueAxis rangeAxis,
                     int rendererIndex,
                     PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        AxisLocation xAxisLocation = plot.getDomainAxisLocation();
        AxisLocation yAxisLocation = plot.getRangeAxisLocation();
        RectangleEdge xEdge = Plot.resolveDomainAxisLocation(xAxisLocation,
                orientation);
        RectangleEdge yEdge = Plot.resolveRangeAxisLocation(yAxisLocation,
                orientation);
        float j2DX0 = (float) domainAxis.valueToJava2D(this.x, dataArea, xEdge);
        float j2DY0 = (float) rangeAxis.valueToJava2D(this.y, dataArea, yEdge);
        float j2DX1 = (float) domainAxis.valueToJava2D(this.x + this.w,
                dataArea, xEdge);
        float j2DY1 = (float) rangeAxis.valueToJava2D(this.y + this.h,
                dataArea, yEdge);
        float xx0 = 0.0f;
        float yy0 = 0.0f;
        float xx1 = 0.0f;
        float yy1 = 0.0f;
        if (orientation == PlotOrientation.HORIZONTAL) {
            xx0 = j2DY0;
            xx1 = j2DY1;
            yy0 = j2DX0;
            yy1 = j2DX1;
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            xx0 = j2DX0;
            xx1 = j2DX1;
            yy0 = j2DY0;
            yy1 = j2DY1;
        }
        // TODO: rotate the image when drawn with horizontal orientation?
        g2.drawImage(this.image, (int) xx0, (int) Math.min(yy0, yy1),
                (int) (xx1 - xx0), (int) Math.abs(yy1 - yy0), null);
        String toolTip = getToolTipText();
        String url = getURL();
        if (toolTip != null || url != null) {
            addEntity(info, new Rectangle2D.Float(xx0, yy0, (xx1 - xx0),
                    (yy1 - yy0)), rendererIndex, toolTip, url);
        }
    }

    /**
     * Tests this object for equality with an arbitrary object.
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
        if (!(obj instanceof XYDataImageAnnotation)) {
            return false;
        }
        XYDataImageAnnotation that = (XYDataImageAnnotation) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(that.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(that.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(that.w)) {
            return false;
        }
        if (Double.doubleToLongBits(this.h) != Double.doubleToLongBits(that.h)) {
            return false;
        }
        if (this.includeInDataBounds != that.includeInDataBounds) {
            return false;
        }
        if (!Objects.equals(this.image, that.image)) {
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
        return (other instanceof XYDataImageAnnotation);
    }

    /**
     * Returns a hash code for this object.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode(); // equals calls superclass, hashCode must also
        hash = 89 * hash + Objects.hashCode(this.image);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.x) ^ 
                                 (Double.doubleToLongBits(this.x) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.y) ^ 
                                 (Double.doubleToLongBits(this.y) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.w) ^ 
                                 (Double.doubleToLongBits(this.w) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.h) ^ 
                                 (Double.doubleToLongBits(this.h) >>> 32));
        hash = 89 * hash + (this.includeInDataBounds ? 1 : 0);
        return hash;
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the annotation can't be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        // FIXME
        //SerialUtils.writeImage(this.image, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        // FIXME
        //this.image = SerialUtils.readImage(stream);
    }

}
