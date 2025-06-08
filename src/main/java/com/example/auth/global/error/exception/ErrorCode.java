package com.example.auth.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  EMAIL_SENDING_FAILED(500, "Email sending failed"),
  EMAIL_VERIFICATION_FAILED(400, "Email verification failed"),
  EMAIL_VALIDATION_NOT_FOUND(404, "Email validation not found"),
  RANDOM_STRING_KEY_NOT_FOUND(404, "Random string key not found"),
  USER_NOT_FOUND(404, "User not found"),
  REFRESH_TOKEN_NOT_FOUND(404, "Refresh token not found");

  private int status;
  private String message;
}
