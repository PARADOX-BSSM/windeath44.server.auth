package com.example.auth.domain.mail.dto.request;

public record PasswordValidationCodeRequest (
        String authorizationCode
) {
}