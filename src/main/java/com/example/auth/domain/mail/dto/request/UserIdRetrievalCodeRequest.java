package com.example.auth.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserIdRetrievalCodeRequest (
        @NotNull(message="code is null")
        String code
) {
}
