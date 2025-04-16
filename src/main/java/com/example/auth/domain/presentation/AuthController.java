package com.example.auth.domain.presentation;

import com.example.auth.domain.presentation.dto.request.TokenRequest;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.service.AuthService;
import com.example.auth.domain.service.GoogleAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<Void> loginCustom(@RequestBody @Valid UserLoginRequest request) {
    TokenResponse tokenResponse = authService.login(request);
    HttpHeaders httpHeaders = getHttpHeaders(tokenResponse);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .headers(httpHeaders)
            .build();
  }

  @PostMapping("/reissue")
  public ResponseEntity<Void> reissue(@RequestBody @Valid TokenRequest refreshToken) {
    TokenResponse tokenResponse = authService.reissue(refreshToken);
    HttpHeaders httpHeaders = getHttpHeaders(tokenResponse);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .headers(httpHeaders)
            .build();
  }

  private ResponseCookie createCookie(String key, String value) {
    return ResponseCookie.from(key, value)
            .httpOnly(true)
            .maxAge(86400)
            .build();
  }

  private HttpHeaders getHttpHeaders(TokenResponse tokenResponse) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("accessToken", tokenResponse.accessToken());
    httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("refreshToken", tokenResponse.refreshToken()).toString());
    return httpHeaders;
  }

}
