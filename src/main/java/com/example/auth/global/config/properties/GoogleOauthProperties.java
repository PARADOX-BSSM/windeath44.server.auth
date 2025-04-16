package com.example.auth.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oauth.google")
@Getter
@Setter
public class GoogleOauthProperties {
  private String client_id;
  private String client_pw;
  private String redirect_url;
}

