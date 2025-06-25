package com.example.auth.domain.mail.sender;

import com.example.auth.domain.mail.exception.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;


abstract class TemplateMailSender {
  private final SpringTemplateEngine templateEngine;

  protected final MailContextMaker mailContextMaker = new MailContextMaker();

  public TemplateMailSender(SpringTemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  public void send(MailMetadatas metadata, JavaMailSender mailSender) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      settingMessage(mimeMessage, metadata);
      // custom logic
      doLogic(metadata); // abstract method
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      throw EmailSendFailedException.getInstance();
    }
  }

  abstract void doLogic(MailMetadatas metadatas);

  private void settingMessage(MimeMessage mimeMessage, MailMetadatas metadata) throws MessagingException, IOException {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    String email = metadata.getData("email");
    String title = metadata.getData("title");
    String fileName = metadata.getData("fileName");

    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject(title);

    settingContextProperties(metadata); // abstract method

    String html = mailContextMaker.getContext(fileName, templateEngine);
    mimeMessageHelper.setText(html, true);
    mimeMessageHelper.addInline("backgroundImage", new ClassPathResource("static/images/email-background.png").getFile());
    mimeMessageHelper.addInline("logo", new ClassPathResource("static/images/PARADOXLOGO.png").getFile());
    mimeMessageHelper.addInline("heart", new ClassPathResource("static/images/heart.png").getFile());
    mimeMessageHelper.addInline("buttons", new ClassPathResource("static/images/buttons.png").getFile());
  }
  abstract void settingContextProperties(MailMetadatas metadata);

  // settingContextProperties Example )
  // private void settingContextProperties() {
  //   mailContextMaker.addProperties(key1, value1)
  //              .addProperties(key2, value2);
  // }

}