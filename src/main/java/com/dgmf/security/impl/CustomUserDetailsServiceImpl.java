package com.dgmf.security.impl;

import com.dgmf.entity.User;
import com.dgmf.repository.UserRepository;
import com.dgmf.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

// Spring Security Will Use this Class to Load the User Object from
// the DB in Order to Make DB Authentication
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {
    private final UserRepository userRepository;

    // Customizing the Spring Security UserDetailsService
    // "loadUserByUsername()" method
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Retrieve User from DB
        User user = userRepository.findByUsernameOrEmail(
                usernameOrEmail, usernameOrEmail
                )
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User Not Found with Username or Email : " +
                                usernameOrEmail)
                );

        // Spring Security Expects a Set of Granted Authority
        // So We Have to Convert User Related Roles to Spring
        // Security User Simple Granted Authorities
        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Convert our Normal User Object into a Spring Security
        // Provided User Object Before to Return It
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities
        );
    }
}
