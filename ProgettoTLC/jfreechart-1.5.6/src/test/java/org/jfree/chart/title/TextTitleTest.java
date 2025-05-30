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
 * ------------------
 * TextTitleTest.java
 * ------------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.util.PaintUtils;

import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link TextTitle} class.
 */
public class TextTitleTest {
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(TextTitle.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withRedefinedSuperclass()
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withPrefabValues(Rectangle2D.class,
                                  TestUtils.createR2D(true),
                                  TestUtils.createR2D(false))
                .withPrefabValues(Font.class,
                                  TestUtils.createFont(true),
                                  TestUtils.createFont(false))
                .verify();
    }
    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        TextTitle t1 = new TextTitle();
        TextTitle t2 = new TextTitle();
        assertEquals(t1, t2);

        t1.setText("Test 1");
        assertNotEquals(t1, t2);
        t2.setText("Test 1");
        assertEquals(t1, t2);

        Font f = new Font("SansSerif", Font.PLAIN, 15);
        t1.setFont(f);
        assertNotEquals(t1, t2);
        t2.setFont(f);
        assertEquals(t1, t2);

        t1.setTextAlignment(HorizontalAlignment.RIGHT);
        assertNotEquals(t1, t2);
        t2.setTextAlignment(HorizontalAlignment.RIGHT);
        assertEquals(t1, t2);

        // paint
        t1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertFalse(PaintUtils.equal(t1.getPaint(), t2.getPaint()));
        t2.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertTrue(PaintUtils.equal(t1.getPaint(), t2.getPaint()));

        // backgroundPaint
        t1.setBackgroundPaint(new GradientPaint(4.0f, 3.0f, Color.RED,
                2.0f, 1.0f, Color.BLUE));
        assertFalse(PaintUtils.equal(t1.getBackgroundPaint(), t2.getBackgroundPaint()));
        t2.setBackgroundPaint(new GradientPaint(4.0f, 3.0f, Color.RED,
                2.0f, 1.0f, Color.BLUE));
        assertTrue(PaintUtils.equal(t1.getBackgroundPaint(), t2.getBackgroundPaint()));

        // maximumLinesToDisplay
        t1.setMaximumLinesToDisplay(3);
        assertNotEquals(t1, t2);
        t2.setMaximumLinesToDisplay(3);
        assertEquals(t1, t2);

        // toolTipText
        t1.setToolTipText("TTT");
        assertNotEquals(t1, t2);
        t2.setToolTipText("TTT");
        assertEquals(t1, t2);

        // urlText
        t1.setURLText(("URL"));
        assertNotEquals(t1, t2);
        t2.setURLText(("URL"));
        assertEquals(t1, t2);

        // expandToFitSpace
        t1.setExpandToFitSpace(!t1.getExpandToFitSpace());
        assertNotEquals(t1, t2);
        t2.setExpandToFitSpace(!t2.getExpandToFitSpace());
        assertEquals(t1, t2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        TextTitle t1 = new TextTitle();
        TextTitle t2 = new TextTitle();
        assertEquals(t1, t2);
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TextTitle t1 = new TextTitle();
        TextTitle t2 = (TextTitle) t1.clone();
        assertNotSame(t1, t2);
        assertSame(t1.getClass(), t2.getClass());
        assertEquals(t1, t2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TextTitle t1 = new TextTitle("Test");
        TextTitle t2 = TestUtils.serialised(t1);
        assertEquals(t1, t2);
    }

}
