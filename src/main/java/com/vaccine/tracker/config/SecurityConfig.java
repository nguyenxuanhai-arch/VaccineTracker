package com.vaccine.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // For initial setup, we'll permit all requests
        // This will be updated with proper security later
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
            .headers().frameOptions().sameOrigin(); // Allow H2 console

        return http.build();
    }
}
