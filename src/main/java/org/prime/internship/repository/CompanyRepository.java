package org.prime.internship.repository;

import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Company;
import org.prime.internship.utility.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements BaseRepository<Company> {

    @Override
    public Company getOne(Integer id) {

        String sql = "SELECT * FROM companies WHERE company_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.next()) {
                    Company company = new Company();
                    company.setCompanyId(resultSet.getInt("company_id"));
                    company.setName(resultSet.getString("name"));
                    company.setLastDocumentDate(resultSet.getTimestamp("lastDocument").toLocalDateTime().toLocalDate());
                    return company;
                }
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Company getOneByName(String name) {
        String sql = "SELECT * FROM companies WHERE name = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt("company_id"));
                company.setName(resultSet.getString("name"));
                company.setLastDocumentDate(resultSet.getTimestamp("lastDocument").toLocalDateTime().toLocalDate());
                return company;
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();

        String sql = "SELECT * FROM companies";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt("company_id"));
                company.setName(resultSet.getString("name"));
                company.setLastDocumentDate(resultSet.getTimestamp("lastDocument").toLocalDateTime().toLocalDate());
                companies.add(company);
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public Company insert(Company company) {

        String sql = "INSERT INTO companies (company_id, name, lastDocument) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, company.getCompanyId());
            statement.setString(2, company.getName());
            statement.setTimestamp(3, Util.convertLocalDateToTimestamp(company.getLastDocumentDate()));
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                company.setCompanyId(generatedKeys.getInt(1));
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Company update(Company company) {

        String sql = "UPDATE companies SET name = ?, lastDocument = ? WHERE company_id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, company.getName());
            statement.setTimestamp(2, Util.convertLocalDateToTimestamp(company.getLastDocumentDate()));
            statement.setInt(3, company.getCompanyId());
            statement.execute();

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM companies WHERE company_id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}