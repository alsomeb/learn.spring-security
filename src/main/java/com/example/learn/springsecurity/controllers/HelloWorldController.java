package com.example.learn.springsecurity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HelloWorldController {

    @GetMapping("hello")
    public String helloWorld(){
        return "Hello World!";
    }

    // How to gen csrf token
    // '_csrf' is from the html input property 'name'
    @GetMapping("csrf-token")
    public CsrfToken retrieveCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf"); // Cast to CsrfToken (Object)
    }
}
