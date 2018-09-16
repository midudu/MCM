package util.ioUtil.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    /**
     * @param filename             must end up with ".xls" not "xlsx"
     * @param sheetIndex           the index of the current sheet. (the
     *                             index of the first sheet is 0)
     * @param readHorizontallyFlag if a row is regarded as an ArrayList,
     *                             then this should be true; if a column
     *                             is regarded as an ArrayList, then this
     *                             should be false
     * @param startRow             the first row is 0, included
     * @param endRow               excluded, -1 for the last row in the sheet
     * @param startColumn          the first column is 0, included
     * @param endColumn            excluded, -1 for the last column in the
     *                             sheet
     * @param result
     */
    public static void importXlsFile(
            String filename, int sheetIndex,
            boolean readHorizontallyFlag,
            int startRow, int endRow,
            int startColumn, int endColumn,
            ArrayList<ArrayList<String>> result) {

        if (!result.isEmpty()) {
            result.clear();
        }
        if (startRow < 0 || startColumn < 0 || sheetIndex < 0) {
            throw new RuntimeException("Input parameters illegal!");
        }


        Workbook workbook = null;
        Sheet sheet = null;
        Cell cell = null;
        try {
            workbook = Workbook.getWorkbook(new File(filename));
            int sheetNumbers = workbook.getNumberOfSheets();
            if (sheetIndex >= sheetNumbers) {
                throw new RuntimeException("Check sheet index!");
            }
            sheet = workbook.getSheet(sheetIndex);
            int columns = sheet.getColumns();
            int rows = sheet.getRows();
            if (endRow == -1) {
                endRow = rows;
            }
            if (endColumn == -1) {
                endColumn = columns;
            }

            if (endColumn > columns || endRow > rows
                    || endColumn <= startColumn
                    || endRow <= startRow) {
                throw new RuntimeException("Index out of range!");
            }

            if (readHorizontallyFlag) {
                for (int row = startRow; row < endRow; row++) {

                    ArrayList<String> currentRow = new ArrayList<>();
                    for (int col = startColumn; col < endColumn; col++) {
                        cell = sheet.getCell(col, row);
                        currentRow.add(cell.getContents());
                    }
                    result.add(currentRow);
                }
            } else {
                for (int col = startColumn; col < endColumn; col++) {

                    ArrayList<String> currentCol = new ArrayList<>();
                    for (int row = startRow; row < endRow; row++) {
                        cell = sheet.getCell(col, row);
                        currentCol.add(cell.getContents());
                    }
                    result.add(currentCol);
                }
            }

        } catch (IOException | BiffException e) {
            e.printStackTrace();

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }


    // Below is for test
    public static void main(String[] args) {

        String filename = "resources/a.xls";

        ArrayList<ArrayList<String>> result1 = new ArrayList<>();
        ArrayList<ArrayList<String>> result2 = new ArrayList<>();

        ExcelReader.importXlsFile(
                filename, 0, true,
                0, -1, 0, -1,
                result1);

        ExcelReader.importXlsFile(
                filename, 0, false,
                0, -1, 0, -1,
                result2);

        if (!result1.isEmpty()) {
            System.out.println("haha");
        }
    }
}