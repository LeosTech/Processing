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
 * DefaultOHLCDatasetTest.java
 * ---------------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.util.Date;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultOHLCDataset} class.
 */
public class DefaultOHLCDatasetTest {

    private static final double EPSILON = 0.0000000001;

    /**
     * A small test for the data range calculated on this dataset.
     */
    @Test
    public void testDataRange() {
        OHLCDataItem[] data = new OHLCDataItem[3];
        data[0] = new OHLCDataItem(new Date(11L), 2.0, 4.0, 1.0, 3.0, 100.0);
        data[1] = new OHLCDataItem(new Date(22L), 4.0, 9.0, 2.0, 5.0, 120.0);
        data[2] = new OHLCDataItem(new Date(33L), 3.0, 7.0, 3.0, 6.0, 140.0);
        DefaultOHLCDataset d = new DefaultOHLCDataset("S1", data);
        Range r = DatasetUtils.findRangeBounds(d, true);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(9.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultOHLCDataset d1 = new DefaultOHLCDataset("Series 1",
                new OHLCDataItem[0]);
        DefaultOHLCDataset d2 = new DefaultOHLCDataset("Series 1",
                new OHLCDataItem[0]);
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        d1 = new DefaultOHLCDataset("Series 2", new OHLCDataItem[0]);
        assertNotEquals(d1, d2);
        d2 = new DefaultOHLCDataset("Series 2", new OHLCDataItem[0]);
        assertEquals(d1, d2);

        d1 = new DefaultOHLCDataset("Series 2", new OHLCDataItem[] {
                new OHLCDataItem(new Date(123L), 1.2, 3.4, 5.6, 7.8, 99.9)});
        assertNotEquals(d1, d2);
        d2 = new DefaultOHLCDataset("Series 2", new OHLCDataItem[] {
                new OHLCDataItem(new Date(123L), 1.2, 3.4, 5.6, 7.8, 99.9)});
        assertEquals(d1, d2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultOHLCDataset d1 = new DefaultOHLCDataset("Series 1",
                new OHLCDataItem[0]);
        DefaultOHLCDataset d2 = (DefaultOHLCDataset) d1.clone();
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        OHLCDataItem item1 = new OHLCDataItem(new Date(1L), 1.0, 2.0, 3.0, 4.0,
                5.0);
        OHLCDataItem item2 = new OHLCDataItem(new Date(2L), 6.0, 7.0, 8.0, 9.0,
                10.0);
        // create an array of items in reverse order
        OHLCDataItem[] items = new OHLCDataItem[] {item2, item1};
        DefaultOHLCDataset d1 = new DefaultOHLCDataset("Series 1", items);
        DefaultOHLCDataset d2 = (DefaultOHLCDataset) d1.clone();
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        d1.sortDataByDate();
        assertNotEquals(d1, d2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        DefaultOHLCDataset d1 = new DefaultOHLCDataset("Series 1",
                new OHLCDataItem[0]);
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultOHLCDataset d1 = new DefaultOHLCDataset("Series 1",
                new OHLCDataItem[0]);
        DefaultOHLCDataset d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

}
