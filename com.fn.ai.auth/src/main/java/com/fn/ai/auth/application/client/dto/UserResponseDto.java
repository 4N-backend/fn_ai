package com.fn.ai.auth.application.client.dto;

import com.fn.ai.auth.security.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PROTECTED)
public record UserResponseDto(UUID id, String username, String password, UserRoleEnum role) {
    public static UserResponseDto of(UUID id, String username, String password, UserRoleEnum role) {
        return new UserResponseDto(id, username, password, role);
    }
}
