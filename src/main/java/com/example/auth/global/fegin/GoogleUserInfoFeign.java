package com.example.auth.global.fegin;

import com.example.auth.global.fegin.dto.OAuthUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="googleUserInfo", url="https://www.googleapis.com")
public interface GoogleUserInfoFeign {
  @PostMapping(value = "/oauth2/v3/userinfo", consumes = "application/x-www-form-urlencoded")
  OAuthUserResponse getUserInfo(@RequestHeader("Authorization") String authorization);
}
