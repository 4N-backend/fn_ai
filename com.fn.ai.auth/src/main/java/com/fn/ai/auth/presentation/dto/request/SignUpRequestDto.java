package com.fn.ai.auth.presentation.dto.request;

import com.fn.ai.auth.security.UserRoleEnum;


public record SignUpRequestDto(String username,
                               String password,
                               UserRoleEnum role) {
}
