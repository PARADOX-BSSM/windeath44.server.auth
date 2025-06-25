package com.example.auth.domain.auth.exception;

import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;

public class NotFoundRefreshTokenException extends GlobalException {
  public NotFoundRefreshTokenException() {
    super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
  }

  private static class Holder {
    private static final NotFoundRefreshTokenException INSTANCE = new NotFoundRefreshTokenException();
  }
  public static NotFoundRefreshTokenException getInstance() {
    return NotFoundRefreshTokenException.Holder.INSTANCE;
  }


}