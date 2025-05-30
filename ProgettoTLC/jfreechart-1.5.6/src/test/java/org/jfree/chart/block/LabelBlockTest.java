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
 * -------------------
 * LabelBlockTest.java
 * -------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.jfree.chart.TestUtils.createFont;
import static org.jfree.chart.TestUtils.createR2D;
import static org.junit.jupiter.api.Assertions.*;

import org.jfree.chart.TestUtils;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;

import org.junit.jupiter.api.Test;

/**
 * Some tests for the {@link LabelBlock} class.
 */
public class LabelBlockTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(LabelBlock.class)
            .withPrefabValues(Rectangle2D.class, createR2D(true), createR2D(false))
            .withPrefabValues(Font.class, createFont(true), createFont(false))
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .suppress(Warning.TRANSIENT_FIELDS)
            .withRedefinedSuperclass()
            .verify();
    }

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        LabelBlock b2 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        assertEquals(b1, b2);
        assertEquals(b2, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.PLAIN, 12),
                Color.RED);
        assertNotEquals(b1, b2);
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.PLAIN, 12),
                Color.RED);
        assertEquals(b1, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.RED);
        assertNotEquals(b1, b2);
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.RED);
        assertEquals(b1, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.BLUE);
        assertNotEquals(b1, b2);
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.BLUE);
        assertEquals(b1, b2);

        b1.setToolTipText("Tooltip");
        assertNotEquals(b1, b2);
        b2.setToolTipText("Tooltip");
        assertEquals(b1, b2);

        b1.setURLText("URL");
        assertNotEquals(b1, b2);
        b2.setURLText("URL");
        assertEquals(b1, b2);

        b1.setContentAlignmentPoint(TextBlockAnchor.CENTER_RIGHT);
        assertNotEquals(b1, b2);
        b2.setContentAlignmentPoint(TextBlockAnchor.CENTER_RIGHT);
        assertEquals(b1, b2);

        b1.setTextAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertNotEquals(b1, b2);
        b2.setTextAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertEquals(b1, b2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        LabelBlock b2 = (LabelBlock) b1.clone();
        assertNotSame(b1, b2);
        assertSame(b1.getClass(), b2.getClass());
        assertEquals(b1, b2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        GradientPaint gp = new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE);
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), gp);
        LabelBlock b2 = TestUtils.serialised(b1);
        assertEquals(b1, b2);
    }

}
