package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.enums.UserRoleEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserSignInResponseDto(UUID id,
                                    String username,
                                    String password,
                                    UserRoleEnum role,
                                    String slackId) {
}
