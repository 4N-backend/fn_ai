package com.fn.ai.user.application.service;

import com.fn.ai.common.exception.BaseException;
import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.model.User;
import com.fn.ai.user.model.repository.UserRespository;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    @Override
    public UserSignUpResponseDto signup(UserSignUpRequestDto requestDto) {
        userRespository.findByUsername_Value(requestDto.username())
                .ifPresent(user -> {
                    throw new BaseException(HttpStatus.BAD_REQUEST, "중복된 username이 존재합니다.");
                });

        User newUser = User.of(requestDto);
        User savedUser = userRespository.save(newUser);
        return UserSignUpResponseDto.of(savedUser);
    }

    @Override
    public UserSignInResponseDto getUserByUsername(String username) {
        User user = userRespository.findByUsername_Value(username)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "유저정보 없음"));
        return new UserSignInResponseDto(
                user.getId(),
                user.getUsername().getValue(),
                user.getPassword().getValue(),
                user.getRole(),
                user.getSlackId().getValue()
        );
    }

}
