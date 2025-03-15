package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserSignUpRequestDto(String username,
                                   String password,
                                   UserRoleEnum role) {

}
