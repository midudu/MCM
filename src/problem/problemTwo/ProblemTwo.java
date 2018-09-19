package problem.problemTwo;

import problem.Problem;
import problem.component.*;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.*;

// 这个类用于解决问题的第二问
public class ProblemTwo extends Problem {

    protected int conflictCount = 0;
    protected HashSet<FlightRecordWithStationType> conflictRecord = new HashSet<>();
    protected HashSet<Integer> conflictId = new HashSet<>();

    protected ArrayList<FlightRecordWithStationType> flightRecordArrayList
            = new ArrayList<>();
    protected ArrayList<PassengerRecord> passengerRecordArrayList
            = new ArrayList<>();
    protected ArrayList<Gate> gatesArrayList
            = new ArrayList<>();

    protected FlightRecordWithStationType[] flightRecordArray
            = new FlightRecordWithStationType[243];

    protected Gate[] gatesArray = new Gate[70];

    protected TreeSet<FlightRecordWithStationType> RTType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> RUType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> NTType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> NUType = new TreeSet<>();

    private HashMap<String, Integer> gatesTypeIndex = new HashMap<>();

    private ArrayList<ArrayList<Integer>> rouletteElement = new ArrayList<>();
    private int totalValueOfRoullete = 0;

    private int lastChangedFlightId = -1;

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

    private ArrayList<ArrayList<Gate>> gatesSetOfMinimumTime;

    private SolutionVector solutionVectorOfTOrS
            = new SolutionVector(flightRecordArray.length - 1);

    private SolutionVector bestSolutionVectorOfTOrS;

    private void generateRouletteElement(){

        for (int i = 0; i < this.flightRecordArrayList.size(); i++) {

            FlightRecordWithStationType flightRecord
                    = this.flightRecordArrayList.get(i);

            String stationType = flightRecord.getStationType();
            if (stationType.contains("N") || stationType.contains("T")) {
                continue;
            }

            int id = flightRecord.getId();
            int relativePassengers = flightRecord.getRelativePassengers();

            ArrayList<Integer> currentElement = new ArrayList<>();
            currentElement.add(id);
            currentElement.add(relativePassengers);

            this.totalValueOfRoullete += relativePassengers;

            this.rouletteElement.add(currentElement);
        }
    }

    private int roullete() {

        double randomValue = new Random().nextDouble() * this.totalValueOfRoullete;

        int sum = 0;
        for (int i = 0; i < this.rouletteElement.size(); i++) {

            sum += this.rouletteElement.get(i).get(1);

            if (sum >= randomValue) {
                return this.rouletteElement.get(i).get(0);
            }
        }

        return -1;
    }

    private SolutionVector generateNewSolutionVector(SolutionVector solutionVector) {

        int toBeChangedFlightId = roullete();
        while (toBeChangedFlightId == lastChangedFlightId) {
            toBeChangedFlightId = roullete();
        }
        lastChangedFlightId = toBeChangedFlightId;

        String oldValue = solutionVector.get(toBeChangedFlightId - 1);
        String newValue = (oldValue.equals("T") ? "S" : "T");

        SolutionVector newSolutionVector = solutionVector.cloneSolutionVector();
        newSolutionVector.set(toBeChangedFlightId - 1, newValue);

        return newSolutionVector;
    }

    private void initializeSolutionVector(SolutionVector solutionVector) {

        ArrayList<ArrayList<String>> resultOfProblemTwo
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemTwo\\resultOfProblemTwoForProgram.xls",
                0, true,
                0, 69,
                0, -1, resultOfProblemTwo);

        for (int i = 0; i < solutionVector.size(); i++) {

            solutionVector.set(i, "T");
        }

