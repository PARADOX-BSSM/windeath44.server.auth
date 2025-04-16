package com.example.auth.domain.service.oauth;

import com.example.auth.global.config.properties.GoogleOauthProperties;
import com.example.auth.global.fegin.GoogleOauthFeign;
import com.example.auth.global.fegin.GoogleUserInfoFeign;
import com.example.auth.global.fegin.dto.GoogleTokenResponse;
import com.example.auth.global.fegin.dto.OAuthUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoogleAuthService implements OAuthService {
  private final GoogleOauthProperties googleOauthProperties;
  private final GoogleOauthFeign googleOauthFeign;
  private final GoogleUserInfoFeign googleUserInfoFeign;

  private final String GOOGLE = "google";
  private final String BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";

  @Override
  public String getLoginUrl() {
    String clientId = googleOauthProperties.getClient_id();
    String redirectUrl = googleOauthProperties.getRedirect_url();
    return UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUrl)
            .queryParam("response_type", "code")
            .queryParam("scope", "email profile")
            .queryParam("access_type", "offline")
            .queryParam("prompt", "consent")
            .build()
            .toUriString();
  }

  @Override
  public OAuthUserResponse getUserInfo(String code) {
    String clientId = googleOauthProperties.getClient_id();
    String redirectUrl = googleOauthProperties.getRedirect_url();
    String clientPw = googleOauthProperties.getClient_pw();

    GoogleTokenResponse googleTokenResponse = googleOauthFeign.getToken(code, clientId, clientPw, redirectUrl, "authorization_code");
    String token = "Bearer " + googleTokenResponse.access_token();
    OAuthUserResponse googleUserInfo = googleUserInfoFeign.getUserInfo(token);
    return googleUserInfo;
  }

  @Override
  public boolean equalsToProvider(String name) {
    return this.GOOGLE.equals(name);
  }

}
