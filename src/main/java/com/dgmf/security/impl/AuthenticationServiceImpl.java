package com.dgmf.security.impl;

import com.dgmf.dto.LoginDto;
import com.dgmf.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

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
}
