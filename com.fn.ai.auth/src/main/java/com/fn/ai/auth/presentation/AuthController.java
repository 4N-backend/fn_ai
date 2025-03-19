package com.fn.ai.auth.presentation;

import com.fn.ai.auth.application.AuthService;
import com.fn.ai.auth.presentation.dto.request.SignInRequestDto;
import com.fn.ai.auth.presentation.dto.request.SignUpRequestDto;
import com.fn.ai.auth.presentation.dto.response.SignInResponseDto;
import com.fn.ai.auth.presentation.dto.response.SignUpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto requestDto) {
    return ResponseEntity.ok().body(authService.signup(requestDto));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponseDto> signin(@RequestBody @Valid SignInRequestDto signInRequestDto) {
    return ResponseEntity.ok().body(authService.signin(signInRequestDto));
  }
}
