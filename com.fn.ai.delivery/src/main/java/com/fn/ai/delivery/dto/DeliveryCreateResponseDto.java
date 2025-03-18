package com.fn.ai.delivery.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCreateResponseDto(
    UUID deliveryId,
    UUID orderId,
    UUID startHubId,
    UUID endHubId,
    String address,
    DeliveryStatus status
) {
  public static DeliveryCreateResponseDto fromEntity(Delivery delivery) {
    return DeliveryCreateResponseDto.builder()
        .deliveryId(delivery.getId())
        .orderId(delivery.getOrderId())
        .startHubId(delivery.getDepartureHubId())
        .endHubId(delivery.getArrivalHubId())
        .address(delivery.getTargetAddress())
        .status(delivery.getStatus())
        .build();
  }
}
