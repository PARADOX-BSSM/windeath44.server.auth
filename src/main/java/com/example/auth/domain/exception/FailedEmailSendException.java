package com.example.auth.domain.exception;

public class FailedEmailSendException extends RuntimeException {
  public FailedEmailSendException(String s) {
    super(s);
  }
}
