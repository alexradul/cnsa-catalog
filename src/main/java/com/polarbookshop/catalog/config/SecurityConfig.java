package com.polarbookshop.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        // Allows users to fetch greetings and books without being authenticated
                        .mvcMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
                        // all other request must be authenticated
                        .anyRequest().hasRole("employee")
                )
                // Enables OAuth2 Resource Server support using the default configuration based on JWT (JWT authentication)
                .oauth2ResourceServer(
                        OAuth2ResourceServerConfigurer::jwt
                )
                // Each request must include an Access Token, so there’s no need to keep
                // a user session alive between requests. We want it to be stateless.
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Since the authentication strategy is stateless and does not involve a browser-based client,
                // we can safely disable the CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtGrantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter
                .setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter
                .setAuthoritiesClaimName("roles");
        var jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter
                .setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
