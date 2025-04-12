package com.example.auth.global.jwt;

import com.example.auth.domain.domain.RefreshToken;
import com.example.auth.domain.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private final JwtProperties jwtProperties;
  private final RefreshTokenRepository refreshTokenRepository;
  private final String REFRESHTOKEN = "RefreshToken";
  private final String ACCESSTOKEN = "RefreshToken";

  public String createRefreshToken(String email) {
    // refresh token -> redis
    String refreshToken = createToken(email, REFRESHTOKEN, jwtProperties.getRefreshTime());
    RefreshToken token = RefreshToken.create(email, refreshToken);
    refreshTokenRepository.save(token);
    return refreshToken;
  }

  public String createAccessToken(String email) {
    return createToken(email, ACCESSTOKEN, jwtProperties.getAccessTime());
  }

  private String createToken(String email, String type, Long time) {
    Date now = new Date();
    return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .setHeaderParam("type", type)
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + time))
            .compact();
  }
}
