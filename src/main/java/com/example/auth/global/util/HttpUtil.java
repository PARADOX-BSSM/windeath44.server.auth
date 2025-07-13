package com.example.auth.global.util;

import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.global.dto.ResponseDto;
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

  public static <T> ResponseDto<T> success(String message, T data) {
    return new ResponseDto<>(message, data);
  }

  public static <T> ResponseDto<T> success(String message) {
    return new ResponseDto<>(message, null);
  }

}
