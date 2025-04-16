package com.example.auth.domain.service;

import com.example.auth.domain.exception.NotFoundUserException;
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

  public UserCheckInfo checkUser(String userId, String password) {
    CheckUserResponse response = sendToLoginUserRequest(userId, password);
    boolean userExists = response.getExistsUser();

    validateIfUserExist(userExists);

    userId = response.getUserId();
    String role = response.getRole();
    UserCheckInfo userCheckInfo = UserCheckInfo.create(userId, role);
    return userCheckInfo;
  }

  private void validateIfUserExist(boolean userExists) {
    if (!userExists) {
      throw new NotFoundUserException("User don't exists.");
    }
  }

  private CheckUserResponse sendToLoginUserRequest (String userId, String password) {
    CheckUserRequest request = CheckUserRequest.newBuilder()
            .setUserId(userId)
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