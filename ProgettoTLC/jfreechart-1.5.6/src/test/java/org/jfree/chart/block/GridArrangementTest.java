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
 * GridArrangementTest.java
 * ------------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.block;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.Size2D;

import org.jfree.data.Range;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link GridArrangement} class.
 */
public class GridArrangementTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(GridArrangement.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .suppress(Warning.TRANSIENT_FIELDS)
            .verify();
    }

    /**
     * Immutable - cloning is not necessary.
     */
    @Test
    public void testCloning() {
        GridArrangement f1 = new GridArrangement(1, 2);
        assertFalse(f1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        GridArrangement f1 = new GridArrangement(33, 44);
        GridArrangement f2 = TestUtils.serialised(f1);
        assertEquals(f1, f2);
    }

    private static final double EPSILON = 0.000000001;

    /**
     * Test arrangement with no constraints.
     */
    @Test
    public void testNN() {
        BlockContainer c = createTestContainer1();
        Size2D s = c.arrange(null, RectangleConstraint.NONE);
        assertEquals(90.0, s.width, EPSILON);
        assertEquals(33.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a fixed width and no height constraint.
     */
    @Test
    public void testFN() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = new RectangleConstraint(100.0, null,
                LengthConstraintType.FIXED, 0.0, null,
                LengthConstraintType.NONE);
        Size2D s = c.arrange(null, constraint);
        assertEquals(100.0, s.width, EPSILON);
        assertEquals(33.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a fixed height and no width constraint.
     */
    @Test
    public void testNF() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = RectangleConstraint.NONE.toFixedHeight(
                100.0);
        Size2D s = c.arrange(null, constraint);
        assertEquals(90.0, s.width, EPSILON);
        assertEquals(100.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a range for the width and a fixed height.
     */
    @Test
    public void testRF() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = new RectangleConstraint(new Range(40.0,
                60.0), 100.0);
        Size2D s = c.arrange(null, constraint);
        assertEquals(60.0, s.width, EPSILON);
        assertEquals(100.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a range for the width and height.
     */
    @Test
    public void testRR() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = new RectangleConstraint(new Range(40.0,
                60.0), new Range(50.0, 70.0));
        Size2D s = c.arrange(null, constraint);
        assertEquals(60.0, s.width, EPSILON);
        assertEquals(50.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a range for the width and no height constraint.
     */
    @Test
    public void testRN() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = RectangleConstraint.NONE.toRangeWidth(
                new Range(40.0, 60.0));
        Size2D s = c.arrange(null, constraint);
        assertEquals(60.0, s.width, EPSILON);
        assertEquals(33.0, s.height, EPSILON);
    }

    /**
     * Test arrangement with a range for the height and no width constraint.
     */
    @Test
    public void testNR() {
        BlockContainer c = createTestContainer1();
        RectangleConstraint constraint = RectangleConstraint.NONE.toRangeHeight(
                new Range(40.0, 60.0));
        Size2D s = c.arrange(null, constraint);
        assertEquals(90.0, s.width, EPSILON);
        assertEquals(40.0, s.height, EPSILON);
    }

    private BlockContainer createTestContainer1() {
        Block b1 = new EmptyBlock(10, 11);
        Block b2 = new EmptyBlock(20, 22);
        Block b3 = new EmptyBlock(30, 33);
        BlockContainer result = new BlockContainer(new GridArrangement(1, 3));
        result.add(b1);
        result.add(b2);
        result.add(b3);
        return result;
    }

    /**
     * The arrangement should be able to handle null blocks in the layout.
     */
    @Test
    public void testNullBlock_FF() {
        BlockContainer c = new BlockContainer(new GridArrangement(1, 1));
        c.add(null);
        Size2D s = c.arrange(null, new RectangleConstraint(20, 10));
        assertEquals(20.0, s.getWidth(), EPSILON);
        assertEquals(10.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle null blocks in the layout.
     */
    @Test
    public void testNullBlock_FN() {
        BlockContainer c = new BlockContainer(new GridArrangement(1, 1));
        c.add(null);
        Size2D s = c.arrange(null, RectangleConstraint.NONE.toFixedWidth(10));
        assertEquals(10.0, s.getWidth(), EPSILON);
        assertEquals(0.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle null blocks in the layout.
     */
    @Test
    public void testNullBlock_FR() {
        BlockContainer c = new BlockContainer(new GridArrangement(1, 1));
        c.add(null);
        Size2D s = c.arrange(null, new RectangleConstraint(30.0, new Range(5.0,
                10.0)));
        assertEquals(30.0, s.getWidth(), EPSILON);
        assertEquals(5.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle null blocks in the layout.
     */
    @Test
    public void testNullBlock_NN() {
        BlockContainer c = new BlockContainer(new GridArrangement(1, 1));
        c.add(null);
        Size2D s = c.arrange(null, RectangleConstraint.NONE);
        assertEquals(0.0, s.getWidth(), EPSILON);
        assertEquals(0.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle less blocks than grid spaces.
     */
    @Test
    public void testGridNotFull_FF() {
        Block b1 = new EmptyBlock(5, 5);
        BlockContainer c = new BlockContainer(new GridArrangement(2, 3));
        c.add(b1);
        Size2D s = c.arrange(null, new RectangleConstraint(200, 100));
        assertEquals(200.0, s.getWidth(), EPSILON);
        assertEquals(100.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle less blocks than grid spaces.
     */
    @Test
    public void testGridNotFull_FN() {
        Block b1 = new EmptyBlock(5, 5);
        BlockContainer c = new BlockContainer(new GridArrangement(2, 3));
        c.add(b1);
        Size2D s = c.arrange(null, RectangleConstraint.NONE.toFixedWidth(30.0));
        assertEquals(30.0, s.getWidth(), EPSILON);
        assertEquals(10.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle less blocks than grid spaces.
     */
    @Test
    public void testGridNotFull_FR() {
        Block b1 = new EmptyBlock(5, 5);
        BlockContainer c = new BlockContainer(new GridArrangement(2, 3));
        c.add(b1);
        Size2D s = c.arrange(null, new RectangleConstraint(30.0, new Range(5.0,
                10.0)));
        assertEquals(30.0, s.getWidth(), EPSILON);
        assertEquals(10.0, s.getHeight(), EPSILON);
    }

    /**
     * The arrangement should be able to handle less blocks than grid spaces.
     */
    @Test
    public void testGridNotFull_NN() {
        Block b1 = new EmptyBlock(5, 5);
        BlockContainer c = new BlockContainer(new GridArrangement(2, 3));
        c.add(b1);
        Size2D s = c.arrange(null, RectangleConstraint.NONE);
        assertEquals(15.0, s.getWidth(), EPSILON);
        assertEquals(10.0, s.getHeight(), EPSILON);
    }

}
