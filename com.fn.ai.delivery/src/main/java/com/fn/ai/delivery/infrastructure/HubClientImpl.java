package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.HubClient;
import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

  private final HubFeignClient hubFeignClient;

  @Override
  public HubRouteResponseDto getHubRoute(HubRouteRequestDto requestDto) {
    return hubFeignClient.getHubRoute(requestDto);
  }
}
