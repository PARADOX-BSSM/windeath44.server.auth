package com.example.auth.domain.mail.dto.request;

public record ValidationEmailCodeRequest (
  String authorizationCode,
  String email
) {
}
