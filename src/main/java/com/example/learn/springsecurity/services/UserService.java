package com.example.learn.springsecurity.services;

import com.example.learn.springsecurity.dto.MovieDTO;
import com.example.learn.springsecurity.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> listUsers();
    List<MovieDTO> findMoviesByUser(int userId);
    UserDTO findUserById(int userId);
    UserDTO addUser(UserDTO userDTO);
}
