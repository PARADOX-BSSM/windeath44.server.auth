package com.example.auth.global.util.mail;

import com.example.auth.domain.service.EmailValidationService;
import com.example.auth.global.config.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class EmailVerificationMailSender extends TemplateMailSender {
  private final EmailValidationService emailValidationService;
  private final MailProperties mailProperties;

  @Autowired
  public EmailVerificationMailSender(SpringTemplateEngine templateEngine, EmailValidationService emailValidationService, MailProperties mailProperties) {
    super(templateEngine);
    this.emailValidationService = emailValidationService;
    this.mailProperties = mailProperties;
  }

  @Override
  void doLogic(String email) {
    emailValidationService.initEmailVerification(email);
  }

  @Override
  void settingContextProperties(String email) {
    String domain = mailProperties.getDomain();
    String name = email.split("@")[0];

    mailContextMaker.addProperties("email", email)
            .addProperties("domain", domain)
            .addProperties("name", name);

  }
}
