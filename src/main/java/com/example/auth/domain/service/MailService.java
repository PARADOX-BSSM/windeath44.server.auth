package com.example.auth.domain.service;

import com.example.auth.global.util.mail.EmailVerificationMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
  private final EmailVerificationMailSender emailVerificationMailSender;
  private final JavaMailSender javaMailSender;

  public void sendToAuthorization(String email) {
    String title = "최애의 사인 windeath44 email 인증 요청";
    String fileName = "authorizationEmail";
    emailVerificationMailSender.send(email, title, fileName, javaMailSender);
  }

}
