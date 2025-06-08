package com.example.auth.global.util;

import com.example.auth.domain.auth.dto.response.TokenResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class HttpUtil {


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

  public String parseCookie(String key, Cookie[] cookies) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(key)) {
        return cookie.getValue();
      }
    }
    return null;
  }
}
