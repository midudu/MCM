package problem.auxiliaryProblems;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;

public class StatisticOfData extends Problem {



    public static void main(String[] args) {

        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulTickets.xls",
                1, true,
                1, 573,
                1, 2, originalTicketsData);

        int totalNumbers = 0;

        for (int i = 0; i < originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord
                    = originalTicketsData.get(i);

            totalNumbers += Integer.valueOf(currentRecord.get(0).trim());
        }

        System.out.println(totalNumbers);
    }
}
