package com.example.auth.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
  private Long refreshTime;
  private Long accessTime;
  private String prefix;
  private String header;
  private String secretKey;
}
