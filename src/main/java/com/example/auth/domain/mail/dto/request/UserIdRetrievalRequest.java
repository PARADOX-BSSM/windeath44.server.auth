package com.example.auth.domain.mail.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserIdRetrievalRequest (
        @Email
        @NotNull(message="email is null")
        String email
){
}
