package com.example.auth.domain.auth.repository;

import com.example.auth.domain.auth.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

}
