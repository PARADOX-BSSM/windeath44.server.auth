package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.PasswordValidationRequest;
import com.example.auth.domain.mail.dto.request.ValidationCodeRequest;
import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.service.PasswordValidationService;
import com.example.auth.global.dto.ResponseDto;
import com.example.auth.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/password")
public class PasswordValidationController {
  private final MailFacade mailFacade;
  private final PasswordValidationService passwordValidationService;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendVerificationCode(@RequestBody @Valid PasswordValidationRequest passwordValidationRequest) {
    String userId = passwordValidationRequest.userId();
    String email = passwordValidationRequest.email();
    mailFacade.sendToAuthorizationForPassword(userId, email);
    ResponseDto<Void> responseDto = HttpUtil.success("send verification code");
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/valid")
  public ResponseEntity<ResponseDto<Void>> verifyCode(@RequestBody @Valid ValidationCodeRequest request) {
    passwordValidationService.verifyCode(request.authorizationCode());
    ResponseDto<Void> responseDto = HttpUtil.success("success verify");
    return ResponseEntity.ok(responseDto);
  }

}
