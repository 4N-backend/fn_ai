package com.fn.ai.auth.application.client.dto;

import com.fn.ai.auth.security.UserRoleEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserRegisterRequestDto(UUID userId,
                                     String username,
                                     String password,
                                     UserRoleEnum role) {

}
