package com.example.auth.domain.auth.controller;

import com.example.auth.domain.auth.dto.request.TokenRequest;
import com.example.auth.domain.auth.dto.request.UserLoginRequest;
import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.global.mapper.ResponseDtoMapper;
import com.example.auth.global.mapper.dto.ResponseDto;
import com.example.auth.global.util.HttpHeaderMaker;
import com.example.auth.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final HttpHeaderMaker httpHeaderMaker;
  private final ResponseDtoMapper responseDtoMapper;

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid UserLoginRequest request) {
    TokenResponse tokenResponse = authService.login(request);
    HttpHeaders httpHeaders = httpHeaderMaker.makeToken(tokenResponse);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("login", null);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .headers(httpHeaders)
            .body(responseDto);
  }

  @PostMapping("/reissue")
  public ResponseEntity<ResponseDto<Void>> reissue(@RequestBody @Valid TokenRequest request) {
    TokenResponse tokenResponse = authService.reissue(request.refreshToken());
    HttpHeaders httpHeaders = httpHeaderMaker.makeToken(tokenResponse);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("access token reissue", null);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .headers(httpHeaders)
            .body(responseDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseDto<Void>> logout(@RequestBody @Valid TokenRequest request) {
    authService.logout(request.refreshToken());
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("logout", null);
    return ResponseEntity.ok(responseDto);
  }





}
