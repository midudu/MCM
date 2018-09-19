package problem.auxiliaryProblems;

import problem.Problem;
import util.ioUtil.excel.ExcelReader;
import util.ioUtil.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is to deal with the input data.
 */

public class TicketsSheetCompression extends Problem {

    private ArrayList<ArrayList<String>> originalTicketsData
            = new ArrayList<>();

    private ArrayList<ArrayList<String>> compressedTicketsData
            = new ArrayList<>();

    private boolean verifyIfARecordDeterminedByArrivalAndLeftFlightName() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                3, true,
                1, 1257,
                0, -1, originalTicketsData);

        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < this.originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = this.originalTicketsData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(4);

            String arrivalDate = currentRecord.get(3);
            String leftDate = currentRecord.get(5);

            String key = arrivalFlightName + "_" + leftFlightName;
            String value = arrivalDate + "_" + leftDate;

            if (hashMap.containsKey(key)) {
                String existingValue = hashMap.get(key);
                if (!existingValue.equals(value)) {
                    return false;
                }
            } else {
                hashMap.put(key, value);
            }
        }

        return true;
    }

    private void compressOriginalTicketsData() {

        ExcelReader.importXlsFile(
                "E:\\Java_Projects\\MCM\\resources\\InputData_2.xls",
                3, true,
                1, 1257,
                0, -1, originalTicketsData);

        HashMap<String, Integer> hashMap = new HashMap<>();

        buildHashMapOfFlightAndNumbers(hashMap);

        generateCompressedTicketsData(hashMap);

        ExcelWriter.exportXlsFile("compressedTickets.xls",
                this.compressedTicketsData,
                0,null,0,0);

        System.out.println("haha");

    }

    private void buildHashMapOfFlightAndNumbers(HashMap<String, Integer> hashMap) {

        for (int i = 0; i < this.originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = this.originalTicketsData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(4);

            Integer passengerNumbers = Integer.valueOf(currentRecord.get(1));

            String key = arrivalFlightName + "_" + leftFlightName;

            if (hashMap.containsKey(key)) {

                Integer existingPassengerNumbers = hashMap.get(key);
                hashMap.put(key, existingPassengerNumbers + passengerNumbers);

            } else {
                hashMap.put(key,passengerNumbers);
            }
        }
    }

    private void generateCompressedTicketsData(HashMap<String, Integer> hashMap) {

        for (int i = 0; i < this.originalTicketsData.size(); i++) {

            ArrayList<String> currentRecord = this.originalTicketsData.get(i);

            String arrivalFlightName = currentRecord.get(2);
            String leftFlightName = currentRecord.get(4);
            String key = arrivalFlightName + "_" + leftFlightName;

            if (!hashMap.containsKey(key)) {
                throw new RuntimeException();
            }

            if (hashMap.get(key) == -1) {
                continue;
            } else {

                Integer passengerNumbers = hashMap.get(key);
                hashMap.put(key, -1);
                currentRecord.set(1, passengerNumbers.toString());

                this.compressedTicketsData.add(currentRecord);
            }
        }
    }

    public static void main(String[] args) {

        TicketsSheetCompression ticketsSheetCompression =
                new TicketsSheetCompression();

        /*boolean result
                = ticketsSheetCompression.verifyIfARecordDeterminedByArrivalAndLeftFlightName();*/

        /*System.out.println(result);*/

        ticketsSheetCompression.compressOriginalTicketsData();
    }
}
