package com.example.auth.domain.mail.exception;

public class EmailSendFailedException extends EmailException {
  public EmailSendFailedException(String s) {
    super(s);
  }
}
