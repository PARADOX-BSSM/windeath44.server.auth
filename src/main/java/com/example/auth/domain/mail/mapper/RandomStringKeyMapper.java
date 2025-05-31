package com.example.auth.domain.mail.mapper;

import com.example.auth.domain.mail.entity.RandomStringKey;
import org.springframework.stereotype.Component;

@Component
public class RandomStringKeyMapper {

  public RandomStringKey createRandomStringKey(String randomStringKey, String email) {
    return new RandomStringKey(randomStringKey, email);
  }

}
