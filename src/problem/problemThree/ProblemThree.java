package problem.problemThree;

import problem.component.FlightRecordWithStationType;
import problem.component.Gate;
import problem.component.SolutionVector;
import problem.problemTwo.ProblemTwo;

import java.util.*;

public class ProblemThree extends ProblemTwo {

    private ArrayList<ArrayList<Gate>> gatesSet = new ArrayList<>();
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


    private void initializeSolutionVectorOfEnumerationMethod(
            SolutionVector solutionVector, int seed, int passengerNumbersThreshold) {

        //j;lgasfasg
    }

    private void adjustCurrentSolutionVector(SolutionVector solutionVector) {

        arrangeCurrentTypeFlightRecords(this.RTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.NTType, solutionVector);

        arrangeCurrentTypeFlightRecords(this.RUType, solutionVector);

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

    private void enumerationMethod() {

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

            System.out.println(
                    String.valueOf(totalTime) + "  "
                            + String.valueOf(unconflictCount) + "  "
                            + String.valueOf(conflictCount));
        }

        System.out.println(minTime);


    }

    private void mainProcess() {

        initialization();


        System.out.println("haha");
    }

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
    }


    public static void main(String[] args) {

        ProblemThree problem = new ProblemThree();

        problem.mainProcess();
    }
}
