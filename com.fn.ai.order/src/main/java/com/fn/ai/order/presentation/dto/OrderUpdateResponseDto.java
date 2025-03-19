package com.fn.ai.order.presentation.dto;

import com.fn.ai.order.model.Order;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderUpdateResponseDto(UUID receiverId,
                                     UUID supplierId,
                                     UUID deliveryId,
                                     String instruction,
                                     List<OrderItemResponseDto> orderItems) {

  public static OrderUpdateResponseDto from(Order order) {
    return OrderUpdateResponseDto.builder()
        .receiverId(order.getReceiverId())
        .supplierId(order.getSupplierId())
        .deliveryId(order.getSupplierId())
        .instruction(order.getInstruction())
        .orderItems(order.getOrderItemList().stream().map(OrderItemResponseDto::of).toList())
        .build();
  }
}
