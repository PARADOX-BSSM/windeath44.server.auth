package com.example.auth.domain.gRPC.service;

import com.example.auth.domain.mail.model.EmailValidation;
import com.example.auth.domain.mail.repository.EmailValidationRepository;
import com.example.grpc.UserRegisterRequest;
import com.example.grpc.UserRegisterResponse;
import com.example.grpc.UserRegisterServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@RequiredArgsConstructor
@Slf4j
public class GrpcUserRegisterService extends UserRegisterServiceGrpc.UserRegisterServiceImplBase {
  private final EmailValidationRepository emailValidationRepository;

  @Override
  public void checkEmailValidation(UserRegisterRequest request, StreamObserver<UserRegisterResponse> responseObserver) {
    // User server에서 회원가입 시, 이메일 검증 상태인지 확인
    try {
      String email = request.getEmail();
      EmailValidation emailValidation = getEmailValidation(email);
      emailValidation.ValidateEmail();
      UserRegisterResponse userRegisterResponse = UserRegisterResponse.newBuilder()
              .build();
      responseObserver.onNext(userRegisterResponse);
      responseObserver.onCompleted();
    } catch (StatusRuntimeException e) {
        log.error(e.toString());
        responseObserver.onError(e);
    }

  }

  private EmailValidation getEmailValidation(String email) {
    return emailValidationRepository.findById(email)
            .orElseThrow(() -> Status.NOT_FOUND
                            .withDescription("Email has already been used.")
                    .asRuntimeException());
  }
}
