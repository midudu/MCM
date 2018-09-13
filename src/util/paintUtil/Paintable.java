package util.paintUtil;

import component.DoublePoint;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

public interface Paintable {

    public static XYDataset createDataSet(
            ArrayList<ArrayList<DoublePoint>> data) {

        ArrayList<XYSeries> series = new ArrayList<>(data.size());

        for (int i = 0; i < data.size(); i++) {

            XYSeries currentSeries = new XYSeries(Integer.toString(i));
            for (int j = 0; j < data.get(i).size(); j++) {
                currentSeries.add(data.get(i).get(j).getX(),
                        data.get(i).get(j).getX());
            }
            series.add(currentSeries);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries currentSeries:series) {
            dataset.addSeries(currentSeries);
        }

        return dataset;
    }

    public static XYDataset createDefaultDataSet() {

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
}
