package com.example.learn.springsecurity.services.impl;

import com.example.learn.springsecurity.dto.MovieDTO;
import com.example.learn.springsecurity.dto.UserDTO;
import com.example.learn.springsecurity.exception.UserNotFoundException;
import com.example.learn.springsecurity.repositories.UserRepository;
import com.example.learn.springsecurity.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Todo Maybe Use DTO in future

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.listUsers().stream()
                .map(user -> UserDTO.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findMoviesByUser(int userId) {
        return userRepository.findMoviesByUserId(userId).stream()
                .map(movie -> MovieDTO.toDTO(movie))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(int userId) {
        var userEntity = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user with id " + userId));

        return UserDTO.toDTO(userEntity);
    }
}
