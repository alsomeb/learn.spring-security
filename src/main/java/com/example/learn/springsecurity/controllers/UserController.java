package com.example.learn.springsecurity.controllers;

import com.example.learn.springsecurity.dto.MovieDTO;
import com.example.learn.springsecurity.dto.UserDTO;
import com.example.learn.springsecurity.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> allUsers(){
        log.info("GET: all users in db");
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable int id) {
        var userDTO = userService.findUserById(id);
        log.info("GET by userID: {}", userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("{id}/movies")
    public ResponseEntity<List<MovieDTO>> findMoviesById(@PathVariable int id) {
        List<MovieDTO> movies = userService.findMoviesByUser(id);
        log.info("GET Movies By User Id: {}", id);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        log.info("POST new User");
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }
}
