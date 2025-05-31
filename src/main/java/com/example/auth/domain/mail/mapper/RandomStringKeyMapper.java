package com.example.auth.domain.mail.domain.mapper;

import com.example.auth.domain.mail.domain.RandomStringKey;
import org.springframework.stereotype.Component;

@Component
public class RandomStringKeyMapper {

  public RandomStringKey createRandomStringKey(String randomStringKey, String email) {
    return new RandomStringKey(randomStringKey, email);
  }

}
