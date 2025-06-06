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
 * ------------
 * LogTick.java
 * ------------
 * (C) Copyright 2014-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 * 
 */

package org.jfree.chart.axis;

import java.text.AttributedString;
import org.jfree.chart.ui.TextAnchor;

/**
 * A tick from a {@link LogAxis}.
 */
public class LogTick extends ValueTick {
    
    /** The attributed string for the tick label. */
    AttributedString attributedLabel;
    
    /**
     * Creates a new instance.
     * 
     * @param type  the type (major or minor tick, {@code null} not 
     *         permitted).
     * @param value  the value.
     * @param label  the label ({@code null} permitted).
     * @param textAnchor  the text anchor.
     */
    public LogTick(TickType type, double value, AttributedString label, 
            TextAnchor textAnchor) {
        super(type, value, null, textAnchor, textAnchor, 0.0);
        this.attributedLabel = label;
    }
    
    /**
     * Returns the attributed string for the tick label, or {@code null} 
     * if there is no label.
     * 
     * @return The attributed string or {@code null}. 
     */
    public AttributedString getAttributedLabel() {
        return this.attributedLabel;
    }
}
