package com.example.auth.domain.exception;

public class NotFoundRefreshTokenException extends RuntimeException {
  public NotFoundRefreshTokenException(String s) {
    super(s);
  }
}
