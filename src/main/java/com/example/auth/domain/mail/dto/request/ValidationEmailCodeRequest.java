package com.example.auth.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;

public record ValidationEmailCodeRequest (
        @NotNull(message="authorizationCode is null")
        String authorizationCode,
        @NotNull(message="email is null")
        String email
) {
}
