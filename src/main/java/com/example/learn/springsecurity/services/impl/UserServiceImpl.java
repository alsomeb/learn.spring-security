package com.example.learn.springsecurity.services.impl;

import com.example.learn.springsecurity.domain.User;
import com.example.learn.springsecurity.repositories.UserRepository;
import com.example.learn.springsecurity.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

// Todo Maybe Use DTO in future

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.listUsers();
    }
}
