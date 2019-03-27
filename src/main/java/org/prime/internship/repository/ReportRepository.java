package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.dto.HyperReport;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository {
    private static String startDate;
    private static String endDate;

    // Generate report for MONTH
    public List<HyperReport> generateReportForMonth(String companyName, int year, int month) throws SQLException, IOException, ClassNotFoundException {

        setMonthlyReportStartAndEndDate(year, month);

        String sql = "SELECT turnovers.date, SUM(turnovers.turnover) " +
                "FROM ((((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date <= ? " +
                "GROUP BY turnovers.date;";

        return generateHyperReport(companyName, sql);
    }

    // Generate report for a QUARTER
    public List<HyperReport> generateReportForQuarter(String companyName, int year, int quarter)
            throws SQLException, IOException, ClassNotFoundException {

        List<HyperReport> quarterHyperReportList = new ArrayList<>();

        String sql = "SELECT SUM(turnovers.turnover) " +
                "FROM ((((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date <= ?;";

        int firstOfThree = setFirstMonthOfQuarter(quarter);
        int lastOfThree = firstOfThree + 2;
        for (int month = firstOfThree; month <= lastOfThree; month++){
            setMonthlyReportStartAndEndDate(year, month);
            quarterHyperReportList.add(generateHyperReport(companyName, quarter, month, sql));
        }
        return quarterHyperReportList;
    }

    // Generate report for a YEAR
    public List<HyperReport> generateReportForYear(String companyName, int year)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> yearHyperReportList = new ArrayList<>();

        String sql = "SELECT SUM(turnovers.turnover) " +
                "FROM ((((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date <= ?;";

        for (int quarter = 1; quarter <= 4; quarter++) {
            setYearQuartersStartAndEndDate(year, quarter);
            yearHyperReportList.add(generateHyperReport(companyName, quarter, sql));
        }
        return yearHyperReportList;
    }
    // Overloaded factory method for creating reports
    private List<HyperReport> generateHyperReport(String companyName, String sql)
            throws SQLException, IOException, ClassNotFoundException {
        List<HyperReport> dailyHyperReport = new ArrayList<>();

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    HyperReport hyperReport = new HyperReport();
                    hyperReport.setCompanyName(companyName);
                    hyperReport.setDate(resultSet.getTimestamp(1).toLocalDateTime().toLocalDate());
                    hyperReport.setMonth(hyperReport.getDate().getMonthValue());
                    hyperReport.setTurnover(resultSet.getDouble(2));

                    dailyHyperReport.add(hyperReport);
                }
            }
            return dailyHyperReport;
        }
    }

    // Overloaded factory method for creating reports
    private HyperReport generateHyperReport(String companyName, int quarter, int month, String sql)
            throws SQLException, IOException, ClassNotFoundException {

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    HyperReport hyperReport = new HyperReport();
                    hyperReport.setCompanyName(companyName);
                    hyperReport.setMonth(month);
                    hyperReport.setQuarter(quarter);
                    hyperReport.setTurnover(resultSet.getDouble(1));

                    return hyperReport;
                }
            }
            return null;
        }
    }

    // Overloaded factory method for creating reports
    private HyperReport generateHyperReport(String companyName, int quarter, String sql)
            throws SQLException, IOException, ClassNotFoundException {

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    HyperReport hyperReport = new HyperReport();
                    hyperReport.setCompanyName(companyName);
                    hyperReport.setQuarter(quarter);
                    hyperReport.setTurnover(resultSet.getDouble(1));

                    return hyperReport;
                }
            }
            return null;
        }
    }

    // THREE METHODS FOR SETTING START AND END DATE

    private static void setMonthlyReportStartAndEndDate(int year, int month) {
        String yearString = Integer.toString(year);

        switch (month) {
            case 1:
                startDate = yearString + "-01-01 00:00:00";
                endDate = yearString + "-01-31 00:00:00";
                break;
            case 2:
                startDate = yearString + "-02-01 00:00:00";
                endDate = yearString + "-02-29 00:00:00";
                break;
            case 3:
                startDate = yearString + "-03-01 00:00:00";
                endDate = yearString + "-03-31 00:00:00";
                break;
            case 4:
                startDate = yearString + "-04-01 00:00:00";
                endDate = yearString + "-04-30 00:00:00";
                break;
            case 5:
                startDate = yearString + "-05-01 00:00:00";
                endDate = yearString + "-05-31 00:00:00";
                break;
            case 6:
                startDate = yearString + "-06-01 00:00:00";
                endDate = yearString + "-06-30 00:00:00";
                break;
            case 7:
                startDate = yearString + "-07-01 00:00:00";
                endDate = yearString + "-07-31 00:00:00";
                break;
            case 8:
                startDate = yearString + "-08-01 00:00:00";
                endDate = yearString + "-08-31 00:00:00";
                break;
            case 9:
                startDate = yearString + "-09-01 00:00:00";
                endDate = yearString + "09-30 00:00:00";
                break;
            case 10:
                startDate = yearString + "-10-01 00:00:00";
                endDate = yearString + "-10-31 00:00:00";
                break;
            case 11:
                startDate = yearString + "-11-01 00:00:00";
                endDate = yearString + "-11-30 00:00:00";
                break;
            case 12:
                startDate = yearString + "-12-01 00:00:00";
                endDate = yearString + "-12-31 00:00:00";
                break;
            default:
                System.out.println("Try with number between 1-12 !!!");
                break;
        }
    }

    private int setFirstMonthOfQuarter(int quarter) {
        int firstOfThree = 0;
        switch (quarter) {
            case 1: firstOfThree = 1; break;
            case 2: firstOfThree = 4; break;
            case 3: firstOfThree = 7; break;
            case 4: firstOfThree = 10; break;
            default:
                System.out.println("Try with numbers 1-4 !!!");
                break;
        }
        return firstOfThree;
    }

    private static void setYearQuartersStartAndEndDate(int year, int quarter) {
        String yearString = Integer.toString(year);

        switch (quarter) {
            case 1:
                startDate = yearString + "-01-01 00:00:00";
                endDate = yearString + "-03-31 00:00:00";
                break;
            case 2:
                startDate = yearString + "-04-01 00:00:00";
                endDate = yearString + "-06-30 00:00:00";
                break;
            case 3:
                startDate = yearString + "-07-01 00:00:00";
                endDate = yearString + "-09-30 00:00:00";
                break;
            case 4:
                startDate = yearString + "-10-01 00:00:00";
                endDate = yearString + "-12-31 00:00:00";
                break;
            default:
                System.out.println("Try with numbers 1-4 !!!");
                break;
        }
    }
}

