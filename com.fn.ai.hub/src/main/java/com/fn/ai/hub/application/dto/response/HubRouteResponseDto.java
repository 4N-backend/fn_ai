package com.fn.ai.hub.application.dto.response;

import com.fn.ai.hub.domain.HubRoute;
import java.util.UUID;

public record HubRouteResponseDto(
    UUID id,
    UUID departureHubId,
    UUID arrivalHubId,
    long travelTime,
    double distance
) {

    public static HubRouteResponseDto of(HubRoute hubRoute) {
        return new HubRouteResponseDto(hubRoute.getId(), hubRoute.getDepatureHubId(),
            hubRoute.getArrivalHubId(),
            hubRoute.getTravelTime().getValue(), hubRoute.getDistance().getValue());
    }
}

