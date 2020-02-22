package com.smoothstack.lms.borrowermicroservice.database;

import com.smoothstack.lms.borrowermicroservice.Debug;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class ConnectionFactory {

    @Value("${spring.datasource.driver-class-name:org.h2.Driver}")
    private String driver;

    @Value("${spring.datasource.url:jdbc:h2:file:./data/database}")
    private String url;

    @Value("${spring.datasource.username:root}")
    private String username;

    @Value("${spring.datasource.password:root}")
    private String password;

    @Bean
    @Scope("singleton")
    public ConnectionFactory getConnectionFactory() {
        return new ConnectionFactory();
    }

    @Bean
    @Scope("prototype")
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            Debug.printException(e);
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(Boolean.FALSE);
        return connection;
    }


    public ConnectionFactory() {

    }

    public ConnectionFactory(File configFile) {
        loadProperties(configFile);

    }

    public ConnectionFactory(String configFileName) {

        Debug.printf("Load config: %s\n", configFileName);
        loadProperties(new File(configFileName));
    }

    public boolean isDriverPresent() {
        try {
            Class.forName(driver);
            return true;
        } catch (ClassNotFoundException e) {
            Debug.printException(e);
            return false;
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void loadProperties(File configFile) {


        if (!configFile.exists()) {

            String parent = configFile.getParent();
            if (parent != null) {
                File directory = new File(parent);
                if (!directory.exists() || directory.mkdirs()) {
                    Debug.printf("Cannot create directory '%s'\n", directory.getName());
                }
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(configFile)) {

                Properties defaultProperties = new Properties();
                defaultProperties.setProperty("driver","com.mysql.cj.jdbc.Driver");
                defaultProperties.setProperty("url", "jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true");
                defaultProperties.setProperty("username", "root");
                defaultProperties.setProperty("password", "root");
                defaultProperties.storeToXML(fileOutputStream, "LMS Application Properties","UTF-8");
                Debug.printf("Config file '%s' was created with default values.\n", configFile.getName());
                Debug.println("Please edit and re-run the program\n");


            } catch (IOException e) {
                Debug.printf("Cannot create config file '%s'\n", configFile.getName());

            }
        }


        try (FileInputStream fileInputStream = new FileInputStream(configFile)) {

            Properties defaultProperties = new Properties();
            defaultProperties.loadFromXML(fileInputStream);

            this.setDriver(defaultProperties.getProperty("driver"));
            this.setUrl(defaultProperties.getProperty("url"));
            this.setUsername(defaultProperties.getProperty("username"));
            this.setPassword(defaultProperties.getProperty("password"));

        } catch (IOException e) {
            Debug.printf("Cannot load config file '%s'\n", configFile.getName());

        }

    }
}
