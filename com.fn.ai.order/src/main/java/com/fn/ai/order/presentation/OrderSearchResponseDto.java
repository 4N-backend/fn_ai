package com.fn.ai.order.presentation;

import com.fn.ai.order.model.Order;
import com.fn.ai.order.model.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record OrderSearchResponseDto(
    UUID orderId,
    UUID receiverId,
    UUID supplierId,
    UUID deliveryId,
    String createdBy,
    List<UUID> productIds,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt,
    String deletedBy,
    LocalDateTime deletedAt
) {

  public static OrderSearchResponseDto from(Order order) {
    return OrderSearchResponseDto.builder()
        .orderId(order.getId())
        .receiverId(order.getReceiverId())
        .supplierId(order.getSupplierId())
        .deliveryId(order.getDeliveryId())
        .createdBy(order.getCreatedBy())
        .createdAt(order.getCreatedAt())
        .updatedBy(order.getUpdatedBy())
        .updatedAt(order.getUpdatedAt())
        .productIds(order.getOrderItemList().stream().map(OrderItem::getProductId)
            .collect(Collectors.toList()))
        .build();
  }
}
