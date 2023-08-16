package com.example.learn.springsecurity.jwt.controller;

import com.example.learn.springsecurity.jwt.dtos.JwtResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

// Decided to put all JWT related in this package even controller
@RestController
@RequestMapping("api/auth")
public class JwtAuthResourceController {

    private final JwtEncoder jwtEncoder;

    public JwtAuthResourceController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    /*
            Authentication Object:
            Represents the token for an authentication request or for an authenticated principal
            once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.

         */
    @PostMapping
    public JwtResponse authenticate(Authentication authentication) {
        //return authentication;
        return new JwtResponse(createToken(authentication));
    }



    private String createToken(Authentication authentication) {
        // We are gathering all the details from the Authentication Object


        // The JWT Claims Set is a JSON object representing the claims conveyed by a JSON Web Token.
        // Create Claims with builder
        // Claims is what Authorities you HAVE
        var claims = JwtClaimsSet.builder()
                .issuer("self") // who has issued it, self issued
                .issuedAt(Instant.now()) // when ?
                .expiresAt(Instant.now().plusSeconds(60 * 30)) // Time When JWT expire
                .subject(authentication.getName()) // name of Principal
                .claim("scope", createScope(authentication)) // What Authorities does he have? String Concat Separated with Space
                .build();

        // Create Token

        // Creating JwtEncoderParams from the claims
        JwtEncoderParameters parameters = JwtEncoderParameters
                .from(claims);


        // Creating the Token from the Parameters above with the help of JwtEncoder.
        String token = jwtEncoder.encode(parameters).getTokenValue();

        return token;
    }

    private String createScope(Authentication authentication) {
        // Gets Users Authorities
        // Might be multiple roles assigned to user (it's a list in Auth object)
        // We want a String of all Roles separated by space, like "ROLE_ADMIN ROLE_USER"

        String userRoles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));

        return userRoles;
    }
}