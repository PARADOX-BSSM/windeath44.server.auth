package com.example.auth.domain.gRPC.service.exception;

import org.springframework.http.HttpStatus;

public class GrpcMappedException extends RuntimeException {
  private final HttpStatus status;

  public GrpcMappedException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
