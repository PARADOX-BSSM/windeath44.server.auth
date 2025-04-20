package com.example.auth.domain.service.oauth;

import com.example.auth.global.fegin.dto.OAuthUserResponse;

public interface OAuthService {
  String getLoginUrl();
  OAuthUserResponse login(String code);
  boolean equalsToProvider(String name);
}
