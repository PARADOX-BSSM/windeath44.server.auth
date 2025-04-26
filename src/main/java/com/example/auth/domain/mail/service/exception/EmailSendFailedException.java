package com.example.auth.domain.mail.service.exception;

public class EmailSendFailedException extends EmailException {
  public EmailSendFailedException(String s) {
    super(s);
  }
}
