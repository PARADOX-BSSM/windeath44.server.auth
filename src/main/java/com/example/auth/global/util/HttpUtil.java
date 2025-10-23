package com.example.auth.global.util;

import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.global.dto.ResponseDto;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

public class HttpUtil {

  public static HttpHeaders makeToken(TokenResponse tokenResponse) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("authorization", tokenResponse.authorization());
    httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("refreshToken", tokenResponse.refreshToken(), 86000).toString());
    return httpHeaders;
  }

  public static HttpHeaders logoutCookie() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("refreshToken", "", 0).toString());
    return httpHeaders;
  }

  private static ResponseCookie createCookie(String key, String value, int age) {
    return ResponseCookie.from(key, value)
            .httpOnly(true)
            .maxAge(age)
            .path("/")
            .domain("windeath44.wiki")
            .sameSite("None")
            .secure(true)
            .build();
  }

  public static <T> ResponseDto<T> success(String message, T data) {
    return new ResponseDto<>(message, data);
  }

  public static <T> ResponseDto<T> success(String message) {
    return new ResponseDto<>(message, null);
  }

}
