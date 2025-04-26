package com.example.auth.domain.gRPC.service.exception;

public class NotFoundUserException extends RuntimeException {
  public NotFoundUserException(String s) {
    super(s);
  }
}
