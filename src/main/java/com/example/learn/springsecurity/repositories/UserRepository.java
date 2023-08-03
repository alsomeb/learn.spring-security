package com.example.learn.springsecurity.repositories;

import com.example.learn.springsecurity.domain.Movie;
import com.example.learn.springsecurity.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> listUsers();
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    List<Movie> findMoviesByUserId(int userId);
    Optional<User> addUser(User user);
}
