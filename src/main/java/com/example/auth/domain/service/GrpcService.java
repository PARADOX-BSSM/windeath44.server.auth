package com.example.auth.domain.service;

import com.example.auth.domain.exception.DontExistsUserException;
import com.example.auth.domain.exception.GrpcMappedException;
import com.example.auth.domain.exception.GrpcStatusMapper;
import com.example.auth.domain.presentation.dto.response.UserCheckInfo;
import com.example.grpc.AuthenticationServiceGrpc;
import com.example.grpc.CheckUserRequest;
import com.example.grpc.CheckUserResponse;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GrpcService {
  @GrpcClient("user-server")
  private AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationSerivceBlockingStub;

  public UserCheckInfo checkUser(String email, String password) {
    CheckUserResponse response = sendToLoginUserRequest(email, password);
    boolean userExists = response.getExistsUser();

    if (!userExists) {
      throw new DontExistsUserException("User don't exists.");
    }

    String userId = response.getUserId();
    String role = response.getRole();
    UserCheckInfo userCheckInfo = UserCheckInfo.create(userId, role);
    return userCheckInfo;
  }

  private CheckUserResponse sendToLoginUserRequest (String email, String password) {
    CheckUserRequest request = CheckUserRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();
    CheckUserResponse response = getCheckUserResponse(request);
    return response;
  }

  private CheckUserResponse getCheckUserResponse(CheckUserRequest request) {
    try {
      CheckUserResponse response = authenticationSerivceBlockingStub.checkUser(request);
      return response;
    } catch (StatusRuntimeException e) {
      throw new GrpcMappedException(e.getStatus().getDescription(), GrpcStatusMapper.resolve(e.getStatus().getCode()));
    }
  }
}