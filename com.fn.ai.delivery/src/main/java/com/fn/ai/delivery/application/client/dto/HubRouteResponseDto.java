package com.fn.ai.delivery.application.client.dto;

import com.fn.ai.delivery.model.DeliveryRoute;
import java.util.List;
import java.util.UUID;

public record HubRouteResponseDto(
    List<RouteInfo> routeInfos
) {

  public record RouteInfo(
      UUID departureHubId,
      UUID arrivalHubId,
      Double distance,
      Long travelTime
  ) {

    public DeliveryRoute toEntity(int sequence) {
      return DeliveryRoute.of(
          this.departureHubId(),
          this.arrivalHubId(),
          this.distance(),
          this.travelTime(),
          sequence
      );
    }
  }
}
