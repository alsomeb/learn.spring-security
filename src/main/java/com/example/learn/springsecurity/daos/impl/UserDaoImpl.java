package com.example.learn.springsecurity.daos.impl;

import com.example.learn.springsecurity.daos.UserDao;
import com.example.learn.springsecurity.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> listUsers() {
        return jdbcTemplate.query("SELECT id, username, email from users", (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build());
    }
}
