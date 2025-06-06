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
 * ---------------
 * FlowEntity.java
 * ---------------
 * (C) Copyright 2021-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.util.Objects;
import org.jfree.chart.plot.flow.FlowPlot;
import org.jfree.chart.util.Args;
import org.jfree.data.flow.FlowKey;

/**
 * A chart entity representing the flow between two nodes in a {@link FlowPlot}.
 * 
 * @since 1.5.3
 */
public class FlowEntity extends ChartEntity {

    /** The key. */
    private final FlowKey key;
    
    /**
     * Creates a new instance.
     * 
     * @param key  the key identifying the flow ({@code null} not permitted).
     * @param area  the outline of the entity ({@code null} not permitted).
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text.
     */
    public FlowEntity(FlowKey key, Shape area, String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
        Args.nullNotPermitted(key, "key");
        this.key = key;
    }
    
    /**
     * Returns the key identifying the flow.
     * 
     * @return The flow key (never {@code null}). 
     */
    public FlowKey getKey() {
        return this.key;
    }

    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "[FlowEntity: " + this.key + "]";
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlowEntity)) {
            return false;
        }
        FlowEntity that = (FlowEntity) obj;
        if (!java.util.Objects.equals(this.key, that.key)) {
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
        return (other instanceof FlowEntity);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 79 * hash + Objects.hashCode(this.key);
        return hash;
    }

}
