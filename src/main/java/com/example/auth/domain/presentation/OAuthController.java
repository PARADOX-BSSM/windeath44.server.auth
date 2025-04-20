package com.example.auth.domain.presentation;

import com.example.auth.domain.facade.OAuthFacade;
import com.example.auth.domain.presentation.dto.response.TokenResponse;
import com.example.auth.domain.presentation.tool.HttpHeaderMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class OAuthController {
  private final OAuthFacade oAuthFacade;
  private final HttpHeaderMaker httpHeaderMaker;

  @GetMapping("/{provider}")
  public ResponseEntity<String> getLoginGoogleUrl(@PathVariable("provider") String provider) {
    String url = oAuthFacade.getLoginUrl(provider);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/{provider}/code")
  public ResponseEntity<TokenResponse> loginGoogle(@PathVariable("provider") String provider, @RequestParam("code") String code) {
    TokenResponse token = oAuthFacade.login(code, provider);
    HttpHeaders httpHeaders = httpHeaderMaker.makeToken(token);
     return ResponseEntity.status(HttpStatus.NO_CONTENT)
             .headers(httpHeaders)
             .build();
  }

}
