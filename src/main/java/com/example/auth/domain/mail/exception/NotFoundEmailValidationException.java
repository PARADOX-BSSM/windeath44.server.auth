package com.example.auth.domain.mail.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class NotFoundEmailValidationException extends GlobalException {
  public NotFoundEmailValidationException() {
    super(ErrorCode.EMAIL_VALIDATION_NOT_FOUND);
  }
  private static class Holder {
    private static final NotFoundEmailValidationException INSTANCE = new NotFoundEmailValidationException();
  }
  public static NotFoundEmailValidationException getInstance() {
    return Holder.INSTANCE;
  }
}
