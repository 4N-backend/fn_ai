package com.fn.ai.delivery.application.client;

import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import java.util.UUID;

public interface HubClient {

  HubRouteResponseDto getHubRoute(UUID departureHubId, UUID arrivalHubId);

}
