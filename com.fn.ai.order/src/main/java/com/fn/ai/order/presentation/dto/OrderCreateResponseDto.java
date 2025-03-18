package com.fn.ai.order.presentation.dto;

import com.fn.ai.order.model.Order;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCreateResponseDto(UUID OrderId,
                                     UUID supplierId,
                                     UUID receiverId,
                                     UUID deliveryId,
                                     String instruction,
                                     List<OrderItemResponseDto> orderItems) {

  public static OrderCreateResponseDto of(Order order) {
    return OrderCreateResponseDto.builder()
        .OrderId(order.getId())
        .supplierId(order.getSupplierId())
        .receiverId(order.getSupplierId())
        .deliveryId(order.getDeliveryId())
        .instruction(order.getInstruction())
        .orderItems(order.getOrderItems().stream().map(OrderItemResponseDto::of).toList())
        .build();
  }

}
