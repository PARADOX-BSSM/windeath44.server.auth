package com.example.auth.global.jwt;

import com.example.auth.domain.auth.model.RefreshToken;
import com.example.auth.domain.auth.repository.RefreshTokenRepository;
import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;
import com.example.auth.global.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

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
            .setIssuer("windeath44")
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + time))
            .setIssuer("windeath44-auth")
            .compact();
  }

  public String getJwt(Map<String, String> headersMap) {
    String bearerToken = headersMap.get(jwtProperties.getHeader().toLowerCase());
    String bearer = jwtProperties.getPrefix();
    if (bearerToken.startsWith(bearer)) {
      String token = bearerToken.substring(bearer.length());
      return token;
    }
    return null;
  }

  public UserCheckInfo getUser(String token) {
    Claims claims = getClaims(token);
    String userId = claims.getSubject();
    String role = claims.get("role", String.class);
    UserCheckInfo user = UserCheckInfo.create(userId, role);
    return user;
  }

  private Claims getClaims(String token) {
    Claims claims = parse(token);
    return claims;
  }
  private Claims parse(String token) {
      return Jwts.parserBuilder().setSigningKey(keyPair.getPublic())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }
}
