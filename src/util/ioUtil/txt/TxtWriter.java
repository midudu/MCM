package util.ioUtil.txt;

import problem.component.FlightRecord;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * This class is to write .txt files.
 */
public class TxtWriter {

    public static void writeTxtFile(String fileName, ArrayList<FlightRecord> data) {

        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(fileName, "UTF-8");

            printWriter.print("id  arrivalTime  arrivalFlightName  " +
                    "arrivalType  planeType  leftTime  leftFlightName  leftType");
            printWriter.println();

            for (int i = 0; i < data.size(); i++) {

                FlightRecord flightRecord = data.get(i);

                printWriter.print(flightRecord.getId());
                printWriter.print("  ");
                printWriter.print(flightRecord.getArrivalTime());
                printWriter.print("  ");
                printWriter.print(flightRecord.getArrivalFlightName());
                printWriter.print("  ");
                printWriter.print(flightRecord.getArrivalType());
                printWriter.print("  ");
                printWriter.print(flightRecord.getPlaneType());
                printWriter.print("  ");
                printWriter.print(flightRecord.getLeftTime());
                printWriter.print("  ");
                printWriter.print(flightRecord.getLeftFlightName());
                printWriter.print("  ");
                printWriter.print(flightRecord.getLeftType());
                printWriter.print("  ");

                if (i != data.size() - 1) {
                    printWriter.println();
                }
            }

            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    // Below is for test
    public static void main(String[] args) {

/*        TxtWriter<Integer> txtWriter = new TxtWriter<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add( i + 1);
        }

        txtWriter.writeTxtFile("integer.txt", arrayList);

        System.out.println("Completed!");*/
    }
}
