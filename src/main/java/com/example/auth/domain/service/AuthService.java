package com.example.auth.domain.service;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.service.gRPC.GrpcClientService;
import com.example.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
  private final GrpcClientService grpcClientService;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public TokenResponse login(UserLoginRequest request) {
    String userKey = grpcClientService.checkUser(request.userId(), request.password());
    TokenResponse tokenResponse = jwtProvider.getTokenResponse(userKey);
    return tokenResponse;
  }

  public TokenResponse reissue(String refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken);
    String userKey = token.getUserKey();
    TokenResponse tokenResponse = jwtProvider.getTokenResponse(userKey);
    return tokenResponse;
  }

  public void logout(String refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new NotFoundRefreshTokenException("Not found refresh token with id"));
    refreshTokenRepository.delete(token); // only delete
  }

  private RefreshToken getRefreshToken(String refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new NotFoundRefreshTokenException("Not found refresh token with id"));
    refreshTokenRepository.delete(token); // To rotate refresh token
    return token;
  }


}
