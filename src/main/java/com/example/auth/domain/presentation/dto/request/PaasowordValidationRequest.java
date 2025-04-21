package com.example.auth.domain.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record PaasowordValidationRequest(
        @NotNull(message="userId is null")
        String userId,
        @NotNull(message="email is null")
        String email
) {
}
