package com.fn.ai.user.presentation.dto;

import com.fn.ai.common.context.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String slackId;
    private UserRoleEnum role;
}
