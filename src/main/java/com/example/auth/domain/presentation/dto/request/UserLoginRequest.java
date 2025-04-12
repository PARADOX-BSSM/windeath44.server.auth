package com.example.auth.domain.presentation.dto.request;

public record UserLoginRequest (
        String email,
        String name,
        String password
) {
}
