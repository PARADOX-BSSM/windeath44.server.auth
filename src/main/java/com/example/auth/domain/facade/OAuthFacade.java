package com.example.auth.domain.facade;

import com.example.auth.domain.exception.NotFoundOAuthService;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.service.gRPC.GrpcClientService;
import com.example.auth.domain.service.oauth.OAuthService;
import com.example.auth.global.fegin.dto.OAuthUserResponse;
import com.example.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthFacade {
  private final List<OAuthService> oAuthServices;
  private final GrpcClientService grpcClientService;
  private final JwtProvider jwtProvider;

  private OAuthService getOAuthService(String provider) {
    return oAuthServices.stream()
            .filter(o -> o.equalsToProvider(provider))
            .findFirst()
            .orElseThrow(() -> new NotFoundOAuthService("Not Found OAuth service with provider"));
  }

  public String getLoginUrl(String provider) {
    OAuthService oAuthService = getOAuthService(provider);
    String url = oAuthService.getLoginUrl();
    return url;
  }

  public TokenResponse login(String code, String provider) {
    OAuthService oAuthService = getOAuthService(provider);
    OAuthUserResponse userResponse = oAuthService.login(code);
    String email = userResponse.email();
    String name = userResponse.name();

        String userKey = grpcClientService.registerUserFromOauth(email, name);

      TokenResponse token = jwtProvider.getTokenResponse(userKey);
    return token;
  }
}
