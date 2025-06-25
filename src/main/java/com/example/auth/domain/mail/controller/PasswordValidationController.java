package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.PasswordValidationRequest;
import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.dto.request.PasswordValidationCodeRequest;
import com.example.auth.domain.mail.service.PasswordValidationService;
import com.example.auth.global.mapper.ResponseDtoMapper;
import com.example.auth.global.mapper.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordValidationController {
  private final MailFacade mailFacade;
  private final PasswordValidationService passwordValidationService;
  private final ResponseDtoMapper responseDtoMapper;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendVerificationCode(@RequestBody @Valid PasswordValidationRequest paasowordValidationRequest) {
    String userId = paasowordValidationRequest.userId();
    String email = paasowordValidationRequest.email();
    mailFacade.sendToAuthorizationForPassword(userId, email);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("send verification code", null);
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/valid")
  public ResponseEntity<ResponseDto<Void>> verifyCode(@RequestBody PasswordValidationCodeRequest request) {
    passwordValidationService.verifyCode(request.authorizationCode());
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("success verify", null);
    return ResponseEntity.ok(responseDto);
  }

}
