package com.example.auth.domain.mail.exception;

public class EmailVerificationFailedException extends EmailException {
  public EmailVerificationFailedException(String s) {
   super(s);
  }
}
