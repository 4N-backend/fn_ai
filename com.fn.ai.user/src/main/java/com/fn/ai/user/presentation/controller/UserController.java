package com.fn.ai.user.presentation.controller;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.common.context.annotation.CurrentUserInfo;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.user.application.service.DeliveryManagerService;
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

  private final DeliveryManagerService deliveryManagerService;

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
//  @GetMapping("/{userId}")
//  public ResponseEntity<CommonResponse<MasterUserInfoResponseDto>> getUserById(@PathVariable UUID userId) {
//    MasterUserInfoResponseDto userInfo = userService.getMasterUserInfo(userId);
//    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
//            CommonResponseCode.SUCCESS.getMessage(), userInfo);
//  }


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

  /**
   * 배송 담당자 등록
   * @param userId 배송 담당자로 등록할 사용자 ID
   * @param requestDto 배송 담당자 등록 정보
   * @param userContext 현재 로그인된 사용자 정보 (Bearer 토큰에서 추출됨)
   */
  @PostMapping("/{userId}/delivery-manager")
  public ResponseEntity<CommonResponse<DeliveryManagerResponseDto>> createDeliveryManager(
          @PathVariable UUID userId,
          @RequestBody DeliveryManagerRequestDto requestDto,
          @CurrentUserInfo UserContext userContext
  ) {

    UserRoleEnum role = userContext.userRole();

    // 등록 권한 확인
    if (!(role == UserRoleEnum.DELIVERY_MANAGER || role == UserRoleEnum.MASTER)) {
      throw new IllegalStateException("접근 권한이 없습니다.");
    }

    // 배송 담당자 생성
    DeliveryManagerResponseDto responseDto = deliveryManagerService.createDeliveryManager(userId, requestDto);

    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
  }


  /**
   * 배송 담당자 정보 조회 - (MASTER-모든 정보 조회 가능, DELIVERY_MANAGER-본인 정보만 조회 가능, HUB_MANAGER-본인 허브 정보만 조회 가능)
   * @param userId 조회할 배송 담당자 ID
   *
   */
  @GetMapping("/delivery-manager/{userId}")
  public ResponseEntity<CommonResponse<DeliveryManagerResponseDto>> getDeliveryManager(
          @PathVariable UUID userId,
          @CurrentUserInfo UserContext userContext
  ) {
    validateAccess(userContext.userRole(), userContext.userId(), userId);

    DeliveryManagerResponseDto responseDto = deliveryManagerService.getDeliveryManager(userId);
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
  }

  /**
   * 배송 담당자 정보 전체 조회
   * MASTER: 전체 조회 가능
   * HUB_MANAGER: 본인 허브 소속만 조회
   * DELIVERY_MANAGER: 본인만 조회
   *
   * @param page 페이지 번호 (기본값: 0)
   * @param size 페이지 크기 (기본값: 10)
   * @param sortBy 정렬 기준 필드 (기본값: createdAt)
   * @param isAsc 오름차순 여부 (기본값: true)
   */
  @GetMapping("/delivery-manager")
  public ResponseEntity<CommonResponse<Page<DeliveryManagerInfoResponseDto>>> getAllDeliveryManagers(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "createdAt") String sortBy,
          @RequestParam(defaultValue = "true") boolean isAsc,
          @CurrentUserInfo UserContext userContext
  ) {
    Page<DeliveryManagerInfoResponseDto> result =
            deliveryManagerService.getAllDeliveryManagers(userContext.userRole(), userContext.userId(), page, size, sortBy, isAsc);

    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), result);
  }


  /**
   * 배송 담당자 정보 수정
   * @param deliveryManagerId 수정할 배송 담당자 ID
   * @param requestDto 수정할 정보
   */
  @PatchMapping("/delivery-manager/{deliveryManagerId}")
  public ResponseEntity<CommonResponse<DeliveryManagerResponseDto>> updateDeliveryManager(
          @PathVariable UUID deliveryManagerId,
          @RequestBody DeliveryManagerUpdaterRequestDto requestDto,
          @CurrentUserInfo UserContext userContext
  ) {
    DeliveryManagerResponseDto responseDto = deliveryManagerService.updateDeliveryManager(
            userContext.userRole(), userContext.userId(), deliveryManagerId, requestDto
    );

    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
  }




  private void validateAccess(UserRoleEnum role, UUID requesterId, UUID targetId) {
    if (role == UserRoleEnum.MASTER) return;

    if (role == UserRoleEnum.DELIVERY_MANAGER && !requesterId.equals(targetId)) {
      throw new IllegalStateException("배송 담당자는 본인 정보만 조회할 수 있습니다.");
    }

    if (role == UserRoleEnum.HUB_MANAGER) {
      UUID targetHubId = deliveryManagerService.getHubIdOf(targetId);
      UUID myHubId = deliveryManagerService.getHubIdOf(requesterId);
      if (!targetHubId.equals(myHubId)) {
        throw new IllegalStateException("허브 관리자는 본인의 허브 배송 담당자만 조회할 수 있습니다.");
      }
      return;
    }

    throw new IllegalStateException("조회 권한이 없습니다.");
  }


}
