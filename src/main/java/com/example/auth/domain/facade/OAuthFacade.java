package com.example.auth.domain.facade;

import com.example.auth.domain.exception.NotFoundOAuthService;
import com.example.auth.domain.service.oauth.OAuthService;
import com.example.auth.global.fegin.dto.OAuthUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthFacade {
  private final List<OAuthService> oAuthServices;

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

  public OAuthUserResponse getUserInfo(String code, String provider) {
    OAuthService oAuthService = getOAuthService(provider);
    OAuthUserResponse userResponse = oAuthService.getUserInfo(code);
    return userResponse;
  }
}
