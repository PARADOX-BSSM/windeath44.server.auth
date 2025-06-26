package com.example.auth.global.error;

import com.example.auth.domain.gRPC.exception.GrpcMappedException;
import com.example.auth.global.error.exception.ErrorCode;
import com.example.auth.global.error.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ErrorResponse> globalExceptionHandler (GlobalException e) {
    ErrorCode errorCode = e.getErrorCode();
    int status = errorCode.getStatus();
    log.error(errorCode.getMessage());
    return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(status));
  }

  @ExceptionHandler(GrpcMappedException.class)
  public ResponseEntity<Void> grpcMappedExceptionHandler(GrpcMappedException e) {
    return ResponseEntity
            .status(e.getStatus())
            .build();
  }



}
