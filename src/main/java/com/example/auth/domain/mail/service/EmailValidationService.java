package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.domain.EmailValidation;
import com.example.auth.domain.mail.domain.repository.EmailValidationRepository;
import com.example.auth.domain.mail.service.exception.NotFoundEmailValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidationService {
  private final EmailValidationRepository emailValidationRepository;

  public void initEmailVerification(String email) {
    EmailValidation emailValidation = EmailValidation.create(email);
    emailValidationRepository.save(emailValidation);
  }

  public void verifyEmail(String email) {
    EmailValidation emailValidation = getEmailValidation(email);
    emailValidation.access();
    emailValidationRepository.save(emailValidation);
  }

  private EmailValidation getEmailValidation(String email) {
    EmailValidation emailValidation  = emailValidationRepository.findById(email)
            .orElseThrow(() -> new NotFoundEmailValidationException("Not Found randomStringKey with code"));
    return emailValidation;
  }
}
