package com.example.auth.domain.service;

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

  public void sendToAuthorization(String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      String subject = "최애의 사인 windeath44 email 인증 요청";
      String body = "최애의 사인 windeath44 email 인증 요청";
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      mimeMessageHelper.setTo(email);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(getAuthorizationHTML("authorizationEmail"), true);
//      settingMessage(email, mimeMessage, subject, "authorizationEmail");
      // Email 전송
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
//  private void settingMessage(String email, MimeMessage mimeMessage, String subject, String fileName) throws MessagingException {
//    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//    mimeMessageHelper.setTo(email);
//    mimeMessageHelper.setSubject(subject);
//    mimeMessageHelper.setText(getAuthorizationHTML(fileName), true);
//  }

  private String getAuthorizationHTML(String fileName) {
    Context context = new Context();
    return templateEngine.process(fileName, context);
  }
}
