package com.example.auth.domain.service.gRPC;

import com.example.auth.domain.exception.NotFoundUserException;
import com.example.auth.domain.exception.GrpcMappedException;
import com.example.auth.domain.exception.GrpcStatusMapper;
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
  private OauthUserLoginServiceGrpc.OauthUserLoginServiceBlockingStub oauthUserLoginServiceBlockingStub;

  public String checkUser(String userId, String password) {
    UserLoginResponse response = sendToLoginUserRequest(userId, password);
    boolean userExists = response.getExistsUser();

    validateIfUserExist(userExists);

    String userKey = response.getUserKey();
    return userKey;
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
    UserLoginResponse response = getCheckLoginUserResponse(request);
    return response;
  }

  public String registerUserFromOauth(String email, String name) {
      OauthUserLoginResponse oauthUserLoginResponse = sendToOAuthUserRequest(email, name);
      String userKey = oauthUserLoginResponse.getUserKey();
      return userKey;
  }

  private OauthUserLoginResponse sendToOAuthUserRequest (String email, String name) {
    OauthUserLoginRequest request = OauthUserLoginRequest.newBuilder()
            .setEmail(email)
            .setProfile(name)
            .build();
    OauthUserLoginResponse response = getOAuthUserResponse(request);
    return response;
  }

  private OauthUserLoginResponse getOAuthUserResponse(OauthUserLoginRequest request) {
    OauthUserLoginResponse response = oauthUserLoginServiceBlockingStub.oauthUserRegister(request);
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
}