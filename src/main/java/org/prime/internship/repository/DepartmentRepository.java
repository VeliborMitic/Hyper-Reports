package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Department;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository implements BaseRepository<Department>{

    @Override
    public Department getOne(Integer id) {

        String sql = "SELECT * " +
                "FROM `departments` " +
                "WHERE department_id = ?";

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

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM `departments`";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    departments.add(createEntityInstance(resultSet));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department insert(Department department) {

        String sql = "INSERT INTO `departments` (department_id, name) "+
                "VALUES (?, ?)";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            writeEntityToDataBase(department, statement);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setDepartmentId(generatedKeys.getInt(1));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Department update(Department department) {

        String sql = "UPDATE `departments`" +
                "SET name = ?," +
                "WHERE department_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            writeEntityToDataBase(department, statement);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE " +
                "FROM `departments` " +
                "WHERE department_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //helper method for CRUD operations - avoids duplicate code
    private Department createEntityInstance(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(resultSet.getInt("department_id"));
        department.setName(resultSet.getString("name"));
        return department;
    }

    //helper method for CRUD operations - avoids duplicate code
    private void writeEntityToDataBase(Department department, PreparedStatement statement) throws SQLException {
        statement.setInt(1, department.getDepartmentId());
        statement.setString(2, department.getName());
        statement.execute();
    }
}
