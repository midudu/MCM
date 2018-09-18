package problem.auxiliaryProblems;

import problem.Problem;
import problem.problemTwo.ProblemTwo;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticOfData extends ProblemTwo {

    private void calculatePassengerNumbers() {

        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTicketsForProgram.xls",
                0, true,
                1, 659,
                0, 2, originalTicketsData);

        int totalNumbers = 0;

        for (int i = 0; i < originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord
                    = originalTicketsData.get(i);

            totalNumbers += Integer.valueOf(currentRecord.get(0).trim());
        }

        System.out.println(totalNumbers);
    }



    public static void main(String[] args) {

        StatisticOfData statisticOfData = new StatisticOfData();

        statisticOfData.calculatePassengerNumbers();

        /*StatisticOfData problem = new StatisticOfData();

        ArrayList<ArrayList<String>> originalPucksData = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucks.xls",
                0, true,
                1, 243,
                0, -1, originalPucksData);

        ArrayList<ArrayList<String>> originalTicketsData = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTickets.xls",
                0, true,
                1, 659,
                0, -1, originalTicketsData);

        HashMap<String, Integer> hashMapArrival = new HashMap<>();
        HashMap<String, Integer> hashMapLeft = new HashMap<>();

        for (int i = 0; i < originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = originalTicketsData.get(i);

            int passengerNumbers = Integer.valueOf(currentRecord.get(1));
            String arrivalFlight = currentRecord.get(2);
            String leftFlight = currentRecord.get(4);

            hashMapArrival.put(arrivalFlight, hashMapArrival.getOrDefault(
                    arrivalFlight, 0) + passengerNumbers);

            hashMapLeft.put(leftFlight, hashMapLeft.getOrDefault(
                    leftFlight, 0) + passengerNumbers);
        }

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (int i = 0; i < 111; i++) {

            ArrayList<String> currentResult = new ArrayList<>();

            ArrayList<String> currentRecord = originalPucksData.get(i);

            String arrivalName = currentRecord.get(2);
            String leftName = currentRecord.get(7);

            ArrayList<String> currentId = new ArrayList<>();
            *//*if (arrivalName.equals("NV6763")
                    || arrivalName.equals("NV6319")
                    || arrivalName.equals("NV6489")
                    || arrivalName.equals("NV6753")
                    || arrivalName.equals("GN0455")
                    || arrivalName.equals("GN0645")) {
                currentResult.add("");
            } else {*//*

                Integer numbers = hashMapArrival.getOrDefault(arrivalName,0)
                        + hashMapLeft.getOrDefault(leftName,0);

                currentResult.add(numbers.toString());
            //}

            result.add(currentResult);
        }

        ExcelWriter.exportXlsFile("numbers.xls",
                result,0,null,0,0);*/

        System.out.println("haha");
    }
}
