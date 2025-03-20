package com.fn.ai.user.application.service;

import com.fn.ai.user.model.User;
import com.fn.ai.user.presentation.dto.UserInfoResponseDto;
import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
  UserSignUpResponseDto signup(UserSignUpRequestDto requestDto);
  UserSignInResponseDto getUserByUsername(String username);
  Page<UserInfoResponseDto> getAllUsers(int page, int size, String sortBy, boolean isAsc);
}
