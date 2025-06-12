package com.example.auth.domain.mail.facade;

import com.example.auth.domain.mail.model.RandomStringKey;
import com.example.auth.global.config.properties.MailProperties;
import com.example.auth.domain.mail.sender.EmailVerificationMailSender;
import com.example.auth.domain.mail.sender.MailMetadatas;
import com.example.auth.domain.mail.sender.RandomStringKeyMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailFacade {
  private final EmailVerificationMailSender emailVerificationMailSender;
  private final RandomStringKeyMailSender randomStringKeyMailSender;

  private final JavaMailSender javaMailSender;
  private final MailProperties mailProperties;

  public void sendToAuthorizationForEmail(String email) {
    String title = "최애의 사인 windeath44 email 인증 요청";
    String fileName = "authorizationEmail";
    String domain = mailProperties.getDomain();

    MailMetadatas mailMetadatas = new MailMetadatas();

    mailMetadatas
            .addData("email", email)
            .addData("title", title)
            .addData("fileName", fileName)
            .addData("domain", domain);

    emailVerificationMailSender.send(mailMetadatas, javaMailSender);
  }

  public void sendToAuthorizationForPassword(String userId, String email) {
    String title = "최애의 사인 windeath44 password 인증코드 발급";
    String fileName = "authorizationPassword";
    String randomStringKey = RandomStringKey.makeKey(5);
    MailMetadatas mailMetadatas = new MailMetadatas();
    mailMetadatas
            .addData("name", userId)
            .addData("email", email)
            .addData("title", title)
            .addData("fileName", fileName)
            .addData("randomStringKey", randomStringKey);

    randomStringKeyMailSender.send(mailMetadatas,  javaMailSender);
  }

}
