package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.model.EmailValidation;
import com.example.auth.domain.mail.mapper.EmailValidationMapper;
import com.example.auth.domain.mail.repository.EmailValidationRepository;
import com.example.auth.domain.mail.dto.response.EmailValidationResponse;
import com.example.auth.domain.mail.exception.NotFoundEmailValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidationService {
  private final EmailValidationRepository emailValidationRepository;
  private final EmailValidationMapper emailValidationMapper;

  public void initEmailVerification(String email) {
    EmailValidation emailValidation = emailValidationMapper.createEmailValidation(email);
    emailValidationRepository.save(emailValidation);
  }

  public void verifyEmail(String email) {
    EmailValidation emailValidation = findEmailValidationById(email);
    emailValidation.access();
    emailValidationRepository.save(emailValidation);
  }

  private EmailValidation findEmailValidationById(String email) {
    EmailValidation emailValidation  = emailValidationRepository.findById(email)
            .orElseThrow(NotFoundEmailValidationException::getInstance);
    return emailValidation;
  }

  public EmailValidationResponse getEmailValidationState(String email) {
    EmailValidation emailValidation = findEmailValidationById(email);
    EmailValidationResponse emailValidationResponse = emailValidationMapper.toEmailValidationResponse(emailValidation);
    return emailValidationResponse;
  }
}
