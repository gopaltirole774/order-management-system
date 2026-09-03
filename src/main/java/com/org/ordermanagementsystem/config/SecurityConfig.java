package com.org.ordermanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**"
                                , "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders", "/orders/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/orders/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/orders/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/orders/*").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


}
