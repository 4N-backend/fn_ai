package com.fn.ai.user.application.service;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.user.presentation.dto.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

  UserSignUpResponseDto signup(UserSignUpRequestDto requestDto);

  UserSignInResponseDto getUserByUsername(String username);

  Page<UserInfoResponseDto> getAllUsers(int page, int size, String sortBy, boolean isAsc);

  UserInfoResponseDto updateUser(UUID userId, UserUpdateRequestDto requestDto);

  UserInfoResponseDto getOneUserInfo(UUID userId);

  MasterUserInfoResponseDto getMasterUserInfo(UUID userId);

  UserDeleteResponseDto deleteUser(UUID userId, UserContext userInfo);


}
