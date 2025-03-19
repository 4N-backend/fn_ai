package com.fn.ai.auth.presentation.dto.response;

import com.fn.ai.auth.security.UserRoleEnum;
import lombok.Builder;

@Builder
public record SignInResponseDto(String username, UserRoleEnum userRoleEnum) {
    public static SignInResponseDto of(String username, UserRoleEnum userRoleEnum) {
        return new SignInResponseDto(username, userRoleEnum);
    }
}
