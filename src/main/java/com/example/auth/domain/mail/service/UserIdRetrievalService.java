package com.example.auth.domain.mail.service;

import com.example.auth.domain.gRPC.service.GrpcClientService;
import com.example.auth.domain.mail.model.RandomStringKey;
import com.example.auth.domain.mail.mapper.RandomStringKeyMapper;
import com.example.auth.domain.mail.repository.RandomStringKeyRepository;
import com.example.auth.domain.mail.exception.NotFoundRandomStringKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.auth.domain.mail.dto.response.UserIdRetrievalResponse;

@Service
@RequiredArgsConstructor
public class UserIdRetrievalService implements ValidationService {
  private final RandomStringKeyRepository randomStringKeyRepository;
  private final RandomStringKeyMapper randomStringKeyMapper;
  private final GrpcClientService grpcClientService;

  public void init(String randomStringKey, String email) {
    RandomStringKey randomStringKeyEntity = randomStringKeyMapper.createRandomStringKey(randomStringKey, email);
    randomStringKeyRepository.save(randomStringKeyEntity);
  }

  public UserIdRetrievalResponse retrieveUserIdByCode(String code) {
    RandomStringKey randomStringKey = randomStringKeyRepository.findById(code)
            .orElseThrow(NotFoundRandomStringKeyException::getInstance);

    String email = randomStringKey.email;
    String userId = grpcClientService.getUserIdByEmail(email);

    randomStringKeyRepository.delete(randomStringKey);
    return UserIdRetrievalResponse.of(userId);
  }
}