package com.example.auth.domain.mail.mapper;

import com.example.auth.domain.mail.dto.response.EmailValidationResponse;
import com.example.auth.domain.mail.model.EmailValidation;
import com.example.auth.domain.mail.model.EmailValidationState;
import org.springframework.stereotype.Component;

@Component
public class EmailValidationMapper {
  public EmailValidationResponse toEmailValidationResponse(EmailValidation emailValidation) {
    return new EmailValidationResponse(emailValidation.getState());
  }
  public EmailValidation createEmailValidation(String randomStringKey) {
    return new EmailValidation(randomStringKey, EmailValidationState.PENDING);
  }
}
