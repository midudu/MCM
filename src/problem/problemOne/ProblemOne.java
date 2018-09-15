package problem.problemOne;

import problem.Problem;
import problem.component.FlightRecord;
import util.ioUtil.txt.TxtWriter;

import javax.swing.tree.TreeCellRenderer;
import java.util.ArrayList;
import java.util.HashMap;
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

    private void generateFlightRecordMap() {


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

    private void divideByGreedyMethod(TreeSet<FlightRecord> treeSet,
                                      ArrayList<ArrayList<FlightRecord>> divisionResult) {

        if (!divisionResult.isEmpty()) {
            divisionResult.clear();
        }

        boolean[] popFlag = new boolean[treeSet.size()];

        ArrayList<FlightRecord> flightRecordArrayList = new ArrayList<>(treeSet);

        for (int i = 0; i < flightRecordArrayList.size(); i++) {

            while (popFlag[i] && i < flightRecordArrayList.size()) {
                i++;
                if (i==flightRecordArrayList.size()) {
                    break;
                }
            }

            if (i == flightRecordArrayList.size()) {
                return;
            }

            ArrayList<FlightRecord> currentFlightRecord = new ArrayList<>();
            currentFlightRecord.add(flightRecordArrayList.get(i));
            popFlag[i] = true;

            int leftTime = flightRecordArrayList.get(i).getLeftTime();
            int j = i;
            while (j < flightRecordArrayList.size() ) {

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

        System.out.println("haha");
    }

    // Below is for test
    public static void main(String[] args) {

        ProblemOne problemOne = new ProblemOne();
        problemOne.getFlightRecordArrayListFromExcel();

        problemOne.generateFlightRecordMap();

        //problemOne.generateTxtFilesOfEightTreeSets();

        ArrayList<ArrayList<FlightRecord>> DIWResult = new ArrayList<>();
        problemOne.divideByGreedyMethod(problemOne.DIW, DIWResult);

        System.out.println("haha");
    }
}
