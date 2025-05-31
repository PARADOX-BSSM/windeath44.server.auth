package com.example.auth.domain.mail.repository;


import com.example.auth.domain.mail.entity.EmailValidation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailValidationRepository extends CrudRepository<EmailValidation, String> {
}
