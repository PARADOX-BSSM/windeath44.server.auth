package com.example.auth.domain.mail.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "EmailValidation", timeToLive = 600)
@AllArgsConstructor
public class EmailValidation {
  @Id
  private String emailValidationKey;
  private EmailValidationState state;

  public void access() {
    this.state = EmailValidationState.ACCESS;
  }

  public void ValidateEmail() {
    this.state.isAccess();
  }

  public EmailValidationState getState() {
    return this.state;
  }
}
