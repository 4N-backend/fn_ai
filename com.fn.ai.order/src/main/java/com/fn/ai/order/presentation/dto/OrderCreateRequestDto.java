package com.fn.ai.order.presentation.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestDto(UUID receiverId,
                                    UUID supplierId,
                                    List<OrderItemRequestDto> orderItems,
                                    String instruction) {

}
