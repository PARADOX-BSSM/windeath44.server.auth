package com.example.auth.global.exception;

import com.example.auth.domain.exception.DontExistsUserException;
import com.example.auth.domain.exception.GrpcMappedException;
import com.example.auth.domain.exception.NotFoundRefreshTokenException;
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

  @ExceptionHandler(DontExistsUserException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void dontExistsUserException(DontExistsUserException e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(GrpcMappedException.class)
  public ResponseEntity<Void> grpcMappedException(GrpcMappedException e) {
    return ResponseEntity
            .status(e.getStatus())
            .build();
  }

}
