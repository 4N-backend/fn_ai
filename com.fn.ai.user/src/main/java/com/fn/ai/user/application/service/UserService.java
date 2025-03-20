package com.fn.ai.user.application.service;

import com.fn.ai.user.presentation.dto.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

  UserSignUpResponseDto signup(UserSignUpRequestDto requestDto);

  UserSignInResponseDto getUserByUsername(String username);

  Page<UserInfoResponseDto> getAllUsers(int page, int size, String sortBy, boolean isAsc);

  UserInfoResponseDto updateUser(UUID userId, UserUpdateRequestDto requestDto);
}
