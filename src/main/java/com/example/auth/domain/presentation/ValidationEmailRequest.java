package com.example.auth.domain.presentation;


import jakarta.validation.constraints.Email;

public record ValidationEmailRequest (
        @Email
        String email
){
}
