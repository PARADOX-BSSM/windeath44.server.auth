package com.example.auth.domain.service;

import com.example.auth.domain.exception.EmailSendFailedException;
import com.example.auth.global.config.properties.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final EmailValidationService emailValidationService;
  private final MailProperties mailProperties;

  public void sendToAuthorization(String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      String subject = "최애의 사인 windeath44 email 인증 요청";
      settingMessage(email, mimeMessage, subject, "authorizationEmail", mailProperties.getDomain());
      emailValidationService.initEmailVerification(email);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      e.printStackTrace();
      throw new EmailSendFailedException("Email send failed");
    }
  }

  private void settingMessage(String email, MimeMessage mimeMessage, String subject, String fileName, String domain) throws MessagingException, IOException {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(getAuthorizationHTML(fileName, email, email.split("@")[0], domain), true);
    mimeMessageHelper.addInline("backgroundImage", new ClassPathResource("static/images/email-background.png").getFile());
    mimeMessageHelper.addInline("logo", new ClassPathResource("static/images/PARADOXLOGO.png").getFile());
    mimeMessageHelper.addInline("heart", new ClassPathResource("static/images/heart.png").getFile());
    mimeMessageHelper.addInline("buttons", new ClassPathResource("static/images/buttons.png").getFile());
  }

  private String getAuthorizationHTML(String fileName, String email, String name, String domain) {
    Context context = new Context();
    context.setVariable("email", email);
    context.setVariable("domain", domain);
    context.setVariable("name", name + "님");
    return templateEngine.process(fileName, context);
  }
}
