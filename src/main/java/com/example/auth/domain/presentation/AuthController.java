package com.example.auth.domain.presentation;

import com.example.auth.domain.presentation.dto.request.TokenRequest;
import com.example.auth.domain.presentation.dto.request.UserLoginRequest;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.presentation.tool.HttpHeaderMaker;
import com.example.auth.domain.service.AuthService;
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

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody @Valid UserLoginRequest request) {
    TokenResponse tokenResponse = authService.login(request);
    HttpHeaders httpHeaders = httpHeaderMaker.makeToken(tokenResponse);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .headers(httpHeaders)
            .build();
  }

  @PostMapping("/reissue")
  public ResponseEntity<Void> reissue(@RequestBody @Valid TokenRequest request) {
    TokenResponse tokenResponse = authService.reissue(request.refreshToken());
    HttpHeaders httpHeaders = httpHeaderMaker.makeToken(tokenResponse);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .headers(httpHeaders)
            .build();
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logout(@RequestBody @Valid TokenRequest request) {
    authService.logout(request.refreshToken());
  }





}
