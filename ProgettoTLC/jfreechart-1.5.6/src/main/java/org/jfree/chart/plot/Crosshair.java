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
 * --------------
 * Crosshair.java
 * --------------
 * (C) Copyright 2009-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import org.jfree.chart.HashUtils;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.labels.StandardCrosshairLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A {@code Crosshair} represents a value on a chart and is usually displayed
 * as a line perpendicular to the x or y-axis (and sometimes including a label
 * that shows the crosshair value as text).  Instances of this class are used
 * to store the cross hair value plus the visual characteristics of the line
 * that will be rendered once the instance is added to a 
 * {@link CrosshairOverlay} (or {@code CrosshairOverlaydFX} if you are using 
 * the JavaFX extensions for JFreeChart).
 * <br><br>
 * Crosshairs support a property change mechanism which is used by JFreeChart
 * to automatically repaint the overlay whenever a crosshair attribute is 
 * updated.
 */
public class Crosshair implements Cloneable, PublicCloneable, Serializable {

    /** Flag controlling visibility. */
    private boolean visible;

    /** The crosshair value. */
    private double value;

    /** The paint for the crosshair line. */
    private transient Paint paint;

    /** The stroke for the crosshair line. */
    private transient Stroke stroke;

    /**
     * A flag that controls whether the crosshair has a label visible.
     */
    private boolean labelVisible;

    /**
     * The label anchor.
     */
    private RectangleAnchor labelAnchor;

    /** A label generator. */
    private CrosshairLabelGenerator labelGenerator;

    /**
     * The x-offset in Java2D units.
     */
    private double labelXOffset;

    /**
     * The y-offset in Java2D units.
     */
    private double labelYOffset;

    /**
     * The label padding.
     */
    private RectangleInsets labelPadding;

    /**
     * The label font.
     */
    private Font labelFont;

    /**
     * The label paint.
     */
    private transient Paint labelPaint;

    /**
     * The label background paint.
     */
    private transient Paint labelBackgroundPaint;

    /** A flag that controls the visibility of the label outline. */
    private boolean labelOutlineVisible;

    /** The label outline stroke. */
    private transient Stroke labelOutlineStroke;

    /** The label outline paint. */
    private transient Paint labelOutlinePaint;

    /** Property change support. */
    private transient PropertyChangeSupport pcs;

    /**
     * Creates a new crosshair with value 0.0.
     */
    public Crosshair() {
        this(0.0);
    }

    /**
     * Creates a new crosshair with the specified value.
     *
     * @param value  the value.
     */
    public Crosshair(double value) {
       this(value, Color.BLACK, new BasicStroke(1.0f));
    }

    /**
     * Creates a new crosshair value with the specified value and line style.
     *
     * @param value  the value.
     * @param paint  the line paint ({@code null} not permitted).
     * @param stroke  the line stroke ({@code null} not permitted).
     */
    public Crosshair(double value, Paint paint, Stroke stroke) {
        Args.nullNotPermitted(paint, "paint");
        Args.nullNotPermitted(stroke, "stroke");
        this.visible = true;
        this.value = value;
        this.paint = paint;
        this.stroke = stroke;
        this.labelVisible = false;
        this.labelGenerator = new StandardCrosshairLabelGenerator();
        this.labelAnchor = RectangleAnchor.BOTTOM_LEFT;
        this.labelXOffset = 5.0;
        this.labelYOffset = 5.0;
        this.labelPadding = RectangleInsets.ZERO_INSETS;
        this.labelFont = new Font("Tahoma", Font.PLAIN, 12);
        this.labelPaint = Color.BLACK;
        this.labelBackgroundPaint = new Color(0, 0, 255, 63);
        this.labelOutlineVisible = true;
        this.labelOutlinePaint = Color.BLACK;
        this.labelOutlineStroke = new BasicStroke(0.5f);
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Returns the flag that indicates whether the crosshair is
     * currently visible.
     *
     * @return A boolean.
     *
     * @see #setVisible(boolean)
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Sets the flag that controls the visibility of the crosshair and sends
     * a proerty change event (with the name 'visible') to all registered
     * listeners.
     *
     * @param visible  the new flag value.
     *
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        boolean old = this.visible;
        this.visible = visible;
        this.pcs.firePropertyChange("visible", old, visible);
    }

