package com.example.auth.domain.exception;

public class EmailVerificationFailedException extends EmailException {
  public EmailVerificationFailedException(String s) {
   super(s);
  }
}
