package com.example.learn.springsecurity.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity // Spring Security will recognize and apply the configurations you've defined in your JwtSecurityConfig class
public class JwtSecurityConfig {

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
                // Oauth2 Resource server, Enables Jwt-encoded bearer token support.
                .oauth2ResourceServer(serverConfigurer ->
                        serverConfigurer.jwt(Customizer.withDefaults()))
                .build();
    }

    // Converting a Checked ex to a runtime ex
    @Bean
    public KeyPair keyPair() {
        KeyPairGenerator rsa;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rsa.initialize(2048);
        return rsa.generateKeyPair();
    }

    // Create RSA KEY OBJECT USING KEYPAIR, autowired bean in the params
    @Bean
    public RSAKey rsaKeyObject(KeyPair keyPair) {
        // Using RSA KEY BUILDER
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()) // type cast
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }


    // Create JWKSource (JSON Web Key Source) using JWKSet
    // Typically we can create a JWKSet with multiple keys
    // We create a new JwkSet with just one key (RSA KEY)
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        // Create JwkSet using RSAKey
        JWKSet jwkSet = new JWKSet(rsaKey);

        // Create JwkSource of type SecurityContext, using JwkSet + impl for get() (lambda)
        // we select the source of the keySet (the one we created above)
        JWKSource<SecurityContext> jwkSource = (jwkSelector, context) -> jwkSelector.select(jwkSet);

        return jwkSource;
    }

    // Create the JwtDecoder which is needed for Resource Server
    // Before we can create this one we need to create the beans above
    // We are not wiring this bean directly, so it's OK to throw exception here
   @Bean
   public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
                .build();
   }


   // JWT ENCODER
   @Bean
   public JwtEncoder JwtEncoder(JWKSource<SecurityContext> jwkSource) {
        // Using Nimbus providing JwkSource
       return new NimbusJwtEncoder(jwkSource);
   }





    /*
 Storing in H2 Mem DB, dataSource bean will be injected
 UserDetailsService is Core interface which loads user-specific data
 */
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
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
