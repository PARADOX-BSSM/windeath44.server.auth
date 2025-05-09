package com.example.auth.domain.auth.service;

import com.example.auth.domain.auth.domain.RefreshToken;
import com.example.auth.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.auth.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.auth.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.auth.presentation.dto.response.TokenResponse;
import com.example.auth.domain.gRPC.presentation.dto.response.UserCheckInfo;

import com.example.auth.domain.gRPC.service.GrpcClientService;
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
    UserCheckInfo userCheckInfo = grpcClientService.checkUser(request.userId(), request.password());
    String userId = userCheckInfo.userId();
    String role = userCheckInfo.role();
    TokenResponse tokenResponse = jwtProvider.getTokenResponse(userId, role);
    return tokenResponse;
  }

  public TokenResponse reissue(String refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken);
    String userId = token.getUserId();
    String role = token.getRole();
    TokenResponse tokenResponse = jwtProvider.getTokenResponse(userId, role);
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
