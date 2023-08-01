package com.example.learn.springsecurity.daos.impl;

import com.example.learn.springsecurity.daos.UserMovieDao;
import com.example.learn.springsecurity.domain.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMovieDaoImpl implements UserMovieDao {

    private final JdbcTemplate jdbcTemplate;

    public UserMovieDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
        RowMapper to map the result set to Movie objects.
        If there are no rows in the result set, the RowMapper will not be invoked, and hence, an empty list will be returned.
     */
    @Override
    public List<Movie> findMoviesByUser(int userId) {
        String sql = "SELECT m.id, m.name, m.release_year FROM movies m " +
                "INNER JOIN user_movie um ON m.id = um.movie_id " +
                "WHERE um.user_id = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> Movie.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .releaseYear(resultSet.getDate("release_year").toLocalDate())
                .build(), userId);
    }
}
