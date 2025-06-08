package com.example.auth.domain.gRPC.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class NotFoundUserException extends GlobalException {
  public NotFoundUserException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  private static class Holder {
    private static final NotFoundUserException INSTANCE = new NotFoundUserException();
  }
  public static NotFoundUserException getInstance() {
    return NotFoundUserException.Holder.INSTANCE;
  }

}
