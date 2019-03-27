package org.prime.internship.repository;

import org.jetbrains.annotations.Nullable;
import org.prime.internship.database.DatabaseManager;
import org.prime.internship.entity.Company;
import org.prime.internship.utility.DateUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CompanyRepository implements BaseRepository<Company> {

    @Override
    public Company getOne(Integer id) {
        String sql = "SELECT * FROM companies WHERE company_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            Company company = getCompany(statement);
            if (company != null) return company;

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

            Company company = getCompany(statement);
            if (company != null) return company;

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private Company getCompany(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return createCompany(resultSet);
            }
        }
        return null;
    }

    private Company createCompany(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setCompanyId(resultSet.getInt("company_id"));
        company.setName(resultSet.getString("name"));
        company.setLastDocumentDate(resultSet.getTimestamp("lastDocument").toLocalDateTime().toLocalDate());
        return company;
    }

    @Override
    public Set<Company> getAll() {
        Set<Company> companies = new HashSet<>();
        String sql = "SELECT * FROM companies";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    companies.add(createCompany(resultSet));
                }
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
            statement.setTimestamp(3, DateUtils.convertLocalDateToTimestamp(company.getLastDocumentDate()));
            statement.execute();

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
        String sql = "UPDATE companies SET name = ?, lastDocument = ? WHERE company_id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, company.getName());
            statement.setTimestamp(2, DateUtils.convertLocalDateToTimestamp(company.getLastDocumentDate()));
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