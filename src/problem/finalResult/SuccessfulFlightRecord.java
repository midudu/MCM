package problem.finalResult;

import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;

// 这个类用来输出题目的“提交结果要求”部分第4问的结果
public class SuccessfulFlightRecord {

    private void problemOne() {

        ArrayList<ArrayList<String>> resultOfProblemOne = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemOne\\resultOfProblemOne.xls",
                1, true,
                1, 220,
                0, -1, resultOfProblemOne);

        int successfulWidePlanesCount = 0;
        int successfulNarrowPlanesCount = 0;

        for (int i = 0; i < resultOfProblemOne.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemOne.get(i);

            String type = currentRecord.get(1);

            if (type.contains("W")) {
                successfulWidePlanesCount++;
            } else if (type.contains("N")) {
                successfulNarrowPlanesCount++;
            }
        }

        double totalSuccessfulRate = (double) (resultOfProblemOne.size()) / 242.0;
        double narrowSuccessfulRate = successfulNarrowPlanesCount / 208.0;
        double wideSuccessfulRate = successfulWidePlanesCount / 34.0;


        System.out.println("共有242架飞机，成功分配"
                + String.valueOf(resultOfProblemOne.size()) + "架，成功比例"
                + String.valueOf(totalSuccessfulRate));

        System.out.println("共有208架窄体机，成功分配"
                + String.valueOf(successfulNarrowPlanesCount) + "架，成功比例"
                + String.valueOf(narrowSuccessfulRate));

        System.out.println("共有34架宽体机，成功分配"
                + String.valueOf(successfulWidePlanesCount) + "架，成功比例"
                + String.valueOf(wideSuccessfulRate));
    }

    private void problemTwo() {

        ArrayList<ArrayList<String>> resultOfProblemTwo = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemTwo\\resultOfProblemTwo.xls",
                0, true,
                0, -1,
                1, -1, resultOfProblemTwo);

        ArrayList<ArrayList<String>> pucks = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucksForProgram.xls",
                0, true,
                1, -1,
                0, 3, pucks);

        int successfulWidePlanesCount = 0;
        int successfulNarrowPlanesCount = 0;
        int successfulPlanesCount = 0;

        for (int i = 0; i < resultOfProblemTwo.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemTwo.get(i);

            for (int j = 0; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                int id = Integer.valueOf(currentRecord.get(j));

                String planeType = pucks.get(id - 1).get(1);
                if (planeType.contains("W")) {
                    successfulWidePlanesCount++;
                } else if (planeType.contains("N")){
                    successfulNarrowPlanesCount++;
                }

                successfulPlanesCount++;
            }
        }

        double totalSuccessfulRate = successfulPlanesCount / 242.0;
        double narrowSuccessfulRate = successfulNarrowPlanesCount / 208.0;
        double wideSuccessfulRate = successfulWidePlanesCount / 34.0;

        System.out.println("共有242架飞机，成功分配"
                + String.valueOf(successfulPlanesCount) + "架，成功比例"
                + String.valueOf(totalSuccessfulRate));

        System.out.println("共有208架窄体机，成功分配"
                + String.valueOf(successfulNarrowPlanesCount) + "架，成功比例"
                + String.valueOf(narrowSuccessfulRate));

        System.out.println("共有34架宽体机，成功分配"
                + String.valueOf(successfulWidePlanesCount) + "架，成功比例"
                + String.valueOf(wideSuccessfulRate));

    }

    private void problemThree() {

        ArrayList<ArrayList<String>> resultOfProblemTwo = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemThree\\resultOfProblemThree.xls",
                0, true,
                0, -1,
                1, -1, resultOfProblemTwo);

        ArrayList<ArrayList<String>> pucks = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\usefulPucksForProgram.xls",
                0, true,
                1, -1,
                0, 3, pucks);

        int successfulWidePlanesCount = 0;
        int successfulNarrowPlanesCount = 0;
        int successfulPlanesCount = 0;

        for (int i = 0; i < resultOfProblemTwo.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemTwo.get(i);

            for (int j = 0; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                int id = Integer.valueOf(currentRecord.get(j));

                String planeType = pucks.get(id - 1).get(1);
                if (planeType.contains("W")) {
                    successfulWidePlanesCount++;
                } else if (planeType.contains("N")){
                    successfulNarrowPlanesCount++;
                }

                successfulPlanesCount++;
            }
        }

        double totalSuccessfulRate = successfulPlanesCount / 242.0;
        double narrowSuccessfulRate = successfulNarrowPlanesCount / 208.0;
        double wideSuccessfulRate = successfulWidePlanesCount / 34.0;

        System.out.println("共有242架飞机，成功分配"
                + String.valueOf(successfulPlanesCount) + "架，成功比例"
                + String.valueOf(totalSuccessfulRate));

        System.out.println("共有208架窄体机，成功分配"
                + String.valueOf(successfulNarrowPlanesCount) + "架，成功比例"
                + String.valueOf(narrowSuccessfulRate));

        System.out.println("共有34架宽体机，成功分配"
                + String.valueOf(successfulWidePlanesCount) + "架，成功比例"
                + String.valueOf(wideSuccessfulRate));

    }

    public static void main(String[] args) {

        SuccessfulFlightRecord successfulFlightRecord = new SuccessfulFlightRecord();

        successfulFlightRecord.problemThree();
    }
}
