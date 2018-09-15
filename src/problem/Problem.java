package problem;

import problem.component.FlightRecord;
import problem.component.GatesInformation;
import util.ioUtil.excel.excelReader;

import java.util.ArrayList;

public class Problem {

    protected ArrayList<FlightRecord> flightRecordArrayList
            = new ArrayList<>();

    protected ArrayList<GatesInformation> gatesInformationArrayList
            = new ArrayList<>();

    protected void getFlightRecordArrayListFromExcel() {

        ArrayList<ArrayList<String>> originalData
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0, true,
                1, 306,
                0, -1, originalData);

        for (int i = 0; i < originalData.size(); i++) {

            /*String arrivalDate = originalData.get(i).get(0);
            String arrivalTime = originalData.get(i).get(1);*/
            String arrivalDate = originalData.get(i).get(9).split(" +")[0];
            String arrivalTime = originalData.get(i).get(9).split(" +")[1];
            /*if (originalData.get(i).get(9).split(" ").length !=2) {
                throw new RuntimeException();
            }*/

            String arrivalFlightName = originalData.get(i).get(2);
            String arrivalType = originalData.get(i).get(3);
            String planeType = originalData.get(i).get(4);
            /*String leftDate = originalData.get(i).get(5);
            String leftTime = originalData.get(i).get(6);*/
            String leftDate = originalData.get(i).get(10).split(" +")[0];
            String leftTime = originalData.get(i).get(10).split(" +")[1];
            /*if (originalData.get(i).get(10).split(" ").length !=2) {
                throw new RuntimeException();
            }*/
            String leftFlightName = originalData.get(i).get(7);
            String leftType = originalData.get(i).get(8);

            int arrival = convertDateToMinute(arrivalDate)
                    + convertTimeToMinute(arrivalTime);
            int left = convertDateToMinute(leftDate)
                    + convertTimeToMinute(leftTime);

            FlightRecord flightRecord = new FlightRecord(i, arrival, left,
                    arrivalFlightName, leftFlightName, arrivalType, leftType, planeType);
            this.flightRecordArrayList.add(flightRecord);
        }
    }

    protected void getGatesInformationArrayListFromExcel() {

        ArrayList<ArrayList<String>> originalData
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                2, true,
                1, 70,
                0, -1, originalData);

        for (int i = 0; i < originalData.size(); i++) {

            String name = originalData.get(i).get(0);
            String location = originalData.get(i).get(2);
            String arrivalType = originalData.get(i).get(3);
            String leftType = originalData.get(i).get(4);
            String planeType = originalData.get(i).get(5);

            GatesInformation gatesInformation
                    = new GatesInformation(i, name, location,
                    arrivalType, leftType, planeType);

            this.gatesInformationArrayList.add(gatesInformation);
        }
    }

    private int convertDateToMinute(String date) {

        String day = date.substring(date.length() - 2, date.length());

        return Integer.valueOf(day) * 24 * 60;
    }

    private int convertTimeToMinute(String time) {

        String[] splitResult = time.split(":");

        if (splitResult.length != 2) {
            throw new RuntimeException();
        }

        return (Integer.valueOf(splitResult[0].trim()) * 60
                + Integer.valueOf(splitResult[1].trim()));
    }


    // Below is for test
    public static void main(String[] args) {

        Problem problem = new Problem();

        problem.getFlightRecordArrayListFromExcel();

        problem.getGatesInformationArrayListFromExcel();

        System.out.println("haha");
    }
}
