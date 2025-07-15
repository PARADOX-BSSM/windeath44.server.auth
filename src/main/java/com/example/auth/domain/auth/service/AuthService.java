package com.example.auth.domain.auth.service;

import com.example.auth.domain.auth.model.RefreshToken;
import com.example.auth.domain.auth.repository.RefreshTokenRepository;
import com.example.auth.domain.auth.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.auth.dto.request.UserLoginRequest;
import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;

import com.example.auth.domain.gRPC.service.GrpcClientService;
import com.example.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            .orElseThrow(NotFoundRefreshTokenException::getInstance);
    refreshTokenRepository.delete(token); // only delete
  }

  private RefreshToken getRefreshToken(String refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(NotFoundRefreshTokenException::getInstance);
    refreshTokenRepository.delete(token); // To rotate refresh token
    return token;
  }


}
