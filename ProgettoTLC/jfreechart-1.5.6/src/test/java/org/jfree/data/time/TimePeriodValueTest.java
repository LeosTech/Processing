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
 * ------------------------
 * TimePeriodValueTest.java
 * ------------------------
 * (C) Copyright 2003-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TimePeriodValue} class.
 */
public class TimePeriodValueTest {

    /**
     * Test that an instance is equal to itself.
     */
    @Test
    public void testEqualsSelf() {
        TimePeriodValue tpv = new TimePeriodValue(new Day(), 55.75);
        assertEquals(tpv, tpv);
    }

    /**
     * Tests the equals() method.
     */
    @Test
    public void testEquals() {
        TimePeriodValue tpv1 = new TimePeriodValue(new Day(30, 7, 2003), 55.75);
        TimePeriodValue tpv2 = new TimePeriodValue(new Day(30, 7, 2003), 55.75);
        assertEquals(tpv1, tpv2);
        assertEquals(tpv2, tpv1);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TimePeriodValue tpv1 = new TimePeriodValue(new Day(30, 7, 2003), 55.75);
        TimePeriodValue tpv2 = TestUtils.serialised(tpv1);
        assertEquals(tpv1, tpv2);
    }

}
