package com.dgmf.security;

import com.dgmf.repository.UserRepository;
import com.dgmf.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

// Spring Security will Use this Class to Load User from DB and
// Perform Database Authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException
    {
          // Load User from DB
          User user = userRepository.findByUsernameOrEmail(
                  usernameOrEmail, usernameOrEmail
                  )
                 .orElseThrow(
                         () -> new UsernameNotFoundException(
                                 "User not found with username or email: " +
                                         usernameOrEmail
                                )
                 );


          // Convert a Set of Roles into a Set of Granted
          // Authorities Regarding Spring Security Expectations
          Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                  .collect(Collectors.toSet());

        // Convert Retrieved User from DB to Spring
        // Security Provided User Object and Return It
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
