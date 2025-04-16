package com.example.auth.global.fegin.dto;

public record GoogleTokenResponse (
        String access_token,
        String expires_in,
        String scope,
        String token_type
)
{

}