    /**
     * Returns the crosshair value.
     *
     * @return The crosshair value.
     *
     * @see #setValue(double)
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the crosshair value and sends a property change event with the name
     * 'value' to all registered listeners.
     *
     * @param value  the value.
     *
     * @see #getValue()
     */
    public void setValue(double value) {
        Double oldValue = this.value;
        this.value = value;
        this.pcs.firePropertyChange("value", oldValue, value);
    }

    /**
     * Returns the paint for the crosshair line.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setPaint(java.awt.Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint for the crosshair line and sends a property change event
     * with the name "paint" to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        Paint old = this.paint;
        this.paint = paint;
        this.pcs.firePropertyChange("paint", old, paint);
    }

    /**
     * Returns the stroke for the crosshair line.
     *
     * @return The stroke (never {@code null}).
     *
     * @see #setStroke(java.awt.Stroke)
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke for the crosshair line and sends a property change event
     * with the name "stroke" to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getStroke()
     */
    public void setStroke(Stroke stroke) {
        Stroke old = this.stroke;
        this.stroke = stroke;
        this.pcs.firePropertyChange("stroke", old, stroke);
    }

    /**
     * Returns the flag that controls whether a label is drawn for
     * this crosshair.
     *
     * @return A boolean.
     *
     * @see #setLabelVisible(boolean)
     */
    public boolean isLabelVisible() {
        return this.labelVisible;
    }

    /**
     * Sets the flag that controls whether a label is drawn for the
     * crosshair and sends a property change event (with the name
     * 'labelVisible') to all registered listeners.
     *
     * @param visible  the new flag value.
     *
     * @see #isLabelVisible()
     */
    public void setLabelVisible(boolean visible) {
        boolean old = this.labelVisible;
        this.labelVisible = visible;
        this.pcs.firePropertyChange("labelVisible", old, visible);
    }

    /**
     * Returns the crosshair label generator.
     *
     * @return The label crosshair generator (never {@code null}).
     *
     * @see #setLabelGenerator(org.jfree.chart.labels.CrosshairLabelGenerator)
     */
    public CrosshairLabelGenerator getLabelGenerator() {
        return this.labelGenerator;
    }

    /**
     * Sets the crosshair label generator and sends a property change event
     * (with the name 'labelGenerator') to all registered listeners.
     *
     * @param generator  the new generator ({@code null} not permitted).
     *
     * @see #getLabelGenerator()
     */
    public void setLabelGenerator(CrosshairLabelGenerator generator) {
        Args.nullNotPermitted(generator, "generator");
        CrosshairLabelGenerator old = this.labelGenerator;
        this.labelGenerator = generator;
        this.pcs.firePropertyChange("labelGenerator", old, generator);
    }

    /**
     * Returns the label anchor point.
     *
     * @return the label anchor point (never {@code null}).
     *
     * @see #setLabelAnchor(org.jfree.chart.ui.RectangleAnchor)
     */
    public RectangleAnchor getLabelAnchor() {
        return this.labelAnchor;
    }

    /**
     * Sets the label anchor point and sends a property change event (with the
     * name 'labelAnchor') to all registered listeners.
     *
     * @param anchor  the anchor ({@code null} not permitted).
     *
     * @see #getLabelAnchor()
     */
    public void setLabelAnchor(RectangleAnchor anchor) {
        RectangleAnchor old = this.labelAnchor;
        this.labelAnchor = anchor;
        this.pcs.firePropertyChange("labelAnchor", old, anchor);
    }

    /**
     * Returns the x-offset for the label (in Java2D units).
     *
     * @return The x-offset.
     *
     * @see #setLabelXOffset(double)
     */
    public double getLabelXOffset() {
        return this.labelXOffset;
    }

    /**
     * Sets the x-offset and sends a property change event (with the name
     * 'labelXOffset') to all registered listeners.
     *
     * @param offset  the new offset.
     *
     * @see #getLabelXOffset()
     */
    public void setLabelXOffset(double offset) {
        Double old = this.labelXOffset;
        this.labelXOffset = offset;
        this.pcs.firePropertyChange("labelXOffset", old, offset);
    }

    /**
     * Returns the y-offset for the label (in Java2D units).
     *
     * @return The y-offset.
     *
     * @see #setLabelYOffset(double)
     */
    public double getLabelYOffset() {
        return this.labelYOffset;
    }

