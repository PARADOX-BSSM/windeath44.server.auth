package com.example.auth.domain.mail.model;

import com.example.auth.domain.mail.exception.EmailVerificationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "EmailValidation", timeToLive = 300)
@AllArgsConstructor
public class EmailValidation {
  @Id
  private String email;
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

  public void verify(String randomStringKey) {
    if (this.emailValidationKey.equals(randomStringKey)) {
      this.state = EmailValidationState.ACCESS;
    } else {
      throw EmailVerificationFailedException.getInstance();
    }
  }
}
