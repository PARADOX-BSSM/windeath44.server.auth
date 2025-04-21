package com.example.auth.domain.mail.presentation;

import com.example.auth.domain.mail.presentation.dto.request.ValidationEmailRequest;
import com.example.auth.domain.mail.service.EmailValidationService;
import com.example.auth.domain.mail.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valid/email")
public class EmailValidationController {
  private final MailService mailService;
  private final EmailValidationService emailValidationService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void sendEmailVerificationCode(@RequestBody @Valid ValidationEmailRequest request) {
    mailService.sendToAuthorizationForEmail(request.email());
  }

  @GetMapping("/{email}")
  @ResponseStatus(HttpStatus.OK)
  public void verifyEmailCode(@PathVariable String email) {
    emailValidationService.verifyEmail(email);
  }
}
