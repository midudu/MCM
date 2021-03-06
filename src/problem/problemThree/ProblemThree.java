package problem.problemThree;

import problem.component.*;
import problem.problemTwo.ProblemTwo;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.*;

/**
 * This class is to solve the problem three
 */
public class ProblemThree extends ProblemTwo {

    /* An ArrayList to store the flight id and its relative numbers of
    passengers */
    private ArrayList<ArrayList<Integer>> rouletteElement = new ArrayList<>();

    /* The total value of the number of relative passengers */
    private int totalValueOfRoullete = 0;

    /* The last changed id of the flight record during the generation of a new
     solution in Simulated Annealing Method */
    private int lastChangedFlightId = -1;

    /* An ArrayList to store all the boarding gates by their type */
    private ArrayList<ArrayList<Gate>> gatesSet = new ArrayList<>();

    /* A HashMap to store the corresponding relationship between the boarding
    gate type and the index in {@code gatesSet} */
    private HashMap<String, Integer> gatesTypeIndex = new HashMap<>();

    {
        gatesTypeIndex.put("IIWTN", 0);
        gatesTypeIndex.put("IIWTC", 1);
        gatesTypeIndex.put("IIWTS", 2);

        gatesTypeIndex.put("IIWSN", 3);
        gatesTypeIndex.put("IIWSC", 4);
        gatesTypeIndex.put("IIWSS", 5);
        gatesTypeIndex.put("IIWSE", 6);

        gatesTypeIndex.put("IINTN", 7);
        gatesTypeIndex.put("IINTC", 8);
        gatesTypeIndex.put("IINTS", 9);

        gatesTypeIndex.put("IINSN", 10);
        gatesTypeIndex.put("IINSC", 11);
        gatesTypeIndex.put("IINSS", 12);
        gatesTypeIndex.put("IINSE", 13);

        gatesTypeIndex.put("IDWTN", 14);
        gatesTypeIndex.put("IDWTC", 15);
        gatesTypeIndex.put("IDWTS", 16);

        gatesTypeIndex.put("IDWSN", 17);
        gatesTypeIndex.put("IDWSC", 18);
        gatesTypeIndex.put("IDWSS", 19);
        gatesTypeIndex.put("IDWSE", 20);

        gatesTypeIndex.put("IDNTN", 21);
        gatesTypeIndex.put("IDNTC", 22);
        gatesTypeIndex.put("IDNTS", 23);

        gatesTypeIndex.put("IDNSN", 24);
        gatesTypeIndex.put("IDNSC", 25);
        gatesTypeIndex.put("IDNSS", 26);
        gatesTypeIndex.put("IDNSE", 27);

        gatesTypeIndex.put("DIWTN", 28);
        gatesTypeIndex.put("DIWTC", 29);
        gatesTypeIndex.put("DIWTS", 30);

        gatesTypeIndex.put("DIWSN", 31);
        gatesTypeIndex.put("DIWSC", 32);
        gatesTypeIndex.put("DIWSS", 33);
        gatesTypeIndex.put("DIWSE", 34);

        gatesTypeIndex.put("DINTN", 35);
        gatesTypeIndex.put("DINTC", 36);
        gatesTypeIndex.put("DINTS", 37);

        gatesTypeIndex.put("DINSN", 38);
        gatesTypeIndex.put("DINSC", 39);
        gatesTypeIndex.put("DINSS", 40);
        gatesTypeIndex.put("DINSE", 41);

        gatesTypeIndex.put("DDWTN", 42);
        gatesTypeIndex.put("DDWTC", 43);
        gatesTypeIndex.put("DDWTS", 44);

        gatesTypeIndex.put("DDWSN", 45);
        gatesTypeIndex.put("DDWSC", 46);
        gatesTypeIndex.put("DDWSS", 47);
        gatesTypeIndex.put("DDWSE", 48);

        gatesTypeIndex.put("DDNTN", 49);
        gatesTypeIndex.put("DDNTC", 50);
        gatesTypeIndex.put("DDNTS", 51);

        gatesTypeIndex.put("DDNSN", 52);
        gatesTypeIndex.put("DDNSC", 53);
        gatesTypeIndex.put("DDNSS", 54);
        gatesTypeIndex.put("DDNSE", 55);
    }


    /* A HashMap to store the backup boarding gates if the current boarding
    gates cannot arrange the flight */
    private HashMap<String, ArrayList<String>> backupGates = new HashMap<>();

