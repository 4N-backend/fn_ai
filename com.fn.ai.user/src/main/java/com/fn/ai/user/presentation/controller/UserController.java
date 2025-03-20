package com.fn.ai.user.presentation.controller;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.user.application.service.UserService;
import com.fn.ai.user.presentation.dto.UserInfoResponseDto;
import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<UserSignUpResponseDto> signup(
      @RequestBody UserSignUpRequestDto requestDto) {
    UserSignUpResponseDto responseDto = userService.signup(requestDto);
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("/{username}")
  public ResponseEntity<UserSignInResponseDto> getUserByUsername(
      @PathVariable String username) {
    UserSignInResponseDto responseDto = userService.getUserByUsername(username);
    return ResponseEntity.ok().body(responseDto);
  }

  /**
   * 유저 정보 전체 조회
   * @param page 현재 페이지 번호
   * @param size 페이지당 아이템 개수
   * @param sortBy 정렬 기준 필드
   * @param isAsc 오름차순 여부
   * @return 페이징된 유저 목록 반환
   */
  @GetMapping
  public ResponseEntity<CommonResponse<Page<UserInfoResponseDto>>> getAllUser(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "createdAt") String sortBy,
          @RequestParam(defaultValue = "true") boolean isAsc) {

    Page<UserInfoResponseDto> usersInfo = userService.getAllUsers(page, size, sortBy, isAsc);

    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), usersInfo);
  }

}
