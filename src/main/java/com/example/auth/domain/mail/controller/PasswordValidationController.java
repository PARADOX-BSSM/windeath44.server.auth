package com.example.auth.domain.mail.presentation;

import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.presentation.dto.request.PaasowordValidationRequest;
import com.example.auth.domain.mail.presentation.dto.request.PasswordValidationCodeRequest;
import com.example.auth.domain.mail.service.PasswordValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordValidationController {
  private final MailFacade mailFacade;
  private final PasswordValidationService passwordValidationService;

  @PostMapping
  public void sendVerificationCode(@RequestBody @Valid PaasowordValidationRequest paasowordValidationRequest) {
    String userId = paasowordValidationRequest.userId();
    String email = paasowordValidationRequest.email();
    mailFacade.sendToAuthorizationForPassword(userId, email);
  }

  @PostMapping("/valid")
  @ResponseStatus(HttpStatus.OK)
  public void verifyCode(@RequestBody PasswordValidationCodeRequest request) {
    passwordValidationService.verifyCode(request.authorizationCode());
  }

}
