package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Company;
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

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Turnover turnover = new Turnover();
                turnover.setTurnover_id(resultSet.getInt("turnover_id"));
                turnover.setEmployee_id(resultSet.getInt("employee_id"));
                turnover.setDate(resultSet.getDate("date").toLocalDate());
                turnover.setTurnoverValue(resultSet.getDouble("turnover"));
                return turnover;
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

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Turnover turnover = new Turnover();
                turnover.setTurnover_id(resultSet.getInt("turnover_id"));
                turnover.setEmployee_id(resultSet.getInt("employee_id"));
                turnover.setDate(resultSet.getDate("date").toLocalDate());
                turnover.setTurnoverValue(resultSet.getDouble("turnover"));
                turnovers.add(turnover);
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turnovers;
    }

    @Override
    public Turnover insert(Turnover turnover) {
        String sql = "INSERT INTO `turnovers` (turnover_id, employee_id, date, turnover) "+
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, turnover.getTurnover_id());
            statement.setInt(2, turnover.getEmployee_id());
            statement.setDate(3, Date.valueOf(turnover.getDate()));
            statement.setDouble(4, turnover.getTurnoverValue());

            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    turnover.setTurnover_id(generatedKeys.getInt(1));
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

            statement.setInt(1, turnover.getEmployee_id());
            statement.setDate(3, Date.valueOf(turnover.getDate()));
            statement.setDouble(4, turnover.getTurnoverValue());
            statement.setInt(1, turnover.getTurnover_id());

            statement.execute();
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
}
