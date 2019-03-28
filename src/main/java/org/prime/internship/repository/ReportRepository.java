package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.dto.ReportDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository {

    // Generates Company Report for MONTH - by Days
    public List<ReportDTO> generateReportForMonth(String companyName, int year, int month)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT  turnovers.date, ROUND(SUM(turnovers.turnover),2) " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND MONTH(turnovers.date) = ? " +
                "GROUP BY turnovers.date;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, month);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setDate(resultSet.getTimestamp(1).toLocalDateTime().toLocalDate());
                    reportDTO.setTurnover(resultSet.getDouble(2));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates Company Report for a QUARTER - by Months
    public List<ReportDTO> generateReportForQuarter(String companyName, int year, int quarter)
            throws SQLException, IOException, ClassNotFoundException {

        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT  MONTH(turnovers.date), ROUND(SUM(turnovers.turnover),2) " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND QUARTER(turnovers.date) = ? " +
                "GROUP BY MONTH(turnovers.date)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, quarter);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setQuarter(quarter);
                    reportDTO.setMonth(resultSet.getInt(1));
                    reportDTO.setTurnover(resultSet.getDouble(2));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates Company Report for a YEAR - by Quarters
    public List<ReportDTO> generateReportForYear(String companyName, int year)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT QUARTER(turnovers.date), ROUND(SUM(turnovers.turnover),2) " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "GROUP BY QUARTER(turnovers.date);";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setQuarter(resultSet.getInt(1));
                    reportDTO.setTurnover(resultSet.getDouble(2));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // TOP N REPORTS SECTION ////////////////////////////////////////////////////////////////////////////////////////

    // TOP N EMPLOYEES

    // Generates TOP N EMPLOYEES report for a MONTH
    public List<ReportDTO> monthlyTopNEmployees(String companyName, int year, int month, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "employees.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND MONTH(turnovers.date) = ? " +
                "GROUP BY employees.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setMonth(month);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setEmployee(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N EMPLOYEES report for a QUARTER
    public List<ReportDTO> quarterlyTopNEmployees(String companyName, int year, int quarter, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "employees.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND QUARTER(turnovers.date) = ? " +
                "GROUP BY employees.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, quarter);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setQuarter(quarter);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setEmployee(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N EMPLOYEES report for a YEAR
    public List<ReportDTO> yearlyTopNEmployees(String companyName, int year, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "employees.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM ((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "GROUP BY employees.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setEmployee(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // TOP N CITIES

    // Generates TOP N CITIES report for a MONTH in Company
    public List<ReportDTO> monthlyTopNCities(String companyName, int year, int month, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "cities.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND MONTH(turnovers.date) = ? " +
                "GROUP BY cities.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setMonth(month);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setCity(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N CITIES report for a QUARTER in Company X
    public List<ReportDTO> quarterlyTopNCities(String companyName, int year, int quarter, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "cities.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND QUARTER(turnovers.date) = ? " +
                "GROUP BY cities.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, quarter);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setQuarter(quarter);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setCity(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N CITIES report for a YEAR in Company X
    public List<ReportDTO> yearlyTopNCities(String companyName, int year, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "cities.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN cities on employees.city_id = cities.city_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "GROUP BY cities.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setCity(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // TOP N DEPARTMENTS

    // Generates TOP N DEPARTMENTS report for a MONTH in Company
    public List<ReportDTO> monthlyTopNDepartments(String companyName, int year, int month, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "departments.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND MONTH(turnovers.date) = ? " +
                "GROUP BY departments.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setMonth(month);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setDepartment(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N DEPARTMENTS report for a QUARTER in Company X
    public List<ReportDTO> quarterlyTopNDepartments(String companyName, int year, int quarter, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "departments.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "AND QUARTER(turnovers.date) = ? " +
                "GROUP BY departments.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, quarter);
            statement.setInt(4, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setQuarter(quarter);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setDepartment(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }

    // Generates TOP N DEPARTMENTS report for a YEAR in Company X
    public List<ReportDTO> yearlyTopNDepartments(String companyName, int year, int topN)
            throws SQLException, IOException, ClassNotFoundException {
        List<ReportDTO> reports = new ArrayList<>();

        String sql = "SELECT ROW_NUMBER() over (ORDER BY SUM(turnovers.turnover)DESC) AS RowNumber, " +
                "departments.name, ROUND(SUM(turnovers.turnover),2) AS TotalTurnover " +
                "FROM (((employees " +
                "JOIN turnovers on employees.employee_id = turnovers.employee_id) " +
                "JOIN companies on employees.company_id = companies.company_id) " +
                "JOIN departments on employees.department_id = departments.department_id) " +
                "WHERE companies.name = ? " +
                "AND YEAR(turnovers.date) = ? " +
                "GROUP BY departments.name " +
                "ORDER BY TotalTurnover " +
                "DESC LIMIT ?;";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, companyName);
            statement.setInt(2, year);
            statement.setInt(3, topN);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCompanyName(companyName);
                    reportDTO.setYear(year);
                    reportDTO.setRowNumber(resultSet.getInt(1));
                    reportDTO.setDepartment(resultSet.getString(2));
                    reportDTO.setTurnover(resultSet.getDouble(3));

                    reports.add(reportDTO);
                }
            }
        }
        return reports;
    }
}

