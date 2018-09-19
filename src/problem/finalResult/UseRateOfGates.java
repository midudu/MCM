package problem.finalResult;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;

// 这个类用来输出题目的“提交结果要求”部分第4问的结果
public class UseRateOfGates extends Problem {

    private void problemOne() {

        ArrayList<ArrayList<String>> resultOfProblemOne = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemThree\\resultOfProblemThree.xls",
                0, true,
                0, -1,
                0, -1, resultOfProblemOne);

        int TGatesUsedCount = 0;
        int SGatesUsedCount = 0;
        for (int i = 0; i < resultOfProblemOne.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemOne.get(i);
            String type = currentRecord.get(0);

            if (type.contains("T")) {
                TGatesUsedCount++;
            } else if (type.contains("S")) {
                SGatesUsedCount++;
            }
        }

        System.out.println("T登机口共有28个，使用了" + String.valueOf(TGatesUsedCount) + "个");
        System.out.println("S登机口共有41个，使用了" + String.valueOf(SGatesUsedCount) + "个");

        ArrayList<ArrayList<String>> pucks = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucksForProgram.xls",
                0, true,
                1, -1,
                0, 9, pucks);

        int startMinute = 20 * 24 * 60;
        int endMinute = 21 * 24 * 60;
        for (int i = 0; i < resultOfProblemOne.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemOne.get(i);

            int totalMinutes = 0;
            int lastLeftTime = startMinute;
            String name = currentRecord.get(0);

            for (int j = 1; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                int flightId = Integer.valueOf(currentRecord.get(j));
                ArrayList<String> flightRecord = pucks.get(flightId - 1);
                String arrivalTimeString = flightRecord.get(7);
                String leftTimeString = flightRecord.get(8);

                String arrivalDate = arrivalTimeString.split(" +")[0];
                String arrivalTime = arrivalTimeString.split(" +")[1];
                int arrival = convertDateToMinute(arrivalDate)
                        + convertTimeToMinute(arrivalTime);
                arrival = (arrival < startMinute ? startMinute : arrival);
                if (arrival < lastLeftTime) {
                    arrival = lastLeftTime;
                }

                String leftDate = leftTimeString.split(" +")[0];
                String leftTime = leftTimeString.split(" +")[1];
                int left = convertDateToMinute(leftDate)
                        + convertTimeToMinute(leftTime);
                left += 45;
                left = (left > endMinute ? endMinute : left);

                totalMinutes += (left - arrival);
                lastLeftTime = left;
            }

            double rate = totalMinutes / 1440.0;
            System.out.println(name + "：" + String.valueOf(rate));
        }
    }

    public static void main(String[] args) {

        UseRateOfGates useRateOfGates = new UseRateOfGates();

        useRateOfGates.problemOne();
    }
}
