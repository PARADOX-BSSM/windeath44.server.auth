package com.example.auth.global.exception;

import com.example.auth.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundRefreshTokenException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFoundRefresTokenExceptionHandler(NotFoundRefreshTokenException e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(NotFoundUserException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void dontExistsUserExceptionHandler(NotFoundUserException e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(GrpcMappedException.class)
  public ResponseEntity<Void> grpcMappedExceptionHandler(GrpcMappedException e) {
    return ResponseEntity
            .status(e.getStatus())
            .build();
  }

  @ExceptionHandler(NotFoundRandomStringKeyException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFoundRandomStringKeyExceptionHandler(NotFoundRandomStringKeyException e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(EmailSendFailedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public void emailSendFailedExceptionHandler(EmailSendFailedException e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(EmailException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void emailExceptionHandler(EmailException e) {
    log.error(e.getMessage());
  }



}
