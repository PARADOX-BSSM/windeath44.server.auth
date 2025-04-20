package com.example.auth.domain.exception;

public class NotFoundEmailValidationException extends RuntimeException {
  public NotFoundEmailValidationException(String s) {
    super(s);
  }
}
