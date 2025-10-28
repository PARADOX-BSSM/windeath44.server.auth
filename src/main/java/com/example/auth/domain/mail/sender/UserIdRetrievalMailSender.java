package com.example.auth.domain.mail.sender;

import com.example.auth.domain.mail.service.UserIdRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class UserIdRetrievalMailSender extends TemplateMailSender {
  private final UserIdRetrievalService userIdRetrievalService;

  @Autowired
  public UserIdRetrievalMailSender(SpringTemplateEngine templateEngine, UserIdRetrievalService userIdRetrievalService) {
    super(templateEngine);
    this.userIdRetrievalService = userIdRetrievalService;
  }

  @Override
  void initializeVerification(MailMetadatas metadatas) {
    String randomStringKey = metadatas.getData("randomStringKey");
    String email = metadatas.getData("email");
    userIdRetrievalService.init(randomStringKey, email);
  }

  @Override
  void settingContextProperties(MailMetadatas metadata) {
    String email = metadata.getData("email");
    String name = email.split("@")[0];
    String randomStringKey = metadata.getData("randomStringKey");

    mailContextMaker.addProperties("email", email)
            .addProperties("name", name)
            .addProperties("randomStringKey", randomStringKey);
  }
}