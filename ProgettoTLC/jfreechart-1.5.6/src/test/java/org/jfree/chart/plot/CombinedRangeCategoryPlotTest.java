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
 * -----------------------------------
 * CombinedRangeCategoryPlotTest.java
 * -----------------------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.List;
import java.util.ResourceBundle;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CombinedRangeCategoryPlot} class.
 */
public class CombinedRangeCategoryPlotTest implements ChartChangeListener {

    /** A list of the events received. */
    private final List<ChartChangeEvent> events = new java.util.ArrayList<>();

    /**
     * Receives a chart change event.
     *
     * @param event  the event.
     */
    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.events.add(event);
    }
    
    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(CombinedRangeCategoryPlot.class)
                .withRedefinedSuperclass() // superclass also defines equals/hashCode
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withPrefabValues(Plot.class, TestUtils.createPlot(true), TestUtils.createPlot(false))
                .withPrefabValues(JFreeChart.class,
                        TestUtils.createJFC(true),
                        TestUtils.createJFC(false))
                .withPrefabValues(ResourceBundle.class,
                        TestUtils.createRB(true),
                        TestUtils.createRB(false))
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withPrefabValues(String.class, "A", "B")
                .withPrefabValues(AttributedString.class, TestUtils.createAS(true), TestUtils.createAS(false))
                .withIgnoredFields("subplotArea")
                .withIgnoredFields("chart", "parent") // superclass
                .verify();
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        CombinedRangeCategoryPlot plot1 = createPlot();
        CombinedRangeCategoryPlot plot2 = createPlot();
        assertEquals(plot1, plot2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CombinedRangeCategoryPlot plot1 = createPlot();
        CombinedRangeCategoryPlot plot2 = (CombinedRangeCategoryPlot) 
                plot1.clone();
        assertNotSame(plot1, plot2);
        assertSame(plot1.getClass(), plot2.getClass());
        assertEquals(plot1, plot2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CombinedRangeCategoryPlot plot1 = createPlot();
        CombinedRangeCategoryPlot plot2 = TestUtils.serialised(plot1);
        assertEquals(plot1, plot2);
    }

    /**
     * This is a test to replicate the bug report 1121172.
     */
    @Test
    public void testRemoveSubplot() {
        CombinedRangeCategoryPlot plot = new CombinedRangeCategoryPlot();
        CategoryPlot plot1 = new CategoryPlot();
        CategoryPlot plot2 = new CategoryPlot();
        CategoryPlot plot3 = new CategoryPlot();
        plot.add(plot1);
        plot.add(plot2);
        plot.add(plot3);
        plot.remove(plot2);
        List plots = plot.getSubplots();
        assertEquals(2, plots.size());
    }

    /**
     * Check that only one chart change event is generated by a change to a
     * subplot.
     */
    @Test
    public void testNotification() {
        CombinedRangeCategoryPlot plot = createPlot();
        JFreeChart chart = new JFreeChart(plot);
        chart.addChangeListener(this);
        CategoryPlot subplot1 = (CategoryPlot) plot.getSubplots().get(0);
        NumberAxis yAxis = (NumberAxis) subplot1.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(!yAxis.getAutoRangeIncludesZero());
        assertEquals(1, this.events.size());

        // a redraw should NOT trigger another change event
        BufferedImage image = new BufferedImage(200, 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        this.events.clear();
        chart.draw(g2, new Rectangle2D.Double(0.0, 0.0, 200.0, 100.0));
        assertTrue(this.events.isEmpty());
    }

    /**
     * Creates a dataset.
     *
     * @return A dataset.
     */
    public CategoryDataset createDataset1() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        // row keys...
        String series1 = "First";
        String series2 = "Second";

        // column keys...
        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";

        result.addValue(1.0, series1, type1);
        result.addValue(4.0, series1, type2);
        result.addValue(3.0, series1, type3);
        result.addValue(5.0, series1, type4);
        result.addValue(5.0, series1, type5);
        result.addValue(7.0, series1, type6);
        result.addValue(7.0, series1, type7);
        result.addValue(8.0, series1, type8);

        result.addValue(5.0, series2, type1);
        result.addValue(7.0, series2, type2);
        result.addValue(6.0, series2, type3);
        result.addValue(8.0, series2, type4);
        result.addValue(4.0, series2, type5);
        result.addValue(4.0, series2, type6);
        result.addValue(2.0, series2, type7);
        result.addValue(1.0, series2, type8);

        return result;

    }

    /**
     * Creates a dataset.
     *
     * @return A dataset.
     */
    public CategoryDataset createDataset2() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        // row keys...
        String series1 = "Third";
        String series2 = "Fourth";

        // column keys...
        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";

        result.addValue(11.0, series1, type1);
        result.addValue(14.0, series1, type2);
        result.addValue(13.0, series1, type3);
        result.addValue(15.0, series1, type4);
        result.addValue(15.0, series1, type5);
        result.addValue(17.0, series1, type6);
        result.addValue(17.0, series1, type7);
        result.addValue(18.0, series1, type8);

        result.addValue(15.0, series2, type1);
        result.addValue(17.0, series2, type2);
        result.addValue(16.0, series2, type3);
        result.addValue(18.0, series2, type4);
        result.addValue(14.0, series2, type5);
        result.addValue(14.0, series2, type6);
        result.addValue(12.0, series2, type7);
        result.addValue(11.0, series2, type8);

        return result;

    }

    /**
     * Creates a sample plot.
     *
     * @return A plot.
     */
    private CombinedRangeCategoryPlot createPlot() {
        CategoryDataset dataset1 = createDataset1();
        CategoryAxis catAxis1 = new CategoryAxis("Category");
        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setDefaultToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        CategoryPlot subplot1 = new CategoryPlot(dataset1, catAxis1, null,
                renderer1);
        subplot1.setDomainGridlinesVisible(true);

        CategoryDataset dataset2 = createDataset2();
        CategoryAxis catAxis2 = new CategoryAxis("Category");
        BarRenderer renderer2 = new BarRenderer();
        renderer2.setDefaultToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        CategoryPlot subplot2 = new CategoryPlot(dataset2, catAxis2, null,
                renderer2);
        subplot2.setDomainGridlinesVisible(true);

        NumberAxis rangeAxis = new NumberAxis("Value");
        CombinedRangeCategoryPlot plot = new CombinedRangeCategoryPlot(
                rangeAxis);
        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
        return plot;
    }

}
