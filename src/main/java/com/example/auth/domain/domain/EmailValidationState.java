package com.example.auth.domain.domain;

import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EmailValidationState {
  PENDING, ACCESS {
    @Override
    public void isAccess() {
      // validate ok
    }
  };

  public void isAccess() {
    log.error("here!!!!!!");
    throw Status.FAILED_PRECONDITION
            .withDescription("Email has already been used.")
            .asRuntimeException();
  }

}
