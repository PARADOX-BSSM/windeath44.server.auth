package com.example.auth.domain.mail.sender;

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
    String randomStringKey = metadatas.getData("randomStringKey");
    String email = metadatas.getData("email");
    emailValidationService.initEmailVerification(randomStringKey, email);
  }

  @Override
  void settingContextProperties(MailMetadatas metadata) {
    String email = metadata.getData("email");
    String domain = metadata.getData("domain");
    String name = email.split("@")[0];
    String randomStringKey = metadata.getData("randomStringKey");

    mailContextMaker.addProperties("email", email)
            .addProperties("domain", domain)
            .addProperties("name", name)
            .addProperties("randomStringKey", randomStringKey);
  }
}
