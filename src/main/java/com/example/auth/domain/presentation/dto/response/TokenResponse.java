package com.example.auth.domain.presentation.dto.response;



public record TokenResponse (
        String accessToken,
        String refreshToken
) {
  public static TokenResponse toTokenResponse(String accessToken, String refreshToken) {
    return new TokenResponse(accessToken, refreshToken);
  }
}
