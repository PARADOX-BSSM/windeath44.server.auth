package com.example.auth.domain.mail.dto.response;

import com.example.auth.domain.mail.model.EmailValidationState;

public record  EmailValidationResponse (
        EmailValidationState state
){

}
