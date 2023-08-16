package com.example.learn.springsecurity.jwt.controller;

import com.example.learn.springsecurity.jwt.JwtTokenService;
import com.example.learn.springsecurity.jwt.dtos.JwtTokenRequest;
import com.example.learn.springsecurity.jwt.dtos.JwtTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Decided to put all JWT related in this package even controller
@RestController
@RequestMapping("api/auth")
@Slf4j
public class JwtAuthResourceController {
    private final JwtTokenService tokenService;

    private final AuthenticationManager authenticationManager;


    public JwtAuthResourceController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /*

            implementation that is designed for simple presentation of a username and password.
            The principal and credentials should be set with an Object that provides the respective property via its Object.toString() method.
            The simplest such Object to use is String.

         */
    @PostMapping
    public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody JwtTokenRequest request) {
        //  create a UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());

        // Attempts to authenticate the passed Authentication object,
        // returning a fully populated Authentication object (including granted authorities) if successful.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Generate JWT token
        String token = tokenService.generateToken(authentication);

        log.info("Auth user: {}", request.username());

        return new ResponseEntity<>(new JwtTokenResponse(token), HttpStatus.OK);
    }
}