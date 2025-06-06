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
 * ChartPanelTest.java
 * -------------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.CaretListener;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ChartPanel} class.
 */
public class ChartPanelTest implements ChartChangeListener, ChartMouseListener {

    private final List<ChartChangeEvent> chartChangeEvents = new ArrayList<>();

    /**
     * Receives a chart change event and stores it in a list for later
     * inspection.
     *
     * @param event  the event.
     */
    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.chartChangeEvents.add(event);
    }

    /**
     * Test that the constructor will accept a null chart.
     */
    @Test
    public void testConstructor1() {
        ChartPanel panel = new ChartPanel(null);
        assertNull(panel.getChart());
    }

    /**
     * Test that it is possible to set the panel's chart to null.
     */
    @Test
    public void testSetChart() {
        JFreeChart chart = new JFreeChart(new XYPlot());
        ChartPanel panel = new ChartPanel(chart);
        panel.setChart(null);
        assertNull(panel.getChart());
    }

    /**
     * Check the behaviour of the getListeners() method.
     */
    @Test
    public void testGetListeners() {
        ChartPanel p = new ChartPanel(null);
        p.addChartMouseListener(this);
        EventListener[] listeners = p.getListeners(ChartMouseListener.class);
        assertEquals(1, listeners.length);
        assertEquals(this, listeners[0]);
        // try a listener type that isn't registered
        listeners = p.getListeners(CaretListener.class);
        assertEquals(0, listeners.length);
        p.removeChartMouseListener(this);
        listeners = p.getListeners(ChartMouseListener.class);
        assertEquals(0, listeners.length);

        // try a null argument
        boolean pass = false;
        try {
            listeners = p.getListeners((Class) null);
        }
        catch (NullPointerException e) {
            pass = true;
        }
        assertTrue(pass);

        // try a class that isn't a listener
        pass = false;
        try {
            listeners = p.getListeners(Integer.class);
        }
        catch (ClassCastException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Ignores a mouse click event.
     *
     * @param event  the event.
     */
    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        // ignore
    }

    /**
     * Ignores a mouse move event.
     *
     * @param event  the event.
     */
    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        // ignore
    }

    /**
     * Checks that a call to the zoom() method generates just one
     * ChartChangeEvent.
     */
    @Test
    public void test2502355_zoom() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoom(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomInBoth() method generates just one
     * ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomInBoth() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomInBoth(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomOutBoth() method generates just one
     * ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomOutBoth() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomOutBoth(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the restoreAutoBounds() method generates just one
     * ChartChangeEvent.
     */
    @Test
    public void test2502355_restoreAutoBounds() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.restoreAutoBounds();
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomInDomain() method, for a plot with more
     * than one domain axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomInDomain() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomInDomain(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomInRange() method, for a plot with more
     * than one range axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomInRange() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangeAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomInRange(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomOutDomain() method, for a plot with more
     * than one domain axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomOutDomain() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomOutDomain(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the zoomOutRange() method, for a plot with more
     * than one range axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_zoomOutRange() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangeAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.zoomOutRange(1.0, 2.0);
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the restoreAutoDomainBounds() method, for a plot
     * with more than one range axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_restoreAutoDomainBounds() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.restoreAutoDomainBounds();
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * Checks that a call to the restoreAutoRangeBounds() method, for a plot
     * with more than one range axis, generates just one ChartChangeEvent.
     */
    @Test
    public void test2502355_restoreAutoRangeBounds() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangeAxis(1, new NumberAxis("X2"));
        ChartPanel panel = new ChartPanel(chart);
        chart.addChangeListener(this);
        this.chartChangeEvents.clear();
        panel.restoreAutoRangeBounds();
        assertEquals(1, this.chartChangeEvents.size());
    }

    /**
     * In version 1.0.13 there is a bug where enabling the mouse wheel handler
     * twice would in fact disable it.
     */
    @Test
    public void testSetMouseWheelEnabled() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("TestChart", "X",
                "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        assertTrue(panel.isMouseWheelEnabled());
        panel.setMouseWheelEnabled(true);
        assertTrue(panel.isMouseWheelEnabled());
        panel.setMouseWheelEnabled(false);
        assertFalse(panel.isMouseWheelEnabled());
    }
}
