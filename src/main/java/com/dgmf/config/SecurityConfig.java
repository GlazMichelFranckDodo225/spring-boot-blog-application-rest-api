package com.dgmf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Java-Based Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        // To Enable Http Basic Authentication
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                // Any Http Incoming Requests Should Be Authenticated
                .authorizeHttpRequests(
                        authorize -> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        // Returns DefaultSecurityFilterChain (Implementation Class of
        // SecurityFilterChain Interface)
        return httpSecurity.build();
    }
}
