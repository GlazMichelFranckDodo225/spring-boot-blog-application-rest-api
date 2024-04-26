package com.dgmf.security;

import org.springframework.security.core.Authentication;

public interface JwtTokenProviderService {
    // Generate JWT Token
    String generateToken(Authentication authentication);
    // Get Username from JWT Token
    String getUsernameByToken(String token);
    // Validate JWT Token
    Boolean validateToken(String token);
}
