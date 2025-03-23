package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.type.DeliveryType;
import java.util.UUID;

public record DeliveryManagerInfoResponseDto(
        UUID id,
        String username,
        UUID hubId,
        DeliveryType type,
        int deliverySequence
) {
    public static DeliveryManagerInfoResponseDto of(DeliveryManager dm) {
        return new DeliveryManagerInfoResponseDto(
                dm.getId(),
                dm.getUser().getUsername().getValue(),
                dm.getHubId(),
                dm.getType(),
                dm.getDeliverySequence()
        );
    }
}
