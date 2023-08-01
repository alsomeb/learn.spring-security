package com.example.learn.springsecurity.repositories;

import com.example.learn.springsecurity.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> listUsers();
}
