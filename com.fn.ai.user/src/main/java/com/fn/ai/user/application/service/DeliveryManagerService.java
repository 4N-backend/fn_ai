package com.fn.ai.user.application.service;

import com.fn.ai.user.presentation.dto.DeliveryManagerRequestDto;
import com.fn.ai.user.presentation.dto.DeliveryManagerResponseDto;

import java.util.UUID;

public interface DeliveryManagerService {
    DeliveryManagerResponseDto createDeliveryManager(UUID userId, DeliveryManagerRequestDto requestDto);

    // 배송 담당자 정보 단건 조회
    DeliveryManagerResponseDto getDeliveryManager(UUID userId);

    // 허브 ID 조회 (HUB_MANAGER 권한 체크용)
    UUID getHubIdOf(UUID userId);
}
