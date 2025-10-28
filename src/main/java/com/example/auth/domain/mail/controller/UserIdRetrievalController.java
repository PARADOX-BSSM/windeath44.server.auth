package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.UserIdRetrievalCodeRequest;
import com.example.auth.domain.mail.dto.request.UserIdRetrievalRequest;
import com.example.auth.domain.mail.dto.response.UserIdRetrievalResponse;
import com.example.auth.domain.mail.facade.MailFacade;
import com.example.auth.domain.mail.service.UserIdRetrievalService;
import com.example.auth.global.dto.ResponseDto;
import com.example.auth.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user-id")
public class UserIdRetrievalController {
  private final MailFacade mailFacade;
  private final UserIdRetrievalService userIdRetrievalService;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendUserIdRetrievalCode(@RequestBody @Valid UserIdRetrievalRequest request) {
    mailFacade.sendUserIdRetrieval(request.email());
    ResponseDto<Void> responseDto = HttpUtil.success("send user id retrieval code");
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/retrieve")
  public ResponseEntity<ResponseDto<UserIdRetrievalResponse>> retrieveUserId(@RequestBody @Valid UserIdRetrievalCodeRequest request) {
    UserIdRetrievalResponse userIdRetrievalResponse = userIdRetrievalService.retrieveUserIdByCode(request.code());
    ResponseDto<UserIdRetrievalResponse> responseDto = HttpUtil.success("retrieve user id", userIdRetrievalResponse);
    return ResponseEntity.ok(responseDto);
  }
}
