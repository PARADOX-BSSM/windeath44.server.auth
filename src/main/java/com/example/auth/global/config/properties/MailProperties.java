package com.example.auth.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("mail")
public class MailProperties {
  private String host;
  private Integer port;
  private String username;
  private String password;
  private Smtp smtp;

  @Getter
  @Setter
  public static class Smtp {
    private boolean auth;
    private int timeout;
    private Starttls starttls;

    @Getter
    @Setter
    public static class Starttls {
      private boolean enable;
    }
  }

}
