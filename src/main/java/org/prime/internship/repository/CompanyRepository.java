package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Company;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements  BaseRepository <Company>{

    @Override
    public Company getOne(Integer id) {

        String sql = "SELECT * " +
                "FROM `companies` " +
                "WHERE company_id = ?";

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
    public Company getOneByName (String name){
        String sql = "SELECT * " +
                "FROM `companies` " +
                "WHERE name = ?";

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
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM `companies`";

        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    companies.add(createEntityInstance(resultSet));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public Company insert(Company company) {

        String sql = "INSERT INTO `companies` (company_id, name, lastDocument) "+
                "VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            writeEntityToDataBase(company, statement);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    company.setCompanyId(generatedKeys.getInt(1));
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Company update(Company company) {

        String sql = "UPDATE `companies`" +
                "SET name = ?," +
                "SET lastDocument = ?," +
                "WHERE company_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            writeEntityToDataBase(company, statement);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE " +
            "FROM `companies` " +
            "WHERE company_id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //helper method for CRUD operations - avoids duplicate code
    private Company createEntityInstance(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setCompanyId(resultSet.getInt("company_id"));
        company.setName(resultSet.getString("name"));
        company.setLastDocumentDate(resultSet.getDate("lastDocument").toLocalDate());
        return company;
    }

    //helper method for CRUD operations - avoids duplicate code
    private void writeEntityToDataBase(Company company, PreparedStatement statement) throws SQLException {
        statement.setString(1, company.getName());
        statement.setDate(2, Date.valueOf(company.getLastDocumentDate()));
        statement.setInt(3, company.getCompanyId());
        statement.execute();
    }

}
