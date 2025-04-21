package com.example.auth.global.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

class MailContextMaker {
  private final Map<String, Object> properties = new HashMap<>();


  public MailContextMaker addProperties(String key, Object value) {
    properties.put(key, value);
    return this;
  }

  public String getContext(String fileName, TemplateEngine templateEngine) {
    Context context = new Context();

    for(Map.Entry<String, Object> prop : properties.entrySet()) {
      String key = prop.getKey();
      Object value = prop.getValue();
      context.setVariable(key, value);
    }

    return templateEngine.process(fileName, context);
  }
}
