package com.fn.ai.auth.presentation.dto.request;

import lombok.Builder;

@Builder
public record SignInRequestDto(String username, String password) {

}
