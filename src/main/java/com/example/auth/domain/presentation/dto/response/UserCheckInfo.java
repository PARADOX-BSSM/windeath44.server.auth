package com.example.auth.domain.presentation.dto.response;

public record UserCheckInfo (
        String userId,
        String role
){
  public static UserCheckInfo create(String userId, String role) {
    return new UserCheckInfo(userId, role);
  }
}
