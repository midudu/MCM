package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;
import problem.component.GatesInformation;
import util.ioUtil.txt.TxtWriter;
import util.paintUtil.opencv.DrawTimeSequence;

import java.util.*;

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

    private ArrayList<TreeSet<FlightRecord>> treeSetArrayList
            = new ArrayList<>();

    private ArrayList<ArrayList<ArrayList<FlightRecord>>> divisionResult
            = new ArrayList<>();

    private HashSet<GatesInformation> I2I_W = new HashSet<>();
    private HashSet<GatesInformation> D2D_W = new HashSet<>();
    private HashSet<GatesInformation> I2DI_W = new HashSet<>();
    private HashSet<GatesInformation> D2DI_W = new HashSet<>();
    private HashSet<GatesInformation> DI2D_W = new HashSet<>();
    private HashSet<GatesInformation> DI2I_W = new HashSet<>();
    private HashSet<GatesInformation> DI2DI_W = new HashSet<>();
    private HashSet<GatesInformation> I2I_N = new HashSet<>();
    private HashSet<GatesInformation> D2D_N = new HashSet<>();
    private HashSet<GatesInformation> I2DI_N = new HashSet<>();
    private HashSet<GatesInformation> D2DI_N = new HashSet<>();
    private HashSet<GatesInformation> DI2D_N = new HashSet<>();
    private HashSet<GatesInformation> DI2I_N = new HashSet<>();
    private HashSet<GatesInformation> DI2DI_N = new HashSet<>();

    private int conflictCount = 0;
    HashSet<FlightRecord> conflictRecord = new HashSet<>();


    private ArrayList<ArrayList<LinkedList<FlightRecord>>> gate = new ArrayList<>();

    private void generateFlightRecordSets() {

        for (FlightRecord flightRecord : this.flightRecordArrayList) {

            String arrivalType = flightRecord.getArrivalType();
            String leftType = flightRecord.getLeftType();
            String planeType = flightRecord.getPlaneType();

            String totalString = arrivalType + leftType + planeType;
            if (totalString.length() != 3) {
                throw new RuntimeException();
            }

            switch (totalString) {
                case "DDN": {
                    DDN.add(flightRecord);
                    break;
                }
                case "DDW": {
                    DDW.add(flightRecord);
                    break;
                }
                case "DIN": {
                    DIN.add(flightRecord);
                    break;
                }
                case "DIW": {
                    DIW.add(flightRecord);
                    break;
                }
                case "IIN": {
                    IIN.add(flightRecord);
                    break;
                }
                case "IIW": {
                    IIW.add(flightRecord);
                    break;
                }
                case "IDN": {
                    IDN.add(flightRecord);
                    break;
                }
                case "IDW": {
                    IDW.add(flightRecord);
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }
        }

        if (mergeFlag) {
            this.DIN_IDN.addAll(this.DIN);
            this.DIN_IDN.addAll(this.IDN);

            this.DIW_IDW.addAll(this.DIW);
            this.DIW_IDW.addAll(this.IDW);
        }

        this.treeSetArrayList.add(this.DDN);
        this.treeSetArrayList.add(this.DDW);
        this.treeSetArrayList.add(this.IIN);
        this.treeSetArrayList.add(this.IIW);
        if (mergeFlag) {
            this.treeSetArrayList.add(this.DIN_IDN);
            this.treeSetArrayList.add(this.DIW_IDW);

        } else {
            this.treeSetArrayList.add(this.DIN);
            this.treeSetArrayList.add(this.DIW);
            this.treeSetArrayList.add(this.IDN);
            this.treeSetArrayList.add(this.IDW);
        }
    }

    private void generateTxtFilesOfEightTreeSets() {

        ArrayList<FlightRecord> DDN = new ArrayList<>(this.DDN);
        ArrayList<FlightRecord> DDW = new ArrayList<>(this.DDW);
        ArrayList<FlightRecord> DIN = new ArrayList<>(this.DIN);
        ArrayList<FlightRecord> DIW = new ArrayList<>(this.DIW);
        ArrayList<FlightRecord> IIN = new ArrayList<>(this.IIN);
        ArrayList<FlightRecord> IIW = new ArrayList<>(this.IIW);
        ArrayList<FlightRecord> IDN = new ArrayList<>(this.IDN);
        ArrayList<FlightRecord> IDW = new ArrayList<>(this.IDW);

        TxtWriter.writeTxtFile("resources/DDN.txt", DDN);
        TxtWriter.writeTxtFile("resources/DDW.txt", DDW);
        TxtWriter.writeTxtFile("resources/DIN.txt", DIN);
        TxtWriter.writeTxtFile("resources/DIW.txt", DIW);
        TxtWriter.writeTxtFile("resources/IDN.txt", IDN);
        TxtWriter.writeTxtFile("resources/IDW.txt", IDW);
        TxtWriter.writeTxtFile("resources/IIN.txt", IIN);
        TxtWriter.writeTxtFile("resources/IIW.txt", IIW);
    }

    private void divideByGreedyMethod(
            ArrayList<TreeSet<FlightRecord>> treeSetArrayList,
            ArrayList<ArrayList<ArrayList<FlightRecord>>> divisionResult) {

        if (!divisionResult.isEmpty()) {
            divisionResult.clear();
        }

        for (TreeSet<FlightRecord> flightRecordTreeSet : treeSetArrayList) {

            ArrayList<ArrayList<FlightRecord>> divisionResultOfCurrentTreeSet
                    = new ArrayList<>();

            divideByGreedyMethodHelper(flightRecordTreeSet,
                    divisionResultOfCurrentTreeSet);

            divisionResult.add(divisionResultOfCurrentTreeSet);
        }
    }

    private void divideByGreedyMethodHelper(
            TreeSet<FlightRecord> treeSet,
            ArrayList<ArrayList<FlightRecord>> divisionResult) {

        if (!divisionResult.isEmpty()) {
            divisionResult.clear();
        }

        boolean[] popFlag = new boolean[treeSet.size()];

        ArrayList<FlightRecord> flightRecordArrayList = new ArrayList<>(treeSet);

        for (int i = 0; i < flightRecordArrayList.size(); i++) {

            while (i < flightRecordArrayList.size() && popFlag[i]) {
                i++;
            }

            if (i == flightRecordArrayList.size()) {
                return;
            }

            ArrayList<FlightRecord> currentFlightRecord = new ArrayList<>();
            currentFlightRecord.add(flightRecordArrayList.get(i));
            popFlag[i] = true;

            int leftTime = flightRecordArrayList.get(i).getLeftTime();
            int j = i;
            while (j < flightRecordArrayList.size()) {

                if (popFlag[j]) {
                    j++;
                    continue;
                }
                if (flightRecordArrayList.get(j).getArrivalTime() < leftTime + 45) {
                    j++;
                    continue;
                }

                leftTime = flightRecordArrayList.get(j).getLeftTime();
                currentFlightRecord.add(flightRecordArrayList.get(j));
                popFlag[j] = true;

                j++;
            }

            divisionResult.add(currentFlightRecord);
        }
    }

    private void generateGatesInformationSets() {

        for (GatesInformation gatesInformation : this.gatesInformationArrayList) {

            String arrivalType = gatesInformation.getArrivalType();
            String leftType = gatesInformation.getLeftType();
            String planeType = gatesInformation.getPlaneType();

            int result = 0;

            if (arrivalType.contains("D")) {
                result += 200;
            }
            if (arrivalType.contains("I")) {
                result += 100;
            }
            if (leftType.contains("D")) {
                result += 20;
            }
            if (leftType.contains("I")) {
                result += 10;
            }
            if (planeType.contains("N")) {
                result += 1;
            } else if (planeType.contains("W")) {
                result += 2;
            }

            switch (result) {
                case 111: {
                    I2I_N.add(gatesInformation);
                    break;
                }
                case 112: {
                    I2I_W.add(gatesInformation);
                    break;
                }
                case 131: {
                    I2DI_N.add(gatesInformation);
                    break;
                }
                case 132: {
                    I2DI_W.add(gatesInformation);
                    break;
                }
                case 221: {
                    D2D_N.add(gatesInformation);
                    break;
                }
                case 222: {
                    D2D_W.add(gatesInformation);
                    break;
                }
                case 231: {
                    D2DI_N.add(gatesInformation);
                    break;
                }
                case 232: {
                    D2DI_W.add(gatesInformation);
                    break;
                }
                case 311: {
                    DI2I_N.add(gatesInformation);
                    break;
                }
                case 312: {
                    DI2I_W.add(gatesInformation);
                    break;
                }
                case 321: {
                    DI2D_N.add(gatesInformation);
                    break;
                }
                case 322: {
                    DI2D_W.add(gatesInformation);
                    break;
                }
                case 331: {
                    DI2DI_N.add(gatesInformation);
                    break;
                }
                case 332: {
                    DI2DI_W.add(gatesInformation);
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }
        }
    }

    private void showResultInPNGForm() {

        if (mergeFlag) {
            String[] fileNameSet = {"DDN", "DDW", "IIN", "IIW", "DIN_IDN", "DIW_IDW"};

            if (fileNameSet.length != this.divisionResult.size()) {
                throw new RuntimeException();
            }

            for (int i = 0; i < this.divisionResult.size(); i++) {

                if (this.divisionResult.get(i).isEmpty()) {
                    continue;
                }

                String fileName = "resources/merge/" + fileNameSet[i] + ".png";
                DrawTimeSequence.drawTimeSequenceTable(fileName,
                        this.divisionResult.get(i));
            }
        } else {

            String[] fileNameSet
                    = {"DDN", "DDW", "IIN", "IIW", "DIN", "DIW", "IDN", "IDW"};

            if (fileNameSet.length != this.divisionResult.size()) {
                throw new RuntimeException();
            }

            for (int i = 0; i < this.divisionResult.size(); i++) {

                if (this.divisionResult.get(i).isEmpty()) {
                    continue;
                }

                String fileName = "resources/unmerge/" + fileNameSet[i] + ".png";
                DrawTimeSequence.drawTimeSequenceTable(fileName,
                        this.divisionResult.get(i));
            }
        }
    }

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
        for (int i = 0; i < 4; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            DIN.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> IDW = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinkedList<FlightRecord> queue = new LinkedList<>();
            IDW.add(queue);
        }

        ArrayList<LinkedList<FlightRecord>> IDN = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
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

        int index = 0;

        while (iterator.hasNext()) {

            FlightRecord flightRecord = iterator.next();

            System.out.print(index);
            index++;

            decideCurrentFlightRecordBelongs(flightRecord);
        }

        System.out.println("haha");
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
                if ( lastFlightLeftTime + 75 <= leftTime ) {
                    int existingFlightNumbers = currentGate.size();
                    if (existingFlightNumbers > maxExistingFlightNumbers) {
                        maxExistingFlightNumbers = existingFlightNumbers;
                        gateIndex = i;
                    }
                }
            }
        }

        /*if (gateIndex == -1) {
            throw new RuntimeException();
        }*/

        if (gateIndex == -1) {
            this.conflictCount++;
            this.conflictRecord.add(flightRecord);

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

    // Below is for test
    public static void main(String[] args) {

        ProblemOne problemOne = new ProblemOne();

        problemOne.getResultWithQueueMethod();

        problemOne.showResultInPNGForm();

        System.out.println("haha");
    }
}