        for (int i = 0; i < resultOfProblemTwo.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemTwo.get(i);

            int gateId = Integer.valueOf(currentRecord.get(0));
            String hallType = this.gatesArray[gateId].getHallType();

            for (int j = 1; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                int flightId = Integer.valueOf(currentRecord.get(j));
                solutionVector.set(flightId - 1, hallType);
            }
        }
    }

    protected ArrayList<ArrayList<Gate>> cloneGatesSet(
            ArrayList<ArrayList<Gate>> gatesSet) {

        ArrayList<ArrayList<Gate>> result = new ArrayList<>();

        for (int i = 0; i < gatesSet.size(); i++) {

            ArrayList<Gate> newGateArrayList = new ArrayList<>();

            ArrayList<Gate> currentGateArrayList = gatesSet.get(i);

            for (int j = 0; j < currentGateArrayList.size(); j++) {

                Gate currentGate = currentGateArrayList.get(j);
                Gate newGate = new Gate(
                        currentGate.getId(), currentGate.getHallType(),
                        currentGate.getLocation(), currentGate.getArrivalType(),
                        currentGate.getLeftType(), currentGate.getPlaneType());

                newGateArrayList.add(newGate);
            }

            result.add(newGateArrayList);
        }

        return result;
    }

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

            int relativePassengers = Integer.valueOf(currentRecord.get(12).trim());

            FlightRecordWithStationType flightRecord
                    = new FlightRecordWithStationType(
                    id, arrival, left, arrivalFlightName, leftFlightName,
                    arrivalType, leftType, planeType, stationType, relativePassengers);

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

    private void initGatesSet() {

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
                } else {
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

    private void initialization() {

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

        generateRouletteElement();
    }

    private void adjustCurrentSolutionVector(SolutionVector solutionVector) {

        arrangeCurrentTypeFlightRecords(this.RTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.RUType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.NTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.NUType, solutionVector);
    }

    private void arrangeCurrentTypeFlightRecords(
            TreeSet<FlightRecordWithStationType> currentTypeFlightRecords
            , SolutionVector solutionVector) {

        Iterator<FlightRecordWithStationType> iterator
                = currentTypeFlightRecords.iterator();

        while (iterator.hasNext()) {

            FlightRecordWithStationType flightRecord = iterator.next();

            decideCurrentFlightRecordBelongs(flightRecord, solutionVector);
        }
    }

    private void decideCurrentFlightRecordBelongs(
            FlightRecordWithStationType flightRecord, SolutionVector solutionVector) {

        String stationType = flightRecord.getStationType();

        String arrivalType = flightRecord.getArrivalType();
        String leftType = flightRecord.getLeftType();
        String planeType = flightRecord.getPlaneType();
        int id = flightRecord.getId();
        String targetStationType = solutionVector.get(id - 1);

        String key = arrivalType + leftType + planeType + targetStationType;

        ArrayList<Gate> currentTypeGates = this.gatesSet.get(gatesTypeIndex.get(key));

        boolean arrangeResult = arrangeOnCurrentTypeOfGates(currentTypeGates, flightRecord);
        if (!arrangeResult) {

            if (stationType.contains("U")) {

                targetStationType = (targetStationType.equals("T") ? "S" : "T");
                solutionVector.set(id - 1,
                        (targetStationType.equals("T") ? "S" : "T"));

                key = arrivalType + leftType + planeType + targetStationType;
                currentTypeGates = this.gatesSet.get(gatesTypeIndex.get(key));
                arrangeResult = arrangeOnCurrentTypeOfGates(
                        currentTypeGates, flightRecord);

                if (!arrangeResult) {
                    if (!conflictId.contains(flightRecord.getId())) {
                        conflictCount++;
                        conflictId.add(flightRecord.getId());
                        conflictRecord.add(flightRecord);
                    }
                }
            } else {
                if (!conflictId.contains(flightRecord.getId())) {
                    conflictCount++;
                    conflictId.add(flightRecord.getId());
                    conflictRecord.add(flightRecord);
                }
            }
        }
    }

    protected boolean arrangeOnCurrentTypeOfGates(
            ArrayList<Gate> currentTypeGates,
            FlightRecordWithStationType flightRecord) {

        if (currentTypeGates.isEmpty()) {
            return false;
        }

        int leftTime = flightRecord.getLeftTime();

        int gateIndex = -1;
        for (int i = 0; i < currentTypeGates.size(); i++) {

            Gate currentGate = currentTypeGates.get(i);
            LinkedList<FlightRecordWithStationType> flightRecordLinkedList
                    = currentGate.getFlightRecords();

            if (flightRecordLinkedList.isEmpty()) {
                gateIndex = i;
            } else {
                if (leftTime >= flightRecordLinkedList.getLast().getLeftTime() + 75) {
                    flightRecordLinkedList.addLast(flightRecord);
                    return true;
                }
                int lastLeftTime = 0;
                for (int j = 0; j < flightRecordLinkedList.size(); j++) {

                    int currentLeftTime = flightRecordLinkedList.get(j).getLeftTime();
                    if (leftTime >= lastLeftTime + 75
                            && leftTime <= currentLeftTime - 75) {

                        flightRecordLinkedList.add(j, flightRecord);
                        return true;
                    }
                    lastLeftTime = currentLeftTime;
                }
            }
        }

        if (gateIndex == -1) {
            return false;
        } else {
            currentTypeGates.get(gateIndex).getFlightRecords().add(flightRecord);
            return true;
        }
    }

    private int calculateTotalTimeOfPassengersProcedure(
            ArrayList<PassengerRecord> passengerRecordArrayList,
            SolutionVector solutionVector) {

        int totalTime = 0;

        for (int i = 0; i < passengerRecordArrayList.size(); i++) {

            PassengerRecord passengerRecord = passengerRecordArrayList.get(i);

            String arrivalFlightType = passengerRecord.getArrivalFlightType();
            String leftFlightType = passengerRecord.getLeftFlightType();
            int arrivalFlightId = passengerRecord.getArrivalFlightId();
            int leftFlightId = passengerRecord.getLeftFlightId();
            int passengerNumbers = passengerRecord.getPassengerNumbers();


            if (conflictId.contains(arrivalFlightId)
                    || conflictId.contains(leftFlightId)) {
                continue;
            }

            String arrivalFlightStationType = solutionVector.get(arrivalFlightId - 1);
            String leftFlightStationType = solutionVector.get(leftFlightId - 1);

            String key = arrivalFlightType + leftFlightType + arrivalFlightStationType
                    + leftFlightStationType;

            if (!Constant.minProcedureTime.containsKey(key)) {
                throw new RuntimeException();
            }

            totalTime += Constant.minProcedureTime.get(key) * passengerNumbers;
        }

        return totalTime;
    }

    private void simulatedAnnealing() {

        System.out.println("Calculating...");

        int acceptCount = 0;

        SolutionVector solutionVector
                = new SolutionVector(this.flightRecordArray.length - 1);

        initializeSolutionVector(solutionVector);

        int minTime = 53905;

        int finalConflictCount = -1;
        int finalUnconflictCount = -1;

        double originalTemperature = 97.0;
        double finalTemperature = 1.0;
        double descendingCoefficient = 0.99999;
        double temperatureCoefficient = 2.0;

        double currentTemperature = originalTemperature;

        while (currentTemperature > finalTemperature) {

            clear();

            SolutionVector newSolutionVector =
                    generateNewSolutionVector(solutionVector);

            adjustCurrentSolutionVector(newSolutionVector);

            int totalTime = calculateTotalTimeOfPassengersProcedure(
                    passengerRecordArrayList, newSolutionVector);

            int unconflictCount = statisticEffectiveFlightRecords(this.gatesArray);
            int conflictCount = this.conflictCount;

            if (totalTime < minTime) {

                minTime = totalTime;
                finalConflictCount = conflictCount;
                finalUnconflictCount = unconflictCount;

                exportToExcel();
                solutionVector = newSolutionVector;

            } else {

                double randomValue = new Random().nextDouble();
                double calculatedValue
                        = Math.exp((minTime - totalTime)
                        * temperatureCoefficient / currentTemperature);

                if (randomValue < calculatedValue) {

                    minTime = totalTime;
                    finalConflictCount = conflictCount;
                    finalUnconflictCount = unconflictCount;

                    exportToExcel();
                    solutionVector = newSolutionVector;

                    acceptCount++;
                }


                /*System.out.println(
                        String.valueOf(totalTime) + "  "
                                + String.valueOf(unconflictCount) + "  "
                                + String.valueOf(conflictCount));*/
            }

            currentTemperature *= descendingCoefficient;
        }

        System.out.println("min time:" + String.valueOf(minTime));
        System.out.println("conflict count:" + String.valueOf(finalConflictCount));
        System.out.println("unconflict count:" + String.valueOf(finalUnconflictCount));
        System.out.println("accept count:" + String.valueOf(acceptCount));
    }

    private void clear() {

        for (int i = 0; i < gatesSet.size(); i++) {

            for (int j = 0; j < gatesSet.get(i).size(); j++) {

                Gate gate = gatesSet.get(i).get(j);

                gate.clear();
            }
        }

        this.conflictId.clear();
        this.conflictRecord.clear();
        this.conflictCount = 0;
    }

    private void initializeSolutionVectorOfEnumerationMethod(
            SolutionVector solutionVector, int seed, int passengerNumbersThreshold) {

        String binarySeed = Integer.toBinaryString(seed);
        int currentSeedIndex = binarySeed.length() - 1;

        String[] map = {"T", "S"};

        for (int i = 1; i < flightRecordArray.length; i++) {

            int id = flightRecordArray[i].getId();

            String stationType = flightRecordArray[i].getStationType();

            if (stationType.contains("T")) {
                solutionVector.set(id - 1, map[0]);
            } else {
                if (stationType.contains("N")) {
                    solutionVector.set(id - 1, map[new Random().nextInt(2)]);
                } else {
                    int relativePassengers
                            = flightRecordArray[i].getRelativePassengers();
                    if (relativePassengers > passengerNumbersThreshold) {

                        if (currentSeedIndex < 0) {
                            solutionVector.set(id - 1, map[0]);
                        } else {
                            solutionVector.set(id - 1,
                                    map[Integer.valueOf(Character.toString(
                                            binarySeed.charAt(currentSeedIndex)))]);

                            currentSeedIndex--;
                        }

                    } else {
                        solutionVector.set(id - 1, map[new Random().nextInt(2)]);
                    }
                }
            }
        }

    }

    private void exportGateSituationToExcel(Gate[] gatesArray) {

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (int i = 1; i < gatesArray.length; i++) {

            ArrayList<String> currentGate = new ArrayList<>();

            Gate gate = gatesArray[i];
            int id = gate.getId();
            currentGate.add(Integer.toString(id));

            LinkedList<FlightRecordWithStationType> flightRecord
                    = gate.getFlightRecords();
            for (int j = 0; j < flightRecord.size(); j++) {
                int flightId = flightRecord.get(j).getId();
                currentGate.add(Integer.toString(flightId));
            }

            result.add(currentGate);
        }

        ExcelWriter.exportXlsFile("resultOfProblemTwo.xls",
                result, 0, null, 0, 0);
    }

    private void exportConflictRecordToExcel(
            HashSet<FlightRecordWithStationType> conflictRecord) {

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        Iterator<FlightRecordWithStationType> iterator = conflictRecord.iterator();

        while (iterator.hasNext()) {

            ArrayList<String> currentRecord = new ArrayList<>();

            FlightRecordWithStationType flightRecord = iterator.next();

            currentRecord.add(Integer.toString(flightRecord.getId()));

            result.add(currentRecord);
        }

        ExcelWriter.exportXlsFile("conflictOfProblemTwo.xls",
                result, 0, null, 0, 0);
    }

    private void exportToExcel() {

        exportGateSituationToExcel(this.gatesArray);
        exportConflictRecordToExcel(this.conflictRecord);
    }

    protected int statisticEffectiveFlightRecords(Gate[] gatesArray) {

        int count = 0;
        for (int i = 1; i < gatesArray.length; i++) {

            Gate gate = gatesArray[i];
            count += gate.getFlightRecords().size();
        }

        return count;
    }


    private void enumerationMethod() {

        long startTime = System.currentTimeMillis();

        SolutionVector solutionVector
                = new SolutionVector(this.flightRecordArray.length - 1);
        int maxSeed = 8388608;
        int minTime = Integer.MAX_VALUE;
        int passengerNumbersThreshold = 39;

        for (int seed = 0; seed < maxSeed; seed++) {

            clear();

            initializeSolutionVectorOfEnumerationMethod(
                    solutionVector, seed, passengerNumbersThreshold);

            adjustCurrentSolutionVector(solutionVector);

            int totalTime = calculateTotalTimeOfPassengersProcedure(
                    passengerRecordArrayList, solutionVector);

            int unconflictCount = statisticEffectiveFlightRecords(this.gatesArray);
            int conflictCount = this.conflictCount;

            if (totalTime < minTime) {

                minTime = totalTime;
                exportToExcel();
            }

            /*System.out.println(
                    String.valueOf(totalTime) + "  "
                            + String.valueOf(unconflictCount) + "  "
                            + String.valueOf(conflictCount));*/
        }

        System.out.println(minTime);

        long endTime = System.currentTimeMillis();
        double min = (endTime - startTime) / 1000.0 / 60.0;

        System.out.println();
        System.out.println("程序运行时间：" + String.valueOf(min) + "min");
    }


    private void mainProcess() {

        initialization();

        //enumerationMethod();
        simulatedAnnealing();
    }

    public static void main(String[] args) {

        ProblemTwo problemTwo = new ProblemTwo();

        problemTwo.mainProcess();

        System.out.println("haha");
    }
}
