package com.example.auth.domain.mail.entity;

import io.grpc.Status;

public enum EmailValidationState {
  PENDING, ACCESS {
    @Override
    public void isAccess() {
      // validate ok
    }
  };

  public void isAccess() {
    throw Status.FAILED_PRECONDITION
            .withDescription("Email has already been used.")
            .asRuntimeException();
  }

}
