package com.example.auth.domain.mail.entity;


import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash(value="RandomStringKey", timeToLive = 300)
@AllArgsConstructor
public class RandomStringKey {
  @Id
  String randomStringKey;
  String email;

  public static RandomStringKey create(String randomStringKey, String email) {
    return new RandomStringKey(randomStringKey, email);
  }

  public static String makeKey(int length) {
    return UUID.randomUUID().toString().substring(0, length);
  }

}
