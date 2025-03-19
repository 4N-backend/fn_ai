package com.fn.ai.user.presentation.dto;


import com.fn.ai.common.context.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserSignUpRequestDto(String username,
                                   String password,
                                   UserRoleEnum role) {

}
