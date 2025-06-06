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
 * CategoryTextAnnotation.java
 * ---------------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (patch 2809117);
 *                   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.category.CategoryDataset;

/**
 * A text annotation that can be placed on a {@link CategoryPlot}.
 */
public class CategoryTextAnnotation extends TextAnnotation
        implements CategoryAnnotation, Cloneable, PublicCloneable,
                   Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3333360090781320147L;

    /** The category. */
    private Comparable category;

    /** The category anchor (START, MIDDLE, or END). */
    private CategoryAnchor categoryAnchor;

    /** The value. */
    private double value;

    /**
     * Creates a new annotation to be displayed at the given location.
     *
     * @param text  the text ({@code null} not permitted).
     * @param category  the category ({@code null} not permitted).
     * @param value  the value.
     */
    public CategoryTextAnnotation(String text, Comparable category,
                                  double value) {
        super(text);
        Args.nullNotPermitted(category, "category");
        this.category = category;
        this.value = value;
        this.categoryAnchor = CategoryAnchor.MIDDLE;
    }

    /**
     * Returns the category.
     *
     * @return The category (never {@code null}).
     *
     * @see #setCategory(Comparable)
     */
    public Comparable getCategory() {
        return this.category;
    }

    /**
     * Sets the category that the annotation attaches to and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param category  the category ({@code null} not permitted).
     *
     * @see #getCategory()
     */
    public void setCategory(Comparable category) {
        Args.nullNotPermitted(category, "category");
        this.category = category;
        fireAnnotationChanged();
    }

    /**
     * Returns the category anchor point.
     *
     * @return The category anchor point.
     *
     * @see #setCategoryAnchor(CategoryAnchor)
     */
    public CategoryAnchor getCategoryAnchor() {
        return this.categoryAnchor;
    }

    /**
     * Sets the category anchor point and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param anchor  the anchor point ({@code null} not permitted).
     *
     * @see #getCategoryAnchor()
     */
    public void setCategoryAnchor(CategoryAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.categoryAnchor = anchor;
        fireAnnotationChanged();
    }

    /**
     * Returns the value that the annotation attaches to.
     *
     * @return The value.
     *
     * @see #setValue(double)
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the value and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param value  the value.
     *
     * @see #getValue()
     */
    public void setValue(double value) {
        this.value = value;
        fireAnnotationChanged();
    }

    /**
     * Draws the annotation.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     */
    @Override
    public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea,
            CategoryAxis domainAxis, ValueAxis rangeAxis) {

        CategoryDataset dataset = plot.getDataset();
        int catIndex = dataset.getColumnIndex(this.category);
        int catCount = dataset.getColumnCount();

        float anchorX = 0.0f;
        float anchorY = 0.0f;
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);

        if (orientation == PlotOrientation.HORIZONTAL) {
            anchorY = (float) domainAxis.getCategoryJava2DCoordinate(
                    this.categoryAnchor, catIndex, catCount, dataArea,
                    domainEdge);
            anchorX = (float) rangeAxis.valueToJava2D(this.value, dataArea,
                    rangeEdge);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            anchorX = (float) domainAxis.getCategoryJava2DCoordinate(
                    this.categoryAnchor, catIndex, catCount, dataArea,
                    domainEdge);
            anchorY = (float) rangeAxis.valueToJava2D(this.value, dataArea,
                    rangeEdge);
        }
        g2.setFont(getFont());
        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, anchorX, anchorY,
                getTextAnchor(), getRotationAngle(), getRotationAnchor());

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
        if (!(obj instanceof CategoryTextAnnotation)) {
            return false;
        }
        CategoryTextAnnotation that = (CategoryTextAnnotation) obj;
        if (!Objects.equals(this.category, that.category)) {
            return false;
        }
        if (!Objects.equals(this.categoryAnchor, that.categoryAnchor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) !=
            Double.doubleToLongBits(that.value)) {
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
        return (other instanceof CategoryTextAnnotation);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode(); // equals calls superclass, hashCode must also
        result = 37 * result + Objects.hashCode(this.category);
        result = 37 * result + Objects.hashCode(this.categoryAnchor);
        long temp = Double.doubleToLongBits(this.value);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
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

}
