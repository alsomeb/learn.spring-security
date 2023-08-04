package com.example.learn.springsecurity.repositories;

import com.example.learn.springsecurity.domain.Movie;
import com.example.learn.springsecurity.domain.User;

import java.util.List;
import java.util.Optional;

/*
Summary:
    The UserRepository interface acts as an abstraction layer that defines a set of methods for the service layer to interact with user data without knowing the specific details of the data access.
    It provides a high-level API for CRUD operations on user data and promotes a separation of concerns between the service layer and the underlying data access logic implemented in the UserDao.
    By using the repository pattern, the codebase becomes more maintainable, testable, and adheres to best practices in software design.
 */

public interface UserRepository {
    List<User> listUsers();
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    List<Movie> findMoviesByUserId(int userId);
    Optional<User> addUser(User user);
    Optional<User> updateUser(User user);
}
