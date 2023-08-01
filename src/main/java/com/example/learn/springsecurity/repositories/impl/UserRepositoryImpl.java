package com.example.learn.springsecurity.repositories.impl;

import com.example.learn.springsecurity.daos.UserDao;
import com.example.learn.springsecurity.daos.UserMovieDao;
import com.example.learn.springsecurity.domain.Movie;
import com.example.learn.springsecurity.domain.User;
import com.example.learn.springsecurity.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;
    private final UserMovieDao userMovieDao;

    public UserRepositoryImpl(UserDao userDao, UserMovieDao userMovieDao) {
        this.userDao = userDao;
        this.userMovieDao = userMovieDao;
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers().stream()
                .map(user -> setWatchListForUser(user)) // better using helper method instead of writing all inside lambda
                .collect(Collectors.toList());
    }

    // helpers
    private User setWatchListForUser(User user) {
        List<Movie> watchList = userMovieDao.findMoviesByUser(user.getId());
        user.setWatchList(watchList);
        return user;
    }
}
