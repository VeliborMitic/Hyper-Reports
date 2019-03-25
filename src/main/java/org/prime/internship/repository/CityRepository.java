package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.City;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CityRepository implements BaseRepository<City> {

    @Override
    public City getOne(Integer id) {
        String sql = "SELECT * FROM cities WHERE city_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    City city = new City();
                    city.setCityId(resultSet.getInt("city_id"));
                    city.setName(resultSet.getString("name"));

                    return city;
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public City getOneByName(String name) {
        String sql = "SELECT * FROM cities WHERE name = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    City city = new City();
                    city.setCityId(resultSet.getInt("city_id"));
                    city.setName(resultSet.getString("name"));

                    return city;
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<City> getAll() {
        Set<City> cities = new HashSet<>();
        String sql = "SELECT * FROM cities";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    City city = new City();
                    city.setCityId(resultSet.getInt("city_id"));
                    city.setName(resultSet.getString("name"));
                    cities.add(city);
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City insert(City city) {
        String sql = "INSERT INTO cities (city_id, name) VALUES (?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, city.getCityId());
            statement.setString(2, city.getName());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setCityId(generatedKeys.getInt(1));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public City update(City city) {
        String sql = "UPDATE cities SET name = ? WHERE city_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city.getName());
            statement.setInt(2, city.getCityId());
            statement.execute();

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM cities WHERE city_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
