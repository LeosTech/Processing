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
 * ----------------------------------------
 * StandardURLTagFragmentGeneratorTest.java
 * ----------------------------------------
 * (C) Copyright 2007-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.imagemap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link StandardURLTagFragmentGenerator} class.
 */
public class StandardURLTagFragmentGeneratorTest {

    /**
     * Some checks for the generateURLFragment() method.
     */
    @Test
    public void testGenerateURLFragment() {
        StandardURLTagFragmentGenerator g
                = new StandardURLTagFragmentGenerator();
        assertEquals(" href=\"abc\"", g.generateURLFragment("abc"));
        assertEquals(" href=\"images/abc.png\"",
                g.generateURLFragment("images/abc.png"));
        assertEquals(" href=\"https://www.jfree.org/images/abc.png\"",
                g.generateURLFragment("https://www.jfree.org/images/abc.png"));
    }

}
