package com.example.auth.domain.auth.exception;

public class NotFoundRefreshTokenException extends RuntimeException {
  public NotFoundRefreshTokenException(String s) {
    super(s);
  }
}
