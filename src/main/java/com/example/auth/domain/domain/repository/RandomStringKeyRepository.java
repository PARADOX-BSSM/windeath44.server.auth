package com.example.auth.domain.domain.repository;

import com.example.auth.domain.domain.RandomStringKey;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStringKeyRepository extends CrudRepository<RandomStringKey, String> {
}
