package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Turnover;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoverRepository implements BaseRepository<Turnover>{

    @Override
    public Turnover getOne(Integer id) {

        String sql = "SELECT * " +
                "FROM `turnovers` " +
                "WHERE turnover_id = ?";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    createEntityInstance(resultSet);
                    //Turnover turnover;
                    return createEntityInstance(resultSet);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Turnover getOneByName(String name) {

        String sql = "SELECT * " +
                "FROM `turnovers` " +
                "WHERE turnover_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    createEntityInstance(resultSet);
                    //Turnover turnover;
                    return createEntityInstance(resultSet);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Turnover> getAll() {
        List<Turnover> turnovers = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM `turnovers`";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    turnovers.add(createEntityInstance(resultSet));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turnovers;
    }

    @Override
    public Turnover insert(Turnover turnover) {
        String sql = "INSERT INTO `turnovers` (employee_id, date, turnover, turnover_id) "+
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            writeEntityToDataBase(turnover, statement);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    turnover.setTurnoverId(generatedKeys.getInt(1));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turnover;
    }

    @Override
    public Turnover update(Turnover turnover) {

        String sql = "UPDATE `turnovers`" +
                "SET employee_id = ?," +
                "SET date = ?," +
                "SET turnover = ?," +
                "WHERE turnover_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            writeEntityToDataBase(turnover, statement);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turnover;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE " +
                "FROM `turnovers` " +
                "WHERE turnover_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //helper method for CRUD operations - avoids duplicate code
    private Turnover createEntityInstance(ResultSet resultSet) throws SQLException {
        Turnover turnover = new Turnover();
        turnover.setTurnoverId(resultSet.getInt("turnover_id"));
        turnover.setEmployeeId(resultSet.getInt("employee_id"));
        turnover.setDate(resultSet.getDate("date").toLocalDate());
        turnover.setTurnoverValue(resultSet.getDouble("turnover"));
        return turnover;
    }

    //helper method for CRUD operations - avoids duplicate code
    private void writeEntityToDataBase(Turnover turnover, PreparedStatement statement) throws SQLException {
        statement.setInt(1, turnover.getEmployeeId());
        statement.setDate(2, Date.valueOf(turnover.getDate()));
        statement.setDouble(3, turnover.getTurnoverValue());
        statement.setInt(4, turnover.getTurnoverId());
        statement.execute();
    }
}
