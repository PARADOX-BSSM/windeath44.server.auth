package com.example.auth.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record TokenRequest (
        @NotNull(message="refresh token이 비어있습니다.")
        String refreshToken
) {

}
