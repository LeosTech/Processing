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
 * ----------------------
 * YIntervalDataItem.java
 * ----------------------
 * (C) Copyright 2006-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.data.ComparableObjectItem;

/**
 * An item representing data in the form (x, y, y-low, y-high).
 */
public class YIntervalDataItem extends ComparableObjectItem {

    /**
     * Creates a new instance of {@code YIntervalDataItem}.
     *
     * @param x  the x-value.
     * @param y  the y-value.
     * @param yLow  the lower bound of the y-interval.
     * @param yHigh  the upper bound of the y-interval.
     */
    public YIntervalDataItem(double x, double y, double yLow, double yHigh) {
        super(x, new YInterval(y, yLow, yHigh));
    }

    /**
     * Returns the x-value.
     *
     * @return The x-value (never {@code null}).
     */
    public Double getX() {
        return (Double) getComparable();
    }

    /**
     * Returns the y-value.
     *
     * @return The y-value.
     */
    public double getYValue() {
        YInterval interval = (YInterval) getObject();
        if (interval != null) {
            return interval.getY();
        }
        else {
            return Double.NaN;
        }
    }

    /**
     * Returns the lower bound of the y-interval.
     *
     * @return The lower bound of the y-interval.
     */
    public double getYLowValue() {
        YInterval interval = (YInterval) getObject();
        if (interval != null) {
            return interval.getYLow();
        }
        else {
            return Double.NaN;
        }
    }

    /**
     * Returns the upper bound of the y-interval.
     *
     * @return The upper bound of the y-interval.
     */
    public double getYHighValue() {
        YInterval interval = (YInterval) getObject();
        if (interval != null) {
            return interval.getYHigh();
        }
        else {
            return Double.NaN;
        }
    }

}
