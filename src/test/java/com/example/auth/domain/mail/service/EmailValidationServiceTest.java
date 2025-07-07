package com.example.auth.domain.mail.service;

import com.example.auth.domain.mail.dto.response.EmailValidationResponse;
import com.example.auth.domain.mail.exception.NotFoundEmailValidationException;
import com.example.auth.domain.mail.mapper.EmailValidationMapper;
import com.example.auth.domain.mail.model.EmailValidation;
import com.example.auth.domain.mail.model.EmailValidationState;
import com.example.auth.domain.mail.repository.EmailValidationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailValidationServiceTest {

    @InjectMocks
    private EmailValidationService emailValidationService;

    @Mock
    private EmailValidationRepository emailValidationRepository;

    @Mock
    private EmailValidationMapper emailValidationMapper;

    @Test
    @DisplayName("이메일 검증 초기화 테스트")
    void when_init_email_verification_then_save_email_validation() {
        // Given
        String email = "test@example.com";
        EmailValidation emailValidation = new EmailValidation(email, EmailValidationState.PENDING);

        given(emailValidationMapper.createEmailValidation(email)).willReturn(emailValidation);

        // When
        emailValidationService.init(email);

        // Then
        then(emailValidationMapper).should().createEmailValidation(email);
        then(emailValidationRepository).should().save(emailValidation);
    }

    @Test
    @DisplayName("이메일 검증 성공 테스트")
    void when_verify_email_then_access_and_save() {
        // Given
        String email = "test@example.com";
        EmailValidation emailValidation = new EmailValidation(email, EmailValidationState.PENDING);

        given(emailValidationRepository.findById(email)).willReturn(Optional.of(emailValidation));

        // When
        emailValidationService.verifyEmail(email);

        // Then
        then(emailValidationRepository).should().findById(email);
        then(emailValidationRepository).should().save(emailValidation);
    }

    @Test
    @DisplayName("이메일 검증 실패 테스트 - 이메일 없음")
    void when_verify_email_with_invalid_email_then_throw_exception() {
        // Given
        String email = "invalid@example.com";
        given(emailValidationRepository.findById(email)).willReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundEmailValidationException.class, () -> emailValidationService.verifyEmail(email));
        then(emailValidationRepository).should().findById(email);
    }

    @Test
    @DisplayName("이메일 검증 상태 조회 성공 테스트")
    void when_get_email_validation_state_then_return_response() {
        // Given
        String email = "test@example.com";
        EmailValidation emailValidation = new EmailValidation(email, EmailValidationState.ACCESS);
        EmailValidationResponse expectedResponse = new EmailValidationResponse(EmailValidationState.ACCESS);

        given(emailValidationRepository.findById(email)).willReturn(Optional.of(emailValidation));
        given(emailValidationMapper.toEmailValidationResponse(emailValidation)).willReturn(expectedResponse);

        // When
        EmailValidationResponse result = emailValidationService.getEmailValidationState(email);

        // Then
        assertEquals(expectedResponse, result);
        then(emailValidationRepository).should().findById(email);
        then(emailValidationMapper).should().toEmailValidationResponse(emailValidation);
    }

    @Test
    @DisplayName("이메일 검증 상태 조회 실패 테스트 - 이메일 없음")
    void when_get_email_validation_state_with_invalid_email_then_throw_exception() {
        // Given
        String email = "invalid@example.com";
        given(emailValidationRepository.findById(email)).willReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundEmailValidationException.class, () -> emailValidationService.getEmailValidationState(email));
        then(emailValidationRepository).should().findById(email);
    }
}
