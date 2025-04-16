package com.example.auth.domain.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class JwksController {

  private final KeyPair keyPair;

  public JwksController(KeyPair keyPair) {
    this.keyPair = keyPair;
  }

  @GetMapping("/.well-known/jwks.json")
  public Map<String, Object> getJwks() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

    Map<String, Object> jwk = new HashMap<>();
    jwk.put("kty", "RSA");
    jwk.put("alg", "RS256");
    jwk.put("use", "sig");
    jwk.put("kid", "my-key-id");  // 임의로 정한 키 ID
    jwk.put("n", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getModulus().toByteArray()));
    jwk.put("e", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getPublicExponent().toByteArray()));

    return Map.of("keys", List.of(jwk));
  }
}