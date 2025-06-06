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
 * JFreeChartTest.java
 * -------------------
 * (C) Copyright 2002-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.ui.Align;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link JFreeChart} class.
 */
class JFreeChartTest implements ChartChangeListener {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    void testEqualsHashCode() {
        EqualsVerifier.forClass(JFreeChart.class)
                .withPrefabValues(TextTitle.class,
                        new TextTitle("tee"),
                        new TextTitle("vee"))
                .withPrefabValues(Plot.class,
                        TestUtils.createPlot(true),
                        TestUtils.createPlot(false))
                .withPrefabValues(RenderingHints.class,
                        new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF),
                        new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON))
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withPrefabValues(Rectangle2D.class,
                        TestUtils.createR2D(true),
                        TestUtils.createR2D(false))
                .withPrefabValues(Font.class,
                        TestUtils.createFont(true),
                        TestUtils.createFont(false))
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .verify();
    }

    /** A pie chart. */
    private JFreeChart pieChart;

    /**
     * Common test setup.
     */
    @BeforeEach
    public void setUp() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43.2);
        data.setValue("Visual Basic", 0.0);
        data.setValue("C/C++", 17.5);
        this.pieChart = ChartFactory.createPieChart("Pie Chart", data);
    }

    /**
     * Check that the equals() method can distinguish all fields.
     */
    @Test
    void testEquals() {
        JFreeChart chart1 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new PiePlot<String>(), true);
        JFreeChart chart2 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new PiePlot<String>(), true);
        assertEquals(chart1, chart2);
        assertEquals(chart2, chart1);

        // renderingHints
        chart1.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        assertNotEquals(chart1, chart2);
        chart2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        assertEquals(chart1, chart2);

        // borderVisible
        chart1.setBorderVisible(true);
        assertNotEquals(chart1, chart2);
        chart2.setBorderVisible(true);
        assertEquals(chart1, chart2);

        // borderStroke
        BasicStroke s = new BasicStroke(2.0f);
        chart1.setBorderStroke(s);
        assertNotEquals(chart1, chart2);
        chart2.setBorderStroke(s);
        assertEquals(chart1, chart2);

        // borderPaint
        chart1.setBorderPaint(Color.RED);
        assertNotEquals(chart1, chart2);
        chart2.setBorderPaint(Color.RED);
        assertEquals(chart1, chart2);

        // padding
        chart1.setPadding(new RectangleInsets(1, 2, 3, 4));
        assertNotEquals(chart1, chart2);
        chart2.setPadding(new RectangleInsets(1, 2, 3, 4));
        assertEquals(chart1, chart2);

        // title
        chart1.setTitle("XYZ");
        assertNotEquals(chart1, chart2);
        chart2.setTitle("XYZ");
        assertEquals(chart1, chart2);

        // subtitles
        chart1.addSubtitle(new TextTitle("Subtitle"));
        assertNotEquals(chart1, chart2);
        chart2.addSubtitle(new TextTitle("Subtitle"));
        assertEquals(chart1, chart2);

        // plot
        chart1 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new RingPlot(), false);
        chart2 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new PiePlot<String>(), false);
        assertNotEquals(chart1, chart2);
        chart2 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new RingPlot(), false);
        assertEquals(chart1, chart2);

        // backgroundPaint
        chart1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(chart1, chart2);
        chart2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(chart1, chart2);

