package com.example.learn.springsecurity.config;

/*
    This is the config for Http Basic Auth. Learning purpose
 */
//@Configuration
//@EnableWebSecurity
/*
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

    /*
     Storing in H2 Mem DB, dataSource bean will be injected
     UserDetailsService is Core interface which loads user-specific data

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user = User.withUsername("tester")
                .password("tester")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("USER")
                .build();

        var user2 = User.withUsername("alex")
                .password("alex")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("ADMIN", "USER")
                .build();

        // Create UserDetailsManager (Provides CRUD operations for both users and groups.)
        // Insert 2 users
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(user2);

        return jdbcUserDetailsManager;
}


    /*
        Execute DDL script at start up, path to DDL script is available in JdbcDaoImpl static variable
     */
/*
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    /*
     Password Hash
     Clients can optionally supply a "version" ($2a, $2b, $2y) and a "strength" (a.k.a. log rounds in BCrypt) and a SecureRandom instance.
     The larger the strength parameter the more work will have to be done (exponentially) to hash the passwords. The default value is 10.
*/
/*
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


 */