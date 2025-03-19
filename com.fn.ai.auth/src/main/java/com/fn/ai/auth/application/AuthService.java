package com.fn.ai.auth.application;

import com.fn.ai.auth.application.client.UserClient;
import com.fn.ai.auth.application.client.dto.UserRegisterRequestDto;
import com.fn.ai.auth.application.client.dto.UserResponseDto;
import com.fn.ai.auth.presentation.dto.request.SignInRequestDto;
import com.fn.ai.auth.presentation.dto.request.SignUpRequestDto;
import com.fn.ai.auth.presentation.dto.response.SignInResponseDto;
import com.fn.ai.auth.presentation.dto.response.SignUpResponseDto;
import com.fn.ai.auth.security.jwt.JwtUtil;
import com.fn.ai.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserClient userClient;

  private final PasswordEncoder passwordEncoder;

  private final JwtUtil jwtUtil;

  public SignUpResponseDto signup(SignUpRequestDto requestDto) {

    UserRegisterRequestDto userResponseDto = UserRegisterRequestDto.of(
        requestDto.username(),
        passwordEncoder.encode(requestDto.password()),
        requestDto.role(),
        requestDto.slackId());

    SignUpResponseDto responseDto = userClient.signup(userResponseDto).orElseThrow(
        () -> new IllegalArgumentException("Signup failed"));
    //후처리 로직(?)
    return responseDto;
  }

  public SignInResponseDto signin(SignInRequestDto signInRequestDto) {
    // 사용자 정보 조회
    UserResponseDto userResponse = userClient.getUserByUsername(signInRequestDto.username())
            .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "유저정보 없음"));

    // 비밀번호 검증
    if (!passwordEncoder.matches(signInRequestDto.password(), userResponse.password())) {
      throw new BaseException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    String token = jwtUtil.createToken(userResponse.id(), userResponse.username(), userResponse.role());


    return SignInResponseDto.of(userResponse.username(), userResponse.role());
  }
}
