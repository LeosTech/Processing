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
 * -----------------------
 * KeyToGroupMapTests.java
 * -----------------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link KeyToGroupMap} class.
 */
public class KeyToGroupMapTest {

    /**
     * Tests the mapKeyToGroup() method.
     */
    @Test
    public void testMapKeyToGroup() {
        KeyToGroupMap m1 = new KeyToGroupMap("G1");

        // map a key to the default group
        m1.mapKeyToGroup("K1", "G1");
        assertEquals("G1", m1.getGroup("K1"));

        // map a key to a new group
        m1.mapKeyToGroup("K2", "G2");
        assertEquals("G2", m1.getGroup("K2"));

        // clear a mapping
        m1.mapKeyToGroup("K2", null);
        assertEquals("G1", m1.getGroup("K2"));  // after clearing, reverts to
                                                // default group

        // check handling of null key
        boolean pass = false;
        try {
            m1.mapKeyToGroup(null, "G1");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Tests that the getGroupCount() method returns the correct values under
     * various circumstances.
     */
    @Test
    public void testGroupCount() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a new map always has 1 group (the default group)
        assertEquals(1, m1.getGroupCount());

        // if the default group is not mapped to, it should still count towards
        // the group count...
        m1.mapKeyToGroup("C1", "G1");
        assertEquals(2, m1.getGroupCount());

        // now when the default group is mapped to, it shouldn't increase the
        // group count...
        m1.mapKeyToGroup("C2", "Default Group");
        assertEquals(2, m1.getGroupCount());

        // complicate things a little...
        m1.mapKeyToGroup("C3", "Default Group");
        m1.mapKeyToGroup("C4", "G2");
        m1.mapKeyToGroup("C5", "G2");
        m1.mapKeyToGroup("C6", "Default Group");
        assertEquals(3, m1.getGroupCount());

        // now overwrite group "G2"...
        m1.mapKeyToGroup("C4", "G1");
        m1.mapKeyToGroup("C5", "G1");
        assertEquals(2, m1.getGroupCount());
    }

    /**
     * Tests that the getKeyCount() method returns the correct values under
     * various circumstances.
     */
    @Test
    public void testKeyCount() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a new map always has 1 group (the default group)
        assertEquals(0, m1.getKeyCount("Default Group"));

        // simple case
        m1.mapKeyToGroup("K1", "G1");
        assertEquals(1, m1.getKeyCount("G1"));
        m1.mapKeyToGroup("K1", null);
        assertEquals(0, m1.getKeyCount("G1"));

        // if there is an explicit mapping to the default group, it is counted
        m1.mapKeyToGroup("K2", "Default Group");
        assertEquals(1, m1.getKeyCount("Default Group"));

        // complicate things a little...
        m1.mapKeyToGroup("K3", "Default Group");
        m1.mapKeyToGroup("K4", "G2");
        m1.mapKeyToGroup("K5", "G2");
        m1.mapKeyToGroup("K6", "Default Group");
        assertEquals(3, m1.getKeyCount("Default Group"));
        assertEquals(2, m1.getKeyCount("G2"));

        // now overwrite group "G2"...
        m1.mapKeyToGroup("K4", "G1");
        m1.mapKeyToGroup("K5", "G1");
        assertEquals(2, m1.getKeyCount("G1"));
        assertEquals(0, m1.getKeyCount("G2"));
    }

    /**
     * Tests the getGroupIndex() method.
     */
    @Test
    public void testGetGroupIndex() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // the default group is always at index 0
        assertEquals(0, m1.getGroupIndex("Default Group"));

        // a non-existent group should return -1
        assertEquals(-1, m1.getGroupIndex("G3"));

        // indices are assigned in the order that groups are originally mapped
        m1.mapKeyToGroup("K3", "G3");
        m1.mapKeyToGroup("K1", "G1");
        m1.mapKeyToGroup("K2", "G2");
        assertEquals(1, m1.getGroupIndex("G3"));
        assertEquals(2, m1.getGroupIndex("G1"));
        assertEquals(3, m1.getGroupIndex("G2"));
    }

    /**
     * Tests the getGroup() method.
     */
    @Test
    public void testGetGroup() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a key that hasn't been mapped should return the default group
        assertEquals("Default Group", m1.getGroup("K1"));

        m1.mapKeyToGroup("K1", "G1");
        assertEquals("G1", m1.getGroup("K1"));
        m1.mapKeyToGroup("K1", "G2");
        assertEquals("G2", m1.getGroup("K1"));
        m1.mapKeyToGroup("K1", null);
        assertEquals("Default Group", m1.getGroup("K1"));

        // a null argument should throw an exception
        boolean pass = false;
        try {
            Comparable g = m1.getGroup(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");
        KeyToGroupMap m2 = new KeyToGroupMap("Default Group");
        assertEquals(m1, m2);
        assertEquals(m2, m1);

        m1.mapKeyToGroup("K1", "G1");
        assertNotEquals(m1, m2);
        m2.mapKeyToGroup("K1", "G1");
        assertEquals(m1, m2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyToGroupMap m1 = new KeyToGroupMap("Test");
        m1.mapKeyToGroup("K1", "G1");
        KeyToGroupMap m2 = (KeyToGroupMap) m1.clone();
        assertNotSame(m1, m2);
        assertSame(m1.getClass(), m2.getClass());
        assertEquals(m1, m2);

        // a small check for independence
        m1.mapKeyToGroup("K1", "G2");
        assertNotEquals(m1, m2);
        m2.mapKeyToGroup("K1", "G2");
        assertEquals(m1, m2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyToGroupMap m1 = new KeyToGroupMap("Test");
        KeyToGroupMap m2 = TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

}
