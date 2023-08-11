package com.example.learn.springsecurity.jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Decided to put all JWT related in this package even controller
@RestController
@RequestMapping("api/auth")
public class JwtAuthResourceController {

    /*
        Authentication Object:
        Represents the token for an authentication request or for an authenticated principal
        once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.

     */
    @PostMapping
    public Authentication authenticate(Authentication authentication) {
        return authentication;
    }
}
