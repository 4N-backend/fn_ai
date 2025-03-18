package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.HubClient;
import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

  private final HubFeignClient hubFeignClient;

  @Override
  public HubRouteResponseDto getHubRoute(UUID departureHubId, UUID arrivalHubId) {
    return hubFeignClient.getHubRoute(departureHubId, arrivalHubId);
  }
}
