package com.example.auth.domain.mail.dto.response;

import com.example.auth.domain.mail.entity.EmailValidationState;

public record  EmailValidationResponse (
        EmailValidationState state
){

}
