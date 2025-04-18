package com.example.auth.domain.service.gRPC;

import com.example.auth.domain.exception.NotFoundUserException;
import com.example.auth.domain.exception.GrpcMappedException;
import com.example.auth.domain.exception.GrpcStatusMapper;
import com.example.auth.domain.presentation.dto.response.UserCheckInfo;
import com.example.grpc.UserLoginRequest;
import com.example.grpc.UserLoginResponse;
import com.example.grpc.UserLoginServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GrpcClientService {
  @GrpcClient("user-server")
  private UserLoginServiceGrpc.UserLoginServiceBlockingStub authenticationServiceBlockingStub;

  public UserCheckInfo checkUser(String userId, String password) {
    UserLoginResponse response = sendToLoginUserRequest(userId, password);
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

  private UserLoginResponse sendToLoginUserRequest (String userId, String password) {
    UserLoginRequest request = UserLoginRequest.newBuilder()
            .setUserId(userId)
            .setPassword(password)
            .build();
    UserLoginResponse response = getCheckUserResponse(request);
    return response;
  }

  private UserLoginResponse getCheckUserResponse(UserLoginRequest request) {
    try {
      UserLoginResponse response = authenticationServiceBlockingStub.checkUser(request);
      return response;
    } catch (StatusRuntimeException e) {
      throw new GrpcMappedException(e.getStatus().getDescription(), GrpcStatusMapper.resolve(e.getStatus().getCode()));
    }
  }
}