package com.example.auth.domain.service;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.presentation.dto.request.TokenRequest;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
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
    authGrpcService.checkUser(email, request.password());
    TokenResponse tokenResponse = getTokenResponse(email);
    return tokenResponse;
  }

  private TokenResponse getTokenResponse(String email) {
    String accessToken = jwtProvider.createAccessToken(email);
    String refreshToken = jwtProvider.createRefreshToken(email);
    TokenResponse tokenResponse = TokenResponse.toTokenResponse(accessToken, refreshToken);
    return tokenResponse;
  }

  public TokenResponse reissue(TokenRequest refreshToken) {
    RefreshToken token = getRefreshToken(refreshToken);
    TokenResponse tokenResponse = getTokenResponse(token.getEmail());
    return tokenResponse;
  }

  private RefreshToken getRefreshToken(TokenRequest refreshToken) {
    RefreshToken token = refreshTokenRepository.findById(refreshToken.refreshToken())
            .orElseThrow(() -> new NotFoundRefreshTokenException("Not found refresh token with id"));
    refreshTokenRepository.delete(token); // refresh token rotate
    return token;
  }

}
