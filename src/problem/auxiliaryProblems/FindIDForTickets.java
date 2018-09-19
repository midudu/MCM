package problem.auxiliaryProblems;

import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

// 这个类用于临时处理数据
public class FindIDForTickets {

    private static void readData(ArrayList<ArrayList<String>> originalPucksData,
                                 ArrayList<ArrayList<String>> originalTicketsData) {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucks.xls",
                0, true,
                1, 243,
                0, -1, originalPucksData);

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTickets.xls",
                0, true,
                1, 659,
                2, 5, originalTicketsData);
    }

    private static void buildHashMap(
            ArrayList<ArrayList<String>> originalPucksData,
            HashMap<String, Integer> arrivalFlightNameToId,
            HashMap<String, Integer> leftFlightNameToId) {

        for (int i = 0; i < originalPucksData.size(); i++) {

            ArrayList<String> currentRecord = originalPucksData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(7);

            if (arrivalFlightNameToId.containsKey(arrivalFlightName)) {

                System.out.println("arrival  " + arrivalFlightName);
            }

            if (leftFlightNameToId.containsKey(leftFlightName)) {
                System.out.println("left  " + leftFlightName);
            }

            arrivalFlightNameToId.put(arrivalFlightName, i + 1);
            leftFlightNameToId.put(leftFlightName, i + 1);
        }
    }

    private static void generateID(
            ArrayList<ArrayList<String>> id,
            ArrayList<ArrayList<String>> originalTicketsData,
            HashMap<String, Integer> arrivalFlightNameToId,
            HashMap<String, Integer> leftFlightNameToId) {

        for (int i = 0; i < originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = originalTicketsData.get(i);

            String arrivalName = currentRecord.get(0);
            String leftName = currentRecord.get(2);

            ArrayList<String> currentId = new ArrayList<>();
            if (arrivalName.equals("NV697")
                    || arrivalName.equals("NV6763")
                    || arrivalName.equals("NV6319")
                    || arrivalName.equals("NV6489")
                    || arrivalName.equals("NV6753")
                    || arrivalName.equals("GN0455")
                    || arrivalName.equals("GN0645")) {
                currentId.add("");
            } else {
                currentId.add(arrivalFlightNameToId.get(arrivalName).toString());
            }

            if (!leftFlightNameToId.containsKey(leftName)) {
                System.out.println("haha");
            }

            currentId.add(leftFlightNameToId.get(leftName).toString());

            id.add(currentId);
        }
    }

    public static void main(String[] args) {

        ArrayList<ArrayList<String>> originalPucksData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();

        readData(originalPucksData, originalTicketsData);

        HashMap<String, Integer> arrivalFlightNameToId = new HashMap<>();
        HashMap<String, Integer> leftFlightNameToId = new HashMap<>();

        buildHashMap(originalPucksData, arrivalFlightNameToId, leftFlightNameToId);

        ArrayList<ArrayList<String>> id = new ArrayList<>();

        generateID(id, originalTicketsData, arrivalFlightNameToId, leftFlightNameToId);

        ExcelWriter.exportXlsFile("id.xls",
                id, 0, null, 0, 0);

        System.out.println("haha");
    }
}
