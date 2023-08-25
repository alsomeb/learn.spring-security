package com.example.learn.springsecurity.services;

import com.example.learn.springsecurity.domain.CustomUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final JdbcTemplate jdbcTemplate;

    public CustomUserDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT id, username, password FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    // Mapping Lambda Impl
                    CustomUser user = new CustomUser(
                            rs.getString("username"),
                            rs.getString("password"),
                            getAuthorities(rs.getLong("id")) // Fetch authorities
                    );
                    user.setId(rs.getLong("id")); // Set the id after we queryForObject()
                    return user;
                },
                username
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long userId) {
        String sql = "SELECT authority FROM authorities WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId)
                .stream()
                .map(SimpleGrantedAuthority::new) // Stores a String representation of an authority granted to the Authentication object.
                .collect(Collectors.toList());
    }
}
