package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliverySummaryResponseDto(
    UUID deliveryId,
    UUID orderId,
    String targetAddress,
    DeliveryStatus status
) {

  public static DeliverySummaryResponseDto fromEntity(Delivery delivery) {
    return DeliverySummaryResponseDto.builder()
        .deliveryId(delivery.getId())
        .orderId(delivery.getOrderId())
        .targetAddress(
            delivery.getTargetAddress() != null ? delivery.getTargetAddress().getValue() : null)
        .status(delivery.getStatus())
        .build();
  }
}
