package problem.finalResult;

import problem.component.Constant;
import problem.component.FlightRecordWithStationType;
import problem.component.Gate;
import problem.component.PassengerRecord;
import problem.problemTwo.ProblemTwo;
import util.ioUtil.excel.ExcelReader;

import java.util.ArrayList;
import java.util.HashMap;


// 这个类用来输出题目的“提交结果要求”部分第4问的结果
public class TransferFailedPassengers extends ProblemTwo {

    protected void generateNewRecords(ArrayList<ArrayList<String>> originalPucksData,
                                      ArrayList<ArrayList<String>> originalTicketsData,
                                      ArrayList<ArrayList<String>> originalGatesData) {

        generateFlightRecords(originalPucksData);

        generatePassengerRecords(originalTicketsData);

        generateGates(originalGatesData);
    }

    private void buildHashMap(HashMap<Integer, Integer> flightIdToGateId,
                              ArrayList<ArrayList<String>> resultOfProblemTwo) {

        for (int i = 0; i < resultOfProblemTwo.size(); i++) {

            ArrayList<String> currentRecord = resultOfProblemTwo.get(i);

            Integer gateId = Integer.valueOf(currentRecord.get(0));

            for (int j = 1; j < currentRecord.size(); j++) {

                if (currentRecord.get(j).equals("")) {
                    break;
                }

                Integer flightId = Integer.valueOf(currentRecord.get(j).trim());
                flightIdToGateId.put(flightId, gateId);
            }
        }
    }


    private void problemTwo() {

        ArrayList<ArrayList<String>> originalPucksData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalTicketsData
                = new ArrayList<>();
        ArrayList<ArrayList<String>> originalGatesData
                = new ArrayList<>();

        readOriginalData(originalPucksData, originalTicketsData, originalGatesData);
        generateNewRecords(originalPucksData, originalTicketsData, originalGatesData);

        ArrayList<ArrayList<String>> resultOfProblemTwo = new ArrayList<>();
        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resultOfProblemTwo\\resultOfProblemTwo.xls",
                0, true,
                0, -1,
                0, -1, resultOfProblemTwo);

        HashMap<Integer, Integer> flightIdToGateId = new HashMap<>();
        buildHashMap(flightIdToGateId, resultOfProblemTwo);

        int totalPassengerNumbers = 0;
        int failedPassengerNumbers = 0;

        int[] timeDistribution = new int[20];
        int[] nervousness = new int[20];

        for (int i = 0; i < this.passengerRecordArrayList.size(); i++) {

            PassengerRecord passengerRecord = this.passengerRecordArrayList.get(i);

            int arrivalFlightId = passengerRecord.getArrivalFlightId();
            int leftFlightId = passengerRecord.getLeftFlightId();

            FlightRecordWithStationType arrivalFlightRecord
                    = this.flightRecordArrayList.get(arrivalFlightId - 1);
            FlightRecordWithStationType leftFlightRecord
                    = this.flightRecordArrayList.get(leftFlightId - 1);

            int timeBetween = leftFlightRecord.getLeftTime()
                    - arrivalFlightRecord.getArrivalTime();

            if (!flightIdToGateId.containsKey(arrivalFlightId) ||
                    !flightIdToGateId.containsKey(leftFlightId)) {
                continue;
            }

            int arrivalFlightGateId = flightIdToGateId.get(arrivalFlightId);
            int leftFlightGateId = flightIdToGateId.get(leftFlightId);

            Gate arrivalFlightGate = this.gatesArrayList.get(arrivalFlightGateId - 1);
            Gate leftFlightGate = this.gatesArrayList.get(leftFlightGateId - 1);

            String arrivalFlightStationType = arrivalFlightGate.getHallType();
            String leftFlightStationType = leftFlightGate.getHallType();
            String arrivalFlightType = arrivalFlightRecord.getArrivalType();
            String leftFlightType = leftFlightRecord.getLeftType();


            String key1 = arrivalFlightType + leftFlightType
                    + arrivalFlightStationType + leftFlightStationType;
            if (!Constant.minProcedureTime.containsKey(key1)) {
                throw new RuntimeException();
            }
            if (!Constant.mrtCount.containsKey(key1)) {
                throw new RuntimeException();
            }

            String key2 = arrivalFlightStationType
                    + arrivalFlightGate.getLocation().substring(0, 1)
                    + leftFlightStationType
                    + leftFlightGate.getLocation().substring(0, 1);
            if (!Constant.walkingTime.containsKey(key2)) {
                throw new RuntimeException();
            }

            int actualTime = Constant.minProcedureTime.get(key1)
                    + Constant.mrtCount.get(key1) * Constant.mrtTime
                    + Constant.walkingTime.get(key2);

            if (actualTime > timeBetween) {
                failedPassengerNumbers += passengerRecord.getPassengerNumbers();
            }

            totalPassengerNumbers += passengerRecord.getPassengerNumbers();

            int key = actualTime / 5;

            timeDistribution[key] += passengerRecord.getPassengerNumbers();

            double nervous = (double) actualTime / (double) timeBetween;
            int nervousKey = (int) (nervous * 10);
            nervousness[nervousKey] += passengerRecord.getPassengerNumbers();
        }


        System.out.println("共有乘客" + String.valueOf(totalPassengerNumbers) + "人");
        System.out.println("换乘失败乘客" + String.valueOf(failedPassengerNumbers) + "人");
        double rate = (double) failedPassengerNumbers / (double) totalPassengerNumbers;
        System.out.println("失败率" + String.valueOf(rate));

        for (int i = 0; i < timeDistribution.length; i++) {

            System.out.print(i * 5);
            System.out.print("--");
            System.out.print((i + 1) * 5);
            System.out.print(":");
            System.out.print(timeDistribution[i] / 1814.0 * 100);
            System.out.println("%");
        }

        for (int i = 0; i < nervousness.length; i++) {

            System.out.print((double) (i * 0.1));
            System.out.print("--");
            System.out.print((double) ((i + 1) * 0.1));
            System.out.print(":");
            System.out.print(nervousness[i] / 1814.0 * 100);
            System.out.println("%");
        }
    }

    public static void main(String[] args) {

        TransferFailedPassengers transferFailedPassengers
                = new TransferFailedPassengers();

        transferFailedPassengers.problemTwo();
    }
}
