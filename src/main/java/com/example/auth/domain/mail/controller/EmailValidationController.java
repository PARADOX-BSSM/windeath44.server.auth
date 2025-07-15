package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.ValidationEmailCodeRequest;
import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.dto.request.ValidationEmailRequest;
import com.example.auth.domain.mail.service.EmailValidationService;
import com.example.auth.global.dto.ResponseDto;
import com.example.auth.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/email")
public class EmailValidationController {
  private final MailFacade mailFacade;
  private final EmailValidationService emailValidationService;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendEmailVerificationCode(@RequestBody @Valid ValidationEmailRequest request) {
    mailFacade.sendToAuthorizationForEmail(request.email());
    ResponseDto<Void> responseDto = HttpUtil.success("send email verification code");
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/valid")
  public ResponseEntity<ResponseDto<Void>> verifyEmailCode(@RequestBody @Valid ValidationEmailCodeRequest validationCodeRequest) {
    emailValidationService.verifyEmail(validationCodeRequest);
    ResponseDto<Void> responseDto = HttpUtil.success("verify email code");
    return ResponseEntity.ok(responseDto);
  }
}
