package com.example.auth.domain.domain.repository;

import com.example.auth.domain.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

}
