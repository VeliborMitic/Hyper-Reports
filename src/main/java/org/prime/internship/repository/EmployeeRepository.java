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

public class EmployeeRepository implements BaseRepository<Employee>{
    @Override
    public Employee getOne(Integer id) {

        String sql = "SELECT * " +
                "FROM `employees` " +
                "WHERE employee_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("name"));
                employee.setCompany_id(resultSet.getInt("company_id"));
                employee.setCity_id(resultSet.getInt("city_id"));
                employee.setDepartment_id(resultSet.getInt("department_id"));
                return employee;
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

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("name"));
                employee.setCompany_id(resultSet.getInt("company_id"));
                employee.setCity_id(resultSet.getInt("city_id"));
                employee.setDepartment_id(resultSet.getInt("department_id"));
                employees.add(employee);
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee insert(Employee employee) {

        String sql = "INSERT INTO `employees` (employee_id, name, company_id, city_id, department_id) "+
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, employee.getEmployee_id());
            statement.setString(2, employee.getName());
            statement.setInt(3, employee.getCompany_id());
            statement.setInt(4, employee.getCity_id());
            statement.setInt(5, employee.getDepartment_id());

            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployee_id(generatedKeys.getInt(1));
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

            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getCompany_id());
            statement.setInt(3, employee.getCity_id());
            statement.setInt(4, employee.getDepartment_id());
            statement.setInt(5, employee.getEmployee_id());

            statement.execute();
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
}
