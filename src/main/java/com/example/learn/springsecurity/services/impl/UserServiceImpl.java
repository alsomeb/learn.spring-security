package com.example.learn.springsecurity.services.impl;

import com.example.learn.springsecurity.domain.User;
import com.example.learn.springsecurity.dto.MovieDTO;
import com.example.learn.springsecurity.dto.UserDTO;
import com.example.learn.springsecurity.exception.UserEmailExistsException;
import com.example.learn.springsecurity.exception.UserNotFoundException;
import com.example.learn.springsecurity.repositories.UserRepository;
import com.example.learn.springsecurity.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        // Check if user exists first
        var matchUser = fetchUserEntityByIdOrElseThrowNotFound(userId);

        return userRepository.findMoviesByUserId(matchUser.getId()).stream()
                .map(movie -> MovieDTO.toDTO(movie))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(int userId) {
        var userEntity = fetchUserEntityByIdOrElseThrowNotFound(userId);

        return UserDTO.toDTO(userEntity);
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        // check if email exists else Throw Exception
        String email = userDTO.getEmail().trim();
        userRepository.findUserByEmail(email)
                .ifPresent(u -> {
                    throw new UserEmailExistsException("Email exists already");
                });

        // save user and return saved object as DTO
        User userEntity = UserDTO.toEntity(userDTO);
        return userRepository.addUser(userEntity)
                .map(UserDTO::toDTO)
                .orElseThrow(() -> new RuntimeException("Failed saving user"));
    }

    // Helpers
    private User fetchUserEntityByIdOrElseThrowNotFound(int id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("No user with id " + id));
    }
}
