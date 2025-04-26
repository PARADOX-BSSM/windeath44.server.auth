package com.example.auth.domain.mail.service.sender;


import java.util.HashMap;
import java.util.Map;

public class MailMetadatas {
  private Map<String, String> metaDatas = new HashMap<>();

  public MailMetadatas addData(String key,String value) {
    metaDatas.put(key, value);
    return this;
  }

  public String getData(String key) {
    String value = metaDatas.get(key);
    return value;
  }
}
