package com.example.auth.domain.gRPC.exception;

import io.grpc.Status;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum GrpcStatusMapper {
  NOT_FOUND(Status.Code.NOT_FOUND, HttpStatus.NOT_FOUND);

  private static final Map<Status.Code, HttpStatus> ERROR_MAP = Arrays.stream(values())
          .collect(Collectors.toMap(e -> e.code, e -> e.httpStatus));

  private final Status.Code code;
  private final HttpStatus httpStatus;

  GrpcStatusMapper(Status.Code code, HttpStatus httpStatus) {
    this.code = code;
    this.httpStatus = httpStatus;
  }
  public static HttpStatus resolve(Status.Code code) {
    return ERROR_MAP.getOrDefault(code, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}