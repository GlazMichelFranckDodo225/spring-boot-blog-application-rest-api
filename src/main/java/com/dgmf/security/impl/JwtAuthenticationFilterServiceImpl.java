package com.dgmf.security.impl;

import com.dgmf.security.JwtAuthenticationFilterService;
import com.dgmf.security.JwtTokenProviderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// To Authenticate JWT Token Before Spring Security Filter Chain
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterServiceImpl
        extends OncePerRequestFilter
        implements JwtAuthenticationFilterService {
    private final JwtTokenProviderService jwtTokenProviderService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        // Get JWT Token from HTTP Request
        String token = getTokenFromRequest(request);

        // Validate JWT Token
        if(StringUtils.hasText(token) && jwtTokenProviderService.validateToken(token)) {
            // Get Username from JWT Token
            String username = jwtTokenProviderService.getUsernameByToken(token);

            // Get User by "username" from DB which contains "username", "password" and "authorities"
            UserDetails retrievedUserWithUserDetailsFromDB =
                    userDetailsService.loadUserByUsername(username);

            // Add Retrieved User from DB and his Authorities to be Authenticated by
            // a "UsernamePasswordAuthenticationToken" Object
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(
                            retrievedUserWithUserDetailsFromDB,
                            null, // Credentials has already to be sent above
                            retrievedUserWithUserDetailsFromDB.getAuthorities()
                    );

            // Add the Request to the "UsernamePasswordAuthenticationToken" Object
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Add the "UsernamePasswordAuthenticationToken" Object to the "SecurityContextHolder"
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Once the JWT Token is Authenticated and Add to the Security Context
        // Holder, Calling of the Spring Security Filter Chain
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        // Get the Bearer Token from the Request Header :
        // Key ==> Authorization / Value ==> Bearer
        String bearerToken = httpServletRequest.getHeader("Authorization");

        // Get the JWT Token from the Bearer Token
        // Key ==> Bearer / Value ==> JWT Token
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