    {
        ArrayList<String> arrayList11 = new ArrayList<>();
        arrayList11.add("IIWTS");
        arrayList11.add("IIWSE");
        backupGates.put("IIWTN", arrayList11);

        ArrayList<String> arrayList12 = new ArrayList<>();
        arrayList12.add("IIWTN");
        arrayList12.add("IIWSE");
        backupGates.put("IIWTS", arrayList12);

        ArrayList<String> arrayList13 = new ArrayList<>();
        arrayList13.add("IIWTN");
        arrayList13.add("IIWTS");
        backupGates.put("IIWSE", arrayList13);

        ArrayList<String> arrayList21 = new ArrayList<>();
        arrayList21.add("DDNSC");
        arrayList21.add("DDNSN");
        arrayList21.add("DDNSS");
        backupGates.put("DDNTC", arrayList21);

        ArrayList<String> arrayList22 = new ArrayList<>();
        arrayList22.add("DDNSC");
        arrayList22.add("DDNSS");
        arrayList22.add("DDNTC");
        backupGates.put("DDNSN", arrayList22);

        ArrayList<String> arrayList23 = new ArrayList<>();
        arrayList23.add("DDNSN");
        arrayList23.add("DDNSS");
        arrayList23.add("DDNTC");
        backupGates.put("DDNSC", arrayList23);

        ArrayList<String> arrayList24 = new ArrayList<>();
        arrayList24.add("DDNSC");
        arrayList24.add("DDNSN");
        arrayList24.add("DDNTC");
        backupGates.put("DDNSS", arrayList24);

        String[][] strings = {{"IINTN", "IINSC"}, {"IDWTN", "IDWTS"},
                {"IDNTN", "IDNTS"}, {"DIWTN", "DIWTS"}, {"DINTN", "DINTS"}};

        for (int i = 0; i < strings.length; i++) {

            ArrayList<String> arrayList1 = new ArrayList<>();
            arrayList1.add(strings[i][1]);
            backupGates.put(strings[i][0], arrayList1);

            ArrayList<String> arrayList2 = new ArrayList<>();
            arrayList2.add(strings[i][0]);
            backupGates.put(strings[i][1], arrayList2);
        }
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
     * The interface function of export results to excel.
     */
    private void exportToExcel() {

        exportGateSituationToExcel(this.gatesArray);
        exportConflictRecordToExcel(this.conflictRecord);
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

        ExcelWriter.exportXlsFile("resultOfProblemThree.xls",
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

        ExcelWriter.exportXlsFile("conflictOfProblemThree.xls",
                result, 0, null, 0, 0);
    }

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
            TreeSet<FlightRecordWithStationType> currentTypeFlightRecords
            , SolutionVector solutionVector) {

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
            FlightRecordWithStationType flightRecord, SolutionVector solutionVector) {

        String arrivalType = flightRecord.getArrivalType();
        String leftType = flightRecord.getLeftType();
        String planeType = flightRecord.getPlaneType();
        int id = flightRecord.getId();
        String targetStationType = solutionVector.get(id - 1);

        String key = arrivalType + leftType + planeType + targetStationType;

        ArrayList<Gate> currentTypeGates = this.gatesSet.get(gatesTypeIndex.get(key));
        boolean arrangeResult = arrangeOnCurrentTypeOfGates(currentTypeGates, flightRecord);

        if (!arrangeResult) {

            if (this.backupGates.containsKey(key)) {

                ArrayList<String> backupGatesName = this.backupGates.get(key);
                for (String backupGateName : backupGatesName) {

                    ArrayList<Gate> currentTypeOfBackupGates
                            = this.gatesSet.get(gatesTypeIndex.get(backupGateName));
                    boolean backupArrangeResult
                            = arrangeOnCurrentTypeOfGates(currentTypeOfBackupGates,
                            flightRecord);
                    if (backupArrangeResult) {

                        solutionVector.set(id - 1,
                                key.substring(key.length() - 2, key.length()));

                        return;
                    }
                }

                if (!conflictId.contains(flightRecord.getId())) {
                    conflictCount++;
                    conflictId.add(flightRecord.getId());
                    conflictRecord.add(flightRecord);
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

            String key1 = arrivalFlightType + leftFlightType
                    + arrivalFlightStationType.substring(0, 1)
                    + leftFlightStationType.substring(0, 1);
            if (!Constant.minProcedureTime.containsKey(key1)) {
                throw new RuntimeException();
            }
            if (!Constant.mrtCount.containsKey(key1)) {
                throw new RuntimeException();
            }

            String key2 = arrivalFlightStationType + leftFlightStationType;
            if (!Constant.walkingTime.containsKey(key2)) {
                throw new RuntimeException();
            }

            int currentTime = Constant.minProcedureTime.get(key1)
                    + Constant.mrtCount.get(key1) * Constant.mrtTime
                    + Constant.walkingTime.get(key2);

            totalTime += (currentTime * passengerNumbers);
        }

        return totalTime;
    }

    /**
     * Get the initial solution from the existing result.
     *
     * @param solutionVector the initial solution vector
     */
    private void initializeSolutionVectorFromProblemThree(
            SolutionVector solutionVector) {

        ArrayList<ArrayList<String>> resultOfProblemTwo
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemTwo\\resultOfProblemTwoForProgram.xls",
                0, true,
                0, 69,
                0, -1, resultOfProblemTwo);

        for (int i = 0; i < solutionVector.size(); i++) {

            solutionVector.set(i, "TN");
        }

        for (int i = 0; i < resultOfProblemTwo.size(); i++) {
            ArrayList<String> currentRecord = resultOfProblemTwo.get(i);

            int gateId = Integer.valueOf(currentRecord.get(0));
            String hallType = this.gatesArray[gateId].getHallType();
            String location = this.gatesArray[gateId].getLocation().substring(0, 1);
            String value = hallType + location;

            for (int j = 1; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                int flightId = Integer.valueOf(currentRecord.get(j));
                solutionVector.set(flightId - 1, value);
            }
        }
    }

    /**
     * To generate new solution vectors according to the current solution vector
     *
     * @param solutionVector the current solution vector
     * @return new solutions vector generated from the current solution vector
     */
    private SolutionVector[] generateNewSolutionVectors(
            SolutionVector solutionVector) {

        int toBeChangedFlightId = roullete();
        while (toBeChangedFlightId == lastChangedFlightId) {
            toBeChangedFlightId = roullete();
        }
        lastChangedFlightId = toBeChangedFlightId;

        FlightRecordWithStationType flightRecord
                = this.flightRecordArray[toBeChangedFlightId];
        String arrivalType = flightRecord.getArrivalType();
        String leftType = flightRecord.getLeftType();
        String planeType = flightRecord.getPlaneType();
        String key = arrivalType + leftType + planeType
                + solutionVector.get(toBeChangedFlightId - 1);
        if (!this.backupGates.containsKey(key)) {
            throw new RuntimeException();
        }

        ArrayList<String> backupGatesNames = this.backupGates.get(key);

        SolutionVector[] variationResult
                = new SolutionVector[backupGatesNames.size()];

        for (int i = 0; i < backupGatesNames.size(); i++) {

            String newValue = backupGatesNames.get(i).substring(
                    backupGatesNames.get(i).length() - 2,
                    backupGatesNames.get(i).length());

            SolutionVector newSolutionVector = solutionVector.cloneSolutionVector();
            newSolutionVector.set(toBeChangedFlightId - 1, newValue);
            variationResult[i] = newSolutionVector;
        }

        return variationResult;
    }

    /**
     * The main process of the problem. The method used is Simulated Annealing.
     */
    private void simulatedAnnealingMethod() {

        System.out.println("Calculating...");

        int acceptCount = 0;

        SolutionVector solutionVector
                = new SolutionVector(this.flightRecordArray.length - 1);

        initializeSolutionVectorFromProblemThree(solutionVector);

        int minTime = 84434;
        int finalConflictCount = -1;
        int finalUnconflictCount = -1;

        double originalTemperature = 97.0;
        double finalTemperature = 1.0;
        double descendingCoefficient = 0.99999;
        double temperatureCoefficient = 2.0;

        double currentTemperature = originalTemperature;

        while (currentTemperature > finalTemperature) {

            SolutionVector[] solutionVectors =
                    generateNewSolutionVectors(solutionVector);

            for (int i = 0; i < solutionVectors.length; i++) {

                clear();

                SolutionVector currentSolutionVector
                        = solutionVectors[i];

                adjustCurrentSolutionVector(currentSolutionVector);

                int totalTime = calculateTotalTimeOfPassengersProcedure(
                        passengerRecordArrayList, currentSolutionVector);

                int unconflictCount = statisticEffectiveFlightRecords(this.gatesArray);
                int conflictCount = this.conflictCount;

                if (totalTime < minTime) {

                    minTime = totalTime;
                    finalConflictCount = conflictCount;
                    finalUnconflictCount = unconflictCount;

                    exportToExcel();
                    solutionVector = currentSolutionVector;

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
                        solutionVector = currentSolutionVector;

                        acceptCount++;
                    }
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

    private void mainProcess() {

        initialization();

        simulatedAnnealingMethod();


        System.out.println("haha");
    }

    /**
     * To divide the boarding gates into different types and add them to
     * different containers.
     */
    private void initGatesSet() {

        for (int i = 0; i < 56; i++) {
            ArrayList<Gate> gateArrayList = new ArrayList<>();
            this.gatesSet.add(gateArrayList);
        }

        for (int id = 1; id < this.gatesArray.length; id++) {

            Gate gate = this.gatesArray[id];

            String arrivalType = gate.getArrivalType();
            String leftType = gate.getLeftType();
            String planeType = gate.getPlaneType();
            String hallType = gate.getHallType();
            String location = gate.getLocation();
            location = location.substring(0, 1);

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

            String key = arrivalType + leftType + planeType + hallType + location;

            if (!this.gatesTypeIndex.containsKey(key)) {
                throw new RuntimeException();
            }

            int index = this.gatesTypeIndex.get(key);

            this.gatesSet.get(index).add(gate);
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


    public static void main(String[] args) {

        ProblemThree problem = new ProblemThree();

        problem.mainProcess();
    }
}
