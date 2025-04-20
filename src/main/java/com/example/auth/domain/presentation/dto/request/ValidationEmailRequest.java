package com.example.auth.domain.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ValidationEmailRequest (
        @Email
        @NotNull(message="email is null")
        String email

  ){
  }