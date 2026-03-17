package com.project.service;

import com.project.dto.LoginRequestDto;
import com.project.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
}
