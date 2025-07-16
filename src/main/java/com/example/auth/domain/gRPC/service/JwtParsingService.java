package com.example.auth.domain.gRPC.service;

import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;
import com.example.auth.global.jwt.JwtProvider;
import io.envoyproxy.envoy.config.core.v3.HeaderValue;
import io.envoyproxy.envoy.config.core.v3.HeaderValueOption;
import io.envoyproxy.envoy.service.auth.v3.*;
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
    AttributeContext attributes = request.getAttributes();
    String jwtToken = null;
    try {
      jwtToken = jwtProvider.getJwt(attributes.getRequest().getHttp().getHeadersMap());
    } catch (Exception e) {
      e.printStackTrace();  // 로그만 찍고 무시
      // jwtToken 그대로 null
    }
    if (ifNull(jwtToken == null || jwtToken.isEmpty(), responseObserver)) return;
    // 유저 정보 추출
    UserCheckInfo user = jwtProvider.getUser(jwtToken);
    String userId = user.userId();
    String role = user.role();

    // userId가 Null
    if (ifNull(userId == null, responseObserver)) return;
    // 성공적으로 반환

    HeaderValueOption userIdHeader = getHeaderValueOption("user-id", userId);
    HeaderValueOption roleHeader = getHeaderValueOption("role", role);

    OkHttpResponse okResponse = getOkResponse(userIdHeader, roleHeader);

    CheckResponse response = getCheckResponse(okResponse);

    responseObserver.onNext(response);
    responseObserver.onCompleted();
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
            .setStatus(com.google.rpc.Status.newBuilder().setCode(0).build())
            .build();
  }

  private CheckResponse getDefaultResponse() {
    HeaderValueOption passHeader = getHeaderValueOption("pass", "true");
    OkHttpResponse okResponse = getOkResponse(passHeader);
    CheckResponse checkResponse = getCheckResponse(okResponse);
    return checkResponse;
  }

  private OkHttpResponse getOkResponse(HeaderValueOption... headers) {
    return OkHttpResponse.newBuilder()
            .addAllHeaders(Arrays.asList(headers))
            .build();
  }

  private boolean ifNull(boolean jwtToken, StreamObserver<CheckResponse> responseObserver) {
    if (jwtToken) {
      CheckResponse response = getDefaultResponse();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
      return true;
    }
    return false;
  }

}
