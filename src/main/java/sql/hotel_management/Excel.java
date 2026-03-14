package sql.hotel_management;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Excel {
    public static void WriteExcel(String SheetName, int rNum, int cNum, String DATA, String filestream) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filestream);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet s = wb.getSheet(SheetName);
            XSSFRow r = s.getRow(rNum);
            if (r == null) {
                r = s.createRow(rNum);
            }
            XSSFCell c = r.createCell(cNum);
            c.setCellValue(DATA);
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(filestream);
            wb.write(fileOutputStream);
            fileOutputStream.close();
            wb.close();
        } catch (Exception e) {
            System.out.println("WriteExcel catch block");
            e.printStackTrace();
        }
    }

    public static String ReadExcel(String SheetName, int rNum, int cNum, String filestream) {
        String data = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filestream);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet s = wb.getSheet(SheetName);
            XSSFRow r = s.getRow(rNum);
            if (r == null) {
                return null;
            }
            XSSFCell c = r.getCell(cNum);
            if (c == null) {
                return null;
            }
            data = c.getStringCellValue();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("Read Excel Catch block");
            e.printStackTrace();
        }
        return data;
    }
}
