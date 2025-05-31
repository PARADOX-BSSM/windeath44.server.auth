package com.example.auth.domain.mail.presentation;

import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.presentation.dto.request.ValidationEmailRequest;
import com.example.auth.domain.mail.presentation.dto.response.EamilValidationResponse;
import com.example.auth.domain.mail.service.EmailValidationService;
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

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void sendEmailVerificationCode(@RequestBody @Valid ValidationEmailRequest request) {
    mailFacade.sendToAuthorizationForEmail(request.email());
  }

  @GetMapping("/{email}")
  @ResponseStatus(HttpStatus.OK)
  public void verifyEmailCode(@PathVariable String email) {
    emailValidationService.verifyEmail(email);
  }

  @GetMapping("/check/{email}")
  public ResponseEntity<EamilValidationResponse> checkEmailVerification(@PathVariable("email") String email) {
    EamilValidationResponse emailValidationResponse = emailValidationService.getEmailValidationState(email);
    return ResponseEntity.ok(emailValidationResponse);
  }
}
