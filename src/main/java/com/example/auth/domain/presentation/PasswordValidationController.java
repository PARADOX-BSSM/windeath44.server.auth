package com.example.auth.domain.presentation;

import com.example.auth.domain.presentation.dto.request.PaasowordValidationRequest;
import com.example.auth.domain.service.MailService;
import com.example.auth.domain.service.PasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordValidationController {
  private final MailService mailService;

  @PostMapping
  public void sendVerificationCode(@RequestBody PaasowordValidationRequest paasowordValidationRequest) {
    String userId = paasowordValidationRequest.userId();
    String email = paasowordValidationRequest.email();
    mailService.sendToAuthorizationForPassword(userId, email);
  }

}
