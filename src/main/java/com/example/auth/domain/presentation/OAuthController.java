package com.example.auth.domain.presentation;

import com.example.auth.domain.service.GoogleAuthService;
import com.example.auth.global.fegin.dto.GoogleUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {
  private final GoogleAuthService googleAuthService;
  @GetMapping("/login/google")
  public ResponseEntity<String> getLoginGoogleUrl() {
    return ResponseEntity.ok(googleAuthService.getLoginUrl());
  }

  @GetMapping("/login/google/code")
  public ResponseEntity<GoogleUserResponse> loginGoogle(@RequestParam("code") String code) {
    GoogleUserResponse googleUserResponse = googleAuthService.getUserInfo(code);
     return ResponseEntity.ok(googleUserResponse);
  }

}
