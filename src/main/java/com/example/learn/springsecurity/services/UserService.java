package com.example.learn.springsecurity.services;

import com.example.learn.springsecurity.dto.MovieDTO;
import com.example.learn.springsecurity.dto.UserDTO;

import java.util.List;

/*

    Summary:
    The Service interface defines a set of high-level methods that encapsulate the business logic and application-specific operations.
    It acts as an intermediary between the presentation layer (controllers or APIs) and the data access layer (DAOs and repositories).
    The Service interface abstracts the implementation details, allowing the presentation layer to interact with the application's business logic without worrying about the underlying complexities.
    It provides a clear separation of concerns, enabling better maintainability, testability, and scalability of the application.
    The Service interface acts as the backbone of the application, orchestrating the flow of data and operations, and ensuring that business rules are enforced before data is persisted or retrieved from the data storage.
 */

public interface UserService {
    List<UserDTO> listUsers();
    List<MovieDTO> findMoviesByUser(int userId);
    UserDTO findUserById(int userId);
    UserDTO addUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, int id);
}
