package com.example.auth.global.util;

import com.example.auth.domain.auth.presentation.dto.response.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderMaker {

  public HttpHeaders makeToken(TokenResponse tokenResponse) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("accessToken", tokenResponse.accessToken());
    httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("refreshToken", tokenResponse.refreshToken()).toString());
    return httpHeaders;
  }

  private ResponseCookie createCookie(String key, String value) {
    return ResponseCookie.from(key, value)
            .httpOnly(true)
            .maxAge(86400)
            .build();
  }
}
