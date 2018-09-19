package problem;

import problem.component.FlightRecord;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;

/**
 * This class contains some general fields and methods used in three problems.
 */
public class Problem {

    /* An {@code ArrayList} container to store the flight records */
    protected ArrayList<FlightRecord> flightRecordArrayList
            = new ArrayList<>();

    /**
     * To get the flight records from the original data
     */
    protected void getFlightRecordArrayListFromExcel() {

        ArrayList<ArrayList<String>> originalData
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "./resources/InputData_2.xls",
                0, true,
                1, 243,
                0, -1, originalData);

        for (int i = 0; i < originalData.size(); i++) {

            String arrivalDate = originalData.get(i).get(9).split(" +")[0];
            String arrivalTime = originalData.get(i).get(9).split(" +")[1];

            String arrivalFlightName = originalData.get(i).get(2);
            String arrivalType = originalData.get(i).get(3);
            String planeType = originalData.get(i).get(4);

            String leftDate = originalData.get(i).get(10).split(" +")[0];
            String leftTime = originalData.get(i).get(10).split(" +")[1];

            String leftFlightName = originalData.get(i).get(7);
            String leftType = originalData.get(i).get(8);

            int id = Integer.valueOf(originalData.get(i).get(14));

            int arrival = convertDateToMinute(arrivalDate)
                    + convertTimeToMinute(arrivalTime);
            int left = convertDateToMinute(leftDate)
                    + convertTimeToMinute(leftTime);

            FlightRecord flightRecord = new FlightRecord(id, arrival, left,
                    arrivalFlightName, leftFlightName, arrivalType, leftType, planeType);
            this.flightRecordArrayList.add(flightRecord);
        }
    }

    /**
     * To calculate the passed minutes from the current date to 2018.1.1 0:00.
     *
     * @param date the current date
     * @return the passed minutes from the current date to 2018.1.1 0:00
     */
    protected int convertDateToMinute(String date) {

        String day = date.substring(date.length() - 2, date.length());

        return Integer.valueOf(day) * 24 * 60;
    }

    /**
     * To calculate the passed minutes from the current time to 2018.1.20 0:00.
     *
     * @param time the current date
     * @return the passed minutes from the current time to 2018.1.20 0:00.
     */
    protected int convertTimeToMinute(String time) {

        String[] splitResult = time.split(":");

        if (splitResult.length != 2) {
            throw new RuntimeException();
        }

        return (Integer.valueOf(splitResult[0].trim()) * 60
                + Integer.valueOf(splitResult[1].trim()));
    }

    // Below is for test
    public static void main(String[] args) {

        /*Problem problem = new Problem();

        problem.dealTicketsSheet();*/
    }
}
