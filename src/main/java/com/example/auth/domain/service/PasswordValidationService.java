package com.example.auth.domain.service;

import com.example.auth.domain.domain.EmailValidation;
import com.example.auth.domain.domain.RandomStringKey;
import com.example.auth.domain.domain.repository.RandomStringKeyRepository;
import com.example.auth.domain.exception.NotFoundRandomStringKeyException;
import com.example.auth.domain.presentation.dto.request.PasswordValidationCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidationService {
  private final RandomStringKeyRepository randomStringKeyRepository;

  public void initRandomStringKey(String randomStringKey, String email) {
    RandomStringKey randomStringKeyEntity = RandomStringKey.create(randomStringKey, email);
    randomStringKeyRepository.save(randomStringKeyEntity);
  }


  public void verifyCode(String code) {
    RandomStringKey randomStringKey = randomStringKeyRepository.findById(code)
            .orElseThrow(() -> new NotFoundRandomStringKeyException("Not found Random String key with code"));
    // no error is success
    randomStringKeyRepository.delete(randomStringKey);
  }
}
