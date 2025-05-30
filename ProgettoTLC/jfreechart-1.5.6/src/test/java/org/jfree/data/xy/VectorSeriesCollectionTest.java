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
 * -------------------------------
 * VectorSeriesCollectionTest.java
 * -------------------------------
 * (C) Copyright 2007-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link VectorSeriesCollection} class.
 */
public class VectorSeriesCollectionTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeries s2 = new VectorSeries("Series");
        s2.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c2 = new VectorSeriesCollection();
        c2.addSeries(s2);
        assertEquals(c1, c2);
        assertEquals(c2, c1);

        c1.addSeries(new VectorSeries("Empty Series"));
        assertNotEquals(c1, c2);

        c2.addSeries(new VectorSeries("Empty Series"));
        assertEquals(c1, c2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeriesCollection c2 = (VectorSeriesCollection) c1.clone();
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        s1.setDescription("XYZ");
        assertNotEquals(c1, c2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        VectorSeriesCollection d1 = new VectorSeriesCollection();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeriesCollection c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * Some checks for the removeSeries() method.
     */
    @Test
    public void testRemoveSeries() {
        VectorSeries s1 = new VectorSeries("S1");
        VectorSeries s2 = new VectorSeries("S2");
        VectorSeriesCollection vsc = new VectorSeriesCollection();
        vsc.addSeries(s1);
        vsc.addSeries(s2);
        assertEquals(2, vsc.getSeriesCount());
        boolean b = vsc.removeSeries(s1);
        assertTrue(b);
        assertEquals(1, vsc.getSeriesCount());
        assertEquals("S2", vsc.getSeriesKey(0));
        b = vsc.removeSeries(new VectorSeries("NotInDataset"));
        assertFalse(b);
        assertEquals(1, vsc.getSeriesCount());
        b = vsc.removeSeries(s2);
        assertTrue(b);
        assertEquals(0, vsc.getSeriesCount());
    }

}
