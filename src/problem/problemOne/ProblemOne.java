package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;
import problem.component.GatesInformation;
import util.ioUtil.txt.TxtWriter;
import util.paintUtil.opencv.DrawTimeSequence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

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
                result+=20;
            }
            if (leftType.contains("I")) {
                result+=10;
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
                case 131:{
                    I2DI_N.add(gatesInformation);
                    break;
                }
                case 132:{
                    I2DI_W.add(gatesInformation);
                    break;
                }
                case 221:{
                    D2D_N.add(gatesInformation);
                    break;
                }
                case 222:{
                    D2D_W.add(gatesInformation);
                    break;
                }
                case 231:{
                    D2DI_N.add(gatesInformation);
                    break;
                }
                case 232:{
                    D2DI_W.add(gatesInformation);
                    break;
                }
                case 311:{
                    DI2I_N.add(gatesInformation);
                    break;
                }
                case 312:{
                    DI2I_W.add(gatesInformation);
                    break;
                }
                case 321:{
                    DI2D_N.add(gatesInformation);
                    break;
                }
                case 322:{
                    DI2D_W.add(gatesInformation);
                    break;
                }
                case 331:{
                    DI2DI_N.add(gatesInformation);
                    break;
                }
                case 332:{
                    DI2DI_W.add(gatesInformation);
                    break;
                }
                default:{
                    throw new RuntimeException();
                }
            }
        }
    }

    private void showResultInPNGForm() {

        String[] fileNameSet = {"DDN", "DDW", "IIN", "IIW", "DIN_IDN", "DIW_IDW"};
        if (fileNameSet.length != this.divisionResult.size()) {
            throw new RuntimeException();
        }

        for (int i = 0; i < this.divisionResult.size(); i++) {

            if (this.divisionResult.get(i).isEmpty()) {
                continue;
            }

            String fileName = "resources/" + fileNameSet[i] + ".png";
            DrawTimeSequence.drawTimeSequenceTable(fileName,
                    this.divisionResult.get(i));
        }
    }

    // Below is for test
    public static void main(String[] args) {

        ProblemOne problemOne = new ProblemOne();

        problemOne.getFlightRecordArrayListFromExcel();
        problemOne.generateFlightRecordSets();

        problemOne.getGatesInformationArrayListFromExcel();
        problemOne.generateGatesInformationSets();

        //problemOne.generateTxtFilesOfEightTreeSets();

        problemOne.divideByGreedyMethod(
                problemOne.treeSetArrayList,
                problemOne.divisionResult);

        problemOne.showResultInPNGForm();

        System.out.println("haha");
    }
}