//        // backgroundImage
//        chart1.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertFalse(chart1.equals(chart2));
//        chart2.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertEquals(chart1, chart2);

        // backgroundImageAlignment
        chart1.setBackgroundImageAlignment(Align.BOTTOM_LEFT);
        assertNotEquals(chart1, chart2);
        chart2.setBackgroundImageAlignment(Align.BOTTOM_LEFT);
        assertEquals(chart1, chart2);

        // backgroundImageAlpha
        chart1.setBackgroundImageAlpha(0.1f);
        assertNotEquals(chart1, chart2);
        chart2.setBackgroundImageAlpha(0.1f);
        assertEquals(chart1, chart2);
    }

    /**
     * A test to make sure that the legend is being picked up in the
     * equals() testing.
     */
    @Test
    void testEquals2() {
        JFreeChart chart1 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new PiePlot<String>(), true);
        JFreeChart chart2 = new JFreeChart("Title",
                new Font("SansSerif", Font.PLAIN, 12), new PiePlot<String>(), false);
        assertNotEquals(chart1, chart2);
        assertNotEquals(chart2, chart1);
    }

    /**
     * Checks the subtitle count - should be 1 (the legend).
     */
    @Test
    void testSubtitleCount() {
        int count = this.pieChart.getSubtitleCount();
        assertEquals(1, count);
    }

    /**
     * Some checks for the getSubtitle() method.
     */
    @Test
    void testGetSubtitle() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        JFreeChart chart = ChartFactory.createPieChart("title", dataset);
        Title t = chart.getSubtitle(0);
        assertInstanceOf(LegendTitle.class, t);

        try {
            chart.getSubtitle(-1);
            fail("Should have thrown an IllegalArgumentException on negative number");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Index out of range.", e.getMessage());
        }

        try {
           chart.getSubtitle(1);
            fail("Should have thrown an IllegalArgumentException on excesive number");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Index out of range.", e.getMessage());
        }

        try {
            chart.getSubtitle(2);
            fail("Should have thrown an IllegalArgumentException on number being out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Index out of range.", e.getMessage());
        }

    }

    /**
     * Serialize a pie chart, restore it, and check for equality.
     */
    @Test
    void testSerialization1() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Type 1", 54.5);
        data.setValue("Type 2", 23.9);
        data.setValue("Type 3", 45.8);

        JFreeChart c1 = ChartFactory.createPieChart("Test", data);
        JFreeChart c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
        LegendTitle lt2 = c2.getLegend();
        assertSame(lt2.getSources()[0], c2.getPlot());
    }

    /**
     * Serialize a 3D pie chart, restore it, and check for equality.
     */
    @Test
    void testSerialization2() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Type 1", 54.5);
        data.setValue("Type 2", 23.9);
        data.setValue("Type 3", 45.8);
        JFreeChart c1 = ChartFactory.createPieChart("Test", data);
        JFreeChart c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * Serialize a bar chart, restore it, and check for equality.
     */
    @Test
    void testSerialization3() {

        // row keys...
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";

        // column keys...
        String category1 = "Category 1";
        String category2 = "Category 2";
        String category3 = "Category 3";
        String category4 = "Category 4";
        String category5 = "Category 5";
        String category6 = "Category 6";
        String category7 = "Category 7";
        String category8 = "Category 8";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);
        dataset.addValue(7.0, series1, category6);
        dataset.addValue(7.0, series1, category7);
        dataset.addValue(8.0, series1, category8);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);
        dataset.addValue(4.0, series2, category6);
        dataset.addValue(2.0, series2, category7);
        dataset.addValue(1.0, series2, category8);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);
        dataset.addValue(3.0, series3, category6);
        dataset.addValue(4.0, series3, category7);
        dataset.addValue(3.0, series3, category8);

        // create the chart...
        JFreeChart c1 = ChartFactory.createBarChart("Vertical Bar Chart",
                "Category", "Value", dataset);
        JFreeChart c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * Serialize a time seroes chart, restore it, and check for equality.
     */
    @Test
    void testSerialization4() {

        RegularTimePeriod t = new Day();
        TimeSeries series = new TimeSeries("Series 1");
        series.add(t, 36.4);
        t = t.next();
        series.add(t, 63.5);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        JFreeChart c1 = ChartFactory.createTimeSeriesChart("Test", "Date",
                "Value", dataset);
        JFreeChart c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * Some checks for the addSubtitle() methods.
     */
    @Test
    void testAddSubtitle() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        JFreeChart chart = ChartFactory.createPieChart("title", dataset);

        TextTitle t0 = new TextTitle("T0");
        chart.addSubtitle(0, t0);
        assertEquals(t0, chart.getSubtitle(0));

        TextTitle t1 = new TextTitle("T1");
        chart.addSubtitle(t1);
        assertEquals(t1, chart.getSubtitle(2));  // subtitle 1 is the legend

        try {
            chart.addSubtitle(null);
            fail("Should have thrown an IllegalArgumentException on index out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'subtitle' argument.", e.getMessage());
        }

        try {
            chart.addSubtitle(-1, t0);
            fail("Should have thrown an IllegalArgumentException on index out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("The 'index' argument is out of range.", e.getMessage());
        }

        try {
            chart.addSubtitle(4, t0);
            fail("Should have thrown an IllegalArgumentException on index out of range");
        }
        catch (IllegalArgumentException e) {
             assertEquals("The 'index' argument is out of range.", e.getMessage());
        }

    }

    /**
     * Some checks for the getSubtitles() method.
     */
    @Test
    void testGetSubtitles() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        JFreeChart chart = ChartFactory.createPieChart("title", dataset);
        List<Title> subtitles = chart.getSubtitles();

        assertEquals(1, chart.getSubtitleCount());

        // adding something to the returned list should NOT change the chart
        subtitles.add(new TextTitle("T"));
        assertEquals(1, chart.getSubtitleCount());
    }

    /**
     * Some checks for the default legend firing change events.
     */
    @Test
    void testLegendEvents() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        JFreeChart chart = ChartFactory.createPieChart("title", dataset);
        chart.addChangeListener(this);
        this.lastChartChangeEvent = null;
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.TOP);
        assertNotNull(this.lastChartChangeEvent);
    }

    /**
     * Some checks for title changes and event notification.
     */
    @Test
    void testTitleChangeEvent() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        JFreeChart chart = ChartFactory.createPieChart("title", dataset);
        chart.addChangeListener(this);
        this.lastChartChangeEvent = null;
        TextTitle t = chart.getTitle();
        t.setFont(new Font("Dialog", Font.BOLD, 9));
        assertNotNull(this.lastChartChangeEvent);
        this.lastChartChangeEvent = null;

        // now create a new title and replace the existing title, several
        // things should happen:
        // (1) Adding the new title should trigger an immediate
        //     ChartChangeEvent;
        // (2) Modifying the new title should trigger a ChartChangeEvent;
        // (3) Modifying the old title should NOT trigger a ChartChangeEvent
        TextTitle t2 = new TextTitle("T2");
        chart.setTitle(t2);
        assertNotNull(this.lastChartChangeEvent);
        this.lastChartChangeEvent = null;

        t2.setFont(new Font("Dialog", Font.BOLD, 9));
        assertNotNull(this.lastChartChangeEvent);
        this.lastChartChangeEvent = null;

        t.setFont(new Font("Dialog", Font.BOLD, 9));
        assertNull(this.lastChartChangeEvent);
        this.lastChartChangeEvent = null;
    }

    @Test
    void testBug942() {
        final String title = "Pie Chart Demo 1\n\n\ntestnew line";
        assertEquals(title, ChartFactory.createPieChart(title, 
                new DefaultPieDataset<String>()).getTitle().getText());
    }

    /** The last ChartChangeEvent received. */
    private ChartChangeEvent lastChartChangeEvent;

    /**
     * Records the last chart change event.
     *
     * @param event  the event.
     */
    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.lastChartChangeEvent = event;
    }

}
