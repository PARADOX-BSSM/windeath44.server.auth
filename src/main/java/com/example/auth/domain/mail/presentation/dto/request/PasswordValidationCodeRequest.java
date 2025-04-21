package com.example.auth.domain.mail.presentation.dto.request;

public record PasswordValidationCodeRequest (
    String authorizationCode
) {
}
