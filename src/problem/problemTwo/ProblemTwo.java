package problem.problemTwo;

import problem.Problem;
import problem.component.FlightRecord;
import problem.component.FlightRecordWithStationType;
import problem.component.PassengerRecord;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;

public class ProblemTwo extends Problem {

    protected ArrayList<FlightRecordWithStationType> flightRecordArrayList
            = new ArrayList<>();
    protected ArrayList<PassengerRecord> passengerRecordArrayList
            = new ArrayList<>();

    protected FlightRecordWithStationType[] flightRecordArray
            = new FlightRecordWithStationType[243];

    protected void readOriginalData(
            ArrayList<ArrayList<String>> originalPucksData,
            ArrayList<ArrayList<String>> originalTicketsData) {

        readOriginalPucksData(originalPucksData);
        readOriginalTicketsData(originalTicketsData);
    }

    protected void readOriginalPucksData(
            ArrayList<ArrayList<String>> originalPucksData) {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucksForProgram.xls",
                0, true,
                1, 243,
                0, -1, originalPucksData);
    }

    protected void readOriginalTicketsData(
            ArrayList<ArrayList<String>> originalTicketsData) {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTicketsForProgram.xls",
                0, true,
                1, 659,
                0, -1, originalTicketsData);
    }

    protected void generateFlightRecords(
            ArrayList<ArrayList<String>> originalPucksData) {

        for (int i = 0; i < originalPucksData.size(); i++) {

            ArrayList<String> currentRecord = originalPucksData.get(i);

            int id = Integer.valueOf(currentRecord.get(11));

            String arrivalDate = currentRecord.get(7).split(" +")[0];
            String arrivalTime = currentRecord.get(7).split(" +")[1];
            int arrival = convertDateToMinute(arrivalDate)
                    + convertTimeToMinute(arrivalTime);

            String leftDate = currentRecord.get(8).split(" +")[0];
            String leftTime = currentRecord.get(8).split(" +")[1];
            int left = convertDateToMinute(leftDate)
                    + convertTimeToMinute(leftTime);

            String arrivalFlightName = "";
            String leftFlightName = "";

            String arrivalType = currentRecord.get(0);
            String leftType = currentRecord.get(2);
            String planeType = currentRecord.get(1);

            String stationType;
            String appearFlag = currentRecord.get(9);
            if (appearFlag.equals("n")) {
                stationType = "R";
            } else if (appearFlag.equals("r")) {
                String stationTypeInSheet = currentRecord.get(10);
                if (stationTypeInSheet.equals("T")) {
                    stationType = "T";
                } else {
                    stationType = "U";
                }
            } else {
                throw new RuntimeException();
            }

            FlightRecordWithStationType flightRecord
                    = new FlightRecordWithStationType(
                    id, arrival, left, arrivalFlightName, leftFlightName,
                    arrivalType, leftType, planeType, stationType);

            this.flightRecordArrayList.add(flightRecord);
        }
    }

    protected void generatePassengerRecords(
            ArrayList<ArrayList<String>> originalTicketsData) {

        for (int i = 0; i < originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = originalTicketsData.get(i);

            int passengerNumbers = Integer.valueOf(currentRecord.get(0));
            String arrivalFlightType = currentRecord.get(1);
            String leftFlightType = currentRecord.get(2);
            int arrivalFlightId = Integer.valueOf(currentRecord.get(3));
            int leftFlightId = Integer.valueOf(currentRecord.get(4));

            PassengerRecord passengerRecord = new PassengerRecord(
                    passengerNumbers, arrivalFlightType, leftFlightType,
                    arrivalFlightId, leftFlightId);

            this.passengerRecordArrayList.add(passengerRecord);
        }

    }

    protected void generateRecords(
            ArrayList<ArrayList<String>> originalPucksData,
            ArrayList<ArrayList<String>> originalTicketsData) {

        generateFlightRecords(originalPucksData);

        generatePassengerRecords(originalTicketsData);

        generateFlightRecordArray(originalPucksData);
    }

    protected void generateFlightRecordArray(
            ArrayList<ArrayList<String>> originalPucksData) {

        if (this.flightRecordArrayList.isEmpty()) {
            throw new RuntimeException();
        }

        for (int i = 0; i < this.flightRecordArrayList.size(); i++) {

            FlightRecordWithStationType flightRecord
                    = this.flightRecordArrayList.get(i);

            int id = flightRecord.getId();

            this.flightRecordArray[id] = flightRecord;
        }
    }

    private void mainProcess() {

        ArrayList<ArrayList<String>> originalPucksData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();

        readOriginalData(originalPucksData, originalTicketsData);

        generateRecords(originalPucksData, originalTicketsData);

        System.out.println("haha");
    }

    public static void main(String[] args) {

        ProblemTwo problemTwo = new ProblemTwo();

        problemTwo.mainProcess();

    }
}
