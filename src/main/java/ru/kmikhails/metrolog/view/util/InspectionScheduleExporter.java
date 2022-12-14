package ru.kmikhails.metrolog.view.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.kmikhails.metrolog.domain.Device;

import javax.xml.ws.Holder;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InspectionScheduleExporter {
    private static final String SHEET_NAME = "График поверки";
    private static final short FONT_SIZE_7 = 7;
    private static final short FONT_SIZE_11 = 11;
    private static final short FONT_SIZE_12 = 12;
    private static final short FONT_SIZE_14 = 14;
    private static final short FONT_SIZE_16 = 16;
    private static final String TIMES_NEW_ROMAN_FONT = "Times New Roman";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void export(List<Device> accounts, String year, String measurementType) {
        String[] headers = ("№№ пп;Номер в Г/р СИ;Наименование и тип СИ;Идентификационный номер СИ;" +
                "предел (диапазон измерений);класс точности, погрешность;Периодичность поверки, мес.;" +
                "Дата последней поверки;Место проведения поверки;Сроки проведения поверки;Отметка о выполнении(дата поверки)").split(";");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            CellStyle cellStyle = workbook.createCellStyle();
            XSSFFont cellFont = (XSSFFont) workbook.createFont();
            cellFont.setFontName(TIMES_NEW_ROMAN_FONT);
            cellFont.setFontHeightInPoints(FONT_SIZE_12);
            cellStyle.setFont(cellFont);

            CellStyle borderStyle = workbook.createCellStyle();
            borderStyle.setBorderLeft(BorderStyle.THIN);

            int mainHeaderSize = addMainHeader(sheet, workbook, year, measurementType);
            int tableHeaderSize = addTableHeader(sheet, headers, cellStyle, borderStyle);
            int headerSize = mainHeaderSize + tableHeaderSize;
            int lastRow = addTableData(accounts, sheet, cellStyle, headerSize);
            printLastRow(workbook, sheet, lastRow);


            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileName = path.substring(0, path.length() - 1) + "график_поверки_" + measurementType + ".xlsx";
            File exportFile = new File(fileName);
            if (exportFile.exists()) {
                exportFile.delete();
            }

            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int addMainHeader(Sheet sheet, Workbook workbook, String year, String measurementType) {
        CellStyle TNR7 = workbook.createCellStyle();
        XSSFFont TNR7Font = (XSSFFont) workbook.createFont();
        TNR7Font.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR7Font.setFontHeightInPoints(FONT_SIZE_7);
        TNR7.setVerticalAlignment(VerticalAlignment.CENTER);
        TNR7.setFont(TNR7Font);

        CellStyle TNR11 = workbook.createCellStyle();
        XSSFFont TNR11Font = (XSSFFont) workbook.createFont();
        TNR11Font.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR11Font.setFontHeightInPoints(FONT_SIZE_11);
        TNR11.setFont(TNR11Font);

        CellStyle TNR12 = workbook.createCellStyle();
        XSSFFont TNR12Font = (XSSFFont) workbook.createFont();
        TNR12Font.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR12Font.setFontHeightInPoints(FONT_SIZE_12);
        TNR12.setFont(TNR12Font);

        CellStyle TNR12Bold = workbook.createCellStyle();
        XSSFFont TNR12BoldFont = (XSSFFont) workbook.createFont();
        TNR12BoldFont.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR12BoldFont.setFontHeightInPoints(FONT_SIZE_12);
        TNR12BoldFont.setBold(true);
        TNR12Bold.setAlignment(HorizontalAlignment.CENTER);
        TNR12Bold.setFont(TNR12BoldFont);

        CellStyle TNR14Bold = workbook.createCellStyle();
        XSSFFont TNR14BoldFont = (XSSFFont) workbook.createFont();
        TNR14BoldFont.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR14BoldFont.setFontHeightInPoints(FONT_SIZE_14);
        TNR14BoldFont.setBold(true);
        TNR14Bold.setAlignment(HorizontalAlignment.CENTER);
        TNR14Bold.setFont(TNR14BoldFont);

        CellStyle TNR16Bold = workbook.createCellStyle();
        XSSFFont TNR16BoldFont = (XSSFFont) workbook.createFont();
        TNR16BoldFont.setFontName(TIMES_NEW_ROMAN_FONT);
        TNR16BoldFont.setFontHeightInPoints(FONT_SIZE_16);
        TNR16BoldFont.setBold(true);
        TNR16Bold.setAlignment(HorizontalAlignment.CENTER);
        TNR16Bold.setFont(TNR16BoldFont);

        int height = 0;
        Row row1 = sheet.createRow(0);
        Cell cellA1 = row1.createCell(0);
        cellA1.setCellStyle(TNR11);
        cellA1.setCellValue("ООО \"СКБ СТРОЙПРИБОР\"");
        Cell cellL1 = row1.createCell(11);
        cellL1.setCellStyle(TNR12Bold);
        cellL1.setCellValue("УТВЕРЖДАЮ");
        height += row1.getHeight();

        Row row2 = sheet.createRow(1);
        Cell cellA2 = row2.createCell(0);
        cellA2.setCellStyle(TNR7);
        cellA2.setCellValue("наименование владельца средств измерения");
        Cell cellK2 = row2.createCell(10);
        cellK2.setCellStyle(TNR12);
        height += row2.getHeight();

        sheet.addMergedRegion(CellRangeAddress.valueOf("K2:N2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("G4:J4"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("G5:J5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("K5:L5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("F6:K6"));

        cellK2.setCellValue("Главный метролог ООО \"СКБ Стройприбор\"");

        Row row3 = sheet.createRow(2);
        row3.setHeight((short) 146);
        height += row3.getHeight();

        Row row4 = sheet.createRow(3);
        row4.setHeight((short) 407);
        Cell cellA4 = row4.createCell(0);
        cellA4.setCellStyle(TNR12);
        cellA4.setCellValue("тел.(351) 277-8-555");
        Cell cellG4 = row4.createCell(6);
        cellG4.setCellStyle(TNR16Bold);
        cellG4.setCellValue("ГРАФИК");
        Cell cellM4 = row4.createCell(12);
        cellM4.setCellStyle(TNR12);
        cellM4.setCellValue("И.А.Капралова");
        height += row4.getHeight();

        Row row5 = sheet.createRow(4);
        Cell cellG5 = row5.createCell(6);
        cellG5.setCellStyle(TNR14Bold);
        cellG5.setCellValue("поверки эталонов и СИ лаборатории");
        height += row5.getHeight();

        CellStyle K5Style = workbook.createCellStyle();
        XSSFFont K5Font = (XSSFFont) workbook.createFont();
        K5Font.setFontName(TIMES_NEW_ROMAN_FONT);
        K5Font.setFontHeightInPoints(FONT_SIZE_7);
        K5Style.setAlignment(HorizontalAlignment.CENTER);
        K5Style.setVerticalAlignment(VerticalAlignment.CENTER);
        K5Style.setFont(TNR7Font);
        K5Style.setBorderTop(BorderStyle.THIN);
        Cell cellK5 = row5.createCell(10);
        cellK5.setCellStyle(K5Style);
        cellK5.setCellValue("подпись");

        Row row6 = sheet.createRow(5);
        Cell cellF7 = row6.createCell(5);
        cellF7.setCellStyle(TNR12Bold);
        cellF7.setCellValue("на  " + year + "  год");
        height += row6.getHeight();

        Row row7 = sheet.createRow(6);
        Cell cellA7 = row7.createCell(0);
        cellA7.setCellStyle(TNR12);
        cellA7.setCellValue("Вид измерений");
        height += row7.getHeight();

        Row row8 = sheet.createRow(7);
        Cell cellA8 = row8.createCell(0);
        cellA8.setCellStyle(TNR12);
        cellA8.setCellValue(measurementType);
        height += row8.getHeight();

        Row row9 = sheet.createRow(8);
        row9.setHeight((short) 146);
        height += row9.getHeight();

        return height;
    }

    private static int addTableHeader(Sheet sheet, String[] headers, CellStyle cellStyle, CellStyle borderStyle) {
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        Row tableHeaderRow = sheet.createRow(9);
        tableHeaderRow.setHeight((short) 487);
        Row tableHeaderNexRow = sheet.createRow(10);
        tableHeaderNexRow.setHeight((short) 812);
        int columnNumber = 0;
        sheet.addMergedRegion(CellRangeAddress.valueOf("A10:A11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B10:B11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("F10:F11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("I10:I11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("J10:J11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("K10:K11"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("L10:L11"));
        sheet.addMergedRegion(new CellRangeAddress(9, 10, 2, 4));
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(9, 10, 12, 13));
        for (String header : headers) {
            Cell cell;
            if (columnNumber == 6 || columnNumber == 7) {
                cell = tableHeaderNexRow.createCell(columnNumber++);
            } else {
                cell = tableHeaderRow.createCell(columnNumber++);
            }
            if (columnNumber == 3) {
                columnNumber = columnNumber + 2;
            }

            cell.setCellStyle(cellStyle);
            cell.setCellValue(header);
            cellStyle.setWrapText(true);
            CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
            CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
        }
        Row row12 = sheet.createRow(11);
        for (int i = 0; i < 2; i++) {
            Cell numberCell = row12.createCell(i);
            numberCell.setCellStyle(cellStyle);
            numberCell.setCellValue(i + 1);

        }
        for (int i = 3; i < headers.length; i++) {
            Cell numberCell = row12.createCell(i + 2);
            numberCell.setCellStyle(cellStyle);
            numberCell.setCellValue(i + 1);

        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("C12:E12"));
        Cell cell3 = row12.createCell(2);
        cell3.setCellStyle(cellStyle);
        cell3.setCellValue(3);
        sheet.addMergedRegion(CellRangeAddress.valueOf("M12:N12"));
        Cell cell11 = row12.createCell(10);
        cell11.setCellStyle(cellStyle);
        cell11.setCellValue(11);

        Cell cellO10 = tableHeaderRow.createCell(14);
        cellO10.setCellStyle(borderStyle);
        Cell cellO11 = tableHeaderNexRow.createCell(14);
        cellO11.setCellStyle(borderStyle);
        Cell cellO12 = row12.createCell(14);
        cellO12.setCellStyle(borderStyle);

        return tableHeaderRow.getHeight() + tableHeaderNexRow.getHeight() + row12.getHeight();
    }

    private static int addTableData(List<Device> devices, Sheet sheet, CellStyle cellStyle, int headerSize) {
        Holder<Integer> rowNumber = new Holder<>(12);
        Holder<Integer> orderNumber = new Holder<>(1);
        for (Device device : devices) {
            Integer rownum = rowNumber.value++;
            Row accountRow = sheet.createRow(rownum);

//            if (headerSize > 11900 && !isExtraHeader) {
//                printExtraHeader(sheet, cellStyle, borderStyle, rownum, accountRow);
//                isExtraHeader = true;
//
//                rownum = rowNumber.value++;
//                accountRow = sheet.createRow(rownum);
//            }
//            if (headerSize > 11900 * 2) {
//                printExtraHeader(sheet, cellStyle, borderStyle, rownum, accountRow);
//                rownum = rowNumber.value++;
//                accountRow = sheet.createRow(rownum);
//            }

            int heigntIndex = 1;
            accountRow.setHeight((short) 410);
            Cell cell = accountRow.createCell(0);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.getRow().setHeightInPoints(cell.getSheet().getDefaultRowHeightInPoints() * 2);
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(orderNumber.value++);
            sheet.setColumnWidth(0, 1522);

            cell = accountRow.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(device.getRegNumber());
            sheet.setColumnWidth(1, 2746);

            cell = accountRow.createCell(2);
            cell.setCellStyle(cellStyle);
            String name = device.getName().getDeviceName();
            if (name.contains("Динамометр")) {
                name = "Динамометр";
            }
            name = name + " " + device.getType();
            if (name.length() > 31) {
                heigntIndex = 2;
            }
            if (name.length() > 62) {
                heigntIndex = 3;
            }
            cell.setCellValue(name);
            sheet.addMergedRegion(new CellRangeAddress(rowNumber.value - 1, rowNumber.value - 1, 2, 4));
            sheet.setColumnWidth(2, 2018);

            cell = accountRow.createCell(3);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(3, 2184);

            cell = accountRow.createCell(4);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(4, 3640);

            cell = accountRow.createCell(5);
            cell.setCellStyle(cellStyle);
            String factoryNumber = device.getFactoryNumber();
            if (factoryNumber.length() > 13 && heigntIndex < 2) {
                heigntIndex = 2;
            }
            if (factoryNumber.length() > 26 && heigntIndex < 3) {
                heigntIndex = 3;
            }
            if (factoryNumber.length() > 39) {
                heigntIndex = 4;
            }
            if (factoryNumber.length() > 52) {
                heigntIndex = 5;
            }
            cell.setCellValue(factoryNumber);
            sheet.setColumnWidth(5, 3144);

            Row preHeaderRow = sheet.getRow(9);
            Cell preHeaderCell = preHeaderRow.createCell(6);
            preHeaderCell.setCellStyle(cellStyle);
            preHeaderCell.setCellValue("Метрологические характеристики");

            cell = accountRow.createCell(6);
            cell.setCellStyle(cellStyle);
            String range = device.getRange();
            if (range.length() > 21 && heigntIndex < 2) {
                heigntIndex = 2;
            }
            cell.setCellValue(range);
            sheet.setColumnWidth(6, 4765);

            cell = accountRow.createCell(7);
            cell.setCellStyle(cellStyle);
            String category = device.getCategory();
            if (category.length() > 22 && heigntIndex < 2) {
                heigntIndex = 2;
            }
            cell.setCellValue(category);
            sheet.setColumnWidth(7, 4401);

            cell = accountRow.createCell(8);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(device.getInspectionFrequency());
            sheet.setColumnWidth(8, 4103);

            cell = accountRow.createCell(9);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(FORMATTER.format(device.getLastInspectionDate()));
            sheet.setColumnWidth(9, 3144);

            cell = accountRow.createCell(10);
            cell.setCellStyle(cellStyle);
            String inspectionPlace = device.getInspectionPlace().getInspectionPlace();
            if (inspectionPlace.contains("Челябинский")) {
                inspectionPlace = "ФБУ \"ЧЦСМ\"";
            } else if (inspectionPlace.contains("Менделеева")) {
                inspectionPlace = "ВНИИМ им. Д.И.Менделеева";
            }
            if (inspectionPlace.length() > 14 && heigntIndex < 2) {
                heigntIndex = 2;
            }
            cell.setCellValue(inspectionPlace);
            sheet.setColumnWidth(10, 4269);

            cell = accountRow.createCell(11);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(FORMATTER.format(device.getNextInspectionDate()));
            sheet.setColumnWidth(11, 3077);

            String merge = "M" + (rownum + 1) + ":N" + (rownum + 1);
            sheet.addMergedRegion(CellRangeAddress.valueOf(merge));

            cell = accountRow.createCell(12);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("");
            sheet.setColumnWidth(12, 3408);

            cell = accountRow.createCell(13);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(13, 1721);

            accountRow.setHeight((short) (380 * heigntIndex));

            headerSize += accountRow.getHeight();
        }
        return sheet.getLastRowNum();
    }

    private static void printLastRow(Workbook workbook, Sheet sheet, int lastRow) {
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont cellFont = (XSSFFont) workbook.createFont();
        cellFont.setFontName(TIMES_NEW_ROMAN_FONT);
        cellFont.setFontHeightInPoints(FONT_SIZE_12);
        cellStyle.setFont(cellFont);

        Row signatureRow = sheet.createRow(lastRow + 3);
        Cell chiefCell = signatureRow.createCell(1);
        chiefCell.setCellStyle(cellStyle);
        chiefCell.setCellValue("Руководитель калибровочной лаборатории");

        Cell signatureCell = signatureRow.createCell(7);
        signatureCell.setCellStyle(cellStyle);
        signatureCell.setCellValue("М.Д.Черникова");
    }

    private static void printExtraHeader(Sheet sheet, CellStyle cellStyle, CellStyle borderStyle,
                                         Integer rownum, Row accountRow) {
        for (int i = 0; i < 2; i++) {
            Cell numberCell = accountRow.createCell(i);
            numberCell.setCellStyle(cellStyle);
            numberCell.setCellValue(i + 1);
        }
        for (int i = 3; i < 11; i++) {
            Cell numberCell = accountRow.createCell(i + 2);
            numberCell.setCellStyle(cellStyle);
            numberCell.setCellValue(i + 1);
        }
        Cell cell3 = accountRow.createCell(2);
        cell3.setCellStyle(cellStyle);
        cell3.setCellValue(3);
        String merge11 = "M" + (rownum + 1) + ":N" + (rownum + 1);
        sheet.addMergedRegion(CellRangeAddress.valueOf(merge11));

        String merge3 = "C" + (rownum + 1) + ":E" + (rownum + 1);
        sheet.addMergedRegion(CellRangeAddress.valueOf(merge3));

        Cell cellO12 = accountRow.createCell(14);
        cellO12.setCellStyle(borderStyle);
    }
}
