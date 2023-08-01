package com.example.learn.springsecurity.services;

import com.example.learn.springsecurity.domain.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
}
