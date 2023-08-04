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
        checkEmailExistCreate(userDTO);

        // save user and return saved object as DTO
        User userEntity = UserDTO.toEntity(userDTO);
        return userRepository.addUser(userEntity)
                .map(UserDTO::toDTO)
                .orElseThrow(() -> new RuntimeException("Failed saving user"));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int id) {
        // fetch existing resource or 404 not found exception if id doesn't belong to an existing resource
        User userEntityToSave = fetchUserEntityByIdOrElseThrowNotFound(id);

        // checks for non-null values for both username and email in the UserDTO before updating the corresponding properties in the User entity
        // This logic makes it easy to update properties provided by payload and keep old as is, no need to include them all in payload
        // I only update properties that isn't null in payload
        if(userDTO.getUsername() != null) {
            userEntityToSave.setUsername(userDTO.getUsername());
        }

        if (userDTO.getEmail() != null) {
            // Check if the new email exists in the database, excluding the current user ID's email of course
            checkEmailExistUpdate(userDTO, id);
            userEntityToSave.setEmail(userDTO.getEmail());
        }

        // Persist to db
        return userRepository.updateUser(userEntityToSave)
                .map(UserDTO::toDTO)
                .orElseThrow(() -> new RuntimeException("Failed updating user"));
    }

    // Helpers
    private User fetchUserEntityByIdOrElseThrowNotFound(int id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("No user with id " + id));
    }

    // Allow users to update their information without any issues if they don't change their email address
    // also if they do update email, check ID isn't current user -> throw exception
    private void checkEmailExistUpdate(UserDTO userDTO, int id) {
        userDTO.setEmail(userDTO.getEmail().trim());
        userRepository.findUserByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    if (user.getId() != id) {
                        throw new UserEmailExistsException("Email exists already");
                    }
                });
    }

    private void checkEmailExistCreate(UserDTO userDTO) {
        userDTO.setEmail(userDTO.getEmail().trim());
        userRepository.findUserByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                        throw new UserEmailExistsException("Email exists already");
                });
    }
}
