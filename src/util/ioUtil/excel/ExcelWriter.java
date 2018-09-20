package util.ioUtil.excel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is to export excel (xls supported but xlsx not)
 */
public class ExcelWriter {

    public static void exportXlsFile(
            String filename, ArrayList<ArrayList<String>> content,
            int sheetIndex, String sheetName,
            int startRow, int startCol) {

        if (content.isEmpty()) {
            return;
        }

        if (!filename.contains(".xls") || sheetIndex < 0
                || startRow < 0 || startCol < 0) {
            throw new RuntimeException();
        }

        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(new File(filename));
            if (sheetName == null) {
                sheetName = String.valueOf(sheetIndex);
            }
            WritableSheet sheet = book.createSheet(sheetName, sheetIndex);

            for (int i = 0; i < content.size(); i++) {
                for (int j = 0; j < content.get(i).size(); ++j) {

                    int row = startRow + i;
                    int col = startCol + j;

                    Label label = new Label(col, row, content.get(i).get(j));

                    try {
                        sheet.addCell(label);
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }
            }

            book.write();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // Below is for test
    public static void main(String[] args) {

        ArrayList<ArrayList<String>> content = new ArrayList<>();

        int count = 0;
        for (int row = 0; row < 10; row++) {

            ArrayList<String> currentRow = new ArrayList<>();
            for (int col = 0; col < 10; col++) {
                count++;
                currentRow.add(String.valueOf(count));
            }
            content.add(currentRow);
        }

        exportXlsFile("haha.xls", content,
        0, null,1, 0);

        System.out.println("haha");
    }
}
