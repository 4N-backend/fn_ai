package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.User;
import com.fn.ai.user.UserRoleEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserSignUpResponseDto(UUID userId,
                                    String username,
                                    String password,
                                    UserRoleEnum role) {
    public static UserSignUpResponseDto of(User user) {
        return UserSignUpResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
