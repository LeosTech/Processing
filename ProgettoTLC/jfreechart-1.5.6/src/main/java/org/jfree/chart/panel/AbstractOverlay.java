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
 * AbstractOverlay.java
 * --------------------
 * (C) Copyright 2009-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.panel;

import javax.swing.event.EventListenerList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.OverlayChangeEvent;
import org.jfree.chart.event.OverlayChangeListener;
import org.jfree.chart.util.Args;

/**
 * A base class for implementing overlays for a {@link ChartPanel}.
 */
public class AbstractOverlay {

    /** Storage for registered change listeners. */
    private final transient EventListenerList changeListeners;

    /**
     * Default constructor.
     */
    public AbstractOverlay() {
        this.changeListeners = new EventListenerList();
    }

    /**
     * Registers an object for notification of changes to the overlay.
     *
     * @param listener  the listener ({@code null} not permitted).
     *
     * @see #removeChangeListener(OverlayChangeListener)
     */
    public void addChangeListener(OverlayChangeListener listener) {
        Args.nullNotPermitted(listener, "listener");
        this.changeListeners.add(OverlayChangeListener.class, listener);
    }

    /**
     * Deregisters an object for notification of changes to the overlay.
     *
     * @param listener  the listener ({@code null} not permitted)
     *
     * @see #addChangeListener(OverlayChangeListener)
     */
    public void removeChangeListener(OverlayChangeListener listener) {
        Args.nullNotPermitted(listener, "listener");
        this.changeListeners.remove(OverlayChangeListener.class, listener);
    }

    /**
     * Sends a default {@link ChartChangeEvent} to all registered listeners.
     * <P>
     * This method is for convenience only.
     */
    public void fireOverlayChanged() {
        OverlayChangeEvent event = new OverlayChangeEvent(this);
        notifyListeners(event);
    }

    /**
     * Sends a {@link ChartChangeEvent} to all registered listeners.
     *
     * @param event  information about the event that triggered the
     *               notification.
     */
    protected void notifyListeners(OverlayChangeEvent event) {
       Object[] listeners = this.changeListeners.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == OverlayChangeListener.class) {
                ((OverlayChangeListener) listeners[i + 1]).overlayChanged(
                        event);
            }
        }
    }

}

