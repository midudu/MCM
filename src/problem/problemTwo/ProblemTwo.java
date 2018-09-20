package problem.problemTwo;

import problem.Problem;
import problem.component.*;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.*;

/**
 * This class is to solve the problem two.
 */
public class ProblemTwo extends Problem {

    /* The number of the flights which cannot be arranged */
    protected int conflictCount = 0;

    /* A HashSet to store the conflict records */
    protected HashSet<FlightRecordWithStationType> conflictRecord = new HashSet<>();

    /* A HashSet to store the id of the conflict records */
    protected HashSet<Integer> conflictId = new HashSet<>();

    /* An ArrayList to store all the flight records */
    protected ArrayList<FlightRecordWithStationType> flightRecordArrayList
            = new ArrayList<>();

    /* An ArrayList to store all the passenger records */
    protected ArrayList<PassengerRecord> passengerRecordArrayList
            = new ArrayList<>();

    /* An ArrayList to store all the boarding gate records */
    protected ArrayList<Gate> gatesArrayList
            = new ArrayList<>();

    /* An array to store all the flight records. The index in the array is the
    id of the flight */
    protected FlightRecordWithStationType[] flightRecordArray
            = new FlightRecordWithStationType[243];

    /* An array to store all the gate records. The index in the array is the
    id of the gate */
    protected Gate[] gatesArray = new Gate[70];

    /* Four TreeSet to store the different types of the flight records */
    protected TreeSet<FlightRecordWithStationType> RTType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> RUType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> NTType = new TreeSet<>();
    protected TreeSet<FlightRecordWithStationType> NUType = new TreeSet<>();

    /* An ArrayList to store the flight id and its relative numbers of passengers */
    private ArrayList<ArrayList<Integer>> rouletteElement = new ArrayList<>();

    /* The total value of the number of relative passengers */
    private int totalValueOfRoullete = 0;

    /* The last changed id of the flight record during the generation of a new
     solution in Simulated Annealing Method */
    private int lastChangedFlightId = -1;

    /* A HashMap to store the corresponding relationship between the boarding
    gate type and the index in {@code gatesSet} */
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

    /* An ArrayList to store all the boarding gates by their type */
    private ArrayList<ArrayList<Gate>> gatesSet = new ArrayList<>();


