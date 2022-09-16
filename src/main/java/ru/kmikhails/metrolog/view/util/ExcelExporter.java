package ru.kmikhails.metrolog.view.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.kmikhails.metrolog.domain.Device;

import javax.xml.ws.Holder;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class ExcelExporter {
    private static final String SHEET_NAME = "СИ с истекшим сроком";
    private static final short TITLE_FONT_SIZE = 14;
    private static final short CELL_FONT_SIZE = 12;
    private static final String FONT_NAME = "Times New Roman";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public static void export(List<Device> accounts) {
        String[] headers = "Наименование СИ,Тип СИ,Заводской №,Срок поверки".split(",");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setFontName(FONT_NAME);
            font.setFontHeightInPoints(TITLE_FONT_SIZE);
            font.setBold(true);
            headerStyle.setFont(font);

            CellStyle cellStyle = workbook.createCellStyle();
            XSSFFont cellFont = (XSSFFont) workbook.createFont();
            cellFont.setFontName(FONT_NAME);
            cellFont.setFontHeightInPoints(CELL_FONT_SIZE);
            cellStyle.setFont(cellFont);

            Cell headerCell = header.createCell(1);
            headerCell.setCellValue(SHEET_NAME);
            headerCell.setCellStyle(headerStyle);

            addTableHeader(sheet, headers, cellStyle);
            addTabelData(accounts, sheet, cellStyle);


            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "си_истекший_срок.xlsx";
            File exportFile = new File(fileLocation);
            if (exportFile.exists()) {
                exportFile.delete();
            }

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTableHeader(Sheet sheet, String[] headers, CellStyle cellStyle) {
        Row tableHeader = sheet.createRow(2);
        Holder<Integer> columnNumber = new Holder<>(1);
        Stream.of(headers)
                .forEach(columnTitle -> {
                    Cell cell = tableHeader.createCell(columnNumber.value++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(columnTitle);
                });
    }

    private static void addTabelData(List<Device> devices, Sheet sheet, CellStyle cellStyle) {
        Holder<Integer> rowNumber = new Holder<>(3);
        devices.forEach(device -> {
            Row accountRow = sheet.createRow(rowNumber.value++);
            Cell cell = accountRow.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(device.getName().getDeviceName());
            sheet.autoSizeColumn(1);

            cell = accountRow.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(device.getType());
            sheet.autoSizeColumn(2);

            cell = accountRow.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(device.getFactoryNumber());
            sheet.autoSizeColumn(3);

            cell = accountRow.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(FORMATTER.format(device.getNextInspectionDate()));
            sheet.autoSizeColumn(4);
        });
    }
}
