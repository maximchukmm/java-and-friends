package edu.frameworks.org.apache.poi;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class XlsDemo {

    public static void main(String[] args) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);

        printAllBuiltInFormatsOfCell();

        XSSFSheet sheet = workbook.createSheet("Report result");
        final FileOutputStream fileOut = new FileOutputStream("/home/mishamm/Other/shared folder for virtual machines/workbook.xlsx");

        fillTitles(sheet);
        fillData(workbook, sheet);

        workbook.write(fileOut);
        fileOut.close();
    }

    private static void printAllBuiltInFormatsOfCell() {
        final String[] cellFormats = BuiltinFormats.getAll();
        for (int i = 0; i < cellFormats.length; i++) {
            System.out.println(i + " : " + cellFormats[i]);
        }
    }

    private static void fillData(XSSFWorkbook workbook, XSSFSheet sheet) {
        final List<List<String>> rowsData = prepareRowsData();
        List<CellStyle> cellStyles = ReportCellFormat.getStyles(workbook, rowsData.get(0));
        // final List<Integer> cellTypes = ReportCellType.getTypes(rowsData.get(0));
        for (int i = 0; i < rowsData.size(); i++) {
            final XSSFRow row = sheet.createRow(i + 1);
            System.out.println("Row: " + (i + 1));
            for (int j = 0; j < rowsData.get(i).size(); j++) {
                // final XSSFCell cell = row.createCell(j, cellTypes.get(j));
                final XSSFCell cell = row.createCell(j);
                cell.setCellStyle(cellStyles.get(j));
                try {
                    cell.setCellValue(Double.parseDouble(rowsData.get(i).get(j)));
                } catch (NumberFormatException e) {
                    cell.setCellValue(rowsData.get(i).get(j));
                }
                try {
                    System.out.println("value: " + cell.getStringCellValue());
                } catch (Exception e) {
                    System.out.println("value: " + cell.getNumericCellValue());
                }
                System.out.println("format: " + cell.getCellStyle().getDataFormatString());
                System.out.println("type: " + cell.getCellType());
            }
            System.out.println();
        }
    }

    private static List<List<String>> prepareRowsData() {
        List<String> firstRowData = new ArrayList<>();
        firstRowData.add("1234");
        firstRowData.add("+123");
        firstRowData.add("-123");
        firstRowData.add("123.1267");
        firstRowData.add("+123.1267");
        firstRowData.add("-123.1267");
        firstRowData.add("строка");
        firstRowData.add("123строка123");
        firstRowData.add("123.123строка");

        List<String> secondRowData = new ArrayList<>();
        secondRowData.add("1234");
        secondRowData.add("+123");
        secondRowData.add("-123");
        secondRowData.add("123.1267");
        secondRowData.add("+123.1267");
        secondRowData.add("-123.1267");
        secondRowData.add("строка");
        secondRowData.add("123строка123");
        secondRowData.add("123.123строка");

        List<String> thirdRowData = new ArrayList<>();
        thirdRowData.add("1234");
        thirdRowData.add("+123");
        thirdRowData.add("-123");
        thirdRowData.add("123.1267");
        thirdRowData.add("+123.1267");
        thirdRowData.add("-123.1267");
        thirdRowData.add("строка");
        thirdRowData.add("123строка123");
        thirdRowData.add("123.123строка");

        List<List<String>> rowsData = new ArrayList<>();
        rowsData.add(firstRowData);
        rowsData.add(secondRowData);
        rowsData.add(thirdRowData);
        return rowsData;
    }

    private static void fillTitles(XSSFSheet sheet) {
        List<String> columnTitles = new ArrayList<>();
        columnTitles.add("Положительное целое число без знака");
        columnTitles.add("Положительное целое число со знаком");
        columnTitles.add("Отрицательное целое число");
        columnTitles.add("Положительное число с плавающей точкой без знака");
        columnTitles.add("Положительное число с плавающей точкой со знаком");
        columnTitles.add("Отрицательное число с плавающей точкой");
        columnTitles.add("Строка1");
        columnTitles.add("Строка2");
        columnTitles.add("Строка3");
        final XSSFRow titles = sheet.createRow(0);
        for (int i = 0; i < columnTitles.size(); i++) {
            final XSSFCell cell = titles.createCell(i);
            cell.setCellValue(columnTitles.get(i));
        }
    }

    private static class ReportCellFormat {

        private static final String GENERAL_FORMAT = BuiltinFormats.getBuiltinFormat(0);

        private static Map<Pattern, String> formats = new HashMap<>();

        static {
            formats.put(Pattern.compile("^[-+]?\\d+$"), "0.00");
            formats.put(Pattern.compile("^[-+]?\\d+(\\.\\d+)$"), "0.000");
        }

        private static String getFormatForValue(String cellValue) {
            for (Map.Entry<Pattern, String> style : formats.entrySet())
                if (style.getKey().matcher(cellValue).matches())
                    return style.getValue();
            return GENERAL_FORMAT;
        }

        private static List<CellStyle> getStyles(XSSFWorkbook wb, List<String> rowValues) {
            return rowValues.stream()
                    .map(value -> {
                        final XSSFDataFormat format = wb.createDataFormat();
                        final XSSFCellStyle style = wb.createCellStyle();
                        style.setDataFormat(format.getFormat(ReportCellFormat.getFormatForValue(value)));
                        return style;
                    })
                    .collect(Collectors.toList());
        }
    }
    //
    // private static class ReportCellType {
    //
    //     private static final int NUMERIC_TYPE = XSSFCell.CELL_TYPE_NUMERIC;
    //     private static final int STRING_TYPE = XSSFCell.CELL_TYPE_STRING;
    //
    //     private static Map<Pattern, Integer> types = new HashMap<>();
    //
    //     static {
    //         types.put(Pattern.compile("^[-+]?\\d+$"), NUMERIC_TYPE);
    //     }
    //
    //     private static List<Integer> getTypes(List<String> rowValues) {
    //         return rowValues.stream()
    //                 .map(ReportCellType::getTypeForValue)
    //                 .collect(Collectors.toList());
    //     }
    //
    //     private static Integer getTypeForValue(String value) {
    //         for (Map.Entry<Pattern, Integer> type : types.entrySet())
    //             if (type.getKey().matcher(value).matches())
    //                 return type.getValue();
    //         return STRING_TYPE;
    //     }
    // }
}
