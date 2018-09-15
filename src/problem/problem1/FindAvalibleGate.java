package problem.problem1;

import util.ioUtil.excel.excelReader;

import java.util.ArrayList;

public class FindAvalibleGate {

    public static void main(String[] args) {

        ArrayList<ArrayList<String>> arrivalType
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
        0,true,
        1, 306,
        5, 6, arrivalType);

        ArrayList<ArrayList<String>> leftType
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0,true,
                1, 306,
                12, 13, leftType);

        ArrayList<ArrayList<String>> planeType
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                0,true,
                1, 306,
                7, 8, planeType);

        ArrayList<ArrayList<String>> gateInformation
                = new ArrayList<>();

        excelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                2,true,
                1, 70,
                3, 6, gateInformation);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        for (int i = 0; i < arrivalType.size(); i++) {

            for (int j = 0; j < gateInformation.size(); j++) {

                if (!gateInformation.get(j).get(0).contains(
                        arrivalType.get(i).get(0))) {
                    break;
                }
                if (!gateInformation.get(j).get(1).contains(
                        leftType.get(i).get(0))) {
                    break;
                }
                if
            }
        }

        System.out.println("haha");
    }


}
