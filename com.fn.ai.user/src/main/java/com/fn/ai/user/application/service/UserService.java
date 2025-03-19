package com.fn.ai.user.application.service;

import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;

public interface UserService {
    UserSignUpResponseDto signup(UserSignUpRequestDto requestDto);
    UserSignInResponseDto getUserByUsername(String username);
}
