package com.example.auth.domain.gRPC.exception;

public class NotFoundUserException extends RuntimeException {
  public NotFoundUserException(String s) {
    super(s);
  }
}
