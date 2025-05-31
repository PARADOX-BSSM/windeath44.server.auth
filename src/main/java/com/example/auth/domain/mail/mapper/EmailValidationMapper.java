package com.example.auth.domain.mail.domain.mapper;

import com.example.auth.domain.mail.domain.EmailValidation;
import com.example.auth.domain.mail.domain.EmailValidationState;
import com.example.auth.domain.mail.presentation.dto.response.EamilValidationResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailValidationMapper {
  public EamilValidationResponse toEmailValidationResponse(EmailValidation emailValidation) {
    return new EamilValidationResponse(emailValidation.getState());
  }
  public EmailValidation createEmailValidation(String email) {
    return new EmailValidation(email, EmailValidationState.PENDING);
  }
}
