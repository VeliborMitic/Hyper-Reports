package org.prime.internship.service;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.prime.internship.entity.dto.HyperReport;
import org.prime.internship.repository.ReportRepository;
import org.prime.internship.utility.DateUtils;
import org.prime.internship.utility.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportService {
    private ReportRepository reportRepository;

    public ReportService() {
        this.reportRepository = new ReportRepository();
    }

    public void generateReportForMonth (String companyName, int year, int month)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> dailyReportList = reportRepository.generateReportForMonth(companyName, year, month);

        String[] columns = {"Date", "Turnover"};

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Monthly Report");
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            headerCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

            // Create a Font for styling companyName cell
            Font companyNameFont = workbook.createFont();
            companyNameFont.setBold(true);
            companyNameFont.setFontHeightInPoints((short) 20);
            companyNameFont.setColor(IndexedColors.RED.getIndex());
            // Create a CellStyle with the font
            CellStyle companyNameCellStyle = workbook.createCellStyle();
            companyNameCellStyle.setFont(companyNameFont);
            companyNameCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Create a Row for CompanyName
            Row nameRow = sheet.createRow(0);
            Cell companyNameCell = nameRow.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
            companyNameCell.setCellValue(companyName.toUpperCase() + " " + year + "-" + month + " Monthly Report");
            companyNameCell.setCellStyle(companyNameCellStyle);

            Row headerRow = sheet.createRow(1);
            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            // Create Other rows and cells with turnovers
            int rowNum = 2;
            for (HyperReport report : dailyReportList) {
                Row row = sheet.createRow(rowNum++);

                Cell date = row.createCell(0);
                    date.setCellValue(DateUtils.convertLocalDateToDate(report.getDate()));
                    date.setCellStyle(dateCellStyle);
                Cell turnover = row.createCell(1);
                    turnover.setCellValue(report.getTurnover());
            }

            Row sumRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell totalCell = sumRow.createCell(0);
            Cell sumCell = sumRow.createCell(1);
            totalCell.setCellStyle(headerCellStyle);
            sumCell.setCellStyle(headerCellStyle);
            totalCell.setCellValue("TOTAL:");
            sumCell.setCellType(CellType.FORMULA);
            sumCell.setCellFormula("SUM(B2:B"  + (sheet.getLastRowNum()) + ")");

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.setDefaultColumnWidth(20);
            }

            // Write the output to a file
            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + month + "-" + "MonthlyReport.xlsx";
            FileOutputStream fileOut = new FileOutputStream(outputFileName);
            workbook.write(fileOut);
            fileOut.close();
        }
    }



    public void generateReportForQuarter (String companyName, int year, int quarter)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> monthList = reportRepository.generateReportForQuarter (companyName, year, quarter);

        String[] columns = {"Month", "Turnover"};

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Quarterly Report");
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            headerCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

            // Create a Font for styling companyName cell
            Font companyNameFont = workbook.createFont();
            companyNameFont.setBold(true);
            companyNameFont.setFontHeightInPoints((short) 20);
            companyNameFont.setColor(IndexedColors.RED.getIndex());
            // Create a CellStyle with the font
            CellStyle companyNameCellStyle = workbook.createCellStyle();
            companyNameCellStyle.setFont(companyNameFont);
            companyNameCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Create a Row
            Row nameRow = sheet.createRow(0);
            Cell companyNameCell = nameRow.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            companyNameCell.setCellValue(companyName.toUpperCase()
                    + " " + year + "-" + quarter + "thQuarter" + " Quarterly Report");
            companyNameCell.setCellStyle(companyNameCellStyle);

            Row headerRow = sheet.createRow(1);
            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with turnovers
            int rowNum = 2;
            for (HyperReport report : monthList) {
                Row row = sheet.createRow(rowNum++);
                Cell month = row.createCell(0);
                    month.setCellValue(report.getMonth());
                Cell turnover = row.createCell(1);
                    turnover.setCellValue(report.getTurnover());
            }

            Row sumRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell totalCell = sumRow.createCell(0);
            Cell sumCell = sumRow.createCell(1);
            totalCell.setCellStyle(headerCellStyle);
            sumCell.setCellStyle(headerCellStyle);
            totalCell.setCellValue("TOTAL:");
            sumCell.setCellType(CellType.FORMULA);
            sumCell.setCellFormula("SUM(B2:B" + (sheet.getLastRowNum()) + ")");

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.setDefaultColumnWidth(20);
            }

            // Write the output to a file
            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + quarter + "thQuarter-" + "QuarterlyReport.xlsx";
            FileOutputStream fileOut = new FileOutputStream(outputFileName);
            workbook.write(fileOut);
            fileOut.close();
        }
    }

    public void generateReportForYear (String companyName, int year)
            throws SQLException, IOException, ClassNotFoundException{
        List<HyperReport> quarterList =
                reportRepository.generateReportForYear(companyName, year);

        String[] columns = {"Quarter", "Turnover"};

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Yearly Report");
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            headerCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

            // Create a Font for styling companyName cell
            Font companyNameFont = workbook.createFont();
            companyNameFont.setBold(true);
            companyNameFont.setFontHeightInPoints((short) 20);
            companyNameFont.setColor(IndexedColors.RED.getIndex());
            // Create a CellStyle with the font
            CellStyle companyNameCellStyle = workbook.createCellStyle();
            companyNameCellStyle.setFont(companyNameFont);
            companyNameCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Create a Row
            Row nameRow = sheet.createRow(0);
            Cell companyNameCell = nameRow.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            companyNameCell.setCellValue(companyName.toUpperCase()
                    + " " + year + " Yearly Report");
            companyNameCell.setCellStyle(companyNameCellStyle);

            Row headerRow = sheet.createRow(1);
            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with turnovers
            int rowNum = 2;
            for (HyperReport report : quarterList) {
                Row row = sheet.createRow(rowNum++);
                Cell quarter = row.createCell(0);
                    quarter.setCellValue(report.getQuarter());
                Cell turnover = row.createCell(1);
                turnover.setCellValue(report.getTurnover());
            }

            Row sumRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell totalCell = sumRow.createCell(0);
            Cell sumCell = sumRow.createCell(1);
            totalCell.setCellStyle(headerCellStyle);
            sumCell.setCellStyle(headerCellStyle);
            totalCell.setCellValue("TOTAL:");
            sumCell.setCellType(CellType.FORMULA);
            sumCell.setCellFormula("SUM(B2:B" + (sheet.getLastRowNum()) + ")");

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.setDefaultColumnWidth(20);
            }

            // Write the output to a file
            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + "YearlyReport.xlsx";
            FileOutputStream fileOut = new FileOutputStream(outputFileName);
            workbook.write(fileOut);
            fileOut.close();
        }
    }


}
