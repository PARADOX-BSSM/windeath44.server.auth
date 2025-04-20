package com.example.auth.domain.exception;

public class NotFoundOAuthService extends RuntimeException {
  public NotFoundOAuthService(String s) {
    super(s);
  }
}
