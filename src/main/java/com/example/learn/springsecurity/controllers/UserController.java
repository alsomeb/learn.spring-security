package com.example.learn.springsecurity.controllers;

import com.example.learn.springsecurity.dto.UserDTO;
import com.example.learn.springsecurity.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
