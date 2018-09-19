package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;
import util.ioUtil.excel.ExcelWriter;

import java.util.*;

// This class is to solve problem one
public class ProblemOne extends Problem {

    /* The number of the flights which cannot be arranged */
    private int conflictCount = 0;

    /* A HashSet to store the conflict records */
    private HashSet<FlightRecord> conflictRecord = new HashSet<>();

    /* An ArrayList to store all the boarding gates */
    private ArrayList<ArrayList<LinkedList<FlightRecord>>> gates
            = new ArrayList<>();

    /**
     * To divide the boarding gates into 8 types and put them into {@code gates}
     */
    private void initArrayListQueue() {

        ArrayList<LinkedList<FlightRecord>> IIW = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            IIW.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> IIN = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            IIN.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> DDW = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            DDW.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> DDN = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            DDN.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> DIW = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            DIW.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> DIN = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            DIN.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> IDW = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            IDW.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> IDN = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            IDN.add(queue);
        }

        this.gates.add(IIW);
        this.gates.add(IIN);
        this.gates.add(IDW);
        this.gates.add(IDN);
        this.gates.add(DIW);
        this.gates.add(DIN);
        this.gates.add(DDW);
        this.gates.add(DDN);
    }

    /**
     * The main function of the problem one
     */
    private void getResultWithQueueMethod() {

        this.getFlightRecordArrayListFromExcel();

        TreeSet<FlightRecord> flightRecordTreeSet
                = new TreeSet<>(this.flightRecordArrayList);

        initArrayListQueue();

        Iterator<FlightRecord> iterator = flightRecordTreeSet.iterator();


        while (iterator.hasNext()) {

            FlightRecord flightRecord = iterator.next();


            decideCurrentFlightRecordBelongs(flightRecord);
        }
    }

    /**
     * To find a proper boarding gate for the current flight record and add it.
     *
     * @param flightRecord  the current flight record
     */
    private void decideCurrentFlightRecordBelongs(FlightRecord flightRecord) {

        String arrivalType = flightRecord.getArrivalType();
        String leftType = flightRecord.getLeftType();
        String planeType = flightRecord.getPlaneType();
        int leftTime = flightRecord.getLeftTime();

        int belongingIndex = calculateFlightBelongingIndex(
                arrivalType, leftType, planeType);

        if (belongingIndex < 0 || belongingIndex > 7) {
            throw new RuntimeException();
        }

        ArrayList<LinkedList<FlightRecord>> currentTypeGates
                = this.gates.get(belongingIndex);

        int gateIndex = -1;
        int maxExistingFlightNumbers = 0;
        for (int i = 0; i < currentTypeGates.size(); i++) {

            LinkedList<FlightRecord> currentGate = currentTypeGates.get(i);
            if (currentGate.isEmpty()) {
                gateIndex = i;
            } else {
                int lastFlightLeftTime =
                        currentGate.getLast().getLeftTime();
                if (lastFlightLeftTime + 75 <= leftTime) {
                    int existingFlightNumbers = currentGate.size();
                    if (existingFlightNumbers > maxExistingFlightNumbers) {
                        maxExistingFlightNumbers = existingFlightNumbers;
                        gateIndex = i;
                    }
                }
            }
        }


        if (gateIndex == -1) {
            this.conflictCount++;
            this.conflictRecord.add(flightRecord);

        } else {
            currentTypeGates.get(gateIndex).add(flightRecord);
        }
    }

    /**
     * To calculate the index of the gates in {@code gates} which the current
     * flight record belongs to.
     *
     * @param arrivalType  the arrival type of the flight
     * @param leftType  the left type of the flight
     * @param planeType  the plane type of the flight
     * @return
     */
    private int calculateFlightBelongingIndex(
            String arrivalType, String leftType, String planeType) {

        int result = 0;

        if (arrivalType.equals("I")) {
            result += 0;
        } else if (arrivalType.equals("D")) {
            result += 4;
        } else {
            throw new RuntimeException();
        }

        if (leftType.equals("I")) {
            result += 0;
        } else if (leftType.equals("D")) {
            result += 2;
        } else {
            throw new RuntimeException();
        }

        if (planeType.equals("W")) {
            result += 0;
        } else if (planeType.equals("N")) {
            result += 1;
        } else {
            throw new RuntimeException();
        }

        return result;
    }

    /**
     * To export to arrange result to excel.
     */
    private void exportFinalResultToExcel() {

        ArrayList<ArrayList<String>> finalResult
                = new ArrayList<>();

        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(0,"IIW");
        hashMap.put(1,"IIN");
        hashMap.put(2,"IDW");
        hashMap.put(3,"IDN");
        hashMap.put(4,"DIW");
        hashMap.put(5,"DIN");
        hashMap.put(6,"DDW");
        hashMap.put(7,"DDN");

        for (int i = 0; i < this.gates.size(); i++) {

            ArrayList<String> gatesType = new ArrayList<>();
            gatesType.add(hashMap.get(i));
            finalResult.add(gatesType);

            ArrayList<LinkedList<FlightRecord>> currentTypeOfGates
                    = this.gates.get(i);

            for (int j = 0; j < currentTypeOfGates.size(); j++) {

                LinkedList<FlightRecord> currentGate
                        = currentTypeOfGates.get(j);

                if (currentGate.isEmpty()) {
                    continue;
                }

                ArrayList<String> currentGateFlights = new ArrayList<>();

                for (int k = 0; k < currentGate.size(); k++) {

                    int currentFlightId = currentGate.get(k).getId();
                    currentGateFlights.add(String.valueOf(currentFlightId));
                }

                finalResult.add(currentGateFlights);
            }
        }

        ExcelWriter.exportXlsFile("resultOfProblemOne.xls",
                finalResult,
                0,null,0,0);
    }

    /**
     * To export the conflict result to excel.
     */
    private void exportConflictResultToExcel() {

        ArrayList<ArrayList<String>> conflictRecordString
                = new ArrayList<>();

        Iterator<FlightRecord> iterator
                = this.conflictRecord.iterator();
        while (iterator.hasNext()) {

            FlightRecord currentConflictRecord
                    = iterator.next();

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(currentConflictRecord.getId()));

            conflictRecordString.add(arrayList);
        }


        ExcelWriter.exportXlsFile("conflictRecordOfProblemOne.xls",
                conflictRecordString,
                0,null,0,0);
    }

    // Below is for test
    public static void main(String[] args) {

        ProblemOne problemOne = new ProblemOne();

        problemOne.getResultWithQueueMethod();

        problemOne.exportFinalResultToExcel();

        problemOne.exportConflictResultToExcel();

        System.out.println("haha");
    }
}
