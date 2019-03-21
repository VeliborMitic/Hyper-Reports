package org.prime.internship.database;
import org.prime.internship.PropertiesReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private DatabaseManager() {
    }

    public static Connection connect() throws IOException, ClassNotFoundException, SQLException {
        Properties prop = PropertiesReader.properties();
        Class.forName(prop.getProperty("jdbc.driver"));
        return DriverManager.getConnection(
                prop.getProperty("database.url"),
                prop.getProperty("database.user"),
                prop.getProperty("database.password"));
    }
}
