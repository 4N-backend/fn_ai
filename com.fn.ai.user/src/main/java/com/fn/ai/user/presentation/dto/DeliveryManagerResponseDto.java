package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.type.DeliveryType;
import java.util.UUID;

public record DeliveryManagerResponseDto(
        UUID id,
        UUID userId,
        UUID hubId,
        DeliveryType type,
        long deliverySequence
) {
    public static DeliveryManagerResponseDto from(DeliveryManager deliveryManager) {
        return new DeliveryManagerResponseDto(
                deliveryManager.getId(),
                deliveryManager.getUser().getId(),
                deliveryManager.getHubId(),
                deliveryManager.getType(),
                deliveryManager.getDeliverySequence()
        );
    }
}

