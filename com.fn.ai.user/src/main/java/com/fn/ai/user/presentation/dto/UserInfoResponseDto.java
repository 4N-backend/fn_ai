package com.fn.ai.user.presentation.dto;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.model.User;
import java.util.UUID;

public record UserInfoResponseDto(
        UUID id,
        String username,
        UserRoleEnum role,
        String slackId
) {
    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getUsername().getValue(),
                user.getRole(),
                user.getSlackId().getValue()
        );
    }
}