    /**
     * Sets the y-offset and sends a property change event (with the name
     * 'labelYOffset') to all registered listeners.
     *
     * @param offset  the new offset.
     *
     * @see #getLabelYOffset()
     */
    public void setLabelYOffset(double offset) {
        Double old = this.labelYOffset;
        this.labelYOffset = offset;
        this.pcs.firePropertyChange("labelYOffset", old, offset);
    }

    /**
     * Returns the label padding.
     *
     * @return The label padding (never {@code null}).
     * @since 1.5.6
     */
    public RectangleInsets getLabelPadding() {
        return labelPadding;
    }

    /**
     * Sets the label padding and sends a property change event (with the name
     * 'labelPadding') to all registered listeners.
     *
     * @param padding the padding ({@code null} not permitted).
     * @since 1.5.6
     */
    public void setLabelPadding(RectangleInsets padding) {
        Args.nullNotPermitted(padding, "padding");
        RectangleInsets old = this.labelPadding;
        this.labelPadding = padding;
        this.pcs.firePropertyChange("labelPadding", old, padding);
    }

    /**
     * Returns the label font.
     *
     * @return The label font (never {@code null}).
     *
     * @see #setLabelFont(java.awt.Font)
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Sets the label font and sends a property change event (with the name
     * 'labelFont') to all registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getLabelFont()
     */
    public void setLabelFont(Font font) {
        Args.nullNotPermitted(font, "font");
        Font old = this.labelFont;
        this.labelFont = font;
        this.pcs.firePropertyChange("labelFont", old, font);
    }

