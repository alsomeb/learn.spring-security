package com.example.learn.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

// This one is used for PostGres JDBC API to get the JdbcTemplate to write queries
// Could possibly be used for the H2 DB aswell but no schemas are set up for H2 on this atm

//@Configuration
public class DataSourceConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
