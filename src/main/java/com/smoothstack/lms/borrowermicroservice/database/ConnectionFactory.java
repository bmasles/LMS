package com.smoothstack.lms.borrowermicroservice.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    @Scope("prototype")
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(Boolean.FALSE);
        return connection;
    }
}
