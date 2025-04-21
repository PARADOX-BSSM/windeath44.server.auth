package com.example.auth.domain.mail.exception;

public class NotFoundRandomStringKeyException extends RuntimeException {
  public NotFoundRandomStringKeyException(String s) {
    super(s);
  }
}
