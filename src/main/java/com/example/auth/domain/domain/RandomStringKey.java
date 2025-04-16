package com.example.auth.domain.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "RandomStringKey", timeToLive = 300)
@AllArgsConstructor
public class RandomStringKey {
  @Id
  private String key;

  public static RandomStringKey create(String key) {
    return new RandomStringKey(key);
  }
}
