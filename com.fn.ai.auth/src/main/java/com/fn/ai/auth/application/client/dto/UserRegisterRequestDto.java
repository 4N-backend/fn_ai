package com.fn.ai.auth.application.client.dto;

import com.fn.ai.auth.security.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserRegisterRequestDto(String username, String password, UserRoleEnum role) {

  public static UserRegisterRequestDto of(String username, String password, UserRoleEnum role) {
    return UserRegisterRequestDto.builder()
        .username(username)
        .password(password)
        .role(role)
        .build();
  }
}
