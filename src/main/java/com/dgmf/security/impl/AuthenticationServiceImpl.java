package com.dgmf.security.impl;

import com.dgmf.dto.LoginDto;
import com.dgmf.dto.RegisterDto;
import com.dgmf.entity.Role;
import com.dgmf.entity.User;
import com.dgmf.exception.BlogAPIException;
import com.dgmf.repository.RoleRepository;
import com.dgmf.repository.UserRepository;
import com.dgmf.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {
        // To Authenticate User with UsernameOrEmail and Password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()
                )
        );

        // To Store the "authentication" Object into Spring Security Context Holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User Login In Successfully !";
    }

    @Override
    public String register(RegisterDto registerDto) {
        // Check If User exists in Database
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST, "Username is already exists !"
            );
        }

        // Check If Email exists in Database
        if(userRepository.existsByUsername(registerDto.getEmail())) {
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST, "Email is already exists !"
            );
        }

        // Create the User into Application
        User user = User.builder()
                .name(registerDto.getName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        // Assign "ROLE_USER" to the New User
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        // Save the New User
        userRepository.save(user);

        return "User Register Successfully !";
    }
}
