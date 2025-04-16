package com.example.auth.domain.presentation;

import com.example.auth.domain.facade.OAuthFacade;
import com.example.auth.global.fegin.dto.OAuthUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {
  private final OAuthFacade oAuthFacade;
  @GetMapping("/login/{provider}")
  public ResponseEntity<String> getLoginGoogleUrl(@PathVariable("provider") String provider) {
    String url = oAuthFacade.getLoginUrl(provider);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/login/{provider}/code")
  public ResponseEntity<OAuthUserResponse> loginGoogle(@PathVariable("provider") String provider, @RequestParam("code") String code) {
    OAuthUserResponse userResponse = oAuthFacade.getUserInfo(code, provider);
     return ResponseEntity.ok(userResponse);
  }

}
