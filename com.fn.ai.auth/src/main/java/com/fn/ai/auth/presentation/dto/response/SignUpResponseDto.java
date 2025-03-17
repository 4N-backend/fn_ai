package com.fn.ai.auth.presentation.dto.response;

import com.fn.ai.auth.security.UserRoleEnum;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SignUpResponseDto(UUID userId,
                                String username,
                                String password,
                                UserRoleEnum role) {

}
