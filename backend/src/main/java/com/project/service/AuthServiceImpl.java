package com.project.service;

import com.project.dto.LoginRequestDto;
import com.project.dto.LoginResponseDto;
import com.project.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${app.auth.customer-email:customer@smartphoto.ai}")
    private String customerEmail;

    @Value("${app.auth.customer-password:Customer@123}")
    private String customerPassword;

    @Value("${app.auth.customer-name:Customer}")
    private String customerName;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new AuthenticationException("Invalid login request.");
        }

        String incomingEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        String expectedEmail = customerEmail.trim().toLowerCase(Locale.ROOT);

        if (!incomingEmail.equals(expectedEmail) || !request.getPassword().equals(customerPassword)) {
            throw new AuthenticationException("Invalid email or password.");
        }

        return new LoginResponseDto(
                UUID.randomUUID().toString(),
                customerEmail,
                customerName,
                "CUSTOMER"
        );
    }
}
