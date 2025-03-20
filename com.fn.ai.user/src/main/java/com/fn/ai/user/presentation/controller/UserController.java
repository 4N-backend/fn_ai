package com.fn.ai.user.presentation.controller;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.annotation.CurrentUserInfo;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.user.application.service.UserService;
import com.fn.ai.user.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

  /**
   * 개인 정보 조회
   * @param userContext 현재 사용자 정보
   * @return 현재 로그인된 사용자 정보
   */
  @GetMapping("/self")
  public ResponseEntity<CommonResponse<UserInfoResponseDto>> getCurrentUserInfo(
          @CurrentUserInfo UserContext userContext) {
    UUID userId = userContext.userId();

    UserInfoResponseDto userInfo = userService.getOneUserInfo(userId);

    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), userInfo);
  }

  /**
   * 유저 정보 단일 조회(MASTER)
   * @param userId 조회할 유저 ID
   */
  @GetMapping("/{userId}")
  public ResponseEntity<CommonResponse<MasterUserInfoResponseDto>> getUserById(@PathVariable UUID userId) {
    MasterUserInfoResponseDto userInfo = userService.getMasterUserInfo(userId);
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), userInfo);
  }


  /**
   * 개인 정보 수정
   * @param userContext 현재 사용자 정보
   * @param requestDto 수정할 정보
   */
  @PatchMapping("/")
  public ResponseEntity<UserInfoResponseDto> updateUserInfo(
          @CurrentUserInfo UserContext userContext,
          @RequestBody UserUpdateRequestDto requestDto) {

    UUID userId = userContext.userId();

    UserInfoResponseDto updatedUser = userService.updateUser(userId, requestDto);

    return ResponseEntity.ok(updatedUser);
  }


  @DeleteMapping("/{userId}")
  public ResponseEntity<CommonResponse<UserDeleteResponseDto>> deleteUser(
          @PathVariable UUID userId,
          @CurrentUserInfo UserContext userInfo) {

    UserDeleteResponseDto responseDto = userService.deleteUser(userId, userInfo);
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
  }
  

}
