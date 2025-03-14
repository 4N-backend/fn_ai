package com.fn.ai.user.application;

import com.fn.ai.user.User;
import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.model.UserRespository;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRespository userRespository;

    public UserSignUpResponseDto signup(UserSignUpRequestDto requestDto) {
        //회원가입 검증로직 필요
        return UserSignUpResponseDto.of(userRespository.save(User.of(requestDto)));
    }

    public UserSignInResponseDto getUserByUsername(String username) {
        User user = userRespository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저정보 없음"));

        return UserSignInResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
