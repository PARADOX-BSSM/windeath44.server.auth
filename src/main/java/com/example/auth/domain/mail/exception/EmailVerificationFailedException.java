package com.example.auth.domain.mail.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class EmailVerificationFailedException extends GlobalException {
  public EmailVerificationFailedException() {
    super(ErrorCode.EMAIL_VERIFICATION_FAILED);
  }
  private static class Holder {
    private static final EmailVerificationFailedException INSTANCE = new EmailVerificationFailedException();
  }
  public static EmailVerificationFailedException getInstance() {
    return Holder.INSTANCE;
  }
}
