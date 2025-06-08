package com.example.auth.global.error;

import com.example.auth.global.error.exception.ErrorCode;

public record ErrorResponse (
        int status,
        String message
) {
  public ErrorResponse(ErrorCode errorCode) {
    this(
            errorCode.getStatus(),
            errorCode.getMessage()
    );
  }
}
