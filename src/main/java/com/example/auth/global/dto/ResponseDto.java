package com.example.auth.global.dto;

public record ResponseDto<T> (
        String message,
        T data
) {

}
