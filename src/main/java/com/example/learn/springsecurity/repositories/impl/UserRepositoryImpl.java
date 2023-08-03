package com.example.learn.springsecurity.repositories.impl;

import com.example.learn.springsecurity.daos.UserDao;
import com.example.learn.springsecurity.daos.UserMovieDao;
import com.example.learn.springsecurity.domain.Movie;
import com.example.learn.springsecurity.domain.User;
import com.example.learn.springsecurity.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;
    private final UserMovieDao userMovieDao;

    public UserRepositoryImpl(UserDao userDao, UserMovieDao userMovieDao) {
        this.userDao = userDao;
        this.userMovieDao = userMovieDao;
    }

    // Might need watch list mapped to every user later
    // and better using helper method instead of writing all inside lambda
    @Override
    public List<User> listUsers() {
        return userDao.listUsers().stream()
                .map(user -> setWatchListForUser(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<Movie> findMoviesByUserId(int userId) {
        return userMovieDao.findMoviesByUser(userId);
    }

    /*
        Using Optional in this scenario is a clean and idiomatic way to handle the possibility of a user not being inserted into the database.
        It allows the calling code in the service layer to use orElse, orElseThrow, or other methods to handle the different cases gracefully.
     */
    @Override
    public Optional<User> addUser(User user) {
        boolean wasInserted = userDao.addUser(user);
        if (wasInserted) {
            // returns Optional of User persisted to db
            return userDao.findUserByEmail(user.getEmail());
        }

        // Returns Optional Empty
        return Optional.empty();
    }

    // helper methods
    private User setWatchListForUser(User user) {
        List<Movie> watchList = userMovieDao.findMoviesByUser(user.getId());
        user.setWatchList(watchList);
        return user;
    }
}
