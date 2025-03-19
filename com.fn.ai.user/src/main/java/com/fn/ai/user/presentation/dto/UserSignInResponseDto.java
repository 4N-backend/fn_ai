package com.fn.ai.user.presentation.dto;

import com.fn.ai.common.context.UserRoleEnum;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSignInResponseDto(UUID id,
                                    String username,
                                    String password,
                                    UserRoleEnum role) {

}

