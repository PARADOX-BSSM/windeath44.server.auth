package com.example.auth.global.mapper;

import com.example.auth.global.mapper.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ResponseDtoMapper {

  public <T> ResponseDto<T> toResponseDto(String message, T data) {
    return new ResponseDto<>(message, data);
  }

}
