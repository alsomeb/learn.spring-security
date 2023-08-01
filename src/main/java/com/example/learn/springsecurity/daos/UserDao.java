package com.example.learn.springsecurity.daos;

import com.example.learn.springsecurity.domain.User;

import java.util.List;

public interface UserDao {
    List<User> listUsers();
}
