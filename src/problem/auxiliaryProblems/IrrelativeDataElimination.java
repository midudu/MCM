package problem.auxiliaryProblems;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 这个类用于临时处理数据
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

    private ArrayList<ArrayList<String>> originalTicketsAndPucksData
            = new ArrayList<>();

    private ArrayList<ArrayList<String>> processedTicketsAndPucksData
            = new ArrayList<>();

    public void eliminateFlightsWithoutPassengersInPucksSheet() {

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

    public void eliminateFlightsOfFixedStationInTicketAndPucksSheet() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\compressedTickets.xls",
                0, true,
                1, 659,
                6, 14, this.originalTicketsAndPucksData);

        for (int i = 0; i < this.originalTicketsAndPucksData.size(); i++) {

            ArrayList<String> currentRecord = this.originalTicketsAndPucksData.get(i);

            String arrivalFlightArrivalType = currentRecord.get(0);
            String arrivalFlightLeftType = currentRecord.get(2);
            String arrivalPlaneType = currentRecord.get(1);
            String arrivalKey = arrivalFlightArrivalType
                    + arrivalFlightLeftType + arrivalPlaneType;
            if (arrivalKey.length() != 3) {
                throw new RuntimeException();
            }
            if (!this.TSType.containsKey(arrivalKey)) {
                throw new RuntimeException();
            }

            String leftFlightArrivalType = currentRecord.get(5);
            String leftFlightLeftType = currentRecord.get(7);
            String leftPlaneType = currentRecord.get(6);
            String leftKey = leftFlightArrivalType
                    + leftFlightLeftType + leftPlaneType;
            if (leftKey.length() != 3) {
                throw new RuntimeException();
            }
            if (!this.TSType.containsKey(leftKey)) {
                throw new RuntimeException();
            }

            currentRecord.add(this.TSType.get(arrivalKey));
            currentRecord.add(this.TSType.get(leftKey));

            this.processedTicketsAndPucksData.add(currentRecord);
        }

        ExcelWriter.exportXlsFile("fixedStationTicketsAndPucks.xls",
                this.processedTicketsAndPucksData,
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

        helper.eliminateFlightsOfFixedStationInTicketAndPucksSheet();

    }
}
