package com.example.auth.domain.presentation.dto.request;

public record PaasowordValidationRequest(
        String userId,
        String email
) {
}
