package com.fn.ai.order.application.dto;

import com.fn.ai.order.presentation.dto.OrderItemRequestDto;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductStockRequestDto(
    UUID productId,
    int stock
) {

  public static ProductStockRequestDto of(OrderItemRequestDto orderItemRequestDto) {
    return ProductStockRequestDto.builder()
        .productId(orderItemRequestDto.productId())
        .stock(orderItemRequestDto.quantity())
        .build();
  }
}
