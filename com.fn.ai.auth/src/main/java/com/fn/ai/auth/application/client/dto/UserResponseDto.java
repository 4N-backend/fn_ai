package com.fn.ai.auth.application.client.dto;

import com.fn.ai.auth.security.UserRoleEnum;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserResponseDto(UUID id, String username, String password, UserRoleEnum role) {

  public static UserResponseDto of(UUID id, String username, String password, UserRoleEnum role) {
    return new UserResponseDto(id, username, password, role);
  }
}
