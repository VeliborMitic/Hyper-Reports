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

    @Parameter(names = {"-c", "--company"},
            required = true,
            validateWith = CompanyValidator.class,
            description = "Company name")
    private String company;

    @Parameter(names = {"-y", "--year"},
            required = true,
            validateWith = YearValidator.class,
            description = "Year")
    private int year;

    @Parameter(names = {"-q", "--quarter"},
            validateWith = QuarterValidator.class,
            description = "Quarter")
    private int quarter;

    @Parameter(names = {"-m", "--month"},
            validateWith = MonthValidator.class,
            description = "Month")
    private int month;

    @Parameter(names = {"-t", "--top"},
            validateWith = TopBottomValidator.class,
            description = "Number of Top entities")
    private int topN;

    @Parameter(names = {"-b", "--bottom"},
            validateWith = TopBottomValidator.class,
            description = "Number of Bottom entities")
    private int bottomN;

    public  void run() throws SQLException, IOException, ClassNotFoundException {

        ReportService reportService = new ReportService();

        if (topN == 0 && bottomN == 0 && quarter ==0 && month == 0){
            reportService.generateReportForYear(company, year);
        }
        if (topN == 0 && bottomN == 0 && month == 0 && quarter != 0){
            reportService.generateReportForQuarter(company, year, quarter);
        }
        if (topN == 0 && bottomN == 0 && quarter == 0 && month != 0){
            reportService.generateReportForMonth(company, year, month);
        }
        if (topN != 0 && bottomN == 0 && quarter ==0 && month != 0){
            reportService.monthlyTopNEmployees(company, year, month, topN);
            reportService.monthlyTopNCities(company, year, month, topN);
            reportService.monthlyTopNDepartments(company, year, month, topN);
        }
        if (topN != 0 && bottomN == 0 && month == 0 && quarter != 0){
            reportService.quarterlyTopNEmployees(company, year, quarter, topN);
            reportService.quarterlyTopNCities(company, year, quarter, topN);
            reportService.quarterlyTopNDepartments(company, year, quarter, topN);
        }
        if (topN != 0 && bottomN == 0 && quarter == 0 && month == 0){
            reportService.yearlyTopNEmployees(company, year, topN);
            reportService.yearlyTopNCities(company, year, topN);
            reportService.yearlyTopNDepartments(company, year, topN);
        }
        if (topN == 0 && bottomN != 0 && quarter ==0 && month != 0){
            reportService.monthlyBottomNEmployees(company, year, month, bottomN);
            reportService.monthlyBottomNCities(company, year, month, bottomN);
            reportService.monthlyBottomNDepartments(company, year, month, bottomN);
        }
        if (topN == 0 && bottomN != 0 && month == 0 && quarter != 0){
            reportService.quarterlyBottomNEmployees(company, year, quarter, bottomN);
            reportService.quarterlyBottomNCities(company, year, quarter, bottomN);
            reportService.quarterlyBottomNDepartments(company, year, quarter, bottomN);
        }
        if (topN == 0 && bottomN != 0 && quarter == 0 && month == 0){
            reportService.yearlyBottomNEmployees(company, year, bottomN);
            reportService.yearlyBottomNCities(company, year, bottomN);
            reportService.yearlyBottomNDepartments(company, year, bottomN);
        }
    }
}
