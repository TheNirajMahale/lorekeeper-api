package com.lorekeeper.lorekeeper_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF because JWT tokens are immune to CSRF attacks by design
            .csrf(csrf -> csrf.disable())
            
            // Stateless sessions — no server-side session cookies
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                // Public: registration and login
                .requestMatchers("/auth/**").permitAll()
                
                // Public: browsing the book catalog (read-only)
                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                
                // Public: searching Open Library (read-only, external proxy)
                .requestMatchers(HttpMethod.GET, "/open-library/**").permitAll()
                
                // TODO: lock down /users/me/library once Phase 4 (JWT auth) is implemented — currently open for testing
                .requestMatchers("/users/me/library/**").permitAll()
                
                // Private: everything else requires a valid JWT token
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
