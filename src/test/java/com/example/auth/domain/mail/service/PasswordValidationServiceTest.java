package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.exception.NotFoundRandomStringKeyException;
import com.example.auth.domain.mail.mapper.RandomStringKeyMapper;
import com.example.auth.domain.mail.model.RandomStringKey;
import com.example.auth.domain.mail.repository.RandomStringKeyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class PasswordValidationServiceTest {

    @InjectMocks
    private PasswordValidationService passwordValidationService;

    @Mock
    private RandomStringKeyRepository randomStringKeyRepository;

    @Mock
    private RandomStringKeyMapper randomStringKeyMapper;

    @Test
    @DisplayName("랜덤 문자열 키 초기화 테스트")
    void when_init_random_string_key_then_save_successfully() {
        // Given
        String randomStringKey = "abc123";
        String email = "test@example.com";
        RandomStringKey randomStringKeyEntity = new RandomStringKey(randomStringKey, email);
        
        given(randomStringKeyMapper.createRandomStringKey(randomStringKey, email)).willReturn(randomStringKeyEntity);
        
        // When
        passwordValidationService.initRandomStringKey(randomStringKey, email);
        
        // Then
        then(randomStringKeyMapper).should().createRandomStringKey(randomStringKey, email);
        then(randomStringKeyRepository).should().save(randomStringKeyEntity);
    }

    @Test
    @DisplayName("코드 검증 성공 테스트")
    void when_verify_code_with_valid_code_then_delete_successfully() {
        // Given
        String code = "abc123";
        String email = "test@example.com";
        RandomStringKey randomStringKey = new RandomStringKey(code, email);
        
        given(randomStringKeyRepository.findById(code)).willReturn(Optional.of(randomStringKey));
        
        // When
        passwordValidationService.verifyCode(code);
        
        // Then
        then(randomStringKeyRepository).should().findById(code);
        then(randomStringKeyRepository).should().delete(randomStringKey);
    }

    @Test
    @DisplayName("코드 검증 실패 테스트 - 코드 없음")
    void when_verify_code_with_invalid_code_then_throw_exception() {
        // Given
        String code = "invalid123";
        given(randomStringKeyRepository.findById(code)).willReturn(Optional.empty());
        
        // When & Then
        assertThrows(NotFoundRandomStringKeyException.class, () -> passwordValidationService.verifyCode(code));
        then(randomStringKeyRepository).should().findById(code);
    }
}