package org.prime.internship.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.prime.internship.cli.validator.CompanyValidator;
import org.prime.internship.cli.validator.MonthValidator;
import org.prime.internship.cli.validator.QuarterValidator;
import org.prime.internship.cli.validator.TopBottomValidator;
import org.prime.internship.cli.validator.YearValidator;
import org.prime.internship.service.ReportService;

import java.io.IOException;
import java.sql.SQLException;

@Parameters(commandNames = "report", commandDescription = "Generates reports for a Company")
public class CommandReport {

    @Parameter(names = {"-c", "--company"}, help = true,
            required = true,
            validateWith = CompanyValidator.class,
            description = "Name of the company to generate report")
    private String company;

    @Parameter(names = {"-y", "--year"}, help = true,
            required = true,
            validateWith = YearValidator.class,
            description = "Year for which you want to generate the report")
    private int year;

    @Parameter(names = {"-q", "--quarter"}, help = true,
            validateWith = QuarterValidator.class,
            description = "Quarter of the year (1 - 4) to generate the report for")
    private int quarter;

    @Parameter(names = {"-m", "--month"},
            validateWith = MonthValidator.class, help = true,
            description = "Month to generate report for")
    private int month;

    @Parameter(names = {"-t", "--top"},
            validateWith = TopBottomValidator.class, help = true,
            description = "Number of Top entities (1 - 100)")
    private int topN;

    @Parameter(names = {"-b", "--bottom"},
            validateWith = TopBottomValidator.class, help = true,
            description = "Number of Bottom entities (1 - 100)")
    private int bottomN;

    public  void run() throws SQLException, IOException, ClassNotFoundException {

        ReportService reportService = new ReportService();

        if (topN == 0 && bottomN == 0 && quarter ==0 && month == 0){
            reportService.generateReportForYear(company.toLowerCase(), year);
        }
        if (topN == 0 && bottomN == 0 && month == 0 && quarter != 0){
            reportService.generateReportForQuarter(company.toLowerCase(), year, quarter);
        }
        if (topN == 0 && bottomN == 0 && quarter == 0 && month != 0){
            reportService.generateReportForMonth(company.toLowerCase(), year, month);
        }
        if (topN != 0 && quarter ==0 && month != 0){
            reportService.monthlyTopNEmployees(company, year, month, topN);
            reportService.monthlyTopNCities(company, year, month, topN);
            reportService.monthlyTopNDepartments(company.toLowerCase(), year, month, topN);
        }
        if (topN != 0 && month == 0 && quarter != 0){
            reportService.quarterlyTopNEmployees(company, year, quarter, topN);
            reportService.quarterlyTopNCities(company, year, quarter, topN);
            reportService.quarterlyTopNDepartments(company.toLowerCase(), year, quarter, topN);
        }
        if (topN != 0 && quarter == 0 && month == 0){
            reportService.yearlyTopNEmployees(company, year, topN);
            reportService.yearlyTopNCities(company, year, topN);
            reportService.yearlyTopNDepartments(company.toLowerCase(), year, topN);
        }
        if (bottomN != 0 && quarter ==0 && month != 0){
            reportService.monthlyBottomNEmployees(company, year, month, bottomN);
            reportService.monthlyBottomNCities(company, year, month, bottomN);
            reportService.monthlyBottomNDepartments(company.toLowerCase(), year, month, bottomN);
        }
        if (bottomN != 0 && month == 0 && quarter != 0){
            reportService.quarterlyBottomNEmployees(company, year, quarter, bottomN);
            reportService.quarterlyBottomNCities(company, year, quarter, bottomN);
            reportService.quarterlyBottomNDepartments(company.toLowerCase(), year, quarter, bottomN);
        }
        if (bottomN != 0 && quarter == 0 && month == 0){
            reportService.yearlyBottomNEmployees(company, year, bottomN);
            reportService.yearlyBottomNCities(company, year, bottomN);
            reportService.yearlyBottomNDepartments(company.toLowerCase(), year, bottomN);
        }
    }
}
