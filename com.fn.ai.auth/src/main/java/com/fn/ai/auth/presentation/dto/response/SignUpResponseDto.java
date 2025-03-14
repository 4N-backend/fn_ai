package com.fn.ai.auth.presentation.dto.response;

import com.fn.ai.auth.security.UserRoleEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SignUpResponseDto(UUID userId,
                                String username,
                                String password,
                                UserRoleEnum role) {
}
