package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.enums.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserSignUpRequestDto(String username,
                                   String password,
                                   UserRoleEnum role, String slackId) {
}
