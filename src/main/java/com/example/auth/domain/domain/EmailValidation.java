package com.example.auth.domain.domain;

import com.example.auth.domain.exception.EmailVerificationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "EmailValidation", timeToLive = 600)
@AllArgsConstructor
public class EmailValidation {
  @Id
  private String email;
  private EmailValidationState state;

  public static EmailValidation create(String email, EmailValidationState state) {
    return new EmailValidation(email, state);
  }

  public void access() {
    this.state = EmailValidationState.ACCESS;
  }

  public void ValidateEmail() {
    this.state.isAccess();
  }
}
