package com.example.auth.domain.auth.service;

import com.example.auth.domain.auth.dto.request.UserLoginRequest;
import com.example.auth.domain.auth.dto.response.TokenResponse;
import com.example.auth.domain.auth.exception.NotFoundRefreshTokenException;
import com.example.auth.domain.auth.model.RefreshToken;
import com.example.auth.domain.auth.repository.RefreshTokenRepository;
import com.example.auth.domain.gRPC.dto.response.UserCheckInfo;
import com.example.auth.domain.gRPC.service.GrpcClientService;
import com.example.auth.global.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private GrpcClientService grpcClientService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("로그인 성공 테스트")
    void when_login_with_valid_credentials_then_return_token_response() {
        // Given
        String userId = "testUser";
        String password = "password";
        String role = "USER";
        UserLoginRequest request = new UserLoginRequest(userId, password);
        UserCheckInfo userCheckInfo = new UserCheckInfo(userId, role);
        TokenResponse expectedResponse = new TokenResponse("accessToken", "refreshToken");

        given(grpcClientService.checkUser(userId, password)).willReturn(userCheckInfo);
        given(jwtProvider.getTokenResponse(userId, role)).willReturn(expectedResponse);

        // When
        TokenResponse result = authService.login(request);

        // Then
        assertEquals(expectedResponse, result);
        then(grpcClientService).should().checkUser(userId, password);
        then(jwtProvider).should().getTokenResponse(userId, role);
    }

    @Test
    @DisplayName("토큰 재발급 성공 테스트")
    void when_reissue_with_valid_refresh_token_then_return_new_token_response() {
        // Given
        String refreshToken = "validRefreshToken";
        String userId = "testUser";
        String role = "USER";
        RefreshToken token = RefreshToken.create(refreshToken, userId, role);
        TokenResponse expectedResponse = new TokenResponse("newAccessToken", "newRefreshToken");

        given(refreshTokenRepository.findById(refreshToken)).willReturn(Optional.of(token));
        given(jwtProvider.getTokenResponse(userId, role)).willReturn(expectedResponse);

        // When
        TokenResponse result = authService.reissue(refreshToken);

        // Then
        assertEquals(expectedResponse, result);
        then(refreshTokenRepository).should().findById(refreshToken);
        then(refreshTokenRepository).should().delete(token);
        then(jwtProvider).should().getTokenResponse(userId, role);
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트 - 리프레시 토큰 없음")
    void when_reissue_with_invalid_refresh_token_then_throw_exception() {
        // Given
        String refreshToken = "invalidRefreshToken";
        given(refreshTokenRepository.findById(refreshToken)).willReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundRefreshTokenException.class, () -> authService.reissue(refreshToken));
        then(refreshTokenRepository).should().findById(refreshToken);
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void when_logout_with_valid_refresh_token_then_delete_token() {
        // Given
        String refreshToken = "validRefreshToken";
        RefreshToken token = RefreshToken.create(refreshToken, "testUser", "USER");
        given(refreshTokenRepository.findById(refreshToken)).willReturn(Optional.of(token));

        // When
        authService.logout(refreshToken);

        // Then
        then(refreshTokenRepository).should().findById(refreshToken);
        then(refreshTokenRepository).should().delete(token);
    }

    @Test
    @DisplayName("로그아웃 실패 테스트 - 리프레시 토큰 없음")
    void when_logout_with_invalid_refresh_token_then_throw_exception() {
        // Given
        String refreshToken = "invalidRefreshToken";
        given(refreshTokenRepository.findById(refreshToken)).willReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundRefreshTokenException.class, () -> authService.logout(refreshToken));
        then(refreshTokenRepository).should().findById(refreshToken);
    }
}