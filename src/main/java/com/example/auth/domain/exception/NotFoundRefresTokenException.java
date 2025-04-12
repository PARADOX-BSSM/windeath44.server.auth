package com.example.auth.domain.exception;

public class NotFoundRefresTokenException extends RuntimeException {
  public NotFoundRefresTokenException(String s) {
    super(s);
  }
}
