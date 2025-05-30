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
 * ---------------------------
 * RendererChangeDetector.java
 * ---------------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer;

import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;

/**
 * A simple class for detecting whether a renderer has generated
 * a {@link RendererChangeEvent}.
 */
public class RendererChangeDetector implements RendererChangeListener {

    /** A flag that records whether a change event has been received. */
    private boolean notified;

    /**
     * Creates a new detector.
     */
    public RendererChangeDetector() {
        this.notified = false;
    }

    /**
     * Returns the flag that indicates whether a change event has been
     * received.
     *
     * @return The flag.
     */
    public boolean getNotified() {
        return this.notified;
    }

    /**
     * Sets the flag that indicates whether a change event has been
     * received.
     *
     * @param notified  the new value of the flag.
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    /**
     * Receives a {@link RendererChangeEvent} from a renderer.
     *
     * @param event  the event.
     */
    @Override
    public void rendererChanged(RendererChangeEvent event) {
        this.notified = true;
    }

}
