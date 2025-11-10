package com.example.auth.domain.auth.dto.response;



public record TokenResponse (
        String authorization,
        String refreshToken
) {
  public static TokenResponse create(String authorization, String refreshToken) {
    return new TokenResponse(authorization, refreshToken);
  }
}
