package com.example.learn.springsecurity.daos;

import com.example.learn.springsecurity.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> listUsers();
    Optional<User> findUserById(int userId);
    Optional<User> findUserByEmail(String email);
    boolean addUser(User user);
}