    /**
     * To add the flight id and its relative number of passengers into
     * {@code flightRecordArrayList} for subsequent Roulette.
     */
    private void generateRouletteElement() {

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

    /**
     * To find a flight id which is to be changed in the solution vector with
     * Roulette method.
     *
     * @return the flight id which is to be changed in the solution vector
     */
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

    /**
     * To generate a new solution vector according to the current solution vector
     *
     * @param solutionVector the current solution vector
     * @return a new solution vector generated from the current solution vector
     */
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

    /**
     * Get the initial solution from the existing result.
     *
     * @param solutionVector the initial solution vector
     */
    private void initializeSolutionVector(SolutionVector solutionVector) {

        ArrayList<ArrayList<String>> resultOfProblemTwo
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "./resultOfProblemTwo/resultOfProblemTwoForProgram.xls",
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

    /**
     * Read original data from file.
     *
     * @param originalPucksData   the original data of flight records
     * @param originalTicketsData the original data of passenger records
     * @param originalGatesData   the original data of the boarding gates
     */
    protected void readOriginalData(
            ArrayList<ArrayList<String>> originalPucksData,
            ArrayList<ArrayList<String>> originalTicketsData,
            ArrayList<ArrayList<String>> originalGatesData) {

        readOriginalPucksData(originalPucksData);
        readOriginalTicketsData(originalTicketsData);
        readOriginalGatesDate(originalGatesData);
    }

    /**
     * To read the original data of the flight records
     *
     * @param originalPucksData the original data of the flight records
     */
    protected void readOriginalPucksData(
            ArrayList<ArrayList<String>> originalPucksData) {

        ExcelReader.importXlsFile(
                "./usefulPucksForProgram.xls",
                0, true,
                1, 243,
                0, -1, originalPucksData);
    }

    /**
     * To read the original data of the passenger records
     *
     * @param originalTicketsData the original data of the passenger records
     */
    protected void readOriginalTicketsData(
            ArrayList<ArrayList<String>> originalTicketsData) {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTicketsForProgram.xls",
                0, true,
                1, 659,
                0, -1, originalTicketsData);
    }

    /**
     * To read the original data of the boarding gates.
     *
     * @param originalGatesData the original data of the boarding gates
     */
    protected void readOriginalGatesDate(
            ArrayList<ArrayList<String>> originalGatesData) {

        ExcelReader.importXlsFile(
                "./resources/InputData_2.xls",
                2, true,
                1, 70,
                0, -1, originalGatesData);
    }

    /**
     * To add the original data of the flight records to
     * {@code flightRecordArrayList}.
     *
     * @param originalPucksData the original data of the flight records
     */
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

    /**
     * To add the original data of the passenger records to
     * {@code passengerRecordArrayList}.
     *
     * @param originalTicketsData the original data of the passenger records
     */
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

    /**
     * To add the original data of the boarding gates to {@code gatesArrayList}.
     *
     * @param originalGatesData the original data of the boarding gates
     */
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

    /**
     * To divide the flight records into four types and add them to different
     * container.
     */
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

    /**
     * To divide the boarding gates into different types and add them to
     * different containers.
     */
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

    /**
     * The interface function of generating records of flights, passengers and
     * gates from the original data.
     *
     * @param originalPucksData   the original data of the flight records
     * @param originalTicketsData the original data of the passenger records
     * @param originalGatesData   the original data of the boarding gates
     */
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

    /**
     * To generate {@code flightRecordArray}. In {@code flightRecordArray}, the
     * index is equal to the id of the flight records.
     */
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

    /**
     * To generate {@code gatesArray}. In {@code gatesArray}, the index is
     * equal to the id of the boarding gates.
     */
    protected void generateGatesArray() {

        for (int i = 0; i < this.gatesArrayList.size(); i++) {

            Gate gate = this.gatesArrayList.get(i);

            int id = gate.getId();

            this.gatesArray[id] = gate;
        }
    }

    /**
     * To do the initialization of the problem.
     */
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

    /**
     * To adjust the current solution vector so that as many flights can be
     * arranged properly as possible.
     *
     * @param solutionVector the current solution vector
     */
    private void adjustCurrentSolutionVector(SolutionVector solutionVector) {

        arrangeCurrentTypeFlightRecords(this.RTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.RUType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.NTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.NUType, solutionVector);
    }

    /**
     * The auxiliary function of {@code adjustCurrentSolutionVector}. The aim
     * is to arrange a specific type of flight records properly and adjust the
     * solution vector if necessary.
     *
     * @param currentTypeFlightRecords a type of flight records set
     * @param solutionVector           the solution vector
     */
    private void arrangeCurrentTypeFlightRecords(
            TreeSet<FlightRecordWithStationType> currentTypeFlightRecords,
            SolutionVector solutionVector) {

        Iterator<FlightRecordWithStationType> iterator
                = currentTypeFlightRecords.iterator();

        while (iterator.hasNext()) {

            FlightRecordWithStationType flightRecord = iterator.next();

            decideCurrentFlightRecordBelongs(flightRecord, solutionVector);
        }
    }

    /**
     * To find a proper boarding gates of the current flight record.
     *
     * @param flightRecord   the current flight record
     * @param solutionVector the solution vector
     */
    private void decideCurrentFlightRecordBelongs(
            FlightRecordWithStationType flightRecord,
            SolutionVector solutionVector) {

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

    /**
     * To find a proper time range for the current flight in the current
     * type of the boarding gates.
     *
     * @param currentTypeGates a type of the boarding gates
     * @param flightRecord     the current flight record
     * @return true if a proper time range can be arranged for the current
     * flight and false for not
     */
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

    /**
     * To calculate the total time used of the procedure time on transferring
     * flights of every passenger.
     *
     * @param passengerRecordArrayList An ArrayList to store all the passenger
     *                                 records
     * @param solutionVector           the solution vector
     * @return the total time
     */
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

    /**
     * The main process of the problem. The method used is Simulated Annealing.
     */
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

    /**
     * To clear the state information before every iteration in Simulated
     * Annealing starts.
     */
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

    /**
     * To export the flights situation in every boarding gates to excel.
     *
     * @param gatesArray  An array to store all the boarding gates
     */
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

    /**
     * To export the flight record which cannot be arranged properly to excel.
     *
     * @param conflictRecord  the conflict flight record
     */
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

    /**
     * The interface function of export results to excel.
     */
    private void exportToExcel() {

        exportGateSituationToExcel(this.gatesArray);
        exportConflictRecordToExcel(this.conflictRecord);
    }

    /**
     * To do the statistic of the number of the flight records which can be
     * arranged properly.
     *
     * @param gatesArray  an Array to store the boarding gates
     * @return the number of the flight records which can be arranged properly
     */
    protected int statisticEffectiveFlightRecords(Gate[] gatesArray) {

        int count = 0;
        for (int i = 1; i < gatesArray.length; i++) {
            Gate gate = gatesArray[i];
            count += gate.getFlightRecords().size();
        }

        return count;
    }


    private void mainProcess() {

        initialization();

        simulatedAnnealing();
    }

    public static void main(String[] args) {

        ProblemTwo problemTwo = new ProblemTwo();

        problemTwo.mainProcess();

        System.out.println("haha");
    }
}
