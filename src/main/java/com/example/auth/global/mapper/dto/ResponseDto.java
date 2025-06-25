package com.example.auth.global.mapper.dto;

public record ResponseDto<T> (
        String message,
        T data
) {
}
