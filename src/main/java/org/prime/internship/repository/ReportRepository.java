package org.prime.internship.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jetbrains.annotations.Nullable;
import org.prime.internship.database.DatabaseManager;

public class ReportRepository {
    private static String startDate;
    private static String endDate;

    public ResultSet getReportForMonth (String companyName, int year, int month) throws SQLException, IOException, ClassNotFoundException {
        setMonthlyReportStartAndEndDate(year, month);
        ResultSet resultSet = getResultSet(companyName);
        if (resultSet != null) return resultSet;
        return null;
    }

    public ResultSet getReportForQuoter (String companyName, int year, int quarter) throws SQLException, IOException, ClassNotFoundException {
        setQuoterReportStartAndEndDate(year, quarter);
        ResultSet resultSet = getResultSet(companyName);
        if (resultSet != null) return resultSet;
        return null;
    }

    public ResultSet getReportForYear (String companyName, int year) throws SQLException, IOException, ClassNotFoundException {
        setYearReportStartAndEndDate(year);
        ResultSet resultSet = getResultSet(companyName);
        if (resultSet != null) return resultSet;
        return null;
    }

    @Nullable
    private ResultSet getResultSet(String companyName) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT companies.name, SUM(turnovers.turnover)\n" +
                "FROM ((((employees\n" +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id)\n" +
                "JOIN companies on employees.company_id = companies.company_id)\n" +
                "JOIN cities on employees.city_id = cities.city_id)\n" +
                "JOIN departments on employees.department_id = departments.department_id)\n" +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date < ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet;
        }
        return null;
    }

    private static void setYearReportStartAndEndDate(int year) {
        startDate = Integer.toString(year) + "-01-01 00:00:00";
        endDate = Integer.toString(year + 1) + "-01-01 00:00:00";
    }

    private static void setQuoterReportStartAndEndDate(int year, int quarter) {
        String yearString = Integer.toString(year);
        String nextYearString = Integer.toString(year + 1);
        switch (quarter) {
            case 1:
                startDate = yearString + "-01-01 00:00:00";
                endDate = yearString + "-04-01 00:00:00";
                break;
            case 2:
                startDate = yearString + "-04-01 00:00:00";
                endDate = yearString + "-07-01 00:00:00";
                break;
            case 3:
                startDate = yearString + "-07-01 00:00:00";
                endDate = yearString + "-10-01 00:00:00";
                break;
            case 4:
                startDate = yearString + "-10-01 00:00:00";
                endDate = nextYearString + "-01-01 00:00:00";
                break;
            default:
                System.out.println("Try wiht numbers 1-4 !!!");
                break;
        }
    }

    private static void setMonthlyReportStartAndEndDate(int year, int month) {
        String yearString = Integer.toString(year);
        String nextYearString = Integer.toString(year + 1);
        switch (month) {
            case 1:
                startDate = yearString + "-01-01 00:00:00";
                endDate = yearString + "-02-01 00:00:00";
                break;
            case 2:
                startDate = yearString + "-02-01 00:00:00";
                endDate = yearString + "-03-01 00:00:00";
                break;
            case 3:
                startDate = yearString + "-03-01 00:00:00";
                endDate = yearString + "-04-01 00:00:00";
                break;
            case 4:
                startDate = yearString + "-04-01 00:00:00";
                endDate = yearString + "-05-01 00:00:00";
                break;
            case 5:
                startDate = yearString + "-05-01 00:00:00";
                endDate = yearString + "-06-01 00:00:00";
                break;
            case 6:
                startDate = yearString + "-06-01 00:00:00";
                endDate = yearString + "-07-01 00:00:00";
                break;
            case 7:
                startDate = yearString + "-07-01 00:00:00";
                endDate = yearString + "-08-01 00:00:00";
                break;
            case 8:
                startDate = yearString + "-07-01 00:00:00";
                endDate = yearString + "-09-01 00:00:00";
                break;
            case 9:
                startDate = yearString + "-08-01 00:00:00";
                endDate = yearString + "-09-01 00:00:00";
                break;
            case 10:
                startDate = yearString + "-09-01 00:00:00";
                endDate = yearString + "-10-01 00:00:00";
                break;
            case 11:
                startDate = yearString + "-10-01 00:00:00";
                endDate = yearString + "-11-01 00:00:00";
                break;
            case 12:
                startDate = yearString + "-11-01 00:00:00";
                endDate = nextYearString + "-01-01 00:00:00";
                break;
            default:
                System.out.println("Try with number between 1-12 !!!");
                break;
        }
    }
}
