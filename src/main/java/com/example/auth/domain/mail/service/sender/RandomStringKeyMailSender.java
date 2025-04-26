package com.example.auth.domain.mail.service.sender;

import com.example.auth.domain.mail.service.PasswordValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Component
public class RandomStringKeyMailSender extends TemplateMailSender {
  private final PasswordValidationService passwordValidationService;

  @Autowired
  public RandomStringKeyMailSender(SpringTemplateEngine templateEngine, PasswordValidationService passwordValidationService) {
    super(templateEngine);
    this.passwordValidationService = passwordValidationService;
  }

  @Override
  void doLogic(MailMetadatas metadatas) {
    String randomStringKey = metadatas.getData("randomStringKey");
    String email = metadatas.getData("email");
    passwordValidationService.initRandomStringKey(randomStringKey, email);
  }

  @Override
  void settingContextProperties(MailMetadatas metadata) {

    String email = metadata.getData("email");
    String randomStringKey = metadata.getData("randomStringKey");
    String name = metadata.getData("name");

    mailContextMaker.addProperties("email", email)
            .addProperties("randomStringKey", randomStringKey)
            .addProperties("name", name);

  }
}
