package com.example.learn.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
    This is the config for Http Basic Auth. Learning purpose
 */
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .authenticated())
                // Disable Http Session -> Making REST API Stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Basic Auth enabled with default Pop Up Modal for Login Credentials in Browser
                .httpBasic(Customizer.withDefaults())
                // Allow any req that comes from the same origin to frame this App
                .headers(headersConfig ->
                        headersConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .build();
    }


    /*
        Storing in memory user details
        UserDetailsService is Core interface which loads user-specific data
        InMemoryUSerDetailsManger is a non-persistent impl of UserDetailsManger (for testing purposes)
     */
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("tester")
                .password("{noop}tester") // no password encrypt == {noop}
                .roles("USER")
                .build();

        var user2 = User.withUsername("alex")
                .password("{noop}alex") // no password encrypt == {noop}
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, user2);
    }
     */

    // Storing in H2 Mem DB, dataSource bean will be injected
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user = User.withUsername("tester")
                .password("{noop}tester") // no password encrypt == {noop}
                .roles("USER")
                .build();

        var user2 = User.withUsername("alex")
                .password("{noop}alex") // no password encrypt == {noop}
                .roles("ADMIN", "USER")
                .build();

        // Create UserDetailsManager + insert 2 users
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(user2);

        return jdbcUserDetailsManager;
}


    /*
        Execute DDL script at start up, path to DDL script is available in JdbcDaoImpl static variable
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }
}
