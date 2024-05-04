package com.dgmf.config;

import com.dgmf.security.JwtAuthenticationEntryPointService;
import com.dgmf.security.JwtAuthenticationFilterService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Java-Based Configuration
@EnableMethodSecurity // Method Level Security
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPointService jwtAuthenticationEntryPointService;
    private final JwtAuthenticationFilterService jwtAuthenticationFilterService;

    // To Encode Passwords
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        // To Enable Http Basic Authentication
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
            .authorizeHttpRequests(authorize ->
                    // Any Http Incoming Requests Should Be Authenticated
                    // authorize.anyRequest().authenticated()
                    // All GET Requests Are Authorized on the Url
                    authorize
                            .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                            // All Other Requests Must Be Authenticated
                            .anyRequest().authenticated()
                    )
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint(
                                        (AuthenticationEntryPoint) jwtAuthenticationEntryPointService
                                )
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
            // .httpBasic(Customizer.withDefaults());

        // Executed before Spring Security Filter Chain
        httpSecurity.addFilterBefore(
                (Filter) jwtAuthenticationFilterService,
                UsernamePasswordAuthenticationFilter.class
        );

        // Returns DefaultSecurityFilterChain (Implementation Class of
        // SecurityFilterChain Interface)
        return httpSecurity.build();
    }

    // Few Users Stored Into a In-Memory Object (InMemoryUserDetailsManager
    // Instance)
    /*@Bean
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
    }*/
}
