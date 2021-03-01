package com.industriousgnomes.postgresql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class PostgreSQL {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQL.class);

    public Connection getConnection() throws Exception {

        try {
            //https://www.postgresql.org/docs/8.2/auth-pg-hba-conf.html
            Properties properties = new Properties();
            properties.setProperty("user", "postgres");
            properties.setProperty("auth-method", "trust");

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "mysecretpassword");

            if (conn != null) {
                LOGGER.info("PostgreSQL - Connected to the PostgreSQL database");
            } else {
                LOGGER.info("PostgreSQL - FAILED to connected to the PostgreSQL database");
            }

            return conn;
        } catch (SQLException e) {
            LOGGER.error(String.format("PostgreSQL - SQL State: %s - %s", e.getSQLState(), e.getMessage()), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(String.format("PostgreSQL - Exception: %s", e.getMessage()), e);
            throw e;
        }
    }
}
