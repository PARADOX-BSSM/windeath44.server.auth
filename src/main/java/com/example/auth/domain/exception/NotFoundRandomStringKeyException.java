package com.example.auth.domain.exception;

public class NotFoundRandomStringKeyException extends RuntimeException {
  public NotFoundRandomStringKeyException(String s) {
    super(s);
  }
}
