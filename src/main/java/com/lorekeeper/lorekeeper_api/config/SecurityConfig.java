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

    private final com.lorekeeper.lorekeeper_api.security.JwtAuthenticationFilter jwtAuthFilter;
    private final com.lorekeeper.lorekeeper_api.security.MdcFilter mdcFilter;

    public SecurityConfig(com.lorekeeper.lorekeeper_api.security.JwtAuthenticationFilter jwtAuthFilter,
                          com.lorekeeper.lorekeeper_api.security.MdcFilter mdcFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.mdcFilter = mdcFilter;
    }

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
                
                // Private: everything else requires a valid JWT token
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(mdcFilter, com.lorekeeper.lorekeeper_api.security.JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
