package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryDetailsResponseDto(
    UUID deliveryId,
    UUID orderId,
    UUID departureHubId,
    UUID arrivalHubId,
    String targetAddress,
    DeliveryStatus status
) {

  public static DeliveryDetailsResponseDto fromEntity(Delivery delivery) {
    return DeliveryDetailsResponseDto.builder()
        .deliveryId(delivery.getId())
        .orderId(delivery.getOrderId())
        .departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .targetAddress(delivery.getTargetAddress().getValue())
        .status(delivery.getStatus())
        .build();
  }
}
