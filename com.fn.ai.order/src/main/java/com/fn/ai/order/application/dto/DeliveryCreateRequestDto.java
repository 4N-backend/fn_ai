package com.fn.ai.order.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCreateRequestDto(UUID orderId,
                                       UUID arrivalHubId,
                                       UUID departureHubId,
                                       String targetAddress) {

  public static DeliveryCreateRequestDto of(
      UUID orderId,
      UUID receiverHubId,
      UUID supplierHubId,
      String receiverHubAddress) {
    return DeliveryCreateRequestDto.builder()
        .orderId(orderId)
        .arrivalHubId(receiverHubId)
        .departureHubId(supplierHubId)
        .targetAddress(receiverHubAddress)
        .build();
  }
}
