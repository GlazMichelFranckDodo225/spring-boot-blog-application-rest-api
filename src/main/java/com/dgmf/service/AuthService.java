package com.dgmf.service;

import com.dgmf.dto.LoginDto;
import com.dgmf.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
