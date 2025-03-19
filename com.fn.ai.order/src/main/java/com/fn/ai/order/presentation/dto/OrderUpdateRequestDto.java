package com.fn.ai.order.presentation.dto;

import java.util.List;
import java.util.UUID;

public record OrderUpdateRequestDto(UUID receiverId,
                                    UUID supplierId,
                                    UUID deliveryId,
                                    String instruction,
                                    List<OrderItemRequestDto> orderItems) {

}
