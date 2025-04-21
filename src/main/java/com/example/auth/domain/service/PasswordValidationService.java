package com.example.auth.domain.service;

import com.example.auth.domain.domain.EmailValidation;
import com.example.auth.domain.domain.RandomStringKey;
import com.example.auth.domain.domain.repository.RandomStringKeyRepository;
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


}
