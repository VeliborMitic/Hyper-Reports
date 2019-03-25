package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;

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

    public ResultSet generateReportForMonth(String companyName, int year, int month) throws SQLException, IOException, ClassNotFoundException {
        setMonthlyReportStartAndEndDate(year, month);

        String sql = "SELECT CAST(turnovers.date AS DATE), SUM(turnovers.turnover)" +
                "FROM ((((employees" +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id)" +
                "JOIN companies on employees.company_id = companies.company_id)" +
                "JOIN cities on employees.city_id = cities.city_id)" +
                "JOIN departments on employees.department_id = departments.department_id)" +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date < ? " +
                "GROUP BY turnovers.date;";

        return generateResultSet(companyName, sql);
    }


    public List<ResultSet> generateReportForQuoter(String companyName, int year, int quarter) throws SQLException, IOException, ClassNotFoundException {
        List<ResultSet> quoterResultSetList = new ArrayList<>();

        String sql = "SELECT SUM(turnovers.turnover)" +
                "FROM ((((employees" +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id)" +
                "JOIN companies on employees.company_id = companies.company_id)" +
                "JOIN cities on employees.city_id = cities.city_id)" +
                "JOIN departments on employees.department_id = departments.department_id)" +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date < ?;";

        switch (quarter) {
            case 1:
                for (int i = 1; i <= 3; i++) {
                    setMonthlyReportStartAndEndDate(year, i);
                    quoterResultSetList.add(generateResultSet(companyName, sql));
                }
                break;
            case 2:
                for (int i = 4; i <= 6; i++) {
                    setMonthlyReportStartAndEndDate(year, i);
                    quoterResultSetList.add(generateResultSet(companyName, sql));
                }
                break;
            case 3:
                for (int i = 7; i <= 9; i++) {
                    setMonthlyReportStartAndEndDate(year, i);
                    quoterResultSetList.add(generateResultSet(companyName, sql));
                }
                break;
            case 4:
                for (int i = 10; i <= 12; i++) {
                    setMonthlyReportStartAndEndDate(year, i);
                    quoterResultSetList.add(generateResultSet(companyName, sql));
                }
                break;
            default:
                System.out.println("Try with numbers 1-4 !!!");
                break;
        }
        return quoterResultSetList;
    }

    public List<ResultSet> generateReportForYear(String companyName, int year) throws SQLException, IOException, ClassNotFoundException {
        List<ResultSet> yearResultSetList = new ArrayList<>();

        String sql = "SELECT SUM(turnovers.turnover)" +
                "FROM ((((employees" +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id)" +
                "JOIN companies on employees.company_id = companies.company_id)" +
                "JOIN cities on employees.city_id = cities.city_id)" +
                "JOIN departments on employees.department_id = departments.department_id)" +
                "WHERE companies.name = ? " +
                "AND turnovers.date >= ? " +
                "AND turnovers.date < ?;";

        for (int i = 1; i <= 4; i++) {
            setQuoterReportStartAndEndDate(year, i);
            yearResultSetList.add(generateResultSet(companyName, sql));
        }
        return yearResultSetList;
    }

    private ResultSet generateResultSet(String companyName, String sql) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setString(2, startDate);
            statement.setString(3, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return resultSet;
            }
        }
        return null;
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
                System.out.println("Try with numbers 1-4 !!!");
                break;
        }
    }
}

