package com.example.auth.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest (
        @NotNull(message="유저 ID가 비어있습니다.")
        String userId,
        @NotNull(message="비밀번호가 비어았습니다.")
        String password
) {
}
