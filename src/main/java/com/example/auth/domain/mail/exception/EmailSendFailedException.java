package com.example.auth.domain.mail.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class EmailSendFailedException extends GlobalException {
  public EmailSendFailedException() {
    super(ErrorCode.EMAIL_SENDING_FAILED);
  }

  private static class Holder {
    private static final EmailSendFailedException INSTANCE = new EmailSendFailedException();
  }
  public static EmailSendFailedException getInstance() {
    return Holder.INSTANCE;
  }
}
