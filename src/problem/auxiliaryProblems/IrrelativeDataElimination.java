package problem.auxiliaryProblems;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IrrelativeDataElimination extends Problem {

    private ArrayList<ArrayList<String>> originalPucksData
            = new ArrayList<>();
    private ArrayList<ArrayList<String>> compressedTicketsData
            = new ArrayList<>();

    private HashSet<String> arrivalFlightNamesInTickets
            = new HashSet<>();
    private HashSet<String> leftFlightNamesInTickets
            = new HashSet<>();

    private ArrayList<ArrayList<String>> processedPucksData
            = new ArrayList<>();

    private HashMap<String, String> TSType = new HashMap<>();
    {
        TSType.put("IIW", "T/S");
        TSType.put("DDW", "S");
        TSType.put("IDW", "T");
        TSType.put("DIW", "T");
        TSType.put("IIN", "T/S");
        TSType.put("DDN", "T/S");
        TSType.put("IDN", "T");
        TSType.put("DIN", "T");
    }

    public void eliminateFlightsWithoutPassengers() {

        readOriginalData();

        buildHashSetOfRelativeFlightsInTickets();

        generateProcessedPucksData();

        ExcelWriter.exportXlsFile("relativePucks.xls",
                this.processedPucksData,
                0, null, 0, 0);

        System.out.println("haha");
    }

    public void eliminateFlightsOfFixedStationInPucksSheet() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0, true,
                1, 243,
                0, 9, this.originalPucksData);

        for (int i = 0; i < this.originalPucksData.size(); i++) {

            ArrayList<String> currentRecord = this.originalPucksData.get(i);

            String arrivalFlightType = currentRecord.get(3);
            String leftFlightType = currentRecord.get(8);
            String planeType = currentRecord.get(4);

            String key = arrivalFlightType + leftFlightType + planeType;
            if (key.length() != 3) {
                throw new RuntimeException();
            }
            if (!this.TSType.containsKey(key)) {
                throw new RuntimeException();
            }

            currentRecord.add(this.TSType.get(key));

            this.processedPucksData.add(currentRecord);
        }

        ExcelWriter.exportXlsFile("fixedStationPucks.xls",
                this.processedPucksData,
                0, null, 0, 0);

        System.out.println("haha");
    }

    private void readOriginalData() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0, true,
                1, 243,
                0, 8, this.originalPucksData);

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\compressedTickets.xls",
                0, true,
                1, 659,
                0, 5, this.compressedTicketsData);
    }

    private void buildHashSetOfRelativeFlightsInTickets() {

        for (int i = 0; i < this.compressedTicketsData.size(); i++) {

            ArrayList<String> currentRecord = this.compressedTicketsData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(4);

            this.arrivalFlightNamesInTickets.add(arrivalFlightName);
            this.leftFlightNamesInTickets.add(leftFlightName);
        }
    }

    private void generateProcessedPucksData() {

        for (int i = 0; i < this.originalPucksData.size(); i++) {

            ArrayList<String> currentRecord = this.originalPucksData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(7);

            if (this.arrivalFlightNamesInTickets.contains(arrivalFlightName)
                    || this.leftFlightNamesInTickets.contains(leftFlightName)) {
                currentRecord.add("r");
            } else {
                currentRecord.add("n");
            }

            this.processedPucksData.add(currentRecord);
        }
    }

    public static void main(String[] args) {

        IrrelativeDataElimination helper
                = new IrrelativeDataElimination();

        helper.eliminateFlightsOfFixedStationInPucksSheet();

    }
}
