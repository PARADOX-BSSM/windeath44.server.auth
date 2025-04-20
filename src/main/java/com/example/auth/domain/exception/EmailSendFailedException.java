package com.example.auth.domain.exception;

public class EmailSendFailedException extends EmailException {
  public EmailSendFailedException(String s) {
    super(s);
  }
}
