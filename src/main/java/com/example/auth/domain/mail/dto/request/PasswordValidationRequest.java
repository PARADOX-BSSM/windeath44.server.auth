package com.example.auth.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;

public record PasswordValidationRequest(
        @NotNull(message="userId is null")
        String userId,
        @NotNull(message="email is null")
        String email
) {
}