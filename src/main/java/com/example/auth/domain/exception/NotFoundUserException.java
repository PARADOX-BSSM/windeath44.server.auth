package com.example.auth.domain.exception;

public class NotFoundUserException extends RuntimeException {
  public NotFoundUserException(String s) {
    super(s);
  }
}
