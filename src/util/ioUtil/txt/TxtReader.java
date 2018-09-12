package util.ioUtil.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtReader {

    /**
     *
     * @param fileName
     * @param column
     * @param readHorizontallyFlag  if a row is regarded as an ArrayList,
     *                              then this should be true; if a column
     *                              is regarded as an ArrayList, then this
     *                              should be false
     * @param result
     */
    public static void readTxtFile(
            String fileName, int column,
            boolean readHorizontallyFlag,
            ArrayList<ArrayList<String>> result) {

        if (!result.isEmpty()) {
            result.clear();
        }

        File file = new File(fileName);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

            if (readHorizontallyFlag) {
                while (scanner.hasNext()) {

                    ArrayList<String> currentRow = new ArrayList<>();
                    for (int i = 0; i < column; i++) {
                        currentRow.add(scanner.next());
                    }
                    result.add(currentRow);
                }
            } else {
                for (int i = 0; i < column; i++) {
                    ArrayList<String> currentColomn = new ArrayList<>();
                    result.add(currentColomn);
                }

                while (scanner.hasNext()) {
                    for (int i = 0; i < column; i++) {
                        result.get(i).add(scanner.next());
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // Below is for test
    public static void main(String[] args) {

        ArrayList<ArrayList<String>> result1 =
                new ArrayList<>();
        ArrayList<ArrayList<String>> result2 =
                new ArrayList<>();

        TxtReader.readTxtFile("./resources/test1.txt",
                3,true, result1);
        TxtReader.readTxtFile("./resources/test1.txt",
                3,false, result2);

        System.out.println(result1.toString());
        System.out.println(result2.toString());
    }
}
