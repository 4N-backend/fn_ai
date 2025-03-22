package com.fn.ai.delivery.application.client.dto;

import java.util.UUID;

public record DeliveryManagerAssignRequestDto(
    UUID departureHubId
) {

  public static DeliveryManagerAssignRequestDto of(UUID departureHubId) {
    return new DeliveryManagerAssignRequestDto(departureHubId);
  }
}
