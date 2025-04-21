package com.example.auth.domain.presentation.dto.request;

public record PasswordValidationCodeRequest (
    String authorizationCode
) {
}