    /**
     * Returns the label paint.  The default value is {@code Color.BLACK}.
     *
     * @return The label paint (never {@code null}).
     *
     * @see #setLabelPaint
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Sets the label paint and sends a property change event (with the name
     * 'labelPaint') to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLabelPaint()
     */
    public void setLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        Paint old = this.labelPaint;
        this.labelPaint = paint;
        this.pcs.firePropertyChange("labelPaint", old, paint);
    }

    /**
     * Returns the label background paint.
     *
     * @return The label background paint (possibly {@code null}).
     *
     * @see #setLabelBackgroundPaint(java.awt.Paint)
     */
    public Paint getLabelBackgroundPaint() {
        return this.labelBackgroundPaint;
    }

    /**
     * Sets the label background paint and sends a property change event with
     * the name 'labelBackgroundPaint') to all registered listeners.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getLabelBackgroundPaint()
     */
    public void setLabelBackgroundPaint(Paint paint) {
        Paint old = this.labelBackgroundPaint;
        this.labelBackgroundPaint = paint;
        this.pcs.firePropertyChange("labelBackgroundPaint", old, paint);
    }

    /**
     * Returns the flag that controls the visibility of the label outline.
     * The default value is {@code true}.
     *
     * @return A boolean.
     *
     * @see #setLabelOutlineVisible(boolean)
     */
    public boolean isLabelOutlineVisible() {
        return this.labelOutlineVisible;
    }

    /**
     * Sets the flag that controls the visibility of the label outlines and
     * sends a property change event (with the name "labelOutlineVisible") to
     * all registered listeners.
     *
     * @param visible  the new flag value.
     *
     * @see #isLabelOutlineVisible()
     */
    public void setLabelOutlineVisible(boolean visible) {
        boolean old = this.labelOutlineVisible;
        this.labelOutlineVisible = visible;
        this.pcs.firePropertyChange("labelOutlineVisible", old, visible);
    }

    /**
     * Returns the label outline paint.
     *
     * @return The label outline paint (never {@code null}).
     *
     * @see #setLabelOutlinePaint(java.awt.Paint)
     */
    public Paint getLabelOutlinePaint() {
        return this.labelOutlinePaint;
    }

    /**
     * Sets the label outline paint and sends a property change event (with the
     * name "labelOutlinePaint") to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLabelOutlinePaint()
     */
    public void setLabelOutlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        Paint old = this.labelOutlinePaint;
        this.labelOutlinePaint = paint;
        this.pcs.firePropertyChange("labelOutlinePaint", old, paint);
    }

    /**
     * Returns the label outline stroke. The default value is 
     * {@code BasicStroke(0.5)}.
     *
     * @return The label outline stroke (never {@code null}).
     *
     * @see #setLabelOutlineStroke(java.awt.Stroke)
     */
    public Stroke getLabelOutlineStroke() {
        return this.labelOutlineStroke;
    }

    /**
     * Sets the label outline stroke and sends a property change event (with
     * the name 'labelOutlineStroke') to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getLabelOutlineStroke()
     */
    public void setLabelOutlineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        Stroke old = this.labelOutlineStroke;
        this.labelOutlineStroke = stroke;
        this.pcs.firePropertyChange("labelOutlineStroke", old, stroke);
    }

    /**
     * Tests this crosshair for equality with an arbitrary object.
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
        if (!(obj instanceof Crosshair)) {
            return false;
        }
        Crosshair that = (Crosshair) obj;
        if (this.visible != that.visible) {
            return false;
        }
        if (Double.compare(this.value, that.value) != 0) {
            return false;
        }
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (!this.stroke.equals(that.stroke)) {
            return false;
        }
        if (this.labelVisible != that.labelVisible) {
            return false;
        }
        if (!this.labelGenerator.equals(that.labelGenerator)) {
            return false;
        }
        if (!this.labelAnchor.equals(that.labelAnchor)) {
            return false;
        }
        if (Double.compare(this.labelXOffset, that.labelXOffset) != 0) {
            return false;
        }
        if (Double.compare(this.labelYOffset, that.labelYOffset) != 0) {
            return false;
        }
        if (!this.labelPadding.equals(that.labelPadding)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelPaint, that.labelPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelBackgroundPaint,
                that.labelBackgroundPaint)) {
            return false;
        }
        if (this.labelOutlineVisible != that.labelOutlineVisible) {
            return false;
        }
        if (!PaintUtils.equal(this.labelOutlinePaint,
                that.labelOutlinePaint)) {
            return false;
        }
        if (!this.labelOutlineStroke.equals(that.labelOutlineStroke)) {
            return false;
        }
        return true;  // can't find any difference
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = HashUtils.hashCode(hash, this.visible);
        hash = HashUtils.hashCode(hash, this.value);
        hash = HashUtils.hashCode(hash, this.paint);
        hash = HashUtils.hashCode(hash, this.stroke);
        hash = HashUtils.hashCode(hash, this.labelVisible);
        hash = HashUtils.hashCode(hash, this.labelAnchor);
        hash = HashUtils.hashCode(hash, this.labelGenerator);
        hash = HashUtils.hashCode(hash, this.labelXOffset);
        hash = HashUtils.hashCode(hash, this.labelYOffset);
        hash = HashUtils.hashCode(hash, this.labelPadding);
        hash = HashUtils.hashCode(hash, this.labelFont);
        hash = HashUtils.hashCode(hash, this.labelPaint);
        hash = HashUtils.hashCode(hash, this.labelBackgroundPaint);
        hash = HashUtils.hashCode(hash, this.labelOutlineVisible);
        hash = HashUtils.hashCode(hash, this.labelOutlineStroke);
        hash = HashUtils.hashCode(hash, this.labelOutlinePaint);
        return hash;
    }

    /**
     * Returns an independent copy of this instance.
     *
     * @return An independent copy of this instance.
     *
     * @throws java.lang.CloneNotSupportedException if there is a problem with
     *         cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // FIXME: clone generator
        return super.clone();
    }

    /**
     * Adds a property change listener.
     *
     * @param l  the listener.
     *
     * @see #removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }

    /**
     * Removes a property change listener.
     *
     * @param l  the listener.
     *
     * @see #addPropertyChangeListener(java.beans.PropertyChangeListener) 
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.pcs.removePropertyChangeListener(l);
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
        SerialUtils.writePaint(this.paint, stream);
        SerialUtils.writeStroke(this.stroke, stream);
        SerialUtils.writePaint(this.labelPaint, stream);
        SerialUtils.writePaint(this.labelBackgroundPaint, stream);
        SerialUtils.writeStroke(this.labelOutlineStroke, stream);
        SerialUtils.writePaint(this.labelOutlinePaint, stream);
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
        this.labelPaint = SerialUtils.readPaint(stream);
        this.labelBackgroundPaint = SerialUtils.readPaint(stream);
        this.labelOutlineStroke = SerialUtils.readStroke(stream);
        this.labelOutlinePaint = SerialUtils.readPaint(stream);
        this.pcs = new PropertyChangeSupport(this);
    }

}
