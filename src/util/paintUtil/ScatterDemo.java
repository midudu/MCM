package util.paintUtil;

import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

class ScatterDemo extends ApplicationFrame {

    public ScatterDemo(String title) {

        super(title);

        DefaultXYDataset defaultXYDataset = new DefaultXYDataset();

        double[][] data1 = new double[2][100];
        for (int i = 0; i < 100; i++) {
            data1[0][i] = i;
            data1[1][i] = i;
        }
        double[][] data2 = new double[2][360];
        for (int i = 0; i < 360; i++) {
            data2[0][i] = 50 * Math.cos(Math.PI * 2 / 360 * i);
            data2[1][i] = 50 * Math.sin(Math.PI * 2 / 360 * i);
        }

        defaultXYDataset.addSeries("line", data1);
        defaultXYDataset.addSeries("circle", data2);

        JFreeChart chart = ChartFactory.createScatterPlot(
                title, "People Numbers", "time",
                defaultXYDataset);

        chart.addSubtitle(new TextTitle("Scatter Demo"));

        XYPlot plot = chart.getXYPlot();
        XYItemRenderer localLineAndShapeRenderer = plot.getRenderer();
        localLineAndShapeRenderer.setSeriesStroke(
                    0, new BasicStroke(0.5f));
        localLineAndShapeRenderer.setSeriesStroke(
                1, new BasicStroke(4f));
        localLineAndShapeRenderer.setSeriesPaint(0,Color.yellow);

        NumberAxis xAxis = (NumberAxis)plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(10));
        xAxis.setRange(-100,100);
        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setRange(-100,100);
        yAxis.setTickUnit(new NumberTickUnit(10));

        ChartPanel panel = new ChartPanel(chart, true);

        panel.setPreferredSize(new java.awt.Dimension(500, 500));

        this.setContentPane(panel);

    }

    public static void main(String[] args) {

        ScatterDemo demo = new ScatterDemo(
                "JFreeChart: ScatterDemo.java");

        demo.pack();

        demo.setVisible(true);
    }
}