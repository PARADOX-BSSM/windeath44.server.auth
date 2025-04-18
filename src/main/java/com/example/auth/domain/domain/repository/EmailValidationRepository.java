package com.example.auth.domain.domain.repository;

import com.example.auth.domain.domain.EmailValidation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailValidationRepository extends CrudRepository<EmailValidation, String> {
}
