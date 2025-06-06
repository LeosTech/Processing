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
 * --------------------
 * CompassPlotTest.java
 * --------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;
import org.jfree.chart.needle.PointerNeedle;
import org.jfree.data.general.DefaultValueDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CompassPlot} class.
 */
public class CompassPlotTest {

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        CompassPlot plot1 = new CompassPlot();
        CompassPlot plot2 = new CompassPlot();
        assertEquals(plot1, plot2);

        // labelType...
        plot1.setLabelType(CompassPlot.VALUE_LABELS);
        assertNotEquals(plot1, plot2);
        plot2.setLabelType(CompassPlot.VALUE_LABELS);
        assertEquals(plot1, plot2);

        // labelFont
        plot1.setLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertNotEquals(plot1, plot2);
        plot2.setLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertEquals(plot1, plot2);

        // drawBorder
        plot1.setDrawBorder(true);
        assertNotEquals(plot1, plot2);
        plot2.setDrawBorder(true);
        assertEquals(plot1, plot2);

        // rosePaint
        plot1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        // roseCenterPaint
        plot1.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        // roseHighlightPaint
        plot1.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CompassPlot p1 = new CompassPlot(null);
        p1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));
        p1.setRoseCenterPaint(new GradientPaint(4.0f, 3.0f, Color.RED, 2.0f,
                1.0f, Color.GREEN));
        p1.setRoseHighlightPaint(new GradientPaint(4.0f, 3.0f, Color.RED, 2.0f,
                1.0f, Color.GREEN));
        CompassPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CompassPlot p1 = new CompassPlot(new DefaultValueDataset(15.0));
        CompassPlot p2 = (CompassPlot) p1.clone();
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Test faulty array bounds; CVE-2024-23077.
     */
    @Test
    public void testArrayBounds() {
        CompassPlot p = new CompassPlot(new DefaultValueDataset(0));
        p.setSeriesNeedle(-1, new PointerNeedle());
    }

}
