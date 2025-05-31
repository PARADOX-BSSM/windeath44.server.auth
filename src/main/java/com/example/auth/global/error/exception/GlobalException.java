package com.example.auth.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GlobalException extends RuntimeException {
  private final ErrorCode errorCode;
}
