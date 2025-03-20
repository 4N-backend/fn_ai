package com.fn.ai.user.presentation.dto;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.model.User;
import java.util.UUID;
import lombok.Builder;

@Builder
public record MasterUserInfoResponseDto(
        UUID userId,
        String username,
        UserRoleEnum role,
        String slackId,
        String createdBy,
        String updatedBy,
        String deletedBy) {

    public static MasterUserInfoResponseDto of(User user) {
        return MasterUserInfoResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername().getValue())
                .role(user.getRole())
                .slackId(user.getSlackId().getValue())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .deletedBy(user.getDeletedBy())
                .build();
    }
}
