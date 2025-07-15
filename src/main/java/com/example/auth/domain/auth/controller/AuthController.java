package com.example.auth.domain.auth.controller;

import com.example.auth.domain.auth.dto.request.UserLoginRequest;
import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.global.dto.ResponseDto;
import com.example.auth.global.util.HttpUtil;
import com.example.auth.domain.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final HttpUtil httpUtil;

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid UserLoginRequest request) {
    TokenResponse tokenResponse = authService.login(request);
    HttpHeaders httpHeaders = httpUtil.makeToken(tokenResponse);
    ResponseDto<Void> responseDto = HttpUtil.success("login");
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .headers(httpHeaders)
            .body(responseDto);
  }

  @PostMapping("/reissue")
  public ResponseEntity<ResponseDto<Void>> reissue(@CookieValue("refreshToken") Cookie refreshTokenCookie) {
    String refreshToken = refreshTokenCookie.getValue();
    TokenResponse tokenResponse = authService.reissue(refreshToken);
    HttpHeaders httpHeaders = httpUtil.makeToken(tokenResponse);
    ResponseDto<Void> responseDto = HttpUtil.success("access token reissue");
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .headers(httpHeaders)
            .body(responseDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseDto<Void>> logout(@CookieValue("refreshToken") Cookie refreshTokenCookie) {
    String refreshToken = refreshTokenCookie.getValue();
    authService.logout(refreshToken);
    ResponseDto<Void> responseDto = HttpUtil.success("logout");
    return ResponseEntity.ok(responseDto);
  }
}
