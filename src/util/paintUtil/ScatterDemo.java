package util.paintUtil;

import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class ScatterDemo extends ApplicationFrame {

    public ScatterDemo(String title) {

        super(title);

        /*DefaultXYDataset defaultXYDataset = new DefaultXYDataset();

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
        defaultXYDataset.addSeries("circle", data2);*/

        XYDataset defaultXYDataset = createDataset();

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

    private static XYDataset createDataset() {

        XYSeries series1 = new XYSeries("First");
        series1.add(1.0, 1.0);
        series1.add(2.0, 4.0);
        series1.add(3.0, 3.0);
        series1.add(4.0, 5.0);
        series1.add(5.0, 5.0);
        series1.add(6.0, 7.0);
        series1.add(7.0, 7.0);
        series1.add(8.0, 8.0);

        XYSeries series2 = new XYSeries("Second");
        series2.add(1.0, 5.0);
        series2.add(2.0, 7.0);
        series2.add(3.0, 6.0);
        series2.add(4.0, 8.0);
        series2.add(5.0, 4.0);
        series2.add(6.0, 4.0);
        series2.add(7.0, 2.0);
        series2.add(8.0, 1.0);

        XYSeries series3 = new XYSeries("Third");
        series3.add(3.0, 4.0);
        series3.add(4.0, 3.0);
        series3.add(5.0, 2.0);
        series3.add(6.0, 3.0);
        series3.add(7.0, 6.0);
        series3.add(8.0, 3.0);
        series3.add(9.0, 4.0);
        series3.add(10.0, 3.0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        return dataset;
    }

    public static void main(String[] args) {

        ScatterDemo demo = new ScatterDemo(
                "JFreeChart: ScatterDemo.java");

        demo.pack();

        demo.setVisible(true);
    }
}