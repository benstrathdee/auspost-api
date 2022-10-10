package com.example.postapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection for the sake of testing with Postman, etc.
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/suburbs/create").authenticated() // Only this endpoint requires authentication
                        .anyRequest().permitAll()
                ).formLogin(); // Simple authentication through the use of a login form at /login
        return http.build();
    }
}
