package util.paintUtil.opencv;

import org.opencv.core.CvType;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import problem.component.FlightRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;

/**
 * This class is to draw timing diagram using the methods in OpenCV.
 */
public class DrawTimingDiagram {

    static {
        String dllPath
                = "./lib/x64/opencv_java320.dll";
        System.load(dllPath);
    }

    /**
     * To draw the timing diagram of the situation of all the boarding gates.
     *
     * @param filename           the file name of the output image
     * @param distributionResult the distribution result of the boarding gates
     */
    public static void drawTotalTimingDiagramOfAllBoardingGates(
            String filename,
            ArrayList<ArrayList<FlightRecord>> distributionResult) {

        Mat resultMat = new Mat(distributionResult.size() * 20, 1440,
                CvType.CV_8UC3, new Scalar(0, 0, 0));

        int startTime = 20 * 24 * 60;
        int endTime = 21 * 24 * 60;

        for (int i = 0; i < distributionResult.size(); i++) {

            Point pt1 = new Point();
            Point pt2 = new Point();
            pt1.y = i * 20;
            pt2.y = i * 20 + 10;

            for (int j = 0; j < distributionResult.get(i).size(); j++) {

                int arrivalTime = distributionResult.get(i).get(j).getArrivalTime();
                int leftTime = distributionResult.get(i).get(j).getLeftTime();
                arrivalTime = (arrivalTime < startTime ? startTime : arrivalTime);
                leftTime = (leftTime > endTime ? endTime : leftTime);

                pt1.x = arrivalTime - startTime;
                pt2.x = leftTime - startTime;

                Imgproc.rectangle(resultMat, pt1, pt2, new Scalar(255, 255, 255), -1);

                pt1.x = pt2.x;
                pt2.x += 45;

                Imgproc.rectangle(resultMat, pt1, pt2, new Scalar(0, 0, 255), -1);
            }
        }

        Imgcodecs.imwrite(filename, resultMat);

        //System.out.println("Draw Rectangle Completed!");
    }

    /**
     * To draw the timing diagram of the situation of a boarding gate.
     *
     * @param filename the file name of the output image
     * @param gate     the flight records in the current boarding gate
     */
    public static void drawTimingDiagramOfASingleGate(
            String filename, List<FlightRecord> gate) {

        Mat resultMat = new Mat(gate.size() * 20 + 10, 1440,
                CvType.CV_8UC3, new Scalar(0, 0, 0));

        int startTime = 20 * 24 * 60;
        int endTime = 21 * 24 * 60;

        for (int i = 0; i < gate.size(); i++) {

            Point pt1 = new Point();
            Point pt2 = new Point();
            pt1.y = i * 20 + 10;
            pt2.y = (i + 1) * 20;

            FlightRecord flightRecord = gate.get(i);
            int arrivalTime = flightRecord.getArrivalTime();
            int leftTime = flightRecord.getLeftTime();
            arrivalTime = (arrivalTime < startTime ? startTime : arrivalTime);
            leftTime = (leftTime > endTime ? endTime : leftTime);

            pt1.x = arrivalTime - startTime;
            pt2.x = leftTime - startTime;

            Imgproc.rectangle(resultMat, pt1, pt2,
                    new Scalar(255, 255, 255), -1);

            pt1.x = pt2.x - 1;
            pt2.x += 74;

            Imgproc.rectangle(resultMat, pt1, pt2,
                    new Scalar(0, 0, 255), -1);
        }

        Imgcodecs.imwrite(filename, resultMat);
    }

    /**
     * To draw the timing diagram of the flight records which can not be
     * arranged properly.
     *
     * @param filename         the file name of the output image
     * @param currentTypeGates the current type of the boarding gates
     * @param conflictRecord   the conflict flight records
     */
    public static void drawTimingDiagramOfConflictSituation(
            String filename, ArrayList<LinkedList<FlightRecord>> currentTypeGates,
            FlightRecord conflictRecord) {

        Mat resultMat = new Mat(
                (currentTypeGates.size() + 1) * 20 + 10,
                1440, CvType.CV_8UC3, new Scalar(0, 0, 0));

        int startTime = 20 * 24 * 60;
        int endTime = 21 * 24 * 60;

        for (int i = 0; i < currentTypeGates.size(); i++) {

            FlightRecord currentGateLastFlight
                    = currentTypeGates.get(i).getLast();

            Point pt1 = new Point();
            Point pt2 = new Point();
            pt1.y = i * 20 + 10;
            pt2.y = (i + 1) * 20;

            int arrivalTime = currentGateLastFlight.getArrivalTime();
            int leftTime = currentGateLastFlight.getLeftTime();
            arrivalTime = (arrivalTime < startTime ? startTime : arrivalTime);
            leftTime = (leftTime > endTime ? endTime : leftTime);

            pt1.x = arrivalTime - startTime;
            pt2.x = leftTime - startTime;

            Imgproc.rectangle(resultMat, pt1, pt2,
                    new Scalar(255, 255, 255), -1);

            pt1.x = pt2.x - 1;
            pt2.x += 74;

            Imgproc.rectangle(resultMat, pt1, pt2,
                    new Scalar(0, 0, 255), -1);
        }


        Point pt1 = new Point();
        Point pt2 = new Point();
        pt1.y = currentTypeGates.size() * 20 + 10;
        pt2.y = (currentTypeGates.size() + 1) * 20;

        int arrivalTime = conflictRecord.getArrivalTime();
        int leftTime = conflictRecord.getLeftTime();
        arrivalTime = (arrivalTime < startTime ? startTime : arrivalTime);
        leftTime = (leftTime > endTime ? endTime : leftTime);

        pt1.x = arrivalTime - startTime;
        pt2.x = leftTime - startTime;

        Imgproc.rectangle(resultMat, pt1, pt2,
                new Scalar(255, 0, 0), -1);

        Imgcodecs.imwrite(filename, resultMat);
    }
}
