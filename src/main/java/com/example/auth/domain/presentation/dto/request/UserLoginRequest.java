package com.example.auth.domain.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest (
        @NotNull(message="이메일이 비어았습니다.")
        String email,
        @NotNull(message="이름이 비어았습니다.")
        String name,
        @NotNull(message="비밀번호가 비어았습니다.")
        String password
) {
}
