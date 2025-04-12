package com.example.auth.domain.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refresh", timeToLive = 86400)
@Builder
@Getter
public class RefreshToken {
  @Id
  private String refreshToken;
  private String email;

  public static RefreshToken create(String email, String refreshToken) {
    return RefreshToken.builder()
            .email(email)
            .refreshToken(refreshToken)
            .build();
  }
}
