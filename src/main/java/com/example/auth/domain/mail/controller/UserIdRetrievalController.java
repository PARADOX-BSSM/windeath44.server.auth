package com.example.auth.domain.mail.controller;

import com.example.auth.domain.mail.dto.request.UserIdRetrievalRequest;
import com.example.auth.domain.mail.facade.MailFacade;
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

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> sendUserIdRetrievalCode(@RequestBody @Valid UserIdRetrievalRequest request) {
    mailFacade.sendUserIdRetrieval(request.email());
    ResponseDto<Void> responseDto = HttpUtil.success("send user id retrieval code");
    return ResponseEntity.ok(responseDto);
  }
}
