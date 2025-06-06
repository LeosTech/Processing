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
 * ---------------------------
 * CategoryLineAnnotation.java
 * ---------------------------
 * (C) Copyright 2005-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (patch 2809117);
 *                   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
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
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;
import org.jfree.data.category.CategoryDataset;

/**
 * A line annotation that can be placed on a {@link CategoryPlot}.
 */
public class CategoryLineAnnotation extends AbstractAnnotation 
        implements CategoryAnnotation, Cloneable, PublicCloneable,
        Serializable {

    /** For serialization. */
    static final long serialVersionUID = 3477740483341587984L;

    /** The category for the start of the line. */
    private Comparable category1;

    /** The value for the start of the line. */
    private double value1;

    /** The category for the end of the line. */
    private Comparable category2;

    /** The value for the end of the line. */
    private double value2;

    /** The line color. */
    private transient Paint paint = Color.BLACK;

    /** The line stroke. */
    private transient Stroke stroke = new BasicStroke(1.0f);

    /**
     * Creates a new annotation that draws a line between (category1, value1)
     * and (category2, value2).
     *
     * @param category1  the category ({@code null} not permitted).
     * @param value1  the value (must be finite).
     * @param category2  the category ({@code null} not permitted).
     * @param value2  the value (must be finite).
     * @param paint  the line color ({@code null} not permitted).
     * @param stroke  the line stroke ({@code null} not permitted).
     */
    public CategoryLineAnnotation(Comparable category1, double value1,
                                  Comparable category2, double value2,
                                  Paint paint, Stroke stroke) {
        super();
        Args.nullNotPermitted(category1, "category1");
        Args.requireFinite(value1, "value1");
        Args.nullNotPermitted(category2, "category2");
        Args.requireFinite(value2, "value2");
        Args.nullNotPermitted(paint, "paint");
        Args.nullNotPermitted(stroke, "stroke");
        this.category1 = category1;
        this.value1 = value1;
        this.category2 = category2;
        this.value2 = value2;
        this.paint = paint;
        this.stroke = stroke;
    }

    /**
     * Returns the category for the start of the line.
     *
     * @return The category for the start of the line (never {@code null}).
     *
     * @see #setCategory1(Comparable)
     */
    public Comparable getCategory1() {
        return this.category1;
    }

    /**
     * Sets the category for the start of the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param category  the category ({@code null} not permitted).
     *
     * @see #getCategory1()
     */
    public void setCategory1(Comparable category) {
        Args.nullNotPermitted(category, "category");
        this.category1 = category;
        fireAnnotationChanged();
    }

    /**
     * Returns the y-value for the start of the line.
     *
     * @return The y-value for the start of the line.
     *
     * @see #setValue1(double)
     */
    public double getValue1() {
        return this.value1;
    }

    /**
     * Sets the y-value for the start of the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param value  the value (must be finite).
     *
     * @see #getValue1()
     */
    public void setValue1(double value) {
        Args.requireFinite(value, "value");
        this.value1 = value;
        fireAnnotationChanged();
    }

    /**
     * Returns the category for the end of the line.
     *
     * @return The category for the end of the line (never {@code null}).
     *
     * @see #setCategory2(Comparable)
     */
    public Comparable getCategory2() {
        return this.category2;
    }

    /**
     * Sets the category for the end of the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param category  the category ({@code null} not permitted).
     *
     * @see #getCategory2()
     */
    public void setCategory2(Comparable category) {
        Args.nullNotPermitted(category, "category");
        this.category2 = category;
        fireAnnotationChanged();
    }

    /**
     * Returns the y-value for the end of the line.
     *
     * @return The y-value for the end of the line.
     *
     * @see #setValue2(double)
     */
    public double getValue2() {
        return this.value2;
    }

    /**
     * Sets the y-value for the end of the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param value  the value (must be finite).
     *
     * @see #getValue2()
     */
    public void setValue2(double value) {
        Args.requireFinite(value, "value");
        this.value2 = value;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint used to draw the connecting line.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setPaint(Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint used to draw the connecting line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.paint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the stroke used to draw the connecting line.
     *
     * @return The stroke (never {@code null}).
     *
     * @see #setStroke(Stroke)
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke used to draw the connecting line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getStroke()
     */
    public void setStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.stroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Draws the annotation.
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param plot  the plot ({@code null} not permitted).
     * @param dataArea  the data area ({@code null} not permitted).
     * @param domainAxis  the domain axis ({@code null} not permitted).
     * @param rangeAxis  the range axis ({@code null} not permitted).
     */
    @Override
    public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea,
                     CategoryAxis domainAxis, ValueAxis rangeAxis) {

        CategoryDataset dataset = plot.getDataset();
        int catIndex1 = dataset.getColumnIndex(this.category1);
        int catIndex2 = dataset.getColumnIndex(this.category2);
        int catCount = dataset.getColumnCount();

        double lineX1 = 0.0f;
        double lineY1 = 0.0f;
        double lineX2 = 0.0f;
        double lineY2 = 0.0f;
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
            plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
            plot.getRangeAxisLocation(), orientation);

        if (orientation == PlotOrientation.HORIZONTAL) {
            lineY1 = domainAxis.getCategoryJava2DCoordinate(
                CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea,
                domainEdge);
            lineX1 = rangeAxis.valueToJava2D(this.value1, dataArea, rangeEdge);
            lineY2 = domainAxis.getCategoryJava2DCoordinate(
                CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea,
                domainEdge);
            lineX2 = rangeAxis.valueToJava2D(this.value2, dataArea, rangeEdge);
        } else if (orientation == PlotOrientation.VERTICAL) {
            lineX1 = domainAxis.getCategoryJava2DCoordinate(
                CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea,
                domainEdge);
            lineY1 = rangeAxis.valueToJava2D(this.value1, dataArea, rangeEdge);
            lineX2 = domainAxis.getCategoryJava2DCoordinate(
                CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea,
                domainEdge);
            lineY2 = rangeAxis.valueToJava2D(this.value2, dataArea, rangeEdge);
        }
        g2.setPaint(this.paint);
        g2.setStroke(this.stroke);
        g2.drawLine((int) lineX1, (int) lineY1, (int) lineX2, (int) lineY2);
    }

    /**
     * Tests this object for equality with another.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CategoryLineAnnotation)) {
            return false;
        }
        CategoryLineAnnotation that = (CategoryLineAnnotation) obj;
        if (!Objects.equals(this.category1, that.category1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value1) !=
            Double.doubleToLongBits(that.value1)) {
            return false;
        }
        if (!Objects.equals(this.category2, that.category2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value2) !=
            Double.doubleToLongBits(that.value2)) {
            return false;
        }
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (!Objects.equals(this.stroke, that.stroke)) {
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
        return (other instanceof CategoryLineAnnotation);
    }
    
    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode(); // equals calls superclass, hashCode must also
        result = 37 * result + Objects.hashCode(this.category1);
        long temp = Double.doubleToLongBits(this.value1);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + Objects.hashCode(this.category2);
        temp = Double.doubleToLongBits(this.value2);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + HashUtils.hashCodeForPaint(this.paint);
        result = 37 * result + Objects.hashCode(this.stroke);
        return result;
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this class will not throw this
     *         exception, but subclasses (if any) might.
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
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.paint, stream);
        SerialUtils.writeStroke(this.stroke, stream);
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
        this.paint = SerialUtils.readPaint(stream);
        this.stroke = SerialUtils.readStroke(stream);
    }

}
