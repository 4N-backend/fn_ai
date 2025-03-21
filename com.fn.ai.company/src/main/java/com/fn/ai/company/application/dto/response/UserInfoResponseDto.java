package com.fn.ai.company.application.dto.response;

import com.fn.ai.common.context.UserRoleEnum;
import java.util.UUID;

public record UserInfoResponseDto(
    UUID userId,
    String username,
    UserRoleEnum role,
    String slackId,
    String createdBy,
    String updatedBy,
    String deletedBy
) {

}
