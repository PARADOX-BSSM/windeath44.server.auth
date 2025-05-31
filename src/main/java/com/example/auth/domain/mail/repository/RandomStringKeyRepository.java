package com.example.auth.domain.mail.repository;

import com.example.auth.domain.mail.entity.RandomStringKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStringKeyRepository extends CrudRepository<RandomStringKey, String> {
}
