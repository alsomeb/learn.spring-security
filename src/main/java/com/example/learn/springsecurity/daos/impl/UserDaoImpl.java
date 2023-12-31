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
        return jdbcTemplate.query("SELECT id, username, email from app_users", (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build());
    }

    /*
        I could have used queryForObject() with JDBC's provided BeanPropertyRowMapper
        In my case, query() avoids the EmptyResultDataAccessException when UserID has NO MATCH in DB 
        I handle this situation in Service layer with UserNotFoundException 404 while using repository ..findUserById().orElseThrow()
        This is possible because we are using Optionals

        It automatically maps the columns in the ResultSet to properties in the Java object based on their names, using JavaBean property conventions
        e.g., for a column named "firstName," it will look for a property "firstName" in the Java class and map the value accordingly

        Using Custom lambda: (rs, rowNum) ->
        This approach gives you more flexibility because you can explicitly define how the mapping should be done. 
        In summary, if you need more control over the mapping process or want to handle complex scenarios, defining a custom lambda allows you to tailor the mapping to your needs.

        Syntax BeanPropertyRowMapper:
        springJdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(User.class), id);
    */
    @Override
    public Optional<User> findUserById(int userId) {
        String sql = "SELECT id, username, email FROM app_users WHERE id = ?";

        // Using query() method to return a list of results ( we only expect it to have a length of 1 )
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build(),
                userId); // userId to add to the param

        return loadUserData(users);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = """
                SELECT id, username, email
                FROM app_users
                WHERE email = ?
                """;

        List<User> result = jdbcTemplate.query(sql, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .build(), email);

        return loadUserData(result);
    }

    @Override
    public boolean addUser(User user) {
        String sql = """
                INSERT INTO app_users (username, email)
                VALUES (?, ?);
                """;

        int rowsAffected = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail());

        return rowsAffected > 0; // if bigger than 0, insert operation was successful
    }


    @Override
    public boolean updateUser(User user) {
        String sql = """
                UPDATE app_users
                SET username = ?,
                    email = ?
                WHERE id = ?;
                """;
        int rowsAffected = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getId());
        return rowsAffected > 0;
    }

    // Helpers
    private Optional<User> loadUserData(List<User> resultFromSql) {
        // Check if the list is NOT empty
        if(!resultFromSql.isEmpty()) {
            // Since we expect only one user with the given userId, we return the first (and only) element
            User user = resultFromSql.get(0);

            // Load the watchlist for the user
            List<Movie> watchList = userMovieDao.findMoviesByUser(user.getId());
            user.setWatchList(watchList);

            // Return the user wrapped in Optional
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
