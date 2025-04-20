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
  private String userKey;

  public static RefreshToken create(String refreshToken, String userKey) {
    return RefreshToken.builder()
            .refreshToken(refreshToken)
            .userKey(userKey)
            .build();
  }
}
