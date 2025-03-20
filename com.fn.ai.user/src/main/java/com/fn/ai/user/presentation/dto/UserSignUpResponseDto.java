package com.fn.ai.user.presentation.dto;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.model.User;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSignUpResponseDto(UUID userId,
                                    String username,
                                    String password,
                                    UserRoleEnum role,
                                    String slackId) {

  public static UserSignUpResponseDto of(User user) {
    return UserSignUpResponseDto.builder()
            .userId(user.getId())
            .username(user.getUsername().getValue())
            .password(user.getPassword().getValue())
            .role(user.getRole())
            .slackId(user.getSlackId().getValue())
            .build();
  }
}
