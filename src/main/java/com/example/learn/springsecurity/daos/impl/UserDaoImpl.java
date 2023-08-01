package com.example.learn.springsecurity.daos.impl;

import com.example.learn.springsecurity.daos.UserDao;
import com.example.learn.springsecurity.daos.UserMovieDao;
import com.example.learn.springsecurity.domain.Movie;
import com.example.learn.springsecurity.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private final UserMovieDao userMovieDao;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, UserMovieDao userMovieDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMovieDao = userMovieDao;
    }

    @Override
    public List<User> listUsers() {
        return jdbcTemplate.query("SELECT id, username, email from users", (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build());
    }

    @Override
    public Optional<User> findUserById(int userId) {
        String sql = "SELECT id, username, email FROM users WHERE id = ?";

        // eftersom vi får tbx en lista med resultat, men kommer alltid ba finnas max 1
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build(), userId);

        if(!users.isEmpty()) {
            User user = users.get(0);
            List<Movie> watchList = userMovieDao.findMoviesByUser(user.getId());
            user.setWatchList(watchList);
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
