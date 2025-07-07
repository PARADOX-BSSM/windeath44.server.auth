package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.dto.request.ValidationEmailCodeRequest;
import com.example.auth.domain.mail.model.EmailValidation;
import com.example.auth.domain.mail.mapper.EmailValidationMapper;
import com.example.auth.domain.mail.repository.EmailValidationRepository;
import com.example.auth.domain.mail.exception.NotFoundEmailValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidationService implements ValidationService {
  private final EmailValidationRepository emailValidationRepository;
  private final EmailValidationMapper emailValidationMapper;

  public void init(String randomStringKey, String email) {
    EmailValidation emailValidation = emailValidationMapper.createEmailValidation(email, randomStringKey);
    emailValidationRepository.save(emailValidation);
  }

  public void verifyEmail(ValidationEmailCodeRequest validationEmailCodeRequest) {
    String randomStringKey = validationEmailCodeRequest.authorizationCode();
    String email = validationEmailCodeRequest.email();

    EmailValidation emailValidation = findEmailValidationById(email);
    emailValidation.verify(randomStringKey);
    emailValidationRepository.save(emailValidation);
  }

  private EmailValidation findEmailValidationById(String email) {
    EmailValidation emailValidation  = emailValidationRepository.findById(email)
            .orElseThrow(NotFoundEmailValidationException::getInstance);
    return emailValidation;
  }
}
