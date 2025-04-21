package com.example.auth.global.util.mail;

import com.example.auth.domain.mail.service.EmailValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Component
public class EmailVerificationMailSender extends TemplateMailSender {
  private final EmailValidationService emailValidationService;

  @Autowired
  public EmailVerificationMailSender(SpringTemplateEngine templateEngine, EmailValidationService emailValidationService) {
    super(templateEngine);
    this.emailValidationService = emailValidationService;
  }

  @Override
  void doLogic(MailMetadatas metadatas) {
    String email = metadatas.getData("email");
    emailValidationService.initEmailVerification(email);
  }

  @Override
  void settingContextProperties(MailMetadatas metadata) {
    String email = metadata.getData("email");
    String domain = metadata.getData("domain");
    String name = email.split("@")[0];

    mailContextMaker.addProperties("email", email)
            .addProperties("domain", domain)
            .addProperties("name", name);
  }
}
