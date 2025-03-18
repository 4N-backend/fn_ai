package com.fn.ai.delivery.application.client.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public record HubRouteResponseDto(
    List<RouteStopInfo> routeStopInfos
) {

  public record RouteStopInfo(
      UUID departureHubId,
      UUID arrivalHubId,
      BigDecimal estimatedDistance,
      Duration estimatedDuration
  ) {

  }
}
