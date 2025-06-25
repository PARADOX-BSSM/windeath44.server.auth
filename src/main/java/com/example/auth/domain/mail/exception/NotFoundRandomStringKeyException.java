package com.example.auth.domain.mail.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class NotFoundRandomStringKeyException extends GlobalException {
  public NotFoundRandomStringKeyException() {
    super(ErrorCode.RANDOM_STRING_KEY_NOT_FOUND);
  }
  private static class Holder {
    private static final NotFoundRandomStringKeyException INSTANCE = new NotFoundRandomStringKeyException();
  }
  public static NotFoundRandomStringKeyException getInstance() {
    return Holder.INSTANCE;
  }
}
