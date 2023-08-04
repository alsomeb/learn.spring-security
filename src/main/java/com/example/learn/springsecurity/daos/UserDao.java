package com.example.learn.springsecurity.daos;

import com.example.learn.springsecurity.domain.User;

import java.util.List;
import java.util.Optional;
/*
    Summary:
    The DAO interface defines a set of methods that provide a standardized way to access and manipulate data from a data source, such as a database.
    It abstracts the underlying data storage implementation details, allowing the rest of the application to interact with data in a database-agnostic manner.
    The DAO interface acts as a contract between the application and the data storage, promoting separation of concerns and enabling easy maintenance and testing of the data access logic.
    By following the DAO pattern, the application gains flexibility, scalability, and better code organization.
 */

public interface UserDao {
    List<User> listUsers();
    Optional<User> findUserById(int userId);
    Optional<User> findUserByEmail(String email);
    boolean addUser(User user);
    boolean updateUser(User user);
}
