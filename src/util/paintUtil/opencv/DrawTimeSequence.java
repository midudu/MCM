package util.paintUtil.opencv;

import org.opencv.core.CvType;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import problem.component.FlightRecord;

import java.util.ArrayList;

import org.opencv.core.Mat;

public class DrawTimeSequence {

    static {
        String dllPath
                = "C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java320.dll";
        System.load(dllPath);
    }

    public static void drawTimeSequenceTable(
            String filename,
            ArrayList<ArrayList<FlightRecord>> divisionResult) {

        Mat resultMat = new Mat(divisionResult.size() * 20, 1440,
                CvType.CV_8UC3, new Scalar(0,0,0));

        int startTime = 20 * 24 * 60;
        int endTime = 21 * 24 * 60;

        for (int i = 0; i < divisionResult.size(); i++) {

            Point pt1 = new Point();
            Point pt2 = new Point();
            pt1.y = i * 20;
            pt2.y = i * 20 + 10;

            for (int j = 0; j < divisionResult.get(i).size(); j++) {

                int arrivalTime = divisionResult.get(i).get(j).getArrivalTime();
                int leftTime = divisionResult.get(i).get(j).getLeftTime();
                arrivalTime = (arrivalTime < startTime ? startTime: arrivalTime);
                leftTime = (leftTime > endTime ? endTime: leftTime);

                pt1.x = arrivalTime - startTime;
                pt2.x = leftTime - startTime;

                Imgproc.rectangle(resultMat, pt1, pt2, new Scalar(255,255,255), -1);
            }
        }

        Imgcodecs.imwrite(filename, resultMat);

        System.out.println("Draw Rectangle Completed!");
    }
}
