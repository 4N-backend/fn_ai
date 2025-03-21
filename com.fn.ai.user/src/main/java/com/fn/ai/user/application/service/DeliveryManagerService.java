package com.fn.ai.user.application.service;

import com.fn.ai.user.presentation.dto.DeliveryManagerRequestDto;
import com.fn.ai.user.presentation.dto.DeliveryManagerResponseDto;

import java.util.UUID;

public interface DeliveryManagerService {
    DeliveryManagerResponseDto createDeliveryManager(UUID userId, DeliveryManagerRequestDto requestDto);
}
