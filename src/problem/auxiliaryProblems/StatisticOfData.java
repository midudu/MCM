package problem.auxiliaryProblems;

import problem.Problem;
import problem.problemTwo.ProblemTwo;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is to do the statistic of the original data.
 */
public class StatisticOfData extends ProblemTwo {

    /**
     * To calculate the total relative passenger numbers in the original data
     * sheet.
     */
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

        System.out.println("haha");
    }
}
