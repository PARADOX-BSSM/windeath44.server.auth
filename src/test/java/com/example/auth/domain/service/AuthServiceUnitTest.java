package com.example.auth.domain.service;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.presentation.dto.request.TokenRequest;
import com.example.auth.global.jwt.JwtProvider;

import net.devh.boot.grpc.server.service.GrpcService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

  @InjectMocks
  AuthService authService;
  @Mock
  GrpcService authGrpcService;
  @Mock
  JwtProvider jwtProvider;
  @Mock
  RefreshTokenRepository refreshTokenRepository;


  @Test
  @DisplayName("refresh token 재발급 ( reissue ) ")
  void when_request_refresh_token_then_issue_refresh_token_successfuly() {
    TokenRequest tokenRequest = new TokenRequest("I'mRefreshToken");
    String refreshToken = tokenRequest.refreshToken();
    RefreshToken token = RefreshToken.create(refreshToken, "kingsejun");
    given(refreshTokenRepository.findById(refreshToken)).willReturn(Optional.ofNullable(token));
    authService.reissue(tokenRequest.refreshToken());
    then(jwtProvider).should().createAccessToken(token.getUserKey());
  }
}