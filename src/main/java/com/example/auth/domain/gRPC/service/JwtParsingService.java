package com.example.auth.domain.gRPC.service;

import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;
import com.example.auth.global.jwt.JwtProvider;
import io.envoyproxy.envoy.config.core.v3.HeaderValue;
import io.envoyproxy.envoy.config.core.v3.HeaderValueOption;
import io.envoyproxy.envoy.service.auth.v3.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Arrays;

@GrpcService
@RequiredArgsConstructor
public class JwtParsingService extends AuthorizationGrpc.AuthorizationImplBase {
  private final JwtProvider jwtProvider;

  @Override
  public void check(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {
    try {
      AttributeContext attributes = request.getAttributes();
      String jwtToken = jwtProvider.getJwt(attributes.getRequest().getHttp().getHeadersMap());
      // token이 Null
      if (ifNull(jwtToken == null || jwtToken.isEmpty(), "Missing JWT token", responseObserver)) return;
      // 유저 정보 추출

      UserCheckInfo user = jwtProvider.getUser(jwtToken);
      String userId = user.userId();
      String role = user.role();

      // userId가 Null
      if (ifNull(userId == null, "Invalid JWT token", responseObserver)) return;
      // 성공적으로 반환
      HeaderValueOption userIdHeader = getHeaderValueOption("user-id", userId);
      HeaderValueOption roleHeader = getHeaderValueOption("role", role);

      OkHttpResponse okResponse = getOkResponse(userIdHeader, roleHeader);

      CheckResponse response = getCheckResponse(okResponse);

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(new StatusException(Status.INTERNAL.withDescription(e.getMessage())));
    }
  }

  private HeaderValueOption getHeaderValueOption(String key, String value) {
    return HeaderValueOption.newBuilder()
            .setHeader(HeaderValue.newBuilder()
                    .setKey(key)
                    .setValue(value)
                    .build())
            .build();
  }

  private CheckResponse getCheckResponse(OkHttpResponse okResponse) {
    return CheckResponse.newBuilder()
            .setOkResponse(okResponse)
            .setStatus(com.google.rpc.Status.newBuilder().setCode(0).setMessage("OK").build())
            .build();
  }

  private OkHttpResponse getOkResponse(HeaderValueOption... headers) {
    return OkHttpResponse.newBuilder()
            .addAllHeaders(Arrays.asList(headers))
            .build();
  }

  private boolean ifNull(boolean jwtToken, String Missing_JWT_token, StreamObserver<CheckResponse> responseObserver) {
    if (jwtToken) {
      DeniedHttpResponse deniedResponse = DeniedHttpResponse.newBuilder()
              .setBody(Missing_JWT_token)
              .build();

      CheckResponse response = getFailedCheckResponse(deniedResponse, 16, "Unauthorized");

      responseObserver.onNext(response);
      responseObserver.onCompleted();
      return true;
    }
    return false;
  }

  private CheckResponse getFailedCheckResponse(DeniedHttpResponse deniedResponse, int code, String message) {
    CheckResponse response = CheckResponse.newBuilder()
            .setDeniedResponse(deniedResponse)
            .setStatus(com.google.rpc.Status.newBuilder().setCode(code).setMessage(message).build())
            .build();
    return response;
  }

}
