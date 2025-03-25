package com.fn.ai.order.presentation.dto;

import java.util.UUID;

public record OrderSearchRequestDto(
    UUID orderId,
    UUID receiverId,
    UUID supplierId,
    UUID deliveryId
) {

}
