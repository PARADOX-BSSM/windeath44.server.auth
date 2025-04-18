package com.example.auth.domain.service;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.presentation.dto.response.UserCheckInfo;
import com.example.auth.domain.service.gRPC.GrpcClientService;
import com.example.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
  private final GrpcClientService authGrpcService;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public TokenResponse login(UserLoginRequest request) {
    UserCheckInfo userCheckInfo = authGrpcService.checkUser(request.userId(), request.password());

    String userId = userCheckInfo.userId();
    String role = userCheckInfo.role();
    TokenResponse tokenResponse = getTokenResponse(userId, role);
    return tokenResponse;
  }

  private TokenResponse getTokenResponse(String userId, String role) {
    String accessToken = jwtProvider.createAccessToken(userId, role);
    String refreshToken = jwtProvider.createRefreshToken(userId, role);
    TokenResponse tokenResponse = TokenResponse.toTokenResponse(accessToken, refreshToken);
    return tokenResponse;
  }

  public TokenResponse reissue(String refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken);
    String userId = token.getUserId();
    String role = token.getRole();
    TokenResponse tokenResponse = getTokenResponse(userId, role);
    return tokenResponse;
  }

  public void logout(String refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken); // only delete
  }

  private RefreshToken getRefreshToken(String refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new NotFoundRefreshTokenException("Not found refresh token with id"));
    refreshTokenRepository.delete(token); // To rotate refresh token
    return token;
  }


}
