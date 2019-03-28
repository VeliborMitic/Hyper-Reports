package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Turnover;
import org.prime.internship.utility.DateUtils;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TurnoverRepository implements BaseRepository<Turnover> {

    @Override
    public Turnover getOne(Integer id) {
        String sql = "SELECT * FROM turnovers WHERE turnover_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Turnover turnover = new Turnover();
                    turnover.setTurnoverId(resultSet.getInt("turnover_id"));
                    turnover.setEmployeeId(resultSet.getInt("employee_id"));
                    turnover.setDate(resultSet.getTimestamp("date").toLocalDateTime().toLocalDate());
                    turnover.setTurnoverValue(resultSet.getDouble("turnover"));

                    return turnover;
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Turnover> getAll() {
        Set<Turnover> turnovers = new HashSet<>();
        String sql = "SELECT * FROM turnovers";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Turnover turnover = new Turnover();
                    turnover.setTurnoverId(resultSet.getInt("turnover_id"));
                    turnover.setEmployeeId(resultSet.getInt("employee_id"));
                    turnover.setDate(resultSet.getTimestamp("date").toLocalDateTime().toLocalDate());
                    turnover.setTurnoverValue(resultSet.getDouble("turnover"));

                    turnovers.add(turnover);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turnovers;
    }

    @Override
    public Turnover insert(Turnover turnover) {
        String sql = "INSERT INTO turnovers (turnover_id, employee_id, date, turnover) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, turnover.getTurnoverId());
            statement.setInt(2, turnover.getEmployeeId());
            statement.setTimestamp(3, DateUtils.convertLocalDateToTimestamp(turnover.getDate()));
            statement.setDouble(4, turnover.getTurnoverValue());

            statement.execute();

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

        String sql = "UPDATE turnovers SET employee_id = ?, date = ?, turnover = ? " +
                "WHERE turnover_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, turnover.getEmployeeId());
            statement.setTimestamp(2, DateUtils.convertLocalDateToTimestamp(turnover.getDate()));
            statement.setDouble(3, turnover.getTurnoverValue());
            statement.setInt(4, turnover.getTurnoverId());

            statement.execute();

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return turnover;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM turnovers WHERE turnover_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
