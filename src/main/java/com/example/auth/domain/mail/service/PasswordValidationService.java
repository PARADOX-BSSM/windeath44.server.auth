package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.domain.RandomStringKey;
import com.example.auth.domain.mail.domain.repository.RandomStringKeyRepository;
import com.example.auth.domain.mail.service.exception.NotFoundRandomStringKeyException;
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
