package com.dgmf.security;

import com.dgmf.dto.LoginDto;
import com.dgmf.dto.RegisterDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
