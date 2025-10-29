package com.example.auth.domain.mail.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class UserIdRetrievalMailSender extends TemplateMailSender {

  @Autowired
  public UserIdRetrievalMailSender(SpringTemplateEngine templateEngine) {
    super(templateEngine);
  }

  @Override
  void initializeVerification(MailMetadatas metadatas) {
    // No longer needed - userId is directly included in the email
  }

  @Override
  void settingContextProperties(MailMetadatas metadata) {
    String email = metadata.getData("email");
    String name = email.split("@")[0];
    String userId = metadata.getData("userId");

    mailContextMaker.addProperties("email", email)
            .addProperties("name", name)
            .addProperties("userId", userId);
  }
}