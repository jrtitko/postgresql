package com.industriousgnomes.postgresql.controllers;

import com.industriousgnomes.postgresql.config.PostgreSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/postgres")
public class PostresController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostresController.class);

    @Autowired
    PostgreSQL postgreSQL;

    @GetMapping
    public String getPostgreSQL(
            @RequestParam(value = "query") String query
    ) {
        try {
            LOGGER.info("PostgresSQL query = " + query);

            Connection connection = postgreSQL.getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            List<String> records = new LinkedList<>();

            while (resultSet.next()) {
                LOGGER.info("Field1: " + resultSet.getObject(1));
                records.add(resultSet.getObject(1).toString());
            }

            LOGGER.info("-- END --");

            return records.toString();
        } catch (Exception e) {
            LOGGER.error("Exception", e);
            return "failed";
        }
    }

    @PostMapping
    public void postPostgreSQL(
            @RequestParam(value = "sql") String sql
    ) {
        try {
            LOGGER.info("PostgresSQL sql = " + sql);

            Connection connection = postgreSQL.getConnection();

            Statement statement = connection.createStatement();

            statement.execute(sql);

            LOGGER.info("-- END --");
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
