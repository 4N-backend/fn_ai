package com.fn.ai.user.application.service;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import com.fn.ai.user.model.User;
import com.fn.ai.user.model.repository.UserRepository;
import com.fn.ai.user.model.vo.Password;
import com.fn.ai.user.model.vo.SlackId;
import com.fn.ai.user.model.vo.Username;
import com.fn.ai.user.presentation.dto.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserSignUpResponseDto signup(UserSignUpRequestDto requestDto) {
        // 회원가입 검증
        validateSignUp(requestDto);

        User user = User.of(requestDto);
        user = userRepository.save(user);
        return UserSignUpResponseDto.of(user);
    }

    @Override
    public UserSignInResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(new Username(username))
                .orElseThrow(() -> new IllegalArgumentException("유저정보 없음"));

        return UserSignInResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername().getValue())
                .password(user.getPassword().getValue())
                .role(user.getRole())
                .build();
    }

    @Override
    public Page<UserInfoResponseDto> getAllUsers(int page, int size, String sortBy, boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable).map(UserInfoResponseDto::of);
    }

    @Override
    public UserInfoResponseDto getOneUserInfo(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoResponseDto.of(user);
    }

    @Override
    public MasterUserInfoResponseDto getMasterUserInfo(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return MasterUserInfoResponseDto.of(user);
    }


    @Override
    @Transactional
    public UserInfoResponseDto updateUser(UUID userId, UserUpdateRequestDto requestDto) {
        // 기존 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (requestDto.getSlackId() != null) {
            user.updateSlackId(requestDto.getSlackId());
        }
        if (requestDto.getRole() != null) {
            user.updateRole(requestDto.getRole());
        }

        userRepository.save(user);

        return UserInfoResponseDto.of(user);
    }

    @Override
    @Transactional
    public UserDeleteResponseDto deleteUser(UUID userId, UserContext userInfo) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.delete();

        userRepository.save(user);

        return UserDeleteResponseDto.of(user);
    }

    private void validateSignUp(UserSignUpRequestDto requestDto) {
        // 필수 필드 체크
        if (requestDto.username() == null || requestDto.username().isBlank()) {
            throw new IllegalArgumentException("사용자명은 필수 입력값입니다.");
        }
        if (requestDto.password() == null || requestDto.password().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }
        if (requestDto.slackId() == null || requestDto.slackId().isBlank()) {
            throw new IllegalArgumentException("Slack ID는 필수 입력값입니다.");
        }

        // 사용자명 중복 체크
        if (userRepository.findByUsername(new Username(requestDto.username())).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        // 사용자명 형식 체크
        try {
            new Username(requestDto.username()); // Username 클래스에서 검증 수행
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("사용자명 형식이 올바르지 않습니다. (소문자+숫자, 4~10자)");
        }

        // 비밀번호 형식 체크
        try {
            new Password(requestDto.password()); // Password 클래스에서 검증 수행
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }

        // Slack ID 형식 체크
        try {
            new SlackId(requestDto.slackId()); // SlackId 클래스에서 검증 수행
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Slack ID 형식이 올바르지 않습니다.");
        }
    }



}
