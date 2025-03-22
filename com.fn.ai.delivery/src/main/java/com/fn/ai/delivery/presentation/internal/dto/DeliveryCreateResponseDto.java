package com.fn.ai.delivery.presentation.internal.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCreateResponseDto(
    UUID deliveryId,
    UUID orderId,
    UUID departureHubId,
    UUID arrivalHubId,
    String targetAddress,
    DeliveryStatus status
) {

  public static DeliveryCreateResponseDto fromEntity(Delivery delivery) {
    return DeliveryCreateResponseDto.builder()
        .deliveryId(delivery.getId())
        .orderId(delivery.getOrderId())
        .departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .targetAddress(delivery.getTargetAddress().getValue())
        .status(delivery.getStatus())
        .build();
  }
}
