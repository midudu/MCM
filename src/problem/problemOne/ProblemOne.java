package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;
import util.ioUtil.excel.ExcelWriter;
import util.paintUtil.opencv.DrawTimeSequence;

import java.util.*;

// 这个类用于解决问题的第一问
public class ProblemOne extends Problem {

    private TreeSet<FlightRecord> DDN = new TreeSet<>();
    private TreeSet<FlightRecord> DDW = new TreeSet<>();
    private TreeSet<FlightRecord> DIN = new TreeSet<>();
    private TreeSet<FlightRecord> DIW = new TreeSet<>();
    private TreeSet<FlightRecord> IDN = new TreeSet<>();
    private TreeSet<FlightRecord> IDW = new TreeSet<>();
    private TreeSet<FlightRecord> IIN = new TreeSet<>();
    private TreeSet<FlightRecord> IIW = new TreeSet<>();

    private TreeSet<FlightRecord> DIN_IDN = new TreeSet<>();
    private TreeSet<FlightRecord> DIW_IDW = new TreeSet<>();

    private boolean mergeFlag = true;

    private int conflictCount = 0;
    private HashSet<FlightRecord> conflictRecord = new HashSet<>();


    private ArrayList<ArrayList<LinkedList<FlightRecord>>> gate
            = new ArrayList<>();

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

        this.gate.add(IIW);
        this.gate.add(IIN);
        this.gate.add(IDW);
        this.gate.add(IDN);
        this.gate.add(DIW);
        this.gate.add(DIN);
        this.gate.add(DDW);
        this.gate.add(DDN);
    }

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

        //drawGatesTimeSequenceImage();
    }

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
                = this.gate.get(belongingIndex);

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

            /*String filename ="resources\\image\\conflict\\" + arrivalType + leftType + planeType
                    + "_" + String.valueOf(conflictCount) + ".png";

            DrawTimeSequence.drawTimeSequenceImageOfConflictSituation(
                    filename, currentTypeGates, flightRecord);*/

        } else {
            currentTypeGates.get(gateIndex).add(flightRecord);
        }
    }

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

    private void drawGatesTimeSequenceImage() {

        String[] gatesType = {"IIW", "IIN", "IDW", "IDN",
                "DIW", "DIN", "DDW", "DDN"};

        for (int i = 0; i < this.gate.size(); i++) {

            ArrayList<LinkedList<FlightRecord>> currentTypeGates
                    = this.gate.get(i);

            for (int j = 0; j < currentTypeGates.size(); j++) {

                LinkedList<FlightRecord> currentGate = currentTypeGates.get(j);
                if (currentGate.isEmpty()) {
                    continue;
                }

                String filename = "resources\\image\\gate\\" + gatesType[i] + "_" + String.valueOf(j)
                        + ".png";

                DrawTimeSequence.drawTimeSequenceImageOfASingleGate(
                        filename, currentGate);
            }
        }
    }

    private void printFinalResultToExcel() {

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

        for (int i = 0; i < this.gate.size(); i++) {

            ArrayList<String> gatesType = new ArrayList<>();
            gatesType.add(hashMap.get(i));
            finalResult.add(gatesType);

            ArrayList<LinkedList<FlightRecord>> currentTypeOfGates
                    = this.gate.get(i);

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

    private void printConflictResultToExcel() {

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

        problemOne.printFinalResultToExcel();

        problemOne.printConflictResultToExcel();

        System.out.println("haha");
    }
}
