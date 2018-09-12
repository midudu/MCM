package util.ioUtil.txt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TxtWriter<T> {

    public void writeTxtFile(String fileName, ArrayList<T> data) {

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");

            for (int i = 0; i < data.size() - 1; i++) {

                printWriter.println(data.get(i));
            }
            printWriter.print(data.get(data.size()-1));

            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    // Below is for test
    public static void main(String[] args) {

        TxtWriter<Integer> txtWriter = new TxtWriter<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add( i + 1);
        }

        txtWriter.writeTxtFile("integer.txt", arrayList);

        System.out.println("Completed!");
    }
}
