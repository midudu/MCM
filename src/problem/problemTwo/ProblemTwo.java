package problem.problemTwo;

import problem.Problem;
import problem.component.FlightRecord;
import problem.component.FlightRecordWithStationType;
import problem.component.Gate;
import problem.component.PassengerRecord;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

public class ProblemTwo extends Problem {

    protected ArrayList<FlightRecordWithStationType> flightRecordArrayList
            = new ArrayList<>();
    protected ArrayList<PassengerRecord> passengerRecordArrayList
            = new ArrayList<>();
    protected ArrayList<Gate> gatesArrayList
            = new ArrayList<>();

    protected FlightRecordWithStationType[] flightRecordArray
            = new FlightRecordWithStationType[243];

    protected Gate[] gatesArray = new Gate[70];

    private TreeSet<FlightRecord> RTType = new TreeSet<>();
    private TreeSet<FlightRecord> RUType = new TreeSet<>();
    private TreeSet<FlightRecord> NTType = new TreeSet<>();
    private TreeSet<FlightRecord> NUType = new TreeSet<>();

    private HashMap<String, Integer> gatesTypeIndex = new HashMap<>();
    {
        gatesTypeIndex.put("IIWT", 0);
        gatesTypeIndex.put("IIWS", 1);
        gatesTypeIndex.put("IINT", 2);
        gatesTypeIndex.put("IINS", 3);
        gatesTypeIndex.put("IDWT", 4);
        gatesTypeIndex.put("IDWS", 5);
        gatesTypeIndex.put("IDNT", 6);
        gatesTypeIndex.put("IDNS", 7);
        gatesTypeIndex.put("DIWT", 8);
        gatesTypeIndex.put("DIWS", 9);
        gatesTypeIndex.put("DINT", 10);
        gatesTypeIndex.put("DINS", 11);
        gatesTypeIndex.put("DDWT", 12);
        gatesTypeIndex.put("DDWS", 13);
        gatesTypeIndex.put("DDNT", 14);
        gatesTypeIndex.put("DDNS", 15);
    }

    private ArrayList<ArrayList<Gate>> gatesSet = new ArrayList<>();

    protected void readOriginalData(
            ArrayList<ArrayList<String>> originalPucksData,
            ArrayList<ArrayList<String>> originalTicketsData,
            ArrayList<ArrayList<String>> originalGatesData) {

        readOriginalPucksData(originalPucksData);
        readOriginalTicketsData(originalTicketsData);
        readOriginalGatesDate(originalGatesData);
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

    protected void readOriginalGatesDate(
            ArrayList<ArrayList<String>> originalGatesData) {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                2, true,
                1, 70,
                0, -1, originalGatesData);
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

            String stationType = "";
            String appearFlag = currentRecord.get(9);
            if (appearFlag.equals("r")) {
                stationType += "R";
            } else {
                stationType += "N";
            }

            String stationTypeInSheet = currentRecord.get(10);
            if (stationTypeInSheet.equals("T")) {
                stationType += "T";
            } else {
                stationType += "U";
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

    protected void generateGates(ArrayList<ArrayList<String>> originalGatesData) {

        for (int i = 0; i < originalGatesData.size(); i++) {

            ArrayList<String> currentRecord = originalGatesData.get(i);

            int id = Integer.valueOf(currentRecord.get(0));
            String hallType = currentRecord.get(2);
            String location = currentRecord.get(3);
            String arrivalType = currentRecord.get(4);
            String leftType = currentRecord.get(5);
            String planeType = currentRecord.get(6);

            Gate gate = new Gate(id, hallType, location, arrivalType,
                    leftType, planeType);

            this.gatesArrayList.add(gate);
        }
    }

    protected void initFlightRecordTreeSets() {

        for (int i = 0; i < this.flightRecordArrayList.size(); i++) {

            FlightRecordWithStationType flightRecord
                    = this.flightRecordArrayList.get(i);

            String stationType = flightRecord.getStationType();

            switch (stationType) {
                case "RT": {
                    this.RTType.add(flightRecord);
                    break;
                }
                case "RU": {
                    this.RUType.add(flightRecord);
                    break;
                }
                case "NT": {
                    this.NTType.add(flightRecord);
                    break;
                }
                case "NU": {
                    this.NUType.add(flightRecord);
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }
        }
    }

    protected void initGatesSet() {

        for (int i = 0; i < 16; i++) {
            ArrayList<Gate> gateArrayList = new ArrayList<>();
            this.gatesSet.add(gateArrayList);
        }

        for (int id = 1; id < this.gatesArray.length; id++) {

            Gate gate = this.gatesArray[id];

            String arrivalType = gate.getArrivalType();
            String leftType = gate.getLeftType();
            String planeType = gate.getPlaneType();
            String hallType = gate.getHallType();

            if (arrivalType.length() == 1 && leftType.length() == 1) {
            } else if (arrivalType.length() != 1 && leftType.length() != 1) {
                int gateID = gate.getId();
                if (gateID < 13) {
                    arrivalType = "D";
                    leftType = "I";
                } else {
                    arrivalType = "I";
                    leftType = "D";
                }
            } else {
                if (arrivalType.length() == 1) {
                    leftType = (arrivalType.equals("D") ? "I" : "D");
                } else  {
                    arrivalType = (leftType.equals("D") ? "I" : "D");
                }
            }

            String key = arrivalType + leftType + planeType + hallType;

            if (!this.gatesTypeIndex.containsKey(key)) {
                throw new RuntimeException();
            }

            int index = this.gatesTypeIndex.get(key);

            this.gatesSet.get(index).add(gate);
        }
    }

    protected void generateRecords(
            ArrayList<ArrayList<String>> originalPucksData,
            ArrayList<ArrayList<String>> originalTicketsData,
            ArrayList<ArrayList<String>> originalGatesData) {

        generateFlightRecords(originalPucksData);

        generatePassengerRecords(originalTicketsData);

        generateFlightRecordArray();

        generateGates(originalGatesData);

        generateGatesArray();
    }

    protected void generateFlightRecordArray() {

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

    protected void generateGatesArray() {

        for (int i = 0; i < this.gatesArrayList.size(); i++) {

            Gate gate = this.gatesArrayList.get(i);

            int id = gate.getId();

            this.gatesArray[id] = gate;
        }
    }

    private void mainProcess() {

        ArrayList<ArrayList<String>> originalPucksData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalGatesData
                = new ArrayList<>();

        readOriginalData(originalPucksData, originalTicketsData, originalGatesData);

        generateRecords(originalPucksData, originalTicketsData, originalGatesData);

        initFlightRecordTreeSets();
        initGatesSet();

        System.out.println("haha");
    }

    public static void main(String[] args) {

        ProblemTwo problemTwo = new ProblemTwo();

        problemTwo.mainProcess();

    }
}
