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
 * CategoryLabelPositionsTest.java
 * -------------------------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.axis;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryLabelPositions} class.
 */
public class CategoryLabelPositionsTest {

    private static final RectangleAnchor RA_TOP = RectangleAnchor.TOP;
    private static final RectangleAnchor RA_BOTTOM = RectangleAnchor.BOTTOM;

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(CategoryLabelPositions.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .verify();
    }

    /**
     * Check that the equals method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        CategoryLabelPositions p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        CategoryLabelPositions p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertEquals(p1, p2);

        p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertNotEquals(p1, p2);
        p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertEquals(p1, p2);

        p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM, TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertNotEquals(p1, p2);
        p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertEquals(p1, p2);

        p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertNotEquals(p1, p2);
        p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertEquals(p1, p2);

        p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER));
        assertNotEquals(p1, p2);
        p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER),
                new CategoryLabelPosition(RA_BOTTOM,
                        TextBlockAnchor.TOP_CENTER));
        assertEquals(p1, p2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        CategoryLabelPositions p1 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        CategoryLabelPositions p2 = new CategoryLabelPositions(
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER),
                new CategoryLabelPosition(RA_TOP, TextBlockAnchor.CENTER));
        assertEquals(p1, p2);
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryLabelPositions p1 = CategoryLabelPositions.STANDARD;
        CategoryLabelPositions p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

}
