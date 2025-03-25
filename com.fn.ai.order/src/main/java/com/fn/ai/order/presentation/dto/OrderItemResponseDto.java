package com.fn.ai.order.presentation.dto;

import com.fn.ai.order.domain.model.OrderItem;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderItemResponseDto(UUID productId,
                                   int quantity) {

  public static OrderItemResponseDto of(OrderItem orderItem) {
    return OrderItemResponseDto.builder()
        .productId(orderItem.getProductId())
        .quantity(orderItem.getQuantity())
        .build();
  }
}
