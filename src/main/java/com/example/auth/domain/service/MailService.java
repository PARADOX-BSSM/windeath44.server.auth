package com.example.auth.domain.service;

import com.example.auth.domain.exception.FailedEmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final AuthService authService;

  public void sendToAuthorization(String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      String subject = "최애의 사인 windeath44 email 인증 요청";

      String key = authService.createRandomStringKey(5);
      settingMessage(email, mimeMessage, subject, "authorizationEmail", key);
      // Email 전송
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      throw new FailedEmailSendException("Failed send Email Exception");
    }
  }
  private void settingMessage(String email, MimeMessage mimeMessage, String subject, String fileName, String key) throws MessagingException {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(getAuthorizationHTML(fileName, key), true);
  }

  private String getAuthorizationHTML(String fileName, String key) {
    Context context = new Context();
    context.setVariable("key", key);
    return templateEngine.process(fileName, context);
  }
}
