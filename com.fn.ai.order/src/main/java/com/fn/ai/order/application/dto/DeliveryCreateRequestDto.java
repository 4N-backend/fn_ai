package com.fn.ai.order.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCreateRequestDto(UUID orderId,
                                       UUID departureHubId,
                                       UUID arrivalHubId,
                                       String targetAddress) {

  public static DeliveryCreateRequestDto of(
      UUID orderId,
      UUID supplierHubId,
      UUID receiverHubId,
      String receiverHubAddress) {
    return DeliveryCreateRequestDto.builder()
        .orderId(orderId)
        .departureHubId(supplierHubId)
        .arrivalHubId(receiverHubId)
        .targetAddress(receiverHubAddress)
        .build();
  }
}
