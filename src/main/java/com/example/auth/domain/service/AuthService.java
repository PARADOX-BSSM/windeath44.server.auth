package com.example.auth.domain.service;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.presentation.dto.request.TokenRequest;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.presentation.dto.response.UserCheckInfo;
import com.example.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {
  private final GrpcService authGrpcService;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public TokenResponse login(UserLoginRequest request) {
    String email = request.email();
    UserCheckInfo userCheckInfo = authGrpcService.checkUser(email, request.password());

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

  public TokenResponse reissue(TokenRequest refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken);
    String userId = token.getUserId();
    String role = token.getRole();
    TokenResponse tokenResponse = getTokenResponse(userId, role);
    return tokenResponse;
  }

  private RefreshToken getRefreshToken(TokenRequest refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken.refreshToken())
            .orElseThrow(() -> new NotFoundRefreshTokenException("Not found refresh token with id"));
    refreshTokenRepository.delete(token); // refresh token rotate
    return token;
  }

}
