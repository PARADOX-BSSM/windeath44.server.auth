package com.example.auth.domain.service;

import com.example.auth.domain.domain.EmailValidation;
import com.example.auth.domain.domain.EmailValidationState;
import com.example.auth.domain.domain.repository.EmailValidationRepository;
import com.example.auth.domain.exception.NotFoundRandomStringKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidationService {
  private final EmailValidationRepository emailValidationRepository;

  public void initEmailVerification(String email) {
    EmailValidation emailValidation = EmailValidation.create(email, EmailValidationState.PENDING);
    emailValidationRepository.save(emailValidation);
  }

  public void verifyEmail(String email) {
    EmailValidation emailValidation = getEmailValidation(email);
    emailValidation.access();
    emailValidationRepository.save(emailValidation);
  }

  private EmailValidation getEmailValidation(String email) {
    EmailValidation emailValidation  = emailValidationRepository.findById(email)
            .orElseThrow(() -> new NotFoundRandomStringKeyException("Not Found randomStringKey with code"));
    return emailValidation;
  }
}
