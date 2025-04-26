package com.example.auth.domain.auth.service.exception;

public class NotFoundRefreshTokenException extends RuntimeException {
  public NotFoundRefreshTokenException(String s) {
    super(s);
  }
}
