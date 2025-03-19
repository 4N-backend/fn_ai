package com.fn.ai.auth.presentation.dto.request;

import com.fn.ai.auth.security.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(
        @NotBlank(message = "Username은 필수입니다.")
        String username,

        @NotBlank(message = "Password는 필수입니다.")
        @Size(min = 8, max = 15, message = "Password는 8자 이상 15자 이하여야 합니다.")
        @Pattern(
                regexp = "^(?=.{8,15}$)[A-Za-z0-9!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?]+$",
                message = "Password는 알파벳 대소문자, 숫자, 특수문자로만 구성되어야 합니다."
        )
        String password,

        UserRoleEnum role,

        @NotBlank(message = "SlackId는 필수입니다.")
        String slackId
) {
}
