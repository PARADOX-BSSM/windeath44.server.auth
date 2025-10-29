package com.example.auth.domain.gRPC.service;

import com.example.auth.domain.gRPC.exception.NotFoundUserException;
import com.example.auth.domain.gRPC.exception.GrpcMappedException;
import com.example.auth.domain.gRPC.exception.GrpcStatusMapper;
import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;
import com.example.grpc.*;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcClientService {

  @GrpcClient("user-server")
  private UserLoginServiceGrpc.UserLoginServiceBlockingStub authenticationServiceBlockingStub;

  @GrpcClient("user-server")
  private UserIdRetrievalServiceGrpc.UserIdRetrievalServiceBlockingStub userIdRetrievalServiceBlockingStub;

  public UserCheckInfo checkUser(String userId, String password) {
    UserLoginResponse response = sendToLoginUserRequest(userId, password);
    boolean userExists = response.getExistsUser();

    validateIfUserExist(userExists);

    UserCheckInfo userCheckInfo = UserCheckInfo.create(response.getUserId(), response.getRole());

    return userCheckInfo;
  }

  private void validateIfUserExist(boolean userExists) {
    if (!userExists) {
      throw NotFoundUserException.getInstance();
    }
  }

  private UserLoginResponse sendToLoginUserRequest (String userId, String password) {
    UserLoginRequest request = UserLoginRequest.newBuilder()
            .setUserId(userId)
            .setPassword(password)
            .build();
    UserLoginResponse response = getCheckLoginUserResponse(request);
    return response;
  }


  private UserLoginResponse getCheckLoginUserResponse(UserLoginRequest request) {
    try {
      UserLoginResponse response = authenticationServiceBlockingStub.checkUser(request);
      return response;
    } catch (StatusRuntimeException e) {
      throw new GrpcMappedException(e.getStatus().getDescription(), GrpcStatusMapper.resolve(e.getStatus().getCode()));
    }
  }

  public String getUserIdByEmail(String email) {
    UserIdRetrievalRequest request = UserIdRetrievalRequest.newBuilder()
            .setEmail(email)
            .build();
    UserIdRetrievalResponse response = getUserIdRetrievalResponse(request);
    return response.getUserId();
  }

  private UserIdRetrievalResponse getUserIdRetrievalResponse(UserIdRetrievalRequest request) {
    try {
      UserIdRetrievalResponse response = userIdRetrievalServiceBlockingStub.getUserIdByEmail(request);
      return response;
    } catch (StatusRuntimeException e) {
      throw new GrpcMappedException(e.getStatus().getDescription(), GrpcStatusMapper.resolve(e.getStatus().getCode()));
    }
  }
}