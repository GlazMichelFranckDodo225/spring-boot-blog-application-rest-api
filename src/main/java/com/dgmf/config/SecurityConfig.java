package com.dgmf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Java-Based Configuration
@EnableMethodSecurity // Method Level Security
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        // To Enable Http Basic Authentication
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .authorizeHttpRequests(authorize ->
                        // Any Http Incoming Requests Should Be Authenticated
                        // authorize.anyRequest().authenticated()
                        authorize.requestMatchers(
                                // All GET Requests Are Authorized on the Url
                                HttpMethod.GET, "/api/v1/**")
                                .permitAll()
                                // All Other Requests Must Be Authenticated
                                .anyRequest().authenticated()
                    )
                .httpBasic(Customizer.withDefaults());

        // Returns DefaultSecurityFilterChain (Implementation Class of
        // SecurityFilterChain Interface)
        return httpSecurity.build();
    }

    // To Encode Passwords
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Few Users Stored Into a In-Memory Object (InMemoryUserDetailsManager
    // Instance)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails johnDoe = User.builder()
                .username("johnDoe")
                .password(passwordEncoder().encode("0123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("4567"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(johnDoe, admin);
    }
}
