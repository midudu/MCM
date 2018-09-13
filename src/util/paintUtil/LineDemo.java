package util.paintUtil;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from a {@link CategoryDataset}.
 */

public class LineDemo extends ApplicationFrame implements Paintable {

    public LineDemo(String title) {

        super(title);

        XYDataset dataSet = Paintable.createDefaultDataSet();

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Java Standard Class Library",   // chart title
                "year",                       // domain axis label
                "Class Count",                   // range axis label
                dataSet,                         // data
                PlotOrientation.VERTICAL,        // orientation
                false,                           // include legend
                true,                            // tooltips
                false                            // urls
        );

        // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) plot.getRenderer();

        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel panel = new ChartPanel(chart);

        panel.setMouseWheelEnabled(true);

        panel.setPreferredSize(new Dimension(500, 270));

        this.setContentPane(panel);
    }

    public static void main(String[] args) {

        LineDemo demo = new LineDemo(
                "JFreeChart: LineChartDemo1.java");

        demo.pack();

        demo.setVisible(true);
    }
}
