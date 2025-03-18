package com.fn.ai.delivery.application.client.dto;

import java.util.UUID;

public record HubRouteRequestDto(
    UUID departureHubId,
    UUID arrivalHubId
) {

  public static HubRouteRequestDto of(UUID departureHubId, UUID arrivalHubId) {
    return new HubRouteRequestDto(departureHubId, arrivalHubId);
  }
}
