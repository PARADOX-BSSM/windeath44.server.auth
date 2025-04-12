package com.example.auth.domain.exception;

public class DontExistsUserException extends RuntimeException {
  public DontExistsUserException(String s) {
    super(s);
  }
}
