package com.example.auth.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Configuration
public class RsaKeyConfig {

  @Value("${jwt.private-key:}")
  private String privateKeyString;
  
  @Value("${jwt.public-key:}")
  private String publicKeyString;

  @Bean
  public KeyPair keyPair() {
    try {
      if (!privateKeyString.isEmpty() && !publicKeyString.isEmpty()) {
        log.info("Loading RSA key pair from environment variables");
        return createKeyPairFromEnv();
      } else {
        log.warn("RSA keys not found in environment, generating new key pair");
        return generateNewKeyPair();
      }
    } catch (Exception e) {
      log.error("Failed to initialize RSA key pair", e);
      throw new RuntimeException("Failed to initialize RSA key pair", e);
    }
  }
  
  private KeyPair createKeyPairFromEnv() throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString.trim());
    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString.trim());
    
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    
    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    
    return new KeyPair(publicKey, privateKey);
  }
  
  private KeyPair generateNewKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    return generator.generateKeyPair();
  }
}