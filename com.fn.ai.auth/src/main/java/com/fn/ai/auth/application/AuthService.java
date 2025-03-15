package com.fn.ai.auth.application;

import com.fn.ai.auth.application.client.UserClient;
import com.fn.ai.auth.application.client.dto.UserRegisterRequestDto;
import com.fn.ai.auth.presentation.dto.request.SignUpRequestDto;
import com.fn.ai.auth.presentation.dto.response.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserClient userClient;

  private final PasswordEncoder passwordEncoder;

  public SignUpResponseDto signup(SignUpRequestDto requestDto) {

    UserRegisterRequestDto userResponseDto = UserRegisterRequestDto.of(
        requestDto.username(),
        passwordEncoder.encode(requestDto.password()),
        requestDto.role());

    SignUpResponseDto responseDto = userClient.signup(userResponseDto).orElseThrow(
        () -> new IllegalArgumentException("Signup failed"));
    //후처리 로직(?)
    return responseDto;
  }
}
