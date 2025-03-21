package com.fn.ai.user.application.service;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.presentation.dto.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DeliveryManagerService {
    DeliveryManagerResponseDto createDeliveryManager(UUID userId, DeliveryManagerRequestDto requestDto);

    // 배송 담당자 정보 단건 조회
    DeliveryManagerResponseDto getDeliveryManager(UUID userId);

    // 허브 ID 조회 (HUB_MANAGER 권한 체크용)
    UUID getHubIdOf(UUID userId);

    Page<DeliveryManagerInfoResponseDto> getAllDeliveryManagers(UserRoleEnum role, UUID requesterId,
                                                                int page, int size, String sortBy, boolean isAsc);

    DeliveryManagerResponseDto updateDeliveryManager(UserRoleEnum role, UUID requesterId,
                                                     UUID targetId, DeliveryManagerUpdaterRequestDto requestDto);

    DeliveryManagerDeleteResponseDto deleteDeliveryManager(UserRoleEnum role, UUID requesterId, UUID deliveryManagerId);

}
