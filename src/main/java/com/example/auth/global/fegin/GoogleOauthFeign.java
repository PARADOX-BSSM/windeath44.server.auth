package com.example.auth.global.fegin;

import com.example.auth.global.fegin.dto.GoogleTokenResponse;
import com.example.auth.global.fegin.dto.OAuthUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="googleOauth", url="https://oauth2.googleapis.com")
public interface GoogleOauthFeign {

  @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
  GoogleTokenResponse getToken(
          @RequestParam("code") String code,
          @RequestParam("client_id") String clientId,
          @RequestParam("client_secret") String clientSecret,
          @RequestParam("redirect_uri") String redirectUri,
          @RequestParam("grant_type") String grantType
  );
}
