package com.dgmf.security;

import com.dgmf.dto.LoginDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);
}
