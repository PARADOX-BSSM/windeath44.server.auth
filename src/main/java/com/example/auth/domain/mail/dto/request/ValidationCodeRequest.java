package com.example.auth.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;

public record ValidationCodeRequest (
        @NotNull(message="authorizationCode is null")
        String authorizationCode
) {
}