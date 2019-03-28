package org.prime.internship;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader {

    private PropertiesReader() {
    }

    public static Properties properties() throws IOException {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("application.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        Objects.requireNonNull(inputStream).close();
        return prop;
    }
}
