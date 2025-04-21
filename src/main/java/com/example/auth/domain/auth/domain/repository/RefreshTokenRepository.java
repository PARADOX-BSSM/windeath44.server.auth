package com.example.auth.domain.auth.domain.repository;

import com.example.auth.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

}
