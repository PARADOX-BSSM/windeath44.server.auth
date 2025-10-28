package com.example.auth.domain.mail.dto.response;

public record UserIdRetrievalResponse(
        String userId
) {
    public static UserIdRetrievalResponse of(String userId) {
        return new UserIdRetrievalResponse(userId);
    }
}
