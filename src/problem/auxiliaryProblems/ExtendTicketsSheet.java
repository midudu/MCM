package problem.auxiliaryProblems;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtendTicketsSheet extends Problem {

    private ArrayList<ArrayList<String>> originalPucksData
            = new ArrayList<>();
    private ArrayList<ArrayList<String>> originalTicketsData
            = new ArrayList<>();

    private HashMap<String, String> arrivalFlightNameToIndexString
            = new HashMap<>();

    private HashMap<String, String> leftFlightNameToIndexString
            = new HashMap<>();

    private ArrayList<ArrayList<String>> addtionalRecord
            = new ArrayList<>();

    private void dealTickets() {

        readOriginalData();

        initArrivalFlightNameToIndexStringHashMap();

        initLeftFlightNameToIndexStringHashMap();

        generateAdditionalRecord();

        exportAdditionalRecordToExcel();

        System.out.println("haha");

    }

    private void readOriginalData() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0, true,
                1, 243,
                0, -1, originalPucksData);

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                1, true,
                1, 1734,
                0, -1, originalTicketsData);
    }

    private void initArrivalFlightNameToIndexStringHashMap() {

        for (int i = 0; i < this.originalPucksData.size(); i++) {

            String arrivalFlightName
                    = this.originalPucksData.get(i).get(2);

            if (this.arrivalFlightNameToIndexString.containsKey(arrivalFlightName)) {

                String originalValue
                        = this.arrivalFlightNameToIndexString.get(arrivalFlightName);

                String newValue = originalValue + "_" + String.valueOf(i);
                this.arrivalFlightNameToIndexString.put(arrivalFlightName, newValue);
            } else {
                this.arrivalFlightNameToIndexString.put(arrivalFlightName,
                        String.valueOf(i));
            }
        }
    }

    private void initLeftFlightNameToIndexStringHashMap() {

        for (int i = 0; i < this.originalPucksData.size(); i++) {

            String leftFlightName
                    = this.originalPucksData.get(i).get(7);

            if (this.leftFlightNameToIndexString.containsKey(leftFlightName)) {

                String originalValue
                        = this.leftFlightNameToIndexString.get(leftFlightName);

                String newValue = originalValue + "_" + String.valueOf(i);
                this.leftFlightNameToIndexString.put(leftFlightName, newValue);
            } else {
                this.leftFlightNameToIndexString.put(leftFlightName,
                        String.valueOf(i));
            }
        }
    }

    private void generateAdditionalRecord() {

        for (int i = 0; i < this.originalTicketsData.size(); i++) {

            ArrayList<String> currentAdditionalRecord = new ArrayList<>();

            ArrayList<String> currentTicketRecord
                    = this.originalTicketsData.get(i);

            String arrivalFlightName = currentTicketRecord.get(2);
            findRecordAccordingToArrivalFlightName(
                    currentAdditionalRecord, arrivalFlightName, i);

            String leftFlightName = currentTicketRecord.get(4);
            findRecordAccordingToLeftFlightName(
                    currentAdditionalRecord, leftFlightName, i);

            this.addtionalRecord.add(currentAdditionalRecord);
        }
    }

    private void findRecordAccordingToArrivalFlightName(
            ArrayList<String> currentAdditionalRecord, String arrivalFlightName,
            int originalTicketsDataIndex) {

        if (this.arrivalFlightNameToIndexString.containsKey(arrivalFlightName)) {

            String indexString
                    = this.arrivalFlightNameToIndexString.get(arrivalFlightName);

            int index = 0;

            if (indexString.contains("_")) {

                String[] indexesString = indexString.split("_");
                int indexOne = Integer.valueOf(indexesString[0]);
                int indexTwo = Integer.valueOf(indexesString[1]);

                int arrivalDate = getDateOfOriginalTicketsData(
                        originalTicketsDataIndex, 3);

                int arrivalDateOfIndexOne = getArrivalDate(indexOne);
                int arrivalDateOfIndexTwo = getArrivalDate(indexTwo);

                if (arrivalDateOfIndexOne == arrivalDateOfIndexTwo) {
                    throw new RuntimeException();
                }

                if (arrivalDate == arrivalDateOfIndexOne) {
                    index = indexOne;
                } else if (arrivalDate == arrivalDateOfIndexTwo) {
                    index = indexTwo;
                } else {
                    throw new RuntimeException();
                }

            } else {
                index = Integer.valueOf(indexString);
            }

            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(3));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(4));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(9));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(10));

        } else {
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
        }
    }

    private void findRecordAccordingToLeftFlightName(
            ArrayList<String> currentAdditionalRecord, String leftFlightName,
            int originalTicketsDataIndex) {

        if (this.leftFlightNameToIndexString.containsKey(leftFlightName)) {

            String indexString
                    = this.leftFlightNameToIndexString.get(leftFlightName);

            int index = 0;

            if (indexString.contains("_")) {

                String[] indexesString = indexString.split("_");
                int indexOne = Integer.valueOf(indexesString[0]);
                int indexTwo = Integer.valueOf(indexesString[1]);

                int leftDate = getDateOfOriginalTicketsData(
                        originalTicketsDataIndex, 5);

                int leftDateOfIndexOne = getLeftDate(indexOne);
                int leftDateOfIndexTwo = getLeftDate(indexTwo);

                if (leftDateOfIndexOne == leftDateOfIndexTwo) {
                    throw new RuntimeException();
                }

                if (leftDate == leftDateOfIndexOne) {
                    index = indexOne;
                } else if (leftDate == leftDateOfIndexTwo) {
                    index = indexTwo;
                } else {
                    throw new RuntimeException();
                }

            } else {
                index = Integer.valueOf(indexString);
            }

            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(8));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(4));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(9));
            currentAdditionalRecord.add(
                    this.originalPucksData.get(index).get(10));

        } else {
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
            currentAdditionalRecord.add("");
        }
    }

    private int getArrivalDate(int originalPucksDataIndex) {

        ArrayList<String> currentOriginalPucksDataRecord
                = this.originalPucksData.get(originalPucksDataIndex);

        String concreteArrivalTime = currentOriginalPucksDataRecord.get(9);

        String arrivalYearMonthDate = concreteArrivalTime.split(" +")[0];
        String arrivalDate = arrivalYearMonthDate.substring(
                arrivalYearMonthDate.length() - 2, arrivalYearMonthDate.length());

        if (arrivalDate.equals("19")) {
            return 19;
        } else if (arrivalDate.equals("20")) {
            return 20;
        } else if (arrivalDate.equals("21")) {
            return 21;
        } else {
            throw new RuntimeException();
        }
    }

    private int getLeftDate(int originalPucksDataIndex) {

        ArrayList<String> currentOriginalPucksDataRecord
                = this.originalPucksData.get(originalPucksDataIndex);

        String concreteLeftTime = currentOriginalPucksDataRecord.get(10);

        String leftYearMonthDate = concreteLeftTime.split(" +")[0];
        String leftDate = leftYearMonthDate.substring(
                leftYearMonthDate.length() - 2, leftYearMonthDate.length());

        if (leftDate.equals("19")) {
            return 19;
        } else if (leftDate.equals("20")) {
            return 20;
        } else if (leftDate.equals("21")) {
            return 21;
        } else {
            throw new RuntimeException();
        }
    }

    private int getDateOfOriginalTicketsData(int rowIndex, int colIndex) {

        if (colIndex != 3 && colIndex != 5) {
            throw new RuntimeException();
        }

        ArrayList<String> currentRecord = this.originalTicketsData.get(rowIndex);

        String date = currentRecord.get(colIndex);

        if (date.contains("19")) {
            return 19;
        } else if (date.contains("21")) {
            return 21;
        } else {
            return 20;
        }
    }

    private void exportAdditionalRecordToExcel() {

        ExcelWriter.exportXlsFile("additional.xls",
                this.addtionalRecord,0,null,0,0);
    }

    public static void main(String[] args) {

        ExtendTicketsSheet problem = new ExtendTicketsSheet();

        problem.dealTickets();
    }
}
