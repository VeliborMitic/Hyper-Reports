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
import org.jetbrains.annotations.NotNull;
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

    public void generateReportForMonth(String companyName, int year, int month)
            throws SQLException, IOException, ClassNotFoundException {

        List<HyperReport> dailyReportList = reportRepository.generateReportForMonth(companyName, year, month);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Monthly Report");

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle headerCellStyle = createHeaderStyle(workbook);

            CellStyle companyNameCellStyle = createCompanyCellStyle(workbook);
            Cell companyNameCell = createCompanyNameCell(sheet, companyNameCellStyle);

            companyNameCell.setCellValue(companyName.toUpperCase() + " " + year + "-" + month + " Monthly Report");

            String[] columns = {"Date", "Turnover"};
            createRowCells(sheet, headerCellStyle, columns);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            int rowNum = 2;
            for (HyperReport report : dailyReportList) {
                Row row = sheet.createRow(rowNum++);
                Cell date = row.createCell(0);
                date.setCellValue(DateUtils.convertLocalDateToDate(report.getDate()));
                date.setCellStyle(dateCellStyle);
                Cell turnover = row.createCell(1);
                turnover.setCellValue(report.getTurnover());
            }

            createSumRow(sheet, headerCellStyle);
            setDefaultColumnWidth(sheet, columns);

            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + month + "-" + "MonthlyReport.xlsx";
            saveOutputFile(workbook, outputFileName);
        }
    }

    public void generateReportForQuarter(String companyName, int year, int quarter)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> monthList = reportRepository.generateReportForQuarter(companyName, year, quarter);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Quarterly Report");

            CellStyle headerCellStyle = createHeaderStyle(workbook);
            CellStyle companyNameCellStyle = createCompanyCellStyle(workbook);

            Cell companyNameCell = createCompanyNameCell(sheet, companyNameCellStyle);
            companyNameCell.setCellValue(companyName.toUpperCase()
                    + " " + year + "-" + quarter + "thQuarter" + " Quarterly Report");

            String[] columns = {"Month", "Turnover"};
            createRowCells(sheet, headerCellStyle, columns);
            int rowNum = 2;
            for (HyperReport report : monthList) {
                rowNum = getRowNum(sheet, rowNum, report, report.getMonth());
            }

            createSumRow(sheet, headerCellStyle);
            setDefaultColumnWidth(sheet, columns);

            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + quarter + "thQuarter-" + "QuarterlyReport.xlsx";
            saveOutputFile(workbook, outputFileName);
        }
    }

    public void generateReportForYear(String companyName, int year)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> quarterList =
                reportRepository.generateReportForYear(companyName, year);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Yearly Report");

            CellStyle headerCellStyle = createHeaderStyle(workbook);
            CellStyle companyNameCellStyle = createCompanyCellStyle(workbook);

            Cell companyNameCell = createCompanyNameCell(sheet, companyNameCellStyle);
            companyNameCell.setCellValue(companyName.toUpperCase()
                    + " " + year + " Yearly Report");

            String[] columns = {"Quarter", "Turnover"};
            createRowCells(sheet, headerCellStyle, columns);
            int rowNum = 2;
            for (HyperReport report : quarterList) {
                rowNum = getRowNum(sheet, rowNum, report, report.getQuarter());
            }

            createSumRow(sheet, headerCellStyle);
            setDefaultColumnWidth(sheet, columns);

            String outputFileName = Util.REPORT_OUTPUT_PATH + companyName +
                    "-" + year + "-" + "YearlyReport.xlsx";
            saveOutputFile(workbook, outputFileName);
        }
    }

    private int getRowNum(XSSFSheet sheet, int rowNum, HyperReport report, int month2) {
        Row row = sheet.createRow(rowNum++);
        Cell month = row.createCell(0);
        month.setCellValue(month2);
        Cell turnover = row.createCell(1);
        turnover.setCellValue(report.getTurnover());
        return rowNum;
    }

    @NotNull
    private static CellStyle createCompanyCellStyle(XSSFWorkbook workbook) {
        Font companyNameFont = workbook.createFont();
        companyNameFont.setBold(true);
        companyNameFont.setFontHeightInPoints((short) 20);
        companyNameFont.setColor(IndexedColors.RED.getIndex());
        CellStyle companyNameCellStyle = workbook.createCellStyle();
        companyNameCellStyle.setFont(companyNameFont);
        companyNameCellStyle.setAlignment(HorizontalAlignment.LEFT);
        return companyNameCellStyle;
    }

    @NotNull
    private Cell createCompanyNameCell(XSSFSheet sheet, CellStyle companyNameCellStyle) {
        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellStyle(companyNameCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        return companyNameCell;
    }

    @NotNull
    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        headerCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        return headerCellStyle;
    }

    private static void setDefaultColumnWidth(XSSFSheet sheet, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            sheet.setDefaultColumnWidth(20);
        }
    }

    private static void saveOutputFile(XSSFWorkbook workbook, String outputFileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFileName);
        workbook.write(fileOut);
        fileOut.close();
    }

    private static void createSumRow(XSSFSheet sheet, CellStyle headerCellStyle) {
        Row sumRow = sheet.createRow(sheet.getLastRowNum() + 1);
        Cell totalCell = sumRow.createCell(0);
        Cell sumCell = sumRow.createCell(1);
        totalCell.setCellStyle(headerCellStyle);
        sumCell.setCellStyle(headerCellStyle);
        totalCell.setCellValue("TOTAL:");
        sumCell.setCellType(CellType.FORMULA);
        sumCell.setCellFormula("SUM(B2:B" + (sheet.getLastRowNum()) + ")");
    }

    private static void createRowCells(XSSFSheet sheet, CellStyle headerCellStyle, String[] columns) {
        Row headerRow = sheet.createRow(1);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }


}
