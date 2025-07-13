package com.example.auth.global.config;

import com.example.auth.global.config.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;


@Configuration
@RequiredArgsConstructor
public class MailConfig {
  private final MailProperties mailProperties;

  @Bean
  public JavaMailSender javaMailSender() {
    String host = mailProperties.getHost();
    int port = mailProperties.getPort();
    String username = mailProperties.getUsername();
    String password = mailProperties.getPassword();

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);
    mailSender.setDefaultEncoding("UTF-8");
    mailSender.setJavaMailProperties(getMailProperties());
    return mailSender;
  }


  private Properties getMailProperties() {
    MailProperties.Smtp smtp = mailProperties.getSmtp();

    boolean auth = smtp.isAuth();
    boolean starttlsEnable = smtp.getStarttls().isEnable();
    int timeout = smtp.getTimeout();

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", auth);
    properties.put("mail.smtp.starttls.enable", starttlsEnable);
    properties.put("mail.smtp.timeout", timeout);
    return properties;
  }
}