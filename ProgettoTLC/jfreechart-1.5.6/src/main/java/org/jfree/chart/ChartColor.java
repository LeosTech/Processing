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
 * ---------------
 * ChartColor.java
 * ---------------
 * (C) Copyright 2003-present, by Cameron Riley and Contributors.
 *
 * Original Author:  Cameron Riley;
 * Contributor(s):   David Gilbert;
 *                   Yuri Blankenstein;
 *
 */

package org.jfree.chart;

import java.awt.Color;
import java.awt.Paint;

/**
 * Class to extend the number of Colors available to the charts. This
 * extends the java.awt.Color object and extends the number of final
 * Colors publicly accessible.
 */
public class ChartColor extends Color {

    /** A very dark red color. */
    public static final Color VERY_DARK_RED = new Color(0x80, 0x00, 0x00);

    /** A dark red color. */
    public static final Color DARK_RED = new Color(0xc0, 0x00, 0x00);

    /** A light red color. */
    public static final Color LIGHT_RED = new Color(0xFF, 0x40, 0x40);

    /** A very light red color. */
    public static final Color VERY_LIGHT_RED = new Color(0xFF, 0x80, 0x80);

    /** A very dark yellow color. */
    public static final Color VERY_DARK_YELLOW = new Color(0x80, 0x80, 0x00);

    /** A dark yellow color. */
    public static final Color DARK_YELLOW = new Color(0xC0, 0xC0, 0x00);

    /** A light yellow color. */
    public static final Color LIGHT_YELLOW = new Color(0xFF, 0xFF, 0x40);

    /** A very light yellow color. */
    public static final Color VERY_LIGHT_YELLOW = new Color(0xFF, 0xFF, 0x80);

    /** A very dark green color. */
    public static final Color VERY_DARK_GREEN = new Color(0x00, 0x80, 0x00);

    /** A dark green color. */
    public static final Color DARK_GREEN = new Color(0x00, 0xC0, 0x00);

    /** A light green color. */
    public static final Color LIGHT_GREEN = new Color(0x40, 0xFF, 0x40);

    /** A very light green color. */
    public static final Color VERY_LIGHT_GREEN = new Color(0x80, 0xFF, 0x80);

    /** A very dark cyan color. */
    public static final Color VERY_DARK_CYAN = new Color(0x00, 0x80, 0x80);

    /** A dark cyan color. */
    public static final Color DARK_CYAN = new Color(0x00, 0xC0, 0xC0);

    /** A light cyan color. */
    public static final Color LIGHT_CYAN = new Color(0x40, 0xFF, 0xFF);

    /** Aa very light cyan color. */
    public static final Color VERY_LIGHT_CYAN = new Color(0x80, 0xFF, 0xFF);

    /** A very dark blue color. */
    public static final Color VERY_DARK_BLUE = new Color(0x00, 0x00, 0x80);

    /** A dark blue color. */
    public static final Color DARK_BLUE = new Color(0x00, 0x00, 0xC0);

    /** A light blue color. */
    public static final Color LIGHT_BLUE = new Color(0x40, 0x40, 0xFF);

    /** A very light blue color. */
    public static final Color VERY_LIGHT_BLUE = new Color(0x80, 0x80, 0xFF);

    /** A very dark magenta/purple color. */
    public static final Color VERY_DARK_MAGENTA = new Color(0x80, 0x00, 0x80);

    /** A dark magenta color. */
    public static final Color DARK_MAGENTA = new Color(0xC0, 0x00, 0xC0);

    /** A light magenta color. */
    public static final Color LIGHT_MAGENTA = new Color(0xFF, 0x40, 0xFF);

    /** A very light magenta color. */
    public static final Color VERY_LIGHT_MAGENTA = new Color(0xFF, 0x80, 0xFF);

    /**
     * Creates a Color with an opaque sRGB with red, green and blue values in
     * range 0-255.
     *
     * @param r  the red component in range 0x00-0xFF.
     * @param g  the green component in range 0x00-0xFF.
     * @param b  the blue component in range 0x00-0xFF.
     */
    public ChartColor(int r, int g, int b) {
        super(r, g, b);
    }

