package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements BaseRepository<Employee> {

    @Override
    public Employee getOne(Integer id) {

        String sql = "SELECT * " +
                "FROM `employees` " +
                "WHERE employee_id = ?";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createEntityInstance(resultSet);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getOneByName(String name) {

        String sql = "SELECT * " +
                "FROM `employees` " +
                "WHERE employee_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createEntityInstance(resultSet);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM `employees`";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(createEntityInstance(resultSet));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee insert(Employee employee) {

        String sql = "INSERT INTO `employees` (name, company_id, city_id, department_id, employee_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            writeEntityToDataBase(employee, statement);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployeeId(generatedKeys.getInt(1));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee update(Employee employee) {

        String sql = "UPDATE `employees`" +
                "SET name = ?," +
                "SET company_id = ?," +
                "SET city_id = ?," +
                "SET department_id = ?," +
                "WHERE employee_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            writeEntityToDataBase(employee, statement);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE " +
                "FROM `employees` " +
                "WHERE employee_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //helper method for CRUD operations - avoids duplicate code
    private void writeEntityToDataBase(Employee employee, PreparedStatement statement) throws SQLException {
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getCompanyId());
        statement.setInt(3, employee.getCityId());
        statement.setInt(4, employee.getDepartmentId());
        statement.setInt(5, employee.getEmployeeId());
        statement.execute();
    }

    //helper method for CRUD operations - avoids duplicate code
    private Employee createEntityInstance(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setName(resultSet.getString("name"));
        employee.setCompanyId(resultSet.getInt("company_id"));
        employee.setCityId(resultSet.getInt("city_id"));
        employee.setDepartmentId(resultSet.getInt("department_id"));
        return employee;
    }
}
