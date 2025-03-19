package com.fn.ai.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank(message = "Username은 필수입니다.")
        String username,

        @NotBlank(message = "Password는 필수입니다.")
        String password
) { }
