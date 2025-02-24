package utilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static Object[][] getExcelData(String filePath, String sheetName) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);

            if (row != null) {
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                data[i - 1][j] = String.valueOf(cell.getNumericCellValue());
                                break;
                            case STRING:
                                data[i - 1][j] = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                data[i - 1][j] = null;
                        }
                    } else {
                        data[i - 1][j] = null;
                    }
                }
            }
        }

        workbook.close();
        return data;
    }
}
