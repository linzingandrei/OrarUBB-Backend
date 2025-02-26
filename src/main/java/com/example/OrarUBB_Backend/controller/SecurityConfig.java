package com.example.OrarUBB_Backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Value("${app.protect.authenticated}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.apply(AadWebApplicationHttpSecurityConfigurer.aadWebApplication())
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowedOrigins).authenticated()
                        .anyRequest().permitAll()
                );
        // @formatter:on
        return http.build();
    }

    @Bean
    @RequestScope
    public ServletUriComponentsBuilder urlBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }
}