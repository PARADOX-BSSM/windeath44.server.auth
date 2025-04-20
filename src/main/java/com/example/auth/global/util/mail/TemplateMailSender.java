package com.example.auth.global.util.mail;

import com.example.auth.domain.exception.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;


@RequiredArgsConstructor
abstract class TemplateMailSender {
  private final SpringTemplateEngine templateEngine;

  protected final MailContextMaker mailContextMaker = new MailContextMaker();

  public void send(String email, String title, String fileName, JavaMailSender mailSender) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      settingMessage(mimeMessage, email, title, fileName);
      // custom logic
        doLogic(email); // abstract method
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      e.printStackTrace();
      throw new EmailSendFailedException("Email send failed");
    }
  }

  abstract void doLogic(String email);

  private void settingMessage(MimeMessage mimeMessage, String email, String title, String fileName) throws MessagingException, IOException {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject(title);

    settingContextProperties(email); // abstract method

    String html = mailContextMaker.getContext(fileName, templateEngine);
    mimeMessageHelper.setText(html, true);
    mimeMessageHelper.addInline("backgroundImage", new ClassPathResource("static/images/email-background.png").getFile());
    mimeMessageHelper.addInline("logo", new ClassPathResource("static/images/PARADOXLOGO.png").getFile());
    mimeMessageHelper.addInline("heart", new ClassPathResource("static/images/heart.png").getFile());
    mimeMessageHelper.addInline("buttons", new ClassPathResource("static/images/buttons.png").getFile());
  }
  abstract void settingContextProperties(String email);

  // settingContextProperties Example )
  // private void settingContextProperties() {
  //   mailContextMaker.addProperties(key1, value1)
  //              .addProperties(key2, value2);
  // }

}