    /**
     * Convenience method to return an array of {@code Paint} objects that
     * represent the pre-defined colors in the {@code Color} and
     * {@code ChartColor} objects.
     *
     * @return An array of objects with the {@code Paint} interface.
     * @see #createDefaultColorArray()
     */
    public static Paint[] createDefaultPaintArray() {
        return createDefaultColorArray();
    }
    
    /**
     * Convenience method to return an array of {@code Color} objects that
     * represent the pre-defined colors in the {@code Color} and
     * {@code ChartColor} objects.
     *
     * @return An array of objects with the {@code Color} interface.
     */
    public static Color[] createDefaultColorArray() {
        return new Color[] {
            new Color(0xFF, 0x55, 0x55),
            new Color(0x55, 0x55, 0xFF),
            new Color(0x55, 0xFF, 0x55),
            new Color(0xFF, 0xFF, 0x55),
            new Color(0xFF, 0x55, 0xFF),
            new Color(0x55, 0xFF, 0xFF),
            Color.PINK,
            Color.GRAY,
            ChartColor.DARK_RED,
            ChartColor.DARK_BLUE,
            ChartColor.DARK_GREEN,
            ChartColor.DARK_YELLOW,
            ChartColor.DARK_MAGENTA,
            ChartColor.DARK_CYAN,
            Color.DARK_GRAY,
            ChartColor.LIGHT_RED,
            ChartColor.LIGHT_BLUE,
            ChartColor.LIGHT_GREEN,
            ChartColor.LIGHT_YELLOW,
            ChartColor.LIGHT_MAGENTA,
            ChartColor.LIGHT_CYAN,
            Color.LIGHT_GRAY,
            ChartColor.VERY_DARK_RED,
            ChartColor.VERY_DARK_BLUE,
            ChartColor.VERY_DARK_GREEN,
            ChartColor.VERY_DARK_YELLOW,
            ChartColor.VERY_DARK_MAGENTA,
            ChartColor.VERY_DARK_CYAN,
            ChartColor.VERY_LIGHT_RED,
            ChartColor.VERY_LIGHT_BLUE,
            ChartColor.VERY_LIGHT_GREEN,
            ChartColor.VERY_LIGHT_YELLOW,
            ChartColor.VERY_LIGHT_MAGENTA,
            ChartColor.VERY_LIGHT_CYAN
        };
    }

    /**
     * Creates an array of {@link Color#darker() darker} {@code colors} to use
     * for e.g. borders.
     * 
     * @param colors original colors
     * @return a new array containing {@link Color#darker() darker} instances of
     *         the original colors.
     */
    public static Color[] createDarkerColorArray(Color[] colors) {
        final Color[] result = new Color[colors.length];
        for (int i = 0; i < colors.length; i++) {
            result[i] = colors[i].darker();
        }
        return result;
    }

    /**
     * Returns either {@link Color#BLACK black} or {@link Color#WHITE white},
     * depending on the provided {@code color} to achieve the best contrast for
     * e.g. text labels.<br>
     * The {@code color} is
     * <a href="https://en.wikipedia.org/wiki/YIQ#From_RGB_to_YIQ">converted
     * from RGB to YIQ</a> and the luminance value (Y; &#8776;[0 .. 255]) is
     * used to determine if {@code color} is closer to either {@link Color#BLACK
     * black} or {@link Color#WHITE white}.
     * 
     * @param color the color for which the contrasted color is computed
     * @return either {@link Color#BLACK black} or {@link Color#WHITE white},
     *         depending on {@code color}
     */
    public static Color getContrastColor(Color color) {
        // From Wikipedia: The Y component represents the luma information, and
        // is the only component used by black-and-white television receivers.
        final double luminanceY = 0.299 * color.getRed()
                + 0.587 * color.getGreen() + 0.114 * color.getBlue();
        return luminanceY >= 128 ? Color.BLACK : Color.WHITE;
    }
}
