package com.example.auth.global.jwt;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.global.config.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private final JwtProperties jwtProperties;
  private final RefreshTokenRepository refreshTokenRepository;
  private final String REFRESH_TOKEN = "REFRESH_TOKEN";
  private final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private final KeyPair keyPair;

  public TokenResponse getTokenResponse(String userId, String role) {
    String accessToken = createAccessToken(userId,role);
    String refreshToken = createRefreshToken(userId, role);
    TokenResponse token = TokenResponse.create(accessToken, refreshToken);
    return token;
  }

  public String createRefreshToken(String userId, String role) {
    // refresh token -> redis
    String refreshToken = createToken(userId, REFRESH_TOKEN, jwtProperties.getRefreshTime(), role);
    RefreshToken token = RefreshToken.create(refreshToken, userId, role);
    refreshTokenRepository.save(token);
    return refreshToken;
  }

  public String createAccessToken(String userId, String role) {
    return createToken(userId, ACCESS_TOKEN, jwtProperties.getAccessTime(), role);
  }

  private String createToken(String userId, String type, Long time, String role) {
    Date now = new Date();
    return Jwts.builder()
            .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
            .setHeaderParam("type", type)
            .setSubject(userId)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + time))
            .setIssuer("windeath44-auth")
            .compact();
  }
}
