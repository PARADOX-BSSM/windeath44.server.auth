package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.ValidationCodeRequest;
import com.example.auth.domain.mail.dto.response.EmailValidationResponse;
import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.dto.request.ValidationEmailRequest;
import com.example.auth.domain.mail.service.EmailValidationService;
import com.example.auth.global.mapper.ResponseDtoMapper;
import com.example.auth.global.mapper.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valid/email")
public class EmailValidationController {
  private final MailFacade mailFacade;
  private final EmailValidationService emailValidationService;
  private final ResponseDtoMapper responseDtoMapper;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendEmailVerificationCode(@RequestBody @Valid ValidationEmailRequest request) {
    mailFacade.sendToAuthorizationForEmail(request.email());
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("send email verification code", null);
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/valid}")
  public ResponseEntity<ResponseDto<Void>> verifyEmailCode(@RequestBody @Valid ValidationCodeRequest validationCodeRequest) {
    emailValidationService.verifyEmail(validationCodeRequest.authorizationCode());
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("verify email code", null);
    return ResponseEntity.ok(responseDto);
  }
}
