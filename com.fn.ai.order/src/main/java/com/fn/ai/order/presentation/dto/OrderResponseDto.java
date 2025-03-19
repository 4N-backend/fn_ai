package com.fn.ai.order.presentation.dto;

import com.fn.ai.order.model.Order;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderResponseDto(UUID orderId,
                               UUID receiverId,
                               UUID supplierId,
                               UUID deliveryId,
                               String instruction,
                               List<OrderItemResponseDto> orderItems) {

  public static OrderResponseDto from(Order order) {
    return OrderResponseDto.builder()
        .orderId(order.getId())
        .receiverId(order.getReceiverId())
        .supplierId(order.getSupplierId())
        .deliveryId(order.getDeliveryId())
        .instruction(order.getInstruction())
        .orderItems(order.getOrderItemList().stream().map(OrderItemResponseDto::of).toList())
        .build();
  }
}
