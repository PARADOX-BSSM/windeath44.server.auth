package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.model.RandomStringKey;
import com.example.auth.domain.mail.mapper.RandomStringKeyMapper;
import com.example.auth.domain.mail.repository.RandomStringKeyRepository;
import com.example.auth.domain.mail.exception.NotFoundRandomStringKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidationService {
  private final RandomStringKeyRepository randomStringKeyRepository;
  private final RandomStringKeyMapper randomStringKeyMapper;

  public void initRandomStringKey(String randomStringKey, String email) {
    RandomStringKey randomStringKeyEntity = randomStringKeyMapper.createRandomStringKey(randomStringKey, email);
    randomStringKeyRepository.save(randomStringKeyEntity);
  }

  public void verifyCode(String code) {
    RandomStringKey randomStringKey = randomStringKeyRepository.findById(code)
            .orElseThrow(NotFoundRandomStringKeyException::getInstance);
    // no error is success
    randomStringKeyRepository.delete(randomStringKey);
  }
}
