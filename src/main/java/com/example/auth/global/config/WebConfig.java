package com.example.auth.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("http://10.129.59.65:5173", "https://10.129.59.65:5173" ,"http://localhost:5173")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("accessToken")
            .allowCredentials(true);
  }
}
