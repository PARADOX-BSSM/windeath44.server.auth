package com.example.auth.domain.mail.service.exception;

public class EmailVerificationFailedException extends EmailException {
  public EmailVerificationFailedException(String s) {
   super(s);
  }
}
