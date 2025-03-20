package com.fn.ai.hub.application.dto.response;

import com.fn.ai.hub.domain.HubRoute;
import java.util.UUID;

public record HubRouteResponseDto(
    UUID id,
    UUID departureHubId,
    String departureHubName,
    UUID arrivalHubId,
    String arrivalHubName,
    long travelTime,
    double distance
) {

    public static HubRouteResponseDto of(HubRoute hubRoute) {
        return new HubRouteResponseDto(hubRoute.getId(), hubRoute.getDepartureHubId(),
            hubRoute.getDepartureHubName(),
            hubRoute.getArrivalHubId(), hubRoute.getArrivalHubName(),
            hubRoute.getTravelTime().getValue(), hubRoute.getDistance().getValue());
    }
}

