package com.example.auth.domain.mail.service.exception;

public class NotFoundRandomStringKeyException extends RuntimeException {
  public NotFoundRandomStringKeyException(String s) {
    super(s);
  }
}
